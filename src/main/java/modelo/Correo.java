/*
 * Correo.java
 *
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package modelo;

import javax.mail.Message;
import javax.mail.Session;
import java.util.Vector;
public interface Correo
{
    public Vector  abreMensajes(String s);
	public void borrarMensaje(int i);
	public Message creaMensaje(String to,String asunto,String texto);
	public void guardaMensaje(Message m,String s);
	public Message[] leeCorreos();
	public void mandaMensaje(Message m);	
	public void mandaMensajeSSL(Message m);


	public void setSesion(Session session);
	public void setConnector(Connector connector);
}
