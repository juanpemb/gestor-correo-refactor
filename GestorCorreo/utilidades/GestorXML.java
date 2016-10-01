/*
 * GestorXML.java
 *
 * Created on 5 de abril de 2006, 21:24
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package utilidades;
import java.io.*;
import java.util.*;
import java.util.Iterator;
import org.jdom.*;
import org.jdom.output.*;
import org.jdom.input.*;

/**
 *
 * @author juanpedro
 */
public class GestorXML {
    private File file;  //archivo donde se guarda el codigo xml
    /** Creates a new instance of GestorXML */
    public GestorXML(String file) {
        this.file=new File(file);
    }
    public GestorXML(File file){
        this.file=file;
    }
    public Element getElemento(String value, String parametro){
        SAXBuilder builder=new SAXBuilder(false);
        try{
            Document doc=builder.build(file);    
            Element root=doc.getRootElement();
            //elemento que devuelvo puede ser un mensaje, una cuenta de configuracion, ó una libreta de direcciones 
            Element resultado=null;
            List lista=root.getChildren();
            Iterator it=lista.iterator();
            while(it.hasNext()){
                Element aux=(Element)it.next();
                String cad = aux.getAttributeValue(value);
                if(cad!=null&&cad.equals(parametro)){
                    System.out.println("GestorXML: getElement :traza --> elemento encontrado");
                    resultado=aux;
                }
            }
            return resultado;
                      
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Error GertorXML: "+e.getMessage());
            return null;            
        }
        
    } 
    public void addElemento(Element elemento ){
            try{
                SAXBuilder builder=new SAXBuilder(false);
                Document doc=builder.build(file);
                Element root=doc.getRootElement();
                root.addContent(elemento);
                XMLOutputter out=new XMLOutputter("", true);
                FileOutputStream fos=new FileOutputStream(file);
                out.output(doc,fos);
                fos.flush();
                fos.close();
                
                
            }catch(Exception e){
                e.printStackTrace();
                System.out.println("GestorXML: addElemento "+e.getMessage());
            }
    }
    public void removeElemento(String parametro, String value){
        //elimina un elemento con el valor id(unico) 
        try{
            SAXBuilder builder=new SAXBuilder(false);
            Document doc=builder.build(file);
            Element root=doc.getRootElement();
            List lista=root.getChildren();
            System.out.println("numero de elementos --> "+lista.size());
            Iterator it=lista.iterator();
            while(it.hasNext()){
                Element aux=(Element)it.next();
                String temp=aux.getAttributeValue(parametro);
                if((temp!=null)&&temp.equals(value)){
                    it.remove();
                    System.out.println("borra elemento");
                    break;
                }
                
            }
            XMLOutputter out=new XMLOutputter("", true);
            FileOutputStream fos=new FileOutputStream("prueba.xml");
            out.output(doc,fos);
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("GestorXML: removeElemento "+e.getMessage());
        }
    }
    public List getList(){
        //devuelve la lista con todos los elementos del archivo XML 
        try{
            SAXBuilder builder=new SAXBuilder(false);
            Document doc=builder.build(file);
            return doc.getRootElement().getChildren();
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("GestorXML: getList -->"+e.getMessage()); 
            return null;
        }   
    }  
    public int getLastId(){
        List l=this.getList();
        Iterator it=l.iterator();
        int mayor=0,i=0;
        while(it.hasNext()){
            
            Element e=(Element)it.next();
            String aux=e.getAttributeValue("id");
            i=Integer.parseInt(aux.trim());
            if(i>=mayor){
                mayor=i;
            }
            
        }
        return mayor;
    }
    public static void main(String args[]){
        GestorXML gestor=new GestorXML("prueba.xml");
        Element elemento=new Element("elemento1");
        elemento.setAttribute("parametro1","valorParametro1");
        elemento.setAttribute("fsadfsadf","fdsfad");
        //gestor.addElemento(elemento);
        elemento = gestor.getElemento("parametro1", "valorParametro1");
        //numero de elementos actualmente
        System.out.println("Numero de elementos____\\\\");
        System.out.println("Numero de elementos    //"+gestor.getLastId());
        if(elemento==null){
            System.out.println("Elemento nulo");
        }
        else{
            String s=elemento.getAttributeValue("parametro2");
            System.out.println("Valor del parametro -->"+s);
            System.out.println(elemento.toString());
        }
        
        
        /* LinkedList lista=gestor.getList();
        Iterator it=(Iterator)lista.iterator();
        while(it.hasNext()){
            Element aux=(Element)it.next();
            System.out.println("Elemento: -->"+ aux.getName());
        }*/


    }
}
