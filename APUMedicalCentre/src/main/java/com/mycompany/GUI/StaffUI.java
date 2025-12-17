/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.GUI;
import com.mycompany.staffmodule.Customer;
import com.mycompany.staffmodule.Doctor;
import com.mycompany.staffmodule.Staff;
import com.mycompany.staffmodule.Appointment;
import com.mycompany.staffmodule.Payment;
import com.mycompany.staffmodule.User;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;

import com.raven.datechooser.DateChooser;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author amar2
 */
public class StaffUI extends javax.swing.JFrame {
    //login staff object
    private Staff staffLogin;
    
     // --- Card names ---
    private static final String CARD_PROFILE   = "card_profile";
    private static final String CARD_CUSTOMERS = "card_customers";
    private static final String CARD_APPT      = "card_appointment";
    private static final String CARD_PAYMENTS  = "card_payments";
    private static final String CARD_CONPAYMENTTABLE = "card_conpaymentTable";
    private static final String CARD_CONPAYMENT = "card_conpayment";
//    private static final String CARD_REPORT = "card_report";
    

    // --- Layout host ---
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel cardHost = new JPanel(cardLayout);

    // --- Top / sidebar ---
    private JLabel lblTitle;
    private JButton btnLogout;
    private JButton btnNavProfile, btnNavCustomers, btnNavAppointment, btnNavPayment, btnNavReport;

    // ---------- Own Profile ---------- tfAddress,
    private JTextField tfId, tfName, tfEmail, tfPhone, tfSalary, tfPosition;
    private JPasswordField pfPassword;
    private JButton btnProfileSave, btnProfileCancel;
    private JComboBox tfGender;
    private JSpinner tfAge;
    private JCheckBox OPShowPassword;

    // ---------- Manage Customers (tabs) ----------
    private JTabbedPane customersTabs;

    // Add tab
    private JTextField tfAddName, tfAddEmail, tfAddPhone;
    private JComboBox<String> cbAddRole; // role selection
    private JSpinner tfAddAge;
    private JComboBox tfAddGender;
    private JPasswordField pfAddPassword;
    private JButton btnAddSave, btnAddCancel;
    private JCheckBox AddCustShowPassword;

    // Edit tab
    private JTable tblEditCustomers;
    private DefaultTableModel editModel;
    private JTextField tfEditId, tfEditName, tfEditEmail, tfEditPhone;
    private JPasswordField pfEditPassword;
    private JComboBox<String> cbEditRole;
    private JComboBox<String> tfEditGender;
    private JSpinner tfEditAge;
    private JButton btnEditApply, btnEditBack;
    private JCheckBox editCustShowPassword;

    // Delete tab
    private JTable tblDeleteCustomers;
    private DefaultTableModel deleteModel;
    private JButton btnDeleteDelete, btnDeleteBack;
    private JTextField tfDeleteId;

    // ---------- Appointment form ----------
    private JTabbedPane appointmentTabs;
    
    //add tab
    private JComboBox<String> cbApptCustomer, cbApptDoctor, cbApptTime;
    private JComponent dpApptDate; // spinner calendar-like
    private JTextField dpApptDateCalendar;
    private JButton btnApptConfirm, btnApptCancel;
    private DateChooser dateChooser = new DateChooser();
    private ArrayList<Customer> addApptCust;
    private ArrayList<Doctor> addApptDoc;
    
    //edit tab
    private DefaultTableModel apptEditModel;
    private JTextField apptEditDateCalendar;
    private DateChooser dateChooserEditAppt = new DateChooser();
    private DateChooser dateChooserFilterEditAppt = new DateChooser();
    private JComboBox<String> apptEditCustomer;
    private ArrayList<Customer> editApptCust;
    private ArrayList<Doctor> editApptDocRead;
    private JButton editApptFilter;
    
        //table
    private JTable tblEditAppointment;
        //select
    private JTextField editApptId, editApptDate;
    private JComboBox<String> editApptDoc, editApptTime;
        //submit
    private JButton btnEditApptConfirm, btnEditApptCancel;
    private JTable tblPaymentAppt;
    
            
    
    
    

    // ---------- Payments ----------
    private JComboBox<String> cbPayAppt; // choose appointment to pay
    private JTextField tfPayAmount;
    private JRadioButton rbCash, rbCredit;
    private ButtonGroup grpPayMethod;//don't want
    private JComboBox payMethod;
    
    private JButton btnExportReceiptPdf, payNow; // Confirm removed per request
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(StaffUI.class.getName());
    private DefaultTableModel paymentModel;
    
    //payment page
    private JComboBox<String> payCust;
    private JTable payApptTable;
    private String payApptId;
    private JButton payCancel, payConfirm, payFilter;
    private ArrayList<Customer> payCustList;
    private JTextField payApptIdComment;
    private JTextArea payApptComment;
    //payment page -> pay now
    private JTextField conPayAmount;
    private JComboBox conPayMethod;
    private JButton conPayNow;
    
    //generate receipt
    private String generateReceiptPayId;
    

    /**
     * Creates new form Staff
     */
    public StaffUI(Staff stLogin) {
        staffLogin = stLogin;
        
        initComponents();
        initializeWindow();
        buildUI();
        wireEvents();
        
        
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
private void initializeWindow() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Staff Portal");
//        setSize(700, 520);
        setSize(700, 620);
        setLocationRelativeTo(null);
    }

     private void buildUI() {
        
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(new Color(230, 240, 250));
        setContentPane(root);

        // ---- Top bar ----
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBorder(new EmptyBorder(10, 16, 10, 16));
        topBar.setBackground(new Color(65, 105, 225));
        lblTitle = new JLabel("Staff Portal");
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setFont(lblTitle.getFont().deriveFont(Font.BOLD, 18f));
        btnLogout = new JButton("Logout");
        styleButton(btnLogout, new Color(220, 20, 60));
        topBar.add(lblTitle, BorderLayout.WEST);
        topBar.add(btnLogout, BorderLayout.EAST);

        // ---- Sidebar ----
        JPanel sidebar = new JPanel(new GridLayout(0, 1, 8, 8));
        sidebar.setPreferredSize(new Dimension(210, 700));
        sidebar.setBorder(new EmptyBorder(12, 12, 12, 12));
        sidebar.setBackground(new Color(255, 228, 225));
        btnNavProfile     = new JButton("Own Profile");
        btnNavCustomers   = new JButton("Manage Customers");
        btnNavAppointment = new JButton("Appointment");
        btnNavPayment = new JButton("Payment");
//        btnNavReport = new JButton("Report");
       
        styleButton(btnNavProfile,     new Color(100,149,237));
        styleButton(btnNavCustomers,   new Color(60,179,113));
        styleButton(btnNavAppointment, new Color(255,140,0));
        styleButton(btnNavPayment, new Color(75, 0, 130));
//        styleButton(btnNavReport, new Color(255, 255, 0));
       
        sidebar.add(btnNavProfile);
        sidebar.add(btnNavCustomers);
        sidebar.add(btnNavAppointment);
        sidebar.add(btnNavPayment);
//        sidebar.add(btnNavReport);

        tblPaymentAppt = new JTable(); // Initialize here

        // ---- Cards ----
        cardHost.add(buildProfileCard(),     CARD_PROFILE);
        cardHost.add(buildCustomersCard(),   CARD_CUSTOMERS);
        cardHost.add(buildAppointmentCard(), CARD_APPT);
        cardHost.add(buildPaymentsCard(),    CARD_PAYMENTS);
        cardHost.add(buildConPaymentTableCard(), CARD_CONPAYMENTTABLE);
        cardHost.add(buildConPaymentCard(), CARD_CONPAYMENT);
//        cardHost.add(buildReportCard(), CARD_REPORT);

        // Assemble
        root.add(topBar, BorderLayout.NORTH);
        root.add(sidebar, BorderLayout.WEST);
        root.add(cardHost, BorderLayout.CENTER);

        cardLayout.show(cardHost, CARD_PROFILE);
    }

    // =========================
    // Profile (like previous)
    // =========================
  
    // =========================
    // Manage Customers (tabs: Add / Edit / Delete)
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

        JLabel title = new JLabel("Own Profile");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 16f));

        tfId = new JTextField();
        tfName = new JTextField();
        pfPassword = new JPasswordField();
        tfEmail = new JTextField();
        tfGender = new JComboBox(new String[] {"Male", "Female"});
        tfPhone = new JTextField();
        tfAge = new JSpinner(new SpinnerNumberModel(18, 18, 65, 1));
        tfSalary = new JTextField();
        tfPosition = new JTextField();
//        tfAddress = new JTextField(); tfEditId.setEditable(false);
        OPShowPassword = new JCheckBox("Show Password");
        
        tfId.setText(staffLogin.getId()); tfId.setEditable(false);
        tfName.setText(staffLogin.getName());
        pfPassword.setText(staffLogin.getPassword());
        tfEmail.setText(staffLogin.getEmail());
        tfPhone.setText(staffLogin.getPhoneNum());
        tfAge.setValue(staffLogin.getAge());
        tfSalary.setText(Double.toString(staffLogin.getSalary())); tfSalary.setEditable(false);
        tfPosition.setText(staffLogin.getPosition()); tfPosition.setEditable(false);
        
        if (staffLogin.getGender() != null) {
            if(staffLogin.getGender().equalsIgnoreCase("Male")){
                tfGender.setSelectedItem("Male");
            }else if(staffLogin.getGender().equalsIgnoreCase("Female")){
                tfGender.setSelectedItem("Female");
            }
        }

        

        btnProfileSave = new JButton("Save");
        styleButton(btnProfileSave, new Color(60,179,113));
        btnProfileCancel = new JButton("Cancel");
        styleButton(btnProfileCancel, new Color(220,20,60));

        int y = 0;
        gc.gridx = 0; gc.gridy = y++; gc.gridwidth = 2; p.add(title, gc); gc.gridwidth = 1;
        
        gc.gridx = 0; gc.gridy = y; p.add(new JLabel("ID"), gc);
        gc.gridx = 1; p.add(tfId, gc); y++;

        gc.gridx = 0; gc.gridy = y; p.add(new JLabel("Name"), gc);
        gc.gridx = 1; p.add(tfName, gc); y++;
        
        gc.gridx = 0; gc.gridy = y; p.add(new JLabel("Password"), gc);
        gc.gridx = 1; p.add(pfPassword, gc); y++;
        
        gc.gridx = 1; gc.gridy = y; p.add(OPShowPassword, gc); y++;

        gc.gridx = 0; gc.gridy = y; p.add(new JLabel("Email"), gc);
        gc.gridx = 1; p.add(tfEmail, gc); y++;

        gc.gridx = 0; gc.gridy = y; p.add(new JLabel("Gender"), gc);
        gc.gridx = 1; p.add(tfGender, gc); y++;
        
        gc.gridx = 0; gc.gridy = y; p.add(new JLabel("Phone"), gc);
        gc.gridx = 1; p.add(tfPhone, gc); y++;
        
        gc.gridx = 0; gc.gridy = y; p.add(new JLabel("Age"), gc);
        gc.gridx = 1; p.add(tfAge, gc); y++;
        
        gc.gridx = 0; gc.gridy = y; p.add(new JLabel("Salary"), gc);
        gc.gridx = 1; p.add(tfSalary, gc); y++;
        
        gc.gridx = 0; gc.gridy = y; p.add(new JLabel("Position"), gc);
        gc.gridx = 1; p.add(tfPosition, gc); y++;

