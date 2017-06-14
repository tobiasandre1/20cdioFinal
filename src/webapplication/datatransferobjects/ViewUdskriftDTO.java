package webapplication.datatransferobjects;

public class ViewUdskriftDTO {

	private ProduktBatchKompDTO pbkomp;
	
	private String oprNavn;
	private int raavareId;
	private String raavareNavn;
	private int receptId;
	private int status;
	
	int pbId; 	  // produktbatchets id
	int rbId;        // i omraadet 1-99999999
	double tara;
	double netto;
	int oprId;					// operatoer-nummer
	
	public ViewUdskriftDTO(ProduktBatchKompDTO pbkomp, String oprNavn, int raavareId, String raavareNavn, int receptId,
			int status) {
		super();
		this.pbkomp = pbkomp;
		this.oprNavn = oprNavn;
		this.raavareId = raavareId;
		this.raavareNavn = raavareNavn;
		this.receptId = receptId;
		this.status = status;
		
		this.pbId = pbkomp.getPbId();
		this.rbId = pbkomp.getPbId();
		this.tara = pbkomp.getTara();
		this.netto = pbkomp.getNetto();
		this.oprId = pbkomp.getOprId();
	}

	public ProduktBatchKompDTO getPbkomp() {
		return pbkomp;
	}

	public void setPbkomp(ProduktBatchKompDTO pbkomp) {
		this.pbkomp = pbkomp;
	}

	public String getOprNavn() {
		return oprNavn;
	}

	public void setOprNavn(String oprNavn) {
		this.oprNavn = oprNavn;
	}

	public int getRaavareId() {
		return raavareId;
	}

	public void setRaavareId(int raavareId) {
		this.raavareId = raavareId;
	}

	public String getRaavareNavn() {
		return raavareNavn;
	}

	public void setRaavareNavn(String raavareNavn) {
		this.raavareNavn = raavareNavn;
	}

	public int getReceptId() {
		return receptId;
	}

	public void setReceptId(int receptId) {
		this.receptId = receptId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	@Override
	public String toString(){
		return pbkomp + "\t" + oprNavn + "\t" + raavareId + "\t" + raavareNavn + "\t" + receptId + "\t" + status;
	}

	public int getPbId() {
		return pbId;
	}

	public void setPbId(int pbId) {
		this.pbId = pbId;
	}

	public int getRbId() {
		return rbId;
	}

	public void setRbId(int rbId) {
		this.rbId = rbId;
	}

	public double getTara() {
		return tara;
	}

	public void setTara(double tara) {
		this.tara = tara;
	}

	public double getNetto() {
		return netto;
	}

	public void setNetto(double netto) {
		this.netto = netto;
	}

	public int getOprId() {
		return oprId;
	}

	public void setOprId(int oprId) {
		this.oprId = oprId;
	}
}
