package logged;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class PromptDatabase {
	
	static Connection con;
	static String url = "jdbc:mysql://localhost:3306/test";

	public static void main(String[] args) throws Exception {
		entryTable();
		//promptTable();
		//dropTable();
		//export();
	}
	
	public static void dropTable() throws Exception{
		con=DriverManager.getConnection(url, "root", "root");
		PreparedStatement ps=con.prepareStatement("DROP TABLE prmptable");
		int i=ps.executeUpdate();
		System.out.println(i+" Table Deleted");
		con.close();
	}
	
	public static void entryTable() throws Exception{
		try {
		con=DriverManager.getConnection(url, "root", "");
		Statement stmt=con.createStatement();
		stmt.execute("CREATE TABLE IF NOT EXISTS logtable"
				+"(username  varchar(20),"
				+"password  varchar(20))");
		
		PreparedStatement ps1=con.prepareStatement("SELECT * FROM logtable WHERE username=? and ?");

		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		
		System.out.println("1.Login\n2.Register\n3.Exit\n\ntype the desired option number then press Enter");
		String st=br.readLine();
		if(st.startsWith("2")) {
		    	System.out.println("Register now: y/n");
			String nu=br.readLine();
			if(nu.startsWith("y")) {
				

			PreparedStatement ps2=con.prepareStatement("insert into logtable values(?,?)");
		
			System.out.println("Enter Username");
			String user2=br.readLine();
			System.out.println("Enter Password");
			String pass2=br.readLine();
			
			ps2.setString(1,user2);
			ps2.setString(2,pass2);
			ps2.executeUpdate();
			System.out.println("Registeration succesful");
		}else {System.out.println("\n");
			entryTable();}

    }else if(st.startsWith("1")) {
	    System.out.println("fill the login details below");
		System.out.println("\nUsername");
		String user1=br.readLine();
		System.out.println("Password");
		String pass1=br.readLine();
		
		ps1.setString(1, user1);
		ps1.setString(2, pass1);
		ResultSet rs=ps1.executeQuery();
		if(rs.next()) {
			System.out.println("log in successful");
			promptTable();
		}
		else {
			System.out.println("Wrong username or password");
			System.out.println("New here? Want to enter register: y/n");
			String nu=br.readLine();
			if(nu.startsWith("n")) {
				System.out.println("\nReturning back to login\n... \n... \nDone");
				entryTable();
			}
			else {
		
		PreparedStatement ps2=con.prepareStatement("insert into logtable values(?,?)");
		
			System.out.println("Enter Username");
			String user2=br.readLine();
			System.out.println("Enter Password");
			String pass2=br.readLine();
			
			ps2.setString(1,user2);
			ps2.setString(2,pass2);
			int i2=ps2.executeUpdate();
			System.out.println(i2+" Logged In");
		 
		con.close();
		}
		}
    }
		else {System.out.println("Thank You");}
		}catch(Exception e) {
			System.out.println(e);
		}System.out.println("\nLogged out");
	}
	
	public static void promptTable() throws Exception{
		try {
		Connection con=DriverManager.getConnection(url,"root","");
		Statement stmt = con.createStatement();
		stmt.execute("CREATE TABLE IF NOT EXISTS prmptable"
         + "  (fname	VARCHAR(10),"
         + "   lname	VARCHAR(10),"
         + "   mobile	INTEGER,"
         + "   email	char(50))");
 
 PreparedStatement ps=con.prepareStatement("INSERT INTO prmptable VALUES(?,?,?,?)");
 BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
 
 System.out.println("\nWelcome to Database");
 
 System.out.println("\nEnter Details");
 
 System.out.println("\nEnter first name");
 String first=br.readLine();
 System.out.println("Enter last name");
 String last=br.readLine();
 System.out.println("Enter mobile no");
 String mob=br.readLine();
 System.out.println("Enter email id");
 String email=br.readLine();
 
 ps.setString(1, first);
 ps.setString(2, last);
 ps.setString(3, mob);
 ps.setString(4, email);
 
 ps.executeUpdate();
 System.out.println("Data entered");
 System.out.println("Want to add another one: y/n");
 String add=br.readLine();
 if(add.endsWith("y")) {
	 promptTable();
 }
 else
 
 con.close();
		}catch(Exception e) {
			System.out.println(e);
		}System.out.println("done");
	}

	public static void export() throws Exception{
		con=DriverManager.getConnection(url, "root", "");
		Statement st=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
		ResultSet i=st.executeQuery("select * from prmptable");
		System.out.println(i+" Table Deleted");
		while(i.next()) {
		System.out.println(i.getString(1)+" "+i.getString(2)+" "+i.getString(3)+" "+i.getString(4));
		}
		con.close();
	}

}