//        gc.gridx = 0; gc.gridy = y; p.add(new JLabel("Address"), gc);
//        gc.gridx = 1; p.add(tfAddress, gc); y++;

        

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        actions.setOpaque(false);
        actions.add(btnProfileCancel);
        actions.add(btnProfileSave);
        gc.gridx = 0; gc.gridy = y; gc.gridwidth = 2; p.add(actions, gc);

        return p;
    }
    
    

    // =========================
    // Manage Customers (tabs: Add / Edit / Delete)
    // =========================
    private JPanel buildCustomersCard() {
        JPanel host = new JPanel(new BorderLayout());
        host.setBorder(new EmptyBorder(16,16,16,16));
        host.setBackground(new Color(248,248,255));

        JLabel title = new JLabel("Manage Customers");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 16f));
        host.add(title, BorderLayout.NORTH);

        customersTabs = new JTabbedPane();
        customersTabs.addTab("Add",    buildCustomerAddTab());
        customersTabs.addTab("Edit",   buildCustomerEditTab());
        customersTabs.addTab("Delete", buildCustomerDeleteTab());

        host.add(customersTabs, BorderLayout.CENTER);
        return host;
    }

    // ---- Add tab ----
    private JPanel buildCustomerAddTab() {
        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createTitledBorder("Add Customer"));
        form.setBackground(new Color(245, 245, 245));

        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(8,8,8,8);
        gc.anchor = GridBagConstraints.LINE_START;
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.weightx = 1.0;
        
//        tfAddId = new JTextField(); tfAddId.setEditable(false);
        tfAddName = new JTextField();
        pfAddPassword = new JPasswordField();
        tfAddEmail = new JTextField();
        tfAddGender = new JComboBox(new String[] {"Male", "Female"});
        tfAddPhone = new JTextField();
        tfAddAge = new JSpinner(new SpinnerNumberModel(18, 0, 120, 1));
        AddCustShowPassword = new JCheckBox("Show Password");

        
        
//        cbAddRole = new JComboBox<>(new String[] {"Customer"}); // default role only
//        cbAddRole.setSelectedIndex(0);

        int r = 0;
        //after add cust, generate Id
//        gc.gridx = 0; gc.gridy = r; form.add(new JLabel("ID"), gc);
//        gc.gridx = 1; form.add(tfAddId, gc); r++;

        gc.gridx = 0; gc.gridy = r; form.add(new JLabel("Name"), gc);
        gc.gridx = 1; form.add(tfAddName, gc); r++;
        
        gc.gridx = 0; gc.gridy = r; form.add(new JLabel("Password"), gc);
        gc.gridx = 1; form.add(pfAddPassword, gc); r++;
        
        gc.gridx = 1; gc.gridy = r; form.add(AddCustShowPassword, gc); r++;

        gc.gridx = 0; gc.gridy = r; form.add(new JLabel("Email"), gc);
        gc.gridx = 1; form.add(tfAddEmail, gc); r++;

        gc.gridx = 0; gc.gridy = r; form.add(new JLabel("Gender"), gc);
        gc.gridx = 1; form.add(tfAddGender, gc); r++;

        gc.gridx = 0; gc.gridy = r; form.add(new JLabel("Phone"), gc);
        gc.gridx = 1; form.add(tfAddPhone, gc); r++;

        gc.gridx = 0; gc.gridy = r; form.add(new JLabel("Age"), gc);
        gc.gridx = 1; form.add(tfAddAge, gc); r++;

//        gc.gridx = 0; gc.gridy = r; form.add(new JLabel("Role"), gc);
//        gc.gridx = 1; form.add(cbAddRole, gc); r++;

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        actions.setOpaque(false);
        btnAddCancel = new JButton("Reset");
        styleButton(btnAddCancel, new Color(220, 20, 60));
        btnAddSave = new JButton("Save");
        styleButton(btnAddSave, new Color(46, 139, 87));
        actions.add(btnAddCancel);
        actions.add(btnAddSave);

        gc.gridx = 0; gc.gridy = r; gc.gridwidth = 2; form.add(actions, gc);
        return form;
    }

    // ---- Edit tab ----
    private JPanel buildCustomerEditTab() {
        JPanel root = new JPanel(new BorderLayout(8,8));
        root.setBackground(new Color(255, 250, 240));
        root.setBorder(BorderFactory.createTitledBorder("Edit Customer"));

        // Table (placeholder rows)
        String[] cols = {"ID", "Name", "Password", "Email", "Gender", "Phone", "Age"};
        //read file from customer
        Customer readCust = new Customer();
        ArrayList<Customer> readCustomers = new ArrayList<>();
        readCustomers = readCust.readFile();
        
//        Object[][] data = {
//            {"C001", "Ali", "ali123",  "ali@mail.com", "Male",  "60123456789", "30"},
//            {"C002", "Sara", "sara123", "sara@mail.com", "Female", "60122289693", "28"},
//        };
        editModel = new DefaultTableModel(cols, 0) { @Override public boolean isCellEditable(int r, int c){ return false; } };
        for (Customer cust : readCustomers) {
            Object[] row = {
                cust.getId(),
                cust.getName(),
                cust.getPassword(),
                cust.getEmail(),
                cust.getGender(),
                cust.getPhoneNum(),
                cust.getAge()
            };
            editModel.addRow(row);
        }
        
        tblEditCustomers = new JTable(editModel);
        tblEditCustomers.setRowHeight(22);
        tblEditCustomers.getTableHeader().setBackground(new Color(65, 105, 225));
        tblEditCustomers.getTableHeader().setForeground(Color.WHITE);
        root.add(new JScrollPane(tblEditCustomers), BorderLayout.CENTER);

        // Form under table
        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(new EmptyBorder(8,8,8,8));
        form.setBackground(new Color(250, 250, 210));
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(6,6,6,6);
        gc.anchor = GridBagConstraints.LINE_START;
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.weightx = 1.0;

        tfEditId = new JTextField(); tfEditId.setEditable(false); //cannot edit
        tfEditName = new JTextField();
        pfEditPassword = new JPasswordField();
        tfEditEmail = new JTextField();
        tfEditGender = new JComboBox<>(new String[] {"Male", "Female"});
        tfEditPhone = new JTextField();
        tfEditAge = new JSpinner(new SpinnerNumberModel(18, 0, 120, 1));
        editCustShowPassword = new JCheckBox("Show Password");
        
//        cbEditRole = new JComboBox<>(new String[] {"Customer"});
//        cbEditRole.setSelectedIndex(0);

        int r = 0;
        gc.gridx = 0; gc.gridy = r; form.add(new JLabel("ID"), gc);
        gc.gridx = 1; form.add(tfEditId, gc); r++;

        gc.gridx = 0; gc.gridy = r; form.add(new JLabel("Name"), gc);
        gc.gridx = 1; form.add(tfEditName, gc); r++;
        
        gc.gridx = 0; gc.gridy = r; form.add(new JLabel("Password"), gc);
        gc.gridx = 1; form.add(pfEditPassword, gc); r++;
        
        gc.gridx = 1; gc.gridy = r; form.add(editCustShowPassword, gc); r++;

        gc.gridx = 0; gc.gridy = r; form.add(new JLabel("Email"), gc);
        gc.gridx = 1; form.add(tfEditEmail, gc); r++;
        
        gc.gridx = 0; gc.gridy = r; form.add(new JLabel("Gender"), gc);
        gc.gridx = 1; form.add(tfEditGender, gc); r++;

        gc.gridx = 0; gc.gridy = r; form.add(new JLabel("Phone"), gc);
        gc.gridx = 1; form.add(tfEditPhone, gc); r++;
        
        gc.gridx = 0; gc.gridy = r; form.add(new JLabel("Age"), gc);
        gc.gridx = 1; form.add(tfEditAge, gc); r++;

//        gc.gridx = 0; gc.gridy = r; form.add(new JLabel("Role"), gc);
//        gc.gridx = 1; form.add(cbEditRole, gc); r++;

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        actions.setOpaque(false);
//        btnEditBack = new JButton("Back");
//        styleButton(btnEditBack, new Color(220,20,60));
        btnEditApply = new JButton("Apply Changes");
        styleButton(btnEditApply, new Color(46,139,87));
//        actions.add(btnEditBack);
        actions.add(btnEditApply);

        gc.gridx = 0; gc.gridy = r; gc.gridwidth = 2; form.add(actions, gc);
        root.add(form, BorderLayout.SOUTH);

        // fill fields when table row selected (design-only)
        tblEditCustomers.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int viewRow = tblEditCustomers.getSelectedRow();
                if (viewRow >= 0) {
                    tfEditId.setText(String.valueOf(editModel.getValueAt(viewRow, 0)));
                    tfEditName.setText(String.valueOf(editModel.getValueAt(viewRow, 1)));
                    pfEditPassword.setText(String.valueOf(editModel.getValueAt(viewRow, 2)));
                    tfEditEmail.setText(String.valueOf(editModel.getValueAt(viewRow, 3)));
                    tfEditGender.setSelectedItem(String.valueOf(editModel.getValueAt(viewRow, 4)));
                    tfEditPhone.setText(String.valueOf(editModel.getValueAt(viewRow, 5)));

                    try {
                        int age = Integer.parseInt(String.valueOf(editModel.getValueAt(viewRow, 6)));
                        tfEditAge.setValue(age);
                    } catch (NumberFormatException ex) {
                        System.err.println("Error parsing age: " + ex.getMessage());
                    }
                }
            }
        });

        return root;
    }

    // ---- Delete tab ----
    private JPanel buildCustomerDeleteTab() {
        JPanel root = new JPanel(new BorderLayout(8,8));
        root.setBackground(new Color(240,255,255));
        root.setBorder(BorderFactory.createTitledBorder("Delete Customer"));

        String[] cols = {"ID", "Name","Password", "Email", "Gender", "Phone", "Age"};
        //read text file from customer
        Customer readCust = new Customer();
        ArrayList<Customer> readCustomers = new ArrayList<>();
        readCustomers = readCust.readFile();
        
//        Object[][] data = {
//            {"C001", "Ali",  "ali123", "ali@mail.com",  "Male" ,"60123456789", "30"},
//            {"C002", "Sara", "sara123" ,"sara@mail.com", "Female" ,"60122289693", "28"},
//        };
        deleteModel = new DefaultTableModel(cols, 0) { @Override public boolean isCellEditable(int r, int c){ return false; } };
        for (Customer cust : readCustomers) {
            Object[] row = {
                cust.getId(),
                cust.getName(),
                cust.getPassword(),
                cust.getEmail(),
                cust.getGender(),
                cust.getPhoneNum(),
                cust.getAge()
            };
            deleteModel.addRow(row);
        }
//        deleteModel = new DefaultTableModel(data, cols) { @Override public boolean isCellEditable(int r, int c){ return false; } };
        tblDeleteCustomers = new JTable(deleteModel);
        tblDeleteCustomers.setRowHeight(22);
        tblDeleteCustomers.getTableHeader().setBackground(new Color(65, 105, 225));
        tblDeleteCustomers.getTableHeader().setForeground(Color.WHITE);
        root.add(new JScrollPane(tblDeleteCustomers), BorderLayout.CENTER);
        
        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(new EmptyBorder(8,8,8,8));
        form.setBackground(new Color(250, 250, 210));
        
        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        actions.setOpaque(false);
//        btnDeleteBack = new JButton("Back");
//        styleButton(btnDeleteBack, new Color(0, 0, 255));
        btnDeleteDelete = new JButton("Delete");
        styleButton(btnDeleteDelete, new Color(220,20,60));
//        actions.add(btnDeleteBack);
        actions.add(btnDeleteDelete);
        
        tfDeleteId = new JTextField();
        
        root.add(actions, BorderLayout.SOUTH);
        
        tblDeleteCustomers.getSelectionModel().addListSelectionListener(e -> {
                if (!e.getValueIsAdjusting()) {
                    int viewRow = tblDeleteCustomers.getSelectedRow();
                    if (viewRow >= 0) {
                        tfDeleteId.setText(String.valueOf(deleteModel.getValueAt(viewRow, 0))); 
                    }
                }
            });

        return root;
    }

    // =========================
    // Appointment (simple form)
    // =========================
    private JPanel buildAppointmentCard() {
        JPanel host = new JPanel(new GridBagLayout());
        host.setBorder(new EmptyBorder(16,16,16,16));
        host.setBackground(new Color(224,255,255));
        
        // Create GridBagConstraints instance
        GridBagConstraints gc = new GridBagConstraints();
        gc.fill = GridBagConstraints.BOTH; // Make the component fill the available space
        gc.weightx = 1.0; // Allow it to grow horizontally
        gc.weighty = 1.0; // Allow it to grow vertically
        
        appointmentTabs = new JTabbedPane();
        appointmentTabs.addTab("Add",    buildAppointmentAddTab());
        appointmentTabs.addTab("Edit",   buildAppointmentEditTab());

        host.add(appointmentTabs, gc);

        

        return host;
    }
    // Add tab
    private JPanel buildAppointmentAddTab(){
        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createTitledBorder("Create Appointment for Customer"));
        form.setBackground(new Color(245, 245, 245));
        
        
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(8,8,8,8);
        gc.anchor = GridBagConstraints.LINE_START;
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.weightx = 1.0;
        
