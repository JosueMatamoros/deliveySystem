package person;

import java.io.Serializable;

public class Employee extends Person implements Serializable {
    private String job;
    private int salary;
    private Boolean state;

    // Constructor
    public Employee(String fullName, String phone, String gender, Byte age, String job, int salary, Boolean state){
        super(fullName, phone, gender, age);
        this.job = job;
        this.salary = salary;
        this.state = state;
    }

    // Getters
    public String getJob(){
        return this.job;
    }
    public int getSalary(){
        return this.salary;
    }
    public Boolean getState(){
        return this.state;
    }
    // Setters
    public void setJob(String job){
        this.job = job;
    }
    public void setSalary(int salary){
        this.salary = salary;
    }
    public void setState(Boolean state){
        this.state = state;
    }
}
