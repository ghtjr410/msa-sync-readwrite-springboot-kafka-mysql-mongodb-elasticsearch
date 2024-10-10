package com.ghtjr.userprofile.dto;

public record UserprofileResponseDto(
        String uuid,
        String nickname,
        String blogName,
        String bio
) {
}