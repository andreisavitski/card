package eu.senla.card.repository;

import eu.senla.card.entity.Card;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long> {

    @NotNull
    @Query("select c from Card c where c.client.id = :clientId")
    List<Card> findByClientId(@NotNull @Param("clientId") Long clientId);
}
