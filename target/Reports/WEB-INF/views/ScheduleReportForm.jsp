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
	<title>Schedule Report Management Form</title>
	<!-- Bootstrap CSS -->
	<%-- <link href="<c:url value="/resources/css/bootstrap.min.css" />" rel="stylesheet"> --%>
	
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.10.0/css/bootstrap-select.min.css">
	<script src="https://code.jquery.com/jquery-2.2.4.min.js"   integrity="sha256-BbhdlvQf/xTY9gja0Dq3HiwQF8LaCRTXxZKRutelT44="   crossorigin="anonymous"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.10.0/js/bootstrap-select.min.js"></script>
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
							Schedule Report Management Form
						</h3>
					</div>
					<div class="panel-body">
						<form:form id="reportRegisterForm" cssClass="form-horizontal" enctype="multipart/form-data" modelAttribute="item" method="post" action="save">
							<div class="form-group">
								<div class="control-label col-xs-3"> <form:label path="description">Description</form:label> </div>
								<div class="col-xs-6">
									<form:hidden path="id" value="${itemObject.id}"/>
									<input type="hidden" name="schedule.id" id="schedule.id" value="${parentSchedule.id}"/>
									<form:input cssClass="form-control" path="description" value="${itemObject.description}"/>
									<form:errors path="description" cssClass="error"/>
								</div>
							</div> 
							
							<div class="form-group">
								<div class="control-label col-xs-3"> <label >Activities</label> </div>
								<div class="col-xs-6">
								<form:select path="activityReports" class="selectpicker" multiple="true">
									<c:forEach var="noobs" items="${parentSchedule.activities}" varStatus="status">
										<option value="${noobs.id}">${noobs.description}</option>
									</c:forEach>
								</form:select>			
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
	
</body>