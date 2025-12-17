package com.mycompany.staffmodule;


import java.io.*;
import java.util.ArrayList;
/*
Extra import goes here
 */
import com.mycompany.GUI.StaffUI;

public class Staff extends User<Staff>{
    private double salary;
    private String position;
    private static String roles;

    public Staff(){}

    public Staff(String email, String password, String name, String gender, String phoneNum, int age, String id, double salary, String position, String roles) {
        super(email, password, name, gender, phoneNum, age, id);
        this.salary = salary;
        this.position = position;
        this.roles = roles;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public static String getRoles() {
        return roles;
    }

    public static void setRoles(String roles) {
        Staff.roles = roles;
    }

    @Override
    public String toString() {
        return "Staff: " + '\n' +
                "email= '" + email + '\'' + '\n' +
                "password= '" + password + '\'' + '\n' +
                "name= '" + name + '\'' + '\n' +
                "gender= '" + gender + '\'' + '\n' +
                "phoneNum= '" + phoneNum + '\'' + '\n' +
                "age= " + age + '\n' +
                "id= '" + id + '\'' + '\n' +
                "salary= " + salary + '\n' +
                "position= '" + position + '\'' + '\n' +
                "roles= '" + roles + '\'' + '\n' +'\n'
                ;
    }

    
    
   


    public ArrayList<Staff> readFile(){


        ArrayList<Staff> staffs = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader("User.txt"));
            String line;
            while((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 10 && parts[9].equalsIgnoreCase("Staff")){
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

                    Staff staff = new Staff(gmail, password, name, gender, phoneNum, age, staffId, salary, position, roles);
                    staffs.add(staff);
                }
            }

            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return staffs;
    }

    public void appendFile(Staff staff) throws IOException {
        try {
            BufferedWriter UserWriter = new BufferedWriter(new FileWriter("User.txt", true));
            UserWriter.append(staff.email + "|" + staff.password + "|" + staff.name + "|" + staff.gender + "|" + staff.phoneNum + "|" + staff.age + "|" + staff.id + "|" + staff.salary + "|" + staff.position + "|" + "Staff" +"\n");
            UserWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public Staff searchById(String staffId){
        ArrayList<Staff> staffs = readFile();

        for (Staff s : staffs) {
            if (s.getId().equalsIgnoreCase(staffId)) { // or getCustId() if that's the correct getter
                return s; // return the found customer
            }
        }

        // If no match found, return null
        return null;
    }
    
    //start add
    
    
    public void editProfile(Staff staffEdit){
//        System.out.println(staffEdit);
        
       ArrayList<Staff> staffs = new ArrayList<>();
       ArrayList<Manager> managers = new ArrayList<>();
       ArrayList<Doctor> doctors = new ArrayList<>();
       Staff readStaff = new Staff();
       Manager readManager = new Manager();
       Doctor readDoctor = new Doctor();
       
       staffs = readStaff.readFile();
       managers = readManager.readFile();
       doctors = readDoctor.readFile();
       
       for(Staff staffIndex : staffs){
           if(staffIndex.getId().equals(staffEdit.getId())){
               staffIndex.setName(staffEdit.getName());
               staffIndex.setPassword(staffEdit.getPassword());
               staffIndex.setEmail(staffEdit.getEmail());
               staffIndex.setGender(staffEdit.getGender());
               staffIndex.setPhoneNum(staffEdit.getPhoneNum());
               staffIndex.setAge(staffEdit.getAge());
           }
       }
       
        System.out.println(staffs);
        
        readStaff.writeFile(staffs, doctors, managers);
        
    }
    @Override
    public boolean ageValidation(int age){
        //age between 18 and 65
        return age >= 18 && age <= 65;

    }
    
    public String generateNextId(){
        ArrayList<Staff> staffs = new ArrayList<>();
        staffs = this.readFile();
        
        if (staffs.isEmpty()) {
            return "S001";
        }
        
        // Find the last customer in the list to get the last ID
        Staff lastStaff = staffs.get(staffs.size() - 1);
        String lastId = lastStaff.getId();

        // Extract the numeric part of the ID (e.g., "C007" -> "007")
        String numericPart = lastId.substring(1);

        // Convert the numeric part to an integer and increment it
        int nextNumber = Integer.parseInt(numericPart) + 1;

        // Format the new number back to a 3-digit string (e.g., 8 -> "008")
        String nextId = String.format("S%03d", nextNumber);

        return nextId;
    }
    
}
