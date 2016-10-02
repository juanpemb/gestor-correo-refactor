/*
 * GestorDirecciones.java
 *
 * Created on 29 de diciembre de 2005, 22:00
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package vista;
import javax.swing.*;

import org.jdom.Element;

import utilidades.CuentaXML;
import utilidades.DireccionXML;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 *
 * @author juanpedro
 */
public class GestorDirecciones extends JFrame implements ActionListener{
    /*una direccion contiene los siguinetes 
     * posicion, nombre, apellidos, email, telefono, direccion 
     */
    private JTabbedPane jtp;
    
    private JPanel verDir, guardarDir;
    private JPanel pvd1,pvd2, pvd3,pvd4,pvd5,pvd6,pvd7;
    private JLabel lvdnombre,lvdapellidos,lvdemail,lvdtelefono,lvddireccion;
    private JTextField tvdnombre,tvdapellidos,tvdemail,tvdtelefono,tvddireccion;
    private JPanel pgd1,pgd2, pgd3,pgd4,pgd5,pgd6,pgd7;
    private JButton anterior, siguiente;
    private JLabel lgdnombre,lgdapellidos,lgdemail,lgdtelefono,lgddireccion;
    private JTextField tgdnombre,tgdapellidos,tgdemail,tgdtelefono,tgddireccion;
    private JButton cancelar, guardar;
    /** Creates a new instance of GestorDirecciones */
    //variables de mensajes
    private CuentaXML cuentaActual;
    private List cuentas;
    //indice de mensaje actual
    private int id=0;
    public GestorDirecciones(String title) {
        super(title);
        /*Instancio ls variables principales*/
        this.jtp=new JTabbedPane();
        this.verDir=new JPanel();
        this.verDir.setLayout(new GridLayout(7,1));
        this.guardarDir=new JPanel();
        this.guardarDir.setLayout(new GridLayout(7,1));
        /*panel ver direcciones*/
        pvd1=new JPanel();
        pvd1.setLayout(new FlowLayout(FlowLayout.LEFT,1,2));
        this.lvdnombre=new JLabel("<html>&#160;&#160;Nombre&#160;&#160;&#160;&#160;&#160;</html> ");
        this.tvdnombre=new JTextField("",10);
        this.tvdnombre.setEditable(false);
        pvd1.add(this.lvdnombre);pvd1.add(this.tvdnombre);
        this.verDir.add(pvd1);

        pvd2=new JPanel();
        pvd2.setLayout(new FlowLayout(FlowLayout.LEFT,1,2));
        this.lvdapellidos=new JLabel("<html>&#160;&#160;Apellidos&#160;&#160;&#160;</html>");
        this.tvdapellidos=new JTextField("",10);
        this.tvdapellidos.setEditable(false);
        pvd2.add(this.lvdapellidos);pvd2.add(tvdapellidos);
        this.verDir.add(pvd2);
        
        pvd3=new JPanel();
        pvd3.setLayout(new FlowLayout(FlowLayout.LEFT,1,2));
        this.lvddireccion=new JLabel("<html>&#160;&#160;Direccion&#160;&#160;</html>");
        this.tvddireccion=new JTextField("", 15);
        this.tvddireccion.setEditable(false);
        pvd3.add(this.lvddireccion);pvd3.add(this.tvddireccion);
        this.verDir.add(pvd3);
        
        pvd4=new JPanel();
        pvd4.setLayout(new FlowLayout(FlowLayout.LEFT,1,2));
        this.lvdemail=new JLabel("<html>&#160;&#160;e-mail&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;</html>");
        this.tvdemail=new JTextField("", 10);
        this.tvdemail.setEditable(false);
        this.pvd4.add(this.lvdemail);this.pvd4.add(this.tvdemail);
        this.verDir.add(pvd4);
        
        pvd5=new JPanel();
        pvd5.setLayout(new FlowLayout(FlowLayout.LEFT,1,2));
        this.lvdtelefono=new JLabel("<html>&#160;&#160;Telefono&#160;&#160;&#160;&#160;</html>");
        this.tvdtelefono=new JTextField("", 10);
        this.tvdtelefono.setEditable(false);
        pvd5.add(this.lvdtelefono);pvd5.add(this.tvdtelefono);
        this.verDir.add(pvd5);
        
        pvd6=new JPanel();
        pvd6.setLayout(new FlowLayout(FlowLayout.CENTER,1,2));
        this.siguiente=new JButton("Siguiente");
        this.siguiente.addActionListener(this);
        this.anterior=new JButton("Anterior");
        this.anterior.addActionListener(this);
        pvd6.add(anterior);pvd6.add(siguiente);
        this.verDir.add(pvd6);
        
        /*pvd7=new JPanel();
        pvd7.setLayout(new FlowLayout(FlowLayout.LEFT,1,2));
        */
        
        /*panel guardar direcciones*/
        pgd1=new JPanel();
        pgd1.setLayout(new FlowLayout(FlowLayout.LEFT,1,2));
        this.lgdnombre=new JLabel("<html>&#160;&#160;Nombre&#160;&#160;&#160;&#160;&#160;</html> ");
        this.tgdnombre=new JTextField("",10);
        pgd1.add(this.lgdnombre);pgd1.add(this.tgdnombre);
        this.guardarDir.add(pgd1);

        pgd2=new JPanel();
        pgd2.setLayout(new FlowLayout(FlowLayout.LEFT,1,2));
        this.lgdapellidos=new JLabel("<html>&#160;&#160;Apellidos&#160;&#160;&#160;</html>");
        this.tgdapellidos=new JTextField("",10);
        pgd2.add(this.lgdapellidos);pgd2.add(tgdapellidos);
        this.guardarDir.add(pgd2);
        
        pgd3=new JPanel();
        pgd3.setLayout(new FlowLayout(FlowLayout.LEFT,1,2));
        this.lgddireccion=new JLabel("<html>&#160;&#160;Direccion&#160;&#160;</html>");
        this.tgddireccion=new JTextField("", 15);
        pgd3.add(this.lgddireccion);pgd3.add(this.tgddireccion);
        this.guardarDir.add(pgd3);
        
        pgd4=new JPanel();
        pgd4.setLayout(new FlowLayout(FlowLayout.LEFT,1,2));
        this.lgdemail=new JLabel("<html>&#160;&#160;e-mail&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;</html>");
        this.tgdemail=new JTextField("", 10);
        //this.tgdemail.setEditable(false);
        this.pgd4.add(this.lgdemail);this.pgd4.add(this.tgdemail);
        this.guardarDir.add(pgd4);
        
        pgd5=new JPanel();
        pgd5.setLayout(new FlowLayout(FlowLayout.LEFT,1,2));
        this.lgdtelefono=new JLabel("<html>&#160;&#160;Telefono&#160;&#160;&#160;&#160;</html>");
        this.tgdtelefono=new JTextField("", 10);
        pgd5.add(this.lgdtelefono);pgd5.add(this.tgdtelefono);
        this.guardarDir.add(pgd5);
        
        pgd6=new JPanel();
        pgd6.setLayout(new FlowLayout(FlowLayout.CENTER,1,2));
        this.cancelar=new JButton("Cancelar");
        this.cancelar.addActionListener(this);
        this.guardar=new JButton("Guardar");
        this.guardar.addActionListener(this);
        pgd6.add(cancelar);pgd6.add(guardar);
        this.guardarDir.add(pgd6);
        
        
        /*añado paneles al jtabbedpane*/
        jtp.add("Ver", this.verDir);
        jtp.add("Guardar", this.guardarDir);
        getContentPane().add(jtp);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        pack();
        setLocation(300, 150);
        setVisible(true);
        
        //Funcionalidad
        //cuentaActual=new CuentasXML();
        id=1;
        this.mostrar(id);
        
    }
    
