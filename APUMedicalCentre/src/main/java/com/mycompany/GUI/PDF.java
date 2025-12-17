/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.GUI;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.border.DashedBorder;
import com.itextpdf.layout.border.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.mycompany.staffmodule.Appointment;
import com.mycompany.staffmodule.Customer;
import com.mycompany.staffmodule.Doctor;
import com.mycompany.staffmodule.Payment;
import com.mycompany.staffmodule.Staff;
import java.io.FileNotFoundException;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 *
 * @author Huawei
 */
public class PDF {
    public static void main(String[] args) throws FileNotFoundException {
        Staff s1 = new Staff("staff1@gmail.com", "staff111!", "Staff1", "Male", "60123456789", 20, "S001", 5000, "Admin", "Staff");
        Doctor d1 = new Doctor("doctor1@gmail.com", "doctor111!", "Doctor1", "Male", "60123495860", 30, "D001", 10000, "General Doctor", "Doctor");
        Customer c1 = new Customer("customer1@gmail.com", "customer111!", "Customer1", "Male", "60123593859", 25, "C001");
        Appointment a1 = new Appointment("A001", Date.valueOf("2025-09-01"), Time.valueOf("14:00:00"), "good feedback", 100, "paid", "finished", "good comment", 5, c1, d1, s1);
        Payment p1 = new Payment("P001", 100, "QR", "Appointment Fees", s1, a1);
        generateReceipt(p1);
    }
    public static void generateReceipt(Payment p1) throws FileNotFoundException{
        String path = "Receipt " + p1.getPayId() + ".pdf"; 
        PdfWriter pdfWriter =  new PdfWriter(path);
        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
        pdfDocument.setDefaultPageSize(PageSize.A4);
        Document document = new Document(pdfDocument);
        
        /*set width*/
        //first part
        float twocol = 285f;
        float twocol150 = twocol + 150f;
        float twoColumnWidth[] = {twocol150, twocol};
        float twoColumnEqual[] = {twocol, twocol};
        float threecol = 190f;
        float fullwidth[] = {threecol *3};
        Paragraph newLine = new Paragraph("\n");
        Border gb = new SolidBorder(Color.GRAY, 2f);
        
        /*set content*/
        //first part
        Table firstPart = new Table(twoColumnWidth);
        firstPart.addCell(new Cell().add("Receipt").setFontSize(30f).setBorder(Border.NO_BORDER).setBold());
        Table nestedTable = new Table(new float[]{twocol/2, twocol/2});
        nestedTable.addCell(getHeaderTextCell("Payment ID"));
        nestedTable.addCell(getHeaderTextCellValue(": " + p1.getPayId()));
        nestedTable.addCell(getHeaderTextCell("Payment Date"));
        nestedTable.addCell(getHeaderTextCellValue(": " + getTodayDateAsString()));
        firstPart.addCell(new Cell().add(nestedTable).setBorder(Border.NO_BORDER));
        
        //big divider
        Table bigDivider = new Table(fullwidth);
        bigDivider.setBorder(gb);
        
        //Appointment Details
        Paragraph apptDetails = new Paragraph("Appointment Details");
        Table apptTable = new Table(twoColumnEqual);
        apptTable.addCell(new Cell().add("Appointment ID"));
        apptTable.addCell(new Cell().add(p1.getBasedOn().getApptId()));
        apptTable.addCell(new Cell().add("Date"));
        apptTable.addCell(new Cell().add(changeDateFormat(String.valueOf(p1.getBasedOn().getDate()))));
        apptTable.addCell(new Cell().add("Time"));
        apptTable.addCell(new Cell().add(timeBackFormat(String.valueOf(p1.getBasedOn().getTime()))));
        apptTable.addCell(new Cell().add("Doctor"));
        apptTable.addCell(new Cell().add(p1.getBasedOn().getDoctor().getName() + "(" + p1.getBasedOn().getDoctor().getPosition() + ")"));
        apptTable.addCell(new Cell().add("Customer"));
        apptTable.addCell(new Cell().add(p1.getBasedOn().getCustomer().getId() + "(" + p1.getBasedOn().getCustomer().getName() + ")"));
        apptTable.addCell(new Cell().add("Assigned By"));
        if(p1.getBasedOn().getAssignedBy() == null){
            apptTable.addCell(new Cell().add("-"));
        }
        else{
            apptTable.addCell(new Cell().add(p1.getBasedOn().getAssignedBy().getId() + "(" + p1.getBasedOn().getAssignedBy().getName() + ")"));
        }
        //divider ---
        Table smallDivider = new Table(fullwidth);
        Border dgb = new DashedBorder(Color.GRAY, 0.5f);        
        
        //Payment Details
        Paragraph payDetails = new Paragraph("Payment Details");
        Table payTable = new Table(twoColumnEqual);
        payTable.addCell(new Cell().add("Payment Type"));
        payTable.addCell(new Cell().add(p1.getType()));
        payTable.addCell(new Cell().add("Payment Method"));
        payTable.addCell(new Cell().add(p1.getPayMethod()));
        payTable.addCell(new Cell().add("Amount"));
        payTable.addCell(new Cell().add(String.valueOf(p1.getAmount())));
        payTable.addCell(new Cell().add("Collected By"));
        payTable.addCell(new Cell().add(p1.getCollectedBy().getId() + "(" + p1.getCollectedBy().getName() + ")"));
        
        //term and condition
        Table termAndCondition = new Table(fullwidth);
        termAndCondition.addCell(new Cell().add("TERMS AND CONDITIONS\n").setBold().setBorder(Border.NO_BORDER));
        ArrayList<String> tncList = new ArrayList<>();
        tncList.add("1.	All payments for services rendered are non-refundable. Refunds for products or medications \n    are subject to our return policy.");
        tncList.add("2.	Your personal data is used solely for appointment management, billing, and providing \n    medical services. We do not share your information with third parties without your explicit consent, unless required by law.");
        tncList.add("3.	For any billing or receipt-related inquiries, please contact our administrative staff during \n    business hours.");
        tncList.add("4.	This receipt is for financial documentation only and does not serve as medical advice. For \n    any questions regarding your treatment or health, please consult your doctor.");
        
        for (String tnc : tncList){
            termAndCondition.addCell(new Cell().add(tnc).setBorder(Border.NO_BORDER));
        }
        
        
        /*add to document*/
        //first part
        document.add(firstPart);
        document.add(newLine);
        document.add(bigDivider);
        document.add(newLine);
        document.add(apptDetails.setBold());
        document.add(apptTable);
        document.add(newLine);
        document.add(smallDivider.setBorder(dgb));
        document.add(newLine);
        document.add(payDetails.setBold());
        document.add(payTable);
        document.add(newLine);
        document.add(smallDivider.setBorder(dgb));
        document.add(newLine);
        document.add(termAndCondition);
        
        document.close();
    }
    
