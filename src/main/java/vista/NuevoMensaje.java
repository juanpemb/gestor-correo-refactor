package vista;
import modelo.Correo;
import modelo.impl.GestorCorreo;
import modelo.impl.MailServerArgs;
import modelo.impl.MessageHandler;
import utilidades.MensajeXML;
import utilidades.Utilidades;

import javax.mail.Message;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Date;

public class NuevoMensaje extends JFrame implements  AnadeCorreo,ActionListener
{	private MessageHandler handler = new MessageHandler();
	private Message m;
	private String archivoAdjunto;
	private MensajeXML mensaje;
	private Correo getorCorreo;
	private JPanel p1,p2,paux;
	private JToolBar jtbar;
	private JButton enviar,guardar,adjuntar;
        private JScrollPane p3;
        private JLabel lpara,lde, lasunto, ladjunto;
        private JTextField tpara,tde,tasunto,tadjunto;
        private JTextArea texto;
        private JFileChooser chooser;
	private Correo correo;	//clase capaz de gestionar el correo
        private JButton adj;
	public NuevoMensaje(String s)
	{
		super(s);
		setLayout(new GridLayout(3,1));//2 filas y una columna
		Container c=getContentPane();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		c.setSize(new Dimension(500,400));
		
		
		p1=new JPanel();
		p1.setLayout(new GridLayout(1,2));
		p2=new JPanel();
        p2.setLayout(new GridLayout(4,2));
                
        lpara=new JLabel("<html>&#160;&#160;&#160;&#160;para</html>");tpara=new JTextField("", 10);
        p2.add(lpara);p2.add(tpara);               
        lde=new JLabel("<html>&#160;&#160;&#160;&#160;de</html>");tde=new JTextField("",10);
        p2.add(lde);p2.add(tde);
                
               
        lasunto=new JLabel("<html>&#160;&#160;&#160;&#160;asunto</html>");tasunto=new JTextField("", 10);
        adj=new JButton("Adjunto...");
        p2.add(lasunto);p2.add(tasunto);
                
        ladjunto=new JLabel("<html><body align=center><h4>&#160;&#160;&#160;&#160;adjunto</h6></body></html>");tadjunto=new JTextField("", 10);
        tadjunto.setEditable(false);
        paux=new JPanel();
        paux.setLayout(new GridLayout(1,2));
                
        adj.addActionListener(this);
        paux.add(tadjunto);paux.add(adj);
        p2.add(ladjunto);p2.add(paux);
                
		
          
        texto=new JTextArea("",10,10);
        p3=new javax.swing.JScrollPane(texto);
        //p3.add(texto);
                
		
		String [] nombres={"enviar","adjuntar","guardar"};
		String [] iconos={"MANDAR.png","ADJUNTAR.png","GUARDAR.png"};
		
		Botones botones=new Botones(nombres, iconos,null, null);
		
		//p1.add(botones);
		//p1.add(p2);
		jtbar=new JToolBar();
        this.enviar=  new JButton(new ImageIcon( "imagenes\\"+File.separator+iconos[0]));
        enviar.addActionListener(this);
        this.adjuntar= new JButton(new ImageIcon( "imagenes\\"+File.separator+iconos[1]));
        adjuntar.addActionListener(this);
        this.guardar= new JButton(new ImageIcon( "imagenes\\"+File.separator+iconos[2]));
        guardar.addActionListener(this);
        
        jtbar.add(enviar);
        jtbar.add(adjuntar);
        jtbar.add(guardar);
        
		p1.add(this.jtbar);        
	
		p3.setSize(new Dimension(400,400));
		c.add(p1);
        c.add(p2);
        c.add(p3);
        
//c.add(editor.getJScrollPane());
		pack();
		setSize(400,300);
                setLocation(300, 150);
		setVisible(true);	
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		chooser=new JFileChooser();
		
	}
	public void addCorreo(Correo c)
	{
		this.correo=c;
	}
	public void deleteCorreo()
	{
		this.correo=null;
	}
	
