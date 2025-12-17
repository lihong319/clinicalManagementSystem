package com.mycompany.staffmodule;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/** Logic class for Doctor profile + appointments (no Swing). */
public class Doctor extends User<Doctor> {

    // ====== Files ======
    private static final String ROLE_NAME = "Doctor";
    private static final String USER_FILE = "User.txt";
    private static final Path   APPOINTMENT_FILE = Paths.get("Appointment.txt");

    // ====== Formats ======
    private static final DateTimeFormatter DATE_FMT   = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter TIME_FMT   = DateTimeFormatter.ofPattern("HH:mm[:ss]");
    private static final DateTimeFormatter TIME_OUT   = DateTimeFormatter.ofPattern("HH:mm:ss"); // when writing
    private static final DateTimeFormatter FINISH_FMT = DateTimeFormatter.ofPattern("HH:mm");

    // ====== Doctor fields ======
    private double salary;
    private String position;
    private static String roles;

    // ====== Ctors ======
    public Doctor() {}
    public Doctor(String appointmenttxt) {} // kept for compatibility

    public Doctor(String email, String password, String name, String gender,
                  String phoneNum, int age, String id,
                  double salary, String position, String roles) {
        super(email, password, name, gender, phoneNum, age, id);
        this.salary = salary;
        this.position = position;
        Doctor.roles = roles;
    }

