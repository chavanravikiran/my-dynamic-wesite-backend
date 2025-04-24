package com.webapp.websiteportal.util;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.*;
import java.util.Objects;

public class ExcelRowCounter {

    public static void main(String[] args) {
        File folder = new File("F:\\Accounts Data Load"); // 🔁 Change path
        File[] files = folder.listFiles();

        if (files == null || files.length == 0) {
            System.out.println("Folder not found or is empty.");
            return;
        }

        int totalFilesProcessed = 0;
        int totalNonEmptyFirstColCells = 0;

        for (File file : files) {
            if (file.isFile()) {
                String fileName = file.getName().toLowerCase();
                int count = 0;

                try {
                    if (fileName.endsWith(".csv")) {
                        count = countCsvFirstColumn(file);
                    } else if (fileName.endsWith(".xlsx") || fileName.endsWith(".xls")) {
                        count = countExcelFirstColumn(file);
                    } else {
                        System.out.println(file.getName() + " -> Skipped (unsupported file type)");
                        continue;
                    }

                    System.out.println(file.getName() + " -> Non-empty first column cells: " + count);
                    totalNonEmptyFirstColCells += count;
                    totalFilesProcessed++;

                } catch (Exception e) {
                    System.out.println(file.getName() + " -> Error: " + e.getMessage());
                }
            }
        }

        System.out.println("====================================");
        System.out.println("Total files processed: " + totalFilesProcessed);
        System.out.println("Total non-empty cells in first column: " + totalNonEmptyFirstColCells);
    }

    private static int countCsvFirstColumn(File file) throws IOException {
        int count = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            boolean isFirstLine = true;
            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false; // Skip header
                    continue;
                }
                String[] columns = line.split(",", -1);
                if (columns.length > 0 && !columns[0].trim().isEmpty()) {
                    count++;
                }
            }
        }
        return count;
    }

    private static int countExcelFirstColumn(File file) throws IOException {
        int count = 0;
        try (FileInputStream fis = new FileInputStream(file)) {
            Workbook workbook;
            if (file.getName().toLowerCase().endsWith(".xlsx")) {
                workbook = new XSSFWorkbook(fis);
            } else {
                workbook = new HSSFWorkbook(fis);
            }

            Sheet sheet = workbook.getSheetAt(0); // read first sheet
            boolean isFirstRow = true;
            for (Row row : sheet) {
                if (isFirstRow) {
                    isFirstRow = false; // Skip header
                    continue;
                }
                Cell cell = row.getCell(0); // First column
                if (cell != null && cell.getCellType() != CellType.BLANK &&
                        !Objects.toString(cell, "").trim().isEmpty()) {
                    count++;
                }
            }
            workbook.close();
        }
        return count;
    }
}
