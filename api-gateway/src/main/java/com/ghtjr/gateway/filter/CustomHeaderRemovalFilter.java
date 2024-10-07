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
        System.out.println("CustomHeaderRemovalFilter: 요청 URI 처리 중 - " + request.getRequestURI());

        // 요청 래퍼를 사용하여 헤더를 제거합니다.
        HttpServletRequest wrapper = new HttpServletRequestWrapper(request) {

            @Override
            public Enumeration<String> getHeaderNames() {
                List<String> headerNames = Collections.list(request.getHeaderNames());
                // 제거할 헤더를 대소문자 무시하고 필터링
                List<String> lowerCaseHeadersToRemove = headersToRemove.stream()
                        .map(String::toLowerCase)
                        .collect(Collectors.toList());

                // 제거할 헤더를 필터링하면서 로그 출력
                headerNames = headerNames.stream()
                        .filter(header -> {
                            boolean shouldRemove = lowerCaseHeadersToRemove.contains(header.toLowerCase());
                            if (shouldRemove) {
                                System.out.println("CustomHeaderRemovalFilter: 헤더 제거 - " + header);
                            }
                            return !shouldRemove;
                        })
                        .collect(Collectors.toList());

                return Collections.enumeration(headerNames);
            }

            @Override
            public String getHeader(String name) {
                if (headersToRemove.contains(name)) {
                    return null;
                }
                return request.getHeader(name);
            }

            @Override
            public Enumeration<String> getHeaders(String name) {
                if (headersToRemove.contains(name)) {
                    return Collections.emptyEnumeration();
                }
                return request.getHeaders(name);
            }
        };

        filterChain.doFilter(wrapper, response);
    }
}
