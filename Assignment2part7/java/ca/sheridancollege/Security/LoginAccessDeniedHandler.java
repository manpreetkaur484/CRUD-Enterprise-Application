package ca.sheridancollege.Security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.security.core.Authentication;

@Component
public class LoginAccessDeniedHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request,
			HttpServletResponse response,
			AccessDeniedException accessDeniedException) 
					throws IOException, ServletException {
		
		// TODO Auto-generated method stub
		Authentication auth=SecurityContextHolder
				.getContext().getAuthentication();
		
		if(auth !=null) {
			System.out.println(auth.getName()+" was trying to access protected resource: "
					+ request.getRequestURI());  //this message will go into console
		}
		
		response.sendRedirect(request.getContextPath()+ "/access-denied"); // this line is the servlet equilant of the underneath line
		//return "redirect/:"
	}
}
