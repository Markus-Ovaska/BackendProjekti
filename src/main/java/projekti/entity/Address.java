package projekti.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "addresses")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//osoitteen merkkimäärien validointi
    @NotNull(message = "Name cannot be null")
    @Size(min = 1, max = 30, message = "Name must be between 1 and 30 characters")
    private String name;

    
    @NotNull(message = "Street cannot be null")
    @Size(min = 1, max = 30, message = "Street must be between 1 and 30 characters")
    private String street;

    
    @NotNull(message = "City cannot be null")
    @Size(min = 1, max = 20, message = "City must be between 1 and 20 characters")
    private String city;

    
    @NotNull(message = "Country cannot be null")
    @Size(min = 1, max = 30, message = "Country must be between 1 and 30 characters")
    private String country;

    
    @NotNull(message = "Postal Code cannot be null")
    @Pattern(regexp = "\\d{5}", message = "Postal Code must be a 5-digit number")
    private String postalCode;

    // Getterit ja setterit
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
}