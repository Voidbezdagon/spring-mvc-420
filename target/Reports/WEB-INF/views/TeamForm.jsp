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
	<title>User Management Form</title>
	<!-- Bootstrap CSS -->
	<%-- <link href="<c:url value="/resources/css/bootstrap.min.css" />" rel="stylesheet"> --%>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
	<style type="text/css">
	.myrow-container{
	margin: 20px;
	}
	</style>
</head>
<body class=".container-fluid">
	<div class="container myrow-container">
				<div class="panel panel-success">
					<div class="panel-heading">
						<h3 class="panel-title">
							Team Management Form
						</h3>
					</div>
					<div class="panel-body">
						<form:form id="reportRegisterForm" cssClass="form-horizontal" enctype="multipart/form-data" modelAttribute="item" method="post" action="save">
							<div class="form-group">
								<div class="control-label col-xs-3"> <form:label path="teamname">Team Name</form:label> </div>
								<div class="col-xs-6">
									<form:hidden path="id" value="${itemObject.id}"/>
									<form:input cssClass="form-control" path="teamname" value="${itemObject.teamname}"/>
									<form:errors path="teamname" cssClass="error"/>
								</div>
							</div> 
							
							<div class="form-group">
								<div class="control-label col-xs-3"> <form:label path="users">Members</form:label> </div>
								<div class="col-xs-6">
								<c:forEach var="noobs" items="${userList}" varStatus="status">
									<label name="users[${status.index}].id" id="users[${status.index}].id">${noobs.username}</label>	
									<input type="checkbox" name="users[${status.index}].id" id="users[${status.index}].id" value="${noobs.id}"/>			
								</c:forEach>
								
									<!-- <form:select path="users" items="${userList}" multiple="true" itemValue="id" itemLabel="username" class="form-control input-sm" /> -->
								</div>
							</div> 
							
							<div class="form-group">
								<div class="row">
									<div class="col-xs-4">
									</div>
									<div class="col-xs-4">
										<input type="submit" id="<%=request.getContextPath()%>/Team/save" class="btn btn-primary" value="Submit"/>
									</div>
									<div class="col-xs-4">
									</div>
								</div>
							</div>
						</form:form>
					</div>
				</div>	
	</div>	
	
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
	
</body>