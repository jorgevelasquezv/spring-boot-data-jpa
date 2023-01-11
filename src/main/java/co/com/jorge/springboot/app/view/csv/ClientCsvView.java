package co.com.jorge.springboot.app.view.csv;

import co.com.jorge.springboot.app.models.entities.Client;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import java.util.Map;

@Component("list.csv")
public class ClientCsvView extends AbstractView {

    public ClientCsvView() {
        setContentType("text/csv");
    }

    @Override
    protected boolean generatesDownloadContent() {
        return true;
    }

    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {

        response.setHeader("Content-Disposition", "attachment; filename=\"clients.csv\"");
        response.setContentType(getContentType());

        Page<Client> clients = (Page<Client>) model.get("clientes");

        ICsvBeanWriter beanWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);

        String[] header = {"id", "name", "lastname", "email", "createAt"};
        beanWriter.writeHeader(header);

        for (Client client: clients) {
            beanWriter.write(client, header);
        }

        beanWriter.close();
    }
}
