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
public class PostServiceRouteConfig {
    @Value("${post.service.url}")
    private String postServiceUrl;

    @Value("${post.service.path.pattern}")
    private String postServicePathPattern;

    @Bean
    public RouterFunction<ServerResponse> postServiceRoute() {
        return GatewayRouterFunctions.route("post_service")
                .route(RequestPredicates.path(postServicePathPattern), HandlerFunctions.http(postServiceUrl))
                .build();
    }
}

