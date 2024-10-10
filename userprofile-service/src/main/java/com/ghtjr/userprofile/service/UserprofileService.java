package com.ghtjr.userprofile.service;

import com.ghtjr.userprofile.dto.UserprofileRequestDto;
import com.ghtjr.userprofile.dto.UserprofileResponseDto;
import com.ghtjr.userprofile.model.Userprofile;
import com.ghtjr.userprofile.repository.UserprofileRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserprofileService {
    private final UserprofileRepository userprofileRepository;

    @Transactional
    public UserprofileResponseDto createOrUpdateUserprofile(String uuid, UserprofileRequestDto requestDto) {
        Userprofile userprofile = userprofileRepository.findByUuid(uuid)
                .orElseGet(() -> new Userprofile());

        userprofile.setUuid(uuid);
        userprofile.setNickname(requestDto.nickname());
        userprofile.setBlogName(requestDto.blogName());
        userprofile.setBio(requestDto.bio());

        Userprofile savedUserprofile = userprofileRepository.save(userprofile);

        return new UserprofileResponseDto(
                savedUserprofile.getUuid(),
                savedUserprofile.getNickname(),
                savedUserprofile.getBlogName(),
                savedUserprofile.getBio()
        );
    }
}