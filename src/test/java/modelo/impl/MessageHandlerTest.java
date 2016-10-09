package modelo.impl;

import org.junit.Test;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import static org.junit.Assert.assertNotNull;

public class MessageHandlerTest {
    private Session session = Session.getDefaultInstance(System.getProperties());

    @Test
    public void leeCorreo() throws Exception {
        MessageHandler handler = new MessageHandler(session);

        String plainMessage = handler.leeCorreo(fakeMessage(session));

        assertNotNull(plainMessage);
    }

    private Message fakeMessage(Session session) throws MessagingException {
        Message m = new MimeMessage(session);
        m.addRecipient(Message.RecipientType.TO, new InternetAddress("test@test.com"));
        m.setFrom();
        m.setSubject("asunto");
        MimeMultipart mmp = new MimeMultipart();
        BodyPart bp = new MimeBodyPart();
        bp.setContent("text", "text/plain");
        mmp.addBodyPart(bp);
        m.setContent(mmp);
        Flags fs = m.getFlags();
        return m;
    }

}