package pl.raqiet.housing.cooperative.api.service;

import org.springframework.security.access.annotation.Secured;
import pl.raqiet.housing.cooperative.domain.entity.Bill;
import pl.raqiet.housing.cooperative.domain.entity.Flat;

import java.util.List;
import java.util.UUID;

public interface BillService {
    @Secured({"ROLE_LOCATOR"})
    void addLoggedInUserFlatBill(Bill bill);
    void addBill(Bill bill);
    @Secured({"ROLE_LOCATOR","ROLE_ADMINISTRATOR", "ROLE_MODERATOR"})
    void editBill(Bill bill);
    @Secured({"ROLE_ADMINISTRATOR", "ROLE_MODERATOR", "ROLE_LOCATOR"})
    List<Bill> listAllBills();
    @Secured({"ROLE_ADMINISTRATOR", "ROLE_MODERATOR", "ROLE_LOCATOR"})
    Bill getBill(UUID id);
    @Secured({"ROLE_ADMINISTRATOR", "ROLE_MODERATOR"})
    void toggleApprove(UUID billId);
    @Secured("ROLE_LOCATOR")
    Bill getCurrentLocatorBill();
    @Secured({"ROLE_ADMINISTRATOR", "ROLE_MODERATOR"})
    void removeBill(UUID billId);

    byte[] getBillPdf(UUID id);
}
