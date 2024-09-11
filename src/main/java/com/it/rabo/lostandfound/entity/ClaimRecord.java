package com.it.rabo.lostandfound.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "claimed_items")
public class ClaimRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "claim_record_generator")
    @SequenceGenerator(
            name = "claim_record_generator",
            sequenceName = "S_CR_SEQUENCE",
            allocationSize = 1
    )
    private Long id;

    @ManyToOne
    @JoinColumn(name = "lost_item_id", nullable = false)
    private LostFound lostFound;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserDetails userDetails;

    private Long quantity;

    public ClaimRecord(UserDetails userDetails, LostFound lostFound, Long quantity) {
        this.lostFound = lostFound;
        this.userDetails = userDetails;
        this.quantity = quantity;
    }
}
