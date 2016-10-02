/*
 * MensajeXML.java
 *
 * Created on 1 de enero de 2004, 12:17
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package utilidades;
import modelo.Correo;
import org.jdom.Element;

import java.io.File;
import java.util.*;
/**
 *
 * @author juanpedro
 */
public class MensajeXML implements ArchivoXML{
    private int id;
    private String origen,destino, asunto, texto,tipoTexto;	//plain � html
    private String bandeja;	//tipo carpeta a la que pertenece
    boolean leido;			//leido � no leido
    
    private String  fecha;
    private File file;
    private GestorXML gestor;
    public static int ENTRADA=1, SALIDA=2,BORRADOR=3,PLANTILLAS=4,ELIMINADOS=5;
    /** Creates a new instance of MensajeXML */
    public MensajeXML() {
    }
    public MensajeXML( String origen,String destino,int bandeja, String asunto,String tipo, String texto, String fecha, File file){
        
        
        this.origen=origen;
        this.destino=destino;
        this.bandeja=Integer.toString(bandeja);
        this.asunto=asunto;
        this.texto=texto;
        this.fecha=fecha;
        this.file=file;
        this.tipoTexto=tipo;
        //por norma general el archivo file sera "mensajes.xml"
        gestor= new GestorXML("mensajes.xml");
        this.id=gestor.getLastId()+1;
    }

    public MensajeXML(String origen,String destino,int bandeja, String asunto,String tipo, String texto, String fecha){
        
        this.origen=origen;
        this.destino=destino;
        this.bandeja=Integer.toString(bandeja);
        this.asunto=asunto;
        this.texto=texto;
        this.fecha=fecha;
        this.file=null;
        this.tipoTexto=tipo;
        
        //por norma general el archivo file sera "mensajes.xml"
        gestor= new GestorXML("mensajes.xml");
        this.id=gestor.getLastId()+1;
    }
    public MensajeXML(Correo correo){
        //meodo en fase de prueba no se si es un metodo util
    }
    public List getMesajes(){
    	List resultado=gestor.getList();
    	return resultado;
    }
        public Object getId(String id){
        try{
            Element e = gestor.getElemento("id", id);
            //este if puede mejorarse para filtrar mas errores
            if(e!=null){
             return new MensajeXML(e.getAttributeValue("origen"), e.getAttributeValue("destino"),Integer.parseInt(e.getAttributeValue("bandeja")), e.getAttributeValue("asunto"),e.getAttributeValue("tipoTexto"), e.getAttributeValue("texto"), e.getAttributeValue("fecha"), new File(e.getAttributeValue("file")));   
            }   
            return null;
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("E: MensajeXML --> geMensajeXML");
            return null;
        }       
        
    }
    
