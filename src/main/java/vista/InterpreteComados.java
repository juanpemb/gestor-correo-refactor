/*
 * Created on Jan 1, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package vista;

import modelo.Correo;
import modelo.impl.GestorCorreo;
import modelo.impl.MailServerArgs;
import modelo.impl.MessageHandler;

import javax.mail.Message;
import javax.mail.MessagingException;
import java.io.DataInputStream;
import java.io.IOException;

public class InterpreteComados {

	private MessageHandler handler = new MessageHandler();
	private Correo correo;
	
	public InterpreteComados(String smtp,String pop, String usuario, String password){
		MailServerArgs mss = new MailServerArgs(smtp,null,pop,null,usuario,usuario,password);
		correo=new GestorCorreo(mss);
	
	}
	

	public String leer() throws MessagingException{
	
		String resultado="";
		String linea="----------------------------------------------";
		Message[] mensajes=correo.leeCorreos();
		for (int i=0; i<mensajes.length;i++){
			resultado=resultado+"\n\n"+linea+"\n"+mensajes[i].getSentDate().toGMTString()+"\n"+handler.leeCorreo(mensajes[i]);
		}

		return resultado;
		
	}
	
	public void enviar(String para,String asunto, String texto ){
		if(correo!=null){						
			correo.mandaMensaje(correo.creaMensaje(para,asunto,texto));
		}
		
	}
	
	public static void main(String[] args) throws IOException, MessagingException {
		String usuario,password,smtp,pop;
		
		DataInputStream dis=new DataInputStream(System.in);
		
		System.out.println("Bienvenido al Interprete de Comandos desarrollado" +
				"para demostrar una de las ventajas de usar el patron MVC\n " +
				"Ahora se va a proceder a la configuraci�n de gestor de correo\n" +
				"Usuario: ");
		 
		usuario=dis.readLine();
		System.out.println("\npassword: ");
		password=dis.readLine();
		System.out.println("Servidor SMTP: ");
		smtp=dis.readLine();
		System.out.println("Servidor POP: ");
		pop=dis.readLine();
		
		System.out.println("Configurando su gestor.............");
		InterpreteComados interprete=new InterpreteComados(smtp,pop,usuario,password);
		while(true){
			System.out.println("�Que desea hacer leer los mensajes[l] o escribir un mensaje[e]?[l][e]");
			String opcion=dis.readLine();
			if(opcion.startsWith("l")){
				//leemos el correo
				String correo=interprete.leer();
				System.out.println(correo);
				
			}else if(opcion.startsWith("e")){
				//escribimos un correo	
				String from,asunto,texto;
				System.out.println("Destinatario: ");
				from=dis.readLine();
				System.out.println("Asunto:");
				asunto=dis.readLine();
				System.out.println("Mensaje:");
				texto=dis.readLine();
				interprete.enviar(from,asunto,texto);
			}
		}
	}
}