	public void enviarMensaje()
	{
	
		if (correo!=null){
			m=correo.creaMensaje(this.tpara.getText(),this.tasunto.getText(),this.texto.getText());
			
			if(this.archivoAdjunto!=null&&archivoAdjunto.length()>0){
				m=handler.incluirArchivo(m,archivoAdjunto);
			}

			correo.mandaMensaje(m);
			System.out.println("Envio el mensajeaSw	qw	qwq");
			//guarda mensaje en la carperta de salida
			//this.guardar();
			//para cuando se integer en el gestor de correo
			//
			//System.exit(1);
			}
		else{
			System.out.println("Error al enviar mensaje correo NULL");		
		}
	}
	public void adjuntarArchivo(String s)
	{
	}
	//modo-->enviado,no enviado
	public void guardar(int bandeja)
	{
		//debo crear excepciones para manejar los errores
		String de,para,asunto,adjunto,texto;
		de=this.tde.getText();
		para=this.tpara.getText();
		asunto=this.tasunto.getText();
		adjunto="En necesario implementar el adjunto";
		texto=this.texto.getText();
		if(adjunto!=null&&adjunto.length()>3){
			mensaje=new MensajeXML(de,para,bandeja,asunto,"leido",texto,Utilidades.sdf.format(new Date()));
			mensaje.guarda(mensaje.getElement());
		}
		else{
			mensaje=new MensajeXML(de,para,bandeja,asunto,"leido",texto,Utilidades.sdf.format(new Date()),new File(adjunto));
			mensaje.guarda(mensaje.getElement());
		}
		//mensaje.guarda(mensaje.getElement());
	
		System.out.println("Mensaje Guardado");
		this.setVisible(false);
	}
	public void guardar(){
		guardar(MensajeXML.SALIDA);
	}
	
	
	/**
	 * @return Returns the correo.
	 */
	public Correo getCorreo() {
		return correo;
	}
	/**
	 * @param correo The correo to set.
	 */
	public void setCorreo(Correo correo) {
		this.correo = correo;
	}
	/**
	 * @return Returns the getorCorreo.
	 */
	public Correo getGetorCorreo() {
		return getorCorreo;
	}
	/**
	 * @param getorCorreo The getorCorreo to set.
	 */
	public void setGetorCorreo(Correo getorCorreo) {
		this.getorCorreo = getorCorreo;
	}
	/**
	 * @return Returns the mensaje.
	 */
	public MensajeXML getMensaje() {
		return mensaje;
	}
	/**
	 * @param mensaje The mensaje to set.
	 */
	public void setMensaje(MensajeXML mensaje) {
		this.mensaje = mensaje;
	}
	public void actionPerformed(ActionEvent ae){
        	if(ae.getSource()==adj||ae.getSource()==adjuntar){
        		System.out.println("Quiero anadir el adjunto");
        		chooser.showOpenDialog(this);
        		if(chooser.getSelectedFile().getAbsolutePath()!=null)this.archivoAdjunto=chooser.getSelectedFile().getAbsolutePath();
        		System.out.println("Archivo seleccionado--<>>"+archivoAdjunto);
        		this.tadjunto.setText(chooser.getSelectedFile().getName());
        		
        	}else if(ae.getSource()==this.enviar){
        		this.enviarMensaje();
        		System.out.println("Envia mensaje");
        		this.guardar();
        	}else if(ae.getSource()==this.guardar){
        		guardar();
        		System.out.println("Guarda el mensaje");
        		
        	}
        	    	
        }
	public static void main(String s[])throws Exception
	{
		System.out.println("antes de llamar construir el nuevo mensaje");
		NuevoMensaje nm=new NuevoMensaje("Titulo(Prueba)");
		MailServerArgs mailServiceSession = new MailServerArgs("127.0.0.1",null,"127.0.0.1",null,"juanpe1..localhost","juanpe1..localhost","111");
		GestorCorreo gc=new GestorCorreo(mailServiceSession);
		nm.addCorreo(gc);
	}
}