//        private DateChooser chDate = new DateChooser();

        JLabel title = new JLabel("Create Appointment");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 16f));

//        cbApptCustomer = new JComboBox<>(new DefaultComboBoxModel<>());
//        cbApptCustomer = new JComboBox<>(new String[]{"C001(Ali)", "C002(Sara)"});
//        cbApptCustomer.setPrototypeDisplayValue("Select Customer...");
//        cbApptCustomer.setSelectedIndex(-1);
        cbApptCustomer = new JComboBox<>();
        refreshCustComboBox();

//        cbApptSpecialization = new JComboBox<>(new DefaultComboBoxModel<>());
//        cbApptSpecialization.setPrototypeDisplayValue("Select Specialization...");
//        cbApptSpecialization.setSelectedIndex(-1);

//        cbApptDoctor = new JComboBox<>(new DefaultComboBoxModel<>());
//        cbApptDoctor = new JComboBox<>(new String[]{"Lee(General Doctor)", "Nicole(Oncologist)"});
//        cbApptDoctor.setPrototypeDisplayValue("Select Doctor...");
//        cbApptDoctor.setSelectedIndex(-1);
          cbApptDoctor = new JComboBox<>();
        refreshDocComboBox();

        cbApptTime = new JComboBox<>(new String[]{"Select Time...", "09:00 - 10:00","10:00 - 11:00","11:00 - 12:00", "13:00 - 14:00", "14:00 - 15:00","15:00 - 16:00","16:00 - 17:00"});
        cbApptTime.setSelectedIndex(0);
        dpApptDate = createDateSpinner(null); // spinner-like date
        dpApptDateCalendar = new JTextField();
        dateChooser.setTextField(dpApptDateCalendar);

        btnApptCancel = new JButton("Cancel");
        styleButton(btnApptCancel, new Color(220,20,60));
        btnApptConfirm = new JButton("Confirm");
        styleButton(btnApptConfirm, new Color(46,139,87));

        int y = 0;
        gc.gridx = 0; gc.gridy = y++; gc.gridwidth = 2; form.add(title, gc); gc.gridwidth = 1;

        gc.gridx = 0; gc.gridy = y; form.add(new JLabel("Customer"), gc);
        gc.gridx = 1; form.add(cbApptCustomer, gc); y++;
        
//        gc.gridx = 0; gc.gridy = y; form.add(new JLabel("Date"), gc);
//        gc.gridx = 1; form.add(dpApptDate, gc); y++;
        
        gc.gridx = 0; gc.gridy = y; form.add(new JLabel("Date"), gc);
        gc.gridx = 1; form.add(dpApptDateCalendar, gc); y++;

//        gc.gridx = 0; gc.gridy = y; form.add(new JLabel("Specialization"), gc);
//        gc.gridx = 1; form.add(cbApptSpecialization, gc); y++;

        gc.gridx = 0; gc.gridy = y; form.add(new JLabel("Doctor"), gc);
        gc.gridx = 1; form.add(cbApptDoctor, gc); y++;

        gc.gridx = 0; gc.gridy = y; form.add(new JLabel("Time"), gc);
        gc.gridx = 1; form.add(cbApptTime, gc); y++;

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        actions.setOpaque(false);
        actions.add(btnApptCancel);
        actions.add(btnApptConfirm);
        gc.gridx = 0; gc.gridy = y; gc.gridwidth = 2; form.add(actions, gc);
        
        return form;
        
    }
private JPanel buildAppointmentEditTab() {
    JPanel root = new JPanel(new BorderLayout(8, 8));
    root.setBackground(new Color(255, 250, 240));
    root.setBorder(BorderFactory.createTitledBorder("Edit Customer Appointment"));

    JPanel filterForm = new JPanel(new GridBagLayout());
    filterForm.setBackground(new Color(245, 245, 245));

    GridBagConstraints gc = new GridBagConstraints();
    gc.insets = new Insets(8, 8, 8, 8);
    gc.anchor = GridBagConstraints.LINE_START;
    gc.fill = GridBagConstraints.HORIZONTAL;
    gc.weightx = 1.0;

    apptEditDateCalendar = new JTextField();
    dateChooserFilterEditAppt.setTextField(apptEditDateCalendar);

    apptEditCustomer = new JComboBox<>();
    refreshEditApptCustCombo();

    editApptFilter = new JButton("Search");
    styleButton(editApptFilter, new Color(65, 105, 225));

    // filter form
    int y = 0;
    gc.gridx = 0; gc.gridy = y; filterForm.add(new JLabel("Date"), gc);
    gc.gridx = 1; filterForm.add(apptEditDateCalendar, gc); y++;

    gc.gridx = 0; gc.gridy = y; filterForm.add(new JLabel("Customer"), gc);
    gc.gridx = 1; filterForm.add(apptEditCustomer, gc); y++;

    gc.gridx = 0; gc.gridy = y; gc.gridwidth = 2;
    gc.fill = GridBagConstraints.NONE;
    gc.anchor = GridBagConstraints.EAST;
    filterForm.add(editApptFilter, gc);

    gc.gridwidth = 1;
    gc.fill = GridBagConstraints.HORIZONTAL;

    root.add(filterForm, BorderLayout.NORTH);

    // ===== Table setup =====
    String[] cols = {"ID", "Date", "Time", "Doctor", "Status", "Assigned By", "Customer"};

    Appointment readAppt = new Appointment();
    ArrayList<Appointment> readAppointments = readAppt.editApptFilter(
            changeDateFormat(apptEditDateCalendar.getText()),
            separateCust(String.valueOf(apptEditCustomer.getSelectedItem()))
    );

    apptEditModel = new DefaultTableModel(cols, 0) {
        @Override public boolean isCellEditable(int r, int c) {
            return false;
        }
    };

    // Populate rows safely
    for (Appointment appts : readAppointments) {
        Customer cust = appts.getCustomer();
        String custDisplay = (cust != null)
                ? cust.getId() + "(" + cust.getName() + ")"
                : "N/A";

        Object[] row = {
            appts.getApptId(),
            changeDateFormat(String.valueOf(appts.getDate())),
            timeBackFormat(String.valueOf(appts.getTime())),
            appts.getDoctor().getName() + "(" + appts.getDoctor().getPosition() + ")",
            appts.getStatus(),
            (appts.getAssignedBy() != null ? appts.getAssignedBy().getName() : "-"),
            custDisplay
        };
        apptEditModel.addRow(row);
    }

    tblEditAppointment = new JTable(apptEditModel);
    tblEditAppointment.setRowHeight(22);
    tblEditAppointment.getTableHeader().setBackground(new Color(65, 105, 225));
    tblEditAppointment.getTableHeader().setForeground(Color.WHITE);
    root.add(new JScrollPane(tblEditAppointment), BorderLayout.CENTER);

    // ===== Form below table =====
    JPanel editForm = new JPanel(new GridBagLayout());
    editForm.setBackground(new Color(245, 245, 245));

    editApptId = new JTextField(15);
    editApptId.setEditable(false);
    editApptDate = new JTextField(15);
    dateChooserEditAppt.setTextField(editApptDate);

    editApptDoc = new JComboBox<>();
    refreshEditApptDocComboBox();

    editApptTime = new JComboBox<>(new String[]{
        "09:00 - 10:00", "10:00 - 11:00", "11:00 - 12:00",
        "13:00 - 14:00", "14:00 - 15:00", "15:00 - 16:00", "16:00 - 17:00"
    });

    int y2 = 0;
    gc.gridx = 0; gc.gridy = y2; editForm.add(new JLabel("Appointment Id"), gc);
    gc.gridx = 1; editForm.add(editApptId, gc); y2++;

    gc.gridx = 0; gc.gridy = y2; editForm.add(new JLabel("Date"), gc);
    gc.gridx = 1; editForm.add(editApptDate, gc); y2++;

    gc.gridx = 0; gc.gridy = y2; editForm.add(new JLabel("Doctor"), gc);
    gc.gridx = 1; editForm.add(editApptDoc, gc); y2++;

    gc.gridx = 0; gc.gridy = y2; editForm.add(new JLabel("Time"), gc);
    gc.gridx = 1; editForm.add(editApptTime, gc); y2++;

    root.add(editForm, BorderLayout.SOUTH);

    // ===== Fill fields on row select =====
    tblEditAppointment.getSelectionModel().addListSelectionListener(e -> {
        if (!e.getValueIsAdjusting()) {
            int viewRow = tblEditAppointment.getSelectedRow();
            if (viewRow >= 0) {
                editApptId.setText(String.valueOf(apptEditModel.getValueAt(viewRow, 0)));
                editApptDate.setText(String.valueOf(apptEditModel.getValueAt(viewRow, 1)));
                editApptTime.setSelectedItem(String.valueOf(apptEditModel.getValueAt(viewRow, 2)));
                editApptDoc.setSelectedItem(String.valueOf(apptEditModel.getValueAt(viewRow, 3)));
            }
        }
    });

    // ===== Action buttons =====
    btnEditApptCancel = new JButton("Cancel Appointment");
    styleButton(btnEditApptCancel, new Color(220, 20, 60));
    btnEditApptConfirm = new JButton("Confirm");
    styleButton(btnEditApptConfirm, new Color(46, 139, 87));

    JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
    actions.setOpaque(false);
    actions.add(btnEditApptCancel);
    actions.add(btnEditApptConfirm);
    gc.gridx = 0; gc.gridy = y2; gc.gridwidth = 2; gc.anchor = GridBagConstraints.LINE_END;
    editForm.add(actions, gc);

    return root;
}
    //Edit tab