    public void borra(String parametro, String value){
        gestor.removeElemento(parametro, value);
    }
    public void guarda(Object o){
        gestor.addElemento((Element)o);               
    }
    public void guardaMensaje(){
    	Element e=this.getElement();
    	guarda(e);
    	System.err.println("Mensaje guardado");
    
    }
    public static MensajeXML  getMensaje(Element e){
        String aasunto,adestino,abandeja,aorigen,atipo, atexto,id;
        File archivo=null;
        String afecha;
        id=e.getAttributeValue("id");
        aasunto=e.getAttributeValue("asunto");
        abandeja=e.getAttributeValue("bandeja");
        adestino=e.getAttributeValue("destino");
        aorigen=e.getAttributeValue("origen");
        atipo=e.getAttributeValue("tipoMensaje");
        atexto=e.getAttributeValue("texto");
        afecha=e.getAttributeValue("fecha");
        String aux=e.getAttributeValue("file");
        if(aux!=null)archivo=new File(aux);
        if(e==null||id==null||aasunto==null||adestino==null||aorigen==null||atexto==null){
            //return new MensajeXML();
            return null;
        }
        /*else{
            String aux=e.getAttributeValue("file");
            //String aux1=e.getAttributeValue("fecha");
            if(aux!=null){
                archivo=new File(aux);
            }if(aux1!=null){
                //afecha=new Date(aux1);
                afecha=new Date().toString();
            }else{
                //esto es un error que habra que resolver
                afecha=new Date().toString();
            }
        
           
        }*/
        return new MensajeXML(aorigen, adestino,Integer.parseInt(abandeja), aasunto,atipo, atexto, afecha, archivo);
    }
    public Element getElement(){
        
        Element e;
        e=new Element("mensaje");
        e.setAttribute("id",Integer.toString(this.id));
        e.setAttribute("origen",this.origen);
        e.setAttribute("destino",this.destino);
        e.setAttribute("bandeja",this.bandeja);
        e.setAttribute("asunto",this.asunto);
        e.setAttribute("tipoMensaje",this.tipoTexto);
        e.setAttribute("texto",this.texto);
        e.setAttribute("fecha",this.fecha.toString());
        if(this.file!=null){
            e.setAttribute("file",this.file.toString());
        }else{
            e.setAttribute("file","");
        }
        
        return e;
    }
    public int getLastId(){
        return gestor.getLastId();
    }
    //para mostrar mensajes en el editor HTML
    public String toString(){
    	String resultado="";
   
	    	if(this.origen!=null)resultado="De:\t"+this.origen+"\n";    	
	    	if(this.destino!=null)resultado=resultado+"Para:\t"+this.destino+"\n";
	    	if(this.asunto!=null)resultado=resultado+"Asunto:\t"+this.asunto+"\n";
	    	if(this.fecha!=null)resultado=resultado+"Fecha:\t"+this.fecha+"\n";
	    	if(this.texto!=null)resultado=resultado+"Mensaje:\n"+this.texto;	
    	
    	return resultado;
    }
    public String toStringHTML(){
    	String resultado="";
    	String cabecera="<table>";
    	String pie="</table>";
    	String inicioLinea="<tr><td>";
    	String finLinea="</td></tr>";
    	String espacio="</td><td>";
    	
    	if(this.origen!=null)resultado=cabecera+inicioLinea+"<b>De:\t</b>"+espacio+this.origen+finLinea;    	
    	if(this.destino!=null)resultado=resultado+inicioLinea+"<b>Para:\t</b>"+espacio+this.destino+finLinea;
    	if(this.asunto!=null)resultado=resultado+inicioLinea+"<b>Asunto:\t</b>"+espacio+this.asunto+finLinea;
    	if(this.fecha!=null)resultado=resultado+inicioLinea+"<b>Fecha:\t</b>"+espacio+this.fecha+finLinea+pie;
    	resultado=resultado+"<hr><br>";
    	if(this.texto!=null)resultado=resultado+cabecera+"<tr align=left><td>"+"<b>Mensaje:\n</b>"+"</td></tr><tr><td><br></td></tr>"+inicioLinea+this.texto+finLinea+pie;
    	
    	return resultado;
    }
    
