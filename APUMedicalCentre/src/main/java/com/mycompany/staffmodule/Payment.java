package com.mycompany.staffmodule;

import com.mycompany.GUI.PDF;
import java.io.*;
import java.sql.Time;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
/*
Extra import goes here
 */


public class Payment {
    private String payId;
    private double amount;
    private String payMethod;
    private String type;
    private Staff collectedBy;
    private Appointment basedOn;
    // Declare the payment appointment table once (class-level)


    public Payment(String payId, double amount, String payMethod, String type, Staff collectedBy, Appointment basedOn) {
        this.payId = payId;
        this.amount = amount;
        this.payMethod = payMethod;
        this.type = type;
        this.collectedBy = collectedBy;
        this.basedOn = basedOn;
    }

    public Payment(){}

    public String getPayId() {
        return payId;
    }

    public void setPayId(String payId) {
        this.payId = payId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Staff getCollectedBy() {
        return collectedBy;
    }

    public void setCollectedBy(Staff collectedBy) {
        this.collectedBy = collectedBy;
    }

    public Appointment getBasedOn() {
        return basedOn;
    }

    public void setBasedOn(Appointment basedOn) {
        this.basedOn = basedOn;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "payId='" + payId + '\'' +
                ", amount=" + amount +
                ", payMethod='" + payMethod + '\'' +
                ", type='" + type + '\'' +
                ", collectedBy=" + collectedBy +
                ", basedOn=" + basedOn +
                '}';
    }

    public ArrayList<Payment> readFile(){


        ArrayList<Payment> payments = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader("Payment.txt"));
            String line;
            while((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 6){
                    String payId = parts[0];
                    double amount = Double.parseDouble(parts[1]) ;
                    String payMethod = parts[2];
                    String type = parts[3];
                    String staffId = parts[4];
                    String apptId = parts[5];

                    Staff staff = new Staff();
                    staff = staff.searchById(staffId);

                    Appointment appointment = new Appointment();
                    appointment = appointment.searchById(apptId);

                    Payment payment = new Payment(payId, amount, payMethod, type, staff, appointment);
                    payments.add(payment);
                }
            }

            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return payments;
    }

    public void appendFile(Payment payment) throws IOException {
        try {
            BufferedWriter UserWriter = new BufferedWriter(new FileWriter("Payment.txt", true));
            if(payment.collectedBy == null){
                UserWriter.append(payment.payId + "|" + payment.amount + "|" + payment.payMethod + "|" + payment.type + "|" + null + "|" + payment.basedOn.getApptId() +"\n");

            }
            else {
                UserWriter.append(payment.payId + "|" + payment.amount + "|" + payment.payMethod + "|" + payment.type + "|" + payment.collectedBy.getId() + "|" + payment.basedOn.getApptId() +"\n");

            }
            UserWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void writeFile(ArrayList<Payment> payments){

        try {
            BufferedWriter UserWriter = new BufferedWriter(new FileWriter("Payment.txt"));

            Iterator paymentLoop = payments.iterator();


            while(paymentLoop.hasNext()) {
                Payment payment = (Payment) paymentLoop.next();
                if(payment.collectedBy == null){
                    UserWriter.write(payment.getPayId() + "|" + payment.getAmount() + "|" + payment.getPayMethod() + "|" + payment.type + "|" + null + "|" + payment.basedOn.getApptId() + "\n");
                }
                else{
                    UserWriter.write(payment.getPayId() + "|" + payment.getAmount() + "|" + payment.getPayMethod() + "|" + payment.type + "|" + payment.collectedBy.getId() + "|" + payment.basedOn.getApptId() + "\n");
                }
            }


            UserWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    
    //add
    public String generateNextId(){
        ArrayList<Payment> payments = new ArrayList<>();
        payments = this.readFile();
        
        if (payments.isEmpty()) {
            return "P001";
        }
        
        // Find the last customer in the list to get the last ID
        Payment lastPayment = payments.get(payments.size() - 1);
        String lastId = lastPayment.getPayId();

        // Extract the numeric part of the ID (e.g., "C007" -> "007")
        String numericPart = lastId.substring(1);

        // Convert the numeric part to an integer and increment it
        int nextNumber = Integer.parseInt(numericPart) + 1;

        // Format the new number back to a 3-digit string (e.g., 8 -> "008")
        String nextId = String.format("P%03d", nextNumber);

        return nextId;
        
    }
    //appointment fees
    public String makePayment(String method, String staffId, String apptId)throws IOException{
        Payment addPay = new Payment();
        Staff findStaff = new Staff();
        Appointment findAppt = new Appointment();
        
        addPay.setPayId(generateNextId());
        addPay.setAmount(20.00);
        addPay.setPayMethod(method);
        addPay.setType("Appointment Fees");
        addPay.setCollectedBy(findStaff.searchById(staffId));
        addPay.setBasedOn(findAppt.searchById(apptId));
        
        this.appendFile(addPay);

        return addPay.getPayId();
    }
    //consultation fees
    public String makePayment(String method, String staffId, String apptId, double conFees) throws IOException{
        System.out.println("method: " + method + " staff id: " + staffId + " appt id: " + apptId + " conFees: " + conFees);
        Payment addPay = new Payment();
        Staff findStaff = new Staff();
        Appointment findAppt = new Appointment();
        
        addPay.setPayId(generateNextId());
        addPay.setAmount(conFees);
        addPay.setPayMethod(method);
        addPay.setType("Consultation Fees");
        addPay.setCollectedBy(findStaff.searchById(staffId));
        addPay.setBasedOn(findAppt.searchById(apptId));
        
        this.appendFile(addPay);
        findAppt.changePaymentStatus(apptId);
        
        return addPay.getPayId();
        
    }
    
    public void generateReceipt(String payId) throws FileNotFoundException{
        ArrayList<Payment> payments = new ArrayList<>();
        payments = this.readFile();
        
        for(Payment eachPay : payments){
            if(eachPay.getPayId().equals(payId)){
                
                PDF.generateReceipt(eachPay);
                break;
            }
        }
    }
    
}
