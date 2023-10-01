package person.aggregated;

public class Address {
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



}
