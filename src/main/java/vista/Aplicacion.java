package vista;

import modelo.Correo;
import org.jdom.Element;
import utilidades.CuentaXML;
import utilidades.MensajeXML;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.LinkedList;
import java.util.List;



public class Aplicacion extends JFrame implements Runnable{
    
	//lista cn todos los gestores de correo (recupero XML)
	private List gestoresCorreo;
	private Correo correo; 	
	//componentes del controlador
	private int carpetaActual,carpetaNueva;
	private int mensajeActual,mensajeNuevo;
	private List mensajes;	//lista  MensajesXML
	private MensajeXML mensaje; //mensaje seleccionado
    private java.util.List correos;       //lista de cuentas de correo
    //componentes Vista
    private Menus menu;
    private Botones botones;
    private Arbol arbol;
    private JTabla tabla;
    private Combo combo;
    private VisorHTML visor;
    private javax.swing.JSplitPane divisor1;//, divisor2;
    private JPanel divisor2;
    private JScrollPane scrollCombo;
    //Hilo
    Thread hilo;
    
        
    /** Creates a new instance of Aplicacion */
    public Aplicacion() {
        super("GCorreo");
    	
        //XXX A�ado el gestor de correo
        this.gestoresCorreo=listaCuentasCorreo();
        //XXX EL gestor de correo por defecto ocupa el lugar 0 en la colleccion
        if(gestoresCorreo!=null&&gestoresCorreo.size()>0){
        	CuentaXML cuenta=(CuentaXML) gestoresCorreo.get(0);
        	correo=CuentaXML.getCorreo(cuenta.getElement());
        	
        }else{
        	System.out.println("No aadolos gestores de correo");
        }
        
        
        
        
        /*Inicio las paneles*/
        Container container=getContentPane();
        container.setLayout(new GridLayout(2,1));
        divisor1=new javax.swing.JSplitPane();
        divisor1.setDividerLocation(JSplitPane.VERTICAL_SPLIT);
        
        divisor2=new JPanel();
        //divisor2.setDividerLocation(JSplitPane.HORIZONTAL_SPLIT);
        //a�ado el menu
        //divisor2=new JPanel();
        divisor2.setLayout(new GridLayout(1,1));
        JPanel auxMenu=new JPanel();
        auxMenu.setLayout(new GridLayout(4,1));
        menu=new Menus(correo);
        auxMenu.add(menu,FlowLayout.LEFT);
        auxMenu.add(new JLabel("<html><br></html>"));
        auxMenu.add(new JLabel("<html><br></html>"));
        botones=new Botones(correo, mensajes);
        auxMenu.add(botones);
        JPanel pcab=new JPanel();
        pcab.setLayout(new GridLayout(2,1));
        //botones=new Botones();
        pcab.add(auxMenu);
        //pcab.add(botones);
        
        pcab.add(divisor1);
        
                
        //a�ado el divisor principal
        arbol=new Arbol();
        JPanel panel1=new JPanel();
        String asunto[][]={{"No Leido","Compra regalos de Navidad"},{"No leido","Revisa el correo"},{"Leido","Publicidad MSN"},{"Leido","Dedicale tiempo al PFC"}};
        String estado[]={"Asunto","Estado"};
        
        tabla=new JTabla(asunto,estado);
        combo=new Combo(Combo.getObjects(MensajeXML.getList()));
        try{            
            visor=new VisorHTML("texto de bienvenidad",true);
        }catch(Exception e){}
        //divisor2.add(new JScrollPane(tabla));
        scrollCombo=new JScrollPane(combo);
        divisor2.add(scrollCombo);
        //divisor2.add(new JScrollPane(visor));
        
        divisor1.setDividerLocation(150);
        divisor1.add(new JScrollPane(arbol),JSplitPane.LEFT);
        divisor1.add(divisor2,JSplitPane.RIGHT);
        //divisor1.add(divisor2);
        pcab.add(divisor1);
        container.add(pcab);
        container.add(new JScrollPane(visor));
        //pack();                
        this.setSize(800,600);
        setLocation(100,50);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        

        System.out.println("Termina el constructor\n\n\n\n\n\n/n/n/n/n(n/n/n/n/n/n/n");
        
        
        //se instancia el hilo
        hilo=new Thread(this);
        hilo.run();
        mensajes=MensajeXML.getList(1);
        
        
    }
    public List listaCuentasCorreo(){
    	List cuentas=(List)new  LinkedList();
    	cuentas=CuentaXML.getCuentasXML();    	
    	return cuentas;
    }
    
    public static void main(String args[]){
        
        new Aplicacion();
        File file=new File(".");
        System.out.println("-->"+file.getAbsolutePath());
        
       
    }


	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run(){
		int contador=0;
		boolean cambioCarpeta=false;
		while(true){
			//Inicializacion
			if(contador==0){
				
				
			}
			//compruebo cual es la bandeja selccionada
			if(carpetaActual!=arbol.getCarpeta()){
				//depuracion
				System.out.println("Carpeta Actual "+carpetaActual);
				
				//actualizo la carpeta
				carpetaActual=arbol.getCarpeta();
				//a�ado los mensajes del tipo reuperado(de momento los muestro todo)
				
				divisor2.setVisible(true);
				mensajes=MensajeXML.getList(carpetaActual);
				this.divisor2.remove(scrollCombo);
				combo=new Combo(Combo.getObjects(mensajes));				
				scrollCombo=new JScrollPane(combo);
				scrollCombo.repaint();
				this.divisor2.add(scrollCombo);				
				this.divisor2.setVisible(true);
				this.setVisible(true);
				
				//repaint();
				
				System.out.println("Numero de mensajes"+mensajes.size());
				cambioCarpeta=true;
			}
			
			//compruebo cual es el item seleccionado del combo
			if(mensajeActual!=combo.getMensajeSeleccionado()||cambioCarpeta){
				mensajeActual=combo.getMensajeSeleccionado();
				if(mensajes.size()>0&&mensajeActual>=0&&mensajeActual<mensajes.size()){
					mensaje=MensajeXML.getMensaje((Element)mensajes.get(mensajeActual));
				}
				System.out.println("Nuevo Msj n� "+mensajeActual);
				System.out.println("Texto del mensaje\n"+mensaje.toStringHTML());
				if(mensaje!=null){
					//visor=new VisorHTML(mensaje.toStringHTML(),true);
					visor.setMensaje(mensaje);
					visor.pintar();
					//repaint();
				}
				cambioCarpeta=false;
			}			
			
			try {
				hilo.sleep(1000);
			} catch (InterruptedException e) {
				// do nothing
				e.printStackTrace();
			}
		}
	}
}
