package pl.raqiet.housing.cooperative.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.raqiet.housing.cooperative.api.service.BillService;
import pl.raqiet.housing.cooperative.domain.entity.Bill;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("rest")
public class BillRestController {
    private final BillService billService;

    @RequestMapping(value = "/bill/{id}", method = RequestMethod.GET, produces = "application/json")
    public Bill getBill(@PathVariable("id") UUID id) {
        return billService.getBill(id);
    }

    @RequestMapping(value = "/bill", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody List<Bill> getBills() {
        return billService.listAllBills();
    }
}
