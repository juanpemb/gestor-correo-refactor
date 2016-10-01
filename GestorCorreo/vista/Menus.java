/*
 * Menus.java
 *
 * Created on 1 de diciembre de 2005, 6:18
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package vista;

import modelo.Correo;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

import utilidades.MensajeXML;

public class Menus extends JMenuBar implements AnadeCorreo, ActionListener{

	private Correo c;
	private MensajeXML mensaje;
	private	String[] archivoItems=new String[]{"Nuevo", "Abrir mensaje guardado..." , "Adjuntos","Cerrar", "Guardar como", "Recibir menajes nuevos", "Procesar mensajes no enviados", "Imprimir", "Salir"  };
	private	String[] editarItems=new String[]{"Deshacer","Cortar","Copiar", "Pegar", "Borrar Mensaje", "Seleccionar", "Buscar" };
	private	String[] verItems=new String[]{"Barra de Herramientas", "Ordenar por", "Mensajes", "Cabeceras","Cuerpo del mensaje como","Ver adjuntos incorporados","Tamaño del texto", "Codificación de caracteres"};
	private	String[] irItems=new String[]{"Siguiente","Anterior", "Pagina de inicio del correo"};
	private	String[] mensajeItems=new String[]{"Nuevo mensaje","Responder", "Responder a todos", "Reenviar", "Reenviar como", "Editar mensaje como nuevo", "Abrir mensaje", "Mover", "Copiar", "Etiqueta", "Marcar"};             
	private	String[] herramientasItems=new String[]{"Libreta de direcciones", "Filtros mensajes","Configuración de cuentas", "Preferencias" };
	private	String[] ayudaItems=new String[]{"Acerca de GCorreo..."};
	
	char[] archivoShortcuts={'N','A','j','C','m','b','v', 'I','S'  };
	char[] editarShortcuts={'h','t', 'C', 'P', 'r', 'l', 'u' };
	char[] verShortcuts={'h' , 'O', 'M', 'C','u','a','x', 'C'};
	char[] irShortcuts={'S','A', 'i'};
	char[] mensajeShortcuts={'N','R', 'p', 'v', 'o', 'E', 'A', 'M','C', 'q', 'c'};
	char[] herramientasShortcuts={'t', 'F','C', 'P'};
	char[] ayudaShortcuts={'A'};
	
	
	public Menus(Correo correo){
		
		this.c=correo;
		
		JMenu archivoMenu=new JMenu("Archivo");
		JMenu editarMenu=new JMenu("Editar");
		JMenu verMenu=new JMenu("Ver");
		JMenu irMenu=new JMenu("Ir");
		JMenu mensajeMenu=new JMenu("Mensaje");
		JMenu herramientasMenu=new JMenu("Herramientas");
		JMenu ayudaMenu=new JMenu("Ayuda");
	
	
	
	
	//aqui creare los controladores
	
	//
	
	//Creo los menuitem "Principales"
	//arcvivo
		for(int i=0;i<archivoItems.length; i++){
			
			JMenuItem item=new JMenuItem(archivoItems[i], archivoShortcuts[i]);
			//le asigno el observador
			item.addActionListener(this);
			archivoMenu.add(item);
			
		
		}
	//editar
		for(int i=0;i<editarItems.length; i++){
			
			JMenuItem item=new JMenuItem(editarItems[i], editarShortcuts[i]);
			//le asigno el observador
			item.addActionListener(this);
			editarMenu.add(item);
			
			
		
		}
	//ver
		for(int i=0;i<verItems.length; i++){
			
			JMenuItem item=new JMenuItem(verItems[i], verShortcuts[i]);
			//le asigno el observador
			item.addActionListener(this);
			verMenu.add(item);
			
		
		}
	//ir
		for(int i=0;i<irItems.length; i++){
			
			JMenuItem item=new JMenuItem(irItems[i], irShortcuts[i]);
			//le asigno el observador
			item.addActionListener(this);
			irMenu.add(item);
			
		
		}
	//mensaje
		for(int i=0;i<mensajeItems.length; i++){
			
			JMenuItem item=new JMenuItem(mensajeItems[i], mensajeShortcuts[i]);
			//le asigno el observador
			item.addActionListener(this);
			mensajeMenu.add(item);
			
		
		}
	
	//herramientas
		for(int i=0;i<herramientasItems.length; i++){
			
			JMenuItem item=new JMenuItem(herramientasItems[i], herramientasShortcuts[i]);
			//le asigno el observador
			item.addActionListener(this);
			herramientasMenu.add(item);
			
		
		}
	//ayuda
		for(int i=0;i<ayudaItems.length; i++){
			
			JMenuItem item=new JMenuItem(ayudaItems[i], ayudaShortcuts[i]);
			//le asigno el observador
			item.addActionListener(this);
			ayudaMenu.add(item);
			
					
		}
	
	//******************************************************************************
	//faltan los submenus
	//******************************************************************************
	
	//añado las lineas de separacion
		
		archivoMenu.insertSeparator(5);
		editarMenu.insertSeparator(1);
		editarMenu.insertSeparator(4);
		verMenu.insertSeparator(1);
		verMenu.insertSeparator(4);
		irMenu.insertSeparator(2);
		mensajeMenu.insertSeparator(7);
		herramientasMenu.insertSeparator(1);
		herramientasMenu.insertSeparator(3);
		
		
		
		
		add(archivoMenu);
		add(editarMenu);
		add(verMenu);
		add(irMenu);
		add(mensajeMenu);
		add(herramientasMenu);
		add(ayudaMenu);
		setBorderPainted(true);
		setSize(new Dimension(20,800));
	}
	public void addCorreo(Correo correo)
	{
		c=correo;
	}
	public void deleteCorreo()
	{
		c=null;
	}
	
	public static void main(String args[]){
		JFrame jf=new JFrame("prueba de menus");
		Menus m=new Menus(null);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.add(m);
		jf.pack();
		jf.setVisible(true);
	
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		//pedazo de metodo para implementar
		//System.out.println(e.getActionCommand());
		if(e.getActionCommand().equals("Nuevo")){
			//Nuevo Mensaje
			System.out.println("Editor de Mensajes");
			NuevoMensaje nm=new NuevoMensaje("Nuevo Mensaje");
		}else if(e.getActionCommand().equals("Abrir mensaje guardado...")){
			System.out.println("Abrir mensaje");
		}else if(e.getActionCommand().equals("Adjuntos")){
			System.out.println("Adjuntos");
		}else if(e.getActionCommand().equals("Cerrar")){
			
		}else if(e.getActionCommand().equals("Guardar como")){
			
		}else if(e.getActionCommand().equals("Recibir mensajes nuevos")){
			System.out.println("Recibir Mensajes");
		}else if(e.getActionCommand().equals("Procesar mensajes no enviados")){
			System.out.println("Enviar Mensajes");
		}else if(e.getActionCommand().equals("Imprimir")){
			System.out.println("Imprimir");
		}else if(e.getActionCommand().equals("Deshacer")){
			System.out.println("Deshacer");
		}else if(e.getActionCommand().equals("Cortar")){
			
		}else if(e.getActionCommand().equals("Copiar")){
			
		}else if(e.getActionCommand().equals("Pegar")){
			
		}else if(e.getActionCommand().equals("Borrar mensaje")){
			
		}else if(e.getActionCommand().equals("Seleccionar")){
			
		}else if(e.getActionCommand().equals("Buscar")){
			System.out.println("Buscar mensaje");
		}else if(e.getActionCommand().equals("Barra de herramientas")){
			
		}else if(e.getActionCommand().equals("Ordenar por")){
			
		}else if(e.getActionCommand().equals("Mensajes")){
			
		}else if(e.getActionCommand().equals("Cabeceras")){
			
		}else if(e.getActionCommand().equals("Ver adjuntos incorporados")){
			
		}else if(e.getActionCommand().equals("Tamaño del texto")){
			
		}else if(e.getActionCommand().equals("Codificacion de caracteres")){
			
		}else if(e.getActionCommand().equals("Siguiente")){
			
		}else if(e.getActionCommand().equals("Anterior")){
			
		}else if(e.getActionCommand().equals("Pagina de inicio del correo")){
			
		}else if(e.getActionCommand().equals("Nuevo mensaje")){
			NuevoMensaje nm=new NuevoMensaje("Nuevo Mensaje");
		}else if(e.getActionCommand().equals("Responder")){
			NuevoMensaje nm=new NuevoMensaje("Responder");
		}else if(e.getActionCommand().equals("Responder a todos")){
			NuevoMensaje nm=new NuevoMensaje("Responder a Todos");
		}else if(e.getActionCommand().equals("Reenviar")){
			NuevoMensaje nm=new NuevoMensaje("Reenviar");
		}else if(e.getActionCommand().equals("Reenviar como")){
			
		}else if(e.getActionCommand().equals("Editar mensaje como nuevo")){
			NuevoMensaje nm=new NuevoMensaje("Editar mensaje como nuevo");
		}else if(e.getActionCommand().equals("Abrir mensaje")){
			
		}else if(e.getActionCommand().equals("Mover")){
			
		}else if(e.getActionCommand().equals("Copiar")){
			
		}else if(e.getActionCommand().equals("Etiqueta")){
			
		}else if(e.getActionCommand().equals("Marcar")){
			
		}else if(e.getActionCommand().equals("Libreta de direcciones")){
			
		}else if(e.getActionCommand().equals("Filtros mensajes")){
			
		}else if(e.getActionCommand().equals("Configuración de cuentas")){
			new GestorCuentas("Cuentas");
		}else if(e.getActionCommand().equals("Preferencias")){
			new GestorCuentas("Cuentas");
		}else if(e.getActionCommand().equals("Acerca de GCorreo...")){
			
		}else{
			System.out.println("Item no chequeado");	
		}
	}

}