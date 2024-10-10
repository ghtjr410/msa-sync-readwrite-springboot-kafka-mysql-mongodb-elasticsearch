package com.ghtjr.userprofile.controller;

import com.ghtjr.userprofile.dto.UserprofileRequestDto;
import com.ghtjr.userprofile.dto.UserprofileResponseDto;
import com.ghtjr.userprofile.service.UserprofileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/profile")
@RequiredArgsConstructor
public class UserprofileController {
    private final UserprofileService userprofileService;

    @PostMapping
    public UserprofileResponseDto createOrUpdateUserprofile(
            @RequestHeader(value = "X-User-Sub", required = false) String uuid,
            @RequestBody UserprofileRequestDto request
    ) {
        return userprofileService.createOrUpdateUserprofile(uuid, request);
    }
}