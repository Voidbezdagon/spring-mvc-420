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
	#map {
        width: 100%;
        height: 400px;
      }
	</style>
</head>
<body class=".container-fluid" onload="loadMaps();">
	<div class="container myrow-container">
				<div class="panel panel-success">
					<div class="panel-heading">
						<h3 class="panel-title">
							Location Management Form 
						</h3>
					</div>
					<div class="panel-body">
						<form:form id="reportRegisterForm" cssClass="form-horizontal" enctype="multipart/form-data" modelAttribute="item" method="post" action="save">
							<div class="form-group">
								<div class="control-label col-xs-3"> <form:label path="name" >Name</form:label> </div>
								<div class="col-xs-6">
									<form:hidden path="id" value="${itemObject.id}"/>
									<form:input cssClass="form-control" path="name" value="${itemObject.name}"/>
									<form:errors path="name" cssClass="error"/>
								</div>
							</div> 
							
							<div class="form-group">
								<form:label path="region" cssClass="control-label col-xs-3">Region</form:label>
								<div class="col-xs-6">
									<form:input cssClass="form-control" path="region" value="${itemObject.lastname}"/>
									<form:errors path="region" cssClass="error"/>
								</div>
							</div>
								<div class="form-group">
									<div class="control-label col-xs-3"> <form:label path="city" >City</form:label> </div>
									<div class="col-xs-6">
										<form:input cssClass="form-control" path="city" value="${itemObject.city}"/>
										<form:errors path="city" cssClass="error"/>
									</div>
								</div> 
							
							<div class="form-group">
								<div class="control-label col-xs-3"><form:label path="zip">ZIP</form:label></div>
								<div class="col-xs-6">
									<form:input cssClass="form-control" path="zip" value="${itemObject.zip}"/>
									<form:errors path="zip" cssClass="error"/>
								</div>
							</div>
							
							<div class="form-group">
								<div class="control-label col-xs-3"><form:label path="street">Street</form:label></div>
								<div class="col-xs-6">
									<form:input cssClass="form-control" path="street" value="${itemObject.street}"/>
									<form:errors path="street" cssClass="error"/>
								</div>
							</div>
							
							<div class="form-group">
								<div class="control-label col-xs-3"><form:label path="streetNumber">Street Number</form:label></div>
								<div class="col-xs-6">
									<form:input cssClass="form-control" path="streetNumber" value="${itemObject.streetNumber}"/>
									<form:errors path="streetNumber" cssClass="error"/>
								</div>
							</div>
							
							<div class="form-group">
								<div class="control-label col-xs-3"><form:label path="details">Details</form:label></div>
								<div class="col-xs-6">
									<form:input cssClass="form-control" path="details" value="${itemObject.details}"/>
									<form:errors path="details" cssClass="error"/>
								</div>
							</div>
							
							<div class="form-group">
								<div class="control-label col-xs-3"><form:label path="lat">Map Location</form:label></div>
								<div class="col-xs-6">
									<div class="map" id="map" ></div>	
									<form:hidden path="lat" value="${itemObject.lat}"/>
									<form:hidden path="lng" value="${itemObject.lng}"/>
								</div>
							</div>
							
							
							<div class="form-group">
								<div class="row">
									<div class="col-xs-4">
									</div>
									<div class="col-xs-4">
										<input type="submit" id="<%=request.getContextPath()%>/Location/save" class="btn btn-primary" value="Submit"/>
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
	<script>
	function loadMaps()
	{
		var lt = 42.135;
		var ln = 24.745;

		if (document.getElementById('lat').value != "")
		{
			lt = document.getElementById('lat').value;
		}
		if (document.getElementById('lng').value != "")
		{
			ln = document.getElementById('lng').value;
		}
		
		var mapDiv = document.getElementById('map');
		
		var map = new google.maps.Map(mapDiv, {
            center: new google.maps.LatLng(lt, ln),
            zoom: 14
        });
		
		var marker = new google.maps.Marker({
		    position: new google.maps.LatLng(lt, ln),
		    map:map,
		    draggable: true
		});

		google.maps.event.addListener(marker, 'dragend', function(evt){
		    document.getElementById('lat').value = evt.latLng.lat().toFixed(3);
		    document.getElementById('lng').value = evt.latLng.lng().toFixed(3);
		});
	}
    </script>

	<script async defer
        src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBp6RmZ-MOlfJZUyb6mNU43EVEia16s2z0&callback=loadMaps">
	</script>
	
</body>