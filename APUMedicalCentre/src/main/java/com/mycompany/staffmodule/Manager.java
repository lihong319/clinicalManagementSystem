package com.mycompany.staffmodule;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.File;


//import com.mycompany.staffmodule.Manager;


/**
 * Manager = Logic class (no Swing UI).
 * Handles User, Appointment, Feedback file operations.
 */
public class Manager extends User<Manager> {

    
    
    // =====================
// ==== ATTRIBUTES =====
// =====================

private double salary;
private String position;
private String role;

// =====================
// ==== CONSTRUCTOR ====
// =====================

    

    public Manager(String email, String password, String name, String gender, String phoneNum, int age, String id, double salary, String position, String role) {
        super(email, password, name, gender, phoneNum, age, id);
        this.salary = salary;
        this.position = position;
        this.role = role;
    }



// default constructor (for testing / empty object)
public Manager() {}

    
    private final String userFile = "User.txt";
    private final String apptFile = "Appointment.txt";
    private final String paymentFile = "Payment.txt";


    // =========================
    // ==== USER FUNCTIONS =====
    // =========================
    
    
    


public double getSalary() { return salary; }
public void setSalary(double salary) { this.salary = salary; }

public String getPosition() { return position; }
public void setPosition(String position) { this.position = position; }

public String getRole() { return role; }
public void setRole(String role) { this.role = role; }


