package modelo.impl;

public class MailServerArgs {
    private static final String DEFAULT_SMTP_PORT = "25";
    private static final String DEFAULT_POP_PORT = "110";

    private final String h, puertoSMTP, he, puertoPOP, de, usuario, pwd;

    public MailServerArgs(String host,
                          String puertoSMTP,
                          String he,
                          String puertoPOP,
                          String de,
                          String usuario,
                          String pwd) {
        this.h = host;
        this.he = he;
        this.puertoSMTP = puertoSMTP == null ? DEFAULT_SMTP_PORT : puertoSMTP;
        this.puertoPOP = puertoPOP == null ? DEFAULT_POP_PORT : puertoPOP;
        this.de = de;
        this.usuario = usuario;
        this.pwd = pwd;
    }

    public String getHost() {
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

    public String getDe() {
        return de;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getPwd() {
        return pwd;
    }
}
