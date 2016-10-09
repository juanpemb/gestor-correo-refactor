package factories;

import javax.mail.Session;
import java.util.Properties;

public class SessionHelper{

    public static final String MAIL_SMTP_HOST = "mail.smtp.host";
    private static final String MAIL_SMTP_AUTH = "mail.smtp.auth";

    public static Session obtainSmtpSession(String smtpServer) {
        Properties p = System.getProperties();
        p.put(MAIL_SMTP_HOST, smtpServer);
        p.put(MAIL_SMTP_AUTH, Boolean.TRUE);
        return Session.getDefaultInstance(p, null);
    }
}
