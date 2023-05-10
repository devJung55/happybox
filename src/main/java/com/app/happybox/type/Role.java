package com.app.happybox.type;

public enum Role {
    ADMIN, MEMBER, WELFARE, DISTRIBUTOR;

    private static final String ROLE_PREFIX = "ROLE_";

    public String getSecurityRole(){
        return ROLE_PREFIX + name();
    }
}
