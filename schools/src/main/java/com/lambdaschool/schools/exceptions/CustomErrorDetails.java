package com.lambdaschool.schools.exceptions;

import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class CustomErrorDetails extends DefaultErrorAttributes {
    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
        Map<String, Object> error = super.getErrorAttributes(webRequest, includeStackTrace);
        Map<String, Object> details = new LinkedHashMap<>();

        details.put("title", error.get("error"));
        details.put("status", error.get("status"));
        details.put("details", error.get("message"));
        details.put("timestamp", error.get("timestamp"));

        details.put("developer message", "path"+ error.get("path"));

        details.put("errors", "");

        return details;
    }
}