//    private JPanel buildAppointmentEditTab(){
//        JPanel root = new JPanel(new BorderLayout(8,8));
//        root.setBackground(new Color(255, 250, 240));
//        root.setBorder(BorderFactory.createTitledBorder("Edit Customer Appointment"));
//
//        JPanel filterForm  = new JPanel(new GridBagLayout());
//        filterForm .setBackground(new Color(245, 245, 245));
//        
//        GridBagConstraints gc = new GridBagConstraints();
//        gc.insets = new Insets(8,8,8,8);
//        gc.anchor = GridBagConstraints.LINE_START;
//        gc.fill = GridBagConstraints.HORIZONTAL;
//        gc.weightx = 1.0;
//        
//        
//        apptEditDateCalendar = new JTextField();
//        dateChooserFilterEditAppt.setTextField(apptEditDateCalendar);
//        
////        apptEditCustomer = new JComboBox<>(new String[]{"C001(Ali)", "C002(Sara)"});
////        apptEditCustomer.setPrototypeDisplayValue("Select Customer...");
////        apptEditCustomer.setSelectedIndex(-1);
//
//        apptEditCustomer = new JComboBox<>();
//        refreshEditApptCustCombo();
//        
//        editApptFilter = new JButton("Search");
//        styleButton(editApptFilter, new Color(65,105,225));
//        
//        
//        //filter
//        int y = 0;
//        
//        gc.gridx = 0; gc.gridy = y; filterForm.add(new JLabel("Date"), gc);
//        gc.gridx = 1; filterForm.add(apptEditDateCalendar, gc); y++;
//        
//        gc.gridx = 0; gc.gridy = y; filterForm.add(new JLabel("Customer"), gc);
//        gc.gridx = 1; filterForm.add(apptEditCustomer, gc); y++;
//        
//        //add search button below customer combo box
//        // Use the next row (y) and set gridwidth to 2 to make it span both columns.
//        gc.gridx = 0; 
//        gc.gridy = y; 
//        gc.gridwidth = 2; // Span across two columns
//        gc.fill = GridBagConstraints.NONE; // Do not stretch
//        gc.anchor = GridBagConstraints.EAST; // Align to the right
//        filterForm.add(editApptFilter, gc);
//
//        // Reset gridwidth for other components
//        gc.gridwidth = 1; 
//        gc.fill = GridBagConstraints.HORIZONTAL;
//        
//        // Add the form panel to the top of the main root panel
//        root.add(filterForm, BorderLayout.NORTH);
//        
//        // table
//        String[] cols = {"ID", "Date", "Time", "Doctor", "Status", "Assigned By", "Customer"};
////        Object[][] data = {
////            {"A001", "05-09-2025", "11:00",  "Ahmad(General Doctor)", "booked",  "Ali"},
////            {"A002", "07-09-2025", "14:00", "Sara(Gastroenterologist)", "cancel", "-"},
////        };
//        
//        Appointment readAppt = new Appointment();
//        ArrayList<Appointment> readAppointments = new ArrayList<>();
//        readAppointments = readAppt.editApptFilter(changeDateFormat(apptEditDateCalendar.getText()), separateCust(String.valueOf(apptEditCustomer.getSelectedItem())));
//        
//        apptEditModel = new DefaultTableModel(cols, 0) {
//            @Override
//            public boolean isCellEditable(int r, int c) {
//                return false;
//            }
//        };
//        
//        //middle
////        editModel = new DefaultTableModel(data, cols) { @Override public boolean isCellEditable(int r, int c){ return false; } };
//
//        apptEditModel = new DefaultTableModel(cols, 0) { @Override public boolean isCellEditable(int r, int c){ return false; } };
//        for (Appointment appts : readAppointments) {
//            if(appts.getAssignedBy() == null){
//                Object[] row = {
//                    appts.getApptId(),
//                    changeDateFormat(String.valueOf(appts.getDate())),
//                    timeBackFormat(String.valueOf(appts.getTime())),
//                    appts.getDoctor().getName() + "(" + appts.getDoctor().getPosition() + ")",
//                    appts.getStatus(),
//                    "-",
//                    appts.getCustomer().getId() + "(" + appts.getCustomer().getName() + ")"
//                };
//                apptEditModel.addRow(row);
//
//            } else{
//                Object[] row = {
//                    appts.getApptId(),
//                    changeDateFormat(String.valueOf(appts.getDate())),
//                    timeBackFormat(String.valueOf(appts.getTime())),
//                    appts.getDoctor().getName() + "(" + appts.getDoctor().getPosition() + ")",
//                    appts.getStatus(),
//                    appts.getAssignedBy().getName(),
//                    appts.getCustomer().getId() + "(" + appts.getCustomer().getName() + ")"
//                };
//                apptEditModel.addRow(row);
//
//            }
//            
//            
//        }
//        
//        tblEditAppointment = new JTable(apptEditModel);
//        tblEditAppointment.setRowHeight(22);
//        tblEditAppointment.getTableHeader().setBackground(new Color(65, 105, 225));
//        tblEditAppointment.getTableHeader().setForeground(Color.WHITE);
//        root.add(new JScrollPane(tblEditAppointment), BorderLayout.CENTER);
//        
//        //below
//        JPanel editForm = new JPanel(new GridBagLayout());
//        editForm.setBackground(new Color(245, 245, 245));
//    
//        //initialise with random value first, later replace
//        editApptId = new JTextField(15);
//        editApptId.setEditable(false);
//        editApptDate = new JTextField(15);
//        dateChooserEditAppt.setTextField(editApptDate);
////        editApptDoc = new JComboBox<>(new String[]{"Lee(General Doctor)", "Nicole(Oncologist)"});
//        editApptDoc = new JComboBox<>();
//        refreshEditApptDocComboBox();
//        editApptTime = new JComboBox<>(new String[]{"09:00 - 10:00", "10:00 - 11:00", "11:00 - 12:00", "13:00 - 14:00", "14:00 - 15:00", "15:00 - 16:00", "16:00 - 17:00"});
//        editApptId.setEditable(false);
//        
//        int y2 = 0;
//        
//        gc.gridx = 0; gc.gridy = y2; editForm.add(new JLabel("Appointment Id"), gc);
//        gc.gridx = 1; editForm.add(editApptId, gc); y2++;
//        
//        gc.gridx = 0; gc.gridy = y2; editForm.add(new JLabel("Date"), gc);
//        gc.gridx = 1; editForm.add(editApptDate, gc); y2++;
//        
//        gc.gridx = 0; gc.gridy = y2; editForm.add(new JLabel("Doctor"), gc);
//        gc.gridx = 1; editForm.add(editApptDoc, gc); y2++;
//        
//        gc.gridx = 0; gc.gridy = y2; editForm.add(new JLabel("Time"), gc);
//        gc.gridx = 1; editForm.add(editApptTime, gc); y2++;
//        
//        root.add(editForm, BorderLayout.SOUTH);
//        
//        // fill fields when table row selected (design-only)
//        tblEditAppointment.getSelectionModel().addListSelectionListener(e -> {
//            if (!e.getValueIsAdjusting()) {
//                int viewRow = tblEditAppointment.getSelectedRow();
//                if (viewRow >= 0) {
//                    editApptId.setText(String.valueOf(apptEditModel.getValueAt(viewRow, 0)));
//                    editApptDate.setText(String.valueOf(apptEditModel.getValueAt(viewRow, 1)));
//                    editApptTime.setSelectedItem(String.valueOf(apptEditModel.getValueAt(viewRow, 2)));
//                    editApptDoc.setSelectedItem(String.valueOf(apptEditModel.getValueAt(viewRow, 3)));
//                    
//                }
//            }
//        });
//        
//        //submit
//        btnEditApptCancel = new JButton("Cancel Appointment");
//        styleButton(btnEditApptCancel, new Color(220,20,60));
//        btnEditApptConfirm = new JButton("Confirm");
//        styleButton(btnEditApptConfirm, new Color(46,139,87));
//        
//        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
//        actions.setOpaque(false);
//        actions.add(btnEditApptCancel);
//        actions.add(btnEditApptConfirm);
//        gc.gridx = 0; gc.gridy = y2; gc.gridwidth = 2; gc.anchor = GridBagConstraints.LINE_END; editForm.add(actions, gc);
//        
//        
//        return root;
//    }

    // =========================
    // Payments (no confirm button, only export)
    // =========================
    private JPanel buildPaymentsCard() {
        JPanel host = new JPanel(new GridBagLayout());
        host.setBorder(new EmptyBorder(16,16,16,16));
        host.setBackground(new Color(248,248,255));

        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(8,8,8,8);
        gc.anchor = GridBagConstraints.LINE_START;
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.weightx = 1.0;

        JLabel title = new JLabel("Payments (Appointment Fees)");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 16f));

//        cbPayAppt = new JComboBox<>(new DefaultComboBoxModel<>()); // fill later
//        cbPayAppt.setPrototypeDisplayValue("Select Appointment...");
//        cbPayAppt.setSelectedIndex(-1);

        tfPayAmount = new JTextField();
        tfPayAmount.setText("RM 20.00");
        tfPayAmount.setEditable(false);
        
        payMethod = new JComboBox<>(new String[]{"Cash", "QR", "Debit Card", "Credit Card"});

//        rbCash = new JRadioButton("Cash");
//        rbCredit = new JRadioButton("Credit");
//        grpPayMethod = new ButtonGroup();
//        grpPayMethod.add(rbCash);
//        grpPayMethod.add(rbCredit);

        btnExportReceiptPdf = new JButton("Generate Receipt (PDF)");
        styleButton(btnExportReceiptPdf, new Color(100,149,237));
        
        payNow = new JButton("Pay Now");
        styleButton(payNow, new Color(100,149,237));

        int y = 0;
        gc.gridx = 0; gc.gridy = y++; gc.gridwidth = 2; host.add(title, gc); gc.gridwidth = 1;

//        gc.gridx = 0; gc.gridy = y; host.add(new JLabel("Appointment"), gc);
//        gc.gridx = 1; host.add(cbPayAppt, gc); y++;

        gc.gridx = 0; gc.gridy = y; host.add(new JLabel("Amount"), gc);
        gc.gridx = 1; host.add(tfPayAmount, gc); y++;

        gc.gridx = 0; gc.gridy = y; host.add(new JLabel("Method"), gc);
        gc.gridx = 1; host.add(payMethod, gc); y++;
//        JPanel method = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
//        method.setOpaque(false);
//        method.add(rbCash);
//        method.add(rbCredit);
//        gc.gridx = 1; host.add(method, gc); y++;

          //back up
//        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
//        actions.setOpaque(false);
//        actions.add(btnExportReceiptPdf);
//        gc.gridx = 0; gc.gridy = y; gc.gridwidth = 2; host.add(actions, gc);
//        
//        JPanel payBtn = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
//        payBtn.setOpaque(false);
//        payBtn.add(payNow);
//        gc.gridx = 0; gc.gridy = y; gc.gridwidth = 2; host.add(payBtn, gc);
        
        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));        actions.setOpaque(false);

        payNow = new JButton("Pay Now");
        styleButton(payNow, new Color(0, 128, 0));
//        actions.add(payNow, BorderLayout.WEST);
        
