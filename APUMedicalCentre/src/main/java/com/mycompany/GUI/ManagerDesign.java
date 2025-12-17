package com.mycompany.GUI;

import com.mycompany.staffmodule.Appointment;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;
import com.mycompany.staffmodule.Manager;







/**
 *
 * @author amar2
 */
public class ManagerDesign extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(ManagerDesign.class.getName());

    // ===== Manager logic object =====
  private Manager manager;


    private static final String[] FEEDBACK_COLS = {
        "ApptID", "Date", "Time", "Feedback", "ExtraCharge", "Comment", "Rating", "DoctorName"
    };

    // ===== View Appointments filtering state =====
    private JComboBox<String> cbApptDoctor, cbApptMonth, cbApptPayment, cbApptRating, cbApptTime;

    // Map the displayed doctor text ("Name (Spec)") -> doctorId
private java.util.Map<String, String> doctorDisplayToId = new java.util.LinkedHashMap<>();

   // --- Card names ---
    private static final String CARD_WELCOME     = "welcome";
    private static final String CARD_OWN_PROFILE = "own_profile";
    private static final String CARD_UPDATE_INFO = "update_info";
    private static final String CARD_MANAGEMENT  = "management";
    private static final String CARD_ADD         = "add";
    private static final String CARD_UPDATE_USER = "update_user";
    private static final String CARD_DELETE_USER = "delete_user";
    private static final String CARD_FEEDBACK    = "feedback";
    private static final String CARD_VIEW_APPOINTMENTS = "view_appointments";
    private static final String CARD_REPORTS     = "reports";

    // --- Main content area ---
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel contentCards   = new JPanel(cardLayout);

    // --- Sidebar buttons ---
    private JButton btnOwnProfile, btnManagement, btnFeedback, btnLogout, btnReports;
    private JButton btnViewAppointment;

    // --- Profile buttons ---
    private JButton btnUpdateInfo, btnCancelProfile;

    // --- Update Info buttons ---
    private JButton btnCancelUpdate, btnConfirmUpdate;

    // --- Management buttons ---
    private JButton btnAddUser, btnUpdateUser, btnDeleteUser;

    // --- Add User buttons ---
    
    private JButton btnCancelAdd, btnSaveAdd;
    private JTextField tfAddAge;
    private JTextField tfAddPhone = new JTextField(14);
private JTextField tfAddSalary = new JTextField(14);

    private JComboBox<String> cbAddGen;

    // --- Update User components ---
    private JTable tblUpdateUser;
    private JTextField tfUpdId, tfUpdName, tfUpdEmail, tfUpdPassword, tfSalary;
    private JButton btnConfirmUpdateuser, btnCancelUpdateuser;
    private JTextField tfUpdPhone, tfUpdPosition;


    // --- Delete User components ---
    private JTable tblDeleteUser;
    private JTextField tfDelId, tfDelName, tfDelEmail, tfDelPassword, tfDSalary;
    private JButton btnConfirmDelete, btnCancelDelete;
