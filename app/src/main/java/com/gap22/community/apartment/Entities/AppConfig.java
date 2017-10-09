package com.gap22.community.apartment.Entities;

/**
 * Created by NARAYANAN on 10/9/2017.
 */

public class AppConfig {

    public String email_user_id;
    public String email_password;

    public AppConfig() {

    }

    public AppConfig(String email_user_id, String email_password) {
        this.email_password = email_password;
        this.email_user_id = email_user_id;
    }
}
