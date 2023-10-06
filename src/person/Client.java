package person;
import java.io.Serializable;
import java.util.ArrayList;
import person.aggregated.Address;

public class Client extends Person implements Serializable {
    // Attributes
    private ArrayList<Address> addresses = new ArrayList<Address>();

    // Constructor
    public Client(String fullName, String phone, String gender, Byte age, ArrayList<Address> addresses){
        super(fullName, phone, gender, age);
        this.addresses = addresses;
    }
    // Methods
    public ArrayList<Address> getAddresses(){

        return this.addresses;
    }
    public void appendAddress(Address address){
        this.addresses.add(address);
    }
    public void removeAddress(Address address){
        this.addresses.remove(address);
    }

}