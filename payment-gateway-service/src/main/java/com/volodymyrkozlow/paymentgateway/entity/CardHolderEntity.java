package com.volodymyrkozlow.paymentgateway.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Entity to manage {@link CardHolderEntity}.
 */
@Table(name = "card_holders")
@Entity
@Getter
@Setter
@ToString
public class CardHolderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @ToString.Exclude
    @OneToMany(mappedBy = "cardHolder", cascade = CascadeType.ALL)
    private List<CardEntity> cards;

    public void addCard(final CardEntity card) {
        Optional.ofNullable(this.cards)
            .ifPresentOrElse(cards -> {
                cards.add(card);
                card.setCardHolder(this);
            }, () -> {
                this.cards = new ArrayList<>();
                cards.add(card);
                card.setCardHolder(this);
            });
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }

        final var that = (CardHolderEntity) o;

        return this.id != null && Objects.equals(this.id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
