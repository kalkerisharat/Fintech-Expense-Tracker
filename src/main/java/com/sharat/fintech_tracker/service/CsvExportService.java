package com.sharat.fintech_tracker.service;
import com.sharat.fintech_tracker.model.Expense;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.List;

@Service
public class CsvExportService {

    public void exportExpensesToCsv(HttpServletResponse response, List<Expense> expenses) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=expenses.csv");

        try (CSVPrinter csvPrinter = new CSVPrinter(response.getWriter(), CSVFormat.DEFAULT.withHeader("ID", "Category", "Amount", "Date", "Description"))) {
            for (Expense expense : expenses) {
                csvPrinter.printRecord(expense.getId(), expense.getCategory(), expense.getAmount(), expense.getDate(), expense.getDescription());
            }
        }
    }
}
