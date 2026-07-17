package robertovisconti.be_u5_w3_d5.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import robertovisconti.be_u5_w3_d5.entities.Utente;

import java.util.UUID;

public interface UtenteRepository extends JpaRepository<Utente, UUID> {

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);
}
