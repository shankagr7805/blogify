package com.shank.Blogify.util.constants;

public enum Privilages {
    RESET_ANY_USER_PASSWORD(1L, "RESET_ANY_USER_PASSWORD"),
    ACCESS_ADMIN_PANEL(2L, "ACCESS_ADMIN_PANEL");

    private Long id; 
    private String authorityString;

    Privilages(Long id, String authorityString) { 
        this.id = id; 
        this.authorityString = authorityString;    
    }

    public Long getId() { 
        return id;
    }

    public String getAuthorityString() {
        return authorityString;
    }
}
