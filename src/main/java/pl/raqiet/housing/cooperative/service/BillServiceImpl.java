package pl.raqiet.housing.cooperative.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.raqiet.housing.cooperative.api.service.BillService;
import pl.raqiet.housing.cooperative.api.service.FlatService;
import pl.raqiet.housing.cooperative.dao.BillRepository;
import pl.raqiet.housing.cooperative.domain.entity.AppUser;
import pl.raqiet.housing.cooperative.domain.entity.Bill;
import pl.raqiet.housing.cooperative.domain.entity.Flat;
import pl.raqiet.housing.cooperative.domain.entity.Role;
import pl.raqiet.housing.cooperative.util.AuthUtils;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class BillServiceImpl implements BillService {
    private final BillRepository billRepository;
    private final FlatService flatService;

    @Override
    public void addLoggedInUserFlatBill(Bill bill) {
        Flat flat = flatService.getFlatOfLoggedInUser();
        if (flat == null) {
            System.out.println("User has no flat");
            return;
        }
        bill.setFlat(flat);
        billRepository.save(bill);
    }

    @Override
    public void editBill(Bill bill) {
        Bill byId = billRepository.findById(bill.getId()).orElse(null);
        if (byId == null) {
            System.out.println("Bill not found");
            return;
        }

        if (AuthUtils.isLoggedInUserInRole(Role.MODERATOR)) {
            if (byId.getFlat().getBlock().getModerators().stream()
                    .map(AppUser::getUsername)
                    .noneMatch(un -> un.equals(AuthUtils.getLoggedInUserUsername()))) {
                throw new AccessDeniedException("No access");
            }
        }

        if (AuthUtils.isLoggedInUserInRole(Role.LOCATOR)) {
            if (!byId.getFlat().getOwner().getUsername().equals(AuthUtils.getLoggedInUserUsername())) {
                throw new AccessDeniedException("No access");
            }
        }

        bill.setFlat(byId.getFlat());
        billRepository.save(bill);
    }

    @Override
    public List<Bill> listAllBills() {
        if (AuthUtils.isLoggedInUserInRole(Role.ADMINISTRATOR)) {
            return billRepository.findAllByOrderByCreationTimeAsc();
        }

        if (AuthUtils.isLoggedInUserInRole(Role.MODERATOR)) {
            return billRepository
                    .findAllByFlatBlockModeratorsContainingUserWithUsernameOrderByCreationTimeAsc(AuthUtils.getLoggedInUserUsername());
        }

        if (AuthUtils.isLoggedInUserInRole(Role.LOCATOR)) {
            return billRepository.findAllByFlatOwnerUsernameOrderByCreationTimeAsc(AuthUtils.getLoggedInUserUsername());
        }

        throw new AccessDeniedException("No access");
    }

    @Override
    public Bill getBill(UUID id) {
        Bill bill = billRepository.findById(id).orElse(null);
        if (AuthUtils.isLoggedInUserInRole(Role.ADMINISTRATOR)) {
            return bill;
        }

        if (bill == null) {
            return null;
        }

        if (AuthUtils.isLoggedInUserInRole(Role.MODERATOR)) {
            if (bill.getFlat().getBlock().getModerators().stream()
                    .map(AppUser::getUsername)
                    .anyMatch(un -> un.equals(AuthUtils.getLoggedInUserUsername()))) {
                return bill;
            }
        }

        if (AuthUtils.isLoggedInUserInRole(Role.LOCATOR)) {
            if (AuthUtils.getLoggedInUserUsername().equals(bill.getFlat().getOwner().getUsername())) {
                return bill;
            }
        }

        throw new AccessDeniedException("No access");
    }

    @Override
    public void toggleApprove(UUID billId) {
        Bill byId = billRepository.findById(billId).orElse(null);

        if (byId == null) {
            System.out.println("Bill not found");
            return;
        }

        if (AuthUtils.isLoggedInUserInRole(Role.MODERATOR)) {
            if (byId.getFlat().getBlock().getModerators().stream()
                    .map(AppUser::getUsername)
                    .noneMatch(un -> un.equals(AuthUtils.getLoggedInUserUsername()))) {
                throw new AccessDeniedException("No access");
            }
        }

        byId.setApproved(!byId.isApproved());
        billRepository.save(byId);
    }

    @Override
    public Bill getCurrentLocatorBill() {
        String loggedInUserUsername = AuthUtils.getLoggedInUserUsername();
        Bill byFlatOwnerUsernameAndCreationTimeIsAfter = billRepository.findByFlatOwnerUsernameAndCreationTimeIsAfter(loggedInUserUsername, LocalDate.now().withDayOfMonth(1).atStartOfDay());
        return byFlatOwnerUsernameAndCreationTimeIsAfter;
    }
}
