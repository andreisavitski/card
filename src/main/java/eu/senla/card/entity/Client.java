package eu.senla.card.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

import static jakarta.persistence.CascadeType.REMOVE;
import static jakarta.persistence.FetchType.EAGER;

@Getter
@Setter
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
