package modelo;

import com.sun.mail.smtp.SMTPSSLTransport;
import utilidades.MensajeXML;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.*;
import java.util.Date;
import java.util.LinkedList;
import java.util.Properties;
import java.util.Vector;

public class GestorCorreo implements Correo {

    private String host, hostEntrante;
    private String puertoSMTP, puertoPOP;
    //	private String para;
    private String de;
    private String usuario;
    private String password;
    private Session sesion;
    private Store store;    //es el almacen de la clase
    private Folder folder;  //es la carpeta de la clase

    public GestorCorreo(String h, String puertoSMTP, String he, String puertoPOP, String d, String u, String pwd) {

        host = h;
        de = d;
        usuario = u;
        password = pwd;
        hostEntrante = he;
        this.puertoSMTP = puertoPOP;
        this.puertoPOP = puertoPOP;

    }

    public GestorCorreo(String h, String he, String d, String u, String pwd) {
        host = h;
        de = d;
        usuario = u;
        password = pwd;
        hostEntrante = he;
        puertoSMTP = "25";
        puertoPOP = "110";

    }

    public void setHost(String a) {
        host = a;
    }

    public void setHostEntrante(String a) {
        hostEntrante = a;
    }

    public void setDe(String a) {
        de = a;
    }

    public void setPassword(String a) {
        password = a;
    }

    public void setUsuario(String a) {
        usuario = a;
    }

    public String getHost(String a) {
        return host;
    }

    public String getHostEntrante(String a) {
        return hostEntrante;
    }

    public String getDe(String a) {
        return de;
    }

    public String getPassword(String a) {
        return password;
    }

    public String getUsuario(String a) {
        return usuario;
    }

    public Folder getFolder() {
        return folder;
    }

    public void setFolder(Folder folder) {
        this.folder = folder;
    }

    public String getPuertoPOP() {
        return puertoPOP;
    }

    public void setPuertoPOP(String puertoPOP) {
        this.puertoPOP = puertoPOP;
    }

    public String getPuertoSMTP() {
        return puertoSMTP;
    }

    public void setPuertoSMTP(String puertoSMTP) {
        this.puertoSMTP = puertoSMTP;
    }

    public Session getSesion() {
        return sesion;
    }