private JTextField tfDelPhone;     // make sure you use this in the panel
private JTextField tfDelSalary;    // OR use tfDSalary consistently (pick ONE name)
private JTextField tfDelPosition;
private JTextField tfDelRole;


    // --- View Appointments components ---
    private JTable tblAppointments;
    private JButton btnExportAppointmentsCSV, btnCancelAppointments;

    // --- Feedback components ---
    private JTable tblFeedback;
    private JButton btnExportCSV, btnCancelFeedback;
    private JComboBox<String> cbDoctorFilter, cbMonthFilter;

    // --- Reports buttons ---
    private JButton btnOpenReport;

    // --- Form fields ---
    private final JTextField tfName     = new JTextField(16);
    private final JTextField tfEmail    = new JTextField(16);
    private final JTextField tfPhone    = new JTextField(16);
    private final JComboBox<String> cbGender = new JComboBox<>(new String[]{"Male","Female"});

    private final JTextField tfPassword = new JTextField(16);

    private final JTextField tfAddId    = new JTextField(14);
    private final JTextField tfAddName  = new JTextField(14);
    private final JTextField tfAddEmail = new JTextField(14);
    private final JPasswordField pfAddPassword = new JPasswordField(14);
    private final JComboBox<String> cbRole = new JComboBox<>(new String[]{"Doctor", "Manager", "Staff"});
    private JTextField tfSpecializationAdd = new JTextField(14);
    
    /**
     * Creates new form ManagerDesign
     */
    public ManagerDesign(Manager manager) {
    this.manager = manager;
    initComponents();
    setupCustomUI();
    setupEventHandlers();
    populateUpdateInfoFields();   // <--- new method to prefill fields
}
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

   private void populateUpdateInfoFields() {
    if (manager != null) {
        tfName.setText(manager.getName() != null ? manager.getName() : "");
        tfEmail.setText(manager.getEmail() != null ? manager.getEmail() : "");
        tfPhone.setText(manager.getPhoneNum() != null ? manager.getPhoneNum() : "");
        cbGender.setSelectedItem(
    manager.getGender()!=null && manager.getGender().equalsIgnoreCase("Female") ? "Female" : "Male"
);

        tfPassword.setText(manager.getPassword() != null ? manager.getPassword() : "");
    }
}
 
  private void setupCustomUI() {
        setSize(700, 520);
        setLocationRelativeTo(null);

        getContentPane().removeAll();
        getContentPane().setLayout(new BorderLayout());

        JPanel sidebar = buildSidebar();
        getContentPane().add(sidebar, BorderLayout.WEST);

        contentCards.add(buildWelcomePanel(), CARD_WELCOME);
        contentCards.add(buildOwnProfilePanel(), CARD_OWN_PROFILE);
        contentCards.add(buildUpdateInfoPanel(), CARD_UPDATE_INFO);
        contentCards.add(buildManagementPanel(), CARD_MANAGEMENT);
        contentCards.add(buildAddPanel(), CARD_ADD);
        contentCards.add(buildUpdateUserPanel(), CARD_UPDATE_USER);
        contentCards.add(buildDeleteUserPanel(), CARD_DELETE_USER);
        contentCards.add(buildFeedbackPanel(), CARD_FEEDBACK);
        contentCards.add(buildReportsPanel(), CARD_REPORTS);

        getContentPane().add(contentCards, BorderLayout.CENTER);

        cardLayout.show(contentCards, CARD_WELCOME);
    }

    private JPanel buildSidebar() {
        JPanel panel = new JPanel(new GridLayout(6, 1, 6, 6));
        panel.setPreferredSize(new Dimension(180, 520));
        panel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        panel.setBackground(new Color(255, 228, 225));

        btnOwnProfile    = new JButton("Own Profile");
        btnManagement    = new JButton("Management");
        btnFeedback      = new JButton("Feedback");
        btnViewAppointment = new JButton("View Appointment");
        btnReports       = new JButton("Generated Reports");
        btnLogout        = new JButton("Logout");

        panel.add(btnOwnProfile);
        panel.add(btnManagement);
        panel.add(btnFeedback);
        panel.add(btnViewAppointment);
        panel.add(btnReports);
        panel.add(btnLogout);

        return panel;
    }

    private JPanel buildWelcomePanel() {
        JPanel p = new JPanel(new BorderLayout());
        JLabel lbl = new JLabel("Welcome! Use the sidebar to navigate.", SwingConstants.CENTER);
        p.add(lbl, BorderLayout.CENTER);
        return p;
    }

    private JPanel buildOwnProfilePanel() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        p.setBorder(BorderFactory.createTitledBorder("Own Profile"));

        btnUpdateInfo   = new JButton("Update Information");
        btnCancelProfile = new JButton("Cancel");

        p.add(btnUpdateInfo);
        p.add(btnCancelProfile);

        return p;
    }

    private JPanel buildUpdateInfoPanel() {
        JPanel p = new JPanel(new GridLayout(6, 2, 10, 10));
        p.setBorder(BorderFactory.createTitledBorder("Update Information"));

        p.add(new JLabel("Full Name:")); p.add(tfName);
        p.add(new JLabel("Email:"));     p.add(tfEmail);
        p.add(new JLabel("Phone:"));     p.add(tfPhone);
        p.add(new JLabel("Gender:"));    p.add(cbGender);

        p.add(new JLabel("Password:"));  p.add(tfPassword);

        btnCancelUpdate  = new JButton("Cancel");
        btnConfirmUpdate = new JButton("Confirm");
        p.add(btnCancelUpdate); p.add(btnConfirmUpdate);

        return p;
    }

    private JPanel buildManagementPanel() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        p.setBorder(BorderFactory.createTitledBorder("Management"));

        btnAddUser    = new JButton("Add");
        btnUpdateUser = new JButton("Update User");
        btnDeleteUser = new JButton("Delete User");

        p.add(btnAddUser);
        p.add(btnUpdateUser);
        p.add(btnDeleteUser);

        return p;
    }
    private JPanel buildDeleteUserPanel() {
    JPanel root = new JPanel(new BorderLayout());
    root.setBorder(BorderFactory.createTitledBorder("Delete User"));

    // Table
    tblDeleteUser = new JTable();
    loadUsersIntoTable(tblDeleteUser);
    tblDeleteUser.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    root.add(new JScrollPane(tblDeleteUser), BorderLayout.CENTER);

    // ---------- RIGHT/LEFT CELL WRAPPERS (inline, no helpers) ----------
    java.util.function.Function<String, JPanel> L = (txt) -> {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        p.setOpaque(false); p.add(new JLabel(txt));
        return p;
    };
    java.util.function.Function<JComponent, JPanel> R = (comp) -> {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        p.setOpaque(false); p.add(comp);
        return p;
    };

    // ---------- FIELDS (use class fields; DO NOT redeclare with types here) ----------
    tfDelId        = new JTextField(15);
    tfDelName      = new JTextField(15);
    tfDelEmail     = new JTextField(15);
    tfDelPassword  = new JTextField(15);
    tfDelPhone     = new JTextField(15);
    tfDelSalary    = new JTextField(15);
    tfDelPosition  = new JTextField(15);
    tfDelRole      = new JTextField(15);

    // Read-only
    tfDelId.setEditable(false);
    tfDelName.setEditable(false);
    tfDelEmail.setEditable(false);
    tfDelPassword.setEditable(false);
    tfDelPhone.setEditable(false);
    tfDelSalary.setEditable(false);
    tfDelPosition.setEditable(false);
    tfDelRole.setEditable(false);

    // ---------- FORM ----------
    // 8 rows (ID, Name, Email, Password, Phone, Salary, Position, Role)
    JPanel form = new JPanel(new GridLayout(8, 2, 10, 10));
    form.setBorder(BorderFactory.createTitledBorder("Selected User Info"));

    form.add(L.apply("ID:"));        form.add(R.apply(tfDelId));
    form.add(L.apply("Name:"));      form.add(R.apply(tfDelName));
    form.add(L.apply("Email:"));     form.add(R.apply(tfDelEmail));
    form.add(L.apply("Password:"));  form.add(R.apply(tfDelPassword));
    form.add(L.apply("Phone:"));     form.add(R.apply(tfDelPhone));
    form.add(L.apply("Salary:"));    form.add(R.apply(tfDelSalary));
    form.add(L.apply("Position:"));  form.add(R.apply(tfDelPosition));
    form.add(L.apply("Role:"));      form.add(R.apply(tfDelRole));

    // ---------- ACTIONS ----------
    btnConfirmDelete = new JButton("Confirm Delete");
    btnCancelDelete  = new JButton("Cancel");
    JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
    actions.add(btnConfirmDelete);
    actions.add(btnCancelDelete);

    JPanel south = new JPanel(new BorderLayout());
    south.add(form, BorderLayout.CENTER);
    south.add(actions, BorderLayout.SOUTH);

    root.add(south, BorderLayout.SOUTH);

    // ---------- FILL FIELDS ON ROW SELECT ----------
    // Resolve column index by name to avoid hardcoded indexes
    java.util.function.Function<String, Integer> colIndex = (name) -> {
        javax.swing.table.TableModel m = tblDeleteUser.getModel();
        for (int i = 0; i < m.getColumnCount(); i++) {
            if (name.equalsIgnoreCase(m.getColumnName(i))) return i;
        }
        return -1;
    };

    tblDeleteUser.getSelectionModel().addListSelectionListener(e -> {
        if (e.getValueIsAdjusting()) return;
        int row = tblDeleteUser.getSelectedRow();
        javax.swing.table.TableModel m = tblDeleteUser.getModel();

        if (row < 0) {
            tfDelId.setText(""); tfDelName.setText(""); tfDelEmail.setText("");
            tfDelPassword.setText(""); tfDelPhone.setText(""); tfDelSalary.setText("");
            tfDelPosition.setText(""); tfDelRole.setText("");
            return;
        }

        int cEmail    = colIndex.apply("Email");
        int cPass     = colIndex.apply("Password");
        int cName     = colIndex.apply("Name");
        int cGender   = colIndex.apply("Gender");      // not displayed, but here if you need
        int cPhone    = colIndex.apply("Phone");
        int cAge      = colIndex.apply("Age");         // not displayed
        int cId       = colIndex.apply("ID");
        int cSalary   = colIndex.apply("Salary");
        int cPosition = colIndex.apply("Position");    // if your column is "Specialization", see below
        int cRole     = colIndex.apply("Role");

        // fallback for older schema that used "Specialization" instead of "Position"
        if (cPosition == -1) cPosition = colIndex.apply("Specialization");

        java.util.function.BiFunction<Integer,Integer,String> v = (r,c) ->
            (c >= 0 && c < m.getColumnCount() && m.getValueAt(r,c) != null) ? m.getValueAt(r,c).toString() : "";

        tfDelEmail.setText(   v.apply(row, cEmail)    );
        tfDelPassword.setText(v.apply(row, cPass)     );
        tfDelName.setText(    v.apply(row, cName)     );
        tfDelPhone.setText(   v.apply(row, cPhone)    );
        tfDelId.setText(      v.apply(row, cId)       );
        tfDelSalary.setText(  v.apply(row, cSalary)   );
        tfDelPosition.setText(v.apply(row, cPosition) );
        tfDelRole.setText(    v.apply(row, cRole)     );
    });

    // ---------- BUTTON HANDLERS (optional if already wired elsewhere) ----------
    btnConfirmDelete.addActionListener(e -> {
        String id = tfDelId.getText().trim();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Select a user first."); return;
        }
        if (!manager.canDeleteUser(id)) {
            JOptionPane.showMessageDialog(this, "Cannot delete: user is assigned to appointments."); return;
        }
        manager.deleteUser(id);
        JOptionPane.showMessageDialog(this, "User deleted.");
        loadUsersIntoTable(tblDeleteUser);
        tblDeleteUser.clearSelection();
    });
    btnCancelDelete.addActionListener(e -> cardLayout.show(contentCards, CARD_MANAGEMENT));

    return root;
}

    
    private JPanel buildAddPanel() {
    // 1) Grid for label/field rows (11 rows x 2 cols)
    JPanel p = new JPanel(new GridLayout(11, 2, 10, 10));
    p.setBorder(BorderFactory.createTitledBorder("Add New User"));

    // 2) Ensure we use class fields (NO local shadowing)
    cbAddGen = new JComboBox<>(new String[]{"Male", "Female"});
    tfAddAge = new JTextField(14);
    tfSpecializationAdd = new JTextField(14);
    tfAddPhone = new JTextField(14);
    tfAddSalary = new JTextField(14);

    tfAddId.setEditable(false);
    tfAddId.setText("Auto");

    // 3) Add rows (labels left, fields right)
    JPanel L, R;

    L = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));  L.add(new JLabel("ID:"));
    R = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0)); R.add(tfAddId);
    p.add(L); p.add(R);

    L = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));  L.add(new JLabel("Name:"));
    R = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0)); R.add(tfAddName);
    p.add(L); p.add(R);

    L = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));  L.add(new JLabel("Email:"));
    R = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0)); R.add(tfAddEmail);
    p.add(L); p.add(R);

    L = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));  L.add(new JLabel("Password:"));
    R = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0)); R.add(pfAddPassword);
    p.add(L); p.add(R);

    L = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));  L.add(new JLabel("Phone:"));
    R = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0)); R.add(tfAddPhone);
    p.add(L); p.add(R);

    L = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));  L.add(new JLabel("Age:"));
    R = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0)); R.add(tfAddAge);
    p.add(L); p.add(R);

    L = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));  L.add(new JLabel("Gender:"));
    R = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0)); R.add(cbAddGen);
    p.add(L); p.add(R);

    L = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));  L.add(new JLabel("Role:"));
    R = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0)); R.add(cbRole);
    p.add(L); p.add(R);

    L = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));  L.add(new JLabel("Position:"));
    R = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0)); R.add(tfSpecializationAdd);
    p.add(L); p.add(R);

    L = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));  L.add(new JLabel("Salary:"));
    R = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0)); R.add(tfAddSalary);
    p.add(L); p.add(R);

    // 4) Actions row: class fields (NO local shadowing)
    btnCancelAdd = new JButton("Cancel");
    btnSaveAdd   = new JButton("Save");
    JPanel actionsLeft  = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));  actionsLeft.add(btnCancelAdd);
    JPanel actionsRight = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0)); actionsRight.add(btnSaveAdd);
    p.add(actionsLeft);
    p.add(actionsRight);

    // 5) ID preview
    cbRole.addItemListener(e -> {
        if (e.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
            try {
                String previewId = manager.generateUniqueId(cbRole.getSelectedItem().toString());
                tfAddId.setText(previewId);
            } catch (Exception ex) { tfAddId.setText("Auto"); }
        }
    });

    // 6) ATTACH LISTENER TO THIS SAME INSTANCE
    btnSaveAdd.addActionListener(e -> {
        try {
            String name    = tfAddName.getText().trim();
            String email   = tfAddEmail.getText().trim();
            String pass    = new String(pfAddPassword.getPassword());
            String phone   = tfAddPhone.getText().trim();
            String ageStr  = tfAddAge.getText().trim();
            String salStr  = tfAddSalary.getText().trim();
            String gender  = cbAddGen.getSelectedItem().toString();
            String role    = cbRole.getSelectedItem().toString();
            String pos     = tfSpecializationAdd.getText().trim();
            String idVal   = tfAddId.getText().trim(); // may be "Auto"

            if (name.isEmpty() || email.isEmpty() || pass.isEmpty() ||
                phone.isEmpty() || ageStr.isEmpty() || salStr.isEmpty()) {
                JOptionPane.showMessageDialog(p, "Please fill in Name, Email, Password, Phone, Age, and Salary.");
                return;
            }

            int age;
            double salary;
            try { age = Integer.parseInt(ageStr); }
            catch (NumberFormatException ex) { JOptionPane.showMessageDialog(p, "Age must be a whole number."); return; }

            try { salary = Double.parseDouble(salStr); }
            catch (NumberFormatException ex) { JOptionPane.showMessageDialog(p, "Salary must be a valid number."); return; }

            // quick UI-side checks (Manager will also validate)
            if (!manager.emailValidation(email))  { JOptionPane.showMessageDialog(p, "Invalid email format."); return; }
            else if (!manager.phoneNumValidation(phone)) { JOptionPane.showMessageDialog(p, "Invalid phone number."); return; }
            else if (!manager.passwordValidation(pass)) { JOptionPane.showMessageDialog(p, "Weak password."); return; }
            else if (!manager.ageValidation(age)) { JOptionPane.showMessageDialog(p, "Age must be 18-65"); return; }
            else{
                manager.appendUser(idVal, name, email, pass, gender, phone, age, salary, role, pos);

                JOptionPane.showMessageDialog(p, "User added successfully!");
            }
            

            // reset
            tfAddId.setText("Auto");
            tfAddName.setText("");
            tfAddEmail.setText("");
            pfAddPassword.setText("");
            tfAddPhone.setText("");
            tfAddAge.setText("");
            tfAddSalary.setText("");
            cbAddGen.setSelectedIndex(0);
            cbRole.setSelectedIndex(0);
            tfSpecializationAdd.setText("");

        } catch (Exception ex) {
            // This will surface any runtime issue (e.g., signature mismatch)
            JOptionPane.showMessageDialog(p, "Error in Save: " + ex.getMessage());
            ex.printStackTrace();
        }
    });

    btnCancelAdd.addActionListener(e -> cardLayout.show(contentCards, CARD_MANAGEMENT));

    // 7) Return THIS panel that contains the button you just wired
    return p;
}


