package person;

import java.io.Serializable;

public class ExpressService extends Person implements Serializable {
    // Attributes
    private String entityName;
    private String identification;
    private String vehiculeType;
    private String vehiculePlate;
    private String caracteristics;
    private Boolean state;

    // Constructor
    public ExpressService(String fullName, String phone, String gender, Byte age, String entityName, String identification, String vehiculeType, String vehiculePlate, String caracteristics){
        super(fullName, phone, gender, age);
        this.entityName = entityName;
        this.identification = identification;
        this.vehiculeType = vehiculeType;
        this.vehiculePlate = vehiculePlate;
        this.caracteristics = caracteristics;
        this.state = false;
    }

    public String getCaracteristics() {
        return caracteristics;
    }
    public String getEntityName() {
        return entityName;
    }

    public String getIdentification() {
        return identification;
    }

    public Boolean getState() {
        return state;
    }

    public String getVehiculePlate() {
        return vehiculePlate;
    }

    public String getVehiculeType() {
        return vehiculeType;
    }

    public void setCaracteristics(String caracteristics) {
        this.caracteristics = caracteristics;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public void setVehiculePlate(String vehiculePlate) {
        this.vehiculePlate = vehiculePlate;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Entity Name: ").append(entityName).append("\n");
        sb.append("Identification: ").append(identification).append("\n");
        sb.append("Vehicule Type: ").append(vehiculeType).append("\n");
        sb.append("Vehicule Plate: ").append(vehiculePlate).append("\n");
        sb.append("Caracteristics: ").append(caracteristics).append("\n");
        sb.append("State: ").append(state).append("\n");
        return sb.toString();
    }
}
