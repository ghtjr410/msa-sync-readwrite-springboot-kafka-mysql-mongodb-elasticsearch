package com.ghtjr.gateway.routes;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

@Configuration
public class UserServiceRouteConfig {
    @Value("${user.service.url}")
    private String userServiceUrl;

    @Value("${user.service.path.pattern}")
    private String userServicePathPattern;

    @Bean
    public RouterFunction<ServerResponse> userServiceRoute() {
        return GatewayRouterFunctions.route("user_service")
                .route(RequestPredicates.path(userServicePathPattern), HandlerFunctions.http(userServiceUrl))
                .build();
    }
}
