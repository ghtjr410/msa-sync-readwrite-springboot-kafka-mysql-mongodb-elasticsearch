package com.ghtjr.gateway.util;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class JwtUtil {
    public static List<String> extractRealmRoles(Map<String, Object> realmAccess) {
        if (realmAccess == null || !realmAccess.containsKey("roles")) {
            return Collections.emptyList();
        }
        return (List<String>) realmAccess.get("roles");
    }
}
