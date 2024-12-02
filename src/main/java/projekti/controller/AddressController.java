package projekti.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import projekti.entity.Address;
import projekti.repository.AddressRepository;

import java.util.List;


@Controller
@RequestMapping("/addresses")
public class AddressController {

    @Autowired
    private AddressRepository addressRepository;


    // Näyttää osoitteet
    @GetMapping
    public String listAddresses(Model model) {
        List<Address> addresses = addressRepository.findAll();
        model.addAttribute("addresses", addresses);
        return "addresses";
    }

    // Uusi osoite
    @GetMapping("/new")
    public String showNewAddressForm(Model model) {
        model.addAttribute("address", new Address()); 
        return "newAddress"; 
    }

    // Tallenna osoite
    @PostMapping
    public String saveAddress(@ModelAttribute("address") @Valid Address address, BindingResult result) {
        if (result.hasErrors()) {
            return "newAddress"; // Validointi, jos virheitä palaa takaisin
        }
        addressRepository.save(address);
        return "redirect:/addresses";
    }

    // Osoitteen editointi
    @GetMapping("/edit/{id}")
    public String showEditAddressForm(@PathVariable Long id, Model model) {
        Address address = addressRepository.findById(id).orElse(null);
        if (address != null) {
            model.addAttribute("address", address);
            return "editAddress";
        }
        return "redirect:/addresses"; 
    }

    // Päivitetyt osoitteet
    @PostMapping("/{id}")
    public String updateAddress(@PathVariable Long id, @ModelAttribute("address") Address updatedAddress) {
        addressRepository.findById(id).ifPresent(existingAddress -> {
            existingAddress.setName(updatedAddress.getName());
            existingAddress.setStreet(updatedAddress.getStreet());
            existingAddress.setCity(updatedAddress.getCity());
            existingAddress.setCountry(updatedAddress.getCountry());
            existingAddress.setPostalCode(updatedAddress.getPostalCode());
            addressRepository.save(existingAddress);
        });
        return "redirect:/addresses";
    }

    // Osoitteen poisto
    @GetMapping("/delete/{id}")
    public String deleteAddress(@PathVariable Long id) {
        addressRepository.deleteById(id);
        return "redirect:/addresses";
    }

// Osoitteiden haku
@GetMapping("/search")
public String searchAddresses(@RequestParam(required = false) String name,
                               @RequestParam(required = false) String street,
                               @RequestParam(required = false) String city,
                               @RequestParam(required = false) String country,
                               @RequestParam(required = false) String postalCode,
                               Model model) {
    List<Address> addresses = addressRepository.searchAddresses(name, street, city, country, postalCode);
    model.addAttribute("addresses", addresses);
    model.addAttribute("name", name);
    model.addAttribute("street", street);
    model.addAttribute("city", city);
    model.addAttribute("country", country);
    model.addAttribute("postalCode", postalCode);
    return "addresses"; 
}

}
