package com.it.rabo.lostandfound.controller;

import com.it.rabo.lostandfound.controller.response.ApiResponse;
import com.it.rabo.lostandfound.service.ClaimRecordsService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/claims")
public class ClaimController {

    private final ClaimRecordsService claimRecordsService;

    public ClaimController(ClaimRecordsService claimRecordsService) {
        this.claimRecordsService = claimRecordsService;
    }

    @GetMapping("")
    @RolesAllowed("ADMIN")
    public ApiResponse getClaims() {
        return ApiResponse.of(claimRecordsService.getListOfClaims());
    }

    @PostMapping("")
    @RolesAllowed("USER")
    public ApiResponse saveClaims(@Valid @RequestBody ClaimsRequest claimsRequest) {
        claimRecordsService.saveClaim(claimsRequest.userId(), claimsRequest.lostItemId(), claimsRequest.quantity());
        return ApiResponse.of("Claim is successful");
    }
    public record ClaimsRequest(Long userId, Long lostItemId, Long quantity) {
    }
}
