package modelo.impl;

import utilidades.MensajeXML;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.LinkedList;

import static modelo.impl.GestorCorreo.FWD;

public class MessageHandler {
    private static final String TEXT_HTML = "text/html";
    private static final String TEXT_PLAIN = "text/plain";

    private final Session session;

    public MessageHandler(){
        this.session=Session.getDefaultInstance(System.getProperties());
    }
    public MessageHandler(Session session){
        this.session = session;
    }

    public Message creaMensaje(String to, String asunto, String texto) {
        MimeMessage m = new MimeMessage(session);

        try {
            m.setFrom();
            m.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            m.setSubject(asunto);
            MimeMultipart mmp = new MimeMultipart();
            BodyPart bp = new MimeBodyPart();
            bp.setContent(texto, TEXT_PLAIN);
            mmp.addBodyPart(bp);
            m.setContent(mmp);
            Flags fs = m.getFlags();
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
            p.setContent(texto, TEXT_HTML);

            mp.addBodyPart(p);

            Message m = new MimeMessage(session);
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

    public Message creaMensajeHTML(String to,
                                   String from,
                                   String texto,
                                   String asunto) {

        MimeMessage m = new MimeMessage(session);
        try {
            m.setFrom(new InternetAddress(from));
            m.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            m.setSubject(asunto);
            BodyPart bp = new MimeBodyPart();
            bp.setContent(texto, TEXT_HTML);
            MimeMultipart mmp = new MimeMultipart();
            mmp.addBodyPart(bp);

            m.setContent(mmp);
            return m;

        } catch (Exception e) {
            throw new RuntimeException("Error al crear el mensaje: " + e.getMessage(),e);
        }

    }

    public Message incluirImagen(Message msj, String imagen) {
        try {
            Message mensaje = new MimeMessage(session);

            mensaje.setSubject(msj.getSubject());
            mensaje.setFrom((msj.getFrom()[0]));
            mensaje.addRecipients(Message.RecipientType.TO,
                    msj.getRecipients(Message.RecipientType.TO)
            );

            BodyPart bp = new MimeBodyPart();

            String htmlText = "<h1>imagen</h1>" + "<img src=cid:\"" + imagen + "\">";    //la imagen es incluida c0mo hipertexto
            bp.setContent(htmlText, TEXT_HTML);

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
            throw new RuntimeException("error al incluir imagen "+e.getMessage(),e);
        }
    }

    public Message incluirArchivo(Message m, String filename) {
        try {
            Message resultado = new MimeMessage(session);
            resultado.setSubject(FWD + " " + m.getSubject());
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
                    if (p.isMimeType(TEXT_PLAIN)) {
                        resultado = resultado + (String) p.getContent();
                    } else if (p.isMimeType(TEXT_HTML)) {
                        resultado = resultado + p.getContent();
                    }
                } else if (isAttach(disposition)) {
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

    private boolean isAttach(String disposition) {
        return (disposition != null) && (disposition.equals(Part.ATTACHMENT)) || (disposition.equals(Part.INLINE));
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

}
