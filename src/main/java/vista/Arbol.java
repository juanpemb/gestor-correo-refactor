package vista;

import modelo.Correo;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class Arbol extends JTree implements AnadeCorreo, MouseListener {

    Correo correo;
    private DefaultMutableTreeNode treeNode0;
    private DefaultMutableTreeNode treeNode1;
    private DefaultMutableTreeNode treeNode2;
    private DefaultMutableTreeNode treeNode3;
    private DefaultMutableTreeNode treeNode4;
    private DefaultMutableTreeNode treeNode5;

    static int ENTRADA = 1, SALIDA = 2, BORRADOR = 3, PLANTILLAS = 4, ELIMINADOS = 5;
    private int carpeta = ENTRADA;
    private boolean actualizado = false;

    public Arbol() {
        super(getDefaultNodos());
        treeNode0 = getDefaultNodos();
        addMouseListener(this);
    }

    public DefaultMutableTreeNode getNodo0() {
        return treeNode0;
    }

    public int getCarpeta() {
        return carpeta;
    }
    @Override
    public void addCorreo(Correo correo) {
        this.correo = correo;
    }

    @Override
    public void deleteCorreo() {
        correo = null;
    }

    public static DefaultMutableTreeNode getDefaultNodos() {
        DefaultMutableTreeNode treeNode0 = new DefaultMutableTreeNode("Carpetas Locales");
        DefaultMutableTreeNode treeNode1 = new DefaultMutableTreeNode("Bandeja entrada");
        DefaultMutableTreeNode treeNode2 = new DefaultMutableTreeNode("Enviados");
        DefaultMutableTreeNode treeNode3 = new DefaultMutableTreeNode("Borradores");
        DefaultMutableTreeNode treeNode4 = new DefaultMutableTreeNode("Plantillas");
        DefaultMutableTreeNode treeNode5 = new DefaultMutableTreeNode("Eliminados");
        treeNode0.add(treeNode1);
        treeNode0.add(treeNode2);
        treeNode0.add(treeNode3);
        treeNode0.add(treeNode4);
        treeNode0.add(treeNode5);

        return treeNode0;
    }

    public void mouseClicked(MouseEvent e) {
        int selRow = this.getRowForLocation(e.getX(), e.getY());
        if (selRow == Arbol.ENTRADA) {
            carpeta = ENTRADA;
        } else if (selRow == Arbol.SALIDA) {
            carpeta = SALIDA;
        } else if (selRow == Arbol.BORRADOR) {
            carpeta = BORRADOR;
        } else if (selRow == Arbol.PLANTILLAS) {
            carpeta = PLANTILLAS;
        } else if (selRow == Arbol.ELIMINADOS) {
            carpeta = ELIMINADOS;
        }
    }


    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
}