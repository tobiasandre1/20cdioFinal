package webapplication.sqlconnector;


// erstat konstanterne nedenfor

public abstract class Constant
{
	public static final String
		server					= "eu-cdbr-west-01.cleardb.com",  	// database-serveren
		database				= "heroku_64a70c889854da5",  			//"jdbcdatabase", // navnet paa din database = dit studienummer
		username				= "b59d0a775e066f", 					// dit brugernavn = dit studienummer 
		password				= "fac2ad55"; 			// dit password som du har valgt til din database
	
	public static final int
		port					= 3306;
}
