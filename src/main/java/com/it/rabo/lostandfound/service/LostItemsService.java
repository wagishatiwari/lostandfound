package com.it.rabo.lostandfound.service;

import com.it.rabo.lostandfound.entity.LostFound;

import java.io.File;
import java.util.List;


public interface LostItemsService {

    List<LostFound> getAllLostAndFoundDetails();

    void updateLostAndFoundDetails(File file);
}