private JPanel buildUpdateUserPanel() {
    JPanel root = new JPanel(new BorderLayout());
    root.setBorder(BorderFactory.createTitledBorder("Update User"));

    tblUpdateUser = new JTable();
    loadUsersIntoTable(tblUpdateUser);
    root.add(new JScrollPane(tblUpdateUser), BorderLayout.CENTER);

    JPanel form = new JPanel(new GridLayout(7, 2, 10, 10)); // rows updated to match fields
    form.setBorder(BorderFactory.createTitledBorder("Edit User Info"));

    tfUpdId        = new JTextField(15);
    tfUpdName      = new JTextField(15);
    tfUpdEmail     = new JTextField(15);
    tfUpdPassword  = new JTextField(15);
    tfSalary       = new JTextField(15);
    tfUpdPhone     = new JTextField(15);
    tfUpdPosition  = new JTextField(15);
    tfUpdId.setEditable(false);   // Make ID read-only

    // Helpers per row (no global helpers, inline + minimal)
    JPanel L, R;

    // ID
    L = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));   L.setOpaque(false); L.add(new JLabel("ID:"));
    R = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));  R.setOpaque(false); R.add(tfUpdId);
    form.add(L); form.add(R);

    // Name
    L = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));   L.setOpaque(false); L.add(new JLabel("Name:"));
    R = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));  R.setOpaque(false); R.add(tfUpdName);
    form.add(L); form.add(R);

    // Email
    L = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));   L.setOpaque(false); L.add(new JLabel("Email:"));
    R = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));  R.setOpaque(false); R.add(tfUpdEmail);
    form.add(L); form.add(R);

    // Password
    L = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));   L.setOpaque(false); L.add(new JLabel("Password:"));
    R = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));  R.setOpaque(false); R.add(tfUpdPassword);
    form.add(L); form.add(R);

    // Salary
    L = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));   L.setOpaque(false); L.add(new JLabel("Salary:"));
    R = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));  R.setOpaque(false); R.add(tfSalary);
    form.add(L); form.add(R);

    // Phone
    L = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));   L.setOpaque(false); L.add(new JLabel("Phone:"));
    R = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));  R.setOpaque(false); R.add(tfUpdPhone);
    form.add(L); form.add(R);

    // Position
    L = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));   L.setOpaque(false); L.add(new JLabel("Position:"));
    R = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));  R.setOpaque(false); R.add(tfUpdPosition);
    form.add(L); form.add(R);

    btnConfirmUpdateuser = new JButton("Confirm");
    btnCancelUpdateuser  = new JButton("Cancel");

    JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
    actions.add(btnConfirmUpdateuser);
    actions.add(btnCancelUpdateuser);

    JPanel south = new JPanel(new BorderLayout());
    south.add(form, BorderLayout.CENTER);
    south.add(actions, BorderLayout.SOUTH);

    root.add(south, BorderLayout.SOUTH);
    return root;
}



    
    private JPanel buildFeedbackPanel() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBorder(BorderFactory.createTitledBorder("Feedback"));

        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        tblFeedback = new JTable(new DefaultTableModel());

        cbDoctorFilter = new JComboBox<>();
        cbDoctorFilter.addItem("All Doctors");
        for (String[] user : manager.listInform()) {
            if (user.length >= 10 && "Doctor".equalsIgnoreCase(user[9])) {
                cbDoctorFilter.addItem(user[2]);
            }
        }

        cbMonthFilter  = new JComboBox<>(new String[]{
            "All Months","January","February","March","April","May","June",
            "July","August","September","October","November","December"
        });

        filterPanel.add(new JLabel("Doctor:"));
        filterPanel.add(cbDoctorFilter);
        filterPanel.add(new JLabel("Month:"));
        filterPanel.add(cbMonthFilter);
        p.add(filterPanel, BorderLayout.NORTH);

        refreshFeedbackTable();

        JScrollPane scrollPane = new JScrollPane(tblFeedback);
        p.add(scrollPane, BorderLayout.CENTER);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        btnExportCSV = new JButton("Export as CSV"); // ✅ assign to class field
