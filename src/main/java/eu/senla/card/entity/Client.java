package eu.senla.card.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

import static jakarta.persistence.CascadeType.REMOVE;
import static jakarta.persistence.FetchType.EAGER;

@Data
@Entity
@Table(name = "client")
public class Client {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "login")
    private String login;

    @OneToMany(mappedBy = "client", cascade = REMOVE, fetch = EAGER)
    private List<Card> cards;
}