    public void setSesion(Session sesion) {
        this.sesion = sesion;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public String getDe() {
        return de;
    }

    public String getHost() {
        return host;
    }

    public String getHostEntrante() {
        return hostEntrante;
    }

    public String getPassword() {
        return password;
    }

    public String getUsuario() {
        return usuario;
    }

    public void initStore() {
        Properties p = System.getProperties();
        p.put("mail.smtp.host", host);
        p.put("mail.smtp.auth", "true");
        sesion = Session.getDefaultInstance(p, null);
        try {
            store = sesion.getStore("pop3");
            System.err.println("Debug ::::: " + hostEntrante + " - " + usuario + " - " + password);
            store.connect(hostEntrante, usuario, password);
            System.out.println("Conecta con el servidor");
        } catch (javax.mail.MessagingException e) {
            System.err.println("error en leeMensajes");
            e.printStackTrace();
        }


    }

    public void initFolder() {
        try {
            if (!store.isConnected()) {
                System.out.println("El store no esta conectado, porfavor hable con el administrador");
                return;
            }
            store.isConnected();
            folder = store.getFolder("INBOX");
            folder.open(Folder.READ_WRITE);
            if (folder.isOpen()) System.out.println("Folder abierto");

        } catch (javax.mail.MessagingException e) {
            System.err.println("error en initFolder");
            e.printStackTrace();
        }
    }

    public void closeStore() {
        try {
            store.close();
        } catch (javax.mail.MessagingException e) {
            System.err.println("error en closeStore");
            e.printStackTrace();
        }

    }

    public void closeFolder() {
        try {
            folder.close(true);
        } catch (javax.mail.MessagingException e) {
            System.err.println("error en closeFolder");
            e.printStackTrace();
        }
    }

    public Message creaMensaje(String to, String asunto, String texto) {
        MimeMessage m = new MimeMessage(sesion);

        try {
            m.setFrom();
            m.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            m.setSubject(asunto);
            MimeMultipart mmp = new MimeMultipart();
            BodyPart bp = new MimeBodyPart();
            bp.setContent(texto, "text/plain");
            mmp.addBodyPart(bp);
            m.setContent(mmp);
            Flags fs = m.getFlags();

            System.out.println("Numero de baderas soportadas: " + fs.getSystemFlags().length);

        } catch (Exception e) {
            System.err.println("en creaMensaje\n");
            System.out.println("Gestor Correo :CreaMensaje: " + e.getMessage());
        }
        return m;


    }

    public Message incluirHTML(Message msj, String texto) {
        try {
            Multipart mp = (Multipart) msj.getContent();
            BodyPart p = new MimeBodyPart();
            p.setContent(texto, "text/html");

            mp.addBodyPart(p);

            Message m = new MimeMessage(sesion);
            m.setFrom(msj.getFrom()[0]);
            m.setSubject(msj.getSubject());

            m.addRecipients(Message.RecipientType.TO, msj.getRecipients(Message.RecipientType.TO));

            m.setContent(mp);

            return m;
        } catch (Exception e) {
            System.err.println("Error en incluir HTML\n");
            e.printStackTrace();
            return null;
        }
    }

    public Message creaMensajeHTML(String to, String asunto, String texto) {
        MimeMessage m = new MimeMessage(sesion);
        try {
            m.setFrom(new InternetAddress(de));
            m.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            m.setSubject(asunto);
            BodyPart bp = new MimeBodyPart();
            bp.setContent(texto, "text/html");
            MimeMultipart mmp = new MimeMultipart();
            mmp.addBodyPart(bp);

            m.setContent(mmp);
            return m;

        } catch (Exception e) {
            System.out.println("error en crea mensajeHTML");
            e.printStackTrace();
            return null;
        }
    }


    public void mandaMensaje(Message m)   //manda un mensaje
    {
        try {
            Transport t = sesion.getTransport("smtp");
            t.connect(host, usuario, password);//conecta con el servidor smtp
            if (m == null) System.out.println("El mensaje es nulo");
            t.sendMessage(m, m.getAllRecipients());
            t.close();

        } catch (javax.mail.MessagingException e) {

            System.err.println("Error en mandaMensaje");
            e.printStackTrace();
        }

    }

    public void mandaMensaje(MensajeXML msjXML) {
        Message message = this.creaMensaje(msjXML.getDestino(), msjXML.getAsunto(), msjXML.getTexto());
        if (msjXML.getFile() != null) {
            message = this.incluirArchivo(message, msjXML.getFile().getAbsolutePath());
        }
        this.mandaMensaje(message);
        System.out.println("El mensajeXML fue enviado");
    }

    public void mandaMensajeSSL(Message m) {
        try {
            SMTPSSLTransport t = new SMTPSSLTransport(sesion, new URLName(host));
            t.connect(host, usuario, password);//conecta con el servidor smtp
            System.out.println("Conecta con el servidorSSL\n");
            t.sendMessage(m, m.getAllRecipients());
            System.out.println("mensaje enviado\n");
            t.close();
        } catch (MessagingException e) {
            System.err.println("Error en mandaMensajeSSL");
            e.printStackTrace();
        }
    }

    public Message[] leeCorreos() {
        Properties p = System.getProperties();
        p.put("mail.smtp.host", host);
        Session sesion = Session.getDefaultInstance(p, null);
        try {

            if (folder.isOpen()) System.out.println("La carpeta esta abierta");

            Message[] m = folder.getMessages();
            Message[] respuestaAuxiliar = new Message[m.length];
            Message[] respuesta;
            int longitud = 0;
            for (int i = 0; i < m.length; i++) {
                System.out.println("Bandera Deleted puesta en true");
                m[i].setFlag(Flags.Flag.DELETED, true);
                respuestaAuxiliar[i] = m[i];
                longitud++;
            }

            respuesta = new Message[longitud];

            for (int i = 0; i < longitud; i++) {
                respuesta[i] = respuestaAuxiliar[i];
            }
            System.out.println("lectura de " + m.length + " Correos");
            return m;

        } catch (javax.mail.MessagingException e) {
            System.err.println("Error al leer correo");
            e.printStackTrace();
            System.err.println("Error al leer correo");
            return null;
        }

    }

    public Message incluirImagen(Message msj, String imagen) {
        try {
            Message mensaje = new MimeMessage(sesion);

            mensaje.setSubject(msj.getSubject());
            mensaje.setFrom((msj.getFrom()[0]));
            mensaje.addRecipients(Message.RecipientType.TO, msj.getRecipients(Message.RecipientType.TO));

            BodyPart bp = new MimeBodyPart();

            String htmlText = "<h1>imagen</h1>" + "<img src=cid:\"" + imagen + "\">";    //la imagen es incluida c0mo hipertexto
            bp.setContent(htmlText, "text/html");

            MimeMultipart mp = (MimeMultipart) msj.getContent();
            mp.addBodyPart(bp);

            bp = new MimeBodyPart();

            DataSource fds = new FileDataSource(imagen);
            bp.setDataHandler(new DataHandler(fds));
            bp.setHeader("Content-ID", "memememe");

            mp.addBodyPart(bp);
            mensaje.setContent(mp);

            return mensaje;

        } catch (Exception e) {
            System.out.println("error en incluye imagen");
            e.printStackTrace();
            return null;
        }
    }

    public Message incluirArchivo(Message m, String filename) {
        try {
            Message resultado = new MimeMessage(sesion);
            resultado.setSubject("Fwd: " + m.getSubject());
            resultado.addRecipient(Message.RecipientType.TO, m.getAllRecipients()[0]);

            BodyPart mbp = new MimeBodyPart();

            Multipart mp = new MimeMultipart();

            mbp.setDataHandler(m.getDataHandler());
            mp.addBodyPart(mbp);

            mbp = new MimeBodyPart();
            DataSource source = new FileDataSource(filename);
            mbp.setDataHandler(new DataHandler(source));
            mbp.setFileName(filename);
            mp.addBodyPart(mbp);


            resultado.setContent(mp);

            return resultado;
        } catch (MessagingException me) {
            System.err.println("Error en incluirArchivo ");
            me.printStackTrace();
            return null;
        }

    }

    public void recogeArchivo(Message m) {
        try {
            Multipart mp = (Multipart) m.getContent();
            for (int i = 0, n = mp.getCount(); i < n; i++) {
                Part p = mp.getBodyPart(i);
                String disposition = p.getDisposition();
                if ((disposition != null) && (disposition.equals(Part.ATTACHMENT)) || (disposition.equals(Part.INLINE))) {
                    System.out.println("estoy en recoge archivo\nllamo a saveFile");
                    saveFile(p.getFileName(), p.getInputStream());
                }
            }
        } catch (Exception e) {
            System.err.println("Error en recoge archivo");
            e.printStackTrace();
        }
    }

    public MensajeXML toMensajeXML(Message m) throws MessagingException {
        MensajeXML mensaje;
        InternetAddress origenA[] = (InternetAddress[]) m.getFrom();
        InternetAddress destinoA[] = (InternetAddress[]) m.getRecipients(Message.RecipientType.TO);
        String origen = origenA[0].getAddress(), destino = destinoA[0].getAddress(), asunto = m.getSubject(), tipo, texto = leeCorreo(m), adjunto;

        if (getAdjuntos(m)[0] != null) {
            adjunto = getAdjuntos(m)[0];
            mensaje = new MensajeXML(origen, destino, MensajeXML.ENTRADA, asunto, "Leido", texto, new Date().toString(), new File(adjunto));
        } else {
            mensaje = new MensajeXML(origen, destino, MensajeXML.ENTRADA, asunto, "Leido", texto, new Date().toString());
        }

        return mensaje;
    }

    public String leeCorreo(Message m) {
        try {
            String resultado = "";    //esta es la cadena en l que se pondra el mensaje de texto o html
            Multipart mp = (Multipart) m.getContent();

            for (int i = 0, n = mp.getCount(); i < n; i++) {
                Part p = mp.getBodyPart(i);
                String disposition = p.getDisposition();

                if (disposition == null) {
                    if (p.isMimeType("text/plain")) {
                        resultado = resultado + (String) p.getContent();
                    } else if (p.isMimeType("text/html")) {
                        resultado = resultado + p.getContent();

                        System.out.println("es texto html");
                    } else {
                        System.out.println("No es texto plano ni html");
                    }
                } else if ((disposition != null) && (disposition.equals(Part.ATTACHMENT)) || (disposition.equals(Part.INLINE))) //es un atachement
                {
                    System.out.println("Es un atachament\n");


                    saveFile(p.getFileName(), p.getInputStream());
                }
            }
            System.out.println("Mensaje recibido\n" + resultado);

            return resultado;
        } catch (MessagingException me) {
            System.err.println("Error en leeCorreo\n");
            me.printStackTrace();
            return null;
        } catch (IOException ioe) {
            System.err.println("Error en leeCorreo\n ");
            ioe.printStackTrace();
            return null;
        }

    }

    public String[] getAdjuntos(Message m) {
        try {
            String[] resultado;    //esta es la cadena en l que se pondra el mensaje de texto o html
            LinkedList lista = new LinkedList();
            Multipart mp = (Multipart) m.getContent();
            int j = 0;
            for (int i = 0, n = mp.getCount(); i < n; i++) {
                Part p = mp.getBodyPart(i);
                String disposition = p.getDisposition();
                if ((disposition != null) && (disposition.equals(Part.ATTACHMENT))) {
                    lista.add(p.getFileName());
                    j++;
                }
            }

            resultado = new String[j + 1];
            int i = 0;
            while (i < j) {
                resultado[i] = (String) lista.get(i);
                i++;
            }

            return resultado;

        } catch (Exception e) {
            System.out.println("GestorCorreo: getAdjuntos: " + e.getMessage());
            return null;
        }

    }

    protected void saveFile(String filename, InputStream is) throws IOException {
        if (filename == null || is == null) {
            System.err.println("No guarda el adjunto");
            return;
        }
        String name, ext;
        name = getName(filename);
        ext = getExtension(filename);

        File f = new File(filename);
        for (int i = 0; f.exists(); i++) {
            f = new File(name + i + '.' + ext);
        }

        FileOutputStream fos = new FileOutputStream(f);

        int c;
        while ((c = is.read()) != -1) {
            fos.write(c);
            fos.flush();

        }

        is.close();
        fos.close();


    }

    public void responderMensaje(Message m, String para, String texto) {
        try {
            MimeMessage respuesta = (MimeMessage) m.reply(true);
            InternetAddress[] a = new InternetAddress[1];
            a[0] = new InternetAddress(para);
            respuesta.setReplyTo(a);
            respuesta.setText(texto);
            Transport t = sesion.getTransport("smtp");
            t.connect(host, usuario, password);//conecta con el servidor smtp
            t.sendMessage(m, m.getAllRecipients());
            t.close();
        } catch (MessagingException e) {
            System.out.println("Error en responderMensajes\n");
            e.printStackTrace();

        }


    }

    public void reenviarMensaje(Message m, String para) {
        try {
            MimeMessage enviar = new MimeMessage(sesion);

            enviar.setSubject("Fwd: " + m.getSubject());
            enviar.setFrom(new InternetAddress(de));
            enviar.addRecipient(Message.RecipientType.TO, new InternetAddress(para));

            BodyPart mbp = new MimeBodyPart();
            mbp.setText("incluyo mi mensaje\n");

            Multipart mp = new MimeMultipart();

            mp.addBodyPart(mbp);

            mbp = new MimeBodyPart();
            mbp.setDataHandler(m.getDataHandler());
            mp.addBodyPart(mbp);


            enviar.setContent(mp);

            Transport t = sesion.getTransport("smtp");
            t.connect(host, usuario, password);//conecta con el servidor smtp
            t.sendMessage(enviar, enviar.getAllRecipients());
            t.close();

        } catch (MessagingException me) {
            System.err.println("Error en reenviarMensaje\n");
            me.printStackTrace();
        }


    }

    public void guardaMensaje(Message m, String s) {
        try {
            Vector v = abreMensajes(s);
            v.add(m);

            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(s));
            oos.reset();
            oos.writeObject(v);
            oos.close();

        } catch (IOException ioe) {
            System.err.println("Error en guardaMensaje\n");
            ioe.printStackTrace();
        }
    }

