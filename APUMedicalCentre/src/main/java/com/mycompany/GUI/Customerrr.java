/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.GUI;

import com.mycompany.staffmodule.Appointment;
import com.mycompany.staffmodule.Customer;
import com.mycompany.staffmodule.User;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class Customerrr extends javax.swing.JFrame {
//    private static final String CURRENT_CUSTOMER_ID = "C001"; // This should be dynamic in real application

    // Card names
    private static final String CARD_PROFILE = "card_profile";
    private static final String CARD_BOOK = "card_book";
    private static final String CARD_EDIT = "card_edit";
    private static final String CARD_FEEDBACK = "card_feedback";

    // Layout + containers
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel cardHost = new JPanel(cardLayout);

    // Top & Sidebar
    private JLabel lblTitle;
    private JButton btnLogout;
    private JPanel sidebar;
    private JButton btnNavProfile, btnNavBook, btnNavEdit, btnNavFeedback;

    // Profile components
    private JTextField tfCustId;
    private JTextField tfName;
    private JTextField tfEmail;
    private JTextField tfPhone;
    private JPasswordField pfPassword;
    private JComboBox<String> cbGender;
    private JSpinner spAge;
    private JButton btnProfileSave;

    // Book Panel components
    private JComboBox<String> cbDoctor;
    private JComboBox<String> cbTime;
    private JSpinner spnBookDate;
    private JButton btnConfirmBooking;

    // Payment Panel components
    private JComboBox<String> cbPaymentMethod;
    private JButton btnPayConfirm, btnPayCancel;
    private JLabel lblPaymentDetails;
    private String lastConfirmedApptId;
    private String lastConfirmedDoctor;
    private String lastConfirmedDate;
    private String lastConfirmedTime;

    // Edit Panel components
    private JTable tblAppointments;
    private DefaultTableModel editTableModel;
    private JComboBox<String> cbDoctorEdit;
    private JComboBox<String> cbTimeEdit;
    private JComponent dpEdit;
    private JButton btnEdit, btnCancelEdit, btnBack;

    // Feedback components
    private JTabbedPane feedbackTabs;
    private JTable tblDrFeedback;
    private DefaultTableModel drFeedbackModel;

   private Customer currentCustomer;

public Customerrr(Customer customer) {
    this.currentCustomer = customer;
    initializeWindow();
    buildUI();
    wireEvents();
    Appointment.checkAndFixAppointmentFile();
    populateProfileFields(); // <--- new
}

// default constructor (for testing only)
public Customerrr() {
    this(new Customer()); 
}

private void populateProfileFields() {
    if (currentCustomer != null) {
        tfCustId.setText(currentCustomer.getId() != null ? currentCustomer.getId() : "");
        tfName.setText(currentCustomer.getName() != null ? currentCustomer.getName() : "");
        pfPassword.setText(currentCustomer.getPassword() != null ? currentCustomer.getPassword() : "");
        tfEmail.setText(currentCustomer.getEmail() != null ? currentCustomer.getEmail() : "");
        cbGender.setSelectedItem(currentCustomer.getGender() != null ? currentCustomer.getGender() : "Male");
        tfPhone.setText(currentCustomer.getPhoneNum() != null ? currentCustomer.getPhoneNum() : "");
        spAge.setValue(currentCustomer.getAge());
    }
}


    private void initializeWindow() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Customer Portal");
        setSize(700, 520);
        setLocationRelativeTo(null);
    }

    private void buildUI() {
        // Root
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(new Color(230, 240, 250));
        setContentPane(root);

        // --- Top bar ---
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBorder(new EmptyBorder(10, 16, 10, 16));
        topBar.setBackground(new Color(65, 105, 225));
        lblTitle = new JLabel("Customer Portal");
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setFont(lblTitle.getFont().deriveFont(Font.BOLD, 18f));
        btnLogout = new JButton("Logout");
        styleButton(btnLogout, new Color(220, 20, 60));
        topBar.add(lblTitle, BorderLayout.WEST);
        topBar.add(btnLogout, BorderLayout.EAST);

        // Sidebar
        sidebar = new JPanel(new GridLayout(0, 1, 8, 8));
        sidebar.setPreferredSize(new Dimension(210, 700));
        sidebar.setBorder(new EmptyBorder(12, 12, 12, 12));
        sidebar.setBackground(new Color(255, 228, 225));
        btnNavProfile = new JButton("Own Profile");
        btnNavBook = new JButton("Book Appointment");
        btnNavEdit = new JButton("Edit Appointment");
        btnNavFeedback = new JButton("Feedback");
        styleButton(btnNavProfile, new Color(100, 149, 237));
        styleButton(btnNavBook, new Color(60, 179, 113));
        styleButton(btnNavEdit, new Color(255, 140, 0));
        styleButton(btnNavFeedback, new Color(218, 112, 214));
        sidebar.add(btnNavProfile);
        sidebar.add(btnNavBook);
        sidebar.add(btnNavEdit);
        sidebar.add(btnNavFeedback);

        // Cards
        cardHost.add(buildProfileCard(), CARD_PROFILE);
        cardHost.add(buildBookCard(), CARD_BOOK);
        cardHost.add(buildEditCard(), CARD_EDIT);
        cardHost.add(buildFeedbackCard(), CARD_FEEDBACK);
        cardHost.add(buildPaymentCard(), "card_payment");

        root.add(topBar, BorderLayout.NORTH);
        root.add(sidebar, BorderLayout.WEST);
        root.add(cardHost, BorderLayout.CENTER);

        cardLayout.show(cardHost, CARD_PROFILE);
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

   // Panel Building Methods
    // =========================
   // Panel Building Methods
    // =========================
    private JPanel buildProfileCard() {
        JPanel p = new JPanel(new GridBagLayout());
        p.setBorder(BorderFactory.createTitledBorder("Own Profile"));
        p.setBackground(new Color(255, 248, 220));

        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(8, 8, 8, 8);
        gc.anchor = GridBagConstraints.LINE_START;
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.weightx = 1.0;

        JLabel t = new JLabel("Own Profile");
        t.setFont(t.getFont().deriveFont(Font.BOLD, 16f));

        // Initialize components
        tfCustId = new JTextField(); tfCustId.setEditable(false);
        tfName = new JTextField();
        pfPassword = new JPasswordField();
        tfEmail = new JTextField();
        cbGender = new JComboBox<>(new String[]{"Male", "Female"});
        tfPhone = new JTextField();
        spAge = new JSpinner(new SpinnerNumberModel(18, 0, 120, 1));

        btnProfileSave = new JButton("Save");
        styleButton(btnProfileSave, new Color(60, 179, 113));
        JButton btnCancel = new JButton("Cancel");
        styleButton(btnCancel, new Color(220, 20, 60));
        JButton btnLoad = new JButton("Load");
        styleButton(btnLoad, new Color(100, 149, 237));

        int y = 0;
        gc.gridx = 0;
        gc.gridy = y++;
        gc.gridwidth = 2;
        p.add(t, gc);
        gc.gridwidth = 1;

        gc.gridx = 0;
        gc.gridy = y;
        p.add(new JLabel("Customer ID"), gc);
        gc.gridx = 1;
        p.add(tfCustId, gc);
        y++;

        gc.gridx = 0;
        gc.gridy = y;
        p.add(new JLabel("Name"), gc);
        gc.gridx = 1;
        p.add(tfName, gc);
        y++;

        gc.gridx = 0;
        gc.gridy = y;
        p.add(new JLabel("Password"), gc);
        gc.gridx = 1;
        p.add(pfPassword, gc);
        y++;

        gc.gridx = 0;
        gc.gridy = y;
        p.add(new JLabel("Gmail"), gc);
        gc.gridx = 1;
        p.add(tfEmail, gc);
        y++;

        gc.gridx = 0;
        gc.gridy = y;
        p.add(new JLabel("Gender"), gc);
        gc.gridx = 1;
        p.add(cbGender, gc);
        y++;

        gc.gridx = 0;
        gc.gridy = y;
        p.add(new JLabel("Phone Number"), gc);
        gc.gridx = 1;
        p.add(tfPhone, gc);
        y++;

        gc.gridx = 0;
        gc.gridy = y;
        p.add(new JLabel("Age"), gc);
        gc.gridx = 1;
        p.add(spAge, gc);
        y++;

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        actions.setOpaque(false);
        actions.add(btnCancel);
        actions.add(btnLoad);
        actions.add(btnProfileSave);
        gc.gridx = 0;
        gc.gridy = y;
        gc.gridwidth = 2;
        p.add(actions, gc);

        // Event handlers
        btnCancel.addActionListener(e -> {
            tfName.setText("");
            pfPassword.setText("");
            tfEmail.setText("");
            cbGender.setSelectedIndex(-1);
            tfPhone.setText("");
            spAge.setValue(18);
        });

        btnProfileSave.addActionListener(e -> {
            String id = tfCustId.getText().trim();
            String name = tfName.getText().trim();
            String password = new String(pfPassword.getPassword()).trim();
            String email = tfEmail.getText().trim();
            String gender = (cbGender.getSelectedItem() == null) ? "" : cbGender.getSelectedItem().toString().trim();
            String phone = tfPhone.getText().trim();
            int age = (int) spAge.getValue();
            
            User saveCust = new Customer();
            
            if(!saveCust.emailValidation(email)){
                JOptionPane.showMessageDialog(this, "Invalid Email !!!");
            }
            else if(!saveCust.passwordValidation(password)){
                JOptionPane.showMessageDialog(this, "Invalid Password !!! Password must be 8 character, including uppercase, lowercase, special character and number");
            }
            else if(!saveCust.phoneNumValidation(phone)){
                JOptionPane.showMessageDialog(this, "Invalid Phone Number !!!");
            }
            else if(!saveCust.ageValidation(age)){
                JOptionPane.showMessageDialog(this, "Invalid Age !!! Age must be at least 0");
            }
            else{
                Customer updated = new Customer(email, password, name, gender, phone, age, id);
                if (updated.updateCustomer(updated)) {
                    JOptionPane.showMessageDialog(this, "Profile updated successfully!");
                } else {
                    JOptionPane.showMessageDialog(this, "Customer ID not found!");
                }
            }

            
        });

        btnLoad.addActionListener(e -> {
            String id = tfCustId.getText().trim();
            if (id.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a Customer ID first.");
                return;
            }

            Customer c = new Customer().searchById(id);
            if (c != null) {
                tfName.setText(c.getName());
                pfPassword.setText(c.getPassword());
                tfEmail.setText(c.getEmail());
                cbGender.setSelectedItem(c.getGender());
                tfPhone.setText(c.getPhoneNum());
                spAge.setValue(c.getAge());
            } else {
                JOptionPane.showMessageDialog(this, "Customer ID not found in file!");
            }
        });

        return p;
    }

    private JPanel buildBookCard() {
        JPanel p = new JPanel(new GridBagLayout());
        p.setBorder(new EmptyBorder(24, 24, 24, 24));
        p.setBackground(new Color(224, 255, 255));

        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(8, 8, 8, 8);
        gc.anchor = GridBagConstraints.LINE_START;
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.weightx = 1.0;

        JLabel title = new JLabel("Book Appointment");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 16f));

        cbDoctor = new JComboBox<>();
        loadDoctorsIntoCombo();
        cbDoctor.setSelectedIndex(-1);

        // Set minimum date to today (no past dates allowed)
        Date today = new Date();
        spnBookDate = new JSpinner(new SpinnerDateModel(today, today, null, java.util.Calendar.DAY_OF_MONTH));
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(spnBookDate, "yyyy-MM-dd");
        spnBookDate.setEditor(dateEditor);

        cbTime = new JComboBox<>();
        cbTime.setEnabled(false);

        JLabel lblHint = new JLabel("Default duration: 1 hour. Break at 12:00 & 13:00 is unavailable.");

        btnConfirmBooking = new JButton("Confirm");
        styleButton(btnConfirmBooking, new Color(65, 105, 225));

        // Layout
        int y = 0;
        gc.gridx = 0;
        gc.gridy = y++;
        gc.gridwidth = 2;
        p.add(title, gc);
        gc.gridwidth = 1;

        gc.gridx = 0;
        gc.gridy = y;
        p.add(new JLabel("Doctor & Specialization"), gc);
        gc.gridx = 1;
        p.add(cbDoctor, gc);
        y++;

        gc.gridx = 0;
        gc.gridy = y;
        p.add(new JLabel("Date"), gc);
        gc.gridx = 1;
        p.add(spnBookDate, gc);
        y++;

        gc.gridx = 0;
        gc.gridy = y;
        p.add(new JLabel("Time"), gc);
        gc.gridx = 1;
        p.add(cbTime, gc);
        y++;

        gc.gridx = 0;
        gc.gridy = y++;
        gc.gridwidth = 2;
        p.add(lblHint, gc);

        gc.gridwidth = 1;
        gc.gridx = 1;
        gc.gridy = y;
        gc.anchor = GridBagConstraints.LINE_END;
        p.add(btnConfirmBooking, gc);

        return p;
    }

    private JPanel buildEditCard() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBorder(new EmptyBorder(24, 24, 24, 24));
        p.setBackground(new Color(255, 239, 213));

        JLabel title = new JLabel("Edit Appointment");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 16f));

        editTableModel = new DefaultTableModel(
                new Object[]{"Appt ID", "Doctor & Specialization", "Date", "Time", "Status", "Staff"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblAppointments = new JTable(editTableModel);
        JScrollPane scroll = new JScrollPane(tblAppointments);

        // Right form
        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(new Color(250, 250, 210));
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(8, 8, 8, 8);
        gc.anchor = GridBagConstraints.LINE_START;
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.weightx = 1.0;

        cbDoctorEdit = new JComboBox<>(new DefaultComboBoxModel<>(new String[]{}));
        cbDoctorEdit.setSelectedIndex(-1);
        loadDoctorsIntoEditCombo();

        cbTimeEdit = new JComboBox<>(new DefaultComboBoxModel<>(buildTimeSlotsWithBreaks()));
        cbTimeEdit.setSelectedIndex(-1);

        Date minTomorrow = java.sql.Date.valueOf(LocalDate.now().plusDays(1));
        dpEdit = createDateSpinner(minTomorrow);

        btnCancelEdit = new JButton("Cancel");
        btnEdit = new JButton("Edit");
        btnBack = new JButton("Back");

        styleButton(btnBack, new Color(100, 149, 237));
        styleButton(btnCancelEdit, new Color(220, 20, 60));
        styleButton(btnEdit, new Color(46, 139, 87));

        int y = 0;
        gc.gridx = 0;
        gc.gridy = y;
        form.add(new JLabel("Doctor"), gc);
        gc.gridx = 1;
        form.add(cbDoctorEdit, gc);
        y++;

        gc.gridx = 0;
        gc.gridy = y;
        form.add(new JLabel("Date"), gc);
        gc.gridx = 1;
        form.add(dpEdit, gc);
        y++;

        gc.gridx = 0;
        gc.gridy = y;
        form.add(new JLabel("Time"), gc);
        gc.gridx = 1;
        form.add(cbTimeEdit, gc);
        y++;

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        actions.add(btnBack);
        actions.add(btnCancelEdit);
        actions.add(btnEdit);

        gc.gridx = 0;
        gc.gridy = y++;
        gc.gridwidth = 2;
        form.add(actions, gc);

        p.add(title, BorderLayout.NORTH);
        p.add(scroll, BorderLayout.CENTER);
        p.add(form, BorderLayout.EAST);

        return p;
    }

    private JPanel buildPaymentCard() {
        JPanel p = new JPanel(new GridBagLayout());
        p.setBorder(new EmptyBorder(24, 24, 24, 24));
        p.setBackground(new Color(245, 255, 250));

        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(8, 8, 8, 8);
        gc.anchor = GridBagConstraints.LINE_START;
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.weightx = 1.0;

        JLabel title = new JLabel("Payment");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 16f));

        lblPaymentDetails = new JLabel("Details will appear here...");
        cbPaymentMethod = new JComboBox<>(new String[]{"Cash", "QR", "Debit Card", "Credit Card"});
        btnPayConfirm = new JButton("Pay RM20");
        btnPayCancel = new JButton("Cancel");

        styleButton(btnPayConfirm, new Color(60, 179, 113));
        styleButton(btnPayCancel, new Color(220, 20, 60));

        int y = 0;
        gc.gridx = 0;
        gc.gridy = y++;
        gc.gridwidth = 2;
        p.add(title, gc);
        gc.gridwidth = 1;

        gc.gridx = 0;
        gc.gridy = y;
        p.add(new JLabel("Booking Details:"), gc);
        gc.gridx = 1;
        p.add(lblPaymentDetails, gc);
        y++;

        gc.gridx = 0;
        gc.gridy = y;
        p.add(new JLabel("Select Payment Method:"), gc);
        gc.gridx = 1;
        p.add(cbPaymentMethod, gc);
        y++;

        gc.gridx = 0;
        gc.gridy = y;
        p.add(btnPayCancel, gc);
        gc.gridx = 1;
        p.add(btnPayConfirm, gc);

        return p;
    }

    private JPanel buildFeedbackCard() {
        JPanel host = new JPanel(new BorderLayout());
        host.setBorder(new EmptyBorder(24, 24, 24, 24));
        host.setBackground(new Color(248, 248, 255));

        JLabel title = new JLabel("Feedback");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 16f));

        feedbackTabs = new JTabbedPane();

        // Doctor Feedback Tab
        drFeedbackModel = new DefaultTableModel(
            new Object[]{"Appt ID", "Date", "Time", "Doctor & Specialization", "Feedback", "Charge"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblDrFeedback = new JTable(drFeedbackModel);
        JScrollPane spDr = new JScrollPane(tblDrFeedback);

        // Set column widths
        TableColumnModel drColumnModel = tblDrFeedback.getColumnModel();
        drColumnModel.getColumn(0).setPreferredWidth(60);
        drColumnModel.getColumn(1).setPreferredWidth(80);
        drColumnModel.getColumn(2).setPreferredWidth(60);
        drColumnModel.getColumn(3).setPreferredWidth(150);
        drColumnModel.getColumn(4).setPreferredWidth(120);
        drColumnModel.getColumn(5).setPreferredWidth(80);

        // Text area for full feedback
        JTextArea txtFullFeedback = new JTextArea(4, 50);
        txtFullFeedback.setLineWrap(true);
        txtFullFeedback.setWrapStyleWord(true);
        txtFullFeedback.setEditable(false);
        JScrollPane spFullFeedback = new JScrollPane(txtFullFeedback);

        tblDrFeedback.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tblDrFeedback.getSelectedRow() >= 0) {
                String feedback = String.valueOf(tblDrFeedback.getValueAt(tblDrFeedback.getSelectedRow(), 4));
                txtFullFeedback.setText(feedback);
            }
        });

        JPanel tabDr = new JPanel(new BorderLayout(8, 8));
        tabDr.add(new JLabel("Doctor Feedback:"), BorderLayout.NORTH);
        tabDr.add(spDr, BorderLayout.CENTER);
        
        JPanel bottomPanel = new JPanel(new BorderLayout(8, 8));
        bottomPanel.add(new JLabel("Full Feedback:"), BorderLayout.NORTH);
        bottomPanel.add(spFullFeedback, BorderLayout.CENTER);
        tabDr.add(bottomPanel, BorderLayout.SOUTH);

        // Add Comment Tab
        DefaultTableModel addCommentModel = new DefaultTableModel(
            new Object[]{"Appt ID", "Date", "Time", "Doctor & Specialization", "Rating", "Comment"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable tblAddComment = new JTable(addCommentModel);
        JScrollPane spAddComment = new JScrollPane(tblAddComment);

        JLabel lblSelectedAppt = new JLabel("Selected Appt ID: None");
        JComboBox<String> cbRating = new JComboBox<>(new String[]{"1", "2", "3", "4", "5"});
        JTextArea taComment = new JTextArea(3, 30);
        taComment.setLineWrap(true);
        taComment.setWrapStyleWord(true);
        JButton btnSendComment = new JButton("Send Comment");
        styleButton(btnSendComment, new Color(65, 105, 225));

        JPanel bottomAdd = new JPanel(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(8, 8, 8, 8);
        gc.anchor = GridBagConstraints.LINE_START;

        int y = 0;
        gc.gridx = 0;
        gc.gridy = y;
        bottomAdd.add(lblSelectedAppt, gc);
        y++;
        gc.gridx = 0;
        gc.gridy = y;
        bottomAdd.add(new JLabel("Rating:"), gc);
        gc.gridx = 1;
        bottomAdd.add(cbRating, gc);
        y++;
        gc.gridx = 0;
        gc.gridy = y;
        bottomAdd.add(new JLabel("Comment:"), gc);
        gc.gridx = 1;
        bottomAdd.add(new JScrollPane(taComment), gc);
        y++;
        gc.gridx = 1;
        gc.gridy = y;
        bottomAdd.add(btnSendComment, gc);

        JPanel tabAdd = new JPanel(new BorderLayout(8, 8));
        tabAdd.add(spAddComment, BorderLayout.CENTER);
        tabAdd.add(bottomAdd, BorderLayout.SOUTH);

        // Selection listener for Add Comment tab
        tblAddComment.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tblAddComment.getSelectedRow() >= 0) {
                String apptId = String.valueOf(tblAddComment.getValueAt(tblAddComment.getSelectedRow(), 0));
                lblSelectedAppt.setText("Selected Appt ID: " + apptId);
            }
        });

        // Send comment button action
        btnSendComment.addActionListener(e -> {
            int row = tblAddComment.getSelectedRow();
            if (row >= 0) {
                String rating = (String) cbRating.getSelectedItem();
                String comment = taComment.getText().trim();
                String apptId = String.valueOf(tblAddComment.getValueAt(row, 0));
                
                if (Appointment.updateAppointmentFeedback(apptId, rating, comment)) {
                    addCommentModel.setValueAt(rating, row, 4);
                    addCommentModel.setValueAt(comment, row, 5);
                    JOptionPane.showMessageDialog(this, "Feedback added to Appt ID: " + apptId);
                    taComment.setText("");
                    cbRating.setSelectedIndex(0);
                } else {
                    JOptionPane.showMessageDialog(this, "This appointment already has feedback or an error occurred.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select an appointment row first.");
            }
        });

        feedbackTabs.addTab("Doctor Feedback", tabDr);
        feedbackTabs.addTab("Add Comment", tabAdd);

        // Tab change listener
        feedbackTabs.addChangeListener(e -> {
            int selectedIndex = feedbackTabs.getSelectedIndex();
            if (selectedIndex == 0) {
                loadDoctorFeedback();
            } else if (selectedIndex == 1) {
                loadAppointmentsForComments(addCommentModel);
            }
        });

        host.add(title, BorderLayout.NORTH);
        host.add(feedbackTabs, BorderLayout.CENTER);

        return host;
    }

    // =========================
    // Event Wiring
    // =========================
    private void wireEvents() {
        // Navigation
        btnNavProfile.addActionListener(e -> cardLayout.show(cardHost, CARD_PROFILE));
        btnNavBook.addActionListener(e -> {
            cardLayout.show(cardHost, CARD_BOOK);
            loadDoctorsIntoCombo();
            refreshTimeSlots();
        });
        btnLogout.addActionListener(e -> {
    // Dispose (close) the current Doctor UI window
    this.dispose();

    // Open the login screen again
    SwingUtilities.invokeLater(() -> new Login().setVisible(true));
});

        btnNavEdit.addActionListener(e -> {
            cardLayout.show(cardHost, CARD_EDIT);
            loadAppointmentsIntoTable();
            initializeEditForm();
            tblAppointments.revalidate();
            tblAppointments.repaint();
        });
        btnNavFeedback.addActionListener(e -> {
            cardLayout.show(cardHost, CARD_FEEDBACK);
            loadDoctorFeedback();
        });
        btnBack.addActionListener(e -> cardLayout.show(cardHost, CARD_PROFILE));
        btnLogout.addActionListener(e -> JOptionPane.showMessageDialog(this, "Logout"));

        // Book appointment events
        cbDoctor.addActionListener(e -> refreshTimeSlots());
        spnBookDate.addChangeListener(e -> refreshTimeSlots());

        btnConfirmBooking.addActionListener(e -> {
            java.util.Date date = (java.util.Date) spnBookDate.getValue();
            String doctorSpec = (String) cbDoctor.getSelectedItem();
            String time = (String) cbTime.getSelectedItem();

            if (date == null || doctorSpec == null || time == null) {
                JOptionPane.showMessageDialog(this, "Please complete all selections.");
                return;
            }

            java.time.LocalDate localDate = date.toInstant()
                    .atZone(java.time.ZoneId.systemDefault())
                    .toLocalDate();
            String doctorId = Appointment.findDoctorIdBySpec(doctorSpec);
            String timeWithSeconds = time + ":00";

            if (!Appointment.isTimeSlotAvailable(doctorId, localDate, timeWithSeconds)) {
                JOptionPane.showMessageDialog(this, "Sorry, this time slot is no longer available. Please select another time.");
                refreshTimeSlots();
                return;
            }

            String apptId = Appointment.generateNextAppointmentId();

            lastConfirmedApptId = apptId;
            lastConfirmedDoctor = doctorSpec;
            lastConfirmedDate = new java.text.SimpleDateFormat("yyyy-MM-dd").format(date);
            lastConfirmedTime = time;

            lblPaymentDetails.setText(
                    "<html>Appt ID: " + apptId
                    + "<br>Doctor & Specialization: " + doctorSpec
                    + "<br>Date: " + lastConfirmedDate
                    + "<br>Time: " + time
                    + "<br>Payment Required: RM20.00</html>"
            );

            cardLayout.show(cardHost, "card_payment");
        });

        // Payment events
        btnPayConfirm.addActionListener(e -> {
    try {
        java.time.LocalDate bookingDate = java.time.LocalDate.parse(lastConfirmedDate);
        String doctorId = Appointment.findDoctorIdBySpec(lastConfirmedDoctor);
        String timeWithSeconds = lastConfirmedTime + ":00";

        // Check availability again before confirming
        if (!Appointment.isTimeSlotAvailable(doctorId, bookingDate, timeWithSeconds)) {
            JOptionPane.showMessageDialog(this, 
                "Sorry, this time slot was just booked by someone else. Please select another time.");
            cardLayout.show(cardHost, CARD_BOOK);
            return;
        }

        // ✅ Use the logged-in customer's ID
        boolean bookingSuccess = Appointment.bookNewAppointment(
                doctorId,
                currentCustomer.getId(),   // <-- changed here
                bookingDate,
                timeWithSeconds,
                20.0
        );

        if (bookingSuccess) {
            String method = (String) cbPaymentMethod.getSelectedItem();
            JOptionPane.showMessageDialog(this,
                    "<html><b>Payment Successful!</b><br>"
                    + "Payment Method: " + method + "<br>"
                    + "Appointment ID: " + lastConfirmedApptId + "<br>"
                    + "Doctor: " + lastConfirmedDoctor + "<br>"
                    + "Date: " + lastConfirmedDate + "<br>"
                    + "Time: " + lastConfirmedTime + "<br>"
                    + "Status: booked<br>"
                    + "Amount Paid: RM20.00</html>");

            resetBookingForm();
            cardLayout.show(cardHost, CARD_BOOK);
        } else {
            JOptionPane.showMessageDialog(this, 
                "Error: Could not complete the booking. Please try again or contact support.");
        }
    } catch (Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, 
            "Error processing payment and booking: " + ex.getMessage());
    }
});


        btnPayCancel.addActionListener(e -> cardLayout.show(cardHost, CARD_BOOK));

        // Edit appointment events
        cbDoctorEdit.addActionListener(e -> {
            if (tblAppointments.getSelectedRow() >= 0) {
                refreshEditTimeSlots();
            }
        });

        ((JSpinner) dpEdit).addChangeListener(e -> {
            if (tblAppointments.getSelectedRow() >= 0) {
                refreshEditTimeSlots();
            }
        });

        tblAppointments.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = tblAppointments.getSelectedRow();
                if (selectedRow >= 0) {
                    String apptId = (String) tblAppointments.getValueAt(selectedRow, 0);
                    populateEditForm(apptId);
                } else {
                    clearEditForm();
                }
            }
        });

      btnEdit.addActionListener(e -> {
    int selectedRow = tblAppointments.getSelectedRow();
    if (selectedRow < 0) {
        JOptionPane.showMessageDialog(this, "Please select an appointment to edit first.");
        return;
    }
    
    String apptId = (String) tblAppointments.getValueAt(selectedRow, 0);
    String currentStatus = (String) tblAppointments.getValueAt(selectedRow, 4); // Status column
    
    // CHECK IF APPOINTMENT IS CANCELLED - NEW VALIDATION
    if ("cancel".equalsIgnoreCase(currentStatus) || "cancelled".equalsIgnoreCase(currentStatus)) {
        JOptionPane.showMessageDialog(this, 
            "Cannot edit cancelled appointments.\n" +
            "Appointment ID: " + apptId + " is already cancelled.",
            "Edit Not Allowed", 
            JOptionPane.WARNING_MESSAGE);
        return;
    }
    
    String currentDoctor = (String) tblAppointments.getValueAt(selectedRow, 1);
    String currentDate = (String) tblAppointments.getValueAt(selectedRow, 2);
    String currentTime = (String) tblAppointments.getValueAt(selectedRow, 3);
    
    String newDoctor = (String) cbDoctorEdit.getSelectedItem();
    java.util.Date newDateObj = (java.util.Date) ((JSpinner) dpEdit).getValue();
    String newTime = (String) cbTimeEdit.getSelectedItem();
    
    if (newDoctor == null || newDateObj == null || newTime == null || 
        newTime.equals("No available times") || newTime.equals("Select doctor and date first")) {
        JOptionPane.showMessageDialog(this, "Please select valid doctor, date, and time before editing.");
        return;
    }
    
    String newDate = new java.text.SimpleDateFormat("yyyy-MM-dd").format(newDateObj);
    
    if (newDoctor.equals(currentDoctor) && newDate.equals(currentDate) && newTime.equals(currentTime)) {
        JOptionPane.showMessageDialog(this, "No changes detected. Please modify at least one field.");
        return;
    }
    
    if (newTime.equals("12:00") || newTime.equals("13:00")) {
        JOptionPane.showMessageDialog(this, "Cannot book during lunch break (12:00-13:00). Please choose another time.");
        return;
    }
    
    String doctorId = Appointment.findDoctorIdBySpec(newDoctor);
    java.time.LocalDate localDate = newDateObj.toInstant()
            .atZone(java.time.ZoneId.systemDefault())
            .toLocalDate();
    String timeWithSeconds = newTime + ":00";
    
    if (!Appointment.isTimeSlotAvailable(doctorId, localDate, timeWithSeconds)) {
        JOptionPane.showMessageDialog(this, "This time slot is no longer available. Please choose another time.");
        refreshEditTimeSlots();
        return;
    }
    
    int confirm = JOptionPane.showConfirmDialog(this,
        "Are you sure you want to update this appointment?\n\n" +
        "From:\n- Doctor: " + currentDoctor + "\n- Date: " + currentDate + "\n- Time: " + currentTime +
        "\n\nTo:\n- Doctor: " + newDoctor + "\n- Date: " + newDate + "\n- Time: " + newTime,
        "Confirm Changes", JOptionPane.YES_NO_OPTION);
    
    if (confirm == JOptionPane.YES_OPTION) {
        if (Appointment.updateAppointment(apptId, newDoctor, newDate, newTime)) {
            JOptionPane.showMessageDialog(this, "Appointment updated successfully!");
            loadAppointmentsIntoTable(); // Refresh the table
            clearEditForm();
        } else {
            JOptionPane.showMessageDialog(this, 
                "Error updating appointment.\n" +
                "This may be because the appointment is cancelled or already updated.",
                "Update Failed", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
});

        btnCancelEdit.addActionListener(e -> {
            int selectedRow = tblAppointments.getSelectedRow();
            if (selectedRow < 0) {
                JOptionPane.showMessageDialog(this, "Please select an appointment to cancel first.");
                return;
            }
            
            String apptId = (String) tblAppointments.getValueAt(selectedRow, 0);
            String doctor = (String) tblAppointments.getValueAt(selectedRow, 1);
            String date = (String) tblAppointments.getValueAt(selectedRow, 2);
            String time = (String) tblAppointments.getValueAt(selectedRow, 3);
            String status = (String) tblAppointments.getValueAt(selectedRow, 4);
            
            if ("Cancelled".equalsIgnoreCase(status)) {
                JOptionPane.showMessageDialog(this, "This appointment is already cancelled.");
                return;
            }
            
            int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to cancel this appointment?\n\n" +
                "Appointment ID: " + apptId + "\n" +
                "Doctor: " + doctor + "\n" +
                "Date: " + date + "\n" +
                "Time: " + time,
                "Confirm Cancellation", JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                if (Appointment.cancelAppointment(apptId)) {
                    JOptionPane.showMessageDialog(this, "Appointment cancelled successfully!");
                    loadAppointmentsIntoTable(); // Refresh the table
                    clearEditForm();
                } else {
                    JOptionPane.showMessageDialog(this, "Error cancelling appointment.");
                }
            }
        });
    }

    // =========================
    // Helper Methods
    // =========================
    private void styleButton(JButton btn, Color bg) {
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
    }

    private void loadDoctorsIntoCombo() {
        List<Appointment.DoctorInfo> doctors = Appointment.loadDoctorsFromUserFile();
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        for (Appointment.DoctorInfo doctor : doctors) {
            model.addElement(doctor.toString());
        }
        cbDoctor.setModel(model);
        cbDoctor.setSelectedIndex(-1);
    }

    private void loadDoctorsIntoEditCombo() {
        cbDoctorEdit.removeAllItems();
        List<Appointment.DoctorInfo> doctors = Appointment.loadDoctorsFromUserFile();
        for (Appointment.DoctorInfo doctor : doctors) {
            cbDoctorEdit.addItem(doctor.toString());
        }
        cbDoctorEdit.setSelectedIndex(-1);
    }

    private void refreshTimeSlots() {
        cbTime.removeAllItems();

        String doctorSpec = (String) cbDoctor.getSelectedItem();
        java.util.Date date = (java.util.Date) spnBookDate.getValue();

        if (doctorSpec == null || date == null) {
            cbTime.setEnabled(false);
            return;
        }

        java.time.LocalDate localDate = date.toInstant()
                .atZone(java.time.ZoneId.systemDefault())
                .toLocalDate();
        String doctorId = Appointment.findDoctorIdBySpec(doctorSpec);

        List<Appointment.TimeSlotInfo> timeSlots = Appointment.getTimeSlotAvailability(doctorId, localDate);

        for (Appointment.TimeSlotInfo slot : timeSlots) {
            if (slot.isAvailable()) {
                String displayTime = slot.getTimeSlot().substring(0, 5);
                cbTime.addItem(displayTime);
            }
        }

        cbTime.setEnabled(cbTime.getItemCount() > 0);

        if (cbTime.getItemCount() == 0) {
            cbTime.addItem("No available times");
            cbTime.setEnabled(false);
        }
    }

    private void refreshEditTimeSlots() {
        cbTimeEdit.removeAllItems();
        
        String doctorSpec = (String) cbDoctorEdit.getSelectedItem();
        java.util.Date date = (java.util.Date) ((JSpinner) dpEdit).getValue();
        
        if (doctorSpec == null || date == null) {
            cbTimeEdit.setEnabled(false);
            cbTimeEdit.addItem("Select doctor and date first");
            return;
        }
        
        java.time.LocalDate localDate = date.toInstant()
                .atZone(java.time.ZoneId.systemDefault())
                .toLocalDate();
        String doctorId = Appointment.findDoctorIdBySpec(doctorSpec);
        
        if (doctorId == null || doctorId.equals("UNKNOWN")) {
            cbTimeEdit.setEnabled(false);
            cbTimeEdit.addItem("Invalid doctor selection");
            return;
        }
        
        List<Appointment.TimeSlotInfo> timeSlots = Appointment.getTimeSlotAvailability(doctorId, localDate);
        
        boolean hasAvailableSlots = false;
        for (Appointment.TimeSlotInfo slot : timeSlots) {
            if (slot.isAvailable()) {
                String displayTime = slot.getTimeSlot().substring(0, 5);
                cbTimeEdit.addItem(displayTime);
                hasAvailableSlots = true;
            }
        }
        
        cbTimeEdit.setEnabled(hasAvailableSlots);
        
        if (!hasAvailableSlots) {
            cbTimeEdit.addItem("No available times");
            cbTimeEdit.setEnabled(false);
        }
    }

    private void resetBookingForm() {
        cbDoctor.setSelectedIndex(-1);
        cbTime.removeAllItems();
        cbTime.setEnabled(false);
        spnBookDate.setValue(new java.util.Date());
        lastConfirmedApptId = null;
        lastConfirmedDoctor = null;
        lastConfirmedDate = null;
        lastConfirmedTime = null;
    }

    private JComponent createDateSpinner(Date minDate) {
        Date now = new Date();
        Date initial = (minDate != null && now.before(minDate)) ? minDate : now;
        SpinnerDateModel model = new SpinnerDateModel(initial, minDate, null, java.util.Calendar.DAY_OF_MONTH);
        JSpinner spinner = new JSpinner(model);
        JSpinner.DateEditor editor = new JSpinner.DateEditor(spinner, "yyyy-MM-dd");
        spinner.setEditor(editor);
        return spinner;
    }

    private static String[] buildTimeSlotsWithBreaks() {
        java.util.List<String> list = new java.util.ArrayList<>();
        for (int h = 9; h <= 16; h++) {
            if (h == 12) continue; // skip lunch hour
            list.add(String.format("%02d:00", h));
        }
        return list.toArray(new String[0]);
    }

    private void loadAppointmentsIntoTable() {
        editTableModel.setRowCount(0);
        
        List<Appointment.AppointmentDetails> appointments = Appointment.loadAppointmentsForCustomer(currentCustomer.getId());
        
        for (Appointment.AppointmentDetails appt : appointments) {
            editTableModel.addRow(new Object[]{
                appt.getAppointmentId(),
                appt.getDoctorName(),
                appt.getDate(),
                appt.getTime(),
                appt.getStatus(),
                appt.getStaffName()
            });
        }
        
        tblAppointments.revalidate();
        tblAppointments.repaint();
    }

    private void populateEditForm(String apptId) {
        btnEdit.setEnabled(true);
        btnEdit.setText("Edit Appointment");
        
        Appointment.AppointmentDetails details = Appointment.getAppointmentById(apptId);
        if (details != null) {
            cbDoctorEdit.setSelectedItem(details.getDoctorName());
            
            try {
                java.sql.Date sqlDate = java.sql.Date.valueOf(details.getDate());
                java.util.Date utilDate = new java.util.Date(sqlDate.getTime());
                ((JSpinner) dpEdit).setValue(utilDate);
            } catch (Exception e) {
                Date minDate = java.sql.Date.valueOf(LocalDate.now().plusDays(1));
                ((JSpinner) dpEdit).setValue(minDate);
            }
            
            String timeStr = details.getTime();
            if (timeStr != null && timeStr.length() >= 5) {
                try {
                    String displayTime = timeStr.substring(0, 5);
                    cbTimeEdit.setSelectedItem(displayTime);
                } catch (Exception e) {
                    System.err.println("Error parsing time: " + timeStr);
                }
            }
        }
        
        refreshEditTimeSlots();
    }

    private void clearEditForm() {
        cbDoctorEdit.setSelectedIndex(-1);
        Date minDate = java.sql.Date.valueOf(LocalDate.now().plusDays(1));
        ((JSpinner) dpEdit).setValue(minDate);
        cbTimeEdit.removeAllItems();
        cbTimeEdit.setEnabled(false);
        tblAppointments.clearSelection();
        btnEdit.setEnabled(false);
        btnEdit.setText("Select an appointment first");
    }

    private void initializeEditForm() {
        clearEditForm();
        loadDoctorsIntoEditCombo();
        
        Date minDate = java.sql.Date.valueOf(LocalDate.now().plusDays(1));
        SpinnerDateModel model = new SpinnerDateModel(minDate, minDate, null, java.util.Calendar.DAY_OF_MONTH);
        JSpinner spinner = new JSpinner(model);
        JSpinner.DateEditor editor = new JSpinner.DateEditor(spinner, "yyyy-MM-dd");
        spinner.setEditor(editor);
        
        if (dpEdit instanceof JSpinner) {
            dpEdit = spinner;
        }
    }

    private void loadDoctorFeedback() {
        drFeedbackModel.setRowCount(0);
        
        List<Appointment.AppointmentDetails> appointments = Appointment.loadAppointmentsForCustomer(currentCustomer.getId());
        
        for (Appointment.AppointmentDetails appt : appointments) {
            String status = appt.getStatus();
            if (status.equalsIgnoreCase("conducting") || 
                status.equalsIgnoreCase("completed") || 
                status.equalsIgnoreCase("booked")) {
                
                String feedback = appt.getFeedback();
                if (feedback == null || feedback.equals("-") || feedback.equals("null") || feedback.isEmpty()) {
                    feedback = "";
                }
                
                drFeedbackModel.addRow(new Object[]{
                    appt.getAppointmentId(),
                    appt.getDate(),
                    appt.getTime(),
                    appt.getDoctorName(),
                    feedback,
                    appt.getCharge()
                });
            }
        }
    }

    private void loadAppointmentsForComments(DefaultTableModel model) {
        model.setRowCount(0);
        
        List<Appointment.AppointmentDetails> appointments = Appointment.loadAppointmentsForCustomer(currentCustomer.getId());
        
        for (Appointment.AppointmentDetails appt : appointments) {
            String status = appt.getStatus();
            if (status.equalsIgnoreCase("conducting") || status.equalsIgnoreCase("completed")) {
                
                String rating = appt.getRating();
                String comment = appt.getComment();
                
                if (rating == null || rating.equals("-") || rating.equals("null") || rating.equals("0") || rating.isEmpty()) {
                    rating = "";
                }
                
                if (comment == null || comment.equals("-") || comment.equals("null") || comment.isEmpty()) {
                    comment = "";
                }
                
                model.addRow(new Object[]{
                    appt.getAppointmentId(),
                    appt.getDate(),
                    appt.getTime(),
                    appt.getDoctorName(),
                    rating,
                    comment
                });
            }
        }
    }

    // =========================
    // Main Entry Point
    // =========================
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            new Customerrr().setVisible(true);
        });
    }
}
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

