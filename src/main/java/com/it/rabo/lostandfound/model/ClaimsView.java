package com.it.rabo.lostandfound.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.it.rabo.lostandfound.entity.ClaimRecord;
import com.it.rabo.lostandfound.entity.LostFound;
import com.it.rabo.lostandfound.entity.UserDetails;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClaimsView {

    private Long userId;

    private String userName;

    @JsonIgnore
    private Long lostAndFoundId;

    private Long quantity;

    private String itemName;

    private String place;

    public static ClaimsView of(UserDetails userDetails, LostFound lostAndFound, ClaimRecord e) {
        return new ClaimsView(
                userDetails.getId(),
                userDetails.getName(),
                lostAndFound.getId(),
                e.getQuantity(),
                lostAndFound.getItemName(),
                lostAndFound.getPlace()
        );
    }

}
