package ca.sheridancollege.controller;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ca.sheridancollege.beans.Contact;
import ca.sheridancollege.database.DatabaseAccess;

@Repository
@Controller
public class ContactController {
	
	@Autowired
	private NamedParameterJdbcTemplate jdbc;

	@Autowired
	@Lazy
	private DatabaseAccess da;

@GetMapping("/")
public String goHome(Model model) {
	model.addAttribute("contact", new Contact());
	return "addContact.html";
}
@GetMapping("/add")
public String addContact(Model model, @ModelAttribute Contact contact) {
	da.addContact(contact);
	model.addAttribute("contact", new Contact());
	return "addContact.html";
}

@GetMapping("/member")
public String viewContact(Authentication authentication,Model model) {
	ArrayList<String> roles= new ArrayList<String>();
	for(GrantedAuthority ga: authentication.getAuthorities()) {
		roles.add(ga.getAuthority());
	}
	ArrayList<Contact> contactList = new ArrayList<Contact>();
	if(roles.contains("ROLE_GUEST")) {
		contactList.addAll(da.getGuestContacts());
	}
	if(roles.contains("ROLE_MEMBER")) {
		contactList.addAll(da.getMemberContacts());
	}
	if(roles.contains("ROLE_ADMIN")) {
		contactList.addAll(da.getAdminContacts());
	}
	
	model.addAttribute("contacts", contactList);
	return "/user/getContact_member.html";
	}

@GetMapping("/guest")
public String viewguestContact(Authentication authentication,Model model) {

	ArrayList<String> roles= new ArrayList<String>();
	for(GrantedAuthority ga: authentication.getAuthorities()) {
		roles.add(ga.getAuthority());
	}
	ArrayList<Contact> contactList = new ArrayList<Contact>();
	if(roles.contains("ROLE_GUEST")) {
		contactList.addAll(da.getGuestContacts());
	}
	if(roles.contains("ROLE_MEMBER")) {
		contactList.addAll(da.getMemberContacts());
	}
	if(roles.contains("ROLE_ADMIN")) {
		contactList.addAll(da.getAdminContacts());
	}
	
	model.addAttribute("contacts", contactList);
	return "/user/getContact_guest.html";
	}

@GetMapping("/admin")
public String viewadminContact(Authentication authentication,Model model) {
	ArrayList<String> roles= new ArrayList<String>();
	for(GrantedAuthority ga: authentication.getAuthorities()) {
		roles.add(ga.getAuthority());
	}
	ArrayList<Contact> contactList = new ArrayList<Contact>();
	if(roles.contains("ROLE_GUEST")) {
		contactList.addAll(da.getGuestContacts());
	}
	if(roles.contains("ROLE_MEMBER")) {
		contactList.addAll(da.getMemberContacts());
	}
	if(roles.contains("ROLE_ADMIN")) {
		contactList.addAll(da.getAdminContacts());
	}
	
	model.addAttribute("contacts", contactList);
	return "/user/getContact_admin.html";
	}

@GetMapping("/view")
public String view() {
	return "index.html";
}


@GetMapping("/edit/{id}")
public String editContact(@PathVariable int id, Model model) {
Contact c = da.getContactById(id);
model.addAttribute("contact", c);
return "editContact.html";
}
@GetMapping("/editA/{id}")
public String editContactA(@PathVariable int id, Model model) {
Contact c = da.getContactById(id);
model.addAttribute("contact", c);
return "editContactAdmin.html";
}
@GetMapping("/editG/{id}")
public String editContactG(@PathVariable int id, Model model) {
Contact c = da.getContactById(id);
model.addAttribute("contact", c);
return "editContactGuest.html";
}
/*
@GetMapping("/modify")
public String editContactRecord(@RequestParam int id,
		                        @RequestParam (required=false) String name,
		                        @RequestParam (required=false) String phoneNumber,
		                        @RequestParam (required=false) String address,
		                        @RequestParam (required=false) String email,
		                        @RequestParam (required=false) String role,
		                        @RequestParam String role1) {
	Contact c = new Contact( name, phoneNumber, address, email, role);
    da.editContact(c);
    if(role.equalsIgnoreCase("member")) {
    return "redirect:/member";}
    if(role.equalsIgnoreCase("admin")) {
        return "redirect:/admin";}
    if(role.equalsIgnoreCase("guest")) {
        return "redirect:/guest";}
	return role1;
    
}
*/

@GetMapping("/modify")
public String editContactRecord(Model model, @ModelAttribute Contact contact) {
    da.editContact(contact);
   // return "redirect:/admin";
    model.addAttribute("contacts", da.getContact());
//    if(role1.equalsIgnoreCase("member")) {
//    return "redirect:/member";}
//    if(role1.equalsIgnoreCase("admin")) {
       return "redirect:/member";}
//    if(role1.equalsIgnoreCase("guest")) {
//        return "redirect:/guest";}
//	return role1;
    
@GetMapping("/modifyA")
public String editContactRecordA(Model model, @ModelAttribute Contact contact) {
    da.editContact(contact);
   // return "redirect:/admin";
    model.addAttribute("contacts", da.getContact());
//    if(role1.equalsIgnoreCase("member")) {
//    return "redirect:/member";}
//    if(role1.equalsIgnoreCase("admin")) {
       return "redirect:/admin";}

@GetMapping("/modifyG")
public String editContactRecordG(Model model, @ModelAttribute Contact contact) {
    da.editContact(contact);
   // return "redirect:/admin";
    model.addAttribute("contacts", da.getContact());
//    if(role1.equalsIgnoreCase("member")) {
//    return "redirect:/member";}
//    if(role1.equalsIgnoreCase("admin")) {
       return "redirect:/guest";}

@GetMapping("/delete/{id}")
public String deleteStudent(@PathVariable int id) {
	Contact c = da.getContactById(id);
	da.deleteContactById(id);
	return "redirect:/member";
}

@GetMapping ("/login")
public String goLogin() {
	return "login.html";
} 
@GetMapping ("/access-denied")
public String goAccessDenied() {
	return "/error/access-denied.html";
} 

@PostMapping("/register")
public String doRegistration(
@RequestParam String username,
@RequestParam String password,
@RequestParam (required=false) boolean member,
@RequestParam (required=false) boolean guest,
@RequestParam (required=false) boolean admin){
	
	da.addUser(username, password);
	long userId = da.findUserAccount(username).getUserId();

	if(member) {
		da.addRole(userId,1);	
	}
	if(guest) {	
		da.addRole(userId,2);
	}
	if(admin) {	
		da.addRole(userId,3);
	}
	return "redirect:/";
}
@GetMapping("/register")
public String goRegister() {
	return "registration.html";
}
}