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
public class ImageServiceRouteConfig {
    @Value("${image.service.url}")
    private String imageServiceUrl;

    @Value("${image.service.path.pattern}")
    private String imageServicePathPattern;

    @Bean
    public RouterFunction<ServerResponse> imageServiceRoute() {
        return GatewayRouterFunctions.route("image_service")
                .route(RequestPredicates.path(imageServicePathPattern), HandlerFunctions.http(imageServiceUrl))
                .build();
    }
}
