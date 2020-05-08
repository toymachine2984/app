package com.globerce.management.service;


import com.globerce.management.entity.system.User;

import java.util.Locale;

public interface MailService {

    void sendVerificationToken(User user, String token, Locale locale);
}
