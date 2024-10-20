package com.se.sos.config;

public class EndpointProperties {
    public static final String[] PUBLIC_EP = {
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
            "/hospital",
    };

    public static final String[] HOS_EP = {
            "/hospital/**",
            "/reception/{receptionId}",
            "/reception/{receptionId}/arrival",
            "/reception/{receptionId}/comment"
    };

    public static final String[] ADMIN_EP = {
            "admin/**"
    };
}