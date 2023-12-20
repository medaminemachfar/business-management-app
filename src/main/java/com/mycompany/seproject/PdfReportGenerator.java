/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.seproject;

/**
 *
 * @author Mega-PC
 * 
 */
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;

public class PdfReportGenerator {

    public static void generatePdfReport(String reportFilePath, String outputPath) {
        String reportText = readReportText(reportFilePath);

        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(outputPath));
            document.open();
            document.add(new Paragraph(reportText));
            System.out.println("PDF report generated successfully.");
        } catch (Exception e) {
            e.getMessage();
        } finally {
            if (document != null) {
                document.close();
            }
        }
    }

    private static String readReportText(String filePath) {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (Exception e) {
            e.getMessage();
        }
        return content.toString();
    }
   
   
}
