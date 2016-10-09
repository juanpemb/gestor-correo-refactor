package modelo.impl;

import com.sun.mail.smtp.SMTPSSLTransport;
import modelo.Connector;
import modelo.Correo;
import utilidades.MensajeXML;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.*;
import java.util.Date;
import java.util.LinkedList;
import java.util.Vector;

public class GestorCorreo implements Correo {

    private static final String TEXT_HTML = "text/html";
    private static final String TEXT_PLAIN = "text/plain";
    public static final String MAIL_SMTP_HOST = "mail.smtp.host";
    public static final String FWD = "Fwd:";

    private Connector connector;
    private MailServerArgs mailServerArgs;
    private MessageHandler messageHandler;

    public GestorCorreo(MailServerArgs mailServerArgs) {

        this.mailServerArgs = mailServerArgs;
    }


    public void setConnector(Connector connector) {

        this.connector = connector;
        this.messageHandler = new MessageHandler(connector.getSession());
    }


    public void mandaMensaje(Message m) {
        if (m == null)
            throw new RuntimeException("Mensaje no valido" + m.toString());

        try {
            Transport transport = connector.getTransport("smtp");
            transport.connect(
                    mailServerArgs.getHost(),
                    Integer.parseInt(mailServerArgs.getPuertoSMTP()),
                    mailServerArgs.getUsuario(),
                    mailServerArgs.getPwd());
            transport.sendMessage(m, m.getAllRecipients());
            transport.close();

        } catch (javax.mail.MessagingException e) {
            throw new RuntimeException("Error en el envio del mensaje " + m.toString(), e);
        }
    }

    public void mandaMensajeSSL(Message m) {
        try {
            SMTPSSLTransport transport = new SMTPSSLTransport(
                    connector.getSession(),
                    new URLName(mailServerArgs.getHost()));

            transport.connect(mailServerArgs.getHost(),
                    mailServerArgs.getUsuario(),
                    mailServerArgs.getPwd());

            transport.sendMessage(m, m.getAllRecipients());
            transport.close();

        } catch (MessagingException e) {
            System.err.println("Error en mandaMensajeSSL");
            e.printStackTrace();
        }
    }

    public Message[] leeCorreos() {
        try {
            Message[] messages = connector.getFolder().getMessages();
            for (Message message: messages) {
                message.setFlag(Flags.Flag.DELETED, true);
            }
            return messages;

        } catch (javax.mail.MessagingException e) {
            throw new RuntimeException("error reading messages");
        }

    }

    @Override
    public void setSesion(Session session) {
        this.connector.setSession(session);
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

    public void responderMensaje(Message m, String para, String texto) {
        try {
            MimeMessage respuesta = (MimeMessage) m.reply(true);
            InternetAddress[] a = new InternetAddress[1];
            a[0] = new InternetAddress(para);
            respuesta.setReplyTo(a);
            respuesta.setText(texto);
            Transport t = connector.getSession().getTransport("smtp");
            t.connect(
                    mailServerArgs.getHost(),
                    mailServerArgs.getUsuario(),
                    mailServerArgs.getPwd()
            );//conecta con el servidor smtp
            t.sendMessage(m, m.getAllRecipients());
            t.close();
        } catch (MessagingException e) {
            System.out.println("Error en responderMensajes\n");
            e.printStackTrace();
        }

    }

    public void reenviarMensaje(Message m, String para) {
        try {
            MimeMessage enviar = new MimeMessage(connector.getSession());

            enviar.setSubject(FWD + " " + m.getSubject());
            enviar.setFrom(new InternetAddress(mailServerArgs.getDe()));
            enviar.addRecipient(Message.RecipientType.TO, new InternetAddress(para));

            BodyPart mbp = new MimeBodyPart();
            mbp.setText("incluyo mi mensaje\n");

            Multipart mp = new MimeMultipart();

            mp.addBodyPart(mbp);

            mbp = new MimeBodyPart();
            mbp.setDataHandler(m.getDataHandler());
            mp.addBodyPart(mbp);


            enviar.setContent(mp);

            Transport t = connector.getTransport("smtp");
            t.connect(mailServerArgs.getHost(),
                    mailServerArgs.getUsuario(),
                    mailServerArgs.getPwd()
            );//conecta con el servidor smtp
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
            Message m = connector.getFolder().getMessage(i);
            m.setFlag(Flags.Flag.DELETED, true);
        } catch (javax.mail.MessagingException e) {
            System.out.println("Error en borrarMensaje");
            e.printStackTrace();
        }

    }

    public Message creaMensaje(String to, String asunto, String texto) {
        MimeMessage m = new MimeMessage(connector.getSession());

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


    public static void main(String args[]) throws Exception {
        System.out.println("Prueba de gestion de correo\n");
        MailServerArgs session = new MailServerArgs("127.0.0.1", null, "127.0.0.1", null, "juanpe1..localhost", "juanpe1..localhost", "111");
        GestorCorreo gc = new GestorCorreo(session);
        MailServerConnector conector = new MailServerConnector();
        conector.initStore(session.getHost(), session.getHe(), session.getUsuario(), session.getPwd());
        conector.initFolder();

        gc.mandaMensaje(gc.creaMensaje("juanpe1@localhost", "pruebas del GC", "Mensaje desde el gestor de correo"));
        System.out.println("Envio el mensaje");

        Message mensajeTemp = gc.creaMensaje("juanpe2@localhost", "Asunto temporal", "Texto dsadfasdfadf");
        Flags flags = mensajeTemp.getFlags();
        Flags.Flag banderas[] = flags.getSystemFlags();
        System.out.println("Numero de banderas soportadas" + banderas.length);

        MensajeXML mensajeXML = new MensajeXML("juanpe1..localhost", "juanpemb@gmail.com", -1, "Prueba del metodo enviaMensaje(XML)", "trabajo", "texto enviado ", new Date().toString());
        gc.leeCorreos();
    }

}
