package tools;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

public class PDFCreator {
	private PDFCreator(){}
	public static void writePDF(String planNumber){
		try{
			
			PDDocument document = new PDDocument();
			PDPage page = new PDPage(new PDRectangle(PDRectangle.A3.getHeight(), PDRectangle.A3.getWidth()));
			PDImageXObject pdImage = PDImageXObject.createFromFile("image_"+planNumber+".png", document);
			PDPageContentStream contentStream = new PDPageContentStream(document, page);
			contentStream.drawImage(pdImage, 0, 0);
			contentStream.beginText(); 
			contentStream.newLineAtOffset(25, PDRectangle.A3.getWidth()-25);
			contentStream.setFont(PDType1Font.TIMES_ROMAN, 20);
			contentStream.showText("Einsatzplan " + planNumber);
			contentStream.endText();
			contentStream.close();
			document.addPage(page);
			document.save("test.pdf");
			document.close();
			
		}catch(Exception e){
			System.out.println(e);
		}
	}
}
