/*
 * GestorCorreo.java
 *
 * Created on 21 de diciembre de 2005, 10:57
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package modelo;


//kelos2000@hotmail.com david tseuhoo@hotmail.com dvchy

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.LinkedList;
import java.util.Properties;
import java.util.Vector;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.URLName;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import utilidades.MensajeXML;

import com.sun.mail.smtp.SMTPSSLTransport;

/**
 * @author juanpedro
 *
 */
public  class GestorCorreo implements Correo
{

	private String host, hostEntrante;	
	private String puertoSMTP, puertoPOP;
	//	private String para;
	private String de;
	private String usuario;
	private String password;
	private Session sesion;
	private Store store;	//es el almacen de la clase
	private Folder folder;  //es la carpeta de la clase
	
	/**
	 * @param h
	 * @param puertoSMTP
	 * @param he
	 * @param puertoPOP
	 * @param d direccion de correo del usuario
	 * @param u usuario
	 * @param pwd password
	 */
	public GestorCorreo(String h,String puertoSMTP,String he,String puertoPOP, String d,String u, String pwd)
	{
		
		host=h;
		de=d;	
		usuario=u;
		password=pwd;
		hostEntrante=he;
		this.puertoSMTP=puertoPOP;
		this.puertoPOP=puertoPOP;
	
	}
	public GestorCorreo(String h,String he, String d,String u, String pwd)
	{
		host=h;	
		de=d;	
		usuario=u;
		password=pwd;
		hostEntrante=he;
		puertoSMTP="25";
		puertoPOP="110";
	
	}
	

	public void setHost(String a)
	{
		host=a;
	}
	public void setHostEntrante(String a)
	{
		hostEntrante=a;
	}
	public void setDe(String a)
	{
		de=a;
	}
	public void setPassword(String a)
	{
		password=a;
	}
	public void setUsuario(String a)
	{
		usuario=a;
	}
	public String getHost(String a)
	{
		return host;
	}
	public String getHostEntrante(String a)
	{
		return hostEntrante;
	}
	public String getDe(String a)
	{
		return de;
	}
	public String getPassword(String a)
	{
		return password;
	}
	public String  getUsuario(String a)
	{
		return usuario;
	}
	public Folder getFolder() {
		return folder;
	}
	public void setFolder(Folder folder) {
		this.folder = folder;
	}
	public String getPuertoPOP() {
		return puertoPOP;
	}
	public void setPuertoPOP(String puertoPOP) {
		this.puertoPOP = puertoPOP;
	}
	public String getPuertoSMTP() {
		return puertoSMTP;
	}
	public void setPuertoSMTP(String puertoSMTP) {
		this.puertoSMTP = puertoSMTP;
	}
	public Session getSesion() {
		return sesion;
	}
	public void setSesion(Session sesion) {
		this.sesion = sesion;
	}
	public Store getStore() {
		return store;
	}
	public void setStore(Store store) {
		this.store = store;
	}
	public String getDe() {
		return de;
	}
	public String getHost() {
		return host;
	}
	public String getHostEntrante() {
		return hostEntrante;
	}
	public String getPassword() {
		return password;
	}
	public String getUsuario() {
		return usuario;
	}

	public void initStore()
	{	
		Properties p=System.getProperties();
		p.put("mail.smtp.host", host);
		p.put("mail.smtp.auth", "true");	
		sesion=Session.getDefaultInstance(p,null);
		try
		{
			store=sesion.getStore("pop3");
			System.err.println("Debug ::::: "+hostEntrante+" - "+usuario+ " - "+password);
			store.connect(hostEntrante, usuario, password);
			System.out.println("Conecta con el servidor");
		}catch(javax.mail.MessagingException e)
		{
			System.err.println("error en leeMensajes");
			e.printStackTrace();
		}
			
	
	}
	public void initFolder()
	{
		try
		{	if(!store.isConnected()){
				System.out.println("El store no esta conectado, porfavor hable con el administrador");
				return;
			}			
			store.isConnected();
			folder=store.getFolder("INBOX");
			folder.open(Folder.READ_WRITE);
			if(folder.isOpen())System.out.println("Folder abierto");
                       
		}catch(javax.mail.MessagingException e)
		{
			System.err.println("error en initFolder");
			e.printStackTrace();
		}
	} 
	public void closeStore()
	{	
		try
		{
			store.close();
		}	
		catch(javax.mail.MessagingException e)
		{
			System.err.println("error en closeStore");
			e.printStackTrace();
		}
		
	}
	public void closeFolder()
	{
		try
		{
			folder.close(true);
		}catch(javax.mail.MessagingException e)
		{
			System.err.println("error en closeFolder");
			e.printStackTrace();
		}
	}
	
