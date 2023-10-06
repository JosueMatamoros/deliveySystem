package person.aggregated;

import java.io.Serializable;

public class Address implements Serializable {
    // Attributes
    private String province;
    private String district;
    private String canton;
    private String exactAddress;
    private String description;

    // Constructor
    public Address(String province, String district, String canton, String exactAddress, String description){
        this.province = province;
        this.district = district;
        this.canton = canton;
        this.exactAddress = exactAddress;
        this.description = description;
    }

    // Getters
    public String getCompleteAddress(){
        return this.province + ", " + this.canton + ", " + this.district + ", " + this.exactAddress + " ( " + this.description + " )";
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Province: ").append(province).append("\n");
        sb.append("District: ").append(district).append("\n");
        sb.append("Canton: ").append(canton).append("\n");
        sb.append("Exact Address: ").append(exactAddress).append("\n");
        sb.append("Description: ").append(description).append("\n");
        return sb.toString();
    }

}