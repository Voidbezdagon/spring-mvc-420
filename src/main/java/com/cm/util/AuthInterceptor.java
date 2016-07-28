package com.cm.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.cm.entity.User;

public class AuthInterceptor implements HandlerInterceptor{
	@Override
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception 
	{
		if( !request.getRequestURI().equals("/content/loginUser") &&
		    !request.getRequestURI().equals("/content/logoutUser") &&
		    !request.getRequestURI().equals("/content/loginForm") &&
		    !request.getRequestURI().equals("/content/createTestAdmin"))
		  {
			  User loggedUser = (User) request.getSession().getAttribute("LOGGED_USER");
			  if(loggedUser == null)
			  {
				  response.sendRedirect("/content/loginForm");
				  return false;
			  }   
			  if(loggedUser.getAdmin() == false)
			  {
				  response.sendRedirect("/content/loginForm");
				  return false;
			  }  
		  }
		  return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
	}
}
