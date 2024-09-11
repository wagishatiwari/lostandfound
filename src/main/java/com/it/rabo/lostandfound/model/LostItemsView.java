package com.it.rabo.lostandfound.model;


import com.it.rabo.lostandfound.entity.LostFound;

public record LostItemsView(long id,
                            String  itemName,
                            long quantity,
                            String place){

    public static LostItemsView of(LostFound lostFound) {
        return new LostItemsView(
                lostFound.getId(),
                lostFound.getItemName(),
                lostFound.getQuantity(),
                lostFound.getPlace()
        );
    }
}