btnCancelFeedback = new JButton("Cancel");
actions.add(btnExportCSV);

        actions.add(btnCancelFeedback);
        p.add(actions, BorderLayout.SOUTH);

        cbDoctorFilter.addActionListener(e -> refreshFeedbackTable());
        cbMonthFilter.addActionListener(e -> refreshFeedbackTable());

        return p;
    }
    private JPanel buildViewAppointmentsPanel() {
    JPanel root = new JPanel(new BorderLayout());
    root.setBorder(BorderFactory.createTitledBorder("View Appointments"));

    JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));

    cbApptDoctor  = new JComboBox<>(); // will show "Name (Spec)" from Appointment.DoctorInfo
    cbApptMonth   = new JComboBox<>();
    cbApptPayment = new JComboBox<>();
    cbApptRating  = new JComboBox<>();
    cbApptTime    = new JComboBox<>();

    filterPanel.add(new JLabel("Doctor:"));   filterPanel.add(cbApptDoctor);
    filterPanel.add(new JLabel("Month:"));    filterPanel.add(cbApptMonth);
    filterPanel.add(new JLabel("Payment:"));  filterPanel.add(cbApptPayment);
    filterPanel.add(new JLabel("Rating:"));   filterPanel.add(cbApptRating);
    filterPanel.add(new JLabel("Time:"));     filterPanel.add(cbApptTime);

    root.add(filterPanel, BorderLayout.NORTH);

    String[] columnNames = {"apptId", "date", "time", "feedback", "consultantFees",
                            "paymentStatus", "status", "Comment", "Rating",
                            "custId", "docId", "staffId"};

    tblAppointments = new JTable(new DefaultTableModel(new Object[0][columnNames.length], columnNames));
    root.add(new JScrollPane(tblAppointments), BorderLayout.CENTER);

    JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
    btnExportAppointmentsCSV = new JButton("Export as CSV");
    btnCancelAppointments    = new JButton("Cancel");
    actions.add(btnExportAppointmentsCSV);
    actions.add(btnCancelAppointments);
    root.add(actions, BorderLayout.SOUTH);

    // ===== Populate filters FROM DATA using Appointment helpers =====
    Appointment ap = new Appointment();
    java.util.List<Appointment> all = ap.readFile();

    // Doctors: use Appointment.loadDoctorsFromUserFile() -> DoctorInfo(id, name, specialization)
    doctorDisplayToId.clear();
    cbApptDoctor.addItem("All Doctors");
    doctorDisplayToId.put("All Doctors", null);
    for (Appointment.DoctorInfo d : Appointment.loadDoctorsFromUserFile()) {
        String display = d.getName() + " (" + d.getSpecialization() + ")";
        doctorDisplayToId.put(display, d.getDoctorId());
        cbApptDoctor.addItem(display); // show name(spec)
    }

    // Build distinct sets from Appointment rows
    java.util.SortedSet<String> months   = new java.util.TreeSet<>();
    java.util.SortedSet<String> payments = new java.util.TreeSet<>(String.CASE_INSENSITIVE_ORDER);
    java.util.SortedSet<String> ratings  = new java.util.TreeSet<>();
    java.util.SortedSet<String> times    = new java.util.TreeSet<>();

    for (Appointment a : all) {
        // Month (from dateObj)
        java.sql.Date d = a.getDateObj();
        if (d != null) {
            java.time.LocalDate ld = d.toLocalDate();
            String monthName = ld.getMonth().name().substring(0,1) + ld.getMonth().name().substring(1).toLowerCase();
            // Proper case (e.g., "SEPTEMBER" -> "September")
            monthName = monthName.charAt(0) + monthName.substring(1).toLowerCase();
            // Better: use java.time.format.TextStyle
            monthName = ld.getMonth().getDisplayName(java.time.format.TextStyle.FULL, java.util.Locale.ENGLISH);
            months.add(monthName);
        }

        // Payment status
        if (a.getPaymentStatus() != null && !a.getPaymentStatus().trim().isEmpty()) {
            payments.add(a.getPaymentStatus().trim());
        }

        // Rating
        ratings.add(String.valueOf(a.getRatingInt()));

        // Time (format like HH:mm or keep HH:mm:ss)
        java.sql.Time t = a.getTimeObj();
        if (t != null) {
            String ts = t.toString(); // "HH:mm:ss"
            // If you prefer HH:mm only:
            if (ts.length() >= 5) ts = ts.substring(0,5);
            times.add(ts);
        }
    }

    cbApptMonth.addItem("All Months");
    for (String m : months) cbApptMonth.addItem(m);

    cbApptPayment.addItem("All Payments");
    for (String s : payments) cbApptPayment.addItem(s);

    cbApptRating.addItem("All Ratings");
    for (String s : ratings) cbApptRating.addItem(s);

    cbApptTime.addItem("All Times");
    for (String s : times) cbApptTime.addItem(s);

    // Listeners -> refresh
    cbApptDoctor.addActionListener(e -> refreshAppointmentsTable());
    cbApptMonth.addActionListener(e -> refreshAppointmentsTable());
    cbApptPayment.addActionListener(e -> refreshAppointmentsTable());
    cbApptRating.addActionListener(e -> refreshAppointmentsTable());
    cbApptTime.addActionListener(e -> refreshAppointmentsTable());

    // Initial load
    refreshAppointmentsTable();
    return root;
}
private void refreshAppointmentsTable() {
    String[] cols = {"apptId", "date", "time", "feedback", "consultantFees",
                     "paymentStatus", "status", "Comment", "Rating",
                     "custId", "docId", "staffId"};

    // Doctor filter: get selected display text and map to ID
    String selectedDoctorDisplay = (String) cbApptDoctor.getSelectedItem();
    String selDocId = doctorDisplayToId.get(selectedDoctorDisplay);
    if (selDocId == null) { // "All Doctors" or not found
        selDocId = "All Doctors";
    }

    java.util.List<String[]> allRows = manager.getAllAppointments();
    java.util.List<String[]> filtered = manager.filterAppointments(
        allRows,
        selDocId,
        (String) cbApptMonth.getSelectedItem(),
        (String) cbApptPayment.getSelectedItem(),
        (String) cbApptRating.getSelectedItem(),
        (String) cbApptTime.getSelectedItem()
    );

    Object[][] data = new Object[filtered.size()][cols.length];
    for (int i = 0; i < filtered.size(); i++) data[i] = filtered.get(i);
    tblAppointments.setModel(new DefaultTableModel(data, cols));
}
private JPanel buildReportsPanel() {
    JPanel p = new JPanel(new GridLayout(3, 1, 10, 10));
    p.setBorder(BorderFactory.createTitledBorder("Dashboard Reports"));

    JLabel lblTotalAppointments = new JLabel("Total Appointments: " + manager.getTotalAppointments());
    JLabel lblTotalAmount       = new JLabel("Total Amount Paid: RM " + manager.getTotalAmountPaid());
    JLabel lblAverageRating     = new JLabel("Average Rating: " + String.format("%.2f", manager.getAverageRating()));

    lblTotalAppointments.setFont(new Font("Arial", Font.BOLD, 14));
    lblTotalAmount.setFont(new Font("Arial", Font.BOLD, 14));
    lblAverageRating.setFont(new Font("Arial", Font.BOLD, 14));

    p.add(lblTotalAppointments);
    p.add(lblTotalAmount);
    p.add(lblAverageRating);

    return p;
}

    private void refreshFeedbackTable() {
        List<String[]> allFeedback = manager.getAllFeedbackRows();
        List<String[]> filtered = manager.filterFeedback(
            allFeedback,
            (String) cbDoctorFilter.getSelectedItem(),
            (String) cbMonthFilter.getSelectedItem()
        );

        Object[][] data = new Object[filtered.size()][FEEDBACK_COLS.length];
        for (int i = 0; i < filtered.size(); i++) {
            data[i] = filtered.get(i);
        }
        tblFeedback.setModel(new DefaultTableModel(data, FEEDBACK_COLS));
    }

    
   
