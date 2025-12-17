package com.mycompany.staffmodule;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
/*
Extra import goes here
 */
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public abstract class User<T> {

    protected String email;
    protected String password;
    protected String name;
    protected String gender;
    protected String phoneNum;
    protected int age;
    protected String id;

    public User(){}

    public User(String email, String password, String name, String gender, String phoneNum, int age, String id) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.gender = gender;
        this.phoneNum = phoneNum;
        this.age = age;
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", phoneNum='" + phoneNum + '\'' +
                ", age=" + age +
                ", id='" + id + '\'' +
                '}';
    }

    public abstract ArrayList<T> readFile();
    public abstract void appendFile(T object) throws IOException;
    public abstract T searchById(String id);

    public void writeFile(ArrayList<Staff> staffs,
            ArrayList<Doctor> doctors, 
            ArrayList<Manager> managers
    ){

        try {
            BufferedWriter UserWriter = new BufferedWriter(new FileWriter("User.txt"));

            Iterator staffLoop = staffs.iterator();
            Iterator doctorLoop = doctors.iterator();
            Iterator managerLoop = managers.iterator();

            while(staffLoop.hasNext()) {
                Staff staff = (Staff) staffLoop.next();
                UserWriter.write(staff.getEmail() + "|" + staff.getPassword() + "|" + staff.getName() + "|" + staff.getGender() + "|" + staff.getPhoneNum() + "|" + staff.getAge() + "|" + staff.getId() + "|" + staff.getSalary() + "|" + staff.getPosition() + "|" + "Staff" + "\n");
            }

            while(doctorLoop.hasNext()) {
                Doctor doctor = (Doctor) doctorLoop.next();
                UserWriter.write(doctor.getEmail() + "|" + doctor.getPassword() + "|" + doctor.getName() + "|" + doctor.getGender() + "|" + doctor.getPhoneNum() + "|" + doctor.getAge() + "|" + doctor.getId() + "|" + doctor.getSalary() + "|" + doctor.getPosition() + "|" + "Doctor" + "\n");
            }

            while(managerLoop.hasNext()) {
                Manager manager = (Manager) managerLoop.next();
                UserWriter.write(manager.getEmail() + "|" + manager.getPassword() + "|" + manager.getName() + "|" + manager.getGender() + "|" + manager.getPhoneNum() + "|" + manager.getAge() + "|" + manager.getId() + "|" + manager.getSalary() + "|" + manager.getPosition() + "|" + "Manager" + "\n");
            }
            UserWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    
    
   
    public static void readFile(String fileName) {
        try (java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    // Generic append function
    public static void appendFile(String fileName, String content) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true))) {
            bw.write(content);
            bw.newLine();  // move to next line after writing
            System.out.println("Content appended successfully.");
        } catch (IOException e) {
            System.out.println("Error appending to file: " + e.getMessage());
        }
    }

    
    
  public static List<String[]> saveFileToArrayList(String fileName) {
        List<String[]> data = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Split attributes by |
                String[] attributes = line.split("\\|");
                data.add(attributes);
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        return data;
    }  
    
    //overwrite 
  public static void overwriteFile(String fileName, List<String[]> rows) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            for (String[] row : rows) {
                bw.write(String.join("|", row));
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }
    
    //add validation
    public boolean emailValidation(String email) {
        // A strict regular expression for email validation.
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";

        // Pre-compile the pattern for efficiency.
        Pattern pattern = Pattern.compile(emailRegex);

        if (email == null) {
            return false;
        }

        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    
    public boolean passwordValidation(String password) {
        if (password == null || password.length() < 8) {
            return false;
        }

        boolean hasUpperCase = false;
        boolean hasLowerCase = false;
        boolean hasDigit = false;
        boolean hasSpecialChar = false;

        String specialChars = "!@#$%^&*()_+-=[]{}|;':\",.<>/?`~";

        for (char ch : password.toCharArray()) {
            if (Character.isUpperCase(ch)) {
                hasUpperCase = true;
            } else if (Character.isLowerCase(ch)) {
                hasLowerCase = true;
            } else if (Character.isDigit(ch)) {
                hasDigit = true;
            } else if (specialChars.contains(String.valueOf(ch))) {
                hasSpecialChar = true;
            }
        }

        return hasUpperCase && hasLowerCase && hasDigit && hasSpecialChar;
    }
    
    public boolean phoneNumValidation(String phoneNum) {
        if (phoneNum == null) {
            return false;
        }
        // Regex for a 10 or 11-digit number with optional formatting.
        // It matches (###) ###-####, ###-###-####, or ###########.
        String phoneRegex = "^(\\([0-9]{3}\\)|[0-9]{3})[-.\\s]?[0-9]{3}[-.\\s]?[0-9]{4}|[0-9]{11}$";
        return phoneNum.matches(phoneRegex);
    }
    
    public boolean ageValidation(int age){
        // age >= 0
        return age >= 0;
    }

//  public abstract String generateNextId();
    
}
