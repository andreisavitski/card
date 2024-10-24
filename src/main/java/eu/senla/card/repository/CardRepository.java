package eu.senla.card.repository;

import eu.senla.card.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CardRepository extends JpaRepository<Card, Long> {

    @Query("select c from Card c where c.client.id = :clientId")
    List<Card> findByClientId(@Param("clientId") Long clientId);

    @Query("select c from Card c where c.number = :number")
    Optional<Card> findByNumber(Long number);
}
