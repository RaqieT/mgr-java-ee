package pl.raqiet.housing.cooperative.api.service;

import pl.raqiet.housing.cooperative.domain.entity.AppUser;
import pl.raqiet.housing.cooperative.domain.entity.Bill;


public interface PdfService {
    byte[] generateBillPdf(Bill bill, AppUser appUser);
}

