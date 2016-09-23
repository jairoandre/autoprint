package br.com.vah.autoprint.report;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.lucene.util.IOUtils;

import javax.imageio.ImageIO;
import javax.print.*;
import javax.print.attribute.HashPrintRequestAttributeSet;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Jairoportela on 21/09/2016.
 */
public class ReportLoader {

  public static void fillReport(String reportName, List list, PrintService printService) throws Exception {
    try {
      System.out.println("Imprimindo " + list.size() + " OS");
      Map<String, Object> params = new HashMap<>();
      BufferedImage logo = ImageIO.read(ClassLoader.class.getResourceAsStream("/logo.png"));
      params.put("LOGO", logo);
      JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(list);
      JasperPrint jasperPrint = JasperFillManager.fillReport(ClassLoader.class.getResourceAsStream(reportName), params, ds);
      ByteArrayInputStream in = new ByteArrayInputStream(JasperExportManager.exportReportToPdf(jasperPrint));
      Doc pdfDoc = new SimpleDoc(in, DocFlavor.INPUT_STREAM.AUTOSENSE, null);
      DocPrintJob printJob = printService.createPrintJob();
      printJob.print(pdfDoc, new HashPrintRequestAttributeSet());
      System.out.println("Impressão realizada com sucesso!");
      in.close();
    } catch (Exception e) {
      System.out.println("Erro inesperado na impressão!");
      e.printStackTrace();
      throw e;
    }
  }
}
