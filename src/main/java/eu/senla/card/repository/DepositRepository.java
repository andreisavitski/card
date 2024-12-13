package eu.senla.card.repository;

import eu.senla.card.entity.Deposit;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface DepositRepository extends JpaRepository<Deposit, Long> {

    @NotNull
    Optional<Deposit> findById(@NotNull UUID id);
}
