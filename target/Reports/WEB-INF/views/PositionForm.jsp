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
				<h3 class="panel-title">Position Management Form</h3>
			</div>
			<div class="panel-body">
				<form:form id="reportRegisterForm" cssClass="form-horizontal"
					enctype="multipart/form-data" modelAttribute="item" method="post"
					action="save">
					<div class="form-group">
						<div class="control-label col-xs-3">
							<form:label path="name">Name</form:label>
						</div>
						<div class="col-xs-6">
							<form:hidden path="id" value="${itemObject.id}" />
							<form:input cssClass="form-control" path="name"
								value="${itemObject.name}" />
							<form:errors path="name" cssClass="error" />
						</div>
					</div>

					<div class="form-group">
						<div class="control-label col-xs-3">
							<form:label path="parentId">Superior Position</form:label>
						</div>
						<div class="col-xs-6">
							<form:select class="form-control" path="parentId">
								<form:option value="0" label="None" />
								<form:options items="${positionList}" itemValue="id"
									itemLabel="name" />
							</form:select>
						</div>
					</div>

					<div class="form-group">
						<div class="row">
							<div class="col-xs-4"></div>
							<div class="col-xs-4">
								<input class="form-control" type="submit"
									id="<%=request.getContextPath()%>/Position/save"
									class="btn btn-default" value="Submit" />
							</div>
							<div class="col-xs-4"></div>
						</div>
					</div>
				</form:form>
			</div>
		</div>
	</div>
	</div>

	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

</body>