package co.com.jorge.springboot.app.view.pdf;

import co.com.jorge.springboot.app.models.entities.Invoice;
import co.com.jorge.springboot.app.models.entities.ItemInvoice;
import com.lowagie.text.Document;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import java.awt.*;
import java.util.Locale;
import java.util.Map;

@Component("invoice/see")
public class InvoicePdfView extends AbstractPdfView {

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private LocaleResolver localeResolver;

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {

        Locale locale = localeResolver.resolveLocale(request);

//        Tambien se puede emplear este método predefinido en la clase para realizar traducciones
        MessageSourceAccessor messages = getMessageSourceAccessor();
        messages.getMessage("text.invoice.form.cliente");

        Invoice invoice = (Invoice) model.get("invoice");

        PdfPTable table = new PdfPTable(1);
        table.setSpacingAfter(20);

        PdfPCell cell = null;

        cell = new PdfPCell(new Phrase(String.format(messageSource.getMessage("text.pdf.cliente", null, locale))));
        cell.setBackgroundColor(new Color(184, 218, 255));
        cell.setPadding(8f);

        table.addCell(cell);
        table.addCell(invoice.getClient().getName() + " " + invoice.getClient().getLastname());
        table.addCell(invoice.getClient().getEmail());

        PdfPTable table2 = new PdfPTable(1);
        table2.setSpacingAfter(20);

        cell = new PdfPCell(new Phrase(String.format(messageSource.getMessage("text.pdf.factura", null, locale))));
        cell.setBackgroundColor(new Color(195, 230, 203));
        cell.setPadding(8f);

        table2.addCell(cell);
        table2.addCell(String.format(messageSource.getMessage("text.see.folio", null, locale)) + ": " + invoice.getId() );
        table2.addCell(String.format(messageSource.getMessage("text.see.descripcion", null, locale)) + ": " + invoice.getDescription());
        table2.addCell(String.format(messageSource.getMessage("text.see.fecha", null, locale)) + ": " + invoice.getCreateAt());

        PdfPTable table3 = new PdfPTable(4);
        table3.setWidths(new float[] {3.5f, 1, 1, 1});

        table3.addCell(String.format(messageSource.getMessage("text.invoice.form.producto", null, locale)));
        table3.addCell(String.format(messageSource.getMessage("text.invoice.form.precio", null, locale)));
        table3.addCell(String.format(messageSource.getMessage("text.invoice.form.cantidad", null, locale)));
        table3.addCell(String.format(messageSource.getMessage("text.invoice.form.total", null, locale)));

        for (ItemInvoice item: invoice.getItemInvoices() ) {
            table3.addCell(item.getProduct().getName());
            table3.addCell(item.getProduct().getPrice().toString());

            cell = new PdfPCell(new Phrase(item.getQuantity().toString()));
            cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

            table3.addCell(cell);
            table3.addCell(item.calculatePrice().toString());
        }

        cell = new PdfPCell(new Phrase(messages.getMessage("text.invoice.form.total") + ": "));
        cell.setColspan(3);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);

        table3.addCell(cell);
        table3.addCell(invoice.getTotal().toString());

        document.add(table);
        document.add(table2);
        document.add(table3);
    }
}
