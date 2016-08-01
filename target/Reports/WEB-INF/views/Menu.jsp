<%@ page language = "java" contentType = "text/html; charset=UTF-8"
	pageEncoding = "UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <title>Bootstrap Case</title>
  <meta charset="utf-8">
</head>
<body>
<nav class="navbar navbar-inverse">
  <div class="container-fluid">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="#">Navigation</a>
    </div>
    <div class="collapse navbar-collapse" id="myNavbar">
      <ul class="nav navbar-nav">
        <li class="dropdown">
          <a class="dropdown-toggle" data-toggle="dropdown" href="#">Users<span class="caret"></span></a>
          <ul class="dropdown-menu">
          	<li><a href="<%=request.getContextPath()%>/User/getAll">User List</a></li>
            <li><a href="<%=request.getContextPath()%>/User/create">Create User</a></li>
          </ul>
        </li>
        <li class="dropdown">
          <a class="dropdown-toggle" data-toggle="dropdown" href="#">Positions<span class="caret"></span></a>
          <ul class="dropdown-menu">
          	<li><a href="<%=request.getContextPath()%>/Position/getAll">Position List</a></li>
            <li><a href="<%=request.getContextPath()%>/Position/create">Create Position</a></li>
          </ul>
        </li>
        <li class="dropdown">
          <a class="dropdown-toggle" data-toggle="dropdown" href="#">Teams<span class="caret"></span></a>
          <ul class="dropdown-menu">
          	<li><a href="<%=request.getContextPath()%>/Team/getAll">Team List</a></li>
            <li><a href="<%=request.getContextPath()%>/Team/create">Create Team</a></li>
          </ul>
        </li>
        <li class="dropdown">
          <a class="dropdown-toggle" data-toggle="dropdown" href="#">Locations<span class="caret"></span></a>
          <ul class="dropdown-menu">
          	<li><a href="<%=request.getContextPath()%>/Location/getAll">Location List</a></li>
            <li><a href="<%=request.getContextPath()%>/Location/create">Create Location</a></li>
          </ul>
        </li>
        <li class="dropdown">
          <a class="dropdown-toggle" data-toggle="dropdown" href="#">Schedule<span class="caret"></span></a>
          <ul class="dropdown-menu">
          	<li><a href="<%=request.getContextPath()%>/Schedule/getAll">Schedule List</a></li>
            <li><a href="<%=request.getContextPath()%>/Schedule/create">Create Schedule</a></li>
          </ul>
        </li>
      </ul>
      <ul class="nav navbar-nav navbar-right">
      	<c:if test="${empty loggedUser}"><li><a href="loginForm"><span class="glyphicon glyphicon-log-in"></span>Login</a></li></c:if>
        <c:if test="${not empty loggedUser}"><li><a href="logoutUser"><span class="glyphicon glyphicon-log-out"></span>Log Out</a></li></c:if>
      </ul>
    </div>
  </div>
</nav>
</body>
</html>