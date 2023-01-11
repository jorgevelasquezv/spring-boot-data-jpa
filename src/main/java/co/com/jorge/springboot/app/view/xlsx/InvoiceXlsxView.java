package co.com.jorge.springboot.app.view.xlsx;

import co.com.jorge.springboot.app.models.entities.Invoice;
import co.com.jorge.springboot.app.models.entities.ItemInvoice;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.*;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import java.util.Map;

@Component("invoice/see.xlsx")
public class InvoiceXlsxView extends AbstractXlsxView {


    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {

        MessageSourceAccessor messages = getMessageSourceAccessor();

        response.setHeader("Content-Disposition", "attachment; filename=\"Invoice_view.xlsx\"");

        Invoice invoice = (Invoice) model.get("invoice");

        Sheet sheet = workbook.createSheet("Factura Spring");

        Row row = sheet.createRow(0);
        Cell cell = row.createCell(0);

        cell.setCellValue(messages.getMessage("text.invoice.form.datos.cliente"));
        row = sheet.createRow(1);
        cell = row.createCell(0);
        cell.setCellValue(invoice.getClient().getName() + " " + invoice.getClient().getLastname());

        row = sheet.createRow(2);
        cell = row.createCell(0);
        cell.setCellValue(invoice.getClient().getEmail());

        sheet.createRow(4).createCell(0).setCellValue(messages.getMessage("text.invoice.form.datos.factura"));
        sheet.createRow(5).createCell(0).setCellValue( messages.getMessage("text.see.folio") + ": " + invoice.getId());
        sheet.createRow(6).createCell(0).setCellValue( messages.getMessage("text.see.descripcion") + ": " + invoice.getDescription());
        sheet.createRow(7).createCell(0).setCellValue( messages.getMessage("text.see.fecha") + ": " + invoice.getCreateAt());

        CellStyle theaderStyle= workbook.createCellStyle();
        theaderStyle.setBorderBottom(BorderStyle.MEDIUM);
        theaderStyle.setBorderTop(BorderStyle.MEDIUM);
        theaderStyle.setBorderRight(BorderStyle.MEDIUM);
        theaderStyle.setBorderLeft(BorderStyle.MEDIUM);
        theaderStyle.setFillBackgroundColor(IndexedColors.BRIGHT_GREEN1.index);
        theaderStyle.setFillPattern(FillPatternType.FINE_DOTS);

        CellStyle tbodyStyle= workbook.createCellStyle();
        tbodyStyle.setBorderBottom(BorderStyle.THIN);
        tbodyStyle.setBorderTop(BorderStyle.THIN);
        tbodyStyle.setBorderRight(BorderStyle.THIN);
        tbodyStyle.setBorderLeft(BorderStyle.THIN);


        Row header = sheet.createRow(9);
        header.createCell(0).setCellValue(messages.getMessage("text.invoice.form.producto"));
        header.createCell(1).setCellValue(messages.getMessage("text.invoice.form.precio"));
        header.createCell(2).setCellValue(messages.getMessage("text.invoice.form.cantidad"));
        header.createCell(3).setCellValue(messages.getMessage("text.invoice.form.total"));

        header.getCell(0).setCellStyle(theaderStyle);
        header.getCell(1).setCellStyle(theaderStyle);
        header.getCell(2).setCellStyle(theaderStyle);
        header.getCell(3).setCellStyle(theaderStyle);
        int rowNum = 10;
        for (ItemInvoice item: invoice.getItemInvoices() ) {
            Row rowItem = sheet.createRow(rowNum ++);
            cell = rowItem.createCell(0);
            cell.setCellValue(item.getProduct().getName());
            cell.setCellStyle(tbodyStyle);

            cell = rowItem.createCell(1);
            cell.setCellValue(item.getProduct().getPrice());
            cell.setCellStyle(tbodyStyle);

            cell = rowItem.createCell(2);
            cell.setCellValue(item.getQuantity());
            cell.setCellStyle(tbodyStyle);

            cell = rowItem.createCell(3);
            cell.setCellValue(item.calculatePrice());
            cell.setCellStyle(tbodyStyle);
        }

        Row rowTotal = sheet.createRow(rowNum);
        cell = rowTotal.createCell(2);
        cell.setCellValue(messages.getMessage("text.invoice.form.grantotal") + ": ");
        cell.setCellStyle(tbodyStyle);

        cell = rowTotal.createCell(3);
        cell.setCellValue(invoice.getTotal());
        cell.setCellStyle(tbodyStyle);
    }
}
