import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

public class KOV_DDL_GENERATOR {public static void main(String[] args) {
		   		KOV_DDL_GENERATOR obj_DDL_GEN = new KOV_DDL_GENERATOR();
		obj_DDL_GEN.get_file();
		
	  }

public void get_file()
//To get the file and construct the query
{
	String str_csvFile = "C:\\Users\\mathumitha\\Desktop\\Kovaion\\Asset\\Kovaion Technology Solutions\\Test_table.csv";
	BufferedReader br = null;
	String str_line = "";
	String str_csvSplitBy = ",";
		try {
br = new BufferedReader(new FileReader(str_csvFile));
int check=1;
String str_query = "";
 String str_primary_key="";
while ((str_line = br.readLine()) != null) {
if (check==1) {	
	String[] table = str_line.split(str_csvSplitBy);
	str_query="Create table " + table[0].toUpperCase() +" (";
}
	 else {
		 if (check!=2) {str_query=str_query+ " , ";}
		 String[] field = str_line.split(str_csvSplitBy);
		 int int_size =  field.length;
		 //Field Name
		 if (int_size>=1){
		 		 if (!field[0].trim().isEmpty())  
		 		 {if (field[0].trim().contains(" ")){throw new IllegalArgumentException("Field Name has blank space");}
		 		 else{ str_query=str_query+field[0].toUpperCase();}}else{
		 			throw new IllegalArgumentException("Field Name is blank");}
		 		 }
		 //End- Field Name
		 //Field Type
		 if (int_size>=2){
		 		if (!field[1].trim().isEmpty())
		 			{
		 		 str_query=str_query+" " + field[1];}else{
		 			throw new IllegalArgumentException("Field type is blank"); }
		 		 }
		 //End- Field Type
		 //Field Length
		 if (int_size>=3){
		 		if (!field[2].trim().isEmpty())
		 		 {str_query=str_query+"(" + field[2] +")";}}
		 //End- Field Length
		 //Primary Key Constraint
		 if (int_size>=4){
			 		 		if (field[3].contentEquals("Y"))
		 		 		 			if (str_primary_key.trim().isEmpty()){
		 						 				str_primary_key= " ,PRIMARY KEY(" + field[0].toUpperCase();
		 				}
		 		 else{str_primary_key=str_primary_key+","+ field[0].toUpperCase();
		 		 }} 
		 //End- Primary Key Constraint
		 //Not Null constraint
		 if (int_size>=5){
			 if (field[4].contentEquals("Y")){
				 str_query=str_query+" NOT NULL";}}
		 //End- Not Null constraint
		 //Unique Constraint
		 if (int_size>=6){
			 if (field[5].contentEquals("Y")){
				 str_query=str_query+" UNIQUE ";}}
		//End- Unique Constraint
		 //Default Constraint
		 if (int_size>=7){
			 if(!field[6].trim().isEmpty()){str_query=str_query + " DEFAULT '"+field[6].trim()+"' ";}
		 }
		 //End- Default Constraint
		 }
check=check+1;
		}
if (str_primary_key.trim().length()>0){
str_primary_key=str_primary_key+ ")";
str_query=str_query+str_primary_key;}
 if (str_query.trim().length()>0){str_query=str_query+ ")";}
System.out.println(str_query );
KOV_DDL_GENERATOR obj_DDL_GEN = new KOV_DDL_GENERATOR();
obj_DDL_GEN.run_query(str_query);
}	
	catch (FileNotFoundException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	} 	
}
public void run_query(String str_query){
	//Establish connection to database
	Statement st_ddl_query;
	KOV_DB_CONN obj_DB_CONN=new KOV_DB_CONN(); 	
	obj_DB_CONN.db_connection();
	try{
		//Run the create statement
	st_ddl_query = obj_DB_CONN.conn.createStatement();
	 PreparedStatement prep_st_query = obj_DB_CONN.conn.prepareStatement(str_query);
prep_st_query.executeUpdate();
	}
	catch (Exception e){
		System.out.println(e);
	}
	}
}