	public Message creaMensaje(String to,String asunto,String texto)
	{
//		Properties p=System.getProperties();
//		p.put("mail.smtp.host", host);
//		p.put("mail.smtp.auth", "true");
	//	Session sesion=Session.getDefaultInstance(p, null); //aqui puedo añadir la clave
		//sesion.setDebug(false);
		MimeMessage m=new MimeMessage(sesion);
	
		try
		{	
			//m.setFrom(new InternetAddress(de));
			m.setFrom();
			m.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
			
			m.setSubject(asunto);
			MimeMultipart mmp=new MimeMultipart();
			BodyPart bp=new MimeBodyPart();
			bp.setContent(texto, "text/plain");
			mmp.addBodyPart(bp);
			m.setContent(mmp);
			Flags fs=m.getFlags();
                        
                        System.out.println("Numero de baderas soportadas: "+fs.getSystemFlags().length);
                        
		}catch(Exception e)				   
		{
			System.err.println("en creaMensaje\n");
			System.out.println("Gestor Correo :CreaMensaje: "+e.getMessage());
		}
		return m;
		
		
	}
	public Message incluirHTML(Message msj, String texto)
	{
		try
		{
			Multipart mp=(Multipart)msj.getContent();
			BodyPart p=new MimeBodyPart();
			p.setContent(texto, "text/html");
			
			//faltan direcciones
			
			mp.addBodyPart(p);
			
			Message m=new MimeMessage(sesion);
			m.setFrom(msj.getFrom()[0]);
			m.setSubject(msj.getSubject());
		
			m.addRecipients(Message.RecipientType.TO, msj.getRecipients(Message.RecipientType.TO));	
			
		//	mp.addBodyPart(m);
			m.setContent(mp);
			
			return m;
		}catch(Exception e)
		{
			System.err.println("Error en incluir HTML\n");
			e.printStackTrace();
			return null;
		}
	}
	
	public Message creaMensajeHTML(String to,String asunto,String texto)
	{
		MimeMessage m=new MimeMessage(sesion);
		try
		{
			m.setFrom(new InternetAddress(de));
			m.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
			m.setSubject(asunto);
			BodyPart bp=new MimeBodyPart();
			bp.setContent(texto, "text/html");
			MimeMultipart mmp=new MimeMultipart();
			mmp.addBodyPart(bp);
			
			m.setContent(mmp);
			return m;	
			
		}catch(Exception e)
		{
			System.out.println("error en crea mensajeHTML");
			e.printStackTrace();
			return null;
		}
		
		
	}
	

	
	public void mandaMensaje(Message m)   //manda un mensaje
	{
//			Properties p=System.getProperties();
//			p.put("mail.smtp.host", host);
//			p.put("mail.smtp.auth","true");
//			Session sesion=Session.getDefaultInstance(p, null); //aqui puedo añadir la clave
		try
		{
			Transport t =sesion.getTransport("smtp");
			t.connect(host,usuario, password);//conecta con el servidor smtp
			if(m==null)System.out.println("El mensaje es nulo");
			t.sendMessage(m, m.getAllRecipients());
			t.close();
			
		}catch(javax.mail.MessagingException e)
		{
			
			System.err.println("Error en mandaMensaje");
			e.printStackTrace();
		}
	
	}
	
	public void mandaMensaje(MensajeXML msjXML){
		//convierto el mensajeXML a un Message le añade un archivo adjunto si es necesario
		Message message = this.creaMensaje(msjXML.getDestino(),msjXML.getAsunto(),msjXML.getTexto());
		if(msjXML.getFile()!=null){
			message=this.incluirArchivo(message,msjXML.getFile().getAbsolutePath());
		}
		this.mandaMensaje(message);
		System.out.println("El mensajeXML fue enviado");
	
	}

