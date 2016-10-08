package vista;

import modelo.Correo;
import org.junit.Test;

import javax.swing.tree.DefaultMutableTreeNode;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class ArbolTest {

    private Correo correo = mock(Correo.class);
    private Arbol arbol = new Arbol();

    @Test
    public void test_add_correo(){
        arbol.addCorreo(correo);

        assertNotNull(arbol.correo);

    }
    @Test
    public void test_delete_correo(){
        arbol.deleteCorreo();
        assertNull(arbol.correo);
    }

    @Test
    public void test_add_borradores(){
        DefaultMutableTreeNode arboles = arbol.getNodo0();

        assertThatTreeNodeIsCorrect(arboles);
    }

    @Test
    public void test_default_carpeta(){
        assertThat(arbol.getCarpeta(),equalTo(Arbol.ENTRADA));
    }

    private void assertThatTreeNodeIsCorrect(DefaultMutableTreeNode arboles) {
        assertThat(arboles.getChildAt(0).toString(), equalTo("Bandeja entrada"));
        assertThat(arboles.getChildAt(1).toString(), equalTo("Enviados"));
        assertThat(arboles.getChildAt(2).toString(), equalTo("Borradores"));
        assertThat(arboles.getChildAt(3).toString(), equalTo("Plantillas"));
        assertThat(arboles.getChildAt(4).toString(), equalTo("Eliminados"));
    }

}