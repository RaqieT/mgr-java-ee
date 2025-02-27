package pl.raqiet.housing.cooperative.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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

    @RequestMapping("/bills/delete/{billId}")
    public String deleteBill(@PathVariable("billId") UUID id) {
        billService.removeBill(id);
        return "redirect:/bills";
    }

    @RequestMapping(value = "/bills/{id}/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> getBillPdf(@PathVariable("id") UUID billId) {
        byte[] myBillPdf = billService.getBillPdf(billId);
        HttpHeaders headers = new HttpHeaders();
        String filename = System.currentTimeMillis() + ".pdf";
        headers.setContentDispositionFormData(filename, filename);
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        return new ResponseEntity<>(myBillPdf, headers, HttpStatus.OK);
    }
}
