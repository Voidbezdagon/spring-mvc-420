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

<title>Schedule Management Form</title>
<%-- <link href="<c:url value="/resources/css/bootstrap.min.css" />" rel="stylesheet"> --%>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css"
	integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7"
	crossorigin="anonymous">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css"
	integrity="sha384-fLW2N01lMqjakBkx3l/M9EahuwpSfeNvV63J5ezn3uZzapT0u7EYsXMjQV+0En5r"
	crossorigin="anonymous">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.37/css/bootstrap-datetimepicker.min.css">
<script src="https://code.jquery.com/jquery-2.2.4.min.js"
	integrity="sha256-BbhdlvQf/xTY9gja0Dq3HiwQF8LaCRTXxZKRutelT44="
	crossorigin="anonymous">
       </script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"
	integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS"
	crossorigin="anonymous">
       </script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.14.1/moment.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.37/js/bootstrap-datetimepicker.min.js"></script>
</head>
<body class=".container-fluid">
	<div class="container myrow-container">
				<div class="panel panel-success">
					<div class="panel-heading">
						<h3 class="panel-title">
							Schedule Management Form
						</h3>
					</div>
					<div class="panel-body">
						<form:form id="scheduleRegisterForm" cssClass="form-horizontal" enctype="multipart/form-data" modelAttribute="item" method="post" action="save">
							<div class="form-group">
								<div class="control-label col-xs-3"> <form:label path="title" >Title</form:label> </div>
								<div class="col-xs-6">
									<form:hidden path="id" value="${itemObject.id}"/>
									<form:input cssClass="form-control" path="title" value="${itemObject.title}"/>
									<form:errors path="title" cssClass="error"/>
								</div>
							</div> 
							
							<div class="form-group">
								<form:label path="description" cssClass="control-label col-xs-3">Description</form:label>
								<div class="col-xs-6">
									<form:input cssClass="form-control" path="description" value="${itemObject.description}"/>
									<form:errors path="description" cssClass="error"/>
								</div>
							</div>
							<div class="form-group">
								<div class="control-label col-xs-3"> <form:label path="recurringTime" >Recurring Time</form:label> </div>
								<div class="col-xs-6">
									<form:input cssClass="form-control" path="recurringTime" value="${itemObject.recurringTime}"/>
									<form:errors path="recurringTime" cssClass="error"/>
								</div>
							</div> 
							
							<div class="form-group">
								<div class="control-label col-xs-3"><label name="assignedTeam.id">Assigned Team</label></div>
								<div class="col-xs-6">
									<select class="form-control" id="assignedTeam.id" name="assignedTeam.id">
									<c:forEach var="team" items="${teamList}">
									   <option value="${team.id}">${team.teamname}</option>
									</c:forEach>
									</select>
								</div>
							</div>
							
							<div class="form-group">
								<div class="control-label col-xs-3"><form:label path="startDate">Start Date: </form:label></div>
								<div class=" col-xs-6">
									<div class='input-group date' id='datetimepicker1'>
										<input type="text" class="form-control" name="startDate" id="startDate"
											value="<fmt:formatDate value='${itemObject.startDate}' pattern='yyyy-MM-dd HH:mm:ss' />" />
										<span class="input-group-addon"> <span
											class="glyphicon glyphicon-calendar"></span>
										</span>
									</div>
									</div>
								</div>
							
							<div class="form-group">
								<div class="row">
									<div class="col-xs-4">
									</div>
									<div class="col-xs-4">
										<input type="submit" id="<%=request.getContextPath()%>/Schedule/save" class="btn btn-primary" value="Submit"/>
									</div>
									<div class="col-xs-4">
									</div>
								</div>
							</div>
						
						</form:form>
					</div>
				</div>	
	</div>	
	<script type="text/javascript">
			$(function () {
				$('#datetimepicker1').datetimepicker({
					format: 'YYYY-M-D HH:mm:ss'
				});
			});
	</script>
	
</body>