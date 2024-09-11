package com.it.rabo.lostandfound.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "lost_items")
public class LostFound {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lost_found_generator")
    @SequenceGenerator(
            name = "lost_found_generator",
            sequenceName = "S_LAF_SEQUENCE",
            allocationSize = 1
    )
    private Long id;
    private String itemName;
    private Integer quantity;
    private String place;

    @OneToMany(mappedBy = "lostFound")
    private Set<ClaimRecord> claimRecords;

    public LostFound(Long id, String itemName, Integer quantity, String place) {
        this.id = id;
        this.itemName = itemName;
        this.quantity = quantity;
        this.place = place;
    }

    public LostFound(String itemName, int quantity, String place) {
        this.itemName = itemName;
        this.quantity = quantity;
        this.place = place;
    }
}
