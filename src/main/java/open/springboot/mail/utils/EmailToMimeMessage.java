/*
 * Copyright 2012-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package open.springboot.mail.utils;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import open.springboot.mail.exceptions.EmailConversionException;
import open.springboot.mail.model.Email;
import open.springboot.mail.model.impl.EmailAttachmentImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.function.Function;

import static com.google.common.base.Optional.fromNullable;
import static java.util.Objects.nonNull;
import static java.util.Optional.ofNullable;

@Component
@Slf4j
public class EmailToMimeMessage implements Function<Email, MimeMessage> {

    private JavaMailSender javaMailSender;

    @Autowired
    public EmailToMimeMessage(final @NonNull JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public MimeMessage apply(final Email email) {
        final MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        final MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage,
                fromNullable(email.getEncoding()).or(Charset.forName("UTF-8")).displayName());

        try {
            messageHelper.setFrom(email.getFrom());
            if (ofNullable(email.getReplyTo()).isPresent()) {
                messageHelper.setReplyTo(email.getReplyTo());
            }
            if (ofNullable(email.getTo()).isPresent()) {
                for (final InternetAddress address : email.getTo()) {
                    messageHelper.addTo(address);
                }
            }
            if (ofNullable(email.getCc()).isPresent()) {
                for (final InternetAddress address : email.getCc()) {
                    messageHelper.addCc(address);
                }
            }
            if (ofNullable(email.getBcc()).isPresent()) {
                for (final InternetAddress address : email.getBcc()) {
                    messageHelper.addBcc(address);
                }
            }
            if (ofNullable(email.getAttachments()).isPresent()) {
                for (final EmailAttachmentImpl attachment : email.getAttachments()) {
                    try {
                        messageHelper.addAttachment(attachment.getAttachmentName(),
                                attachment.getInputStream(), attachment.getContentType().getType());
                    } catch (IOException e) {
                        log.error("Error while converting Email to MimeMessage");
                        throw new EmailConversionException(e);
                    }
                }
            }
            messageHelper.setSubject(ofNullable(email.getSubject()).orElse(""));
            messageHelper.setText(ofNullable(email.getBody()).orElse(""));

            if (nonNull(email.getSentAt())) {
                messageHelper.setSentDate(email.getSentAt());
            }
        } catch (MessagingException e) {
            log.error("Error while converting Email to MimeMessage");
            throw new EmailConversionException(e);
        }


        return mimeMessage;
    }


}
