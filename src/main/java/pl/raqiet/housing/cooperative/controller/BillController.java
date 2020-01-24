package pl.raqiet.housing.cooperative.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.raqiet.housing.cooperative.api.service.BillService;
import pl.raqiet.housing.cooperative.api.service.FlatService;
import pl.raqiet.housing.cooperative.domain.entity.Bill;
import pl.raqiet.housing.cooperative.domain.entity.Role;
import pl.raqiet.housing.cooperative.util.AuthUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class BillController {
    private final BillService billService;
    private final FlatService flatService;

    @RequestMapping(value = "/bills")
    public String showBills(Model model) {
        model.addAttribute("billList", billService.listAllBills());
        if (AuthUtils.isLoggedInUserInRole(Role.LOCATOR)) {
            model.addAttribute("flat", flatService.getFlatOfLoggedInUser());
        }
        return "bill.html";
    }

    @RequestMapping(value = "/bills/manager")
    public String showBillManager(Model model, HttpServletRequest request) {
        String billId;
        Bill bill;
        try {
            billId = ServletRequestUtils.getRequiredStringParameter(request, "billId");
            bill = billService.getBill(UUID.fromString(billId));
        } catch (ServletRequestBindingException e) {
            bill = new Bill();
        }
        model.addAttribute("bill", bill);
        return "billManager.html";
    }

    @RequestMapping(value = "/bills/my-bill")
    public String showBillManager(Model model) {
        Bill bill = billService.getCurrentLocatorBill();
        if (bill == null) {
            bill = new Bill();
        }
        model.addAttribute("bill", bill);
        return "billManager.html";
    }

    @RequestMapping(value = "/bills/save", method = RequestMethod.POST)
    public String saveBill(@ModelAttribute Bill bill) {

        if (bill.getId() == null) {
            billService.addLoggedInUserFlatBill(bill);
        } else {
            billService.editBill(bill);
        }

        return "redirect:/bills";
    }

    @RequestMapping(value = "/bills/approve")
    public String toggleApprove(HttpServletRequest request) {
        String billId;
        try {
            billId = ServletRequestUtils.getRequiredStringParameter(request, "billId");
        } catch (ServletRequestBindingException e) {
            return "redirect:/bills";
        }
        billService.toggleApprove(UUID.fromString(billId));
        return "redirect:/bills";
    }
}
