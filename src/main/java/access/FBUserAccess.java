package access;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;

import objects.FacebookUser;

import com.restfb.types.FacebookType;

// not tested yet

public class FBUserAccess {
	
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	
	public void saveUserInfo(long id, FacebookUser user) throws Exception{
		  
		  MySQLAccess access = new MySQLAccess();
		  
		  try {
			  
			  connect = access.connectDriver();
			  
			  // get current date	  
			  Calendar cal = Calendar.getInstance();
//			  SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
//			  sdf.format(cal.getTime());
			  
			  // check whether there is an instance for the facebook-datas in the database or not
			  int count = 0;
			  preparedStatement = connect.prepareStatement("SELECT COUNT(*) from AAL.FBUSER where id= ? ; ");
			  preparedStatement.setLong(1, id);
			  resultSet = preparedStatement.executeQuery();
			  resultSet.next();
			  count = resultSet.getInt(1);
			  
			  if(count == 1){
				  updateFBUser(id, user); 
			  }
			  else{
				  preparedStatement = connect.prepareStatement("insert into  AAL.FBUSER values (?, ?, ?, ?, ? , ?, ?)");
				  preparedStatement.setLong(1, id);
			      preparedStatement.setLong(2, user.getFbid());
			      preparedStatement.setString(3, user.getName());
			      preparedStatement.setString(4, user.getMiddleName());
			      preparedStatement.setString(5, user.getLastName());
			      preparedStatement.setString(6, user.getGender());
			      preparedStatement.setTimestamp(7, new java.sql.Timestamp(cal.getTimeInMillis()));
			      preparedStatement.executeUpdate();  
			      
			      preparedStatement = connect.prepareStatement("SELECT id, fb_id, name, middle_name, last_name, gender, timestamp FBUSER from AAL.FBUSER");
			      resultSet = preparedStatement.executeQuery();		
			  }
		  } 
		  catch (Exception e) {
		      throw e;
	      } 
		  finally {
		      access.close(connect);
		  }
	  }
	
	public FacebookUser readFBUser(long id) throws Exception{
		  
		  FacebookUser user = new FacebookUser();
		  MySQLAccess access = new MySQLAccess();
		  
		  try {
			 
			  connect = access.connectDriver();
			  
		      preparedStatement = connect.prepareStatement("SELECT fb_id, name, middle_name, last_name, gender, timestamp FBUSER from AAL.FBUSER where id= ? ; ");
		      preparedStatement.setString(1, String.valueOf(id));
	          resultSet = preparedStatement.executeQuery();
	          
	          // put all datas into an user-object
	          while (resultSet.next()) {

	        	  user.setFbid(resultSet.getLong("fb_id"));
	    	      user.setName(resultSet.getString("name"));
	    	      if(resultSet.getString("middle_name") != null){
	    	    	  user.setMiddleName(resultSet.getString("middle_name"));
	    	      }
	    	      if(resultSet.getString("gender") != null){
	    	    	  user.setGender(resultSet.getString("gender"));
	    	      }
	    	      user.setLastName(resultSet.getString("last_name"));
	    	      
	    	      user.setTimestamp(resultSet.getTimestamp(6));

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
	
	public void deleteFBUser(long id) throws Exception{
		  
		  MySQLAccess access = new MySQLAccess();
		  
		  try {
			  
			  connect = access.connectDriver();
			 
			  preparedStatement = connect.prepareStatement("delete from AAL.FBUSER where id= ? ; ");
		      preparedStatement.setString(1, String.valueOf(id));
		      preparedStatement.executeUpdate();
		      
		  } 
		  catch (Exception e) {
		      throw e;
		  } 
		  finally {
		      access.close(connect);
		  }
	  }
	
	public void updateFBUser(long id, FacebookUser user) throws Exception{
		  
		  MySQLAccess access = new MySQLAccess();
		  
		  try {
			  
			  connect = access.connectDriver();
			  
			  Calendar cal = Calendar.getInstance();
//			  SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
//			  sdf.format(cal.getTime());
			 
			  preparedStatement = connect.prepareStatement("update AAL.FBUSER set fb_id =?, name=?, middle_name=?, last_name=?, gender=?, timestamp=? where id= ? ; ");
			  preparedStatement.setLong(1, user.getFbid());
		      preparedStatement.setString(2, user.getName());
		      preparedStatement.setString(3, user.getMiddleName());
		      preparedStatement.setString(4, user.getLastName());
		      preparedStatement.setString(5, user.getGender());
		      preparedStatement.setTimestamp(6, new java.sql.Timestamp(cal.getTimeInMillis()));
		      preparedStatement.setLong(7, id);
		      preparedStatement.executeUpdate(); 

		  } 
		  catch (Exception e) {
		      throw e;
		  } 
		  finally {
		      access.close(connect);
		  }
	}
	
}
