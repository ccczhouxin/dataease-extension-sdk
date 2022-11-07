package io.dataease.plugins.datasource.request;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class ApplicationDefinitionRequest {
    private List<Map<String, String>> headers = new ArrayList<>();
    private Map<String, Object> body = new HashMap<>();
    private AuthManager authManager = new AuthManager();


    @Data
    public static class AuthManager{
        private String password;
        private String username;
        private String verification = "";
    }
}
