<%@ page language = "java" contentType = "text/html; charset=UTF-8"
	pageEncoding = "UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Schedule List</title>
	<!-- Bootstrap CSS -->
	  <meta name="viewport" content="width=device-width, initial-scale=1">
	  <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
	  <script src="https://code.jquery.com/jquery-1.10.2.min.js"></script>
	  <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
	<style type="text/css">
		.myrow-container {
		margin: 20px;
		}
		.map {
		width: 100%;
        height: 300px;
      	}
	</style>
</head>
<body class=".container-fluid" onload="collapseMaps()">
<div class="container myrow-container">
	<c:import url="/Menu"/>
			<div class="panel panel-success">
				<h3 class="panel-title">
					<div align="left"><b>Schedule List</b></div>
				</h3>
			</div>
			<div class="panel-body">
				<c:if test="${empty ItemList}">
					There are no locations.
				</c:if>
				<c:if test="${not empty ItemList}">
					<c:set var="object" value="${ItemList.pageList[0]}" />
					<form action="<%=request.getContextPath()%>/Schedule/getAll" id="scheduleFilterForm">
						<div class="row">
							<div class="col-md-2">Search:
							<select name="searchColumn${itemClass['class'].simpleName}" id="searchColumn${itemClass['class'].simpleName}">
									<c:forEach var="field" items="${columnNames}">
										<c:if test="${searchColumn == field.value}">
											<option value="${field.value}" label="${field.value}" selected="selected"/>
										</c:if>
										<c:if test="${searchColumn != field.value}">
											<option value="${field.value}" label="${field.value}"/>
										</c:if>
									</c:forEach>
									</select></div>
							<div class="col-md-2"><input type="text" name="searchName${itemClass['class'].simpleName}" id="searchName${itemClass['class'].simpleName}" value="${searchName}"></div>
							<div class="col-md-2">
							Sort By:
									<select name="sortColumn${itemClass['class'].simpleName}" id="sortColumn${itemClass['class'].simpleName}">
									<c:forEach var="field" items="${columnNames}">
										<c:if test="${sortColumn == field.value}">
											<option value="${field.value}" label="${field.value}" selected="selected"/>
										</c:if>
										<c:if test="${sortColumn != field.value}">
											<option value="${field.value}" label="${field.value}"/>
										</c:if>
									</c:forEach>
									</select>
							</div>
							<div class="col-md-2">
									<select name="sortOrder${itemClass['class'].simpleName}" id="sortOrder${itemClass['class'].simpleName}">
									<c:if test="${sortOrder == 'ascending'}">
									   <option value="ascending" label="ascending" selected="selected"/>
									</c:if>
									<c:if test="${sortOrder != 'ascending'}">
									   <option value="ascending" label="ascending"/>
									</c:if>
									   <c:if test="${sortOrder == 'descending'}">
									   <option value="descending" label="descending" selected="selected"/>
									</c:if>
									<c:if test="${sortOrder != 'descending'}">
									   <option value="descending" label="descending"/>
									</c:if>
									</select>
							</div>
							Items per page: 
							<select name="itemsPerPage${itemClass['class'].simpleName}" id="itemsPerPage${itemClass['class'].simpleName}" onchange="this.form.submit()">
								<c:forEach var="i" begin="1" end="3">
									<c:if test="${i == itemsPerPage}"><option value="${i}" label="${i}" selected="selected"/></c:if>
									<c:if test="${i != itemsPerPage}"><option value="${i}" label="${i}"/></c:if>
								</c:forEach>
							</select>
							<div class="col-md-1"><input class="btn btn-success" type='submit' value='Submit'/></div>
						<div class="row">
						<c:if test="${maxPages != 1}">
							<c:if test="${page == 0}">
							<div class="col-md-1"></div>
							<div class="col-md-1"><button class="btn btn-success" onclick="this.form.submit()" name="page${itemClass['class'].simpleName}" id="page${itemClass['class'].simpleName}" value='${page + 1}'>${page + 1}</button></div>
							</c:if>
							<c:if test="${page == (maxPages - 1)}">
							<div class="col-md-1"><button class="btn btn-success" onclick="this.form.submit()" name="page${itemClass['class'].simpleName}" id="page${itemClass['class'].simpleName}" value='${page - 1}'>${page - 1}</button></div>
							<div class="col-md-1"></div>
							</c:if>
							<c:if test="${page > 0 && page < (maxPages-1)}">
							<div class="col-md-1"><button class="btn btn-success" onclick="this.form.submit()" name="page${itemClass['class'].simpleName}" id="page${itemClass['class'].simpleName}" value='${page - 1}'>${page - 1}</button></div>
							<div class="col-md-1"><button class="btn btn-success" onclick="this.form.submit()" name="page${itemClass['class'].simpleName}" id="page${itemClass['class'].simpleName}" value='${page + 1}'>${page + 1}</button></div>
							</c:if>
						</c:if>
						</div>
						</div>
					</form>
					
					<table class="table table-hover table-bordered">
						<thead style="background-color: #bce8f1;">
						<tr>
							<th>Title</th>
							<th>Description</th>
							<th>Start Date</th>
							<th>Recurring Time</th>
							<th>Assigned Team</th>
							<th></th>
							<th></th>
							<th></th>
							<th></th>
							<th></th>
							<th></th>
						</tr>
						</thead>
						<tbody>
							<c:forEach items="${ItemList.pageList}" var="item">
								<tr>
									<th><c:out value="${item.title}"/></th>
									<th><c:out value="${item.description}"/></th>
									<th><c:out value="${item.startDate}"/></th>
									<th><c:out value="${item.recurringTime}"/></th>
									<th><c:out value="${item.assignedTeam.teamname}"/></th>
									<th><c:out value=""/></th>
									<th><button type="button" class="btn btn-info" data-toggle="collapse" data-target="#bot<c:out value='${item.title}'/>">Activities</button></th>	
									<th><button type="button" class="btn btn-info" data-toggle="collapse" data-target="#noob<c:out value='${item.title}'/>">Reports</button></th>
									<th><a href="<%=request.getContextPath()%>/Schedule/edit?id=<c:out value='${item.id}'/>">Edit</a></th>
									<th><a href="<%=request.getContextPath()%>/Schedule/delete?id=<c:out value='${item.id}'/>">Delete</a></th>
									<th><a href="<%=request.getContextPath()%>/ScheduleActivity/create?parentId=<c:out value='${item.id}'/>">Create Activity</a></th>
								</tr>
								<tr>
									<td colspan="11">
										<div class="collapse" id="bot${item.title}">
											<c:forEach items="${item.activities}" var="activity">
												<div>
													<c:out value="${activity.description}"/>
												</div>
											</c:forEach>
										</div>
										<div class="collapse" id="noob${item.title}">
											
										</div>
										
									</td>
								</tr>
								
							</c:forEach>
							
						</tbody>
					</table>
				</c:if>
			</div>
</div>
</body>
</html>
	