	public void mandaMensajeSSL(Message m)
	{
		try
		{
			SMTPSSLTransport  t=new SMTPSSLTransport(sesion,new URLName(host));
			t.connect(host,usuario, password);//conecta con el servidor smtp
			System.out.println("Conecta con el servidorSSL\n");
			t.sendMessage(m, m.getAllRecipients());
			System.out.println("mensaje enviado\n");
			t.close();
			
			
		}catch(MessagingException e)
		{
			System.err.println("Error en mandaMensajeSSL");
			e.printStackTrace();
		}
	
	
	
	
	}



	public Message[] leeCorreos()
	{
		Properties p=System.getProperties();
		p.put("mail.smtp.host", host);
		Session sesion=Session.getDefaultInstance(p,null);
		try
		{
			
		//	initStore();
		//	initFolder();
			
			if (folder.isOpen())System.out.println("La carpeta esta abierta");
			
			Message[] m=folder.getMessages();
			Message[] respuestaAuxiliar=new Message[m.length];
			Message[] respuesta;
			int longitud=0;
			for(int i=0;i<m.length;i++){
				System.out.println("Bandera Deleted puesta en true");
				m[i].setFlag(Flags.Flag.DELETED,true);
				respuestaAuxiliar[i]=m[i];
				longitud++;
			}
			
			respuesta=new Message[longitud];
			
			for (int i=0;i<longitud;i++){				
				
				respuesta[i]=respuestaAuxiliar[i];
				
			}
			System.out.println("lectura de "+m.length+" Correos");
			return m;
			
		}catch(javax.mail.MessagingException e)
		{
			System.err.println("Error al leer correo");
			e.printStackTrace();
			System.err.println("Error al leer correo");
			return null;
		}	
	

	}			//este metodo lee todos los mensajes del la cuenta 
	
	

//	public void leerMensajesHTML()
//	{
//	}

//para incluir imagenes en los mensajes es necesario tratarlas como un atachment y reerenciarlas con una URL

	public Message incluirImagen(Message msj,String imagen)
	{
		try
		{
			Message mensaje=new MimeMessage(sesion);
			
			mensaje.setSubject(msj.getSubject());
			mensaje.setFrom((msj.getFrom()[0]));
			mensaje.addRecipients(Message.RecipientType.TO, msj.getRecipients(Message.RecipientType.TO));
			
			//parte de la imagen
			
			BodyPart bp=new MimeBodyPart();
			//BodyPart bp=(BodyPart)msj.getContent();
			
			String htmlText="<h1>imagen</h1>"+"<img src=cid:\""+imagen+"\">";	//la imagen es incluida c0mo hipertexto
			bp.setContent(htmlText, "text/html");
			
			//recojo el Multipart del mensaje recibido por parametro
			//y le añado la parte que ontiene la referencia a la imagen
			//MimeMultipart mp=(MimeMultipart)new MimeMultipart();
			MimeMultipart mp=(MimeMultipart)msj.getContent();
			mp.addBodyPart(bp);
			
			//creamos la parte de la imagen
			bp=new MimeBodyPart();
			
			//incluimos la imagen al mensaje
			DataSource fds=new FileDataSource(imagen);
			bp.setDataHandler(new DataHandler(fds));
			bp.setHeader("Content-ID", "memememe");
			
			//lo añado a la multipart
			
			mp.addBodyPart(bp);
			//lo 
			mensaje.setContent(mp);
			
			return mensaje;
		
		}catch(Exception e)
		{
			System.out.println("error en incluye imagen");
			e.printStackTrace();
			return null;
			
		}			
			
	
	
	}

	
	//este metodo añade al mensaje un atachament y devuelve el mensaje 
	public  Message incluirArchivo(Message m , String filename)
		{
			try
			{
				Message resultado=new MimeMessage(sesion);
				resultado.setSubject("Fwd: "+m.getSubject());
				//resultado.setFrom(new InternetAddress(de));
				resultado.addRecipient(Message.RecipientType.TO,m.getAllRecipients()[0]);

				
				BodyPart mbp=new MimeBodyPart();
				
				
				//incluyo u multiplart para combinar las partes
				
				Multipart mp=new MimeMultipart();
				//recojo el mensaje a reenviar en un mbp y lo añado al multipart
				mbp.setDataHandler(m.getDataHandler());
				mp.addBodyPart(mbp);
				
				//añado el archivo al mensaje
				
				mbp=new MimeBodyPart();
				DataSource source=new FileDataSource(filename);
				mbp.setDataHandler(new DataHandler(source));
				mbp.setFileName(filename);
				mp.addBodyPart(mbp);
				
				//pongo el multipart en el mensaje
				
				resultado.setContent(mp);
				
				return resultado;
			}catch(MessagingException me)
			{
				System.err.println("Error en incluirArchivo ");
				me.printStackTrace();
				return null;
			}	
	
	}
	
