package webapplication.datatransferobjects;

public class ViewVejningDTO {

	int opr_id;
	String opr_navn;
	int tara;
	int netto;
	int raavare_id;
	String raavare_navn;
	int maengde;

	public ViewVejningDTO(int opr_id, String opr_navn, int tara, int netto, int raavare_id, String raavare_navn, int maengde) {
		
		this.opr_id = opr_id;
		this.opr_navn = opr_navn;
		this.tara = tara;
		this.netto = netto;
		this.raavare_id = raavare_id;
		this.raavare_navn = raavare_navn;
		this.maengde = maengde;
		
	}

	public int getOpr_id() {
		return opr_id;
	}

	public void setOpr_id(int opr_id) {
		this.opr_id = opr_id;
	}

	public String getOpr_navn() {
		return opr_navn;
	}

	public void setOpr_navn(String opr_navn) {
		this.opr_navn = opr_navn;
	}

	public int getTara() {
		return tara;
	}

	public void setTara(int tara) {
		this.tara = tara;
	}

	public int getNetto() {
		return netto;
	}

	public void setNetto(int netto) {
		this.netto = netto;
	}

	public int getRaavare_id() {
		return raavare_id;
	}

	public void setRaavare_id(int raavare_id) {
		this.raavare_id = raavare_id;
	}

	public String getRaavare_navn() {
		return raavare_navn;
	}

	public void setRaavare_navn(String raavare_navn) {
		this.raavare_navn = raavare_navn;
	}

	public int getMaengde() {
		return maengde;
	}

	public void setMaengde(int maengde) {
		this.maengde = maengde;
	}
	
	public String toString() {return opr_id + "\t" + opr_navn + "\t" + tara + "\t" + netto + "\t" + raavare_id + "\t" + raavare_navn + "\t" + maengde;}

}
