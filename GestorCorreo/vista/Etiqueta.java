package vista;






public class Etiqueta{
	String etiqueta;
	public Etiqueta(String de, String asunto, String fecha){
		String inicio="<html><table width=100%><tr width=100%><td>";
		String intermedio="</td><td> | </td><td align=right>";
		String fin="</td></tr></table><html>";
		etiqueta=inicio+de+intermedio+asunto+intermedio+fecha;
	}
	
	public Etiqueta(String de, String asunto, String fecha,boolean leido){
		
		if(leido){
			String inicio="<html><table width=100%><tr width=100%><td width=500>";
			String intermedio="</td><td> | </td><td width=300 align=right>";
			String fin="</td></tr></table><html>";
			etiqueta=inicio+de+intermedio+asunto+intermedio+fecha;
		}else{
			String inicio="<html><table width=100%><tr width=100%><td>";
			String intermedio="</td><td>   </td><td align=right>";
			String fin="</td></tr></table><html>";
			etiqueta=inicio+de+intermedio+asunto+intermedio+fecha;
		}
	
	}
	
	/**
	 * @return Returns the etiqueta.
	 */
	public String getEtiqueta() {
		return etiqueta;
	}
	/**
	 * @param etiqueta The etiqueta to set.
	 */
	public void setEtiqueta(String etiqueta) {
		this.etiqueta = etiqueta;
	}
}