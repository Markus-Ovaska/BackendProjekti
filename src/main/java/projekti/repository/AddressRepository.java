package projekti.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import projekti.entity.Address;
import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {
 //osoitteiden haku query metodin avulla. Haun tulokseen ei vaikuta esim isot ja pienet kirjaimet
    @Query("SELECT a FROM Address a " +
           "WHERE (:name IS NULL OR LOWER(a.name) LIKE LOWER(CONCAT('%', :name, '%'))) " +
           "AND (:street IS NULL OR LOWER(a.street) LIKE LOWER(CONCAT('%', :street, '%'))) " +
           "AND (:city IS NULL OR LOWER(a.city) LIKE LOWER(CONCAT('%', :city, '%'))) " +
           "AND (:country IS NULL OR LOWER(a.country) LIKE LOWER(CONCAT('%', :country, '%'))) " +
           "AND (:postalCode IS NULL OR a.postalCode LIKE CONCAT('%', :postalCode, '%'))")
    List<Address> searchAddresses(@Param("name") String name,
                                  @Param("street") String street,
                                  @Param("city") String city,
                                  @Param("country") String country,
                                  @Param("postalCode") String postalCode);
}

