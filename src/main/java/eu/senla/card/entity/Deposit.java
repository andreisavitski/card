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

import java.time.LocalDateTime;
import java.util.UUID;

import static jakarta.persistence.CascadeType.MERGE;

@Getter
@Setter
@Builder
@Entity
@Table(name = "deposit")
@NoArgsConstructor
@AllArgsConstructor
public class Deposit {

    @Id
    @Column(name = "id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "deposit_type_id", nullable = false)
    private DepositType depositType;

    @Column(name = "deposit_opening_date", nullable = false)
    private LocalDateTime depositOpeningDate;

    @Column(name = "deposit_closing_date")
    private LocalDateTime depositClosingDate;

    @ManyToOne(cascade = MERGE)
    @JoinColumn(name = "card_id", unique = true, nullable = false)
    private Card card;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;
}
