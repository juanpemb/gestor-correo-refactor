/*
 * GectorCuentas.java
 *
 * Created on 1 de enero de 2004, 11:51
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package vista;
import modelo.Correo;
import org.jdom.Element;
import utilidades.CuentaXML;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 *
 * @author juanpedro
 */
public class GestorCuentas extends JFrame implements AnadeCorreo, ActionListener {
    
    private Correo correo;
    private JPanel pVerCuentas, pGuardarCuentas;
    private JPanel pvc1,pvc2,pvc3, pvc4,pvc5, pgc1, pgc2, pgc3, pgc4, pgc5, pgc6, pgc7;
    private JButton anterior, siguiente,guardar, borrar;
    private javax.swing.JTabbedPane jtp;
    /*etiquetas y textfield Guardar Cuentas*/
    private JLabel lgcnombre, lgcservS, lgcservP, lgcusuario, lgcpass1,lgcpass2;
    private JTextField tgcnombre, tgcservS, tgcportS, tgcserP,tgcportP, tgcusuario;
    private JPasswordField tgcpass1,tgcpass2;
    /*etiquetas y textField verCuentas*/
    private JLabel lvcnombre, lvcservS,lvcservP,lvcusuario;
    private JTextField tvcnombre, tvcservS, tvcportS,tvcservP, tvcportP,tvcusuario;
    //indicador de posicion    
    int id;
    
    public GestorCuentas(String title){
        super(title);
        /*creo todos los elementos de orden superior*/
        jtp=new JTabbedPane();
        pVerCuentas=new JPanel();
        pGuardarCuentas=new JPanel();
        
        /*creo primero el panel GuardarCuentas y todos sus elementos*/
            
        pGuardarCuentas.setLayout(new GridLayout(7,1));
        
        lgcnombre=new JLabel("<html>&#160;&#160;&#160;&#160;Nombre Cuenta&#160;&#160;</html>");tgcnombre=new JTextField("", 10);        
        pgc1=new JPanel();
        pgc1.setLayout(new FlowLayout(FlowLayout.LEFT, 1,2));
        pgc1.add(lgcnombre);pgc1.add(tgcnombre);
        pGuardarCuentas.add(pgc1);
        
        this.lgcservS=new JLabel("<html>&#160;&#160;&#160;&#160;Servidor SMTP&#160;&#160;&#160;</html>");this.tgcservS=new JTextField("",10);this.tgcportS=new JTextField("",4);
        pgc2=new JPanel();pgc2.setLayout(new FlowLayout(FlowLayout.LEFT, 1,3));
        pgc2.add(this.lgcservS);pgc2.add(this.tgcservS);pgc2.add(this.tgcportS);
        pGuardarCuentas.add(pgc2);
        
        this.lgcservP=new JLabel("<html>&#160;&#160;&#160;&#160;&#160;&#160;Servidor POP&#160;&#160;&#160;&#160;</html>");this.tgcserP=new JTextField("",10);this.tgcportP=new JTextField("", 4);
        pgc3=new JPanel();pgc3.setLayout(new FlowLayout(FlowLayout.LEFT, 1,3));
        pgc3.add(this.lgcservP);pgc3.add(this.tgcserP);pgc3.add(this.tgcportP);
        pGuardarCuentas.add(pgc3);        
        
        this.lgcusuario=new JLabel("<html>&#160;&#160;&#160;&#160;Usuario&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;</html>");this.tgcusuario=new JTextField("",10);
        pgc4=new JPanel();pgc4.setLayout(new FlowLayout(FlowLayout.LEFT, 1, 2));
        pgc4.add(this.lgcusuario);
        pgc4.add(this.tgcusuario);
        pGuardarCuentas.add(pgc4);
        
        this.lgcpass1=new JLabel("<html>&#160;&#160;&#160;&#160;Password&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;</html>");this.tgcpass1=new JPasswordField("",10);
        this.tgcpass1.setEchoChar('X');
        
        pgc5=new JPanel();
        pgc5.setLayout(new FlowLayout(FlowLayout.LEFT, 1, 2));
        pgc5.add(this.lgcpass1);pgc5.add(this.tgcpass1);
        pGuardarCuentas.add(pgc5);
 
        this.lgcpass2=new JLabel("<html>&#160;&#160;&#160;&#160;Password&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;</html>");this.tgcpass2=new JPasswordField("",10);
        this.tgcpass2.setEchoChar('X');
        pgc6=new JPanel();pgc6.setLayout(new FlowLayout(FlowLayout.LEFT, 1,2));
        pgc6.add(this.lgcpass2);pgc6.add(this.tgcpass2);
        pGuardarCuentas.add(pgc6);
        this.borrar=new JButton("cancelar");this.guardar=new JButton("guardar");
        this.borrar.addActionListener(this);this.guardar.addActionListener(this);
        pgc7=new JPanel();pgc7.setLayout(new FlowLayout(FlowLayout.CENTER, 1, 2));
        pgc7.add(borrar);pgc7.add(guardar);
        pGuardarCuentas.add(pgc7);
        
        /*creo el panel ver cuentas */
        pVerCuentas.setLayout(new GridLayout(6,1));
        this.lvcnombre=new JLabel("<html>&#160;&#160;&#160;&#160;Cuenta&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;</html>");this.tvcnombre=new JTextField("",10);
        this.tvcnombre.setEditable(false);
       
        pvc1=new JPanel();
        pvc1.setLayout(new FlowLayout(FlowLayout.LEFT, 1, 2));
        pvc1.add(lvcnombre);pvc1.add(tvcnombre);
        pVerCuentas.add(pvc1);
        
        this.lvcservS=new JLabel("<html>&#160;&#160;&#160;&#160;Servidor SMTP&#160;&#160;&#160;</html>");this.tvcservS=new JTextField("", 10);this.tvcportS=new JTextField("", 2);
        this.tvcportS.setEditable(false);
        this.tvcservS.setEditable(false);
        pvc2=new JPanel();
        pvc2.setLayout(new FlowLayout(FlowLayout.LEFT, 1, 3));
        pvc2.add(lvcservS);pvc2.add(tvcservS);pvc2.add(tvcportS);        
        pVerCuentas.add(pvc2);
        
        this.lvcservP=new JLabel("<html>&#160;&#160;&#160;&#160;Servidor POP&#160;&#160;&#160;&#160;&#160;&#160;</html>");this.tvcservP=new JTextField("",10);this.tvcportP=new JTextField("",2);
        this.tvcservP.setEditable(false);this.tvcportP.setEditable(false);
        pvc3=new JPanel();
        pvc3.setLayout(new FlowLayout(FlowLayout.LEFT, 1, 3));
        pvc3.add(this.lvcservP);pvc3.add(this.tvcservP);pvc3.add(this.tvcportP);
        pVerCuentas.add(pvc3);
        
        this.lvcusuario=new JLabel("<html>&#160;&#160;&#160;&#160;Usuario&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;</html>");this.tvcusuario=new JTextField("",10);
        this.tvcusuario.setEditable(false);
        pvc4=new JPanel();
        pvc4.setLayout(new FlowLayout(FlowLayout.LEFT,1,2));        
        pvc4.add(this.lvcusuario);pvc4.add(this.tvcusuario);
        pVerCuentas.add(pvc4);
        
        pvc5=new JPanel();
        pvc5.setLayout(new FlowLayout(FlowLayout.CENTER,1,2));
        this.anterior=new JButton("anterior");
        this.anterior.addActionListener(this);
        this.siguiente=new JButton("siguiente");
        this.siguiente.addActionListener(this);
        pvc5.add(anterior);pvc5.add(siguiente);
        
        
        pVerCuentas.add(pvc5);
        
         pVerCuentas.add(new JLabel("<html></html>"));
        jtp.add("Ver", pVerCuentas);
        jtp.add("Guardar", pGuardarCuentas);
        getContentPane().add(jtp);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setLocation(300, 150);
        pack();
        setVisible(true);
        id=1;
        mostrar(id);
    }
    
