package ca.sheridancollege.database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import ca.sheridancollege.beans.Contact;
import ca.sheridancollege.beans.User;

@Repository
public class DatabaseAccess {

	@Autowired
	protected NamedParameterJdbcTemplate jdbc;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder; 
	
	//ADD CONTACT
	public void addContact(Contact contact) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "INSERT INTO contact_list (name, phoneNumber, address, email, role) VALUES" +
		"(:name, :phoneNumber, :address, :email, :role)";
		parameters.addValue("name", contact.getName());
		parameters.addValue("phoneNumber", contact.getPhoneNumber());
		parameters.addValue("address", contact.getAddress());
		parameters.addValue("email", contact.getEmail());
		parameters.addValue("role", contact.getRole());
		
		jdbc.update(query, parameters);
	}
	
	//GET CONTACT
	public ArrayList<Contact> getContact(){
		String q = "Select * from contact_list";
		ArrayList<Contact> contacts =
				(ArrayList<Contact>)jdbc.query(q, new
	    BeanPropertyRowMapper<Contact>(Contact.class));
		return contacts;
	}
	
	//GET CONTACT BY IDgetContactById
	public Contact getContactById(int id) {
		 MapSqlParameterSource parameters = new MapSqlParameterSource();
		 String q = "SELECT * FROM contact_list where id=:id";
		 parameters.addValue("id", id);
		 ArrayList<Contact> contacts =
				 (ArrayList<Contact>)jdbc.query(q, parameters, new
			BeanPropertyRowMapper<Contact>(Contact.class));
		 
		 if(contacts.size()>0)
			 return contacts.get(0);
		 else
			 return null;
	}
	
	//EDIT CONTACT
	public void editContact(Contact contact) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String q = "UPDATE contact_list SET name =:name,phoneNumber=:phoneNumber,address=:address,email=:email,role=:role WHERE id=:id";
		
		parameters.addValue("id", contact.getId());
		parameters.addValue("name",contact.getName());
		parameters.addValue("phoneNumber",contact.getPhoneNumber());
		parameters.addValue("address",contact.getAddress());
		parameters.addValue("email",contact.getEmail());
		parameters.addValue("role",contact.getRole());
		jdbc.update(q, parameters);
	}
	
	//DELETE STUDENT BY ID
	public void deleteContactById(int id) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String q = "DELETE from contact_list WHERE id=:id";
		parameters.addValue("id", id);
		jdbc.update(q, parameters);
	}

	public  User findUserAccount(String userName) {
		String query = "SELECT * FROM sec_user where userName=:userName";
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("userName", userName);
		ArrayList<User> userList = 
				(ArrayList<User>)jdbc.query(query, parameters,
		new BeanPropertyRowMapper<User>(User.class));
		if (userList.size()>0)
		return userList.get(0);
		else
		return null;
		}

		public List<String> getRolesById(long userId) {
		ArrayList<String> roles = new ArrayList<String>();
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "select user_role.userId, sec_role.roleName "
		+ "FROM user_role, sec_role "
		+ "WHERE user_role.roleId=sec_role.roleId "
		+ "and userId=:userId";

		parameters.addValue("userId", userId);
		List<Map<String, Object>> rows =
				jdbc.queryForList(query, parameters);
		for (Map<String, Object> row : rows) {
		roles.add((String)row.get("roleName"));
		}

		return roles;
		}	
		
		//add user to Database
		public void addUser(String username, String password) {
			MapSqlParameterSource parameters = new MapSqlParameterSource();
				String q =
		"insert into sec_user (userName, encryptedPassword, ENABLED) values (:userName, :password,1)";		
		parameters.addValue("userName", username);
		parameters.addValue("password", passwordEncoder.encode(password));
		jdbc.update(q, parameters);		
		}
		
		public void addRole(long userId, long roleId) {
			MapSqlParameterSource parameters = new MapSqlParameterSource();
			String  q = "INSERT INTO user_role(userId, roleId)values(:userId, :roleId)";
	    	parameters.addValue("userId", userId);
			parameters.addValue("roleId", roleId);
			jdbc.update(q, parameters);
		}
		
		//Asign UserId and Role Id
		public void addRole(String userName, String password, String user) {
			MapSqlParameterSource parameters = new MapSqlParameterSource();
			
			if(user=="member") {
				String q =
						"INSERT INTO sec_role(roleName)values('ROLE_MEMBER')";	
				parameters.addValue("userName", userName);
				parameters.addValue("password", passwordEncoder.encode(password));
				jdbc.update(q, parameters);
			}
			if(user=="guest") {
				String q =
						"INSERT INTO sec_role(roleName)values('ROLE_GUEST')";	
				parameters.addValue("userName", userName);
				parameters.addValue("password", passwordEncoder.encode(password));
				jdbc.update(q, parameters);
			}
			
		}	
		
	//GET CONTACTS FOR ADMIN	
		public ArrayList<Contact> getAdminContacts(){
			//MapSqlParameterSource parameter  = new MapSqlParameterSource();
			String q = "Select * from contact_list where role='ROLE_ADMIN'";

			ArrayList<Contact> users = (ArrayList<Contact>)jdbc.query(q,new BeanPropertyRowMapper<Contact>(Contact.class));
			return users;
			
		}
		
		//GET CONTACTS FOR MEMBER
		public ArrayList<Contact> getMemberContacts(){
			//MapSqlParameterSource parameter  = new MapSqlParameterSource();
			String q = "Select * from contact_list where role='ROLE_MEMBER'";

			ArrayList<Contact> users = (ArrayList<Contact>)jdbc.query(q, new BeanPropertyRowMapper<Contact>(Contact.class));
			return users;
		}
		
		//GET CONTACTS FRO GUEST
		public ArrayList<Contact> getGuestContacts(){
			
			String q = "Select * from contact_list where role='ROLE_GUEST'";
			ArrayList<Contact> users = 
		(ArrayList<Contact>)jdbc.query(q,
				new BeanPropertyRowMapper<Contact>(Contact.class));
			return users;
		}	
}//class closing