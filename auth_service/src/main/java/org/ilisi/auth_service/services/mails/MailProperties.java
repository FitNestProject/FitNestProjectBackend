package org.ilisi.auth_service.services.mails;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "spring.mail")
public class MailProperties {
    private String host;
    private String port;
    private String username;
    private String password;

}