    public void readUser() {
        readFile(userFile);
    }

public boolean updateProfile(String email, String password, String name,
                             String gender, String phone) {
    if (!emailValidation(email)) return false;
    if (!phoneNumValidation(phone)) return false;
    if (!passwordValidation(password)) return false;

    this.email = email;
    this.password = password;
    this.name = name;
    this.gender = gender;
    this.phoneNum = phone;
    return persist();
}

public static Manager findById(String id) {
    try (BufferedReader br = new BufferedReader(new FileReader("User.txt"))) {
        String line;
        while ((line = br.readLine()) != null) {
            String[] p = line.split("\\|", -1);
            if (p.length >= 10 && p[6].equalsIgnoreCase(id) && "Manager".equalsIgnoreCase(p[9])) {
                // email|password|name|gender|phone|age|id|salary|position|role
                String email    = p[0];
                String password = p[1];
                String name     = p[2];
                String gender   = p[3];
                String phone    = p[4];
                int age         = Integer.parseInt(p[5]);
                String userId   = p[6];
                double salary   = Double.parseDouble(p[7]);
                String position = p[8];
                String role     = p[9];

                return new Manager(email, password, name, gender, phone, age, userId, salary, position, role);
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
    return null;
}


  
public void appendUser(String idIgnored, String name, String email, String password,
                       String gender, String phone, int age, double salary,
                       String role, String position) {
    if (name == null || name.trim().isEmpty()) {
        JOptionPane.showMessageDialog(null, "Name is required."); return;
    }
    if (!emailValidation(email)) { JOptionPane.showMessageDialog(null, "Invalid email."); return; }
    if (!phoneNumValidation(phone)) { JOptionPane.showMessageDialog(null, "Invalid phone."); return; }
    if (!passwordValidation(password)) { JOptionPane.showMessageDialog(null, "Weak password."); return; }
    if (!ageValidation(age)) { JOptionPane.showMessageDialog(null, "Age must be 18–65."); return; }
    if (salary < 0) { JOptionPane.showMessageDialog(null, "Salary must be >= 0."); return; }
    if (emailExists(email)) { JOptionPane.showMessageDialog(null, "Email already exists."); return; }

    String newId = generateUniqueId(role);

    // email|password|name|gender|phone|age|id|salary|position|role
    String content = String.join("|",
            email, password, name, gender, phone,
            String.valueOf(age), newId, String.valueOf(salary), position, role
    );
    appendFile(userFile, content);
}



    public List<String[]> listInform() {
        return saveFileToArrayList(userFile);
    }

    // Replace your current method with this one:
public void updateUser(String id, String name, String email, String phone,
                       double salary, String position, String password) {
    if (id == null || id.trim().isEmpty()) { JOptionPane.showMessageDialog(null, "Missing ID."); return; }
    if (!emailValidation(email))         { JOptionPane.showMessageDialog(null, "Invalid email.");  return; }
    if (!phoneNumValidation(phone))      { JOptionPane.showMessageDialog(null, "Invalid phone.");  return; }
    if (!passwordValidation(password))   { JOptionPane.showMessageDialog(null, "Weak password.");  return; }
    if (salary < 0)                      { JOptionPane.showMessageDialog(null, "Salary must be >= 0."); return; }

    List<String[]> data = saveFileToArrayList(userFile);
    boolean found = false;

    // User.txt schema: email|password|name|gender|phone|age|id|salary|position|role
    for (String[] row : data) {
        if (row.length >= 10 && row[6].equals(id)) {
            row[2] = name;                    // Name
            row[0] = email;                   // Email
            row[4] = phone;                   // Phone
            row[7] = String.valueOf(salary);  // Salary
            row[8] = position;                // Position
            row[1] = password;                // Password
            found = true;
            break;
        }
    }

    if (!found) {
        JOptionPane.showMessageDialog(null, "User not found: " + id);
        return;
    }
    overwriteFile(userFile, data);
}


    public boolean canDeleteUser(String userId) {
    List<String[]> appts = saveFileToArrayList(apptFile);
    if (appts == null) return true;
    for (String[] row : appts) { // ...|custId|docId|staffId
        if (row.length >= 12) {
            if (userId.equalsIgnoreCase(row[10]) || userId.equalsIgnoreCase(row[11])) {
                return false;
            }
        }
    }
    return true;
}

public void deleteUser(String userId) {
    if (!canDeleteUser(userId)) {
        JOptionPane.showMessageDialog(null, "Cannot delete: user is assigned to appointments.");
        return;
    }
    List<String[]> data = saveFileToArrayList(userFile);
    List<String[]> out = new ArrayList<>();
    boolean removed = false;
    for (String[] row : data) {
        if (row.length >= 7 && row[6].equals(userId)) { removed = true; continue; }
        out.add(row);
    }
    if (!removed) { JOptionPane.showMessageDialog(null, "User not found: " + userId); return; }
    overwriteFile(userFile, out);
}


    

    // ==============================
    // ==== APPOINTMENT FUNCTIONS ===
    // ==============================

    public List<String[]> getAllAppointments() {
        List<String[]> rows = saveFileToArrayList(apptFile);
        if (rows == null) return new ArrayList<>();
        List<String[]> out = new ArrayList<>();
        for (String[] r : rows) {
            if (r.length >= 12) out.add(r);
        }
        return out;
    }

    public Set<String> getUniqueDoctorIds(List<String[]> apptRows) {
        Set<String> docIds = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
        for (String[] r : apptRows) {
            String docId = safe(r, 10);
            if (!docId.isEmpty()) docIds.add(docId);
        }
        return docIds;
    }

    public Set<String> getUniqueTimes(List<String[]> apptRows) {
        Set<String> times = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
        for (String[] r : apptRows) {
            String time = safe(r, 2);
            if (!time.isEmpty()) times.add(time);
        }
        return times;
    }

    public List<String[]> filterAppointments(List<String[]> allRows,
                                             String selDoc, String selMonth,
                                             String selPayment, String selRating,
                                             String selTime) {

        boolean fDoc     = selDoc != null     && !"All Doctors".equalsIgnoreCase(selDoc);
        boolean fMonth   = selMonth != null   && !"All Months".equalsIgnoreCase(selMonth);
        boolean fPayment = selPayment != null && !"All Payments".equalsIgnoreCase(selPayment);
        boolean fRating  = selRating != null  && !"All Ratings".equalsIgnoreCase(selRating);
        boolean fTime    = selTime != null    && !"All Times".equalsIgnoreCase(selTime);

        int wantedMonth = -1;
        if (fMonth) wantedMonth = monthNameToNumber(selMonth);

        List<String[]> out = new ArrayList<>();
        for (String[] r : allRows) {
            String date    = safe(r, 1);
            String time    = safe(r, 2);
            String pay     = safe(r, 5);
            String rating  = safe(r, 8);
            String docId   = safe(r, 10);

            if (fDoc && !docId.equalsIgnoreCase(selDoc)) continue;
            if (fMonth) {
                int m = extractMonth(date);
                if (m != wantedMonth) continue;
            }
            if (fPayment && !pay.equalsIgnoreCase(selPayment)) continue;
            if (fRating && !rating.equals(selRating)) continue;
            if (fTime && !time.equals(selTime)) continue;

            out.add(r);
        }
        return out;
    }

    // ===========================
    // ==== FEEDBACK FUNCTIONS ===
    // ===========================

    private Map<String, String> buildDoctorMap() {
        Map<String, String> map = new HashMap<>();
        List<String[]> users = listInform();
        if (users == null) return map;

        for (String[] u : users) {
            if (u.length >= 10) {
                String role = u[9];
                if ("Doctor".equalsIgnoreCase(role)) {
                    String id   = u[6];
                    String name = u[2];
                    map.put(id, name);
                }
            }
        }
        return map;
    }

    public List<String[]> getAllFeedbackRows() {
        List<String[]> out = new ArrayList<>();
        Map<String, String> doctorMap = buildDoctorMap();

        List<String[]> appointments = saveFileToArrayList(apptFile);
        if (appointments == null) return out;

        for (String[] row : appointments) {
            if (row.length < 11) continue;

            String apptId   = safe(row, 0);
            String date     = safe(row, 1);
            String time     = safe(row, 2);
            String feedback = safe(row, 3);
            String fee      = safe(row, 4);
            String comment  = safe(row, 7);
            String rating   = safe(row, 8);
            String docId    = safe(row, 10);

            String doctorName = doctorMap.getOrDefault(docId, docId);

            out.add(new String[]{apptId, date, time, feedback, fee, comment, rating, doctorName});
        }
        return out;
    }

    public List<String[]> filterFeedback(List<String[]> allFeedbackRows,
                                         String selectedDoctor, String selectedMonth) {

        boolean filterDoctor = (selectedDoctor != null && !"All Doctors".equalsIgnoreCase(selectedDoctor));
        boolean filterMonth  = (selectedMonth  != null && !"All Months".equalsIgnoreCase(selectedMonth));

        int wantedMonth = -1;
        if (filterMonth) wantedMonth = monthNameToNumber(selectedMonth);

        List<String[]> out = new ArrayList<>();
        for (String[] r : allFeedbackRows) {
            String doctorName = r[7];
            String date       = r[1];

            if (filterDoctor && !doctorName.equalsIgnoreCase(selectedDoctor)) continue;
            if (filterMonth) {
                int m = extractMonth(date);
                if (m != wantedMonth) continue;
            }

            out.add(r);
        }
        return out;
    }

    // =========================
    // ==== HELPER METHODS =====
    // =========================

    private static String safe(String[] arr, int idx) {
        return (idx >= 0 && idx < arr.length && arr[idx] != null) ? arr[idx] : "";
    }

    private int monthNameToNumber(String name) {
        switch (name.toLowerCase()) {
            case "january": return 1;
            case "february": return 2;
            case "march": return 3;
            case "april": return 4;
            case "may": return 5;
            case "june": return 6;
            case "july": return 7;
            case "august": return 8;
            case "september": return 9;
            case "october": return 10;
            case "november": return 11;
            case "december": return 12;
            default: return -1;
        }
    }

    private int extractMonth(String yyyyMmDd) {
        if (yyyyMmDd != null && yyyyMmDd.length() >= 7 && yyyyMmDd.charAt(4) == '-') {
            try {
                return Integer.parseInt(yyyyMmDd.substring(5, 7));
            } catch (NumberFormatException ignored) {}
        }
        return -1;
    }
    
    
    

// ============================
// ==== REPORTS FUNCTIONS =====
// ============================

// Count total appointments
public int getTotalAppointments() {
    return getAllAppointments().size();
}

// Sum total amount paid (from payment.txt)
public double getTotalAmountPaid() {
    List<String[]> payments = saveFileToArrayList(paymentFile);
    double total = 0.0;
    if (payments != null) {
        for (String[] row : payments) {
            if (row.length >= 2) {
                try {
                    total += Double.parseDouble(row[1]); // amount column
                } catch (NumberFormatException ignored) {}
            }
        }
    }
    return total;
}


// Calculate average rating (from appointments)
public double getAverageRating() {
    List<String[]> appts = getAllAppointments();
    double sum = 0;
    int count = 0;
    for (String[] r : appts) {
        if (r.length > 8) {
            try {
                int rating = Integer.parseInt(r[8]);
                sum += rating;
                count++;
            } catch (NumberFormatException ignored) {}
        }
    }
    return count > 0 ? sum / count : 0.0;
}
// === ID generation helpers ===
private String getRolePrefix(String role) {
    if (role == null) return "U";
    switch (role.trim().toLowerCase()) {
        case "doctor":   return "D";
        case "staff":    return "S";
        case "manager":  return "M";
        case "customer":
        case "patient":  return "C";
        default:         return "U";
    }
}
//helpers and validations 
// === DUPLICATE CHECKS ===
private boolean emailExists(String email) {
    if (email == null || email.trim().isEmpty()) return false;
    List<String[]> data = saveFileToArrayList(userFile);
    if (data == null) return false;
    String target = email.trim();
    for (String[] row : data) {
        if (row.length > 0 && row[0] != null && row[0].trim().equalsIgnoreCase(target)) {
            return true; // duplicate email
        }
    }
    return false;
}

private boolean idExists(String id) {
    if (id == null || id.trim().isEmpty()) return false;
    List<String[]> data = saveFileToArrayList(userFile);
    if (data == null) return false;
    String target = id.trim();
    for (String[] row : data) {
        if (row.length > 6 && row[6] != null && row[6].trim().equalsIgnoreCase(target)) {
            return true; // duplicate id
        }
    }
    return false;
}

// Keep generating until we find an unused ID (safety against collisions)
public String generateUniqueId(String role) {
    String id = generateAutoId(role);
    int guard = 0; // safety to avoid infinite loop in weird files
    while (idExists(id) && guard < 10000) {
        // bump the numeric tail by 1
        String prefix = id.replaceAll("[0-9]", "");
        String numStr = id.substring(prefix.length()).replaceAll("[^0-9]", "");
        int n = 0;
        try { n = Integer.parseInt(numStr); } catch (NumberFormatException ignored) {}
        id = prefix + String.format("%03d", n + 1);
        guard++;
    }
    return id;
}


private String generateAutoId(String role) {
    String prefix = getRolePrefix(role).toUpperCase();
    List<String[]> data = saveFileToArrayList(userFile);
    int maxNum = 0;

    if (data != null) {
        for (String[] row : data) {
            if (row.length > 6 && row[6] != null) {
                String existingId = row[6].trim().toUpperCase();
                if (existingId.startsWith(prefix)) {
                    String num = existingId.substring(prefix.length()).replaceAll("[^0-9]", "");
                    if (!num.isEmpty()) {
                        try {
                            maxNum = Math.max(maxNum, Integer.parseInt(num));
                        } catch (NumberFormatException ignored) {}
                    }
                }
            }
        }
    }
    return prefix + String.format("%03d", maxNum + 1); // e.g., D001
}

public boolean persist() {
    List<String> lines = new ArrayList<>();
    boolean replaced = false;

    try (BufferedReader br = new BufferedReader(new FileReader("User.txt"))) {
        String line;
        while ((line = br.readLine()) != null) {
            String[] p = line.split("\\|", -1);
            if (p.length >= 10 && p[6].equalsIgnoreCase(this.id)) {
                // Replace line with updated info
                line = String.join("|",
                        this.email,
                        this.password,
                        this.name,
                        this.gender,
                        this.phoneNum,
                        String.valueOf(this.age),
                        this.id,
                        String.valueOf(this.salary),
                        this.position,
                        "Manager"
                );
                replaced = true;
            }
            lines.add(line);
        }
    } catch (IOException e) {
        e.printStackTrace();
        return false;
    }

    try (BufferedWriter bw = new BufferedWriter(new FileWriter("User.txt", false))) {
        for (int i = 0; i < lines.size(); i++) {
            bw.write(lines.get(i));
            if (i < lines.size() - 1) bw.newLine();
        }
    } catch (IOException e) {
        e.printStackTrace();
        return false;
    }

    return replaced;
}
// ===============================
// ==== EXPORT FUNCTIONS =========
// ===============================

public void exportTableToCSV(JTable table, String defaultFileName) {
    JFileChooser chooser = new JFileChooser();
    chooser.setSelectedFile(new File(defaultFileName + ".csv"));

    if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
        File file = chooser.getSelectedFile();

        try (FileWriter fw = new FileWriter(file)) {
            DefaultTableModel model = (DefaultTableModel) table.getModel();

            // Write header
            for (int i = 0; i < model.getColumnCount(); i++) {
                fw.write(model.getColumnName(i));
                if (i < model.getColumnCount() - 1) fw.write(",");
            }
            fw.write("\n");

            // Write rows
            for (int row = 0; row < model.getRowCount(); row++) {
                for (int col = 0; col < model.getColumnCount(); col++) {
                    Object val = model.getValueAt(row, col);
                    fw.write(val != null ? val.toString() : "");
                    if (col < model.getColumnCount() - 1) fw.write(",");
                }
                fw.write("\n");
            }

            JOptionPane.showMessageDialog(null,
                    "CSV exported successfully:\n" + file.getAbsolutePath(),
                    "Export Successful",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Error exporting CSV: " + e.getMessage(),
                    "Export Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}


    public ArrayList<Manager> readFile(){


        ArrayList<Manager> managers = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader("User.txt"));
            String line;
            while((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 10 && parts[9].equalsIgnoreCase("Manager")){
                    String gmail = parts[0];
                    String password = parts[1];
                    String name = parts[2];
                    String gender = parts[3];
                    String phoneNum = parts[4];
                    int age = Integer.parseInt(parts[5]) ;
                    String staffId = parts[6];
                    double salary = Double.parseDouble(parts[7]);
                    String position = parts[8];
                    String roles = parts[9];

                    Manager manager = new Manager(gmail, password, name, gender, phoneNum, age, staffId, salary, position, roles);
                    managers.add(manager);
                }
            }

            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return managers;
    }
    
@Override
    public void appendFile(Manager manager) throws IOException {
        try {
            BufferedWriter UserWriter = new BufferedWriter(new FileWriter("User.txt", true));
            UserWriter.append(manager.email + "|" + manager.password + "|" + manager.name + "|" + manager.gender + "|" + manager.phoneNum + "|" + manager.age + "|" + manager.id + "|" + manager.salary + "|" + manager.position + "|" + "Manager" +"\n");
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
    
@Override
    public Manager searchById(String managerId){
        ArrayList<Manager> managers = readFile();

        for (Manager m : managers) {
            if (m.getId().equalsIgnoreCase(managerId)) { // or getCustId() if that's the correct getter
                return m; // return the found customer
            }
        }

        // If no match found, return null
        return null;
    }
   
}

