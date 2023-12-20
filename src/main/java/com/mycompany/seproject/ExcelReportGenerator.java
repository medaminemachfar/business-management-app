/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.seproject;

/**
 *
 * @author Mega-PC
 */
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.StringTokenizer;

public class ExcelReportGenerator {

    public static void generateExcelReport(String inputFilePath, String outputPath) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Report");

            // Read data from the input file
            try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath))) {
                String line;
                int rowNum = 0;

                while ((line = reader.readLine()) != null) {
                    StringTokenizer tokenizer = new StringTokenizer(line, ",");
                    Row row = sheet.createRow(rowNum++);
                    int cellNum = 0;

                    while (tokenizer.hasMoreTokens()) {
                        Cell cell = row.createCell(cellNum++);
                        String token = tokenizer.nextToken().trim();

                        
                        if (cellNum == 3) {
                            try {
                                LocalDate date = LocalDate.parse(token);
                                cell.setCellValue(date.toString());
                            } catch (Exception e) {
                              
                                e.printStackTrace();
                            }
                        } else {
                            cell.setCellValue(token);
                        }
                    }
                }
            }

            // Write the workbook to the output file
            try (FileOutputStream fileOut = new FileOutputStream(outputPath)) {
                workbook.write(fileOut);
                System.out.println("Excel report generated successfully.");
            }

        } catch (IOException e) {
            e.getMessage();
        }
    }

    public static void main(String[] args) {
        String inputFilePath = "path/to/your/inputfile.txt"; // Provide the actual path to your input text file
        String outputPath = "report.xlsx";
        generateExcelReport(inputFilePath, outputPath);
    }
}

