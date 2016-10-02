/*
 * Correo.java
 *
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package modelo;

import utilidades.MensajeXML;

import javax.mail.Message;
import javax.mail.MessagingException;
import java.util.Vector;
public interface Correo
{
	
       
    public Vector  abreMensajes(String s);
	public void borrarMensaje(int i);
	public void closeFolder();
	public void closeStore();
	public Message creaMensaje(String to,String asunto,String texto);
	public Message creaMensajeHTML(String to,String asunto,String texto);
	public void guardaMensaje(Message m,String s);
	public Message incluirArchivo(Message m, String g);
	public Message incluirHTML(Message m, String s);
	public Message incluirImagen(Message m, String s);
	public void initFolder();
	public void initStore();
	public String leeCorreo(Message m);
	public Message[] leeCorreos();
	public void mandaMensaje(Message m);	
	public void mandaMensajeSSL(Message m);
	public void recogeArchivo(Message m);
	public void reenviarMensaje(Message m, String s);
	public void responderMensaje(Message m, String s, String st);
	
	public void mandaMensaje(MensajeXML m);
	public MensajeXML toMensajeXML(Message m) throws MessagingException;

}
