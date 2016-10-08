/*
 * Created on 21-ene-2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package vista;


import org.jdom.Element;
import utilidades.MensajeXML;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DateFormat;
import java.util.*;

/**
 * @author juanpedro
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Combo extends JList implements MouseListener{
	private int mensajeSeleccionado;
	private List mensajes;	//lista de mensajes XML	

	public Combo(Object []o) {
		super(o);
		this.addMouseListener(this);
		// TODO Auto-generated constructor stub
	}
	public int getMensajeSeleccionado(){
		return this.mensajeSeleccionado;
	}
	public static Object[] getObjects(List mensajes){
		Object [] resultado;
		DateFormat df=DateFormat.getDateInstance(DateFormat.MEDIUM,Locale.getDefault());
		LinkedList lista=new LinkedList();
		if(mensajes==null){
			return Collections.EMPTY_LIST.toArray();
		}
		Iterator it=mensajes.iterator();
		while(it.hasNext()){
			MensajeXML msj=MensajeXML.getMensaje((Element)it.next());
			
			//Etiqueta e=new Etiqueta(msj.getOrigen(),msj.getAsunto(),df.format(msj.getFecha()));
				Etiqueta e1;
				String origen=msj.getOrigen(),asunto=msj.getAsunto(),fecha=msj.getFecha();
				if(origen!=null&&asunto!=null&&fecha!=null){
					if(msj.isLeido())System.out.println("Mensaje Leido");
					else System.out.println("Mensaje no leido");
					
					
					e1=new Etiqueta(origen,asunto,fecha);
					lista.add(e1.getEtiqueta());
				}
		}
		do{
			lista.add("");			
		}while(lista.size()<7);
		
		return lista.toArray();
		
	}
	
	
	public JPanel getLista(){
		return null;
	}
	
	
	public static void main(String args[]) throws Exception{
		
		JFrame jf=new JFrame("JList");
		
		jf.getContentPane().add(new Combo(Combo.getObjects(MensajeXML.getList())));
		jf.setVisible(true);
		jf.pack();
		Thread.sleep(5000);
		jf.getContentPane().removeAll();
		String [] hola={"fdsfa","fdsafad"};
		Combo combo=new Combo(hola);
		combo.setVisible(true);
		jf.getContentPane().add(combo);
		System.out.println("Cambio el combo");
		jf.repaint();
	}
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		//System.out.println(this.locationToIndex(e.getPoint()));
		this.mensajeSeleccionado=this.locationToIndex(e.getPoint());
		//System.out.println(this.mensajeSeleccionado);
		//Depuracion 
		
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
	
	
}
