package modelo;

import javax.mail.*;

public interface Connector {

    public void setSession(Session session);
    public Session getSession();
    public void setStore(Store store);
    public Store getStore();
    public void setFolder(Folder folder);
    public Folder getFolder();
    public Transport getTransport(String smtp) throws MessagingException;
    public void initStore(String smtpServer,String popServer, String usuario, String password);

    void initFolder();

    void closeStore();

    void closeFolder();
}
