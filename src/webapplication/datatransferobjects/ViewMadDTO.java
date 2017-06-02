package webapplication.datatransferobjects;

public class ViewMadDTO {

	int recept_id;
	int raavare_id;
	String recept_navn;
	String raavare_navn;
	String leverandoer;
	
	public ViewMadDTO (int recept_id, int raavare_id, String recept_navn, String raavare_navn, String leverandoer){
		
		this.recept_id = recept_id;
		this.raavare_id = raavare_id;
		this.recept_navn = recept_navn;
		this.raavare_navn = raavare_navn;
		this.leverandoer = leverandoer;
		
	}
	
	public int getReceptId() {return recept_id;}
	public void setReceptId(int recept_id) {this.recept_id = recept_id;}
	public int getRaavareId() {return raavare_id;}
	public void setRaavareId(int raavare_id) {this.raavare_id = raavare_id;}
	public String getReceptNavn() {return recept_navn;}
	public void setReceptNavn(String recept_navn) {this.recept_navn = recept_navn;}
	public String getRaavareNavn() {return raavare_navn;}
	public void setReceptId(String raavare_navn) {this.raavare_navn = raavare_navn;}
	public String getleverandoer() {return leverandoer;}
	public void setleverandoer(String leverandoer) {this.leverandoer = leverandoer;}
	public String toString() {return recept_id + "\t" + raavare_id + "\t" + recept_navn + "\t" + raavare_navn + "\t" + leverandoer; }
		
	
		
}
