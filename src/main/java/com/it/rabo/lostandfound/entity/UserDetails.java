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
@Table(name = "user_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_details_generator")
    @SequenceGenerator(
            name = "user_details_generator",
            sequenceName = "S_UD_SEQUENCE",
            allocationSize = 1
    )
    private Long id;

    private String  name;

    @OneToMany(mappedBy = "userDetails")
    private Set<ClaimRecord> claimRecords;

    public UserDetails(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
