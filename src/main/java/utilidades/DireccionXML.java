/*
 * DireccionXML.java
 *
 * Created on 1 de enero de 2004, 0:56
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package utilidades;
import java.util.List;
import org.jdom.Element;
/**
 *
 * @author juanpedro
 */
public class DireccionXML implements ArchivoXML {
    
    private String nombre,apellidos, direccion, email,telefono;
    private int id;
    private GestorXML gestor;
    /** Creates a new instance of DireccionXML */
    public DireccionXML(String nombre, String apellidos,String direccion, String email, String telefono ) {
        this.nombre=nombre;
        this.apellidos=apellidos;
        this.direccion=direccion;
        this.email=email;
        this.telefono=telefono;
        gestor=new GestorXML("direcciones.xml");
        this.id=gestor.getLastId()+1;
        
    }
    public DireccionXML(){
    	gestor=new GestorXML("direcciones.xml");
    
    }
    
	public String getApellidos() {
		return apellidos;
	}
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
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
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
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
    
    public Object getId(String id){
        return  gestor.getElemento("id",id);    	
    }
    //podria ser un metodo etatico
    public DireccionXML getDireccionId(String id){
    	
    	Element e=gestor.getElemento("id",id);
    	DireccionXML direccion=new DireccionXML(e.getAttributeValue("nombre"),e.getAttributeValue("apellidos"),e.getAttributeValue("direccion"),e.getAttributeValue("email"), e.getAttributeValue("telefono") );   	
    	return direccion;
    }
    
    public void borra(String parametro, String value){
        gestor.removeElemento(parametro, value);    
    }
    
    public Element getElement(){
        Element e=new Element("direccion");
        e.setAttribute("id",Integer.toString(id));
        e.setAttribute("nombre",this.nombre);
        e.setAttribute("apellidos",this.apellidos);
        e.setAttribute("direccion",this.direccion);
        e.setAttribute("email",this.email);
        e.setAttribute("telefono",this.telefono);
        return e;
    }
   
    public static void main(String args[]){
        DireccionXML direccion =new DireccionXML("Juan Pedro",  "Martínez", "Ascension Rosell", "juanpe1@correo.es", "987654543");
        direccion.guarda();
        System.out.println("Echo el intento de guardar\nNumero de elementos -->"+direccion.getLastId());
       direccion=new DireccionXML();
       Element e=(Element)direccion.getId("2");
       System.out.println(e.getAttributeValue("id"));
        
    }
    
}
