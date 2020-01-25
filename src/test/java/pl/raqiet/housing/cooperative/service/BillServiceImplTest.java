package pl.raqiet.housing.cooperative.service;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.mockito.stubbing.Answer;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import pl.raqiet.housing.cooperative.api.service.AppUserService;
import pl.raqiet.housing.cooperative.api.service.BillService;
import pl.raqiet.housing.cooperative.api.service.FlatService;
import pl.raqiet.housing.cooperative.api.service.PdfService;
import pl.raqiet.housing.cooperative.dao.BillRepository;
import pl.raqiet.housing.cooperative.domain.entity.AppUser;
import pl.raqiet.housing.cooperative.domain.entity.Bill;
import pl.raqiet.housing.cooperative.domain.entity.Flat;
import pl.raqiet.housing.cooperative.domain.entity.Role;
import pl.raqiet.housing.cooperative.util.HousingCooperativeException;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

public class BillServiceImplTest {
    private static BillService billService;
    private static FlatService flatService;
    private static PdfService pdfService;
    private static AppUserService appUserService;
    private static BillRepository billRepository;

    @BeforeClass
    public static void init() {
        billRepository = mock(BillRepository.class);
        appUserService =  mock(AppUserService.class);
        pdfService = mock(PdfService.class);
        flatService = mock(FlatService.class);
        billService = new BillServiceImpl(billRepository, flatService, pdfService, appUserService);
    }

    @AfterEach
    public void tearDown() {
        SecurityContextHolder.getContext().setAuthentication(null);
    }

    @Test
    public void removeBillDeniedAuthTest() {
        // given
        UUID id = UUID.randomUUID();

        // when, then
        assertThrows(AccessDeniedException.class, () -> billService.removeBill(id));
    }

    @Test
    public void removeBillPassAuthTest() {
        // given
        UUID id = UUID.randomUUID();

        // when
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                "user",
                "ADMINISTRATOR",
                Collections.singleton(Role.ADMINISTRATOR)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // when
        assertDoesNotThrow(() -> billService.removeBill(id));
    }

    @Test
    public void addLoggedInUserFlatBillWithoutContext() {
        // when, then
        assertThrows(HousingCooperativeException.class, () -> billService.addLoggedInUserFlatBill(new Bill()));
    }

    @Test
    public void editBillLocatorTest() {
        // given
        Bill bill = new Bill();
        Flat flat = new Flat();
        AppUser appUser = new AppUser();
        String loggedInLocator = "locator";


        // when
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                loggedInLocator,
                "LOCATOR",
                Collections.singleton(Role.LOCATOR)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        appUser.setUsername(loggedInLocator);
        flat.setOwner(appUser);
        bill.setFlat(flat);
        UUID uuid = UUID.randomUUID();
        bill.setId(uuid);

        when(billRepository.findById(uuid)).thenReturn(Optional.of(bill));
        billService.editBill(bill);

        // then
        Bill result = billService.getBill(uuid);
        assertEquals(bill, result);
        assertEquals(bill.getFlat(), result.getFlat());
    }

    @Test
    public void editBillWhichNotBelongsToLocatorTest() {
        // given
        Bill bill = new Bill();
        UUID uuid = UUID.randomUUID();
        Flat flat = new Flat();
        AppUser appUser = new AppUser();
        String loggedInLocator = "locator";


        // when
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                loggedInLocator,
                "LOCATOR",
                Collections.singleton(Role.LOCATOR)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        appUser.setUsername("differentUsername");
        flat.setOwner(appUser);
        bill.setFlat(flat);
        bill.setId(uuid);

        when(billRepository.findById(uuid)).thenReturn(Optional.of(bill));

        // then
        assertThrows(AccessDeniedException.class, () -> billService.editBill(bill));
    }

    @Test
    public void addLoggedInUserFlatBillTest() {
        // given
        Bill bill = new Bill();
        Flat flat = new Flat();

        // when
        when(flatService.getFlatOfLoggedInUser()).thenReturn(flat);

        // then
        assertDoesNotThrow(() -> billService.addLoggedInUserFlatBill(bill));
        assertEquals(flat, bill.getFlat());
    }

    @Test
    public void listAllBillsForLocator() {
        // given
        String locatorUsername = "abc";
        AppUser appUser1 = new AppUser();
        appUser1.setUsername(locatorUsername);
        AppUser appUser2 = new AppUser();
        appUser2.setUsername("gbnwhja");

        Flat flat1 = new Flat();
        flat1.setOwner(appUser1);
        Flat flat2 = new Flat();
        flat2.setOwner(appUser1);

        Bill bill1 = new Bill();
        bill1.setId(UUID.randomUUID());
        bill1.setFlat(flat1);

        Bill bill2 = new Bill();
        bill2.setId(UUID.randomUUID());
        bill2.setFlat(flat1);

        Bill bill3 = new Bill();
        bill3.setId(UUID.randomUUID());
        bill3.setFlat(flat2);

        // when
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                locatorUsername,
                "LOCATOR",
                Collections.singleton(Role.LOCATOR)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        when(billRepository.findAllByFlatBlockModeratorsContainingUserWithUsernameOrderByRegisterTimeDesc(locatorUsername)).thenReturn(Arrays.asList(bill1, bill2, bill3));
        when(billRepository.findAllByOrderByRegisterTimeDesc()).thenReturn(Arrays.asList(bill1, bill2, bill3));
        when(billRepository.findAllByFlatOwnerUsernameOrderByRegisterTimeDesc(locatorUsername)).thenReturn(Arrays.asList(bill1, bill2));

        // then
        assertEquals(2, billService.listAllBills().size());
    }
}
