/*
 * JTabla.java
 *
 * Created on 1 de diciembre de 2005, 6:16
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package vista;
import javax.swing.*;

import utilidades.AnadeMensaje;
import utilidades.MensajeXML;

import java.awt.*;
import java.util.Date;

import modelo.Correo;

public class JTabla extends JTable
{
	private Correo c;
	private List mensajes;
	public JTabla()
	{
		super();
		setShowVerticalLines(false);
		setShowHorizontalLines(false);
		this.setEnabled(false);	//esta linea hace que no se pueda editar la tabla
		this.setColumnSelectionAllowed(true);
		
	}
	public JTabla(int filas, int columnas)
	{
		super(filas, columnas);
		setShowVerticalLines(false);
		setShowHorizontalLines(false);
		this.setEnabled(false);	//esta linea hace que no pueda editar la tabla
	}
	public JTabla(Object[][] datos, Object[] nombresCol)
	{
		super(datos, nombresCol);
		setShowVerticalLines(true);
		setShowHorizontalLines(true);
		this.setEnabled(false);	//no se edita en la tabla
		this.setColumnSelectionAllowed(false);
		this.setRowSelectionAllowed(true);
		this.setSelectionMode(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
	}
	
	/*este metodo devuelve la tabla en un panel con la tabla*/
	public JScrollPane getJTabla()
	{
		JPanel panel=new JPanel();
		JTabla aux=this;
		panel.setLayout(new BorderLayout());
		panel.add(aux.getTableHeader(),BorderLayout.PAGE_START);
		panel.add(aux,BorderLayout.CENTER);

		return new JScrollPane(panel);
	
	}
	
	public void addCorreo(Correo correo)
	{
		c=correo;
	}
	public void deleteCorreo()
	{
		c=null;
	}
	public void addMensajes(List mensajes){
		this.mensajes=mensajes;
	}
	public List getMensajes(){
		return this.mensajes;
	}	
	
	public static void main(String args[])
	{
	
	
		Object[][]datos;//={{"1","dato2"},{"4","dato2"},{"dato1","dato2"},{"dato1","dato2"}};
		Object[] nomDatos={"Nombre1", "Nombre2"	};
		datos=new Object[2][2];
		datos[0][0]=new JLabel("Label1");
		datos[0][1]=new JLabel("LAbel2");
		datos[1][0]=new JLabel("Label3");
		datos[1][1]=new JLabel("Label4");
		MensajeXML ms=new MensajeXML("JuanPe1@correo.es","juanpe2@correo.es",1,"pruebo los archivos XML","text/plain" ,"Este es el mensaje que sera guardado e el archivo mensajes.xml",new Date().toString());
		
		//JTabla jt=new JTabla(ms.getCuerpo(1),ms.getCabeceraTabla());
		JTabla jt=new JTabla(datos,nomDatos);
		JScrollPane panel=new JScrollPane(jt);
//		panel.setLayout(new BorderLayout());
//		panel.add(jt.getTableHeader(),BorderLayout.PAGE_START);
//		panel.add(jt,BorderLayout.CENTER);
//		
//		System.out.println(datos[1].length+"\n"+ nomDatos.length);
//		jt.setShowVerticalLines(false);
//		jt.setShowHorizontalLines(false);
//		panel.add(jt);
//		//jt.setEditable(false);
	
		
		JFrame jf=new JFrame("prueba de JTabla");
		
		
		jf.add(panel);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.pack();
		jf.setVisible(true);
		
	
	}
	/* (non-Javadoc)
	 * @see utilidades.AnadeMensaje#setMensaje(utilidades.MensajeXML)
	 */
	public void setMensaje(MensajeXML msj) {
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 * @see utilidades.AnadeMensaje#getMensaje()
	 */
	public MensajeXML getMensaje() {
		// TODO Auto-generated method stub
		return null;
	}
}