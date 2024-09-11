package com.it.rabo.lostandfound.service.impl;

import com.it.rabo.lostandfound.entity.ClaimRecord;
import com.it.rabo.lostandfound.entity.LostFound;
import com.it.rabo.lostandfound.entity.UserDetails;
import com.it.rabo.lostandfound.model.ClaimsView;
import com.it.rabo.lostandfound.repository.ClaimRecordsRepository;
import com.it.rabo.lostandfound.repository.LostAndFoundRepository;
import com.it.rabo.lostandfound.repository.UserDetailsRepository;
import com.it.rabo.lostandfound.service.ClaimRecordsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
public class ClaimRecordsServiceImpl implements ClaimRecordsService {

    private final ClaimRecordsRepository claimRecordsRepository;
    private final LostAndFoundRepository lostAndFoundRepository;
    private final UserDetailsRepository userDetailsRepository;

    public ClaimRecordsServiceImpl(ClaimRecordsRepository claimRecordsRepository, UserDetailsRepository userDetailsRepository, LostAndFoundRepository lostAndFoundRepository) {
        this.claimRecordsRepository = claimRecordsRepository;
        this.userDetailsRepository = userDetailsRepository;
        this.lostAndFoundRepository = lostAndFoundRepository;
    }

    @Override
    public void saveClaim(Long userId, Long lostItemId, Long quantity) {
        var lostItem = lostAndFoundRepository.findById(lostItemId).orElseThrow(() -> new NoSuchElementException("Lost items not found"));
        var user = userDetailsRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("User not found"));
        Optional.of(lostItem)
                .filter(item -> item.getQuantity() >= quantity)
                .orElseThrow(() -> new IllegalArgumentException("Quantity is greater than available quantity"));
        saveOrUpdateClaimRecord(user, lostItem, quantity);
    }

    @Override
    public Map<Long, List<ClaimsView>> getListOfClaims() {
        return claimRecordsRepository.findAll().stream()
                .map(this::mappingClaimView)
                .collect(Collectors.groupingBy(ClaimsView::getLostAndFoundId));
    }

    private ClaimsView mappingClaimView(ClaimRecord e) {
        return ClaimsView.of(e.getUserDetails(), e.getLostFound(), e);
    }

    private void saveOrUpdateClaimRecord(UserDetails user, LostFound lostFound, Long quantity) {
        claimRecordsRepository.findByLostFound_IdAndUserDetails_Id(lostFound.getId(), user.getId()).ifPresentOrElse(
                updateExistingClaimRecord(quantity),
                saveNewClaimRecord(user, lostFound, quantity)
        );
    }

    private Runnable saveNewClaimRecord(UserDetails user, LostFound lostFound, Long quantity) {
        return () -> claimRecordsRepository.save(new ClaimRecord(user, lostFound, quantity));
    }

    private Consumer<ClaimRecord> updateExistingClaimRecord(Long quantity) {
        return e -> {
            e.setQuantity(quantity);
            claimRecordsRepository.save(e);
        };
    }

}
