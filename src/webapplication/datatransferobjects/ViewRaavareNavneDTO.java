package webapplication.datatransferobjects;

public class ViewRaavareNavneDTO {
	private int pbId;
	private int rbId;
	private String raavareNavn;
	
	public ViewRaavareNavneDTO(int pbId, int rbId, String raavareNavn) {
		super();
		this.pbId = pbId;
		this.rbId = rbId;
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
