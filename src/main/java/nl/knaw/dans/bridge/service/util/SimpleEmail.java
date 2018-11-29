/**
 * Copyright (C) 2018 DANS - Data Archiving and Networked Services (info@dans.knaw.nl)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package nl.knaw.dans.bridge.service.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class SimpleEmail {
    private static final Logger LOG = LoggerFactory.getLogger(SimpleEmail.class);
    @Autowired
    private Environment env;

    @Autowired
    private JavaMailSender mailSender;


    public void sendToAdmin(final String subject, final String message) {
        final SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(Objects.requireNonNull(env.getProperty("bridge.apps.support.email.send.to")).split(","));
        email.setSubject(subject);
        email.setText(message );
        email.setFrom(env.getProperty("bridge.apps.support.email.from"));
        LOG.info("Trying to send email to: " + env.getProperty("bridge.apps.support.email.send.to"));
        mailSender.send(email);
        LOG.info("Email send to: " + env.getProperty("bridge.apps.support.email.send.to"));
        LOG.info("Email message: " + message);
    }
}
