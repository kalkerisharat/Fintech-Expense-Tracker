package com.sharat.fintech_tracker.controller;

import com.sharat.fintech_tracker.service.CsvExportService;
import com.sharat.fintech_tracker.service.PdfExportService;
import com.sharat.fintech_tracker.service.EmailService;
import com.sharat.fintech_tracker.service.ExpenseService;
import com.sharat.fintech_tracker.model.Expense;
import com.sharat.fintech_tracker.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/report")
public class ReportController {

        private final CsvExportService csvExportService;
        private final PdfExportService pdfExportService;
        private final EmailService emailService;
        private final ExpenseService expenseService;

        @Autowired
        public ReportController(
                        CsvExportService csvExportService,
                        PdfExportService pdfExportService,
                        EmailService emailService,
                        ExpenseService expenseService) {
                this.csvExportService = csvExportService;
                this.pdfExportService = pdfExportService;
                this.emailService = emailService;
                this.expenseService = expenseService;
        }

        // Endpoint to export expenses as CSV
        @GetMapping("/export-expenses")
        public void exportExpenses(HttpServletResponse response, @AuthenticationPrincipal User user)
                        throws IOException {
                List<Expense> expenses = expenseService.getAllExpenses(user);
                csvExportService.exportExpensesToCsv(response, expenses);
        }

        // Endpoint to export expenses as PDF
        @GetMapping("/export-expenses-pdf")
        public void exportExpensesToPdf(HttpServletResponse response, @AuthenticationPrincipal User user)
                        throws Exception {
                List<Expense> expenses = expenseService.getAllExpenses(user);
                pdfExportService.generateExpenseReport(response, expenses);
        }

        // Endpoint to send the expense report via email
        @GetMapping("/send-expense-report")
        public String sendExpenseReportEmail(@AuthenticationPrincipal User user) throws Exception {
                List<Expense> expenses = expenseService.getAllExpenses(user);
                ByteArrayResource pdfResource = generatePdfReportAsResource(expenses);
                emailService.sendExpenseReportEmail(user.getEmail(), expenses, pdfResource);
                return "Report sent successfully!";
        }

        // Implement the missing method to generate PDF as ByteArrayResource
        private ByteArrayResource generatePdfReportAsResource(List<Expense> expenses) throws Exception {
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

                // If using iText 7:
                com.itextpdf.kernel.pdf.PdfWriter writer = new com.itextpdf.kernel.pdf.PdfWriter(outputStream);
                com.itextpdf.kernel.pdf.PdfDocument pdf = new com.itextpdf.kernel.pdf.PdfDocument(writer);
                com.itextpdf.layout.Document document = new com.itextpdf.layout.Document(pdf);

                // Add title
                com.itextpdf.layout.element.Paragraph title = new com.itextpdf.layout.element.Paragraph(
                                "Expense Report");
                title.setTextAlignment(com.itextpdf.layout.properties.TextAlignment.CENTER);
                document.add(title);

                // Create table
                com.itextpdf.layout.element.Table table = new com.itextpdf.layout.element.Table(
                                com.itextpdf.layout.properties.UnitValue
                                                .createPercentArray(new float[] { 1, 2, 2, 2, 3 }))
                                .useAllAvailableWidth();

                // Add headers
                table.addHeaderCell(
                                new com.itextpdf.layout.element.Cell()
                                                .add(new com.itextpdf.layout.element.Paragraph("ID")));
                table.addHeaderCell(
                                new com.itextpdf.layout.element.Cell()
                                                .add(new com.itextpdf.layout.element.Paragraph("Category")));
                table.addHeaderCell(
                                new com.itextpdf.layout.element.Cell()
                                                .add(new com.itextpdf.layout.element.Paragraph("Amount")));
                table.addHeaderCell(
                                new com.itextpdf.layout.element.Cell()
                                                .add(new com.itextpdf.layout.element.Paragraph("Date")));
                table.addHeaderCell(
                                new com.itextpdf.layout.element.Cell()
                                                .add(new com.itextpdf.layout.element.Paragraph("Description")));

                // Add data rows
                for (Expense expense : expenses) {
                        table.addCell(new com.itextpdf.layout.element.Cell()
                                        .add(new com.itextpdf.layout.element.Paragraph(
                                                        String.valueOf(expense.getId()))));
                        table.addCell(new com.itextpdf.layout.element.Cell()
                                        .add(new com.itextpdf.layout.element.Paragraph(
                                                        expense.getCategory().toString())));
                        table.addCell(new com.itextpdf.layout.element.Cell()
                                        .add(new com.itextpdf.layout.element.Paragraph(
                                                        String.valueOf(expense.getAmount()))));
                        table.addCell(new com.itextpdf.layout.element.Cell()
                                        .add(new com.itextpdf.layout.element.Paragraph(expense.getDate().toString())));
                        table.addCell(new com.itextpdf.layout.element.Cell()
                                        .add(new com.itextpdf.layout.element.Paragraph(expense.getDescription())));
                }

                document.add(table);
                document.close();

                return new ByteArrayResource(outputStream.toByteArray());
        }
}