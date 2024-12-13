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
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

import static jakarta.persistence.CascadeType.MERGE;

@Getter
@Setter
@ToString
@Builder
@Entity
@Table(name = "deposit")
@NoArgsConstructor
@AllArgsConstructor
public class Deposit {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "deposit_type_id")
    private DepositType depositType;

    @Column(name = "deposit_opening_date")
    private LocalDateTime depositOpeningDate;

    @Column(name = "deposit_closing_date")
    private LocalDateTime depositClosingDate;

    @ToString.Exclude
    @ManyToOne(cascade = MERGE)
    @JoinColumn(name = "card_id")
    private Card card;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;
}
