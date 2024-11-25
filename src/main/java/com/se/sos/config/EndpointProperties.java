package com.se.sos.config;

public class EndpointProperties {
    public static final String[] PUBLIC_EP = {
            "swagger-ui/**",
            "swagger-resources/**",
            "v3/api-docs/**",
            "/login/**",
            "/signup/**",
            "/test/**",
            "/reissue-token",
            "/dup-check",
            "/reception/{receptionId}/guest",
    };

    public static final String[] AMB_EP = {
            "/ambulance/**",
            "/reception",
            "/reception/{receptionId}/re",
    };

    public static final String[] HOS_EP = {
            "/hospital/{hospitalId}/reception",
            "/hospital/{hospitalId}/emergencyStatus",
            "/hospital/{hospitalId}/reception/accept",
            "/reception/{receptionId}/arrival",
            "/reception/{receptionId}/comment"
    };

    public static final String[] ADMIN_EP = {
            "admin/**"
    };
}