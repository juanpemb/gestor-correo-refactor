/*
 * CuentaXML.java
 *
 * Created on 1 de enero de 2004, 0:27
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package utilidades;
import modelo.Correo;
import modelo.impl.GestorCorreo;
import modelo.impl.MailServerArgs;
import org.jdom.Element;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
/**
 *
 * @author juanpedro
 */
public class CuentaXML implements ArchivoXML{
    private String nombre,usuario,password,servS, portS, servP, portP;
    int tipo;
    private int id;    
    private GestorXML gestor;
    static int PREDETERMINADO=0, ORDINARIO=1;
    
	public GestorXML getGestor() {
		return gestor;
	}
	public void setGestor(GestorXML gestor) {
		this.gestor = gestor;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPortP() {
		return portP;
	}
	public void setPortP(String portP) {
		this.portP = portP;
	}
	public String getPortS() {
		return portS;
	}
	public void setPortS(String portS) {
		this.portS = portS;
	}
	public String getServP() {
		return servP;
	}
	public void setServP(String servP) {
		this.servP = servP;
	}
	public String getServS() {
		return servS;
	}
	public void setServS(String servS) {
		this.servS = servS;
	}
	public int getTipo() {
		return tipo;
	}
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
    /** Creates a new instance of CuentaXML */
    public CuentaXML() {
    	gestor=new GestorXML("cuentas.xml");
    }
    public CuentaXML(String nombre, String usuario, String password, String servS, String portS, String servP, String portP,int tipo){
        this.nombre=nombre;
        this.usuario=usuario;
        this.password=password;
        this.servS=servS;
        this.portS=portS;
        this.servP=servP;
        this.portP=portP;
        this.tipo=tipo;
        gestor=new GestorXML("cuentas.xml");        
        this.id=gestor.getLastId()+1;
    }
    public Object getId(String id){
        Element e=gestor.getElemento("id", id);
        if(e!=null)return (Object)e;
        else return null;
    }
    public Element getElement(){
        Element e=new Element("cuenta");
        e.setAttribute("id",Integer.toString(id));
        e.setAttribute("nombre",nombre);
        e.setAttribute("usuario",usuario);
        e.setAttribute("password",password);
        e.setAttribute("servs",servS);
        e.setAttribute("ports",portS);
        e.setAttribute("servp",this.servP);
        e.setAttribute("portp",portP);
        e.setAttribute("tipo",Integer.toString(tipo));
        return e;
    }
    public void borra( String parametro, String value){
        gestor.removeElemento(parametro, value);
    }
    public void guarda(Object o){
        gestor.addElemento((Element)o);
    
    }
    public void guarda(){
        Element e=getElement();
        gestor.addElemento(e);
    }
    public int getLastId(){
        return gestor.getLastId();
    }
    public List getLista(){
        //devuelve una lista con los
        return gestor.getList();
        
    }
    
    public static Correo getCorreo(Element e){
        //devuelve un elemento como un GestorCorreo
    	String nombre=e.getAttributeValue("nombre");
    	String usuario=e.getAttributeValue("usuario");
    	String password=e.getAttributeValue("password");
    	String servS=e.getAttributeValue("servs");
    	String ports=e.getAttributeValue("ports");
    	String servP=e.getAttributeValue("servp");
    	String portP=e.getAttributeValue("portp");
    	String tipoa=e.getAttributeValue("tipo");
    	int tipo=Integer.parseInt(tipoa);
    	
    	
    	GestorCorreo gc=new GestorCorreo(new MailServerArgs(servS, ports, servP, portP, "", usuario, password));
    	if(gc!=null){
    		System.out.println("El gestor de correo NO es nulo");
    	}
    	else System.out.println("El gestor de correo Es nulo");
    	return (Correo)gc;
    }
    public static CuentaXML getCuentaXML(Element e){
    	
    		
    	String nombre=e.getAttributeValue("nombre");
    	String usuario=e.getAttributeValue("usuario");
    	String password=e.getAttributeValue("password");
    	String servS=e.getAttributeValue("servs");
    	String ports=e.getAttributeValue("ports");
    	String servP=e.getAttributeValue("servp");
    	String portP=e.getAttributeValue("portp");
    	String tipoa=e.getAttributeValue("tipo");
    	int tipo=Integer.parseInt(tipoa);
    	
    	
    	return new CuentaXML( nombre, usuario,  password, servS, ports,  servP, portP,tipo);    
    } 
    public static List getCuentasXML(){
		List resultado=(List)new LinkedList();
		CuentaXML cuenta=new CuentaXML();
		if(cuenta==null || cuenta.getLista()==null){
			return resultado;
		}
		List lista=cuenta.getLista();
    	Iterator it=lista.iterator();
    	
    	while(it.hasNext()){
    		Element e=(Element)it.next();
    		CuentaXML cuentaTemp=CuentaXML.getCuentaXML(e);
    		resultado.add(cuentaTemp);    		
    	}
    	    	
    	return resultado;
    }
    
    public static void main(String args[]){
        CuentaXML cuenta=new CuentaXML("Juan Pedro", "juanpe1@correo.es", "111", "127.0.0.1", "25", "127.0.0.1", "110", CuentaXML.PREDETERMINADO);
        cuenta.guarda();
        if(cuenta.getId("1")!=null)System.out.println("JajajajajajajAJAJajaja");
        else System.out.println("JJOJOjojoojojojojo.........");
        
        
    
    }
}
