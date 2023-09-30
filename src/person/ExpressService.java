package person;

public class ExpressService extends Person{
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
}
