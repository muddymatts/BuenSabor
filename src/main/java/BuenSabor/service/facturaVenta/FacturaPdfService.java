package BuenSabor.service.facturaVenta;

import BuenSabor.model.FacturaVenta;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;

@Service
public class FacturaPdfService {

    private final TemplateEngine templateEngine;

    public FacturaPdfService(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public byte[] generarPdf(FacturaVenta factura) throws Exception {
        // 1. Renderizar HTML con Thymeleaf
        Context context = new Context();

        context.setVariable("factura", factura);

        String htmlContent = templateEngine.process("factura2", context);

        // 2. Convertir HTML a PDF con Flying Saucer
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(htmlContent);
        renderer.layout();
        renderer.createPDF(baos);

        return baos.toByteArray();
    }
}
