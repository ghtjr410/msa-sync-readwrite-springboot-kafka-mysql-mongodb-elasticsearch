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
public class Routes {
    @Value("${userprofile.service.url}")
    private String userprofileServiceUrl;

    @Value("${image.service.url}")
    private String imageServiceUrl;

    @Bean
    public RouterFunction<ServerResponse> userprofileServiceRoute() {
        return GatewayRouterFunctions.route("userprofile_service")
                .route(RequestPredicates.path("/api/user/user-profile/**"), HandlerFunctions.http(userprofileServiceUrl))
                .route(RequestPredicates.path("/api/admin/user-profile/**"), HandlerFunctions.http(userprofileServiceUrl))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> imageServiceRoute() {
        return GatewayRouterFunctions.route("image_service")
                .route(RequestPredicates.path("/api/user/images/**"), HandlerFunctions.http(imageServiceUrl))
                .build();
    }
}
