package com.it.rabo.lostandfound.controller;

import com.it.rabo.lostandfound.controller.response.ApiResponse;
import com.it.rabo.lostandfound.model.LostItemsView;
import com.it.rabo.lostandfound.service.LostItemsService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/lost-items")
@RolesAllowed("ADMIN")
public class LostItemsController {

    private final LostItemsService lostItemsService;

    public LostItemsController(LostItemsService lostItemsService) {
        this.lostItemsService = lostItemsService;
    }

    @GetMapping("")
    public ApiResponse<List<LostItemsView>> getLostFound() {
        return new ApiResponse<>(lostItemsService.getAllLostAndFoundDetails().stream().map(LostItemsView::of).toList(),
                HttpStatus.OK.value());
    }

    @PostMapping("")
    public ApiResponse<Void> upload(@RequestParam("file") MultipartFile file) throws IOException {
        throwExceptionIfNoFileFound(file);
        lostItemsService.updateLostAndFoundDetails(convertMultipartToFile(file));
        return new ApiResponse<>("File uploaded successfully", HttpStatus.CREATED.value());
    }

    private static File convertMultipartToFile(MultipartFile file) throws IOException {

        Path tempDir = Files.createTempDirectory("");
        Path tempFile = tempDir.resolve(Objects.requireNonNull(file.getOriginalFilename()));
        Files.copy(file.getInputStream(), tempFile);
        return tempFile.toFile();
    }

    private void throwExceptionIfNoFileFound(MultipartFile file) throws NoSuchFileException {
        if (file.isEmpty()) {
            throw new NoSuchFileException("Failed to store empty file.");
        }
    }
}