	//Este metodo lee el mensaje de correo electronico ysi tiene un atachenebnt lo guarda en el disco
	public void recogeArchivo(Message m)
	{
		try
		{
			Multipart mp=(Multipart)m.getContent();
			for (int i=0,n=mp.getCount();i<n;i++)
			{
				Part p=mp.getBodyPart(i);
				String disposition=p.getDisposition();
				if((disposition!=null )&&(disposition.equals(Part.ATTACHMENT))||(disposition.equals(Part.INLINE)))
				{
					System.out.println("estoy en recoge archivo\nllamo a saveFile");
					saveFile(p.getFileName(),p.getInputStream());
				}
				
			}
		}catch(Exception e)
		{
			System.err.println("Error en recoge archivo");
			e.printStackTrace();
		}
	}

	public MensajeXML toMensajeXML(Message m) throws MessagingException {
		// TODO Auto-generated method stub
		MensajeXML mensaje;
		InternetAddress origenA[]=(InternetAddress[]) m.getFrom();
		InternetAddress destinoA[]=(InternetAddress[])m.getRecipients(Message.RecipientType.TO);
		String origen=origenA[0].getAddress(), destino=destinoA[0].getAddress(),asunto=m.getSubject(),tipo,texto=leeCorreo(m), adjunto;
		
		if(getAdjuntos(m)[0]!=null){
			adjunto=getAdjuntos(m)[0];
			mensaje=new MensajeXML(origen,destino,MensajeXML.ENTRADA,asunto,"Leido",texto,new Date().toString(),new File(adjunto));
		}else{
			mensaje=new MensajeXML(origen,destino,MensajeXML.ENTRADA,asunto,"Leido",texto,new Date().toString());
		}
		
		return mensaje;
	}
	
	public String leeCorreo(Message m)
	{	try
		{
			String resultado=""; 	//esta es la cadena en l que se pondra el mensaje de texto o html
			Multipart mp=(Multipart)m.getContent();
			
			for (int i=0,n=mp.getCount();i<n;i++)
			{
				Part p=mp.getBodyPart(i);
				String disposition=p.getDisposition();
				
				if (disposition==null)
				{
					if (p.isMimeType("text/plain"))
					{
						resultado=resultado+(String)p.getContent();
					}
					else if(p.isMimeType("text/html"))
					{
						resultado=resultado+p.getContent();
			
						System.out.println("es texto html");
					}
			
					else
					{
	//					resultado=resultado+p.getContent();
						System.out.println("No es texto plano ni html");
	//					System.out.println(p.getContent());
					}
				}
				else if ((disposition!=null )&&(disposition.equals(Part.ATTACHMENT))||(disposition.equals(Part.INLINE))) //es un atachement
				{
					//es un atachement
					System.out.println("Es un atachament\n");
					
					
					saveFile(p.getFileName(), p.getInputStream());
				}
			}
			System.out.println("Mensaje recibido\n"+resultado);
			
			return resultado;
		}catch(MessagingException me)
		{
			System.err.println("Error en leeCorreo\n");
			me.printStackTrace();
			return null;
		}catch(IOException ioe)
		{
			System.err.println("Error en leeCorreo\n ");
			ioe.printStackTrace();
			return null;
		}
			
	}
	
	public String [] getAdjuntos(Message m){
		try
		{
			String [] resultado; 	//esta es la cadena en l que se pondra el mensaje de texto o html
			LinkedList lista=new LinkedList();
			Multipart mp=(Multipart)m.getContent();
			int j=0;
			for(int i=0,n=mp.getCount();i<n;i++){
				Part p=mp.getBodyPart(i);
				String disposition=p.getDisposition();
				if((disposition!=null)&&(disposition.equals(Part.ATTACHMENT))){
					lista.add(p.getFileName());
					j++;
				}			
			}
			
			resultado=new String[j+1];
			int i=0; 
			while(i<j){
				resultado[i]=(String)lista.get(i);	
				i++;
			}
			
			return resultado;
			
		}catch(Exception e){
			System.out.println("GestorCorreo: getAdjuntos: "+e.getMessage());
			return null;
		}
		
	}

