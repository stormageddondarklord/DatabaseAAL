package access;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;

import objects.LivingUser;

public class UserAccess {
	
	  private Connection connect = null;
	  private Statement statement = null;
	  private PreparedStatement preparedStatement = null;
	  private ResultSet resultSet = null;
	
	// a method for creating a new user
	  public void saveNewUser(String name, String lastname, String gender, String email, String website) throws Exception{
		  
		  MySQLAccess access = new MySQLAccess();
		  
		  try {
			  
			  connect = access.connectDriver();
			  
			  // get current date	  
			  Calendar cal = Calendar.getInstance();
//			  SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
//			  sdf.format(cal.getTime());
			  
			  preparedStatement = connect.prepareStatement("insert into  AAL.USER values (default, ?, ?, ?, ? , ?, ?)");
			  preparedStatement.setString(1, name);
		      preparedStatement.setString(2, lastname);
		      preparedStatement.setString(3, gender);
		      preparedStatement.setString(4, email);
		      preparedStatement.setString(5, website);
		      preparedStatement.setTimestamp(6, new java.sql.Timestamp(cal.getTimeInMillis()));
		      preparedStatement.executeUpdate();  
		      
		      preparedStatement = connect.prepareStatement("SELECT id, name, last_name, gender, email, webpage, timestamp USER from AAL.USER");
		      resultSet = preparedStatement.executeQuery();
		      access.writeResultSet(resultSet);
		      
		  } 
		  catch (Exception e) {
		      throw e;
	      } 
		  finally {
		      access.close(connect);
		  }

	  }
	  
	  // methode to update user profile
	  // works flawless, but more efficient way to save data is needed
	  public void updateUser(long id, LivingUser user) throws Exception{
		  
		  MySQLAccess access = new MySQLAccess();
		  
		  try {
			  
			  connect = access.connectDriver();
			  
			  Calendar cal = Calendar.getInstance();
//			  SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
//			  sdf.format(cal.getTime());
			 
			  preparedStatement = connect.prepareStatement("update AAL.USER set name=?, last_name=?, gender=?, email=?, webpage=?, timestamp=? where id= ? ; ");
			  preparedStatement.setString(1, user.getName());
			  preparedStatement.setString(2, user.getLastame());
			  preparedStatement.setString(3, user.getGender());
			  preparedStatement.setString(4, user.getEmail());
			  preparedStatement.setString(5, user.getWebpage());
			  preparedStatement.setTimestamp(6, new java.sql.Timestamp(cal.getTimeInMillis()));
		      preparedStatement.setLong(7, id);
		      preparedStatement.executeUpdate();
		      
		      preparedStatement = connect.prepareStatement("SELECT id, name, last_name, gender, email, webpage, timestamp USER from AAL.USER");
	          resultSet = preparedStatement.executeQuery();
	          access.writeResultSet(resultSet);
		  } 
		  catch (Exception e) {
		      throw e;
		  } 
		  finally {
		      access.close(connect);
		  }
		  
	  }
	  
	  
	  // method to delete an user with given id
	  // works flawless
	  public void deleteUser(long id) throws Exception{
		  
		  MySQLAccess access = new MySQLAccess();
		  
		  try {
			  
			  connect = access.connectDriver();
			 
			  preparedStatement = connect.prepareStatement("delete from AAL.USER where id= ? ; ");
		      preparedStatement.setString(1, String.valueOf(id));
		      preparedStatement.executeUpdate();
		      
		      preparedStatement = connect.prepareStatement("SELECT id, name, last_name, gender, email, webpage, timestamp USER from AAL.USER");
	          resultSet = preparedStatement.executeQuery();
	          access.writeResultSet(resultSet);
	          
	          // deleting the profile results in deleting of the facebook-datas as well
	          FBUserAccess fbaccess = new FBUserAccess();
	          fbaccess.deleteFBUser(id);
	          
		  } 
		  catch (Exception e) {
		      throw e;
		  } 
		  finally {
		      access.close(connect);
		  }
	  }
	  
	  // get all datas of an user of given id
	  // works flawless
	  public LivingUser readUser(long id) throws Exception{
		  
		  LivingUser user = new LivingUser();
		  MySQLAccess access = new MySQLAccess();
		  
		  try {
			 
			  connect = access.connectDriver();
			  
		      preparedStatement = connect.prepareStatement("SELECT id, name, last_name, gender, email, webpage, timestamp USER from AAL.USER where id= ? ; ");
		      preparedStatement.setString(1, String.valueOf(id));
	          resultSet = preparedStatement.executeQuery();
	          
	          // put all datas into an user-object
	          while (resultSet.next()) {

	    	      user.setName(resultSet.getString("name"));
	    	      user.setLastname(resultSet.getString("last_name"));
	    	      user.setGender(resultSet.getString("gender"));
	    	      user.setEmail(resultSet.getString("email"));
	    	      user.setWebsite(resultSet.getString("webpage"));
	    	      user.setTimestamp(resultSet.getTimestamp(7));

	    	    }
		  } 
		  catch (Exception e) {
		      throw e;
		  } 
		  finally {
		      access.close(connect);
		  }
		  
		  // no user found with given id
		  if(user.getName().equals("")){
			  return null;
		  }
		  
		  return user;
		  
	  }

}