//        btnExportReceiptPdf = new JButton("Generate Receipt (PDF)");
//        styleButton(btnExportReceiptPdf, new Color(100, 149, 237));
//        actions.add(btnExportReceiptPdf, BorderLayout.EAST);
        
        actions.add(payNow);
//        actions.add(btnExportReceiptPdf);

        gc.gridx = 0;
        gc.gridy = y;
        gc.gridwidth = 2;
        host.add(actions, gc);
        y++;


        return host;
    }
    
    // payment page
    private JPanel buildConPaymentTableCard(){
        JPanel host = new JPanel(new BorderLayout());
        host.setBorder(new EmptyBorder(16,16,16,16));
        host.setBackground(new Color(248,248,255));
        
        JPanel filterForm  = new JPanel(new GridBagLayout());
        filterForm .setBackground(new Color(245, 245, 245));
        
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(8,8,8,8);
        gc.anchor = GridBagConstraints.LINE_START;
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.weightx = 1.0;
        
//        payCust = new JComboBox<>(new String[]{"C001(Ali)", "C002(Sara)"});
//        payCust.setPrototypeDisplpayCustayValue("Select Customer...");
//        payCust.setSelectedIndex(-1);
          payCust = new JComboBox<>();
          payCustComboBox();
          
         payFilter = new JButton("Search");
        styleButton(payFilter, new Color(65,105,225));
        
        //filter
        int y = 0;
        gc.gridx = 0; gc.gridy = y; filterForm.add(new JLabel("Customer"), gc);
        gc.gridx = 1; filterForm.add(payCust, gc); y++;
        
        gc.gridx = 0; 
        gc.gridy = y; 
        gc.gridwidth = 2; // Span across two columns
        gc.fill = GridBagConstraints.NONE; // Do not stretch
        gc.anchor = GridBagConstraints.EAST; // Align to the right
        filterForm.add(payFilter, gc);

        
        // Add the form panel to the top of the main root panel
        host.add(filterForm, BorderLayout.NORTH);
        
        //table
        String[] cols = {"ID", "Date", "Time", "Doctor", "Status", "Consultation Fees", "Customer", "Rating", "Comment"};
//        Object[][] data = {
//            {"A001", "05-09-2025", "11:00",  "Ahmad(General Doctor)", "booked",  "500.00", "cust1"},
//            {"A002", "07-09-2025", "14:00", "Sara(Gastroenterologist)", "cancel", "200.00", "cust2"},
//        };
        Appointment readAppt = new Appointment();
        ArrayList<Appointment> appointments = new ArrayList<>();
        appointments = readAppt.payFilterAppt(separateCust(String.valueOf(payCust.getSelectedItem())));

        paymentModel = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };
        
        paymentApptTable(appointments);
        
        
        //middle
        
        payApptTable = new JTable(paymentModel);
        payApptTable.setRowHeight(22);
        payApptTable.getTableHeader().setBackground(new Color(65, 105, 225));
        payApptTable.getTableHeader().setForeground(Color.WHITE);
        host.add(new JScrollPane(payApptTable), BorderLayout.CENTER);
        
        //create a new panel to hold both comment and pay now button
        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.setOpaque(false);
        
        //view comment
        JPanel commentForm = new JPanel(new GridBagLayout());
        commentForm.setBackground(new Color(245, 245, 245));
        
        payApptComment = new JTextArea(4, 20); // 4 rows, 20 columns
        payApptComment.setEditable(false);
        payApptComment.setLineWrap(true);
        payApptComment.setWrapStyleWord(true);
        JScrollPane commentScrollPane = new JScrollPane(payApptComment);
        
        payApptIdComment = new JTextField();
        payApptIdComment.setEditable(false);
        
        GridBagConstraints gc2 = new GridBagConstraints();
        gc2.insets = new Insets(6,6,6,6);
        gc2.anchor = GridBagConstraints.LINE_START;
        gc2.fill = GridBagConstraints.HORIZONTAL;
        gc2.weightx = 1.0;
        
        int y3 = 0;
        
        gc2.gridx = 0; gc2.gridy = y3; gc2.weightx = 0.1; gc2.fill = GridBagConstraints.NONE; commentForm.add(new JLabel("Appointment Id"), gc2);
        gc2.gridx = 1; gc2.gridy = y3; gc2.weightx = 1.0; gc2.fill = GridBagConstraints.HORIZONTAL; commentForm.add(payApptIdComment, gc2); y3++;
        
        gc2.gridx = 0; gc2.gridy = y3; gc2.weightx = 0.1; gc2.fill = GridBagConstraints.NONE; commentForm.add(new JLabel("Comment"), gc2);
        gc2.gridx = 1; gc2.gridy = y3; gc2.weightx = 1.0; gc2.fill = GridBagConstraints.HORIZONTAL; commentForm.add(commentScrollPane, gc2); y3++;
        
        southPanel.add(commentForm, BorderLayout.NORTH);
        
        //submit
        int y2 = 0;
//        payCancel = new JButton("Cancel");
//        styleButton(payCancel, new Color(220,20,60));
        payConfirm = new JButton("Pay Now");
        styleButton(payConfirm, new Color(46,139,87));
        
        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        actions.setOpaque(false);
//        actions.add(payCancel);
        actions.add(payConfirm);
        gc.gridx = 0; gc.gridy = y2; gc.gridwidth = 2; gc.anchor = GridBagConstraints.LINE_END; 
        southPanel.add(actions, BorderLayout.SOUTH);
        
        host.add(southPanel, BorderLayout.SOUTH);
        
        
        // fill fields when table row selected 
        payApptTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int viewRow = payApptTable.getSelectedRow();
                if (viewRow >= 0) {
                    payApptId = (String.valueOf(paymentModel.getValueAt(viewRow, 0)));
                    payApptIdComment.setText(String.valueOf(paymentModel.getValueAt(viewRow, 0)));
                    conPayAmount.setText("RM " + String.valueOf(paymentModel.getValueAt(viewRow, 5)));
                    System.out.println(payApptId);
                    
                    payApptComment.setText(String.valueOf(paymentModel.getValueAt(viewRow, 8)));
                    
                }
            }
        });
        
        return host;
    }
    private JPanel buildConPaymentCard(){
        JPanel host = new JPanel(new GridBagLayout());
        host.setBorder(new EmptyBorder(16,16,16,16));
        host.setBackground(new Color(248,248,255));

        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(8,8,8,8);
        gc.anchor = GridBagConstraints.LINE_START;
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.weightx = 1.0;

        JLabel title = new JLabel("Payments (Consultation Fees)");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 16f));



        conPayAmount = new JTextField();
//        conPayAmount.setText("RM 20.00");
        conPayAmount.setEditable(false);
        
        conPayMethod = new JComboBox<>(new String[]{"Cash", "QR", "Debit Card", "Credit Card"});



//        btnExportReceiptPdf = new JButton("Generate Receipt (PDF)");
//        styleButton(btnExportReceiptPdf, new Color(100,149,237));
        
        int y = 0;
        gc.gridx = 0; gc.gridy = y++; gc.gridwidth = 2; host.add(title, gc); gc.gridwidth = 1;

        gc.gridx = 0; gc.gridy = y; host.add(new JLabel("Amount"), gc);
        gc.gridx = 1; host.add(conPayAmount, gc); y++;

        gc.gridx = 0; gc.gridy = y; host.add(new JLabel("Method"), gc);
        gc.gridx = 1; host.add(conPayMethod, gc); y++;

        
        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));        actions.setOpaque(false);

        conPayNow = new JButton("Pay Now");
        styleButton(conPayNow, new Color(0, 128, 0));
        
//        btnExportReceiptPdf = new JButton("Generate Receipt (PDF)");
//        styleButton(btnExportReceiptPdf, new Color(100, 149, 237));
        
        actions.add(conPayNow);
//        actions.add(btnExportReceiptPdf);

        gc.gridx = 0;
        gc.gridy = y;
        gc.gridwidth = 2;
        host.add(actions, gc);
        y++;


        return host;
    }
    
    // Report page
//    private JPanel buildReportCard(){
//        JPanel host = new JPanel(new GridBagLayout());
//        host.setBorder(new EmptyBorder(16,16,16,16));
//        host.setBackground(new Color(248,248,255));
//        
//        return host;
//    }

    // =========================
    // Helpers
    // =========================
    private void styleButton(JButton b, Color bg) {
        b.setBackground(bg);
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createLineBorder(new Color(30,30,30)));
    }

    private JComponent createDateSpinner(java.util.Date minDate) {
        java.util.Date now = new java.util.Date();
        java.util.Date initial = (minDate != null && now.before(minDate)) ? minDate : now;
        SpinnerDateModel model = new SpinnerDateModel(initial, minDate, null, java.util.Calendar.DAY_OF_MONTH);
        JSpinner spinner = new JSpinner(model);
        JSpinner.DateEditor editor = new JSpinner.DateEditor(spinner, "yyyy-MM-dd");
        spinner.setEditor(editor);
        return spinner;
    }
    
    //refresh customer table
    public void refreshCustomerTable() {
        // Clear all existing rows from the table model
        editModel.setRowCount(0);

        // Re-read customers from the file
        Customer readCust = new Customer();
        ArrayList<Customer> readCustomers = readCust.readFile();

        // Add the refreshed data to the table
        for (Customer cust : readCustomers) {
            Object[] row = {
                cust.getId(),
                cust.getName(),
                cust.getPassword(),
                cust.getEmail(),
                cust.getGender(),
                cust.getPhoneNum(),
                cust.getAge()
            };
            editModel.addRow(row);
        }
        
        //same thing in delete table
        
        deleteModel.setRowCount(0);

        
        // Add the refreshed data to the table
        for (Customer cust : readCustomers) {
            Object[] row = {
                cust.getId(),
                cust.getName(),
                cust.getPassword(),
                cust.getEmail(),
                cust.getGender(),
                cust.getPhoneNum(),
                cust.getAge()
            };
            deleteModel.addRow(row);
        }
    }
    //refresh appointment table
    public void refreshApptTable(){
        // Clear all existing rows from the table model 
        apptEditModel.setRowCount(0);

        // Re-read customers from the file
        Appointment readAppt = new Appointment();
        ArrayList<Appointment> readAppointments = readAppt.editApptFilter(changeDateFormat(apptEditDateCalendar.getText()), separateCust(String.valueOf(apptEditCustomer.getSelectedItem())));

        // Add the refreshed data to the table
        for (Appointment appts : readAppointments) {
            if(appts.getAssignedBy() == null){
                Object[] row = {
                    appts.getApptId(),
                    changeDateFormat(String.valueOf(appts.getDate())),
                    timeBackFormat(String.valueOf(appts.getTime())),
                    appts.getDoctor().getName() + "(" + appts.getDoctor().getPosition() + ")",
                    appts.getStatus(),
                    "-",
                    appts.getCustomer().getId() + "(" + appts.getCustomer().getName() + ")"
                };
                apptEditModel.addRow(row);

            } else{
                Object[] row = {
                    appts.getApptId(),
                    changeDateFormat(String.valueOf(appts.getDate())),
                    timeBackFormat(String.valueOf(appts.getTime())),
                    appts.getDoctor().getName() + "(" + appts.getDoctor().getPosition() + ")",
                    appts.getStatus(),
                    appts.getAssignedBy().getName(),
                    appts.getCustomer().getId() + "(" + appts.getCustomer().getName() + ")"
                };
                apptEditModel.addRow(row);

            }
        }
    }
    //filter edit appointment table
    public void editApptFilterSearch(ArrayList<Appointment> appointments){
        // Clear all existing rows from the table model
        apptEditModel.setRowCount(0);

        // Re-read customers from the file
        

        // Add the refreshed data to the table
        for (Appointment appts : appointments) {
            if(appts.getAssignedBy() == null){
                Object[] row = {
                    appts.getApptId(),
                    changeDateFormat(String.valueOf(appts.getDate())),
                    timeBackFormat(String.valueOf(appts.getTime())),
                    appts.getDoctor().getName() + "(" + appts.getDoctor().getPosition() + ")",
                    appts.getStatus(),
                    "-",
                    appts.getCustomer().getId() + "(" + appts.getCustomer().getName() + ")"
                };
                apptEditModel.addRow(row);

            } else{
                Object[] row = {
                    appts.getApptId(),
                    changeDateFormat(String.valueOf(appts.getDate())),
                    timeBackFormat(String.valueOf(appts.getTime())),
                    appts.getDoctor().getName() + "(" + appts.getDoctor().getPosition() + ")",
                    appts.getStatus(),
                    appts.getAssignedBy().getName(),
                    appts.getCustomer().getId() + "(" + appts.getCustomer().getName() + ")"
                };
                apptEditModel.addRow(row);

            }
        }
    }
    
   
    //payment page appointment table
