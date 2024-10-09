package com.ghtjr.gateway.routes;

import com.ghtjr.gateway.filter.JwtHeaderFilter;
import com.ghtjr.gateway.util.HeaderType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import java.util.EnumSet;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class UserprofileServiceRouteConfig {
    @Value("${userprofile.service.url}")
    private String userprofileServiceUrl;

    @Value("${userprofile.service.path.pattern}")
    private String userprofileServicePathPattern;

    private final JwtHeaderFilter jwtHeaderFilter;

    @Bean
    public RouterFunction<ServerResponse> userprofileServiceRoute() {
        Set<HeaderType> headersToInclude = EnumSet.of(HeaderType.SUB);

        return GatewayRouterFunctions.route("userprofile_service")
                .route(RequestPredicates.path(userprofileServicePathPattern), HandlerFunctions.http(userprofileServiceUrl))
                .filter(jwtHeaderFilter.addJwtHeadersFilter(headersToInclude))
                .build();
    }
}