	protected void saveFile(String filename, InputStream is)throws IOException
	{
		if(filename==null || is==null){
			System.err.println("No guarda el adjunto");
			return;
		}
		String name, ext;
		name=getName(filename);
		ext=getExtension(filename);
		
		File f=new File(filename);
		for (int i=0;f.exists();i++)
		{
			f=new File(name+i+'.'+ext);
		}
		
		//FileInputStream fis=(FileInputStream)is;
		
		FileOutputStream fos=new FileOutputStream(f);
		
		int c;
		while ((c=is.read())!=-1)
		{
			fos.write(c);
			fos.flush();
			
		}

	//	FileChannel canalFuente=is.getChannel();
	//	FileChannel canalDestino=fos.getChannel();
	//	canalFuente.transferTo(0,canalFuente.size(),canalDestino);
	
	
	
		is.close();
		fos.close();
		
		
		
	}

	public  void responderMensaje(Message m,String para,String texto)
	 {
		try
		{	
			MimeMessage respuesta=(MimeMessage)m.reply(true);
			InternetAddress[] a=new InternetAddress[1];
			a[0]=new InternetAddress(para);
			respuesta.setReplyTo(a);
			respuesta.setText(texto);
			Transport t =sesion.getTransport("smtp");
			t.connect(host,usuario, password);//conecta con el servidor smtp
			t.sendMessage(m, m.getAllRecipients());
			t.close();
		}catch(MessagingException e)
		{
			System.out.println("Error en responderMensajes\n");
			e.printStackTrace();
			
		}	
		
	
	
	}
	public void reenviarMensaje(Message m, String para) 
	{
		try
		{
			MimeMessage enviar=new MimeMessage(sesion);
			
			enviar.setSubject("Fwd: "+m.getSubject());
			enviar.setFrom(new InternetAddress(de));
			enviar.addRecipient(Message.RecipientType.TO,new InternetAddress(para));
			
			//mi parte del mensaje
			
			BodyPart mbp=new MimeBodyPart();
			mbp.setText("incluyo mi mensaje\n");
			
			//incluyo u multiplart para combinar las partes
			
			Multipart mp=new MimeMultipart();
			//añado mi texto al mensaje
			mp.addBodyPart(mbp);
			//recojo el mensaje a reenviar en un mbp y lo añado al multipart
			mbp=new MimeBodyPart();
			mbp.setDataHandler(m.getDataHandler());
			mp.addBodyPart(mbp);
			
			//añado el multipart al mensaje a reenviar
			
			enviar.setContent(mp);
			
			Transport t =sesion.getTransport("smtp");
			t.connect(host,usuario, password);//conecta con el servidor smtp
			t.sendMessage(enviar, enviar.getAllRecipients());
			t.close();
			
		}catch(MessagingException me)
		{
			System.err.println("Error en reenviarMensaje\n");
			me.printStackTrace();
		}	
		
		
		
		
	}
	/*guarda en disco el mensaje*/
	//metodo deprecated
	
