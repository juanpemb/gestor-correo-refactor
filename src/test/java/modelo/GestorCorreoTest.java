package modelo;

import modelo.impl.GestorCorreo;
import modelo.impl.MailServerArgs;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;
import static org.mockito.Mockito.*;


public class GestorCorreoTest {

    private static final int SMTP_PORT = 1025;
    private Folder folderMock = mock(Folder.class);
    private Store storeMock = mock(Store.class);
    private Connector conector = mock(Connector.class);
    private Message fakeMessage;
    private Session session;
    private Message[] messagesExpected;

    @Before
    public void setup() throws MessagingException {
        session = Session.getDefaultInstance(System.getProperties());
        fakeMessage = fakeMessage(session);
        messagesExpected = (Message[]) Arrays.asList(fakeMessage(session))
                .toArray();
    }

    @Test
    public void mandaMensaje() throws Exception {
        Correo correo = arrangeCorreo();

        correo.mandaMensaje(fakeMessage);

        verify(conector, times(1)).getTransport("smtp");
    }


    @Test
    @Ignore
    public void mandaMensajeSSL() throws Exception {
        Correo correo = arrangeCorreo();

        correo.mandaMensajeSSL(fakeMessage);

        verify(conector,times(1)).getTransport("smtp");
    }

    @Test
    public void leeCorreos() throws Exception {
        Correo correo = arrangeCorreo();
        mockFolderToReturnOneMessage();

        Message[] correos = correo.leeCorreos();

        assertArrayEquals(messagesExpected,correos );
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

    private MailServerArgs arrangeGestorCorreoSmtp() {
        MailServerArgs serverArgs = mock(MailServerArgs.class);
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

    private Correo arrangeCorreo() throws MessagingException {
        MailServerArgs serverArgs = arrangeGestorCorreoSmtp();
        Correo correo = new GestorCorreo(serverArgs);

        correo.setConnector(conector);
        correo.setSesion(session);

        when(conector.getStore()).thenReturn(storeMock);
        when(conector.getSession()).thenReturn(session);
        when(conector.getFolder()).thenReturn(folderMock);
        when(conector.getTransport("smtp")).thenReturn(mock(Transport.class));

        return correo;
    }

    private void mockFolderToReturnOneMessage() throws MessagingException {
        when(folderMock.isOpen()).thenReturn(Boolean.TRUE);
        when(folderMock.getMessages()).thenReturn(messagesExpected);
    }


}