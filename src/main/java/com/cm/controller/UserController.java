package com.cm.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.cm.entity.User;
import com.cm.service.PositionService;
import com.cm.service.UserService;
import com.cm.util.UserFormValidator;

@Controller
public class UserController extends BaseController<User>{

	@Autowired
	private UserService userService;
	
	@Autowired
	private PositionService positionService;
	
	@Autowired
	@Qualifier("userValidator")
	private UserFormValidator validator;

	@InitBinder("item")
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(validator);
	}
	
	//START BASE CONTROLLER
	@RequestMapping(value="User/create")
	public ModelAndView createUser(HttpServletRequest request) throws Exception
	{
		request.setAttribute("positions", positionService.getAll());
		return create(request);
	}
	
	@RequestMapping(value="User/edit")
	public ModelAndView editUser(HttpServletRequest request) throws Exception
	{
		request.setAttribute("positions", positionService.getAll());
		User user = userService.getById(Long.parseLong(request.getParameter("id")));
		if (user.getAvatar() != null)
			request.setAttribute("userAvatar", getAvatar(user));
		return edit(request);
	}
	
	@RequestMapping(value="User/delete")
	public ModelAndView deleteUser(HttpServletRequest request) throws Exception
	{
		return delete(request);
	}
	
	@RequestMapping(value="User/save")
	public ModelAndView saveUser(@ModelAttribute("item") @Validated User item, BindingResult bindingResult, @RequestParam(value="file", required=false) MultipartFile file, HttpServletRequest request) throws Exception
	{	
		if (bindingResult.hasErrors()) {
			request.setAttribute("positions", positionService.getAll());
			if (item.getId() == null)
				return create(request);
			else
				return edit(request);
		}
		
		if (!file.isEmpty())
		{
			if (file.getContentType().equals("image/bmp") ||
				file.getContentType().equals("image/png") ||
				file.getContentType().equals("image/jpg") ||
				file.getContentType().equals("image/jpeg"))
			{
				setAvatar(item, file);
			}
			else
			{
				request.setAttribute("avatarError", "Avatar must be .bmp, .jpg or .png");
				if (item.getId() == null)
					return create(request);
				else
					return edit(request);	
			}	
		}
		else
		{
			if (item.getAvatar().length() < 10)
				item.setAvatar(null);
		}
			
		
		return save(item, request);
	}
	
	@RequestMapping(value = "User/getAll")
	public ModelAndView getAllUsers(HttpServletRequest request) throws Exception
	{
		return getAll(request);
	}

	@RequestMapping(value="/Menu")
	public ModelAndView menu(HttpServletRequest request)
	{
		User loggedUser = (User) request.getSession().getAttribute("LOGGED_USER");
		
		request.setAttribute("loggedUser", loggedUser);
		ModelAndView mav = new ModelAndView("Menu");
		return mav;
	}
	
	@Override
	public void feedSort(List<User> List, String column) {
		switch (column)
		{
		case "First Name": Collections.sort(List, (p1, p2) -> p1.getFirstname().compareTo(p2.getFirstname()));
			break;
		case "Last Name": Collections.sort(List, (p1, p2) -> p1.getLastname().compareTo(p2.getLastname()));
			break;
		case "Username": Collections.sort(List, (p1, p2) -> p1.getUsername().compareTo(p2.getUsername()));
			break;
		case "Password": Collections.sort(List, (p1, p2) -> p1.getPassword().compareTo(p2.getPassword()));
			break;
		case "Admin": Collections.sort(List, (p1, p2) -> p1.getAdmin().compareTo(p2.getAdmin()));
			break;
		}
	}
	
	@Override
	public void feedSortLists(LinkedHashMap<String, String> Map) {
		Map.put("firstname", "First Name");
		Map.put("lastname", "Last Name");
		Map.put("username", "Username");
		Map.put("password", "Password");
		Map.put("admin", "Admin");
	}
	
	@Override
	public void setAvatars(List<User> List) throws IOException {
		for(User i: List)
		{
			if (i.getAvatar() != null)
			{
				i.setAvatar(getAvatar(i));
			}
		}
	}
	//END BASE CONTROLLER
	
	@RequestMapping(value = {"loginForm", "/"})
	public ModelAndView loginForm(@ModelAttribute User user, HttpServletRequest request)
	{
	    User loggedUser = (User) request.getSession().getAttribute("LOGGED_USER");
	    
		ModelAndView mav = new ModelAndView("loginForm", "userObject", user);
		mav.addObject("loggedUser", loggedUser);
		return mav;
	}
	
	@RequestMapping(value="loginUser", method = RequestMethod.POST)
	public ModelAndView loginUser(@ModelAttribute User user, HttpServletResponse response, HttpServletRequest request) throws Exception
	{
		User loggingUser = userService.getUserByUnamePwd(user.getUsername(), user.getPassword());
		if (loggingUser != null)
		{
			request.getSession().setAttribute("LOGGED_USER", loggingUser);
			return new ModelAndView("redirect:User/getAll");
		}
		return new ModelAndView("loginForm");
	}
	
	@RequestMapping(value="logoutUser")
	public ModelAndView logoutUser(@ModelAttribute User user, HttpServletRequest request)
	{		
		request.getSession().setAttribute("LOGGED_USER", null);
		return new ModelAndView("redirect:loginForm");
	}
	
	@RequestMapping("createTestAdmin")
	public ModelAndView createTestAdmin()
	{
		User admin = new User();
		admin.setFirstname("admin");
		admin.setLastname("admin");
		admin.setUsername("admin");
		admin.setPassword("admin");
		admin.setAdmin(true);
		
		userService.create(admin);
		return new ModelAndView ("redirect:loginForm");
	}
	
	public String getAvatar(User user) throws IOException
	{
		Path path = Paths.get(user.getAvatar());
		byte[] encodeBase64 = Base64.encodeBase64(Files.readAllBytes(path));
		return new String(encodeBase64, "UTF-8");
	}
	
	public void setAvatar(User item, MultipartFile file) throws Exception
	{
		String uploadsDir = "/home/void/workspace/Content Management/upload/";
		if(! new File(uploadsDir).exists())
        {
            new File(uploadsDir).mkdir();
        }
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		Calendar cal = Calendar.getInstance();
		System.out.println(dateFormat.format(cal.getTime()));
		
		String orgName = file.getOriginalFilename();
        String filePath = uploadsDir + item.getUsername() + "_" +dateFormat.format(cal.getTime()) + "." + FilenameUtils.getExtension(orgName);
        File dest = new File(filePath);
        file.transferTo(dest);
        if (item.getAvatar() != null)
        {
        	File oldFile = new File(item.getAvatar());
        	oldFile.delete();
        }
        item.setAvatar(filePath);
	}
}
