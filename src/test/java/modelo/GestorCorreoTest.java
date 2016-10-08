package modelo;

import modelo.impl.GestorCorreo;
import modelo.impl.MailServerArgs;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.subethamail.wiser.Wiser;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class GestorCorreoTest {

    private final Session session = Session.getDefaultInstance(System.getProperties());

    private Wiser wiser = new Wiser();
    private Message fakeMessage;

    @Before
    public void setup() throws MessagingException {
        wiser.setPort(1025);
        wiser.setHostname("localhost");
        wiser.start();
        fakeMessage = fakeMessage(session);
    }

    @After
    public void teardown(){
        wiser.stop();
    }

    @Test
    public void mandaMensaje() throws Exception {
        Correo correo = arrangeCorreo();

        correo.mandaMensaje(fakeMessage);
    }

    private Correo arrangeCorreo() {
        MailServerArgs serverArgs = arrangeGestorCorreoSmtp();

        Correo correo = new GestorCorreo(serverArgs);
        correo.setSession(session);
        return correo;
    }

    private MailServerArgs arrangeGestorCorreoSmtp() {
        MailServerArgs serverArgs= mock(MailServerArgs.class);
        when(serverArgs.getHost()).thenReturn("localhost");
        when(serverArgs.getPuertoSMTP()).thenReturn("1025");
        return serverArgs;
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

    @Test
    public void mandaMensajeSSL() throws Exception {

    }

    @Test
    public void leeCorreos() throws Exception {

    }

    @Test
    public void leeCorreo() throws Exception {

    }

    @Test
    public void getAdjuntos() throws Exception {

    }

    @Test
    public void saveFile() throws Exception {

    }

    @Test
    public void responderMensaje() throws Exception {

    }

    @Test
    public void reenviarMensaje() throws Exception {

    }

    @Test
    public void guardaMensaje() throws Exception {

    }

    @Test
    public void abreMensajes() throws Exception {

    }

    @Test
    public void borrarMensaje() throws Exception {

    }

}