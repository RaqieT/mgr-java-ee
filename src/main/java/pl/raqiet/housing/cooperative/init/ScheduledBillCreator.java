package pl.raqiet.housing.cooperative.init;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.raqiet.housing.cooperative.api.service.BillService;
import pl.raqiet.housing.cooperative.api.service.FlatService;
import pl.raqiet.housing.cooperative.domain.Const;
import pl.raqiet.housing.cooperative.domain.entity.Bill;
import pl.raqiet.housing.cooperative.domain.entity.Flat;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ScheduledBillCreator {
    private final FlatService flatService;
    private final BillService billService;

    @Scheduled(cron = "0 0 0 1 * *")
    public void timeIsUpLetsDoThisLeeeeroyJenkins() {
        System.out.println("Generating bills...");
        List<Flat> notBilledFlatsOfPreviousMonth = flatService.getNotBilledFlatsOfPreviousMonth();
        notBilledFlatsOfPreviousMonth.forEach(flat -> billService.addBill(getStandardBill(flat)));
        System.out.println("Done.");
    }

    private Bill getStandardBill(Flat flat) {
        Bill bill = new Bill();
        bill.setApproved(true);
        bill.setFlat(flat);
        bill.setGasUsage(Const.ReadingsDefaultValues.GAS_VALUE);
        bill.setPowerUsage(Const.ReadingsDefaultValues.POWER_VALUE);
        bill.setWaterUsage(Const.ReadingsDefaultValues.WATER_VALUE);
        bill.setRegisterTime(LocalDateTime.now().minusMonths(1));
        return bill;
    }
}
