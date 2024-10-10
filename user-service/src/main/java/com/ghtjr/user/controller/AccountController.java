package com.ghtjr.user.controller;

import com.ghtjr.user.service.KeycloakAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class AccountController {

    private final KeycloakAdminService keycloakAdminService;

    @DeleteMapping("/account/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAccount(@AuthenticationPrincipal Jwt jwt) {
        // 토큰에서 사용자 정보 추출
        String userId = jwt.getClaimAsString("sub"); // 사용자 UUID
        // Keycloak에서 사용자 삭제
        keycloakAdminService.deleteUser(userId);
        // 회원 탈퇴 이벤트 발행 (예: Kafka)
        // eventPublisher.publishUserDeletedEvent(userId);

    }
}
