package eu.senla.card.repository;

import eu.senla.card.entity.DepositType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DepositTypeRepository extends JpaRepository<DepositType, UUID> {
}
