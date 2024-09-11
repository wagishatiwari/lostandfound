package com.it.rabo.lostandfound.controller;

import com.it.rabo.lostandfound.controller.response.ApiResponse;
import com.it.rabo.lostandfound.model.ClaimsView;
import com.it.rabo.lostandfound.service.ClaimRecordsService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/claims")
public class ClaimController {

    private final ClaimRecordsService claimRecordsService;

    public ClaimController(ClaimRecordsService claimRecordsService) {
        this.claimRecordsService = claimRecordsService;
    }

    @GetMapping("")
    @RolesAllowed("ROLE_ADMIN")
    public ApiResponse<Map<Long, List<ClaimsView>>> getClaims() {
        return new ApiResponse<>(claimRecordsService.getListOfClaims(), HttpStatus.OK.value());
    }


    @PostMapping("")
    @RolesAllowed("ROLE_USER")
    public ApiResponse<Void> saveClaims(@Valid @RequestBody ClaimsRequest claimsRequest) {
        claimRecordsService.saveClaim(claimsRequest.userId(), claimsRequest.lostItemId(), claimsRequest.quantity());
        return new ApiResponse<>("Claim is successful", HttpStatus.CREATED.value());
    }
    public record ClaimsRequest(Long userId, Long lostItemId, Long quantity) {
    }
}
