/*
 * Botones.java
 *
 * Created on 1 de diciembre de 2005, 5:47
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package vista;
//**********************************************
//Esta clase es un panel con la barra de botones
//**********************************************
import modelo.Correo;
import java.awt.*;
import java.awt.event.*;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.swing.*;
import java.io.*;

import utilidades.AnadeMensaje;
import utilidades.MensajeXML;

public class Botones extends JToolBar implements AnadeCorreo,AnadeMensaje, ActionListener{


	private String [] nombreEtiquetas=new String[]{"Recibir mensajes","Redactar","Libreta de Direcciones", "Responder", "Responder a todos", "Reenviar", "Borrar", "Imprimir"};
	private String [] nombreIconos=new String[]{"RECIBIR.GIF", "ENVIAR.GIF","DIRECCIONES.GIF","RESPONDER.GIF","RESPONDER.GIF","REENVIAR.GIF","BORRAR.GIF","IMPRIMIR.GIF"};
	private JButton[] botones=new JButton[nombreEtiquetas.length]; 
	private Correo c;
        private MensajeXML mensaje;
        private java.util.List mensajesXML;
	
	public Botones(Correo correo,java.util.List mensajesXML){
		setFloatable(true);
		this.mensajesXML=mensajesXML;
		for(int i=0; i<nombreEtiquetas.length; i++){
		//botones[i]=new JButton(nombreEtiquetas[i],new ImageIcon( "imagenes"+File.separator+nombreIconos[i]));
		botones[i]=new JButton(new ImageIcon( "imagenes"+File.separator+nombreIconos[i]));
                botones[i].addActionListener(this);
			add(botones[i]);
			//addSeparator();
                        
		
		}		
		c=correo;
	}
	
	public Botones(String[] nombres, String[] iconos, Correo correo, java.util.List mensajeXML )
	{
		this.mensajesXML=mensajeXML;
		
		if(nombres.length!=iconos.length) System.out.println("array de diferentes tamaños\n");
		else
		{
			for (int i=0;i<nombres.length;i++)
			{
				botones[i]=new JButton(nombres[i],new ImageIcon( "imagenes"+File.separator+iconos[i]));
				add(botones[i]);
			}
		}
		c=correo;
	}
	
	public void addCorreo(Correo correo)
	{
		this.c=correo;
		System.out.println("Botones:AddCorreo...");
		if(c==null)System.out.println("Botones:addCorreo: El mensaje ES nulo");
	}
	public void deleteCorreo()
	{
		c=null;
	}
        public void setMensaje(MensajeXML mensaje){
            this.mensaje=mensaje;
        }
        public MensajeXML getMensaje(){return mensaje; }
        
	
	public void actionPerformed(ActionEvent a) {
            if(a.getSource()==this.botones[0]){
                try {
					//recibir
					leerCorreos();
				} catch (MessagingException e) {
					// TODO Auto-generated catch block
					System.out.println("Error al leer correo desde Botones"+e.getMessage());
					e.printStackTrace();
				}	
            	
            	System.out.println("recibir mensajes");
            }else if(a.getSource()==this.botones[1]){
                //redactar nuevo mensaje
            	NuevoMensaje nuevoMensaje=new NuevoMensaje("Nuevo Mensaje");
            	System.out.println("Botones: ActionPerformed");
            	if(c==null)System.out.println("Botones: ActionPerformed: el gestor Es Nulo");
            	nuevoMensaje.setCorreo(c);
            	
            	
            }else if(a.getSource()==botones[2]){
                //Direcciones
            	new GestorDirecciones("Libreta de direcciones");
            }else if(a.getSource()==botones[3]){
                //responder se le debe pasar un mensaje para poder responderlo
            	NuevoMensaje nuevoMensaje=new NuevoMensaje("Responder Mensaje");
            	nuevoMensaje.addCorreo(c);
            	
            }else if(a.getSource()==botones[4]){
                //omo el de arriba
            	NuevoMensaje nuevoMensaje=new NuevoMensaje("Responder a Todos");
            	nuevoMensaje.addCorreo(c);
            }else if(a.getSource()==botones[5]){
                //reenviar
            	NuevoMensaje nuevoMensaje=new NuevoMensaje("Reenviar Mensaje");
            	nuevoMensaje.addCorreo(c);
            }else if(a.getSource()==botones[6]){
                //borrar
            	System.out.println("Borra Mensaje");
            }else if(a.getSource()==botones[7]){
                //imprimir
            	System.out.println("Imprimir mensaje");
            }
            
        }
		
	/**
	 * @throws MessagingException
	 * 
	 */
	private void leerCorreos() throws MessagingException {
		//leo los mensajes
		c.initStore();
		c.initFolder();
		Message[] m = this.c.leeCorreos();
		for(int i=0; i<m.length-1; i++){
			MensajeXML mensaje=c.toMensajeXML(m[i]);
			mensaje.guardaMensaje();
			System.out.println("Mensaje Guardado en mensajes.xml");
			c.closeFolder();
			c.closeStore();
			
			
		}
		//mensajesXML.add();
		
	}

	public static void main(String args[]){

		JFrame jf=new JFrame("pruebo el Toolbar");
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Botones toolbar=new Botones(null,null);
		jf.add(toolbar, BorderLayout.NORTH);
		jf.pack();
		jf.setVisible(true);
		
	}

}




 

