package back_end.e_commerce.model;
import jakarta.persistence.*;
 

@Entity
@Table(name = "addresses")
public class Address {
    @id
    @GenerateValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id" , nullable = false)
    private Long id;
    @Column(name = "address_line1", nullable = false , length = 512)
    private String addressLine1;
    @Column(name = "address_line2", length = 512)
    private String addressLine2;
    @Column(name = "city", nullable = false)
    private String city;
    @Column(name = "country", nullable = false , length = 100)
    private String country;
    @MantyToOne(optional = false)
    @JoinColumn(name = "local_user_id", nullable = false)
    private  LocalUser user;
    public LocalUser getUser() {
        return user;
    
    }
    public void setUser(LocalUser user) {
        this.user = user;
    }

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