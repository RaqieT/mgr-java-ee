package pl.raqiet.housing.cooperative.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

    @RequestMapping(value = "/bills/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Bill getBill(@PathVariable("id") UUID id) {
        return billService.getBill(id);
    }

    @RequestMapping(value = "/bills", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<Bill> getBills() {
        return billService.listAllBills();
    }
}