private void setupEventHandlers() {
    
    
    btnOwnProfile.addActionListener(e -> cardLayout.show(contentCards, CARD_OWN_PROFILE));
    btnManagement.addActionListener(e -> cardLayout.show(contentCards, CARD_MANAGEMENT));
    btnFeedback.addActionListener(e -> cardLayout.show(contentCards, CARD_FEEDBACK));
    btnReports.addActionListener(e -> cardLayout.show(contentCards, CARD_REPORTS));
btnLogout.addActionListener(e -> {
    // Dispose (close) the current Doctor UI window
    this.dispose();

    // Open the login screen again
    SwingUtilities.invokeLater(() -> new Login().setVisible(true));
});
    // When clicking on a row in Update User table → fill text fields
// When clicking on a row in Update User table → fill text fields
tblUpdateUser.getSelectionModel().addListSelectionListener(e -> {
    if (!e.getValueIsAdjusting() && tblUpdateUser.getSelectedRow() != -1) {
        int row = tblUpdateUser.getSelectedRow();

        // Column indexes must match your table header order:
        // {"Email","Password","Name","Gender","Phone","Age","ID","Salary","Position","Role"}
        // If your header is different, adjust the two indexes below accordingly.

        tfUpdId.setText(tblUpdateUser.getValueAt(row, 6).toString());       // ID
        tfUpdName.setText(tblUpdateUser.getValueAt(row, 2).toString());     // Name
        tfUpdEmail.setText(tblUpdateUser.getValueAt(row, 0).toString());    // Email
        tfUpdPassword.setText(tblUpdateUser.getValueAt(row, 1).toString()); // Password
        tfSalary.setText(tblUpdateUser.getValueAt(row, 7).toString());      // Salary

        // 👇 Add these two lines exactly here
        tfUpdPhone.setText(tblUpdateUser.getValueAt(row, 4).toString());     // Phone (index 4)
        tfUpdPosition.setText(tblUpdateUser.getValueAt(row, 8).toString()); // Position (formerly "Specialization")
  // Position (index 8)
    }
});



    btnLogout.addActionListener(e -> {
        JOptionPane.showMessageDialog(this, "Logout clicked");
        dispose();
    });

    btnUpdateInfo.addActionListener(e -> cardLayout.show(contentCards, CARD_UPDATE_INFO));
    btnCancelProfile.addActionListener(e -> cardLayout.show(contentCards, CARD_WELCOME));
    btnCancelUpdate.addActionListener(e -> cardLayout.show(contentCards, CARD_WELCOME));
    btnCancelUpdateuser.addActionListener(e -> cardLayout.show(contentCards, CARD_WELCOME));
    btnConfirmUpdate.addActionListener(e -> onSaveProfile());


    btnAddUser.addActionListener(e -> {
        // refresh ID preview when entering the Add screen (read-only)
        try {
            String roleSel = cbRole.getSelectedItem() == null ? "User" : cbRole.getSelectedItem().toString();
            String preview = manager.generateUniqueId(roleSel);
            tfAddId.setText(preview);
        } catch (Exception ex) {
            tfAddId.setText("Auto");
        }
        cardLayout.show(contentCards, CARD_ADD);
    });

    btnUpdateUser.addActionListener(e -> {
        loadUsersIntoTable(tblUpdateUser);
        cardLayout.show(contentCards, CARD_UPDATE_USER);
    });

    btnDeleteUser.addActionListener(e -> {
        loadUsersIntoTable(tblDeleteUser);
        cardLayout.show(contentCards, CARD_DELETE_USER);
    });

   btnConfirmUpdateuser.addActionListener(e -> {
    try {
        String id       = tfUpdId.getText().trim();
        String name     = tfUpdName.getText().trim();
        String email    = tfUpdEmail.getText().trim();
        String phone    = tfUpdPhone.getText().trim();       // ✅ get phone
        String position = tfUpdPosition.getText().trim();    // ✅ get position
        String pass     = tfUpdPassword.getText().trim();    // ✅ get password
        String salaryStr= tfSalary.getText().trim();

        if (id.isEmpty() || name.isEmpty() || email.isEmpty()
                || phone.isEmpty() || position.isEmpty()
                || pass.isEmpty() || salaryStr.isEmpty()) {
            JOptionPane.showMessageDialog(
                this, "Please fill all fields.", "Error", JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        double salary;
        try {
            salary = Double.parseDouble(salaryStr);
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this, "Salary must be a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        //validation
        if (!manager.emailValidation(email))  { JOptionPane.showMessageDialog(null, "Invalid email format."); return; }
            else if (!manager.phoneNumValidation(phone)) { JOptionPane.showMessageDialog(null, "Invalid phone number."); return; }
            else if (!manager.passwordValidation(pass)) { JOptionPane.showMessageDialog(null, "Weak password."); return; }
            
            else{
                // ✅ Call the updated Manager method
                manager.updateUser(id, name, email, phone, salary, position, pass);

                JOptionPane.showMessageDialog(this, "User updated successfully.");
                
                tfUpdId.setText("");
                tfUpdName.setText("");
                tfUpdEmail.setText("");
                tfUpdPhone.setText("");
                tfUpdPosition.setText("");
                tfUpdPassword.setText("");
                tfSalary.setText("");
                
            }

        

        // reload the table with fresh data
        loadUsersIntoTable(tblUpdateUser);
        tblUpdateUser.clearSelection();

    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Error updating user: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
});

   // Optional: nicer UX
tblDeleteUser.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

// Helper to get column index by name from the table model
java.util.function.Function<String, Integer> col = (name) -> {
    javax.swing.table.TableModel m = tblDeleteUser.getModel();
    for (int i = 0; i < m.getColumnCount(); i++) {
        if (name.equalsIgnoreCase(m.getColumnName(i))) return i;
    }
    return -1; // not found
};

tblDeleteUser.getSelectionModel().addListSelectionListener(e -> {
    if (e.getValueIsAdjusting()) return;
    int row = tblDeleteUser.getSelectedRow();
    if (row == -1) {
        // Clear if nothing selected
        tfDelId.setText("");
        tfDelName.setText("");
        tfDelEmail.setText("");
        tfDelPassword.setText("");
        tfDelPhone.setText("");
        tfDelSalary.setText("");
        tfDelPosition.setText("");
        tfDelRole.setText("");
        return;
    }

    javax.swing.table.TableModel m = tblDeleteUser.getModel();

    // Map by column name to avoid index mistakes
    int cEmail    = col.apply("Email");
    int cPass     = col.apply("Password");
    int cName     = col.apply("Name");
    int cPhone    = col.apply("Phone");
    int cId       = col.apply("ID");
    int cSalary   = col.apply("Salary");
    int cPosition = col.apply("Position");       // if your table uses "Specialization", see below
    int cRole     = col.apply("Role");

    // Fallback if your column is named "Specialization" instead of "Position"
    if (cPosition == -1) cPosition = col.apply("Specialization");

    // Safe getter
    java.util.function.BiFunction<Integer,Integer,String> val = (r,c) ->
        (c >= 0 && c < m.getColumnCount() && m.getValueAt(r,c) != null) ? m.getValueAt(r,c).toString() : "";

    tfDelEmail.setText(   val.apply(row, cEmail)    );
    tfDelPassword.setText(val.apply(row, cPass)     );
    tfDelName.setText(    val.apply(row, cName)     );
    tfDelPhone.setText(   val.apply(row, cPhone)    );
    tfDelId.setText(      val.apply(row, cId)       );
    tfDelSalary.setText(  val.apply(row, cSalary)   );
    tfDelPosition.setText(val.apply(row, cPosition) );
    tfDelRole.setText(    val.apply(row, cRole)     );
});



    btnCancelAdd.addActionListener(e -> cardLayout.show(contentCards, CARD_MANAGEMENT));
    btnCancelDelete.addActionListener(e -> cardLayout.show(contentCards, CARD_MANAGEMENT));

    btnCancelFeedback.addActionListener(e -> cardLayout.show(contentCards, CARD_WELCOME));
// btnExportPdf.addActionListener(e -> JOptionPane.showMessageDialog(this, "Export as PDF clicked"));

    contentCards.add(buildViewAppointmentsPanel(), CARD_VIEW_APPOINTMENTS);

    btnViewAppointment.addActionListener(e -> cardLayout.show(contentCards, CARD_VIEW_APPOINTMENTS));
    btnCancelAppointments.addActionListener(e -> cardLayout.show(contentCards, CARD_WELCOME));
    
    // Feedback Export (CSV instead of PDF)
btnExportCSV.addActionListener(e -> 
    manager.exportTableToCSV(tblFeedback, "feedback_report")
);

// Appointments Export (CSV instead of PDF)
btnExportAppointmentsCSV.addActionListener(e -> 
    manager.exportTableToCSV(tblAppointments, "appointments_report")
);


   
}
    private void loadUsersIntoTable(JTable table) {
        String[] cols = {"Email", "Password", "Name", "Gender", "Phone", "Age", "ID", "Salary", "Specialization", "Role"};
        List<String[]> users = manager.listInform();

        Object[][] data = new Object[users.size()][cols.length];
        for (int i = 0; i < users.size(); i++) {
            data[i] = users.get(i);
        }
        table.setModel(new DefaultTableModel(data, cols));
    }
    
private void onSaveProfile() {
    if (manager == null) {
        JOptionPane.showMessageDialog(this, "No manager loaded.");
        return;
    }

    String email  = tfEmail.getText().trim();
    String pwd    = tfPassword.getText().trim();
    String name   = tfName.getText().trim();
   String gender = (String) cbGender.getSelectedItem();

    String phone  = tfPhone.getText().trim();

    boolean ok = manager.updateProfile(email, pwd, name, gender, phone);

    JOptionPane.showMessageDialog(this, ok ? "Profile saved." : "Save failed.");

    if (ok) {
        // Reload the manager from file to make sure we have the latest
        Manager reloaded = Manager.findById(manager.getId());
        if (reloaded != null) {
            this.manager = reloaded;
        }
        populateUpdateInfoFields();
    }
}







    /**
     * @param args the command line arguments
     */
   public static void main(String args[]) {
    Manager mg1 = new Manager(
//        "manager1@gmail.com",
//        "manager111",
//        "manager1",
//        "Male",
//        "60120000001",
//        40,
//        "M001",
//        5000,
//        "Head Manager",
//        "Manager"
    );
    java.awt.EventQueue.invokeLater(() -> new ManagerDesign(mg1).setVisible(true));
}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
