package com.it.rabo.lostandfound.service.impl;

import com.it.rabo.lostandfound.entity.ClaimRecord;
import com.it.rabo.lostandfound.entity.LostFound;
import com.it.rabo.lostandfound.entity.UserDetails;
import com.it.rabo.lostandfound.model.ClaimsView;
import com.it.rabo.lostandfound.repository.ClaimRecordsRepository;
import com.it.rabo.lostandfound.repository.LostAndFoundRepository;
import com.it.rabo.lostandfound.repository.UserDetailsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ClaimRecordsServiceImplTest {

    @InjectMocks
    private ClaimRecordsServiceImpl victim;
    @Mock
    private ClaimRecordsRepository claimRecordsRepository;
    @Mock
    private LostAndFoundRepository lostAndFoundRepository;
    @Mock
    private UserDetailsRepository userDetailsRepository;


    @Test
    void saveClaim(){
        LostFound lf = new LostFound(1L, "Laptop", 1, "Airport");
        UserDetails ud = new UserDetails(1L, "John");

        when(lostAndFoundRepository.findById(1L)).thenReturn(Optional.of(lf));
        when(userDetailsRepository.findById(1L)).thenReturn(Optional.of(ud));
        victim.saveClaim(1L, 1L, 1L);
        verify(claimRecordsRepository, times(1)).save(any());
    }

    @Test
    void throwErrorWhen_claimRequest_has_claimed_more_content_then_present(){
        LostFound lf = new LostFound(1L, "Laptop", 1, "Airport");
        UserDetails ud = new UserDetails(1L, "John");
        when(lostAndFoundRepository.findById(1L)).thenReturn(Optional.of(lf));
        when(userDetailsRepository.findById(1L)).thenReturn(Optional.of(ud));
        assertThrows(IllegalArgumentException.class, () -> victim.saveClaim(1L, 1L, 2L));

    }

    @Test
    void throwErrorWhen_ClaimRequest_with_non_existing_lostItem(){
        when(lostAndFoundRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> victim.saveClaim(1L, 1L, 1L));
    }

    @Test
    void throwErrorWhen_claimRequest_with_non_existing_user(){
        LostFound lf = new LostFound(1L, "Laptop", 1, "Airport");
        when(lostAndFoundRepository.findById(1L)).thenReturn(Optional.of(lf));

        when(lostAndFoundRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows("",NoSuchElementException.class, () -> victim.saveClaim(1L, 1L, 1L));
    }
    @Test
    void getEmptyListOfClaimsIfThereIsNoClaims(){
        Map<Long, List<ClaimsView>> result = victim.getListOfClaims();
        assert result.isEmpty();
    }


    @Test
    void getListOfClaims(){
        LostFound lf = new LostFound(1L, "Laptop", 1, "Airport");
        LostFound lf2 = new LostFound(2L, "Laptop", 1, "Airport");
        UserDetails ud = new UserDetails(1L, "John");
        UserDetails ud2 = new UserDetails(2L, "Sam");
        List<ClaimRecord> claimRecords = List.of(new ClaimRecord(ud, lf, 1L),
                new ClaimRecord(ud, lf2, 2L),
                new ClaimRecord(ud2, lf2, 2L));
        when(claimRecordsRepository.findAll()).thenReturn(claimRecords);
        Map<Long, List<ClaimsView>> result = victim.getListOfClaims();

        assert result.size() == 2;
        assert result.get(1L).size() == 1;
        assert result.get(2L).size() == 2;
        assert result.get(1L).get(0).getUserId().equals(1l);
        assert result.get(1L).get(0).getUserName().equals("John");
        assert result.get(1L).get(0).getQuantity().equals(1l);
        assert result.get(1L).get(0).getLostAndFoundId().equals(1l);
        assert result.get(1L).get(0).getPlace().equals("Airport");
        assert result.get(1L).get(0).getItemName().equals("Laptop");
        assert result.get(2L).get(0).getUserId().equals(1l);
        assert result.get(2L).get(0).getUserName().equals("John");
        assert result.get(2L).get(0).getQuantity().equals(2l);
        assert result.get(2L).get(0).getLostAndFoundId().equals(2l);
        assert result.get(2l).get(0).getPlace().equals("Airport");
        assert result.get(2l).get(0).getItemName().equals("Laptop");
        assert result.get(2l).get(1).getUserId().equals(2l);
        assert result.get(2l).get(1).getUserName().equals("Sam");
        assert result.get(2l).get(1).getQuantity().equals(2l);
        assert result.get(2l).get(1).getLostAndFoundId().equals(2l);
        assert result.get(2l).get(1).getPlace().equals("Airport");
        assert result.get(2l).get(1).getItemName().equals("Laptop");

    }



}
