package back_end.e_commerce.model;

@Entity
@Table(name = "addresses")
public class Address {
    private Long id;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String country;
    private String LocalUser;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddressLine1() {
        return this.addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return this.addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLocalUser() {
        return this.LocalUser;
    }

    public void setLocalUser(String LocalUser) {
        this.LocalUser = LocalUser;
    }
    
   
}