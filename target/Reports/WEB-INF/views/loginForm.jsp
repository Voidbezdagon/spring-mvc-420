<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Login</title>
	<!-- Bootstrap CSS -->
	<%-- <link href="<c:url value="/resources/css/bootstrap.min.css" />" rel="stylesheet"> --%>
	<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
	  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/startbootstrap-sb-admin-2/3.3.7/css/sb-admin-2.min.css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/startbootstrap-sb-admin-2/3.3.7/css/timeline.min.css">	
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
  <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/startbootstrap-sb-admin-2/3.3.7/js/sb-admin-2.min.js"></script>
	<style type="text/css">
	.myrow-container{
	margin: 20px;
	}
	</style>
</head>
<body class=".container-fluid">
	<div>
		<c:if test="${empty loggedUser}">
			<div class="panel panel-success">
				<div class="panel-heading">
					<h3 class="panel-title">
						Login Form
					</h3>
				</div>
				<div class="panel-body">
					<form:form class="form-horizontal" modelAttribute="user" method="post" action="loginUser">
		
						<div class="form-group">
							<div class="control-label col-xs-3"><form:label path="username">Username</form:label></div>
							<div class="col-xs-6">
								<form:input type="text" path="username" class="form-control" value=""/>
							</div>
						</div>
						
						<div class="form-group">
							<div class="control-label col-xs-3"><form:label path="password">Password</form:label></div>
							<div class="col-xs-6">
								<form:input type="password" path="password" class="form-control" value=""/>
							</div>
						</div>
						
						<div class="form-group">
							<div class="row">
								<div class="col-xs-4">
								</div>
								<div class="col-xs-4">
									<input type="submit" class="btn btn-primary" value="Login" onclick="return submitLoginForm();"/>
								</div>
								<div class="col-xs-4">
								</div>
							</div>
						</div>  
					</form:form>
						<div class="col-xs-6">
							<a href = "createTestAdmin"><button type="button" class="btn btn-success" value="Create Test Admin">Create Test Admin username:admin password:admin</button></a>
						</div>
				</div>
			</div>	
		</c:if>	
		<c:if test="${not empty loggedUser}">
		<c:import url="/Menu"/>
		</div>
			<div><p>You are logged in as <b>${loggedUser.username}</b>.</p></div>
			<div><a href="<c:url value="logoutUser"/>"><button type="button" class="btn btn-success" value="Logout">Logout</button></a></div>
		</div>
		</c:if>	
	


	<script type="text/javascript">
	function submitLoginForm() {
		var username = $('#username').val();
		var password = $('#password').val();

		if(username.length == 0) {
			alert('Please enter a Username');
			$('#username').focus();
			return false;
		}
		
		if(password.length == 0) {
			alert('Please enter a Password');
			$('#password').focus();
			return false;
		}
		return true;
	};
	</script>
	
</body>