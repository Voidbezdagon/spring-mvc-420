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
	  <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css"/>
 
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
  <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
	<style type="text/css">
	.myrow-container{
	margin: 20px;
	}
	#map {
        width: 100%;
        height: 400px;
      }
      .controls {
        margin-top: 10px;
        border: 1px solid transparent;
        border-radius: 2px 0 0 2px;
        box-sizing: border-box;
        -moz-box-sizing: border-box;
        height: 32px;
        outline: none;
        box-shadow: 0 2px 6px rgba(0, 0, 0, 0.3);
      }

      #fullAddress {
        background-color: #fff;
        font-family: Roboto;
        font-size: 15px;
        font-weight: 300;
        margin-left: 12px;
        padding: 0 11px 0 13px;
        text-overflow: ellipsis;
        width: 300px;
      }

      #fullAddress:focus {
        border-color: #4d90fe;
      }
	</style>
</head>
<body class=".container-fluid">
<div id="page-wrapper" align="center">
	<div class="container myrow-container">
				<div class="panel panel-default">
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
										<form:input cssClass="form-control" path="city" value="${itemObject.city}" onchange="setAddress()" onkeyup="setAddress()" onpaste="setAddress()"/>
										<form:errors path="city" cssClass="error"/>
									</div>
								</div> 
							
							<div class="form-group">
								<div class="control-label col-xs-3"><form:label path="zip" >ZIP</form:label></div>
								<div class="col-xs-6">
									<form:input cssClass="form-control" path="zip" value="${itemObject.zip}" onchange="setAddress()" onkeyup="setAddress()" onpaste="setAddress()"/>
									<form:errors path="zip" cssClass="error"/>
								</div>
							</div>
							
							<div class="form-group">
								<div class="control-label col-xs-3"><form:label path="street">Street</form:label></div>
								<div class="col-xs-6">
									<form:input cssClass="form-control" path="street" value="${itemObject.street}" onchange="setAddress()" onkeyup="setAddress()" onpaste="setAddress()"/>
									<form:errors path="street" cssClass="error"/>
								</div>
							</div>
							
							<div class="form-group">
								<div class="control-label col-xs-3"><form:label path="streetNumber" >Street Number</form:label></div>
								<div class="col-xs-6">
									<form:input cssClass="form-control" path="streetNumber" value="${itemObject.streetNumber}" onchange="setAddress()" onkeyup="setAddress()" onpaste="setAddress()"/>
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
									<input class="controls" id="fullAddress"/>
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
										
										<input type="submit" id="<%=request.getContextPath()%>/Location/save" class="btn btn-default form-control" value="Submit"/>
									</div>
									<div class="col-xs-4">
									</div>
								</div>
							</div>
										
							
						</form:form>
					</div>
				</div>	
	</div>	
	
	</div>
	<script>
	var searchBox;
	var places;
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
		
		var input = document.getElementById('fullAddress');
		searchBox = new google.maps.places.SearchBox(input);
		map.controls[google.maps.ControlPosition.TOP_LEFT].push(input);
		
		google.maps.event.addListener(searchBox, 'places_changed', function () {
			   places = searchBox.getPlaces();
			   if (places[0].geometry.viewport) {
			      map.fitBounds(places[0].geometry.viewport);
			   } else {
			      map.setCenter(places[0].geometry.location);
			      map.setZoom(8); 
			   }
			   
			   marker.setPosition(places[0].geometry.location);
			   document.getElementById('lat').value = places[0].geometry.location.lat().toFixed(3);
			  
			   document.getElementById('lng').value = places[0].geometry.location.lng().toFixed(3);
			   
		});
	}
	
	function setAddress()
	{
		var city = document.getElementById('city').value;
		var zip = document.getElementById('zip').value;
		var street = document.getElementById('street').value;
		var streetNumber = document.getElementById('streetNumber').value;
		
		document.getElementById('fullAddress').value = (zip + " " + city + " " + street + " " + streetNumber);
	}
	
    </script>
    <script async defer
        src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBp6RmZ-MOlfJZUyb6mNU43EVEia16s2z0&libraries=places&callback=loadMaps">
	</script>
	
	
	
</body>