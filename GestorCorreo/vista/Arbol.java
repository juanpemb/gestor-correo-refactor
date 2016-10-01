/*
 * Arbol.java
 *
 * 
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 * Esta clase informa al controlador de la carpet que quermos ver (Entrada, Enviados,...)
 */
package vista;
import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.*;
import modelo.Correo;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;


public class Arbol extends JTree implements AnadeCorreo, MouseListener{
	
	private Correo c;
	private DefaultMutableTreeNode treeNode0; 
	private	DefaultMutableTreeNode treeNode1;
	private	DefaultMutableTreeNode treeNode2;
	private	DefaultMutableTreeNode treeNode3;
	private	DefaultMutableTreeNode treeNode4;
	private	DefaultMutableTreeNode treeNode5;
	private static int ENTRADA=1, SALIDA=2,BORRADOR=3,PLANTILLAS=4,ELIMINADOS=5;
    private int carpeta=ENTRADA;
    private boolean actualizado=false;
        
	
	public Arbol(){
		super();
		treeNode0=new DefaultMutableTreeNode("Carpetas Locales"); 
		treeNode1=new DefaultMutableTreeNode("Bandeja entrada");
		treeNode2=new DefaultMutableTreeNode("Enviados");
		treeNode3=new DefaultMutableTreeNode("Borradores");
		treeNode4=new DefaultMutableTreeNode("Plantillas");
		treeNode5=new DefaultMutableTreeNode("Eliminados");
		
		treeNode0.add(treeNode1);
		treeNode0.add(treeNode2);
		treeNode0.add(treeNode3);
		treeNode0.add(treeNode4);
		treeNode0.add(treeNode5);

		
		
			
		
	}
	
	public DefaultMutableTreeNode getNodo0()
	{
		return treeNode0;
	}
	
	public Arbol(Hashtable h){
	
		super(h);
	}
	
	/**
	 * @return Returns the actualizado.
	 */
	public boolean isActualizado(){
		return actualizado;
	}
	/**
	 * @param actualizado The actualizado to set.
	 */
	public void setActualizado(boolean actualizado) {
		this.actualizado = actualizado;
	}
	public Arbol(DefaultMutableTreeNode dmtn){
	
		super(dmtn);
		treeNode0=dmtn;
		this.addMouseListener(this);
	
	
	}
	
	public JScrollPane getArbolScrollPane(){
		
		JScrollPane jsp=new JScrollPane(this);
		jsp.createVerticalScrollBar();
		
		return jsp;
	
	}


	/*este metodo deveulve el valor de los nodos que seran usados
	para la implementacion de mi presentacion
	*/
	public int getCarpeta(){
            return carpeta;
        }
	public void addCorreo(Correo correo)
	{
		c=correo;
	}
	public void deleteCorreo()
	{
		c=null;
	}
	
	
	
	
	public static DefaultMutableTreeNode getDefaultNodos(){
		DefaultMutableTreeNode treeNode0=new DefaultMutableTreeNode("Carpetas Locales"); 
		DefaultMutableTreeNode treeNode1=new DefaultMutableTreeNode("Bandeja entrada");
		DefaultMutableTreeNode treeNode2=new DefaultMutableTreeNode("Enviados");
		DefaultMutableTreeNode treeNode3=new DefaultMutableTreeNode("Borradores");
		DefaultMutableTreeNode treeNode4=new DefaultMutableTreeNode("Plantillas");
		DefaultMutableTreeNode treeNode5=new DefaultMutableTreeNode("Eliminados");
		treeNode0.add(treeNode1);
		treeNode0.add(treeNode2);
		treeNode0.add(treeNode3);
		treeNode0.add(treeNode4);
		treeNode0.add(treeNode5);
		
		
		
		return treeNode0;
		
	
	}

	public void addNodoBandejaEntrada(String s)
	{	
		DefaultMutableTreeNode treeNode=(DefaultMutableTreeNode)treeNode0.getChildAt(0);
		treeNode.add(new DefaultMutableTreeNode(s));
	}
	public void addNodoEnviados(String s)
	{	DefaultMutableTreeNode treeNode=(DefaultMutableTreeNode)treeNode0.getChildAt(1);
		treeNode.add(new DefaultMutableTreeNode(s));
	}
	public void addNodoBorradores(String s)
	{
		DefaultMutableTreeNode treeNode=(DefaultMutableTreeNode)treeNode0.getChildAt(2);
		treeNode.add(new DefaultMutableTreeNode(s));
	}
	public void addNodoPlantillas(String s)
	{
		DefaultMutableTreeNode treeNode=(DefaultMutableTreeNode)treeNode0.getChildAt(3);
		treeNode.add(new DefaultMutableTreeNode(s));
	}
	public void addNodoEliminados(String s)
	{
		DefaultMutableTreeNode treeNode=(DefaultMutableTreeNode)treeNode0.getChildAt(4);
		treeNode.add(new DefaultMutableTreeNode(s));
	}
	
	public static void main(String args[])throws Exception
	{
	
			
		Arbol a=new Arbol(Arbol.getDefaultNodos());
		JFrame jf=new JFrame("prueba de la clase JTree");
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.add(a.getArbolScrollPane());
		jf.pack();
		jf.setVisible(true);
		/*Thread.sleep(5000);
		a.addNodoEliminados("mensaje eliminado");
		jf.repaint();
		System.out.println("añado un mensaje a la carpeta de eliminados\nEspero otors 5 segundos");
		Thread.sleep(5000);
		a.addNodoBandejaEntrada("Mensaje recibido");
		jf.repaint();
		a.addNodoBandejaEntrada("Mensaje recibido");
		jf.repaint();
		a.addNodoBandejaEntrada("Mensaje recibido");
		jf.repaint();
		a.addNodoBandejaEntrada("Mensaje recibido");
		jf.repaint();
		*/
	
	}

	/* (non-Javadoc)
	 * @see javax.swing.event.TreeSelectionListener#valueChanged(javax.swing.event.TreeSelectionEvent)
	 */
	public void valueChanged(TreeSelectionEvent e) {
		// TODO Auto-generated method stub
		
		
		System.out.println("Nodo seleccionado==>"+carpeta);
		System.out.println("Nodo que provoca el evento"+e.getSource().toString());
		System.out.println("Nodo que es selecionado==>"+this.treeNode0.getLastChild());
		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		int selRow = this.getRowForLocation(e.getX(), e.getY());
		if(selRow==Arbol.ENTRADA){System.out.println("ENTRADA");carpeta=ENTRADA;}
		else if(selRow==Arbol.SALIDA){System.out.println("SALIDA");carpeta=SALIDA;}
		else if(selRow==Arbol.BORRADOR){System.out.println("BORRADOR");carpeta=BORRADOR;}
		else if(selRow==Arbol.PLANTILLAS){System.out.println("PLANTILLAS");carpeta=PLANTILLAS;}
		else if(selRow==Arbol.ELIMINADOS){System.out.println("ELIMINADOS");carpeta=ELIMINADOS;}
				
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