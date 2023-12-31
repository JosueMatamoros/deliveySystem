package person;

import java.io.Serializable;

abstract class Person implements Serializable {
    // Attributes
    private String fullName;
    private String phone;
    private String gender;
    private Byte age;

    // Constructor
    public Person(String fullName, String phone, String gender, Byte age ){
        this.fullName = fullName;
        this.phone = phone;
        this.gender = gender;
        this.age = age;
    }

    // Getters
    public String getFullName(){
        return this.fullName;
    }
    public String getPhone(){
        return this.phone;
    }
    public String getGender(){
        return  this.gender;
    }
    public Byte getAge(){
        return this.age;
    }
    // Setters
    public void setPhone(String phone){
        this.phone = phone;
    }
    public void setAge(Byte age){
        this.age = age;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}