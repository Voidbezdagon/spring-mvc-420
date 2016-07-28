<%@ page language = "java" contentType = "text/html; charset=UTF-8"
	pageEncoding = "UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Task List</title>
	<!-- Bootstrap CSS -->
	<%-- <link href="<c:url value="/resources/css/bootstrap.min.css" />" rel="stylesheet"> --%>
	  <meta name="viewport" content="width=device-width, initial-scale=1">
	  <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
	  <script src="https://code.jquery.com/jquery-1.10.2.min.js"></script>
	  <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
	<style type="text/css">
		.myrow-container {
		margin: 20px;
		}
		.map {
        height: 300px;
      	}
	</style>
</head>
<body class=".container-fluid" onload="loadMaps();">
<div class="container myrow-container">
	<c:import url="/Menu"/>
			<div class="panel panel-success">
				<h3 class="panel-title">
					<div align="left"><b>Location List</b></div>
				</h3>
			</div>
			<div class="panel-body">
				<c:if test="${empty ItemList}">
					There are no locations.
				</c:if>
				<c:if test="${not empty ItemList}">
					<c:set var="object" value="${ItemList.pageList[0]}" />
					<form action="<%=request.getContextPath()%>/Location/getAll">
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
					
					<table class="table table-hover table-bordered">
						<thead style="background-color: #bce8f1;">
						<tr>
							<th>Name</th>
							<th>Region</th>
							<th>City</th>
							<th>ZIP</th>
							<th>Street</th>
							<th>Street Number</th>
							<th>Details</th>
							<th>Map Location</th>
							<th></th>
							<th></th>
						</tr>
						</thead>
						<tbody>
							<c:forEach items="${ItemList.pageList}" var="item">
								<tr>
									<th><c:out value="${item.name}"/></th>
									<th><c:out value="${item.region}"/></th>
									<th><c:out value="${item.city}"/></th>
									<th><c:out value="${item.zip}"/></th>
									<th><c:out value="${item.street}"/></th>
									<th><c:out value="${item.streetNumber}"/></th>
									<th><c:out value="${item.details}"/></th>	
									<th></th>
									<th><a href="<%=request.getContextPath()%>/Location/edit?id=<c:out value='${item.id}'/>">Edit</a></th>
									<th><a href="<%=request.getContextPath()%>/Location/delete?id=<c:out value='${item.id}'/>">Delete</a></th>
								</tr>
								<tr>
									<td class="map" id="map${item.name}" colspan="10">
									</td>
									<input type="hidden" class="mapLat" id="mapLat${item.name}" value="${item.lat}"/>
									<input type="hidden" class="mapLng" id="mapLng${item.name}" value="${item.lng}"/>	
								</tr>
								
							</c:forEach>
							
						</tbody>
					</table>
				</c:if>
				
			</div>
</div>
</form>
			<div class="container">
			<button type="button" class="btn btn-info" data-toggle="collapse" data-target="#hui">Collapsible</button>
			<div class="collapse" id="hui">Pi6ka</div>
			</div>
			<div class="container">
			  <h2>Simple Collapsible</h2>
			  <button type="button" class="btn btn-info" data-toggle="collapse" data-target="#demo">Simple collapsible</button>
			  <div id="demo" class="collapse">
			    Lorem ipsum dolor sit amet, consectetur adipisicing elit,
			    sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,
			    quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.
			  </div>
			</div>
<script>
	
function loadMaps()
{
	allMaps = document.getElementsByClassName('map');
	allLats = document.getElementsByClassName('mapLat');
	allLngs = document.getElementsByClassName('mapLng');

	for (key in allMaps)
	{
		var mapDiv = document.getElementById(allMaps[key].id);
    	var pos = new google.maps.LatLng(allLats[key].value, allLngs[key].value);
    	var map = new google.maps.Map(mapDiv, {
        	center: pos,
        	zoom: 14
    	});
		var marker = new google.maps.Marker({
	    position: pos,
	    map:map,
	    draggable: true
		});
	}
}
    </script>

<script async defer
        src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBp6RmZ-MOlfJZUyb6mNU43EVEia16s2z0&callback=loadMaps">
</script>

</body>
</html>
	