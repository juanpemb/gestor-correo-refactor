/*
 * ArchivoXML.java
 *
 * Created on 1 de enero de 2004, 0:35
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package utilidades;

/**
 *
 * @author juanpedro
 */
public interface ArchivoXML {
    public Object getId(String id);
    public void guarda(Object object);
    public void borra(String parametro, String valor);
}
