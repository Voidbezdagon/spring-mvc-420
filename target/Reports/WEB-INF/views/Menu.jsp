<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta charset="utf-8">

</head>
<body>
	<!-- Navigation -->
	<nav class="navbar navbar-default " role="navigation">
	<!-- Brand and toggle get grouped for better mobile display -->
	<div class="navbar-header">
		<button type="button" class="navbar-toggle" data-toggle="collapse"
			data-target=".navbar-ex1-collapse">
			<span class="icon-bar"></span> 
			<span class="icon-bar"></span> 
			<span class="icon-bar"></span>
		</button>
		<a class="navbar-brand" href="#">Company Name</a>
	</div>
	<!-- Top Menu Items -->
	<ul class="nav navbar-right navbar-ex1-collapse top-nav" style="margin-right:0.05%;">
		<c:if test="${empty loggedUser}">
			<li><a class="text-muted" href="<%=request.getContextPath()%>/loginForm"><span
					class="glyphicon glyphicon-log-in"></span>Login</a></li>
		</c:if>
		<c:if test="${not empty loggedUser}">
			<li><a class="text-muted" href="<%=request.getContextPath()%>/logoutUser"><span
					class="glyphicon glyphicon-log-out"></span>Log Out</a></li>
		</c:if>
	</ul>
	<!-- Sidebar Menu Items - These collapse to the responsive navigation menu on small screens -->
	<div class=" navbar-default sidebar navbar-ex1-collapse " role="navigation">
		<div class="sidebar-nav navbar-collapse">
			<ul id="side-menu" class="nav in">
			
				<li><a class="text-muted" href="javascript:;" data-toggle="collapse"
					data-target="#users"><i class="fa fa-fw fa-user"></i> Users <i
						class="fa fa-fw fa-caret-down"></i></a>
					<ul id="users" class="nav nav-second-level collapse">
					<c:if test="${loggedUser.admin==false }">
						<li><a class="text-muted"  href="<%=request.getContextPath()%>/User/editUser?id=<c:out value="${loggedUser.id}"/>">Edit Profile</a></li>
					</c:if>
					<c:if test="${loggedUser.admin==true }">
						<li><a class="text-muted"  href="<%=request.getContextPath()%>/User/getAll">User
								List</a></li>
						<li><a class="text-muted"  href="<%=request.getContextPath()%>/User/create">Create
								User</a></li>
					</c:if>
					</ul></li>
				<c:if test="${loggedUser.admin==true }">
				<li><a class="text-muted" href="javascript:;" data-toggle="collapse"
					data-target="#positions"><i class="fa fa-fw fa-briefcase"></i>
						Positions <i class="fa fa-fw fa-caret-down"></i></a>
					<ul id="positions" class="nav nav-second-level collapse">
						<li><a class="text-muted"  href="<%=request.getContextPath()%>/Position/getAll">Position
								List</a></li>
						<li><a class="text-muted"  href="<%=request.getContextPath()%>/Position/create">Create
								Position</a></li>
					</ul></li>
				<li><a class="text-muted" href="javascript:;" data-toggle="collapse"
					data-target="#teams"><i class="fa fa-fw fa-users"></i> Teams <i
						class="fa fa-fw fa-caret-down"></i></a>
					<ul id="teams" class="nav nav-second-level collapse">
						<li><a class="text-muted"  href="<%=request.getContextPath()%>/Team/getAll">Team
								List</a></li>
						<li><a class="text-muted"  href="<%=request.getContextPath()%>/Team/create">Create
								Team</a></li>
					</ul></li>
				<li><a class="text-muted" href="javascript:;" data-toggle="collapse"
					data-target="#locations"><i class="fa fa-fw fa-location-arrow"></i>
						Locations <i class="fa fa-fw fa-caret-down"></i></a>
					<ul id="locations" class="nav nav-second-level collapse">
						<li><a class="text-muted"  href="<%=request.getContextPath()%>/Location/getAll">Location
								List</a></li>
						<li><a class="text-muted"  href="<%=request.getContextPath()%>/Location/create">Create
								Location</a></li>
					</ul></li>
					</c:if>
				<li><a class="text-muted" href="javascript:;" data-toggle="collapse"
					data-target="#schedule"><i class="fa fa-fw fa-calendar"></i>
						Schedule <i class="fa fa-fw fa-caret-down"></i></a>
					<ul id="schedule" class="nav nav-second-level collapse">
						<li><a class="text-muted"  href="<%=request.getContextPath()%>/Schedule/getAll">Schedule
								List</a></li>
								<c:if test="${loggedUser.admin==true }">
						<li><a class="text-muted"  href="<%=request.getContextPath()%>/Schedule/create">Create
								Schedule</a></li>
								</c:if>
					</ul></li>
			</ul>
		</div>
	</div>
	</nav>
</body>
</html>