	public void guardaMensaje(Message m,String s)
	{
		try
		{	
			/*recupero el valor del vector a escribir*/
			Vector v=abreMensajes(s);
			v.add(m);
			
		
			ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream(s));
			oos.reset();
			oos.writeObject(v);
			oos.close();
		
		
		
		}catch(IOException ioe)
		{
			System.err.println("Error en guardaMensaje\n");
			ioe.printStackTrace();
			
		}
	
	
	
	
	}
	/*recupera el mensaje del disco duro*/
	public Vector abreMensajes(String s)
	{
		try
		{
			ObjectInputStream ois=new ObjectInputStream(new FileInputStream(s));
			Vector v=(Vector)ois.readObject();
			ois.close();
			return v;
		
		}catch(Exception e)
		{	
			System.out.println("Error en abreMensaje\n");
			e.printStackTrace();
			return null;
		}
	}
	
	

	public  void borrarMensaje(int i)
		{
		try
		{	
			
			
			
		//	Folder folder=store.getFolder("INBOX");
		//	folder.open(Folder.READ_WRITE);
			Message m=folder.getMessage(i);
			m.setFlag(Flags.Flag.DELETED, true);
		//	folder.expunge();
			
		//	folder.close(true);
		//	store.close();
		//	para cerrar el almacen o la carpeta se deben usar los metodos creados 
		//	para eso
		
		}catch(javax.mail.MessagingException e)
		{
			System.out.println("Error en borrarMensaje");
			e.printStackTrace();
		}	
		
	
	
	}
	
	private String getName(String f)
	{	if(f == null)
			return "";
	
		String aux=f;
		String nombre=null;	
		int i=aux.lastIndexOf('.');
		
		if (i > 0  &&  i<aux.length()-1)
		{
			nombre=aux.substring(0,i);
		}
		return nombre;
	}
	
	private String getExtension(String f) {
        if(f==null)
        	return "";
		String ext = null;
        String s = f;
        int i = s.lastIndexOf('.');

        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }
        return ext;
    }


	public static void main(String args[])throws Exception
	{
		System.out.println("Prueba de gestion de correo\n");
	//	GestionCorreo gc=new GestionCorreo("mailhost.terra.es","pop3.terra.es","chiclemb@terra.es","chiclemb.terra.es","292829");
		GestorCorreo gc=new GestorCorreo("127.0.0.1","127.0.0.1","juanpe1..localhost","juanpe1..localhost","111");
	
		gc.initStore();
		gc.initFolder();
		//Message[] mensajes=gc.leeCorreos();
                gc.mandaMensaje(gc.creaMensaje("juanpe1@localhost", "pruebas del GC" , "Mensaje desde el gestor de correo"));
                System.out.println("Envio el mensaje");
                
         Message mensajeTemp=gc.creaMensaje("juanpe2@localhost","Asunto temporal","Texto dsadfasdfadf");
         Flags flags=mensajeTemp.getFlags();
         Flags.Flag banderas[]=flags.getSystemFlags();
         System.out.println("Numero de banderas soportadas"+banderas.length);
		//for (int i=0;i<mensajes.length;i++)
		//{
		//	System.out.println(gc.leeCorreo(mensajes[i]));
                        
		//}
//		gc.guardaMensaje(mensajes[0], "mensaje.msj");
//		Vector recuperado=gc.abreMensajes("mensaje.msj");
		
	
	//	System.err.println(mensajes.length+"mensajes en la carpeta");
		
		
	//	for(int i=0; i<mensajes.length;i++)
	//	{
	//		System.out.println("Mensaje:\n"+mensajes[i].getContent()+"\n");
	//		gc.recogeArchivo(mensajes[i]);
	//	}
		
		
		//gc.borrarMensaje(1);
	//	Message m=gc.creaMensaje("chicle_jpmb@yahoo.com","proyecto de chicle" ,"HOLA de nuevo  soy chicle te envio un dibujo hecho por mi, a ver si te gusta unbeso");
	//	incluyo archivo al mensaje	
	//	System.out.println("añade un archivo adjunto");
	//	m=gc.incluirImagen(m,"Dibujo.jpg");
		
	//	gc.mandaMensaje(m);
	//	gc.mandaMensajeSSL(m);
	//	Message aux=gc.añadeArchivo(mensajes[0],"MVC.pdf");
	//	gc.mandaMensaje(aux);
	//	Message[] mensajes=gc.leeCorreos();
	//	gc.leeCorreo(mensajes[0]);	
	
	//	m=gc.incluirArchivo(m, "MVC.pdf");
	//	m=gc.incluirImagen(m, "Dibujo.jpg");
	//	gc.mandaMensaje(m);
		
		
	
	//	
	//	for (int i=0;i<mensajes.length;i++)
	//	{
	//		System.out.println(gc.leeCorreo(mensajes[i]));
	//	}
	//	gc.saveFile("MVC.pdf", new FileInputStream("MVC6.pdf"));

	//	intento leer el archivo recibido	
	//	System.out.println("intento leer el archivo");
	//	gc.leeCorreo(mensajes[0]);
	
        MensajeXML mensajeXML=new MensajeXML("juanpe1..localhost", "juanpemb@gmail.com", -1,"Prueba del metodo enviaMensaje(XML)","trabajo","texto enviado ",new Date().toString());        
        //gc.mandaMensaje(mensajeXML);
		gc.leeCorreos();
                
		gc.closeFolder();
		gc.closeStore();
		
		
	//	System.out.println("pruebo el metodo saveFile\n");
	//	gc.saveFile("MVC.pdf", new FileInputStream("MVC.pdf"));
	
	
	}
	/* (non-Javadoc)
	 * @see modelo.Correo#toMensajeXML(javax.mail.Message)
	 */
		
	
	
	
}
