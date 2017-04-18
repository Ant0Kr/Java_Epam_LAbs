package resources;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import controllers.WindowController;
import implementations.DataDaoImplements;
import javafx.collections.ObservableList;
import models.Data;

public class GeneratePDF {

	private static Font TIME_ROMAN_BIG = new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.BOLD);
	private static Font TIME_ROMAN_SMALL = new Font(Font.FontFamily.TIMES_ROMAN, 13, Font.BOLD);

	public static void createPDF(String way) throws FileNotFoundException, DocumentException, ClassNotFoundException,
			SQLException, InterruptedException {

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
		paragraph.add(new Paragraph("Table " + WindowController.getCurrentSection(), TIME_ROMAN_BIG));

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
		table = new PdfPTable(4);

		PdfPCell c1 = new PdfPCell(new Phrase("File name", TIME_ROMAN_SMALL));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);

		c1 = new PdfPCell(new Phrase("Way", TIME_ROMAN_SMALL));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);

		c1 = new PdfPCell(new Phrase("Extension", TIME_ROMAN_SMALL));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);

		c1 = new PdfPCell(new Phrase("Size", TIME_ROMAN_SMALL));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);

		table.setHeaderRows(1);
		DataDaoImplements.getInstance().ConnectionDB();
		ObservableList<Data> item = DataDaoImplements.getInstance()
				.Read_Data(WindowController.getMyNum());
		DataDaoImplements.getInstance().CloseDB();
		int size = item.size();
		for (int i = 0; i < size; i++) {

			table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
			table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
			Data storage = item.get(i);
			table.addCell(storage.getFile_name());
			table.addCell(storage.getWay());
			table.addCell(storage.getExtension());
			table.addCell(storage.getSize());

		}

		document.add(table);
	}

}