    public void addCorreo(Correo correo){
        this.correo=correo;
    }
    public void deleteCorreo(){
        this.correo=null;
    }
    public static void main(String args[]){
        new GestorCuentas("--Gestor Cuentas: pruebo el sistema--");
    
    }
    
    
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==this.guardar){
			this.guardar();
		}else if(e.getSource()==this.borrar){
			//borra una cuenta de correo electronico
			System.out.println("Borrar la cuenta");
			
			
		}else if(e.getSource()==this.anterior){
			System.out.println("Anterior");
			if(id>=1){
				id--;
				mostrar(id);
			}
		}else if(e.getSource()==this.siguiente){
			
			System.out.println("Siguiente");
			if(id<new CuentaXML().getLastId()){
				id++;
				mostrar(id);
			}
		}
		
		
	}
//	crea un mensaje XML y lo guarda con la i 
    public void guardar(){
    	String nombre=this.tgcnombre.getText();
    	String usuario=this.tgcusuario.getText();
    	String password=this.tgcpass1.getText();
    	String servS=this.tgcservS.getText();
    	String portS=this.tgcportS.getText();
    	String servP=this.tgcserP.getText();
    	String portP=this.tgcportP.getText();
    	String tipo="";
		
    	if(nombre!=null&&usuario!=null&&password!=null&&servS!=null&&portS!=null&&servS!=null&&portP!=null){
    	CuentaXML cuenta=new CuentaXML(nombre,usuario,password,servS,portS,servP,portP,0);
    	cuenta.guarda();
    	System.out.println("Cuenta guardada");
    	}
    }

	
	public void mostrar(int id){
		Element e=(Element)new CuentaXML().getId(Integer.toString(id));
		CuentaXML cuenta=CuentaXML.getCuentaXML(e);
		this.tvcnombre.setText(cuenta.getNombre());
		this.tvcservS.setText(cuenta.getServS());
		this.tvcportS.setText(cuenta.getPortS());
		this.tvcservP.setText(cuenta.getServP());
		System.out.println(cuenta.getPortP());
		this.tvcportP.setText(cuenta.getPortP());
		this.tvcusuario.setText(cuenta.getUsuario());
	}
}