    public void actionPerformed(ActionEvent a){
    	if(a.getSource()==this.guardar){
    		this.guardar();
    		this.borrarTextField();
    	}else if(a.getSource()==this.cancelar){
    		System.exit(0);
    	}else if(a.getSource()==this.anterior){
    		System.out.println("anterior");
    		
    		if(id>1){
    			id=id-1;
    			mostrar(id);
    		}
    	}else if(a.getSource()==this.siguiente){
    		System.out.println("siguiente");
    		
    		if(id<new DireccionXML().getLastId()){
    			id=id+1;
    			this.mostrar(id);
    		}
    	}
    	
    }
    public void guardar(){
    	DireccionXML direccion=new DireccionXML(this.tgdnombre.getText(),this.tgdapellidos.getText(),this.tgddireccion.getText(),this.tgdemail.getText(),this.tgdtelefono.getText());
    	direccion.guarda();
    	System.out.println("¿¿Guarda??");
    }
    
    public void mostrar(int id){
    	
    	DireccionXML direccion=new DireccionXML().getDireccionId(Integer.toString(id));
		
    	this.tvdnombre.setText(direccion.getNombre());
    	this.tvdapellidos.setText(direccion.getApellidos());
    	this.tvddireccion.setText(direccion.getDireccion());
    	this.tvdemail.setText(direccion.getEmail());
    	this.tvdtelefono.setText(direccion.getTelefono());
    }
    public void borrarTextField(){
    	
    	this.tvdnombre.setText("");
    	this.tvdapellidos.setText("");
    	this.tvddireccion.setText("");
    	this.tvdemail.setText("");
    	this.tvdtelefono.setText("");
    	
    }
    public static void main(String args[]){
        new GestorDirecciones("Gestor de direcciones");
    }
}
