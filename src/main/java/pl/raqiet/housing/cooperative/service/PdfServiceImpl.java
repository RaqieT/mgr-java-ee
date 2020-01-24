package pl.raqiet.housing.cooperative.service;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.raqiet.housing.cooperative.api.service.PdfService;
import pl.raqiet.housing.cooperative.domain.Const;
import pl.raqiet.housing.cooperative.domain.entity.AppUser;
import pl.raqiet.housing.cooperative.domain.entity.Bill;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class PdfServiceImpl implements PdfService {
    private final I18nService i18nService;

    public byte[] generateBillPdf(Bill bill, AppUser appUser) {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
//            OutputStream o = response.getOutputStream();
//            response.setCharacterEncoding("UTF-8");
//            response.setContentType("application/pdf");
//            response.setHeader("Content-Disposition", "inline; filename=" + appUser.getLogin() + ".pdf");
            Document pdf = new Document();
            PdfWriter.getInstance(pdf, byteArrayOutputStream);
            pdf.open();
            pdf.add(new Paragraph(i18nService.getMessage("bill") + ": " + bill.getId() + "/" + bill.getRegisterTime()));
            pdf.add(new Paragraph(Chunk.NEWLINE));
            pdf.add(new Paragraph(i18nService.getMessage("user") + ": " + appUser.getFirstName() + " " + appUser.getLastName() + " | " + bill.getRegisterTime()));
            pdf.add(new Paragraph(Chunk.NEWLINE));
            PdfPTable table = new PdfPTable(3);
            table.addCell(i18nService.getMessage("gas.usage"));
            table.addCell(String.valueOf(bill.getGasUsage()));
            table.addCell(bill.getGasPrice() + " PLN");
            table.addCell(i18nService.getMessage("power.usage"));
            table.addCell(String.valueOf(bill.getPowerUsage()));
            table.addCell(bill.getPowerPrice() + " PLN");
            table.addCell(i18nService.getMessage("water.usage"));
            table.addCell(String.valueOf(bill.getWaterUsage()));
            table.addCell(bill.getWaterPrice() + " PLN");
            pdf.add(table);
            pdf.close();
            return byteArrayOutputStream.toByteArray();
        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        }

        return new byte[0];
    }
}

