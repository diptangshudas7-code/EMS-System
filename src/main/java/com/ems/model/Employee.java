// Employee.java
package com.ems.model;

public class Employee {
    private int id;
    private String name, department, email, username;
    private double salary;

    // Constructor
    public Employee(int id, String name, String department, double salary, String email, String username) {
        this.id = id; this.name = name; this.department = department;
        this.salary = salary; this.email = email; this.username = username;
    }

    // Getters
    public int getId()           { return id; }
    public String getName()      { return name; }
    public String getDepartment(){ return department; }
    public double getSalary()    { return salary; }
    public String getEmail()     { return email; }
    public String getUsername()  { return username; }
}