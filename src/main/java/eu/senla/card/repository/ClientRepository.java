package eu.senla.card.repository;

import eu.senla.card.entity.Client;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ClientRepository extends JpaRepository<Client, Long> {

    @NotNull
    Optional<Client> findById(@NotNull UUID id);
}
