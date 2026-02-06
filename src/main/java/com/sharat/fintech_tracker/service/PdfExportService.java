package com.sharat.fintech_tracker.service;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.sharat.fintech_tracker.model.Expense;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Service
public class PdfExportService {

    public void generateExpenseReport(HttpServletResponse response, List<Expense> expenses) throws IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=expenses-report.pdf");

        // Create PDF document
        PdfWriter writer = new PdfWriter(response.getOutputStream());
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        // Add title
        Paragraph title = new Paragraph("Expense Report");
        title.setTextAlignment(TextAlignment.CENTER);
        document.add(title);

        // Create table
        Table table = new Table(UnitValue.createPercentArray(new float[] { 1, 2, 2, 2, 3 }))
                .useAllAvailableWidth();

        // Add headers
        table.addHeaderCell(new Cell().add(new Paragraph("ID")));
        table.addHeaderCell(new Cell().add(new Paragraph("Category")));
        table.addHeaderCell(new Cell().add(new Paragraph("Amount")));
        table.addHeaderCell(new Cell().add(new Paragraph("Date")));
        table.addHeaderCell(new Cell().add(new Paragraph("Description")));

        // Add data rows
        for (Expense expense : expenses) {
            table.addCell(new Cell().add(new Paragraph(String.valueOf(expense.getId()))));
            table.addCell(new Cell().add(new Paragraph(expense.getCategory().toString())));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(expense.getAmount()))));
            table.addCell(new Cell().add(new Paragraph(expense.getDate().toString())));
            table.addCell(new Cell().add(new Paragraph(expense.getDescription())));
        }

        document.add(table);
        document.close();
    }
}