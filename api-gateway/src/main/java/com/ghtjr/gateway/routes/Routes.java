package com.ghtjr.gateway.routes;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.servlet.function.*;

import java.util.*;

@Configuration
public class Routes {
    @Value("${userprofile.service.url}")
    private String userprofileServiceUrl;

    @Value("${image.service.url}")
    private String imageServiceUrl;

    @Value("${post.service.url}")
    private String postServiceUrl;

    @Bean
    public RouterFunction<ServerResponse> userprofileServiceRoute() {
        return GatewayRouterFunctions.route("userprofile_service")
                .route(RequestPredicates.path("/api/user/profile"), HandlerFunctions.http(userprofileServiceUrl))
                .route(RequestPredicates.path("/api/admin/user-profile/**"), HandlerFunctions.http(userprofileServiceUrl))
                .filter(addJwtHeadersFilter())
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> imageServiceRoute() {
        return GatewayRouterFunctions.route("image_service")
                .route(RequestPredicates.path("/api/user/images/**"), HandlerFunctions.http(imageServiceUrl))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> postServiceRoute() {
        return GatewayRouterFunctions.route("post_service")
                .route(RequestPredicates.path("api/user/posts/**"), HandlerFunctions.http(postServiceUrl))
                .build();
    }

    private HandlerFilterFunction<ServerResponse, ServerResponse> addJwtHeadersFilter() {
        return (request, next) -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.getPrincipal() instanceof Jwt) {
                Jwt jwt = (Jwt) authentication.getPrincipal();
                String sub = jwt.getSubject();

//              // 요청 헤더에 추가
                HttpServletRequest servletRequest = request.servletRequest();
                HeaderMapRequestWrapper requestWrapper = new HeaderMapRequestWrapper(servletRequest);
                requestWrapper.addHeader("X-User-Sub", sub);

                // 새로운 ServerRequest 생성
                ServerRequest newRequest = ServerRequest.create(requestWrapper, request.messageConverters());

                return next.handle(newRequest);
            } else {
                return next.handle(request);
            }
        };
    }
    // HeaderMapRequestWrapper 클래스 추가
    private static class HeaderMapRequestWrapper extends HttpServletRequestWrapper {

        private final Map<String, String> headerMap = new HashMap<>();

        public HeaderMapRequestWrapper(HttpServletRequest request) {
            super(request);
        }

        public void addHeader(String name, String value) {
            headerMap.put(name, value);
        }

        @Override
        public String getHeader(String name) {
            String headerValue = headerMap.get(name);

            if (headerValue != null) {
                return headerValue;
            }
            return super.getHeader(name);
        }

        @Override
        public Enumeration<String> getHeaderNames() {
            Set<String> set = new HashSet<>(headerMap.keySet());

            Enumeration<String> e = super.getHeaderNames();
            while (e.hasMoreElements()) {
                String n = e.nextElement();
                set.add(n);
            }

            return Collections.enumeration(set);
        }

        @Override
        public Enumeration<String> getHeaders(String name) {
            List<String> list = new ArrayList<>();

            if (headerMap.containsKey(name)) {
                list.add(headerMap.get(name));
            }

            Enumeration<String> e = super.getHeaders(name);
            while (e.hasMoreElements()) {
                list.add(e.nextElement());
            }

            return Collections.enumeration(list);
        }
    }
}
