package com.ap.consumer.security;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

@Component
public class AuthFailureHandler extends SimpleUrlAuthenticationFailureHandler {

	 private final String DEFAULT_FAILURE_URL = "http://localhost:8000/loginForm?error=true&exception=";

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		// TODO Auto-generated method stub

		String errorMsg = "";

		if (exception instanceof BadCredentialsException) {
			errorMsg = "error100";
		} else if (exception instanceof DisabledException) {
			errorMsg = "error101";
		} else {
			errorMsg = "error102";
		}

		response.sendRedirect(DEFAULT_FAILURE_URL+errorMsg); 
	}

}