//   private void paymentApptTable(List<Appointment> readAppointments)
// {
//    // Define column headers
//    String[] cols = {"ID", "Date", "Time", "Doctor", "Status", "Assigned By", "Customer"};
//
//    // Create a non-editable model
//    DefaultTableModel paymentModel = new DefaultTableModel(cols, 0) {
//        @Override
//        public boolean isCellEditable(int row, int col) {
//            return false;
//        }
//    };
//
//    // Loop through appointments safely
//    for (Appointment appts : readAppointments) {
//        // Safely handle Customer (can be null)
//        Customer cust = appts.getCustomer();
//        String custDisplay = (cust != null)
//                ? cust.getId() + " (" + cust.getName() + ")"
//                : "N/A";
//
//        // Safely handle AssignedBy (can be null)
//        String assignedByName = (appts.getAssignedBy() != null)
//                ? appts.getAssignedBy().getName()
//                : "-";
//
//        Object[] row = {
//            appts.getApptId(),
//            changeDateFormat(String.valueOf(appts.getDate())),
//            timeBackFormat(String.valueOf(appts.getTime())),
//            appts.getDoctor().getName() + " (" + appts.getDoctor().getPosition() + ")",
//            appts.getStatus(),
//            assignedByName,
//            custDisplay
//        };
//
//        paymentModel.addRow(row);
//    }
//
//    // Assign the model to the table (tblPaymentAppt is already initialized)
//    tblPaymentAppt.setModel(paymentModel);
//    tblPaymentAppt.setRowHeight(22);
//    tblPaymentAppt.getTableHeader().setBackground(new Color(65, 105, 225));
//    tblPaymentAppt.getTableHeader().setForeground(Color.WHITE);
//}
public void paymentApptTable(ArrayList<Appointment> appointments) {
    // Clear all existing rows from the table model
    paymentModel.setRowCount(0);

    // Loop through appointments
    for (Appointment appts : appointments) {
        // Safely handle Customer (may be null)
        Customer cust = appts.getCustomer();
        String custDisplay = (cust != null)
                ? cust.getId() + " (" + cust.getName() + ")"
                : "N/A";

        Object[] row = {
            appts.getApptId(),
            changeDateFormat(String.valueOf(appts.getDate())),
            timeBackFormat(String.valueOf(appts.getTime())),
            appts.getDoctor().getName() + " (" + appts.getDoctor().getPosition() + ")",
            appts.getStatus(),
            appts.getConsultationFees(),
            custDisplay,
            appts.getRating(),
            appts.getComment()
        };

        paymentModel.addRow(row);
    }
}


//    public void paymentApptTable(ArrayList<Appointment> appointments){
//        // Clear all existing rows from the table model
//        paymentModel.setRowCount(0);
//
//        // Re-read customers from the file
//        
//
//        // Add the refreshed data to the table
//        for (Appointment appts : appointments) {
//                Object[] row = {
//                    appts.getApptId(),
//                    changeDateFormat(String.valueOf(appts.getDate())),
//                    timeBackFormat(String.valueOf(appts.getTime())),
//                    appts.getDoctor().getName() + "(" + appts.getDoctor().getPosition() + ")",
//                    appts.getStatus(),
//                    appts.getConsultationFees(),
//                    appts.getCustomer().getId() + "(" + appts.getCustomer().getName() + ")",
//                    appts.getRating(),
//                    appts.getComment()
//                };
//                paymentModel.addRow(row);
//
//            
//        }
//    }
    
    //refresh customer combo box    addApptCust
    public void refreshCustComboBox(){
        //add tab customer Combo box
        //clear all existing items from the combo box
        cbApptCustomer.removeAllItems(); 
        
        // Add the placeholder item back
        cbApptCustomer.addItem("Select Customer...");
    
        Customer readCust = new Customer();
        addApptCust = readCust.readFile();
        
        // Loop through the list of customers and add them to the combo box
        for (Customer customer : addApptCust) {
            // Format the string as "ID(Name)"
            String customerString = customer.getId() + "(" + customer.getName() + ")";
            cbApptCustomer.addItem(customerString);
        }
        
        
        // Set the selected index to -1 to show the placeholder initially
        cbApptCustomer.setSelectedIndex(0);
   
    }
    
    //refresh doctor combo box      addApptDoc
    public void refreshDocComboBox(){
        //clear all existing items from the combo box
        cbApptDoctor.removeAllItems(); 
        
        // Add the placeholder item back
        cbApptDoctor.addItem("Select Doctor...");
    
        Doctor readDoc = new Doctor("Appointment.txt");
        addApptDoc = readDoc.readFile();
        
        // Loop through the list of customers and add them to the combo box
        for (Doctor doctor : addApptDoc) {
            // Format the string as "ID(Name)"
            String doctorString = doctor.getName() + "(" + doctor.getPosition() + ")";
            cbApptDoctor.addItem(doctorString);
        }
        cbApptDoctor.setSelectedIndex(0);
    }
    
    //refresh time combo box
    public void refreshTimeComboBox(){
        cbApptTime.setSelectedIndex(0);
    }
    
    //edit appointment customer combo box
    public void refreshEditApptCustCombo(){
            //clear all existing items from the combo box
            apptEditCustomer.removeAllItems(); 

            // Add the placeholder item back
            apptEditCustomer.addItem("All Customer");

            Customer readCust = new Customer();
            editApptCust = readCust.readFile();

            // Loop through the list of customers and add them to the combo box
            for (Customer customer : editApptCust) {
                // Format the string as "ID(Name)"
                String customerString = customer.getId() + "(" + customer.getName() + ")";
                apptEditCustomer.addItem(customerString);
            }


            // Set the selected index to -1 to show the placeholder initially
            apptEditCustomer.setSelectedIndex(0);

        
    }
    
    //edit appointment doctor combo box
    public void refreshEditApptDocComboBox(){
        editApptDoc.removeAllItems(); 
        Doctor readDoc = new Doctor("Appointment.txt");
            editApptDocRead = readDoc.readFile();

            // Loop through the list of customers and add them to the combo box
            for (Doctor doctor : editApptDocRead) {
                // Format the string as "ID(Name)"
                String doctorString = doctor.getName() + "(" + doctor.getPosition() + ")";
                editApptDoc.addItem(doctorString);
            }


            // Set the selected index to -1 to show the placeholder initially
            editApptDoc.setSelectedIndex(-1);
    }
    
    public void payCustComboBox(){
        //clear all existing items from the combo box
            payCust.removeAllItems(); 

            // Add the placeholder item back
            payCust.addItem("All Customer");

            Customer readCust = new Customer();
            payCustList = readCust.readFile();

            // Loop through the list of customers and add them to the combo box
            for (Customer customer : payCustList) {
                // Format the string as "ID(Name)"
                String customerString = customer.getId() + "(" + customer.getName() + ")";
                payCust.addItem(customerString);
            }


            // Set the selected index to -1 to show the placeholder initially
            payCust.setSelectedIndex(0);
    }
    //customer seperator combo box
    public String separateCust(String custString){
        String custId = "";
//        custString = String.valueOf(cbApptCustomer.getSelectedItem()) ;
        
        // Check if the selected item is not null and not the placeholder
        if (custString != null && !custString.equals("Select Customer...")) {
            if(custString.equalsIgnoreCase("all")){
                return custString;
            }
            // Find the index of the opening parenthesis
            int startIndex = custString.indexOf("(");

            // If a parenthesis is found, extract the substring before it
            if (startIndex != -1) {
                custId = custString.substring(0, startIndex);
                // Now you have the customer ID in the 'custId' variable.
                // You can print it to verify.
                System.out.println("Extracted Customer ID: " + custId);
            }
        }
        return custId;
    }
    
    //doctor seperator combo box
    public String separateDoctor(String docString){
        String docId = "";
        
      
        // Check if a valid item is selected
        if (docString != null && !docString.isEmpty()) {
            // Extract the doctor's name from the string, which is the part before the '('
            int parenthesisIndex = docString.indexOf("(");
            if (parenthesisIndex != -1) {
                String selectedName = docString.substring(0, parenthesisIndex);

                // Read the list of doctors from the file
                Doctor readDoc = new Doctor("Appointment.txt");
                ArrayList<Doctor> doctors = readDoc.readFile();

                // Loop through the list to find the matching name
                for (Doctor doctor : doctors) {
                    if (doctor.getName().equals(selectedName)) {
                        // Found a match, get the ID and break the loop
                        docId = doctor.getId();
                        break; 
                    }
                }
            }
        }

        // Now 'docId' holds the ID of the selected doctor.
        // You can use it as needed, for example, by printing it.
        if (docId != null) {
            System.out.println("The ID of the selected doctor is: " + docId);
        } else {
            System.out.println("No matching doctor found or selection is invalid.");
        }
        
        return docId;
    }
    //change 05-09-2025 to 2025-05-09 vice versa
    public String changeDateFormat(String wrongFormat){
        // Split the date string at the hyphens.
        // For "05-09-2025", this creates an array ["05", "09", "2025"].
        String[] parts = wrongFormat.split("-");

        // Check if the split was successful and there are 3 parts.
        if (parts.length == 3) {
            // Reassemble the parts in the YYYY-MM-DD format.
            // parts[2] is "2025", parts[1] is "09", parts[0] is "05".
            String correctFormat = parts[2] + "-" + parts[1] + "-" + parts[0];
            return correctFormat;
        }
        // Return null or an empty string if the format is invalid.
        return null;
    }
    
