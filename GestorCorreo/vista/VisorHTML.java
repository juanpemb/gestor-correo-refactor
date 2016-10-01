/*
 * VisorHTML.java
 *
 * Created on 1 de enero de 2004, 1:00
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package vista;
import modelo.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;
import javax.swing.event.*;

import utilidades.AnadeMensaje;
import utilidades.MensajeXML;

import java.io.*;
import java.util.Date;

public class VisorHTML extends JEditorPane implements AnadeCorreo, AnadeMensaje, MouseListener  {

	private String archivo;
	private Correo c;
	private MensajeXML mensaje;
	
	public VisorHTML(String a)throws IOException{
		
		super(a);
		this.addMouseListener(this);
		setEditable(false);
		setSize(new Dimension(300,800));
    		addHyperlinkListener(new HyperlinkListener(){
			public void hyperlinkUpdate(HyperlinkEvent ev ){
				try{
					if(ev.getEventType()==HyperlinkEvent.EventType.ACTIVATED){setPage(ev.getURL());}
				}catch(IOException ioe){ioe.printStackTrace();System.out.println(1);}
			}
		
		});
		
	}
	
	
	public VisorHTML(String s, boolean b)
	{
		super();
		this.setEditable(false);
		if (b)setContentType("text/html");
		{
			setContentType("text/html");
		}		
		setText(s);
		this.addMouseListener(this);
	}

	public void addCorreo(Correo correo)
	{
		c=correo;
	}
	public void deleteCorreo()
	{
		c=null;
	}
	

	/* (non-Javadoc)
	 * @see utilidades.AnadeMensaje#setMensaje(utilidades.MensajeXML)
	 */
	public void setMensaje(MensajeXML msj) {
		// TODO Auto-generated method stub
		mensaje=msj;
	}
	
	/* (non-Javadoc)
	 * @see utilidades.AnadeMensaje#getMensaje()
	 */
	public MensajeXML getMensaje() {
		// TODO Auto-generated method stub
		return mensaje;
	}

	//este metodo muestra el mensajeXML
	public void pintar(){
		this.setText(mensaje.toStringHTML());
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		pintar();
	}


	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	
	public static void main(String s[])throws Exception{
		
		JFrame jf=new JFrame();
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		VisorHTML visor=new VisorHTML("Texto por encima del html.<html><h1>texto html</h1><html>",true);
		jf.add(visor);
        //        jf.add(new VisorHTML("http://localhost/catalog/"));
		MensajeXML ms=new MensajeXML("JuanPe1@correo.es","juanpe2@correo.es",1,"pruebo los archivos XML","text/plain" ,"Este es el mensaje que sera guardado e el archivo mensajes.xml",new Date().toString());
        visor.setMensaje(ms);        
		jf.pack();
		jf.setVisible(true);
	
	}


}