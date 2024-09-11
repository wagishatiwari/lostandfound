package com.it.rabo.lostandfound.service;

import com.it.rabo.lostandfound.model.ClaimsView;

import java.util.List;
import java.util.Map;

public interface ClaimRecordsService {

    void saveClaim(Long userId, Long lostAndFoundId, Long quantity);
    Map<Long, List<ClaimsView>> getListOfClaims();
}
