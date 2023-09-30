package person;

public class Employee extends Person{
    private String job;
    private Double salary;
    private Boolean state;

    // Constructor
    public Employee(String fullName, String phone, String gender, Byte age, String job, Double salary, Boolean state){
        super(fullName, phone, gender, age);
        this.job = job;
        this.salary = salary;
        this.state = state;
    }

    // Getters
    public String getJob(){
        return this.job;
    }
    public Double getSalary(){
        return this.salary;
    }
    public Boolean getState(){
        return this.state;
    }
    // Setters
    public void setJob(String job){
        this.job = job;
    }
    public void setSalary(Double salary){
        this.salary = salary;
    }
    public void setState(Boolean state){
        this.state = state;
    }
}
