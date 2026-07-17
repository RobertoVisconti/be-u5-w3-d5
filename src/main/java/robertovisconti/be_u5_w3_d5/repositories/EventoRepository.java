package robertovisconti.be_u5_w3_d5.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import robertovisconti.be_u5_w3_d5.entities.Evento;

import java.util.UUID;

public interface EventoRepository extends JpaRepository<Evento, UUID> {
}
