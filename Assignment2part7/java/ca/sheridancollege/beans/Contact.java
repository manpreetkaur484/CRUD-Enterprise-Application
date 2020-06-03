package ca.sheridancollege.beans;
import java.io.Serializable;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Contact implements Serializable{
	private static final long serialVersionUID = 1L;
	private String name;
	private String phoneNumber;
	private String address;
	private String email;
	private int id;
	private String role;
	
	private String[] roles = {"ROLE_ADMIN", "ROLE_GUEST", "ROLE_MEMBER"};
	
	public Contact(String name, String phoneNumber, String address, String email, String role) {
		this.name=name;
		this.phoneNumber=phoneNumber;
		this.address=address;
		this.email=email;
		this.role=role;
	}
}
