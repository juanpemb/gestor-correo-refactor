package modelo.impl;

import modelo.Connector;

import javax.mail.*;
import java.util.Properties;

public class MailServerConnector implements Connector {

    private static final String POP_3 = "pop3";
    private static final String INBOX = "INBOX";
    private static final String MAIL_SMTP_HOST = "mail.smtp.host";
    private static final String MAIL_SMTP_AUTH = "mail.smtp.auth";

    private Session sesion;
    private Store store;
    private Folder folder;

    @Override
    public void setSession(Session session) {
        this.sesion = session;
    }

    @Override
    public Session getSession() {
        return sesion;
    }

    @Override
    public void setStore(Store store) {
        this.store = store;
    }

    @Override
    public Store getStore() {
        return this.store;
    }

    @Override
    public void setFolder(Folder folder) {
        this.folder = folder;
    }

    @Override
    public Folder getFolder() {
        return folder;
    }

    @Override
    public Transport getTransport(String smtp) throws MessagingException {
        return getSession().getTransport(smtp);
    }
    @Override
    public void initStore(String smtpServer,String popServer, String usuario, String password) {
        Properties p = System.getProperties();
        p.put(MAIL_SMTP_HOST, smtpServer);
        p.put(MAIL_SMTP_AUTH, Boolean.TRUE);
        sesion = Session.getDefaultInstance(p, null);
        try {
            store = sesion.getStore(POP_3);
            store.connect(popServer, usuario, password);
        } catch (javax.mail.MessagingException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void initFolder() {
        if (!store.isConnected()) {
            return;
        }
        try {
            store.isConnected();
            folder = store.getFolder(INBOX);
            folder.open(Folder.READ_WRITE);
        } catch (javax.mail.MessagingException e) {
            System.err.println("error en initFolder");
            e.printStackTrace();
        }
    }
    @Override
    public void closeStore() {
        try {
            store.close();
        } catch (javax.mail.MessagingException e) {
            System.err.println("error en closeStore");
            e.printStackTrace();
        }

    }
    @Override
    public void closeFolder() {
        try {
            folder.close(true);
        } catch (javax.mail.MessagingException e) {
            System.err.println("error en closeFolder");
            e.printStackTrace();
        }
    }
}
