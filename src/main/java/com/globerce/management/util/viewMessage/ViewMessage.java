package com.globerce.management.util.viewMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class ViewMessage {


    private String type;
    private String message;
    private static MessageSource messageSource;

    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        ViewMessage.messageSource = messageSource;
    }

    public ViewMessage() {
    }

    public ViewMessage(String type, String message) {
        this.type = type;
        this.message = message;
    }

    public static ViewMessage.ViewMessageBuilder getMessage(String type, Object[] args) {
        return builder().message(type, messageSource, args != null ? args : new Object[]{});
    }

    public static ViewMessage getMessage(String type, boolean isSuccess, Locale locale) {
        return builder()
                .locale(locale)
                .isSuccess(isSuccess)
                .message(type, messageSource, null)
                .build();
    }

    public static ViewMessage getMessage(String type, boolean isSuccess, Object[] args, Locale locale) {
        return builder()
                .locale(locale)
                .isSuccess(isSuccess)
                .message(type, messageSource, args)
                .build();
    }

    private static ViewMessage.ViewMessageBuilder builder() {
        return new ViewMessage.ViewMessageBuilder();
    }


    public String getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }


    public static class ViewMessageBuilder {
        private static String type;
        private static String message;
        private static Locale locale = new Locale("en", "US");

        public ViewMessage.ViewMessageBuilder isSuccess(boolean flag) {
            type = flag ? "success" : "error";
            return this;
        }


        public ViewMessage.ViewMessageBuilder message(String type, MessageSource messageSource, Object[] args) {
            message = messageSource.getMessage(type, args != null ? args : new Object[]{}, locale);
            return this;
        }

        public ViewMessage.ViewMessageBuilder locale(Locale locale) {
            ViewMessageBuilder.locale = locale;
            return this;
        }

        public ViewMessage build() {
            return new ViewMessage(type, message);
        }
    }


}
