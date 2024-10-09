package com.ghtjr.gateway.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.*;

@Component
public class CustomHeaderRemovalFilter extends OncePerRequestFilter {
    private final Set<String> headersToRemove = Set.of("x-user-roles", "x-user-sub");

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        HttpServletRequest wrapper = new HttpServletRequestWrapper(request) {

            @Override
            public Enumeration<String> getHeaderNames() {
                Enumeration<String> originalHeaderNames = request.getHeaderNames();
                List<String> headerNames = new ArrayList<>();
                while (originalHeaderNames.hasMoreElements()) {
                    String headerName = originalHeaderNames.nextElement();
                    if (!headersToRemove.contains(headerName.toLowerCase())) {
                        headerNames.add(headerName);
                    }
                }
                return Collections.enumeration(headerNames);
            }

            @Override
            public String getHeader(String name) {
                if (headersToRemove.contains(name.toLowerCase())) {
                    return null;
                }
                return request.getHeader(name);
            }

            @Override
            public Enumeration<String> getHeaders(String name) {
                if (headersToRemove.contains(name.toLowerCase())) {
                    return Collections.emptyEnumeration();
                }
                return request.getHeaders(name);
            }
        };

        filterChain.doFilter(wrapper, response);
    }
}
