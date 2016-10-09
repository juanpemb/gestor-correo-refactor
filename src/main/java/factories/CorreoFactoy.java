package factories;

import modelo.Connector;
import modelo.Correo;
import modelo.impl.GestorCorreo;
import modelo.impl.MailServerArgs;
import modelo.impl.MailServerConnector;

public class CorreoFactoy {

    private static Correo correo;

    public static Correo getCorreo(MailServerArgs args){
        if(correo == null){
            correo = new GestorCorreo(args);
            Connector connector = new MailServerConnector();
            connector.initStore(args.getHost(),args.getHe(),args.getUsuario(),args.getPwd());
            connector.initFolder();
        }
        return correo;
    }

}
