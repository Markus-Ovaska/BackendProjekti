package projekti.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projekti.entity.Address;
import projekti.repository.AddressRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {

    @Autowired
    private AddressRepository addressRepository;

    // Create a new address
    @PostMapping
    public ResponseEntity<?> createAddress(@RequestBody Address address) {
        List<String> errors = validateAddress(address);
        if (!errors.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }
        Address savedAddress = addressRepository.save(address);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAddress);
    }

    // Get all addresses
    @GetMapping
    public List<Address> getAllAddresses() {
        return addressRepository.findAll();
    }

    // Get an address by ID
    @GetMapping("/{id}")
    public ResponseEntity<Address> getAddress(@PathVariable Long id) {
        return addressRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Get addresses by city
    @GetMapping("/city/{city}")
    public List<Address> getAddressesByCity(@PathVariable String city) {
        return addressRepository.findByCity(city);
    }

    // Update an address
    @PutMapping("/{id}")
    public ResponseEntity<?> updateAddress(@PathVariable Long id, @RequestBody Address updatedAddress) {
        List<String> errors = validateAddress(updatedAddress);
        if (!errors.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }

        return addressRepository.findById(id)
                .map(existingAddress -> {
                    existingAddress.setName(updatedAddress.getName());
                    existingAddress.setStreet(updatedAddress.getStreet());
                    existingAddress.setCity(updatedAddress.getCity());
                    existingAddress.setState(updatedAddress.getState());
                    existingAddress.setPostalCode(updatedAddress.getPostalCode());
                    existingAddress.setEmail(updatedAddress.getEmail());
                    Address savedAddress = addressRepository.save(existingAddress);
                    return ResponseEntity.ok(savedAddress);
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Delete an address
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long id) {
        Optional<Address> existingAddress = addressRepository.findById(id);
    
        if (existingAddress.isPresent()) {
            addressRepository.delete(existingAddress.get());
            return ResponseEntity.noContent().build(); // Return 204 No Content for successful deletion
        } else {
            return ResponseEntity.notFound().build(); // Return 404 Not Found if the address is not found
        }
    }

    // Manual validation method
    private List<String> validateAddress(Address address) {
        List<String> errors = new ArrayList<>();

        if (address.getName() == null || address.getName().isEmpty() || address.getName().length() > 100) {
            errors.add("Name must be between 1 and 100 characters.");
        }
        if (address.getStreet() == null || address.getStreet().isEmpty() || address.getStreet().length() > 255) {
            errors.add("Street must be between 1 and 255 characters.");
        }
        if (address.getCity() == null || address.getCity().isEmpty() || address.getCity().length() > 100) {
            errors.add("City must be between 1 and 100 characters.");
        }
        if (address.getState() == null || address.getState().isEmpty() || address.getState().length() > 50) {
            errors.add("State must be between 1 and 50 characters.");
        }
        if (address.getPostalCode() == null || !address.getPostalCode().matches("\\d{5}")) {
            errors.add("Postal Code must be a 5-digit number.");
        }
        if (address.getEmail() != null && !address.getEmail().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            errors.add("Email must be a valid email address.");
        }

        return errors;
    }
}
