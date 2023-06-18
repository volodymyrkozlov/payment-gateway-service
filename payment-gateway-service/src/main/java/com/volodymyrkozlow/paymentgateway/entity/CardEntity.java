package com.volodymyrkozlow.paymentgateway.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Objects;

/**
 * Entity to manage {@link CardEntity}.
 */
@Table(name = "cards")
@Entity
@Getter
@Setter
@ToString
public class CardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String pan;

    @Column(nullable = false)
    private String expiry;

    @ToString.Exclude
    @JoinColumn(name = "card_holder_id", nullable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private CardHolderEntity cardHolder;

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }

        final var that = (CardEntity) o;

        return this.id != null && Objects.equals(this.id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
