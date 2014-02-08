package access;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MySQLAccess {
	
  private Statement statement = null;
  private PreparedStatement preparedStatement = null;
  private ResultSet resultSet = null;
  
  final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
  
  // method to get the current date and time
  public String now(){
	  
	Calendar cal = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
    return sdf.format(cal.getTime());
  }
  
  // get connection to driver
  public Connection connectDriver() throws Exception{
	 
	  Connection connect = null;
	  
	  try {
		  Class.forName("com.mysql.jdbc.Driver");
		  connect = DriverManager.getConnection("jdbc:mysql://r3ach.myds.me/AAL?" + "user=AAL&password=gruppe02");
		  return connect;
	  } 
	  catch (Exception e) {
		  throw e;
	  }
  }
  
  // dummy-method
  private void writeMetaData(ResultSet resultSet) throws SQLException {
    //   Now get some metadata from the database
    // Result set get the result of the SQL query
    
    System.out.println("The columns in the table are: ");
    
    System.out.println("Table: " + resultSet.getMetaData().getTableName(1));
    for  (int i = 1; i<= resultSet.getMetaData().getColumnCount(); i++){
      System.out.println("Column " +i  + " "+ resultSet.getMetaData().getColumnName(i));
    }
  }

  // test-purpose
  public void writeResultSet(ResultSet resultSet) throws SQLException {
    // ResultSet is initially before the first data set
    while (resultSet.next()) {
      // It is possible to get the columns via name
      // also possible to get the columns via the column number
      // which starts at 1
      // e.g. resultSet.getSTring(2);
      String id = resultSet.getString("id");
      String name = resultSet.getString("name");
      String last_name = resultSet.getString("last_name");
      String gender = resultSet.getString("gender");
      String email = resultSet.getString("email");
      String website = resultSet.getString("webpage");
      Timestamp date = resultSet.getTimestamp(7);
      System.out.println("ID: " + id);
      System.out.println("Name: " + name);
      System.out.println("Familienname: " + last_name);
      System.out.println("Geschlecht: " + gender);
      System.out.println("E-Mail: " + email);
      System.out.println("Website: " + website);
      System.out.println("Timestamp: " + date.toString());
    }
  }

  public void close(Connection connect) {
    try {
      if (resultSet != null) {
        resultSet.close();
      }

      if (statement != null) {
        statement.close();
      }

      if (connect != null) {
        connect.close();
      }
    } catch (Exception e) {

    }
  }

} 