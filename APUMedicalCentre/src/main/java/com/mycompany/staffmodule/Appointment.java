package com.mycompany.staffmodule;

import javax.print.Doc;
import java.io.*;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class Appointment {
    // File paths
    private static final String APPOINTMENT_FILE = 
        "Appointment.txt";
    private static final String USER_FILE = 
        "User.txt";

    // Instance fields - merged from both versions
    private String appointmentId; // from first version
    private String apptId; // from second version
    private String date; // String version from first
    private Date dateObj; // Date version from second
    private String time; // String version from first
    private Time timeObj; // Time version from second
    private String feedback;
    private String charge; // from first version
    private double consultationFees; // from second version
    private String duration;
    private String paymentStatus;
    private String status;
    private String comment;
    private String rating; // String version from first
    private int ratingInt; // int version from second
    private String customerId; // from first version
    private String doctorId; // from first version
    private String staffId; // from first version
    private Customer customer; // from second version
    private Doctor doctor; // from second version
    private Staff assignedBy; // from second version

    // Constructors
    public Appointment() {}

    // Constructor from first version
    public Appointment(String appointmentId, String date, String time, String feedback, 
                      String charge, String duration, String status, String comment, 
                      String rating, String customerId, String doctorId, String staffId) {
        this.appointmentId = appointmentId;
        this.apptId = appointmentId; // sync both ID fields
        this.date = date;
        this.time = time;
        this.feedback = feedback;
        this.charge = charge;
        this.duration = duration;
        this.status = status;
        this.comment = comment;
        this.rating = rating;
        this.customerId = customerId;
        this.doctorId = doctorId;
        this.staffId = staffId;
    }

    // Constructor from second version
    public Appointment(String apptId, Date date, Time time, String feedback, double consultationFees, 
                      String paymentStatus, String status, String comment, int rating, 
                      Customer customer, Doctor doctor, Staff assignedBy) {
        this.apptId = apptId;
        this.appointmentId = apptId; // sync both ID fields
        this.dateObj = date;
        this.date = date != null ? date.toString() : null;
        this.timeObj = time;
        this.time = time != null ? time.toString() : null;
        this.feedback = feedback;
        this.consultationFees = consultationFees;
        this.paymentStatus = paymentStatus;
        this.status = status;
        this.comment = comment;
        this.ratingInt = rating;
        this.rating = String.valueOf(rating);
        this.customer = customer;
        this.doctor = doctor;
        this.assignedBy = assignedBy;
    }

    // Inner classes from first version
    public static class DoctorInfo {
        private String doctorId;
        private String name;
        private String specialization;

        public DoctorInfo(String doctorId, String name, String specialization) {
            this.doctorId = doctorId;
            this.name = name;
            this.specialization = specialization;
        }

        public String getDoctorId() { return doctorId; }
        public String getName() { return name; }
        public String getSpecialization() { return specialization; }

        @Override
        public String toString() {
            return name + " (" + specialization + ")";
        }
    }

    public static class TimeSlotInfo {
        private String timeSlot;
        private boolean available;

        public TimeSlotInfo(String timeSlot, boolean available) {
            this.timeSlot = timeSlot;
            this.available = available;
        }

        public String getTimeSlot() { return timeSlot; }
        public boolean isAvailable() { return available; }
    }

    public static class AppointmentDetails {
        private String appointmentId;
        private String doctorName;
        private String date;
        private String time;
        private String status;
        private String staffName;
        private String feedback;
        private String charge;
        private String rating;
        private String comment;

        public AppointmentDetails(String appointmentId, String doctorName, String date, 
                                String time, String status, String staffName) {
            this.appointmentId = appointmentId;
            this.doctorName = doctorName;
            this.date = date;
            this.time = time;
            this.status = status;
            this.staffName = staffName;
        }

        // Getters
        public String getAppointmentId() { return appointmentId; }
        public String getDoctorName() { return doctorName; }
        public String getDate() { return date; }
        public String getTime() { return time; }
        public String getStatus() { return status; }
        public String getStaffName() { return staffName; }
        public String getFeedback() { return feedback; }
        public String getCharge() { return charge; }
        public String getRating() { return rating; }
        public String getComment() { return comment; }

        // Setters for feedback-related fields
        public void setFeedback(String feedback) { this.feedback = feedback; }
        public void setCharge(String charge) { this.charge = charge; }
        public void setRating(String rating) { this.rating = rating; }
        public void setComment(String comment) { this.comment = comment; }
    }

    // File operations - merged from both versions
    public ArrayList<Appointment> readFile() {
        ArrayList<Appointment> appointments = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader("Appointment.txt"));
            String line;
            while((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 12 ){
                    String apptId = parts[0];
                    Date date = Date.valueOf(parts[1]);
                    Time time = Time.valueOf(parts[2]);
                    String feedback = parts[3];
                    double consultationFees = Double.parseDouble(parts[4]);
                    String paymentStatus = parts[5];
                    String status = parts[6];
                    String comment = parts[7];
                    int rating = Integer.parseInt(parts[8]);
                    String custId = parts[9];
                    String docId = parts[10];
                    String staffId = parts[11];

                    Customer customer = new Customer();
                    customer = customer.searchById(custId);

                    Doctor doctor = new Doctor();
                    doctor = doctor.searchById(docId);

                    Staff staff = new Staff();
                    staff = staff.searchById(staffId);

                    Appointment appointment = new Appointment(apptId, date, time, feedback, consultationFees, paymentStatus, status, comment, rating, customer, doctor, staff);
                    appointments.add(appointment);
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return appointments;
    }

    public void writeFile(ArrayList<Appointment> appointments) {
        try {
            BufferedWriter UserWriter = new BufferedWriter(new FileWriter("Appointment.txt"));
            Iterator appointmentLoop = appointments.iterator();

            while(appointmentLoop.hasNext()) {
                Appointment appointment = (Appointment) appointmentLoop.next();
                if(appointment.assignedBy == null){
                    UserWriter.write(appointment.getApptId() + "|" + appointment.getDateObj() + "|" + appointment.getTimeObj() + "|" + appointment.getFeedback() + "|" + appointment.getConsultationFees() + "|" + appointment.getPaymentStatus() + "|" + appointment.getStatus() + "|" + appointment.getComment() + "|" + appointment.getRatingInt() + "|" + appointment.customer.getId() + "|" + appointment.doctor.getId() + "|" + null + "\n");
                }
                else{
                    UserWriter.write(appointment.getApptId() + "|" + appointment.getDateObj() + "|" + appointment.getTimeObj() + "|" + appointment.getFeedback() + "|" + appointment.getConsultationFees() + "|" + appointment.getPaymentStatus() + "|" + appointment.getStatus() + "|" + appointment.getComment() + "|" + appointment.getRatingInt() + "|" + appointment.customer.getId() + "|" + appointment.doctor.getId() + "|" + appointment.assignedBy.getId() + "\n");
                }
            }
            UserWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void appendFile(Appointment appointment) throws IOException {
        try {
            BufferedWriter UserWriter = new BufferedWriter(new FileWriter("Appointment.txt", true));
            if(appointment.assignedBy == null){
                UserWriter.append(appointment.apptId + "|" + appointment.dateObj + "|" + appointment.timeObj + "|" + appointment.feedback + "|" + appointment.consultationFees + "|" + appointment.paymentStatus + "|" + appointment.status + "|" + appointment.comment + "|" + appointment.ratingInt + "|" + appointment.customer.getId() + "|" + appointment.doctor.getId() + "|" + null +"\n");
            }
            else{
                UserWriter.append(appointment.apptId + "|" + appointment.dateObj + "|" + appointment.timeObj + "|" + appointment.feedback + "|" + appointment.consultationFees + "|" + appointment.paymentStatus + "|" + appointment.status + "|" + appointment.comment + "|" + appointment.ratingInt + "|" + appointment.customer.getId() + "|" + appointment.doctor.getId() + "|" + appointment.assignedBy.getId() +"\n");
            }
            UserWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Doctor management methods from first version
    public static List<DoctorInfo> loadDoctorsFromUserFile() {
        List<DoctorInfo> doctors = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(USER_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 10 && "Doctor".equalsIgnoreCase(parts[9].trim())) {
                    String doctorId = parts[6].trim();
                    String name = parts[2].trim();
                    String specialization = parts[8].trim();
                    doctors.add(new DoctorInfo(doctorId, name, specialization));
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading doctors: " + e.getMessage());
        }
        return doctors;
    }

    public static String findDoctorIdBySpec(String doctorSpec) {
        try (BufferedReader reader = new BufferedReader(new FileReader(USER_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 10) {
                    String name = parts[2].trim();
                    String specialization = parts[8].trim();
                    String role = parts[9].trim();
                    String docId = parts[6].trim();

                    if ("Doctor".equalsIgnoreCase(role)) {
                        String formatted = name + " (" + specialization + ")";
                        if (formatted.equalsIgnoreCase(doctorSpec)) {
                            return docId;
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error finding doctor ID: " + e.getMessage());
        }
        return "UNKNOWN";
    }

    public static String getDoctorNameById(String doctorId) {
        if (doctorId == null || doctorId.equals("null") || doctorId.equals("-") || doctorId.isEmpty()) {
            return "Not Assigned";
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(USER_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 10 && parts[6].trim().equals(doctorId) && "Doctor".equalsIgnoreCase(parts[9].trim())) {
                    String name = parts[2].trim();
                    String specialization = parts[8].trim();
                    return name + " (" + specialization + ")";
                }
            }
        } catch (IOException e) {
            System.err.println("Error getting doctor name: " + e.getMessage());
        }
        return "Unknown Doctor";
    }

    public static String getStaffNameById(String staffId) {
        if (staffId == null || staffId.equals("null") || staffId.equals("-") || staffId.isEmpty()) {
            return "Not Assigned";
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(USER_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 10 && parts[6].trim().equals(staffId) && "Staff".equalsIgnoreCase(parts[9].trim())) {
                    return parts[2].trim();
                }
            }
        } catch (IOException e) {
            System.err.println("Error getting staff name: " + e.getMessage());
        }
        return "Unknown Staff";
    }

    // Time slot management from first version
    public static List<TimeSlotInfo> getTimeSlotAvailability(String doctorId, LocalDate date) {
        List<TimeSlotInfo> timeSlots = new ArrayList<>();
        Set<String> bookedSlots = getBookedTimeSlots(doctorId, date);

        // Generate time slots from 9 AM to 4 PM, excluding lunch (12:00)
        for (int h = 9; h <= 16; h++) {
            if (h == 12) continue; // Skip lunch hour
            String timeSlot = String.format("%02d:00:00", h);
            boolean available = !bookedSlots.contains(timeSlot);
            timeSlots.add(new TimeSlotInfo(timeSlot, available));
        }

        return timeSlots;
    }

    private static Set<String> getBookedTimeSlots(String doctorId, LocalDate date) {
        Set<String> bookedSlots = new HashSet<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(APPOINTMENT_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.replace("[", "|").replace("]", "|");
                String[] parts = line.split("\\|");
                if (parts.length >= 12) {
                    String fileDoctorId = parts[10].trim();
                    String fileDate = parts[1].trim();
                    String fileTime = parts[2].trim();
                    String status = parts[6].trim();

                    if (doctorId.equals(fileDoctorId) && 
                        date.toString().equals(fileDate) && 
                        !"cancel".equalsIgnoreCase(status)) {
                        bookedSlots.add(fileTime);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading booked slots: " + e.getMessage());
        }
        return bookedSlots;
    }

    public static boolean isTimeSlotAvailable(String doctorId, LocalDate date, String timeSlot) {
        Set<String> bookedSlots = getBookedTimeSlots(doctorId, date);
        return !bookedSlots.contains(timeSlot);
    }

    // ID generation - merged from both versions
    public static String generateNextAppointmentId() {
        int maxId = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(APPOINTMENT_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.replace("[", "|").replace("]", "|");
                String[] parts = line.split("\\|");
                if (parts.length >= 1) {
                    String appointmentId = parts[0].trim();
                    if (appointmentId.startsWith("A")) {
                        try {
                            int id = Integer.parseInt(appointmentId.substring(1));
                            maxId = Math.max(maxId, id);
                        } catch (NumberFormatException e) {
                            // Skip invalid IDs
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error generating appointment ID: " + e.getMessage());
        }
        return "A" + String.format("%03d", maxId + 1);
    }

    public String generateNextId() {
        ArrayList<Appointment> appointments = new ArrayList<>();
        appointments = this.readFile();
        
        if (appointments.isEmpty()) {
            return "A001";
        }
        
        Appointment lastAppointment = appointments.get(appointments.size() - 1);
        String lastId = lastAppointment.getApptId();
        String numericPart = lastId.substring(1);
        int nextNumber = Integer.parseInt(numericPart) + 1;
        String nextId = String.format("A%03d", nextNumber);
        return nextId;
    }

    // Booking methods - from first version
    public static boolean bookNewAppointment(String doctorId, String customerId, LocalDate date, 
                                           String timeSlot, double consultationFees) {
        try {
            if (!isTimeSlotAvailable(doctorId, date, timeSlot)) {
                return false;
            }

            String appointmentId = generateNextAppointmentId();
            String appointmentLine = appointmentId + "|" +
                                   date.toString() + "|" +
                                   timeSlot + "|" +
                                   "-|" + // feedback
                                   "0.0" + "|" + // charge
                                   "unpaid|" + // fee
                                   "booked|" + // status
                                   "-|" + // comment
                                   "0|" + // rating
                                   customerId + "|" +
                                   doctorId + "|" +
                                   "null"; // staff ID

            try (PrintWriter writer = new PrintWriter(new FileWriter(APPOINTMENT_FILE, true))) {
                writer.println(appointmentLine);
                return true;
            }
        } catch (IOException e) {
            System.err.println("Error booking appointment: " + e.getMessage());
            return false;
        }
    }

    // Appointment creation from second version
    public String createAppointment(String StaffId, String custId, String date, String doctorId, String time, String paymentMethod) throws IOException{
        System.out.println("StaffID: " + StaffId + "DocId: " + doctorId + "CustId: " + custId + "Date: " + date  + "Time: " + time );
        Appointment addAppt = new Appointment();
        Staff findStaff = new Staff();
        Doctor findDoctor = new Doctor();
        Customer findCust = new Customer();
        
        addAppt.setApptId(generateNextId());
        addAppt.setDateObj(Date.valueOf(date));
        addAppt.setTimeObj(Time.valueOf(time));
        addAppt.setFeedback("-");
        addAppt.setConsultationFees(0.0);
        addAppt.setPaymentStatus("unpaid");
        addAppt.setStatus("booked");
        addAppt.setComment("-");
        addAppt.setRatingInt(0);
        addAppt.setCustomer(findCust.searchById(custId));
        addAppt.setDoctor(findDoctor.searchById(doctorId));
        addAppt.setAssignedBy(findStaff.searchById(StaffId));
        
        this.appendFile(addAppt);
        
        Payment addPay = new Payment();
        return addPay.makePayment(paymentMethod, StaffId, addAppt.getApptId());
    }

    // Search and filter methods from second version
    public Appointment searchById(String apptId) {
        ArrayList<Appointment> appointments = readFile();

        for (Appointment a : appointments) {
            if (a.getApptId().equalsIgnoreCase(apptId)) {
                return a;
            }
        }
        return null;
    }

    public ArrayList<Appointment> editApptFilter(String date, String custId) {
        ArrayList<Appointment> appointments = new ArrayList<>();
        appointments = this.readFile();
        
        if(custId.equalsIgnoreCase("all")){
            return appointments;
        }
        
        Iterator<Appointment> iterator = appointments.iterator();
        while (iterator.hasNext()) {
            Appointment appt = iterator.next();

            boolean dateMismatch = !date.isEmpty() && !String.valueOf(appt.getDateObj()).equalsIgnoreCase(date);
            boolean custIdMismatch = !custId.isEmpty() && !appt.getCustomer().getId().equalsIgnoreCase(custId);

            if (dateMismatch || custIdMismatch) {
                iterator.remove();
            }
        }
        
        return appointments;
    }

    public ArrayList<Appointment> payFilterAppt(String custId) {
        ArrayList<Appointment> appointments = new ArrayList<>();
        
        appointments = this.readFile();
        if(custId.equalsIgnoreCase("all")){
            return appointments;
        }
        
        Iterator<Appointment> iterator = appointments.iterator();
        while (iterator.hasNext()) {
            Appointment appt = iterator.next();
            
            boolean custIdMismatch = !custId.isEmpty() && !appt.getCustomer().getId().equalsIgnoreCase(custId);

            if (custIdMismatch || appt.getPaymentStatus().equalsIgnoreCase("paid") || !appt.getStatus().equalsIgnoreCase("completed")) {
                iterator.remove();
            }
        }
        System.out.println(appointments);
        
        return appointments;
    }

    // Appointment loading from first version
    public static List<AppointmentDetails> loadAppointmentsForCustomer(String customerId) {
        List<AppointmentDetails> appointments = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(APPOINTMENT_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.replace("[", "|").replace("]", "|");
                String[] parts = line.split("\\|");
                if (parts.length >= 12) {
                    String fileCustomerId = parts[9].trim();
                    
                    if (customerId.equals(fileCustomerId)) {
                        String apptId = parts[0].trim();
                        String dateStr = parts[1].trim();
                        String timeStr = parts[2].trim();
                        String status = parts[6].trim();
                        String doctorId = parts[10].trim();
                        String staffId = parts[11].trim();

                        String doctorName = getDoctorNameById(doctorId);
                        String staffName = getStaffNameById(staffId);
                        String formattedTime = formatTimeForDisplay(timeStr);
                        
                        AppointmentDetails details = new AppointmentDetails(
                            apptId, doctorName, dateStr, formattedTime, status, staffName
                        );
                        
                        details.setFeedback(parts[3].trim());
                        details.setCharge(formatCharge(parts[4].trim()));
                        details.setRating(parts[8].trim());
                        details.setComment(parts[7].trim());
                        
                        appointments.add(details);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading appointments: " + e.getMessage());
        }
        return appointments;
    }

    // Update methods - merged from both versions
    public static boolean updateAppointment(String apptId, String newDoctorSpec, String newDate, String newTime) {
        ArrayList<String> appointments = new ArrayList<>();
        boolean updated = false;
        
        try (BufferedReader reader = new BufferedReader(new FileReader(APPOINTMENT_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 12 && parts[0].trim().equals(apptId)) {
                    // Check if appointment is cancelled
                    String currentStatus = parts[6].trim();
                    if ("cancel".equalsIgnoreCase(currentStatus) || "cancelled".equalsIgnoreCase(currentStatus)) {
                        System.err.println("Cannot update cancelled appointment: " + apptId);
                        return false; // Cannot update cancelled appointments
                    }
                    
                    String doctorId = findDoctorIdBySpec(newDoctorSpec);
                    String updatedLine = apptId + "|" + 
                                       newDate + "|" + 
                                       newTime + ":00|" +
                                       parts[3] + "|" +
                                       parts[4] + "|" +
                                       parts[5] + "|" +
                                       parts[6] + "|" +
                                       parts[7] + "|" +
                                       parts[8] + "|" +
                                       parts[9] + "|" +
                                       doctorId + "|" +
                                       parts[11];
                    
                    appointments.add(updatedLine);
                    updated = true;
                } else {
                    appointments.add(line);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading appointments for update: " + e.getMessage());
            return false;
        }
        
        if (updated) {
            try (PrintWriter writer = new PrintWriter(new FileWriter(APPOINTMENT_FILE))) {
                for (String line : appointments) {
                    writer.println(line);
                }
                return true;
            } catch (IOException e) {
                System.err.println("Error saving updated appointments: " + e.getMessage());
                return false;
            }
        }
        return false;
    }

    public void editAppointment(String apptId, String date, String doctor, String time) {
        System.out.println("appt id: " + apptId + " date: " + date + " doctor: " + doctor + " time: " + time);
        
        ArrayList<Appointment> appointments = new ArrayList<>();
        appointments = this.readFile();
        
        Doctor findDoc = new Doctor();
        
        for(Appointment apptIndex : appointments){
           if(apptIndex.getApptId().equals(apptId)){
               // Check if appointment is cancelled
               String currentStatus = apptIndex.getStatus();
               if ("cancel".equalsIgnoreCase(currentStatus) || "cancelled".equalsIgnoreCase(currentStatus)) {
                   System.err.println("Cannot edit cancelled appointment: " + apptId);
                   return; // Cannot edit cancelled appointments
               }
               
               apptIndex.setDateObj(Date.valueOf(date));
               apptIndex.setDoctor(findDoc.searchById(doctor));
               apptIndex.setTimeObj(Time.valueOf(time));
               break;
           }
       }
        
        this.writeFile(appointments);
    }

    // Cancel methods - merged
    public static boolean cancelAppointment(String apptId) {
        ArrayList<String> appointments = new ArrayList<>();
        boolean cancelled = false;
        
        try (BufferedReader reader = new BufferedReader(new FileReader(APPOINTMENT_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 12 && parts[0].trim().equals(apptId)) {
                    String updatedLine = parts[0] + "|" + 
                                       parts[1] + "|" + 
                                       parts[2] + "|" +
                                       parts[3] + "|" +
                                       parts[4] + "|" +
                                       parts[5] + "|" +
                                       "cancel|" + // Update status
                                       parts[7] + "|" +
                                       parts[8] + "|" +
                                       parts[9] + "|" +
                                       parts[10] + "|" +
                                       parts[11];
                    
                    appointments.add(updatedLine);
                    cancelled = true;
                } else {
                    appointments.add(line);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading appointments for cancellation: " + e.getMessage());
            return false;
        }
        
        if (cancelled) {
            try (PrintWriter writer = new PrintWriter(new FileWriter(APPOINTMENT_FILE))) {
                for (String line : appointments) {
                    writer.println(line);
                }
                return true;
            } catch (IOException e) {
                System.err.println("Error saving cancelled appointments: " + e.getMessage());
                return false;
            }
        }
        return false;
    }

    public void cancelAppt(String apptId) {
        ArrayList<Appointment> appointments = new ArrayList<>();
        appointments = this.readFile();
        
        for(Appointment apptIndex : appointments){
           if(apptIndex.getApptId().equals(apptId)){
               apptIndex.setStatus("cancel");
               break;
           }
       }
        this.writeFile(appointments);
    }

    // Payment methods from second version
    public void changePaymentStatus(String apptId) throws IOException {
        ArrayList<Appointment> appointments = new ArrayList<>();
        appointments = this.readFile();
        
        for(Appointment apptIndex : appointments){
           if(apptIndex.getApptId().equals(apptId)){
               apptIndex.setPaymentStatus("paid");
               break;
           }
       }
        this.writeFile(appointments);
    }

    // Get appointment by ID from first version
    public static AppointmentDetails getAppointmentById(String apptId) {
        try (BufferedReader reader = new BufferedReader(new FileReader(APPOINTMENT_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.replace("[", "|").replace("]", "|");
                String[] parts = line.split("\\|");
                if (parts.length >= 12 && parts[0].trim().equals(apptId)) {
                    String doctorId = parts[10].trim();
                    String staffId = parts[11].trim();
                    
                    String doctorName = getDoctorNameById(doctorId);
                    String staffName = getStaffNameById(staffId);
                    String formattedTime = formatTimeForDisplay(parts[2].trim());
                    
                    AppointmentDetails details = new AppointmentDetails(
                        parts[0].trim(), doctorName, parts[1].trim(), 
                        formattedTime, parts[6].trim(), staffName
                    );
                    
                    details.setFeedback(parts[3].trim());
                    details.setCharge(formatCharge(parts[4].trim()));
                    details.setRating(parts[8].trim());
                    details.setComment(parts[7].trim());
                    
                    return details;
                }
            }
        } catch (IOException e) {
            System.err.println("Error getting appointment by ID: " + e.getMessage());
        }
        return null;
    }

    // Feedback update methods from first version
    public static boolean updateAppointmentFeedback(String apptId, String rating, String comment) {
        ArrayList<String> appointments = new ArrayList<>();
        boolean updated = false;
        
        try (BufferedReader reader = new BufferedReader(new FileReader(APPOINTMENT_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.replace("[", "|").replace("]", "|");
                String[] parts = line.split("\\|", -1);
                
                if (parts.length >= 12 && parts[0].trim().equals(apptId)) {
                    String currentRating = parts[8].trim();
                    String currentComment = parts[7].trim();
                    
                    if ((!currentRating.isEmpty() && !currentRating.equals("-") && 
                         !currentRating.equals("null") && !currentRating.equals("0")) ||
                        (!currentComment.isEmpty() && !currentComment.equals("-") && 
                         !currentComment.equals("null"))) {
                        return false; // Already has feedback
                    }
                    
                    String updatedLine = parts[0] + "|" + 
                                       parts[1] + "|" + 
                                       parts[2] + "|" + 
                                       parts[3] + "|" +
                                       parts[4] + "|" + 
                                       parts[5] + "|" + 
                                       parts[6] + "|" + 
                                       comment + "|" +
                                       rating + "|" +
                                       parts[9] + "|" + 
                                       parts[10] + "|" + 
                                       parts[11];
                    
                    appointments.add(updatedLine);
                    updated = true;
                } else {
                    appointments.add(line);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading appointments for feedback update: " + e.getMessage());
            return false;
        }
        
        if (updated) {
            try (PrintWriter writer = new PrintWriter(new FileWriter(APPOINTMENT_FILE))) {
                for (String line : appointments) {
                    writer.println(line);
                }
                return true;
            } catch (IOException e) {
                System.err.println("Error saving feedback: " + e.getMessage());
                return false;
            }
        }
        return false;
    }

    // Validation methods from second version
    public boolean canUpdateAppointment(String apptId) {
        ArrayList<Appointment> appointments = readFile();
        
        for (Appointment appointment : appointments) {
            if (appointment.getApptId().equals(apptId)) {
                String status = appointment.getStatus();
                // Check if status is cancelled (handle different case variations)
                if ("cancel".equalsIgnoreCase(status) || "cancelled".equalsIgnoreCase(status)) {
                    return false; // Cannot update cancelled appointments
                }
                return true;
            }
        }
        return false; // Appointment not found
    }

    public boolean deleteCustAllowed(String custId) {
        ArrayList<Appointment> appointments = new ArrayList<>();
        appointments = this.readFile();

        for(Appointment apptIndex : appointments){
           if(apptIndex.getCustomer().getId().equals(custId)){
               return false;
           }
       }
        return true;
    }

    public boolean appointmentValidation(String date, String doctorId, String time) {
        System.out.println("1 - date: " + date + "doc id: " + doctorId + "time: " + time);
        ArrayList<Appointment> appointments = new ArrayList<>();
        appointments = this.readFile();

        for(Appointment apptIndex : appointments){
           if(String.valueOf(apptIndex.getTimeObj()).equals(time) && 
              String.valueOf(apptIndex.getDateObj()).equals(date) && 
              apptIndex.getDoctor().getId().equals(doctorId)){

               if(!apptIndex.getStatus().equals("cancel")){
                    return false;
               }
           }
       }
        
        return true;
    }
    
    public boolean appointmentValidation(String date, String time) {
        System.out.println("2 - date: " + date + "time: " + time);
        
        String appointmentDateTimeString = date + " " + time;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        try {
            LocalDateTime appointmentDateTime = LocalDateTime.parse(appointmentDateTimeString, formatter);
            LocalDateTime now = LocalDateTime.now();
            return appointmentDateTime.isAfter(now);

        } catch (DateTimeParseException e) {
            System.err.println("Error parsing date or time: " + e.getMessage());
            return false;
        }
    }
    
    public boolean appointmentValidation(String apptId){
        ArrayList<Appointment> appointments = new ArrayList<>();
        appointments = this.readFile();

        for(Appointment apptIndex : appointments){
           if(apptIndex.getApptId().equalsIgnoreCase(apptId)){

               if(apptIndex.getStatus().equals("completed") || apptIndex.getStatus().equals("conducting")){
                    return false;
               }
               break;
           }
       }
        return true;
    }

    // File maintenance from first version
    public static void checkAndFixAppointmentFile() {
        ArrayList<String> correctedLines = new ArrayList<>();
        boolean needsFix = false;
        
        try (BufferedReader reader = new BufferedReader(new FileReader(APPOINTMENT_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("[") || line.contains("]") || !line.contains("|")) {
                    needsFix = true;
                    String correctedLine = line.replace("[", "|").replace("]", "|");
                    String[] parts = correctedLine.split("\\|", -1);
                    if (parts.length < 12) {
                        correctedLine = correctedLine + "|".repeat(12 - parts.length);
                    }
                    correctedLines.add(correctedLine);
                } else {
                    correctedLines.add(line);
                }
            }
        } catch (IOException e) {
            System.err.println("Error checking file format: " + e.getMessage());
            return;
        }
        
        if (needsFix) {
            try (PrintWriter writer = new PrintWriter(new FileWriter(APPOINTMENT_FILE))) {
                for (String line : correctedLines) {
                    writer.println(line);
                }
                System.out.println("Appointment file format corrected");
            } catch (IOException e) {
                System.err.println("Error fixing file format: " + e.getMessage());
            }
        }
    }

    // Utility methods from first version
    private static String formatTimeForDisplay(String timeStr) {
        if (timeStr == null || timeStr.trim().isEmpty() || timeStr.equals("null")) {
            return "Invalid Time";
        }
        
        try {
            if (timeStr.length() >= 8 && timeStr.contains(":")) {
                String[] timeParts = timeStr.split(":");
                if (timeParts.length >= 2) {
                    return timeParts[0] + ":" + timeParts[1];
                }
            }
            return timeStr.length() > 5 ? timeStr.substring(0, 5) : timeStr;
        } catch (Exception e) {
            return "Invalid Time";
        }
    }

    private static String formatCharge(String charge) {
        if (charge == null || charge.isEmpty() || charge.equals("-") || charge.equals("null")) {
            return "RM 0.00";
        }
        
        try {
            double amount = Double.parseDouble(charge);
            return String.format("RM %.2f", amount);
        } catch (NumberFormatException e) {
            return "RM " + charge;
        }
    }

    // ToString method from second version
    @Override
    public String toString() {
        return "Appointment{" +
                "apptId='" + apptId + '\'' +
                ", date=" + dateObj +
                ", time=" + timeObj +
                ", feedback='" + feedback + '\'' +
                ", consultationFees=" + consultationFees +
                ", paymentStatus='" + paymentStatus + '\'' +
                ", status='" + status + '\'' +
                ", comment='" + comment + '\'' +
                ", rating=" + ratingInt +
                ", customer=" + customer +
                ", doctor=" + doctor +
                ", assignedBy=" + assignedBy +
                '}';
    }

    // Getters and Setters - merged from both versions
    public String getAppointmentId() { return appointmentId; }
    public void setAppointmentId(String appointmentId) { 
        this.appointmentId = appointmentId;
        this.apptId = appointmentId; // sync both ID fields
    }
    
    public String getApptId() { return apptId; }
    public void setApptId(String apptId) { 
        this.apptId = apptId;
        this.appointmentId = apptId; // sync both ID fields
    }
    
    public String getDate() { return date; }
    public void setDate(String date) { 
        this.date = date;
        if (date != null) {
            try {
                this.dateObj = Date.valueOf(date);
            } catch (IllegalArgumentException e) {
                // Handle invalid date format
            }
        }
    }
    
    public Date getDateObj() { return dateObj; }
    public void setDateObj(Date dateObj) { 
        this.dateObj = dateObj;
        this.date = dateObj != null ? dateObj.toString() : null;
    }
    
    public String getTime() { return time; }
    public void setTime(String time) { 
        this.time = time;
        if (time != null) {
            try {
                this.timeObj = Time.valueOf(time);
            } catch (IllegalArgumentException e) {
                // Handle invalid time format
            }
        }
    }
    
    public Time getTimeObj() { return timeObj; }
    public void setTimeObj(Time timeObj) { 
        this.timeObj = timeObj;
        this.time = timeObj != null ? timeObj.toString() : null;
    }
    
    public String getFeedback() { return feedback; }
    public void setFeedback(String feedback) { this.feedback = feedback; }
    
    public String getCharge() { return charge; }
    public void setCharge(String charge) { 
        this.charge = charge;
        try {
            this.consultationFees = Double.parseDouble(charge);
        } catch (NumberFormatException e) {
            // Handle invalid number format
        }
    }
    
    public double getConsultationFees() { return consultationFees; }
    public void setConsultationFees(double consultationFees) { 
        this.consultationFees = consultationFees;
        this.charge = String.valueOf(consultationFees);
    }
    
    public String getDuration() { return duration; }
    public void setDuration(String duration) { this.duration = duration; }
    
    public String getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }
    
    public String getRating() { return rating; }
    public void setRating(String rating) { 
        this.rating = rating;
        try {
            this.ratingInt = Integer.parseInt(rating);
        } catch (NumberFormatException e) {
            this.ratingInt = 0;
        }
    }
    
    public int getRatingInt() { return ratingInt; }
    public void setRatingInt(int ratingInt) { 
        this.ratingInt = ratingInt;
        this.rating = String.valueOf(ratingInt);
    }
    
    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }
    
    public String getDoctorId() { return doctorId; }
    public void setDoctorId(String doctorId) { this.doctorId = doctorId; }
    
    public String getStaffId() { return staffId; }
    public void setStaffId(String staffId) { this.staffId = staffId; }
    
    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }
    
    public Doctor getDoctor() { return doctor; }
    public void setDoctor(Doctor doctor) { this.doctor = doctor; }
    
    public Staff getAssignedBy() { return assignedBy; }
    public void setAssignedBy(Staff assignedBy) { this.assignedBy = assignedBy; }
}