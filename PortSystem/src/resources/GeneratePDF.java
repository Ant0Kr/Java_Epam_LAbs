package resources;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import gui.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.containerGood;


public class GeneratePDF {

	private static Font TIME_ROMAN_BIG = new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.BOLD);
	private static Font TIME_ROMAN_SMALL = new Font(Font.FontFamily.TIMES_ROMAN, 13, Font.BOLD);
	private static Boolean checkFlag;
	private static LinkedList<containerGood> queneGood;

	public static void createPDF(String way,LinkedList<containerGood> goodQuene,Boolean flag) throws FileNotFoundException, DocumentException, ClassNotFoundException, SQLException, InterruptedException {
		
		queneGood = goodQuene;
		checkFlag = flag;
		File file = new File(way);
		if (file.exists())
			file.delete();
		Document document = new Document();
		PdfWriter.getInstance(document, new FileOutputStream(way));
		document.open();
		addMetaDataToPDF(document);
		addTitlePageToPDF(document);
		createTableOnPDF(document);
		document.close();
	}

	private static void addMetaDataToPDF(Document document) {
		document.addTitle("Table in PDF");
		document.addSubject("PDF doc");
		document.addAuthor("Antoha Karachun");
		document.addCreator("antoha12018");
	}

	private static void addTitlePageToPDF(Document document) throws DocumentException {

		Paragraph paragraph = new Paragraph();
		paragraph.add(new Paragraph(" "));
		if(checkFlag)
			paragraph.add(new Paragraph("Table "+Main.getCurrentShipSection(), TIME_ROMAN_BIG));
		else
			paragraph.add(new Paragraph("Table "+Main.getCurrentPierSection(), TIME_ROMAN_BIG));

		paragraph.add(new Paragraph(" "));
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
		paragraph.add(new Paragraph("PDF created date " + simpleDateFormat.format(new Date()), TIME_ROMAN_SMALL));
		document.add(paragraph);

	}

	private static void createTableOnPDF(Document document)
			throws DocumentException, ClassNotFoundException, SQLException {

		Paragraph paragraph = new Paragraph();
		paragraph.add(new Paragraph(" "));
		paragraph.add(new Paragraph(" "));
		document.add(paragraph);
		PdfPTable table;
		
	    table = new PdfPTable(2);
		
		PdfPCell c1 = new PdfPCell(new Phrase("Name of Good",TIME_ROMAN_SMALL));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);

		c1 = new PdfPCell(new Phrase("Count",TIME_ROMAN_SMALL));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);
		
		table.setHeaderRows(1);
		
		for (int i = 0;i<queneGood.size();i++){
			
			table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
			table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(queneGood.get(i).getGoodName());
			table.addCell(Integer.toString(queneGood.get(i).getGoodCount()));
		}
		
		
		document.add(table);
	}

}
