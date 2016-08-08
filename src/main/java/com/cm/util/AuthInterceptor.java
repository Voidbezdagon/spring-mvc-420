package com.cm.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.cm.entity.User;

public class AuthInterceptor implements HandlerInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		/*if (!request.getRequestURI().equals("/content/loginUser")
				&& !request.getRequestURI().equals("/content/loginForm")
				&& !request.getRequestURI().equals("/content/logoutUser")
				&& !request.getRequestURI().equals("/content/createTestAdmin")
				&& !request.getRequestURI().equals("/content/Menu")) {
			User loggedUser = (User) request.getSession().getAttribute("LOGGED_USER");
			if (loggedUser == null) {
				response.sendRedirect("/content/loginForm");
				return false;
			} else {
				if (!request.getRequestURI().equals("/content/Schedule/getAll")
						&& !request.getRequestURI().equals("/content/ScheduleReport/getAll")
						&& !request.getRequestURI().equals("/content/ScheduleReport/create")
						&& !request.getRequestURI().equals("/content/WEB-INF/views/ScheduleList.jsp")) {
					if(loggedUser.getAdmin() == false){
						response.sendRedirect("/content/Schedule/getAll");
						return false;
					}
				}
			}
		}*/

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