//    change 09:00 - 10:00 to 09:00:00
    public String changeTimeFormat(String wrongFormat){
        // Find the index of the first space.
        // For "09:00 - 10:00", this is at index 5.
        int spaceIndex = wrongFormat.indexOf(" ");

        // Check if a space was found.
        if (spaceIndex != -1) {
            // Extract the substring before the space ("09:00").
            String startTime = wrongFormat.substring(0, spaceIndex);

            // Append ":00" for the seconds.
            String correctFormat = startTime + ":00";
            return correctFormat;
        }
        // Return null or an empty string if the format is invalid.
        return null;
    }
    //change 09:00:00 to 09:00 - 10:00
    public String timeBackFormat(String textFileFormat){
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
    
    public double removeRM(String price){
        // example of String "RM 0.0"
        // Check if the string is not null and starts with "RM "
        if (price != null && price.startsWith("RM ")) {
            // Remove the "RM " prefix by getting a substring starting from index 3
            String priceWithoutRM = price.substring(3);

            try {
                // Convert the remaining string to a double and return it
                return Double.parseDouble(priceWithoutRM);
            } catch (NumberFormatException e) {
                // Handle the case where the string cannot be parsed into a double
                System.err.println("Error: The string '" + price + "' does not contain a valid number after 'RM '.");
                return 0.0; // Or throw an exception, depending on your needs
            }
        } else {
            // Handle the case where the input string is invalid
            System.err.println("Error: The string '" + price + "' is not in the expected format (e.g., 'RM 0.0').");
            return 0.0; // Or throw an exception
        }
    }
    
    //generate Receipt
    public void generateReceiptFrame(String payId) throws FileNotFoundException{
//        JFrame grFrame = new JFrame("Generate Receipt");
//        grFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        grFrame.setSize(400, 300);
//        grFrame.setVisible(true);
        
        int confirmationResult = JOptionPane.showConfirmDialog(
                null,
                "Are you sure you want to Generate Receipt?",
                "Confirm Generate Receipt",
                JOptionPane.YES_NO_OPTION
        );
        
        if(confirmationResult == JOptionPane.YES_OPTION){
            JOptionPane.showMessageDialog(null, "Receipt Generated");
            Payment generateReceipt = new Payment();
            generateReceipt.generateReceipt(payId);
        }else {
            JOptionPane.showMessageDialog(null, "No Receipt");

        }
    }
    
    


    // =========================
    // Wire events (navigation + demo placeholders only)
    // =========================
    private void wireEvents() {
        btnNavProfile.addActionListener(e -> cardLayout.show(cardHost, CARD_PROFILE));
        btnNavCustomers.addActionListener(e -> cardLayout.show(cardHost, CARD_CUSTOMERS));
        btnNavAppointment.addActionListener(e -> {
            cardLayout.show(cardHost, CARD_APPT);
            refreshCustComboBox();
            refreshDocComboBox();
        
        });
        btnLogout.addActionListener(e -> {
            // Dispose (close) the current Doctor UI window
            this.dispose();

            // Open the login screen again
            SwingUtilities.invokeLater(() -> new Login().setVisible(true));
        });
//          btnLogout.addActionListener(e -> {
//    int choice = JOptionPane.showConfirmDialog(
//        this,
//        "Are you sure you want to logout?",
//        "Confirm Logout",
//        JOptionPane.YES_NO_OPTION
//    );
//
//    if (choice == JOptionPane.YES_OPTION) {
//        // Close current UI
//        this.dispose();
//
//        // Open Login frame again
//        Login.login();   // ✅ use the static method from your Login class
//    }
//});



        btnNavPayment.addActionListener(e -> {
            cardLayout.show(cardHost, CARD_CONPAYMENTTABLE);
            ArrayList<Appointment> appointments = new ArrayList<>();
            Appointment filterAppt = new Appointment();
            appointments = filterAppt.payFilterAppt(separateCust(String.valueOf(payCust.getSelectedItem())));
            
            paymentApptTable(appointments);
        });
//        btnNavReport.addActionListener(e -> cardLayout.show(cardHost, CARD_REPORT));
        
        //show password own profile
        OPShowPassword.addActionListener(e -> {
            if (OPShowPassword.isSelected()) {
                // Show password
                pfPassword.setEchoChar((char) 0);
            } else {
                // Hide password
                pfPassword.setEchoChar('*');
            }
        });
        //show password add customer
        AddCustShowPassword.addActionListener(e -> {
            if (AddCustShowPassword.isSelected()) {
                // Show password
                pfAddPassword.setEchoChar((char) 0);
            } else {
                // Hide password
                pfAddPassword.setEchoChar('*');
            }
        });
        
        //show password edit customer
        editCustShowPassword.addActionListener(e -> {
            if (editCustShowPassword.isSelected()) {
                // Show password
                pfEditPassword.setEchoChar((char) 0);
            } else {
                // Hide password
                pfEditPassword.setEchoChar('*');
            }
        });
            

        btnLogout.addActionListener(e -> {
    // Dispose (close) the current Doctor UI window
        JOptionPane.showMessageDialog(null, "Logged out");
        dispose();

    // Open the login screen again
//    SwingUtilities.invokeLater(() -> new Login().setVisible(true));
});

        // edit profile
        if (btnProfileSave != null) btnProfileSave.addActionListener(e -> {
            
            Staff saveStaff = new Staff();
            saveStaff.setId(tfId.getText());
            saveStaff.setName(tfName.getText());
            
            User staffValidate = new Staff();
            //change password to character
            char[] passwordChars = pfPassword.getPassword();
            String passwordString = new String(passwordChars);
            saveStaff.setPassword(passwordString);
            
            saveStaff.setEmail(tfEmail.getText());
            saveStaff.setGender(String.valueOf(tfGender.getSelectedItem()));
            saveStaff.setPhoneNum(tfPhone.getText());
            saveStaff.setAge((Integer)tfAge.getValue());
            saveStaff.setSalary(staffLogin.getSalary());
            saveStaff.setPosition(staffLogin.getPosition());
            saveStaff.setRoles("Staff");
            
            if(!saveStaff.emailValidation(tfEmail.getText())){
                JOptionPane.showMessageDialog(this, "Invalid Email !!!");
            }
            else if(!staffValidate.passwordValidation(saveStaff.getPassword())){
                JOptionPane.showMessageDialog(this, "Invalid Password !!! Password must be 8 character, including uppercase, lowercase, special character and number");
            }
            else if(!saveStaff.phoneNumValidation(saveStaff.getPhoneNum())){
                JOptionPane.showMessageDialog(this, "Invalid Phone Number !!!");
            }
            else if(!saveStaff.ageValidation(saveStaff.getAge())){
                JOptionPane.showMessageDialog(this, "Invalid Age !!! Age must between 18 and 65");
            }
            else{
                saveStaff.editProfile(saveStaff);
                JOptionPane.showMessageDialog(this, "Profile saved");
            }
            
            
        
        });
        //reset profile to original one
        if (btnProfileCancel != null) btnProfileCancel.addActionListener(e -> {
            
            tfId.setText(staffLogin.getId()); tfId.setEditable(false);
            tfName.setText(staffLogin.getName());
            pfPassword.setText(staffLogin.getPassword());
            tfEmail.setText(staffLogin.getEmail());
            tfPhone.setText(staffLogin.getPhoneNum());
            tfAge.setValue(staffLogin.getAge());
            tfSalary.setText(Double.toString(staffLogin.getSalary())); tfSalary.setEditable(false);
            tfPosition.setText(staffLogin.getPosition()); tfPosition.setEditable(false);

            if (staffLogin.getGender() != null) {
                if(staffLogin.getGender().equalsIgnoreCase("Male")){
                    tfGender.setSelectedItem("Male");
                }else if(staffLogin.getGender().equalsIgnoreCase("Female")){
                    tfGender.setSelectedItem("Female");
                }
            }
                JOptionPane.showMessageDialog(this, "Cancel");
        
        });
        if (btnProfileCancel != null) btnProfileCancel.addActionListener(e -> cardLayout.show(cardHost, CARD_PROFILE));

        //add customer
        if (btnAddSave != null) btnAddSave.addActionListener(e -> {
            Customer addCust = new Customer();
            
            addCust.setName(tfAddName.getText());
            
            char[] passwordChars = pfAddPassword.getPassword();
            String passwordString = new String(passwordChars);
            addCust.setPassword(passwordString);
            
            addCust.setEmail(tfAddEmail.getText());
            addCust.setAge((Integer)tfAddAge.getValue());
            addCust.setPhoneNum(tfAddPhone.getText());
            addCust.setGender(String.valueOf(tfAddGender.getSelectedItem()));
            
            if(!addCust.emailValidation(addCust.getEmail())){
                JOptionPane.showMessageDialog(this, "Invalid Email !!!");
            }
            else if(!addCust.passwordValidation(addCust.getPassword())){
                JOptionPane.showMessageDialog(this, "Invalid Password !!! Password must be 8 character, including uppercase, lowercase, special character and number");
            }
            else if(!addCust.ageValidation(addCust.getAge())){
                JOptionPane.showMessageDialog(this, "Invalid Age !!! Age must be at least 0");
            }
            else if(!addCust.phoneNumValidation(addCust.getPhoneNum())){
                JOptionPane.showMessageDialog(this, "Invalid Phone Number !!!");
            }
            else{
                try {
                    addCust.AddCust(addCust);
                    JOptionPane.showMessageDialog(this, "Customer added successfully!");
                    refreshCustomerTable();

                    // Clear the form fields for the next entry
                    tfAddName.setText("");
                    pfAddPassword.setText("");
                    tfAddEmail.setText("");
                    tfAddAge.setValue(0);
                    tfAddPhone.setText("");
                    tfAddGender.setSelectedItem("Male");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Error adding customer: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);            
                }
            }
            

            
            
        });
        //cancel add customer, set the textfield to null
        if (btnAddCancel != null) btnAddCancel.addActionListener(e -> {customersTabs.setSelectedIndex(0);
            // Clear the form fields 
                tfAddName.setText("");
                pfAddPassword.setText("");
                tfAddEmail.setText("");
                tfAddAge.setValue(0);
                tfAddPhone.setText("");
                tfAddGender.setSelectedItem("Male");
        });

        //edit customer
        if (btnEditApply != null) btnEditApply.addActionListener(e -> {
            Customer writeCust = new Customer();
            writeCust.setId(tfEditId.getText());
            writeCust.setName(tfEditName.getText());
            writeCust.setEmail(tfEditEmail.getText());
            
            char[] passwordChars = pfEditPassword.getPassword();
            String passwordString = new String(passwordChars);
            writeCust.setPassword(passwordString);
            
            writeCust.setPhoneNum(tfEditPhone.getText());
            writeCust.setGender(String.valueOf(tfEditGender.getSelectedItem()));
            writeCust.setAge((Integer)tfEditAge.getValue());
            
            int selectedRow = tblEditCustomers.getSelectedRow();

            if (selectedRow == -1) {
                // No row is selected, show an error message.
                JOptionPane.showMessageDialog(this, "Please select a row !!!");
            } else {
                if(!writeCust.emailValidation(writeCust.getEmail())){
                    JOptionPane.showMessageDialog(this, "Invalid Email !!!");
                }
                else if(!writeCust.passwordValidation(writeCust.getPassword())){
                    JOptionPane.showMessageDialog(this, "Invalid Password !!! Password must be 8 character, including uppercase, lowercase, special character and number");
                }
                else if(!writeCust.phoneNumValidation(writeCust.getPhoneNum())){
                    JOptionPane.showMessageDialog(this, "Invalid Phone Number !!!");
                }
                else if(!writeCust.ageValidation(writeCust.getAge())){
                    JOptionPane.showMessageDialog(this, "Invalid Age !!! Age must at least 0");
                }
                else{
                    writeCust.editProfile(writeCust);

                    refreshCustomerTable();
                    tfEditId.setText("");
                    tfEditName.setText("");
                        pfEditPassword.setText("");
                        tfEditEmail.setText("");
                        tfEditAge.setValue(0);
                        tfEditPhone.setText("");
                        tfEditGender.setSelectedItem("Male");


                    JOptionPane.showMessageDialog(this, "Changes applied");
                }
            }

            
            
            

        
        });
        //edit customer back
//        if (btnEditBack != null) btnEditBack.addActionListener(e -> customersTabs.setSelectedIndex(1));

        //delete customer
        if (btnDeleteDelete != null) btnDeleteDelete.addActionListener(e -> {
//            String.valueOf(deleteModel.getValueAt(viewRow, 0))
            int confirmDeleteCustResult = JOptionPane.showConfirmDialog(
                    null,
                    "Are you Sure you want to delete this customer?",
                    "Confirm Deletion",
                    JOptionPane.YES_NO_OPTION
            
            );
            
            if(confirmDeleteCustResult == JOptionPane.YES_OPTION){
                int selectedRow = tblDeleteCustomers.getSelectedRow();

                if (selectedRow == -1) {
                    // No row is selected, show an error message.
                    JOptionPane.showMessageDialog(this, "Please select a row !!!");
                } else {
                    Customer deleteCust = new Customer();
                    if(deleteCust.deleteCust(tfDeleteId.getText())){
                        JOptionPane.showMessageDialog(this, "Customer Deleted !!!");
                        refreshCustomerTable();

                    }
                    else{
                        JOptionPane.showMessageDialog(this, "Customer cannot be deleted since (he/she) had made appointment");

                    }
                }
            }else{
                JOptionPane.showMessageDialog(null, "Deletion Cancelled");
            }
            
            
            
            
            


        
        });


        // Appointment: Confirm -> navigate to Payments panel
        if (btnApptConfirm != null) btnApptConfirm.addActionListener(e -> {
            //cbApptCustomer dpApptDateCalendar cbApptDoctor cbApptTime
            Appointment validAppt = new Appointment();
            if(cbApptCustomer.getSelectedItem().equals("Select Customer...")){
                JOptionPane.showMessageDialog(this, "Please Select a Customer !!!");
            }
            else if(cbApptDoctor.getSelectedItem().equals("Select Doctor...")){
                JOptionPane.showMessageDialog(this, "Please Select a Doctor !!!");
            }
            else if(cbApptTime.getSelectedItem().equals("Select Time...")){
                JOptionPane.showMessageDialog(this, "Please Select a Time !!!");
            }
            else if(!validAppt.appointmentValidation(changeDateFormat(dpApptDateCalendar.getText()), separateDoctor(String.valueOf(cbApptDoctor.getSelectedItem())), changeTimeFormat(String.valueOf(cbApptTime.getSelectedItem())))){
                JOptionPane.showMessageDialog(this, "The appointment has been booked! Please select other slot!");
            }
            else if(!validAppt.appointmentValidation(changeDateFormat(dpApptDateCalendar.getText()), changeTimeFormat(String.valueOf(cbApptTime.getSelectedItem())))){
                JOptionPane.showMessageDialog(this, "The appointment selected has been past");
            }else{
                cardLayout.show(cardHost, CARD_PAYMENTS);
            }
        });
        
        //edit appointment seearch editApptFilter apptEditDateCalendar apptEditCustomer 
        if (editApptFilter != null) editApptFilter.addActionListener(e -> {
            Appointment filterAppt = new Appointment();
           ArrayList<Appointment> appointments = new ArrayList<>(); 
           appointments = filterAppt.editApptFilter(changeDateFormat(apptEditDateCalendar.getText()), separateCust(String.valueOf(apptEditCustomer.getSelectedItem())));
            editApptFilterSearch(appointments);
            
            
        });
        
        //edit appointment confirm btn btnEditApptConfirm
        if (btnEditApptConfirm != null) btnEditApptConfirm.addActionListener(e -> {
                    Appointment editAppt = new Appointment();
            int selectedRow = tblEditAppointment.getSelectedRow();

            if (selectedRow == -1) {
                // No row is selected, show an error message.
                JOptionPane.showMessageDialog(this, "Please select a row !!!");
            } else {
                    if(editApptDoc.getSelectedItem().equals("Select Doctor...")){
                    JOptionPane.showMessageDialog(this, "Please Select a Doctor !!!");
                }
                else if(editApptTime.getSelectedItem().equals("Select Time...")){
                    JOptionPane.showMessageDialog(this, "Please Select a Time !!!");
                }
                else if(!editAppt.appointmentValidation(changeDateFormat(editApptDate.getText()), separateDoctor(String.valueOf(editApptDoc.getSelectedItem())), changeTimeFormat(String.valueOf(editApptTime.getSelectedItem())))){
                    JOptionPane.showMessageDialog(this, "The appointment has been booked! Please select other slot!");
                }
                else if(!editAppt.appointmentValidation(changeDateFormat(editApptDate.getText()), changeTimeFormat(String.valueOf(editApptTime.getSelectedItem())))){
                    JOptionPane.showMessageDialog(this, "The appointment selected has been past");
                }
                else if(!editAppt.appointmentValidation(editApptId.getText())){
                    JOptionPane.showMessageDialog(this, "The appointment have been completed / is conducting !!! cannot Edit!!!");

                }
                else{
                    //editApptTime editApptDoc editApptDate editApptId
                    editAppt.editAppointment(editApptId.getText(), changeDateFormat(editApptDate.getText()) , separateDoctor(String.valueOf(editApptDoc.getSelectedItem())), changeTimeFormat(String.valueOf(editApptTime.getSelectedItem())) );
                    refreshCustComboBox();
                    refreshDocComboBox();
                    refreshApptTable();
                    JOptionPane.showMessageDialog(this, "Updated Successfully !!!");
                }
                
                
            }
            
        });
        //edit appointment (cancel appointment) - change status to cancel btnEditApptCancel
        if (btnEditApptCancel != null) btnEditApptCancel.addActionListener(e -> {
            
            
            
                int selectedRow = tblEditAppointment.getSelectedRow();

                if (selectedRow == -1) {
                    // No row is selected, show an error message.
                    JOptionPane.showMessageDialog(this, "Please select a row !!!");
                } else {
                    int confirmCancelApptResult = JOptionPane.showConfirmDialog(
                        null,
                        "Are you Sure you want to cancel this appointment?",
                        "Confirm Cancellation",
                        JOptionPane.YES_NO_OPTION
            
                    );
                    if(confirmCancelApptResult == JOptionPane.YES_OPTION){
                        Appointment cancelAppt = new Appointment();
                        if(!cancelAppt.appointmentValidation(editApptId.getText())){
                            JOptionPane.showMessageDialog(this, "The Appointment has been completed / is conducting !!! cannot cancel !!!");
                        }
                        else{
                            
                            cancelAppt.cancelAppt(editApptId.getText());
                            refreshDocComboBox();
                                refreshCustComboBox();
                                refreshTimeComboBox();
                                refreshApptTable();
                            JOptionPane.showMessageDialog(this, "Appointment Cancel !!!");
                        }
                    }
                }
            
            
            
        });
        
        //payment page pay now
        if (payConfirm != null) payConfirm.addActionListener(e -> {
            // You could pre-fill payment fields here if you want (e.g., selected appt/customer).
//            cardLayout.show(cardHost, CARD_PAYMENTS);
            int selectedRow = payApptTable.getSelectedRow();

            if (selectedRow == -1) {
                // No row is selected, show an error message.
                JOptionPane.showMessageDialog(this, "Please select a row !!!");
            } else {
                // A row is selected, proceed with the payment logic.
                // For example, you can get the appointment ID.
                payApptId = (String.valueOf(paymentModel.getValueAt(selectedRow, 0)));
                System.out.println("Selected Appointment ID for payment: " + payApptId);

                
                cardLayout.show(cardHost, CARD_CONPAYMENT);
            }


        });
        if (btnApptCancel != null) btnApptCancel.addActionListener(e -> {
            //cbApptCustomer dpApptDateCalendar cbApptDoctor cbApptTime
            cbApptCustomer.setSelectedIndex(0);
            cbApptDoctor.setSelectedIndex(0);
            cbApptTime.setSelectedIndex(0);
            
            cardLayout.show(cardHost, CARD_APPT);
        });
        
        //pay now appointment fees
        if (payNow != null) payNow.addActionListener(e -> {
            // dpApptDateCalendar cbApptTime cbApptCustomer cbApptDoctor staffId
            //separateDoctor()    separateCust()     staffLogin.getId();   payMethod
//            System.out.println("StaffID: " + staffLogin.getId() + "DocId: " + separateDoctor() + "CustId: " + separateCust() + "Date: " + changeDateFormat(dpApptDateCalendar.getText())  + "Time: " + changeTimeFormat(String.valueOf(cbApptTime.getSelectedItem())) );
            Appointment addAppt = new Appointment();
            try {
                generateReceiptPayId = addAppt.createAppointment(staffLogin.getId(), separateCust(String.valueOf(cbApptCustomer.getSelectedItem())), changeDateFormat(dpApptDateCalendar.getText()), separateDoctor(String.valueOf(cbApptDoctor.getSelectedItem())), changeTimeFormat(String.valueOf(cbApptTime.getSelectedItem())), String.valueOf(payMethod.getSelectedItem()));
                JOptionPane.showMessageDialog(this, "Payment successful !!!");
                
                refreshDocComboBox();
                refreshCustComboBox();
                refreshTimeComboBox();
                refreshApptTable();
//                generateReceiptFrame(generateReceiptPayId);
                cardLayout.show(cardHost, CARD_APPT);
            } catch (IOException ex) {
                Logger.getLogger(StaffUI.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
            
            

        });
        
        //payment search payFilter
        if (payFilter != null) payFilter.addActionListener(e -> {
            ArrayList<Appointment> appointments = new ArrayList<>();
            Appointment filterAppt = new Appointment();
            appointments = filterAppt.payFilterAppt(separateCust(String.valueOf(payCust.getSelectedItem())));
            
            paymentApptTable(appointments);
            

        });
        //pay now for consultation fees
        if (conPayNow != null) conPayNow.addActionListener(e -> {
            Payment payConFee = new Payment();
            try {
                generateReceiptPayId = payConFee.makePayment(String.valueOf(conPayMethod.getSelectedItem()), staffLogin.getId(), payApptId, removeRM(conPayAmount.getText()));
                
                ArrayList<Appointment> appointments = new ArrayList<>();
                Appointment filterAppt = new Appointment();
                appointments = filterAppt.payFilterAppt(separateCust(String.valueOf(payCust.getSelectedItem())));

                paymentApptTable(appointments);
                JOptionPane.showMessageDialog(this, "Payment successful !!!");
                generateReceiptFrame(generateReceiptPayId);
                cardLayout.show(cardHost, CARD_CONPAYMENTTABLE);
                
                
                
                

            } catch (IOException ex) {
                Logger.getLogger(StaffUI.class.getName()).log(Level.SEVERE, null, ex);
            }

        });
        

        // Export PDF (placeholder only)
        if (btnExportReceiptPdf != null) btnExportReceiptPdf.addActionListener(e ->
                JOptionPane.showMessageDialog(this, "Export Receipt (PDF) clicked — not implemented."));
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        Staff st1 = new Staff("staff1@gmail.com","staff111","staff1","Male", "60123909484", 20,"S004", 2000, "Admin", "Staff");
        java.awt.EventQueue.invokeLater(() -> new StaffUI(st1).setVisible(true));
        
//        System.out.println(st1.getGender());
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
