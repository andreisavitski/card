package eu.senla.card.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Builder
@Entity
@Table(name = "card")
@AllArgsConstructor
@NoArgsConstructor
public class Card {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "number", nullable = false, unique = true)
    private Long number;

    @Column(name = "amount")
    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;
}
