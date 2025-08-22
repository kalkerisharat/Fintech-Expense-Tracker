package com.sharat.fintech_tracker.util;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.sharat.fintech_tracker.model.Expense;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Service
public class PdfGeneratorUtil {

    public void generateExpenseReport(HttpServletResponse response, List<Expense> expenses) throws IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=expenses-report.pdf");

        PdfWriter writer = new PdfWriter(response.getOutputStream());
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        Paragraph title = new Paragraph("Expense Report");
        title.setTextAlignment(TextAlignment.CENTER);
        document.add(title);

        Table table = new Table(new float[]{1, 2, 2, 2, 3});

        table.addCell(new Cell().add(new Paragraph("ID")));
        table.addCell(new Cell().add(new Paragraph("Category")));
        table.addCell(new Cell().add(new Paragraph("Amount")));
        table.addCell(new Cell().add(new Paragraph("Date")));
        table.addCell(new Cell().add(new Paragraph("Description")));

        for (Expense expense : expenses) {
            table.addCell(new Cell().add(new Paragraph(String.valueOf(expense.getId()))));
            table.addCell(new Cell().add(new Paragraph(expense.getCategory())));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(expense.getAmount()))));
            table.addCell(new Cell().add(new Paragraph(expense.getDate().toString())));
            table.addCell(new Cell().add(new Paragraph(expense.getDescription())));
        }

        document.add(table);
        document.close();
    }
}