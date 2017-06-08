package webapplication.datatransferobjects;

import java.util.ArrayList;
import java.util.List;

/**
 * Operatoer Data Access Objekt
 * 
 * @author mn/tb
 * @version 1.2
 */

public class OperatoerDTO
{
	/** Operatoer-identifikationsnummer (opr_id) i omraadet 1-99999999. Vaelges af brugerne */
	int oprId;                     
	/** Operatoernavn (opr_navn) min. 2 max. 20 karakterer */
	String oprNavn;                
	/** Operatoer-initialer min. 2 max. 3 karakterer */
	String ini;                 
	/** Operatoer cpr-nr 10 karakterer */
	String cpr;                 
	/** Operatoer password min. 7 max. 8 karakterer */
	String password;   
	/** Operatoer roller "admin", "pharmacist", "foreman", "laborant". max. 40 karakterer**/
	List<String> roles;
	/** Is the user active? TRUE or FALSE**/
	boolean oprActive;

	public OperatoerDTO(int oprId, String oprNavn, String ini, String password, boolean oprActive)
	{
		this.oprId = oprId;
		this.oprNavn = oprNavn;
		this.ini = ini;
		this.password = password;
		this.oprActive = oprActive;
		this.roles = new ArrayList<String>();
	}
	
	public OperatoerDTO(int oprId, String oprNavn, String ini, String password, boolean oprActive, List<String> roles)
	{
		this.oprId = oprId;
		this.oprNavn = oprNavn;
		this.ini = ini;
		this.password = password;
		this.oprActive = oprActive;
		this.roles = roles;
	}
	
    public OperatoerDTO(OperatoerDTO opr)
    {
    	this.oprId = opr.getOprId();
    	this.oprNavn = opr.getOprNavn();
    	this.ini = opr.getIni();
    	this.password = opr.getPassword();
    	this.roles = opr.getRoles();
    	this.oprActive = opr.getOprActive();
    }
    
    public int getOprId() { return oprId; }
	public void setOprId(int oprId) { this.oprId = oprId; }
	public String getOprNavn() { return oprNavn; }
	public void setOprNavn(String oprNavn) { this.oprNavn = oprNavn; }
	public String getIni() { return ini; }
	public void setIni(String ini) { this.ini = ini; }
	public String getPassword() { return password; }
	public void setPassword(String password) { this.password = password; }
	public List<String> getRoles(){return roles;}
	public void setRoles(List<String> roles){this.roles = roles;}
	public void addRole(String role){this.roles.add(role);}
	public boolean getOprActive(){return oprActive;}
	public void setOprActive(boolean oprActive){this.oprActive = oprActive;	}
	@Override
	public String toString() { 
		String rlst = "";
		for(String s:roles){rlst += s + " ";}
		return oprId + "\t" + oprNavn + "\t" + ini + "\t" + password + "\t" + oprActive + "\t" + rlst; }
	
	
	
}
