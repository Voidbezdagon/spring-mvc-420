<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css"
	integrity="sha384-fLW2N01lMqjakBkx3l/M9EahuwpSfeNvV63J5ezn3uZzapT0u7EYsXMjQV+0En5r"
	crossorigin="anonymous">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
  <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<style type="text/css">
.myrow-container {
	margin: 20px;
}
</style>
</head>
<body class=".container-fluid">
	<div id="page-wrapper" align="center">
		<div class="container myrow-container">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title">User Management Form</h3>
				</div>
				<div class="panel-body">
					<form:form id="reportRegisterForm" cssClass="form-horizontal"
						enctype="multipart/form-data" modelAttribute="item" method="post"
						action="save">
						<div class="form-group">
							<div class="control-label col-xs-3">
								<form:label path="firstname">First Name</form:label>
							</div>
							<div class="col-xs-6">
								<form:hidden path="id" value="${itemObject.id}" />
								<form:hidden path="avatar" value="${itemObject.avatar}" />
									<form:input cssClass="form-control" path="firstname"
									value="${itemObject.firstname}" />
								<form:errors path="firstname" cssClass="error" />
							</div>
						</div>

						<div class="form-group">
							<form:label path="lastname" cssClass="control-label col-xs-3">Last Name</form:label>
							<div class="col-xs-6">
								<form:input cssClass="form-control" path="lastname"
									value="${itemObject.lastname}" />
								<form:errors path="lastname" cssClass="error" />
							</div>
						</div>
						<div class="form-group">
							<div class="control-label col-xs-3">
								<form:label path="username">User Name</form:label>
							</div>
							<div class="col-xs-6">
								<form:input cssClass="form-control" path="username"
									value="${itemObject.username}" />
								<form:errors path="username" cssClass="error" />
								<span><c:out value="${duplicateUname}"/></span>
							</div>
						</div>

						<div class="form-group">
							<div class="control-label col-xs-3">
								<form:label path="password">Password</form:label>
							</div>
							<div class="col-xs-6">
								<form:input type="password" cssClass="form-control" path="password"
									value="${itemObject.password}" />
								<form:errors path="password" cssClass="error" />
							</div>
						</div>
						
						<div class="form-group">
							<div class="control-label col-xs-3">
								<label>Confirm Password</label>
							</div>
							<div class="col-xs-6">
								<input type="password" class="form-control"
									name="password-confirm" id="password-confirm" />
								<span id="password-error"></span>
							</div>
						</div>
						

						<div class="form-group">
							<div class="control-label col-xs-3">
								<form:label path="admin">Admin</form:label>
							</div>
							<div class="col-xs-6">
								<form:select class="form-control" path="admin">
									<form:option value="true" label="true" />
									<form:option value="false" label="false" />
								</form:select>
							</div>
						</div>

						<div class="form-group">
							<div class="control-label col-xs-3">
								<label name="position.id">Position</label>
							</div>
							<div class="col-xs-6">
								<select class="form-control" name="position.id" id="position.id">
									<c:forEach var="item" items="${positions}">
										<option value="${item.id}" label="${item.name}">${item.name}</option>
									</c:forEach>
								</select>
								<form:errors path="position" cssClass="error" />
							</div>
						</div>

						<div class="form-group">
							<div class="control-label col-xs-3">
								<label name="file">Avatar</label> <img
									src="data:image/jpeg;base64, ${userAvatar}" height="50px"
									width="50px" />
							</div>
							<div class="col-xs-6">
								<input type="file" id="file" name="file" class="form-control " />
								<span cssClass="error">${avatarError}</span>
							</div>
						</div>
						<div class="form-group">
							<div class="row">
								<div class="col-xs-4"></div>
								<div class="col-xs-4">
									<input type="submit"
										id="<%=request.getContextPath()%>/User/save"
										class="btn btn-default form-control" value="Submit" />
								</div>
								<div class="col-xs-4"></div>
							</div>
						</div>
					</form:form>
				</div>
			</div>
		</div>
	</div>
	<script>
		$('#password')
				.on(
						"change paste keyup",
						function() {
							if (document.getElementById('password').value != document
									.getElementById('password-confirm').value)
								document.getElementById('password-error').innerHTML = 'Passwords do not match';
							else {
								document.getElementById('password-error').innerHTML = '';
							}
						});
		$('#password-confirm')
				.on(
						"change paste keyup",
						function() {
							if (document.getElementById('password').value != document
									.getElementById('password-confirm').value)
								document.getElementById('password-error').innerHTML = 'Passwords do not match';
							else
								document.getElementById('password-error').innerHTML = '';
						});

		function submitForm() {
			if (document.getElementById('password').value != document
					.getElementById('password-confirm').value) {
				document.getElementById('password-error').innerHTML = 'Passwords do not match';
				return false;
			} else {
				document.getElementById('password-error').innerHTML = '';
				return true;
			}
		};
	</script>
</body>