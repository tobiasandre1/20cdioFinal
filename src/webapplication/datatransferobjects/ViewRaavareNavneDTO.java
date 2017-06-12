package webapplication.datatransferobjects;

public class ViewRaavareNavneDTO {
	private int pbId;
	private int receptId;
	private int rbId;
	private int raavareId;
	private String raavareNavn;
	
	public ViewRaavareNavneDTO(int pbId, int receptId, int rbId, int raavareId, String raavareNavn) {
		super();
		this.pbId = pbId;
		this.receptId = receptId;
		this.rbId = rbId;
		this.raavareId = raavareId;
		this.raavareNavn = raavareNavn;
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
	public String getRaavareNavn() {
		return raavareNavn;
	}
	public void setRaavareNavn(String raavareNavn) {
		this.raavareNavn = raavareNavn;
	}
	
	@Override
	public String toString(){
		return pbId + "\t" + rbId + "\t" + raavareNavn;
	}
	
	
}