    // ====== Getters/Setters ======
    public double getSalary() { return salary; }
    public void setSalary(double salary) { this.salary = salary; }

    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }

    public static String getRoles() { return roles; }
    public static void setRoles(String roles) { Doctor.roles = roles; }

    

    // ======================================================================
    //                        PROFILE  (User.txt)
    // ======================================================================

    /** Convert this Doctor object into a User.txt line */
    private String toUserLine() {
        return String.join("|",
                this.email,
                this.password,
                this.name,
                this.gender,
                this.phoneNum,
                String.valueOf(this.age),
                this.id,
                String.valueOf(this.salary),
                this.position,
                ROLE_NAME
        );
    }

    /** Read all lines of User.txt */
    private static List<String> readAllUserLines() {
        try {
            Path p = Paths.get(USER_FILE);
            if (!Files.exists(p)) return new ArrayList<>();
            return Files.readAllLines(p, StandardCharsets.UTF_8);
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    private static String[] splitUser(String line) { return line.split("\\|", -1); }

//    /** Find a Doctor by ID from User.txt */
    public static Doctor findById(String docId) {
        if (docId == null || docId.isEmpty()) return null;
        try (BufferedReader r = new BufferedReader(new FileReader(USER_FILE))) {
            String line;
            while ((line = r.readLine()) != null) {
                String[] p = splitUser(line);
                if (p.length == 10 && ROLE_NAME.equalsIgnoreCase(p[9]) &&
                        p[6].equalsIgnoreCase(docId)) {
                    try {
                        return new Doctor(
                                p[0], p[1], p[2], p[3], p[4],
                                Integer.parseInt(p[5]), p[6],
                                Double.parseDouble(p[7]), p[8], p[9]
                        );
                    } catch (Exception ignored) { /* skip malformed */ }
                }
            }
        } catch (IOException e) { e.printStackTrace(); }
        return null;
    }
    // Backward-compat shim for older code that calls doctor.searchById(...).
public Doctor searchById(String docId) {
    return Doctor.findById(docId);
}


//    /** Update (or append) this doctor in User.txt */
    public boolean persist() {
        List<String> lines = readAllUserLines();
        boolean replaced = false;

        for (int i = 0; i < lines.size(); i++) {
            String[] p = splitUser(lines.get(i));
            if (p.length >= 10 && ROLE_NAME.equalsIgnoreCase(p[9]) &&
                    p[6].equalsIgnoreCase(this.id)) {
                lines.set(i, this.toUserLine());
                replaced = true;
                break;
            }
        }
        if (!replaced) lines.add(this.toUserLine());

        try (BufferedWriter w = new BufferedWriter(new FileWriter(USER_FILE, false))) {
            for (int i = 0; i < lines.size(); i++) {
                w.write(lines.get(i));
                if (i < lines.size() - 1) w.newLine();
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /** Update this doctor's profile fields and persist to User.txt */
    public boolean updateProfile(String email, String password, String name, String gender,
                                 String phoneNum, int age, double salary, String position) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.gender = gender;
        this.phoneNum = phoneNum;
        this.age = age;
        this.salary = salary;
        this.position = position;
        if (Doctor.roles == null || Doctor.roles.isEmpty()) Doctor.roles = ROLE_NAME;
        return persist();
    }

    // Simple validators used by UI
    public static List<String> validateDoctorProfile(String name, String phoneNum, String ageText, String salaryText) {
        List<String> errors = new ArrayList<>();

        if (name == null || name.isBlank()) {
            errors.add("Name cannot be empty.");
        } else if (!name.matches("^[A-Za-z ]+$")) {
            errors.add("Name must contain letters only.");
        }
        if (phoneNum == null || !phoneNum.matches("^\\d{8,15}$")) {
            errors.add("Phone must contain 8–15 digits only.");
        }
        try {
            int age = Integer.parseInt(ageText);
            if (age < 18 || age > 100) errors.add("Age must be between 18 and 100.");
        } catch (NumberFormatException e) {
            errors.add("Age must be an integer.");
        }
        try { Double.parseDouble(salaryText); }
        catch (NumberFormatException e) { errors.add("Salary must be numeric."); }
        return errors;
    }

public ArrayList<Doctor> readFile() {
    ArrayList<Doctor> doctors = new ArrayList<>();
    try (BufferedReader br = new BufferedReader(new FileReader("User.txt"))) {
        String line;
        while ((line = br.readLine()) != null) {
            String[] p = line.split("\\|", -1);
            if (p.length >= 10 && "Doctor".equalsIgnoreCase(p[9])) {
                try {
                    doctors.add(new Doctor(
                        p[0], p[1], p[2], p[3], p[4],
                        Integer.parseInt(p[5]), p[6],
                        Double.parseDouble(p[7]), p[8], p[9]
                    ));
                } catch (NumberFormatException ignore) { /* skip bad row */ }
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
    return doctors;
}
    public void appendFile(Doctor doctor) throws IOException {
        try {
            BufferedWriter UserWriter = new BufferedWriter(new FileWriter("User.txt", true));
            UserWriter.append(doctor.email + "|" + doctor.password + "|" + doctor.name + "|" + doctor.gender + "|" + doctor.phoneNum + "|" + doctor.age + "|" + doctor.id + "|" + doctor.salary + "|" + doctor.position + "|" + "Doctor" +"\n");
            UserWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    
    @Override
    public boolean ageValidation(int age){
        //age between 18 and 65
        return age >= 18 && age <= 65;

    }


    // ======================================================================findById
    //                    APPOINTMENTS  (Appointment.txt)
    // ======================================================================

    /** Appointment record model (12 columns expected in file) */
    public static class Appointment {
        public String appId, feedback, extraCharge, paymentStatus, status, comment,
                      rating, custId, docId, staffId;
        public LocalDate date;
        public LocalTime time, finishedTime;
    }

    /** Read all Appointment.txt lines (empty list if file missing) */
    private static List<String> readAllApptLines() throws IOException {
        if (!Files.exists(APPOINTMENT_FILE)) return Collections.emptyList();
        return Files.readAllLines(APPOINTMENT_FILE, StandardCharsets.UTF_8);
    }

    /** Parse a line into Appointment (returns null if malformed) */
    private static Appointment parseApptSafe(String line) {
        try {
            String[] parts = line.split("\\|", -1);
            if (parts.length < 12) return null;

            Appointment a = new Appointment();
            a.appId        = parts[0].trim();
            a.date         = LocalDate.parse(parts[1].trim(), DATE_FMT);
            a.time         = LocalTime.parse(parts[2].trim(), TIME_FMT);
            a.feedback     = parts[3].trim();
            a.extraCharge  = parts[4].trim();
            a.paymentStatus= parts[5].trim();
            a.status       = parts[6].trim();
            a.comment      = parts[7].trim();
            a.rating       = parts[8].trim();
            a.custId       = parts[9].trim();
            a.docId        = parts[10].trim();
            a.staffId      = parts[11].trim();
            a.finishedTime = extractFinishedTime(a.comment);
            return a;
        } catch (Exception e) {
            return null;
        }
    }

    /** Serialize appointment back to file line */
    private static String toApptLine(Appointment a) {
        return String.join("|",
                a.appId,
                a.date.format(DATE_FMT),
                a.time.format(TIME_OUT),
                safe(a.feedback),
                safe(a.extraCharge),
                safe(a.paymentStatus),
                safe(a.status),
                safe(a.comment),
                safe(a.rating),
                safe(a.custId),
                safe(a.docId),
                safe(a.staffId)
        );
    }

    private static String safe(String s) { return s == null ? "" : s; }

    /** Extract FIN=HH:mm token from comment, if present */
    private static LocalTime extractFinishedTime(String comment) {
        if (comment == null || comment.isEmpty()) return null;
        String[] tokens = comment.split("[;\\s]+");
        for (String t : tokens) {
            if (t.startsWith("FIN=")) {
                String hhmm = t.substring(4).trim();
                try { return LocalTime.parse(hhmm, FINISH_FMT); } catch (Exception ignored) {}
            }
        }
        return null;
    }

    /** Replace or add FIN=HH:mm token in comment */
    private static String upsertFinishedTimeTag(String comment, LocalTime finished) {
        String finTag = "FIN=" + finished.format(FINISH_FMT);
        if (comment == null || comment.isEmpty()) return finTag;
        String[] parts = comment.split(";");
        List<String> cleaned = new ArrayList<>();
        for (String p : parts) {
            String s = p.trim();
            if (s.isEmpty()) continue;
            if (!s.startsWith("FIN=")) cleaned.add(s);
        }
        cleaned.add(finTag);
        return String.join("; ", cleaned);
    }

    /** Convert Appointment to table row (for 12-column appointment table) */
    private static Object[] toTableRow(Appointment a) {
        return new Object[]{
                a.appId, a.date.format(DATE_FMT), a.time.toString(), a.feedback,
                a.extraCharge, a.paymentStatus, a.status, a.comment, a.rating,
                a.custId, a.docId, a.staffId
        };
    }

    /** Convert Appointment to 7-column report row */
    private static Object[] toReportRow(Appointment a) {
        return new Object[]{
                a.appId, a.custId, a.docId,
                a.date.format(DATE_FMT),
                a.status, a.extraCharge, a.feedback
        };
    }

    /** Load all appointments for a specific date */
    private static List<Appointment> loadAppointmentsForDate(LocalDate date) throws IOException {
        List<Appointment> out = new ArrayList<>();
        for (String line : readAllApptLines()) {
            Appointment a = parseApptSafe(line);
            if (a != null && date.equals(a.date)) out.add(a);
        }
        return out;
    }

    /** Load today's appointments (system zone) */
    public List<Appointment> loadTodayAppointments() throws IOException {
        return loadAppointmentsForDate(LocalDate.now());
    }

    /** Load today's appointments for a given doctor ID */
    public List<Appointment> loadTodayAppointmentsForDoctor(String doctorId) throws IOException {
        return loadTodayAppointments().stream()
                .filter(a -> a.docId.equalsIgnoreCase(doctorId))
                .collect(Collectors.toList());
    }

    /** Load today's UNPAID appointments for a doctor as table rows (for JTable) */
    public List<Object[]> loadTodayUnpaidAsTableRowsForDoctor(String doctorId) throws IOException {
        return loadTodayAppointmentsForDoctor(doctorId).stream()
                .filter(a -> "unpaid".equalsIgnoreCase(a.paymentStatus.trim()))
                .map(Doctor::toTableRow)
                .collect(Collectors.toList());
    }

    /**
     * Update selected appointment fields and save back to Appointment.txt.
     * Any of finishedTime / feedback / status / extraCharge may be null to skip.
     */
    public boolean updateAppointmentFields(String appId,
                                           LocalTime finishedTime,
                                           String feedback,
                                           String status,
                                           String extraCharge) throws IOException {
        List<String> lines = readAllApptLines();
        boolean updated = false;

        for (int i = 0; i < lines.size(); i++) {
            Appointment a = parseApptSafe(lines.get(i));
            if (a == null) continue;
            if (a.appId.equalsIgnoreCase(appId)) {
                if (feedback != null)    a.feedback = feedback;
                if (status != null)      a.status = status;
                if (extraCharge != null) a.extraCharge = extraCharge;
//                if (finishedTime != null) {
//                    a.comment = upsertFinishedTimeTag(a.comment, finishedTime);
//                    a.finishedTime = finishedTime;
//                }
                lines.set(i, toApptLine(a));
                updated = true;
                break;
            }
        }

        if (updated) {
            Files.write(APPOINTMENT_FILE, lines, StandardCharsets.UTF_8,
                    StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE);
        }
        return updated;
    }

    /** Load ALL appointments as 7-column report rows (used by Reports tab) */
    public List<Object[]> loadAllAsReportRows() throws IOException {
        List<Object[]> rows = new ArrayList<>();
        for (String line : readAllApptLines()) {
            Appointment a = parseApptSafe(line);
            if (a != null) rows.add(toReportRow(a));
        }
        return rows;
    }
}
