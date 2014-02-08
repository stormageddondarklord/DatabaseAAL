package agents.beans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import access.MySQLAccess;
import de.dailab.jiactng.agentcore.AbstractAgentBean;

public class GroupingBean extends AbstractAgentBean {
	
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	private MySQLAccess access = null;
	
	@Override
	public void doStart() throws Exception{
		super.doStart();
		access = new MySQLAccess();
		connect = access.connectDriver();
	}
	
	@Override
	public void execute(){
		System.out.println("Hello, World. It's working");
	}
	
	public void formGroup(long id) throws Exception{
		
		  try {
			  
			  formGroup(id, "book");
			  formGroup(id, "tv");
			  formGroup(id, "game");
			  formGroup(id, "movie");
			  formGroup(id, "interest");
			  formGroup(id, "music");
	          
		  } 
		  catch (Exception e) {
		      throw e;
		  } 
		  finally {
		      access.close(connect);
		  }
		
	}
	
	// TODO: quit group by dislikes
	
	private void formGroup(long id, String label) throws SQLException{
		
		ArrayList<Long> ids = new ArrayList<Long>();
		ResultSet result;
		  
      	preparedStatement = connect.prepareStatement("SELECT "+label+"id from AAL.FB"+label.toUpperCase()+"S where id= ? ; ");
      	preparedStatement.setString(1, String.valueOf(id));
        resultSet = preparedStatement.executeQuery();
        
        // get all interest ids of interests that interest the user
        while (resultSet.next()) {
      	  	ids.add(resultSet.getLong(label+"id"));	        	 
  	  	}
        
        // for every interest find users with same preferences
        for(Long e : ids){
      	  
	  	  	preparedStatement = connect.prepareStatement("SELECT * from AAL.INTERESTGROUP where gid= ? ; ");
	  	  	preparedStatement.setString(1, String.valueOf(e));
	  	  	resultSet = preparedStatement.executeQuery();
	          
		      // check whether the group already exists and whether the user is already signed up.
		      if(resultSet.next()){
		    	  
		    	  
		    	  preparedStatement = connect.prepareStatement("update AAL.INTERESTGROUP set liked=? where gid= ? ; ");
				  preparedStatement.setBoolean(1, true);
				  preparedStatement.setLong(2, resultSet.getLong("gid"));
				  preparedStatement.executeUpdate();
				  
		    	  // look if the user needs to be signed up for the already existing group
		    	  preparedStatement = connect.prepareStatement("SELECT * from AAL.GROUPMEMBER where gid= ? and mid= ? ; ");
		    	  preparedStatement.setString(1, String.valueOf(resultSet.getLong("gid")));
		    	  preparedStatement.setString(2, String.valueOf(id));
		    	  result = preparedStatement.executeQuery();
		    	  
		    	  if(result.next()){
		    		  continue;
		    	  }
		    	  else{
		    		  preparedStatement = connect.prepareStatement("insert into  AAL.GROUPMEMBER values (?, ?)");
		    		  preparedStatement.setString(1, String.valueOf(resultSet.getLong("gid")));
			    	  preparedStatement.setString(2, String.valueOf(id));
			    	  preparedStatement.executeUpdate();
		    	  }
		      }
		  
		      // all users with same interest
		      preparedStatement = connect.prepareStatement("SELECT * from AAL.FB"+label.toUpperCase()+"S where "+label+"id= ? ; ");
		      preparedStatement.setString(1, String.valueOf(e));
	          resultSet = preparedStatement.executeQuery();
	    
	          // count the number of interested users
	          resultSet.last();
	          int count = resultSet.getRow();
	          resultSet.first();
	          
	          // if there are more than 1 user with same preference then form the group and make all users a member of the group
	          if(count > 1){
	        	  preparedStatement = connect.prepareStatement("insert into  AAL.INTERESTGROUP values (?, ?, ?, ?, ?)");
		  		  preparedStatement.setLong(1, resultSet.getLong(label+"id"));
		  		  preparedStatement.setString(2, resultSet.getString("name"));
		  		  preparedStatement.setString(3, resultSet.getString("category"));
		  		  preparedStatement.setString(4, "nothing");
		  		  preparedStatement.setBoolean(5, true);
		  		  preparedStatement.executeUpdate();
		  		  
		  		  resultSet.beforeFirst();
		  		  
		  		  while (resultSet.next()) {
		    		  preparedStatement = connect.prepareStatement("insert into  AAL.GROUPMEMBER values (?, ?)");
		    		  preparedStatement.setLong(1, resultSet.getLong(label+"id"));
		    		  preparedStatement.setLong(2, resultSet.getLong("id"));
		    		  preparedStatement.executeUpdate();
		          }        	 
	  		  }
        }
        
//        quitGroup(id);
	}
	
	private void quitGroup(long id) throws SQLException{
		  
      	preparedStatement = connect.prepareStatement("SELECT * from AAL.INTERESTGROUP where id= ? ; ");
      	preparedStatement.setString(1, String.valueOf(id));
        resultSet = preparedStatement.executeQuery();
        
        while(resultSet.next()){
        	
        	if(resultSet.getBoolean("liked")){
        		continue;
        	}
        	
        	
        }
	}

}
