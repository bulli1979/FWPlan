package tools;

import java.io.File;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import application.ValueHolder;
import data.UserElement;
import data.db.DBUserElement;
import helper.ImagePaint;

public class PDFCreator {
	private PDFCreator(){}
	public static void writePDF(){
		try{
			
			PDDocument document = new PDDocument();
			PDPage page = new PDPage(new PDRectangle(PDRectangle.A3.getHeight(), PDRectangle.A3.getWidth()));
			List<UserElement> userElements = DBUserElement.getInstance().getAllElementsForPlan(ValueHolder.INSTANCE.getPlan().getId());
			File printFile = ImagePaint.createImageFile(userElements);
			
			PDImageXObject pdImage = PDImageXObject.createFromFile(printFile.getAbsolutePath(), document);
			PDPageContentStream contentStream = new PDPageContentStream(document, page);
			contentStream.drawImage(pdImage, 0, 0);
			contentStream.beginText(); 
			contentStream.newLineAtOffset(25, PDRectangle.A3.getWidth()-25);
			contentStream.setFont(PDType1Font.TIMES_ROMAN, 20);
			contentStream.showText("Einsatzplan " + ValueHolder.INSTANCE.getPlan().getPlanNumber());
			contentStream.endText();
			contentStream.close();
			document.addPage(page);
			document.save(ValueHolder.INSTANCE.getOutputPath()+"plan-"+ValueHolder.INSTANCE.getPlan().getPlanNumber()+".pdf");
			document.close();
			
		}catch(Exception e){
			System.out.println(e);
			e.printStackTrace();
		}
	}
}
