package com.ghtjr.gateway.filter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

import java.util.*;

public class HeaderMapRequestWrapper extends HttpServletRequestWrapper {
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