    public Vector abreMensajes(String s) {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(s));
            Vector v = (Vector) ois.readObject();
            ois.close();
            return v;

        } catch (Exception e) {
            System.out.println("Error en abreMensaje\n");
            e.printStackTrace();
            return null;
        }
    }


    public void borrarMensaje(int i) {
        try {
            Message m = folder.getMessage(i);
            m.setFlag(Flags.Flag.DELETED, true);
        } catch (javax.mail.MessagingException e) {
            System.out.println("Error en borrarMensaje");
            e.printStackTrace();
        }


    }

    private String getName(String f) {
        if (f == null)
            return "";

        String aux = f;
        String nombre = null;
        int i = aux.lastIndexOf('.');

        if (i > 0 && i < aux.length() - 1) {
            nombre = aux.substring(0, i);
        }
        return nombre;
    }

    private String getExtension(String f) {
        if (f == null)
            return "";
        String ext = null;
        String s = f;
        int i = s.lastIndexOf('.');

        if (i > 0 && i < s.length() - 1) {
            ext = s.substring(i + 1).toLowerCase();
        }
        return ext;
    }


    public static void main(String args[]) throws Exception {
        System.out.println("Prueba de gestion de correo\n");
        //	GestionCorreo gc=new GestionCorreo("mailhost.terra.es","pop3.terra.es","chiclemb@terra.es","chiclemb.terra.es","292829");
        GestorCorreo gc = new GestorCorreo("127.0.0.1", "127.0.0.1", "juanpe1..localhost", "juanpe1..localhost", "111");

        gc.initStore();
        gc.initFolder();
        gc.mandaMensaje(gc.creaMensaje("juanpe1@localhost", "pruebas del GC", "Mensaje desde el gestor de correo"));
        System.out.println("Envio el mensaje");

        Message mensajeTemp = gc.creaMensaje("juanpe2@localhost", "Asunto temporal", "Texto dsadfasdfadf");
        Flags flags = mensajeTemp.getFlags();
        Flags.Flag banderas[] = flags.getSystemFlags();
        System.out.println("Numero de banderas soportadas" + banderas.length);

        MensajeXML mensajeXML = new MensajeXML("juanpe1..localhost", "juanpemb@gmail.com", -1, "Prueba del metodo enviaMensaje(XML)", "trabajo", "texto enviado ", new Date().toString());
        gc.leeCorreos();

        gc.closeFolder();
        gc.closeStore();

    }

}
