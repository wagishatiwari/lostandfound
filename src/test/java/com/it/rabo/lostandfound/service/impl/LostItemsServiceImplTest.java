package com.it.rabo.lostandfound.service.impl;

import com.it.rabo.lostandfound.entity.LostFound;
import com.it.rabo.lostandfound.processor.LostAndFoundProcessor;
import com.it.rabo.lostandfound.repository.LostAndFoundRepository;
import com.it.rabo.lostandfound.util.FileUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LostItemsServiceImplTest {

    @InjectMocks
    private LostItemsServiceImpl victim;
    @Mock
    private LostAndFoundRepository lostAndFoundRepository;
    @Mock
    private LostAndFoundProcessor lostAndFoundProcessor;;

    @Test
    void testGetAllLostAndFoundDetails() {
        LostFound lostFound = new LostFound();
        List<LostFound> lostFoundList = List.of(lostFound);
        when(lostAndFoundRepository.findAll()).thenReturn(lostFoundList);
        List<LostFound> result = victim.getAllLostAndFoundDetails();
        assertEquals(lostFoundList, result);
        verify(lostAndFoundRepository, times(1)).findAll();
    }
    @Test
    void testUpdateLostAndFoundDetails() throws IOException {
        File uploadedFile = FileUtil.getFileFromResource("LostItems_corrupted.pdf");

        List<LostFound> processedList = Collections.singletonList(new LostFound());
        when(lostAndFoundProcessor.process(uploadedFile)).thenReturn(processedList);
        victim.updateLostAndFoundDetails(uploadedFile);
        verify(lostAndFoundProcessor, times(1)).process(uploadedFile);
        verify(lostAndFoundRepository, times(1)).saveAll(processedList);
    }

}
