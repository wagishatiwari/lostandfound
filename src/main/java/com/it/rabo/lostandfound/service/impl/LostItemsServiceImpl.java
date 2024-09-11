package com.it.rabo.lostandfound.service.impl;

import com.it.rabo.lostandfound.entity.LostFound;
import com.it.rabo.lostandfound.processor.LostAndFoundProcessor;
import com.it.rabo.lostandfound.repository.LostAndFoundRepository;
import com.it.rabo.lostandfound.service.LostItemsService;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
public class LostItemsServiceImpl implements LostItemsService {

    private final LostAndFoundRepository lostAndFoundRepository;

    private final LostAndFoundProcessor lostAndFoundProcessor;

    public LostItemsServiceImpl(LostAndFoundRepository lostAndFoundRepository, LostAndFoundProcessor lostAndFoundProcessor){
        this.lostAndFoundRepository = lostAndFoundRepository;
        this.lostAndFoundProcessor = lostAndFoundProcessor;
    }

    @Override
    public List<LostFound> getAllLostAndFoundDetails() {
        return lostAndFoundRepository.findAll();
    }

    @Override
    public void updateLostAndFoundDetails(File file) {
        lostAndFoundRepository.saveAll(lostAndFoundProcessor.process(file));
    }
}
