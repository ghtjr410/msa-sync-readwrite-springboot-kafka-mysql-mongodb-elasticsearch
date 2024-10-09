package com.ghtjr.gateway.util;

import lombok.Getter;

@Getter
public enum HeaderType {
    SUB("X-User-Sub"),
    ROLES("X-User-Roles");

    private final String headerName;

    HeaderType(String headerName) {
        this.headerName = headerName;
    }
}
