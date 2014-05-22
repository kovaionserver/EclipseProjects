
import java.sql.*;
public class KOV_DB_CONN {
	static Connection conn=null;
	public static void main(String[] args){

	}
	public void db_connection(){
	    try {
	        Class.forName("org.postgresql.Driver");
conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/KOVDVL","postgres", "KOV@2014");
	    }
	    catch (Exception e) {
	        System.out.println("Unable to connect to Database");
	        System.out.println(e);
	    }
	}

	}
