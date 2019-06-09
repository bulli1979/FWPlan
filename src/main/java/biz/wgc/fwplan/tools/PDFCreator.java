package biz.wgc.fwplan.tools;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.attach.impl.DefaultTagWorkerFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfString;
import com.itextpdf.kernel.pdf.PdfViewerPreferences;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.WriterProperties;
import com.itextpdf.layout.font.FontProvider;

import biz.wgc.fwplan.constants.ValueHolder;
import biz.wgc.fwplan.data.Plan;
import biz.wgc.fwplan.data.UserElement;
import biz.wgc.fwplan.data.db.DBUserElement;
import biz.wgc.fwplan.helper.ImagePaint;

public class PDFCreator {
	private PDFCreator() {
	}

	public static void writePDF() {
		try {

			PDDocument document = new PDDocument();
			document.addPage(createMapPage(document));
			createTextPage();
			document.save(ValueHolder.INSTANCE.getOutputPath() + "plan-"
					+ ValueHolder.INSTANCE.getPlan().getPlanNumber() + "_Site2.pdf");
			document.close();

		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
	}

	private static PDPage createMapPage(PDDocument document) throws IOException {
		PDPage page = new PDPage(new PDRectangle(PDRectangle.A3.getHeight(), PDRectangle.A3.getWidth()));
		List<UserElement> userElements = DBUserElement.getInstance()
				.getAllElementsForPlan(ValueHolder.INSTANCE.getPlan().getId());
		File printFile = ImagePaint.createImageFile(userElements);
		PDImageXObject pdImage = PDImageXObject.createFromFile(printFile.getAbsolutePath(), document);
		PDPageContentStream contentStream = new PDPageContentStream(document, page);
		contentStream.drawImage(pdImage, 0, 0);
		contentStream.beginText();
		contentStream.newLineAtOffset(25, PDRectangle.A3.getWidth() - 25);
		contentStream.setFont(PDType1Font.TIMES_ROMAN, 20);
		contentStream.showText("Einsatzplan " + ValueHolder.INSTANCE.getPlan().getPlanNumber());
		contentStream.endText();
		contentStream.close();
		return page;
	}

	private static void createTextPage() throws IOException {
		String path = ValueHolder.INSTANCE.getOutputPath() + "plan-" + ValueHolder.INSTANCE.getPlan().getPlanNumber()
				+ "_Site1.pdf";
		FileOutputStream outputStream = new FileOutputStream(path);

		WriterProperties writerProperties = new WriterProperties();
		writerProperties.addXmpMetadata();

		PdfWriter pdfWriter = new PdfWriter(outputStream, writerProperties);

		PdfDocument pdfDoc = new PdfDocument(pdfWriter);
		pdfDoc.getCatalog().setLang(new PdfString("de-CH"));
		pdfDoc.setTagged();
		pdfDoc.getCatalog().setViewerPreferences(new PdfViewerPreferences().setDisplayDocTitle(true));
		ConverterProperties props = new ConverterProperties();
		FontProvider fp = new FontProvider();
		fp.addStandardPdfFonts();
		props.setFontProvider(fp);
		DefaultTagWorkerFactory tagWorkerFactory = new DefaultTagWorkerFactory();
		props.setTagWorkerFactory(tagWorkerFactory);
		try (FileInputStream fileInputStream = new FileInputStream(
				new File(ValueHolder.INSTANCE.getHTMLPath() + "plan.html"))) {
			
			HtmlConverter.convertToPdf(manipulateHTML(fileInputStream), pdfDoc, props);
		}
		pdfDoc.close();

	}

	private static InputStream manipulateHTML(FileInputStream fileInputStream) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream, StandardCharsets.UTF_8));
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = reader.readLine()) != null) {
			sb.append(line);
			sb.append('\n');
		}
		String html = sb.toString();
		Plan plan = ValueHolder.INSTANCE.getPlan();

		html = html.replaceAll("@@title@@", addBr(plan.getTitle()));
		html = html.replaceAll("@@plannumber@@", addBr(plan.getPlanNumber()));
		html = html.replaceAll("@@description@@", addBr(plan.getDescription()));
		html = html.replaceAll("@@adress@@", addBr(plan.getAdress()));
		html = html.replaceAll("@@instantaction@@", addBr(plan.getInstantAction()));
		html = html.replaceAll("@@wathertransport@@", createWhaterTransportTable());
		html = html.replaceAll("@@importantcontacts@@", addBr(plan.getImportantContacts()));
		return new ByteArrayInputStream(html.getBytes());
	}
	
	private static String createWhaterTransportTable() {
		int countWatherTransport =  Tool.INSTANCE.countWhaterTransports();
		StringBuilder wt = new StringBuilder("<table><tbody><tr>");
		Plan plan = ValueHolder.INSTANCE.getPlan();
		for(int i = 0; i < countWatherTransport;i++) {
			wt.append("<td>"+ (i==0? addBr(plan.getWatherTransport1()):i==1?addBr(plan.getWatherTransport2()):i==2?addBr(plan.getWatherTransport3()):addBr(plan.getWatherTransport4()))+"</td>");
		}
		wt.append("</tr></tbody></table>");
		return wt.toString();
	}
	
	private static String addBr(String toAdd) {
		return toAdd.replaceAll("\\n", "<br>");
	}
}
