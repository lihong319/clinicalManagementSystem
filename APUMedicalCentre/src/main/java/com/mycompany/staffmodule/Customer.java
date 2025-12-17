package com.mycompany.staffmodule;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
/*
Extra import goes here
 */


public class Customer extends User<Customer>{


    public Customer(){}

    public Customer(String email, String password, String name, String gender, String phoneNum, int age, String id) {
        super(email, password, name, gender, phoneNum, age, id);
    }



    @Override
    public String toString() {
        return "Customer{" +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", phoneNum='" + phoneNum + '\'' +
                ", age=" + age +
                ", id='" + id + '\'' +
                '}';
    }

    

    public ArrayList<Customer> readFile(){


        ArrayList<Customer> customers = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader("Customer.txt"));
            String line;
            while((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 7 ){
                    String gmail = parts[0];
                    String password = parts[1];
                    String name = parts[2];
                    String gender = parts[3];
                    String phoneNum = parts[4];
                    int age = Integer.parseInt(parts[5]) ;
                    String staffId = parts[6];

                    Customer customer = new Customer(gmail, password, name, gender, phoneNum, age, staffId);
                    customers.add(customer);
                }
            }

            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return customers;
    }

    public void appendFile(Customer customer) throws IOException {
        try {
            BufferedWriter UserWriter = new BufferedWriter(new FileWriter("Customer.txt", true));
            UserWriter.append(customer.email + "|" + customer.password + "|" + customer.name + "|" + customer.gender + "|" + customer.phoneNum + "|" + customer.age + "|" + customer.id + "\n");
            UserWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void writeFile(ArrayList<Customer> customers){

        try {
            BufferedWriter UserWriter = new BufferedWriter(new FileWriter("Customer.txt"));

            Iterator customerLoop = customers.iterator();


            while(customerLoop.hasNext()) {
                Customer customer = (Customer) customerLoop.next();
                UserWriter.write(customer.getEmail() + "|" + customer.getPassword() + "|" + customer.getName() + "|" + customer.getGender() + "|" + customer.getPhoneNum() + "|" + customer.getAge() + "|" + customer.getId() + "\n");
            }


            UserWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @Override
    public Customer searchById(String custId){
        ArrayList<Customer> customers = readFile();

        for (Customer c : customers) {
            if (c.getId().equalsIgnoreCase(custId)) { // or getCustId() if that's the correct getter
                return c; // return the found customer
            }
        }

        // If no match found, return null
        return null;
    }
    
     public boolean updateCustomer(Customer updated) {
        ArrayList<Customer> customers = readFile();
        for (int i = 0; i < customers.size(); i++) {
            if (customers.get(i).getId().equalsIgnoreCase(updated.getId())) {
                customers.set(i, updated);
                writeFile(customers);
                return true;
            }
        }
        return false;
    }
    //add
    public void AddCust(Customer addCust)throws IOException{
        
        addCust.setId(generateNextId());
        
        this.appendFile(addCust);
        
    }
    public String generateNextId(){
        ArrayList<Customer> customers = new ArrayList<>();
        customers = this.readFile();
        
        if (customers.isEmpty()) {
            return "C001";
        }
        
        // Find the last customer in the list to get the last ID
        Customer lastCustomer = customers.get(customers.size() - 1);
        String lastId = lastCustomer.getId();

        // Extract the numeric part of the ID (e.g., "C007" -> "007")
        String numericPart = lastId.substring(1);

        // Convert the numeric part to an integer and increment it
        int nextNumber = Integer.parseInt(numericPart) + 1;

        // Format the new number back to a 3-digit string (e.g., 8 -> "008")
        String nextId = String.format("C%03d", nextNumber);

        return nextId;
        
    }
    
    public void editProfile(Customer custEdit){
        ArrayList<Customer> customers = new ArrayList<>();
        customers = this.readFile();
        
        for(Customer custIndex : customers){
           if(custIndex.getId().equals(custEdit.getId())){
               custIndex.setName(custEdit.getName());
               custIndex.setPassword(custEdit.getPassword());
               custIndex.setEmail(custEdit.getEmail());
               custIndex.setGender(custEdit.getGender());
               custIndex.setPhoneNum(custEdit.getPhoneNum());
               custIndex.setAge(custEdit.getAge());
           }
       }
        
        this.writeFile(customers);
    }
    
    public boolean deleteCust(String custId){
        boolean canDelete;
        ArrayList<Customer> customers = new ArrayList<>();
        customers = this.readFile();
        
        Appointment appt = new Appointment();
        canDelete = appt.deleteCustAllowed(custId);
        
        if(canDelete){
            Customer customerToRemove = null;
            for(Customer custIndex : customers){
               if(custIndex.getId().equals(custId)){
                    customerToRemove = custIndex;          
                    break;
               }
           }

            if (customerToRemove != null) {
                customers.remove(customerToRemove);
                System.out.println("Customer with ID " + custId + " deleted successfully.");
            }else {
                System.out.println("Customer with ID " + custId + " not found.");
            }

            this.writeFile(customers);
            return true;
        }
        else{
            return false;
        }
        
        
        
        
    }
}