    //Metodos para mostrar mensajes en la tabla
    public String [] getCabeceraTabla(){
    	String [] cabecera=new String[4];
    	cabecera[0]="De";
    	cabecera[1]="Asunto";
    	cabecera[2]="Fecha";
    	cabecera[3]="Estado";
    	return cabecera;
    }
    //devuelve el campo de los mensajes a mostrar dependiendo del tipo(Leido, Enviado,...)
    public String[][] getCuerpo(int tipo){
    	List todosMensajes=gestor.getList();
    	Vector seleccionados=new Vector();
    	Iterator it=todosMensajes.iterator();
    	while(it.hasNext()){
    		Element e=(Element)it.next();
    		MensajeXML msj=MensajeXML.getMensaje(e);
    		if(msj.getBandeja().equals(Integer.toString(tipo)))seleccionados.add(msj);    		
    	}
    	String [][]cuerpo=new String[seleccionados.size()-1][4];
    	for(int i=0;i<seleccionados.size()-1;i++){
    		MensajeXML aux=(MensajeXML)seleccionados.get(i);
    		cuerpo[i][0]=aux.getOrigen();
    		cuerpo[i][1]=aux.getAsunto();
    		cuerpo[i][2]=aux.getFecha();
    		if(aux.isLeido())cuerpo[i][3]="Leido";
    		else cuerpo[i][3]="No leido";
    	}
    	return cuerpo;
    }
      
   
	/**
	 * @return Returns the asunto.
	 */
	public String getAsunto() {
		return asunto;
	}
	/**
	 * @param asunto The asunto to set.
	 */
	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}
	/**
	 * @return Returns the destino.
	 */
	public String getDestino() {
		return destino;
	}
	/**
	 * @param destino The destino to set.
	 */
	public void setDestino(String destino) {
		this.destino = destino;
	}
	/**
	 * @return Returns the fecha.
	 */
	public String getFecha() {
		return fecha;
	}
	/**
	 * @param fecha The fecha to set.
	 */
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getFechaString(){
		return fecha.toString();
	}
	/**
	 * @return Returns the file.
	 */
	public File getFile() {
		return file;
	}
	/**
	 * @param file The file to set.
	 */
	public void setFile(File file) {
		this.file = file;
	}
	/**
	 * @return Returns the gestor.
	 */
	public GestorXML getGestor() {
		return gestor;
	}
	/**
	 * @param gestor The gestor to set.
	 */
	public void setGestor(GestorXML gestor) {
		this.gestor = gestor;
	}
	/**
	 * @return Returns the id.
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id The id to set.
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return Returns the origen.
	 */
	public String getOrigen() {
		return origen;
	}
	/**
	 * @param origen The origen to set.
	 */
	public void setOrigen(String origen) {
		this.origen = origen;
	}
	/**
	 * @return Returns the texto.
	 */
	public String getTexto() {
		return texto;
	}
	/**
	 * @param texto The texto to set.
	 */
	public void setTexto(String texto) {
		this.texto = texto;
	}
	/**
	 * @return Returns the tipoTexto.
	 */
	public String getTipoTexto() {
		return tipoTexto;
	}
	/**
	 * @param tipoTexto The tipoTexto to set.
	 */
	/**
	 * @return Returns the bandeja.
	 */
	public String getBandeja() {
		return bandeja;
	}
	/**
	 * @param bandeja The bandeja to set.
	 */
	public void setBandeja(String bandeja) {
		this.bandeja = bandeja;
	}
	
	/**
	 * @return Returns the leido.
	 */
	public boolean isLeido() {
		return leido;
	}
	/**
	 * @param leido The leido to set.
	 */
	public void setLeido(boolean leido) {
		this.leido = leido;
	}
	public void setTipoTexto(String tipoTexto) {
		this.tipoTexto = tipoTexto;
	}
	public static List getList(){
		
		GestorXML gestor=new GestorXML("mensajes.xml");
		return gestor.getList();
	}
	
	public static  List getList(int id){
		
		GestorXML gestor=new GestorXML("mensajes.xml");
		List lista=gestor.getList();
		List resultado=new LinkedList();
		
		Iterator it=lista.iterator();
		while(it.hasNext()){
			MensajeXML msj=MensajeXML.getMensaje((Element)it.next());
			if(Integer.parseInt(msj.getBandeja())==id){
				resultado.add(msj.getElement());
			}
		}				
		return (List)resultado;
	}
	
	 public static void main(String args[]){
        MensajeXML ms=new MensajeXML("JuanPe1@correo.es","juanpe2@correo.es",1,"pruebo los archivos XML","text/plain" ,"Este es el mensaje que sera guardado e el archivo mensajes.xml",new Date().toString());
        ms.guarda(ms.getElement());
        System.out.println("Guarda el mensaje");
        System.out.println("Numero de mensajes: "+ms.getLastId());
        System.out.println("Mensaje de Texto:\n"+ms.toString());
        String [][]resultado=ms.getCuerpo(1);        
               
        
    }
	
}
