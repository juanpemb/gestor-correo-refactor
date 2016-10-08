package modelo;

public class MailServerSession {
    private static final String DEFAULT_SMTP_PORT = "25";
    private static final String DEFAULT_POP_PORT = "110";

    private final String h;
    private final String puertoSMTP;
    private final String he;
    private final String puertoPOP;
    private final String d;
    private final String u;
    private final String pwd;

    public MailServerSession(String host, String puertoSMTP, String he, String puertoPOP, String de, String usuario, String pwd) {
        this.h = host;
        this.puertoSMTP = puertoSMTP == null? DEFAULT_SMTP_PORT: puertoSMTP;
        this.he = he;
        this.puertoPOP = puertoPOP == null ?  DEFAULT_POP_PORT: puertoPOP;
        this.d = de;
        this.u = usuario;
        this.pwd = pwd;
    }

    public String getH() {
        return h;
    }

    public String getPuertoSMTP() {
        return puertoSMTP;
    }

    public String getHe() {
        return he;
    }

    public String getPuertoPOP() {
        return puertoPOP;
    }

    public String getD() {
        return d;
    }

    public String getUsuario() {
        return u;
    }

    public String getPwd() {
        return pwd;
    }
}