    static Cell getHeaderTextCell(String textValue){
        return new Cell().add(textValue).setBold().setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.LEFT);
    }
    
    static Cell getHeaderTextCellValue(String textValue){
        return new Cell().add(textValue).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.LEFT);
    }
    
    public static String getTodayDateAsString() {
        // Get the current date
        LocalDate today = LocalDate.now();

        // Define the desired date format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // Format the date and return the string
        return today.format(formatter);
    }
    
    //change 09:00:00 to 09:00 - 10:00
    public static String timeBackFormat(String textFileFormat){
        // Define the format for parsing the input string
        DateTimeFormatter parseFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        try {
            // Parse the input string into a LocalTime object
            LocalTime startTime = LocalTime.parse(textFileFormat, parseFormatter);

            // Add one hour to the start time to get the end time
            LocalTime endTime = startTime.plusHours(1);

            // Define the format for displaying the time
            DateTimeFormatter displayFormatter = DateTimeFormatter.ofPattern("HH:mm");

            // Format both the start and end times and combine them into a single string
            String displayFormat = startTime.format(displayFormatter) + " - " + endTime.format(displayFormatter);

            return displayFormat;
        } catch (Exception e) {
            // Handle potential parsing errors if the format is incorrect
            System.err.println("Error parsing time format: " + textFileFormat);
            return "";
        }
    }
    
     //change 05-09-2025 to 2025/05/09 
    public static String changeDateFormat(String wrongFormat){
        // Split the date string at the hyphens.
        // For "05-09-2025", this creates an array ["05", "09", "2025"].
        String[] parts = wrongFormat.split("-");

        // Check if the split was successful and there are 3 parts.
        if (parts.length == 3) {
            // Reassemble the parts in the YYYY-MM-DD format.
            // parts[2] is "2025", parts[1] is "09", parts[0] is "05".
            String correctFormat = parts[2] + "/" + parts[1] + "/" + parts[0];
            return correctFormat;
        }
        // Return null or an empty string if the format is invalid.
        return null;
    }
            
            
}
