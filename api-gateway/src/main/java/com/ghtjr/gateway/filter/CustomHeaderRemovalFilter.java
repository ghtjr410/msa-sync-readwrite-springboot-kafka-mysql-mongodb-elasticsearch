package com.ghtjr.gateway.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;

@Component

public class CustomHeaderRemovalFilter extends OncePerRequestFilter {
    private  final List<String> headersToRemove = List.of("X-User-Roles", "X-User-Sub");


    @Override
    protected  void doFilterInternal(HttpServletRequest request,
                                     HttpServletResponse response,
                                     FilterChain filterChain) throws ServletException, IOException {

        // 요청 래퍼를 사용하여 헤더를 제거합니다.
        HttpServletRequest wrapper = new HttpServletRequestWrapper(request) {

            /**
             * 헤더 이름 목록을 가져오며, 제거할 헤더를 제외합니다.
             */
            @Override
            public Enumeration<String> getHeaderNames() {
                List<String> headerNames = Collections.list(request.getHeaderNames());
                List<String> lowerCaseHeadersToRemove = headersToRemove.stream()
                        .map(String::toLowerCase)
                        .collect(Collectors.toList());

                // 제거할 헤더를 필터링
                headerNames = headerNames.stream()
                        .filter(header -> !lowerCaseHeadersToRemove.contains(header.toLowerCase()))
                        .collect(Collectors.toList());

                return Collections.enumeration(headerNames);
            }

            /**
             * 특정 헤더의 값을 반환하며, 제거할 헤더인 경우 null을 반환합니다.
             */
            @Override
            public String getHeader(String name) {
                if (headersToRemove.contains(name)) {
                    return null;
                }
                return request.getHeader(name);
            }

            /**
             * 특정 헤더의 모든 값을 반환하며, 제거할 헤더인 경우 빈 열거형을 반환합니다.
             */
            @Override
            public Enumeration<String> getHeaders(String name) {
                if (headersToRemove.contains(name)) {
                    return Collections.emptyEnumeration();
                }
                return request.getHeaders(name);
            }
        };

        // 필터 체인을 계속 진행
        filterChain.doFilter(wrapper, response);
    }
}
