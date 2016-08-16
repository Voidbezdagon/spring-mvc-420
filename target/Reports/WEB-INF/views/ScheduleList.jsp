<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Schedule List</title>
<!-- Bootstrap CSS -->
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css" />
<link rel="stylesheet"
	href="http://cdnjs.cloudflare.com/ajax/libs/fullcalendar/2.9.1/fullcalendar.min.css">
<link rel="stylesheet"
	href="http://labs.voronianski.com/jquery.avgrund.js/avgrund.css">
<%-- <link rel="stylesheet" href="http://cdnjs.cloudflare.com/ajax/libs/fullcalendar/2.9.1/fullcalendar.print.css">  --%>
<script src="https://code.jquery.com/jquery-2.2.4.min.js"
	integrity="sha256-BbhdlvQf/xTY9gja0Dq3HiwQF8LaCRTXxZKRutelT44="
	crossorigin="anonymous"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.14.1/moment.min.js"></script>
<script src="http://labs.voronianski.com/media/js/jquery.avgrund.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.14.1/moment.min.js"></script>
<script
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<script
	src="http://cdnjs.cloudflare.com/ajax/libs/fullcalendar/2.9.1/fullcalendar.min.js"></script>

<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/startbootstrap-sb-admin-2/3.3.7/css/sb-admin-2.min.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/startbootstrap-sb-admin-2/3.3.7/css/timeline.min.css">
<link
	href="https://maxcdn.bootstrapcdn.com/font-awesome/4.6.3/css/font-awesome.min.css"
	rel="stylesheet"
	integrity="sha384-T8Gy5hrqNKT+hzMclPo118YTQO6cYprQmhrYwIiQ/3axmI1hQomh7Ud2hPOy8SP1"
	crossorigin="anonymous">



<style type="text/css">
.myrow-container {
	margin: 20px;
}

.myrow-container {
	margin: 20px;
}

.arrow {
	float: right;
	line-height: 1.42857
}

.glyphicon.arrow:before {
	content: "\e079"
}

.active>a>.glyphicon.arrow:before {
	content: "\e114"
}

.fa.arrow:before {
	content: "\f104"
}

.active>a>.fa.arrow:before {
	content: "\f107"
}

.plus-times {
	float: right
}

.fa.plus-times:before {
	content: "\f067"
}

.active>a>.fa.plus-times {
	filter: progid:DXImageTransform.Microsoft.BasicImage(rotation=1);
	-webkit-transform: rotate(45deg);
	-moz-transform: rotate(45deg);
	-ms-transform: rotate(45deg);
	-o-transform: rotate(45deg);
	transform: rotate(45deg)
}

.plus-minus {
	float: right
}

.fa.plus-minus:before {
	content: "\f067"
}

.active>a>.fa.plus-minus:before {
	content: "\f068"
}

.myrow-container {
	margin: 20px;
}

.fc-content:hover {
	cursor: pointer;
}

#heading-button:hover {
	text-decoration: none;
}

#heading-button:focus {
	text-decoration: none;
}
</style>
</head>
<body class="container-fluid">
	<div id="wrapper">
		<c:import url="/Menu" />
		<div id="page-wrapper">
			<div class="panel panel-default">
				<div class="panel-title">
					<h3 align="center" style="font-size: 1.3em;">
						<b>Schedule Calendar</b>
					</h3>
				</div>
			</div>
			<div id="calendar" style="margin-bottom: 20px;"></div>
			<c:if test="${logged_user.admin==true}">
				<div class="panel panel-default">
				
						<div class="panel-title">

							<h3 align="center" style="font-size: 1.3em;">

								<b>Schedule List</b>
							</h3>
						</div>
				</div>
				<div	 id="schedule-crud">
					<div class="panel-body">
						<c:set var="now"
							value='<%=new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss:").format(new java.util.Date()).substring(0, 10)%>' />
							<c:set var="object" value="${ItemList[0]}" />
							<form action="<%=request.getContextPath()%>/Schedule/getAll">
								<div class="row">
									<div class="col-md-2">
										Search by:
										<div class="col-md-10">
											<select class="form-control"
												name="searchColumn${itemClass['class'].simpleName}"
												id="searchColumn${itemClass['class'].simpleName}">
												<c:forEach var="field" items="${columnNames}">
													<c:if test="${searchColumn == field.value}">
														<option value="${field.value}" label="${field.value}"
															selected="selected">${field.value}</option>
													</c:if>
													<c:if test="${searchColumn != field.value}">
														<option value="${field.value}" label="${field.value}">${field.value}</option>
													</c:if>
												</c:forEach>
											</select>
										</div>
									</div>
									<div class="col-md-2">
										Type your search here:
										<div class="col-md-12">
											<input class="form-control" type="text"
												name="searchName${itemClass['class'].simpleName}"
												id="searchName${itemClass['class'].simpleName}"
												value="${searchName}">
										</div>
									</div>
									<div class="col-md-2">
										Sort By:
										<div class="col-md-10">
											<select class="form-control"
												name="sortColumn${itemClass['class'].simpleName}"
												id="sortColumn${itemClass['class'].simpleName}">
												<c:forEach var="field" items="${columnNames}">
													<c:if test="${sortColumn == field.value}">
														<option value="${field.value}" label="${field.value}"
															selected="selected">${field.value}</option>
													</c:if>
													<c:if test="${sortColumn != field.value}">
														<option value="${field.value}" label="${field.value}">${field.value}</option>
													</c:if>
												</c:forEach>
											</select>
										</div>
									</div>
									<div class="col-md-2">
										Order:
										<div class="col-md-10">
											<select class="form-control"
												name="sortOrder${itemClass['class'].simpleName}"
												id="sortOrder${itemClass['class'].simpleName}">
												<c:if test="${sortOrder == 'ascending'}">
													<option value="ascending" label="ascending"
														selected="selected">ascending</option>
												</c:if>
												<c:if test="${sortOrder != 'ascending'}">
													<option value="ascending" label="ascending">ascending</option>
												</c:if>
												<c:if test="${sortOrder == 'descending'}">
													<option value="descending" label="descending"
														selected="selected">descending</option>
												</c:if>
												<c:if test="${sortOrder != 'descending'}">
													<option value="descending" label="descending">descending</option>
												</c:if>
											</select>
										</div>
									</div>
									<div class="col-md-2">
										Items per page:
										<div class="col-md-9">
											<select class="form-control"
												name="itemsPerPage${itemClass['class'].simpleName}"
												id="itemsPerPage${itemClass['class'].simpleName}"
												onchange="this.form.submit()">
												<c:forEach var="i" begin="5" end="25" step="5">
													<c:if test="${i == itemsPerPage}">
														<option value="${i}" label="${i}" selected="selected">${i}</option>
													</c:if>
													<c:if test="${i != itemsPerPage}">
														<option value="${i}" label="${i}">${i}</option>
													</c:if>
												</c:forEach>
											</select>
										</div>

										<div class="col-md-2 col-md-offset-1">
											<input class="btn btn-default " type="submit" value='Submit' />
										</div>
									</div>
									<div class="col-md-1 col-md-offset-1" style="margin-top: 18px;">
										<c:if test="${maxPages != 1}">
											<c:if test="${page == 0}">
												<div class="col-md-1"></div>
												<div class="col-md-1">
													<button class="btn btn-default "
														onclick="this.form.submit()"
														name="page${itemClass['class'].simpleName}"
														id="page${itemClass['class'].simpleName}"
														value='${page + 1}'>${page + 1}</button>
												</div>
											</c:if>
											<c:if test="${page == (maxPages - 1)}">
												<div class="col-md-1">
													<button class="btn btn-default "
														onclick="this.form.submit()"
														name="page${itemClass['class'].simpleName}"
														id="page${itemClass['class'].simpleName}"
														value='${page - 1}'>${page - 1}</button>
												</div>
												<div class="col-md-1"></div>
											</c:if>
											<c:if test="${page > 0 && page < (maxPages-1)}">
												<div class="col-md-1">
													<button class="btn btn-default "
														onclick="this.form.submit()"
														name="page${itemClass['class'].simpleName}"
														id="page${itemClass['class'].simpleName}"
														value='${page - 1}'>${page - 1}</button>
												</div>
												<div class="col-md-1">
													<button class="btn btn-default "
														onclick="this.form.submit()"
														name="page${itemClass['class'].simpleName}"
														id="page${itemClass['class'].simpleName}"
														value='${page + 1}'>${page + 1}</button>
												</div>
											</c:if>
										</c:if>
									</div>
								</div>
							</form>
							
							<c:if test="${empty ItemList}">
								<h3 align="center">There are no Schedules.</h3>
							</c:if>
							<c:if test="${not empty ItemList}">
							<div class="row" style="margin-top: 40px;">
								<table class="table table-striped table-hover table-bordered">
									<thead>
										<tr>
											<th>Title</th>
											<th>Description</th>
											<th>Start Date</th>
											<th>End Date</th>
											<th>Recurring Time</th>
											<th>Assigned Team</th>
											<th>Location</th>
											<th></th>
											<th></th>
											<th></th>
											<th></th>
											<th></th>
											<th></th>
										</tr>
									</thead>
									<tbody>

										<c:forEach items="${ItemPageList.pageList}" var="item">
											<tr>
												<th><c:out value="${item.title}" /></th>
												<th><c:out value="${item.description}" /></th>
												<th><c:out value="${item.startDate}" /></th>
												<th><c:out value="${item.endDate}" /></th>
												<th><c:out value="${item.recurringTime}" /></th>
												<th><c:out value="${item.assignedTeam.teamname}" /></th>
												<th><c:out value="${item.location.name}" /></th>
												<th><button type="button" class="btn btn-default"
														data-toggle="collapse"
														data-target="#bot<c:out value='${item.id}'/>">Activities</button></th>
												<th><button type="button" class="btn btn-default"
														data-toggle="collapse"
														data-target="#noob<c:out value='${item.id}'/>">Reports</button></th>
												<th><a class="text-muted"
													href="<%=request.getContextPath()%>/Schedule/edit?id=<c:out value='${item.id}'/>">Edit</a></th>
												<th><a class="text-muted"
													href="<%=request.getContextPath()%>/Schedule/delete?id=<c:out value='${item.id}'/>">Delete</a></th>
												<th><a class="text-muted"
													href="<%=request.getContextPath()%>/ScheduleActivity/create?parentId=<c:out value='${item.id}'/>">Create
														Activity</a></th>
												<th><a class="text-muted"
													href="<%=request.getContextPath()%>/ScheduleReport/create?parentId=<c:out value='${item.id}'/>">Create
														Report</a></th>
											</tr>
											<tr>
												<td colspan="13">
													<div class="collapse" id="bot${item.id}">
														<div align="center">
															<table
																class="table table-striped table-hover table-bordered">
																<h4>
																	<b>Activities</b>
																</h4>
																<tbody>
																	<c:forEach items="${item.activities}" var="activity">

																		<tr>
																			<td  class="col-md-11"><c:out value="${activity.description}" /></td>
																			<td class="col-md-1"><a
																				href="<%=request.getContextPath()%>/ScheduleActivity/delete?id=<c:out value='${activity.id}'/>">Delete</a></td>
																		</tr>

																	</c:forEach>
																</tbody>
															</table>
														</div>
													</div>
													<div class="collapse" id="noob${item.id}">
														<div align="center">
															<table
																class="table table-striped table-hover table-bordered">
																<h4>
																	<b>Reports</b>
																</h4>
																<tbody>
																	<c:forEach items="${item.reports}" var="report">


																		<tr>
																			<td class="col-md-6"><c:out
																					value="${report.date}" /></td>
																			<td class="col-md-6"><c:out
																					value="${report.description}" /></td>
																		</tr>
																		<tr>
																			<td colspan="2"><c:forEach
																					items="${report.activityReports}" var="aReport">
																					<table
																						class="table table-striped table-hover table-bordered">
																						<tbody>
																							<tr>
																								<td class="col-md-11"><c:out
																										value="${aReport.scheduleActivity.description}" /></td>
																								<td class="col-md-1"
																									style="text-align: center; font-size: 2em;"><i
																									class="fa fa-check-square-o"
																									style="color: green;"></i></td>
																							</tr>
																						</tbody>
																					</table>
																				</c:forEach></td>
																		</tr>


																	</c:forEach>
																</tbody>
															</table>
														</div>
													</div>

												</td>
											</tr>

										</c:forEach>

									</tbody>
								</table>
							</div>
						</c:if>
					</div>
				</div>
			</c:if>
			<input type="hidden" id="contextPath"
				value="<%=request.getContextPath()%>" />
			
		</div>
	</div>
	<script>

	var contextPath = document.getElementById("contextPath");
	
	$(document).ready(function()
	{	
		eventArray = [];
		<c:forEach items="${ItemList}" var="noob">
			<c:if test="${noob.recurringTime > 0}">
				for (i = new Date('<c:out value="${noob.startDate}"/>'); i <= new Date('<c:out value="${noob.endDate}"/>'); i.setDate(i.getDate() + <c:out value="${noob.recurringTime}"/>))
					{
						
						var hasReport = 0;
						<c:forEach items="${noob.reports}" var="report">
							var reportDate = moment(new Date('<c:out value="${report.date}"/>')).format('YYYY-MM-DD');
							if (reportDate === moment(i).format('YYYY-MM-DD'))
							{
								hasReport = 1;
							}
						</c:forEach>
						if (hasReport === 1)
						{
							eventArray.push(
							{
								title: '<c:out value="${noob.title}"/>',
								start: new Date(i),
								scheduleId: '<c:out value="${noob.id}"/>',
								hasReport: 1,
								color: '#00b300'
							});
						}
						else
						{
							eventArray.push(
							{
								title: '<c:out value="${noob.title}"/>',
								start: new Date(i),
								scheduleId: '<c:out value="${noob.id}"/>',
								hasReport: 0

							});
						}
					}
			</c:if>
		</c:forEach>
		
		var calendar = $('#calendar').fullCalendar(
		{
			events: eventArray,
			
			eventRender: function(event, element) {
		        element.avgrund({
		    	    width: 380, // max is 640px
		    	    height: 280, // max is 350px
		    	    showClose: true, // switch to 'true' for enabling close button
		    	    showCloseText: 'Close', // type your text for close button
		    	    closeByEscape: true, // enables closing popup by 'Esc'..
		    	    closeByDocument: true, // ..and by clicking document itself
		    	    enableStackAnimation: false, // enables different type of popin's animation
		    	    openOnEvent: true, // set to 'false' to init on load
		    	    setEvent: 'click', // use your event like 'mouseover', 'touchmove', etc.
		    	    template: function (element) {
		    	    	if ((event.hasReport == 0) && (moment(event.start).format('YYYY-MM-DD') == moment(new Date()).format('YYYY-MM-DD')))
		    	    	{
		    	    		var guz;
		    	    		guz = '<a href="<%=request.getContextPath()%>/ScheduleReport/create?parentId=' + event.scheduleId + '"><button class="btn btn-default" >Create Report</button></a>'
		    	    		<c:forEach items="${ItemList}" var="item">
		    	    			if (<c:out value="${item.id}"/> == event.scheduleId)
		    	    				{
		    	    					guz = guz + '<h4>Location: <c:out value="${item.location.name}"/></h4>';
		    	    					guz = guz + '<div style="height:187px ; width:360px; border:1px solid #ccc; overflow:auto; margin-top: 10px;">';
			    	    				guz = guz + '<ul style="display: inline-block; float: left">';
		    	    					<c:forEach items="${item.activities}" var="activity">
		    	    						guz = guz + '<li><c:out value="${activity.description}"/></li>';
		    	    					</c:forEach>
		    	    					guz = guz + '</ul>';
		    	    					
		    	    					guz = guz + '<ul style="display: inline-block; list-style: none;">';
		    	    					<c:forEach items="${item.activities}" var="activity">
		    	    						<c:forEach items="${activity.scheduleActivityReports}" var="report">
			    	    						if (moment(new Date('<c:out value="${report.date}"/>')).format('YYYY-MM-DD') == moment(event.start).format('YYYY-MM-DD'))
			    	    							guz = guz + '<li><c:if test="${not empty report.isFinished}"> <i class="fa fa-check-square-o" style="color: green;"></i> </c:if></li>';
		    	    						</c:forEach>
	    	    						</c:forEach>
		    	    					guz = guz + '</ul>';
		    	    					guz = guz + '</div>';
		    	    					return guz
		    	    				}
		    	    		</c:forEach>
		    	    	}
		    	    	else
		    	    	{
		    	    		var guz;
		    	    		guz = '<a href="<%=request.getContextPath()%>/ScheduleReport/create?parentId=' + event.scheduleId + '"><button class="btn btn-default"  disabled>Create Report</button></a>'
		    	    		<c:forEach items="${ItemList}" var="item">
		    	    			if (<c:out value="${item.id}"/> == event.scheduleId)
		    	    				{
		    	    					guz = guz + '<h4>Location: <c:out value="${item.location.name}"/></h4>';
		    	    					guz = guz + '<div style="height:215px ; width:360px; border:1px solid #ccc; overflow:auto; margin-top: 10px;">';
		    	    					guz = guz + '<ul style="display: inline-block; float: left">';
		    	    					<c:forEach items="${item.activities}" var="activity">
		    	    						guz = guz + '<li><c:out value="${activity.description}"/></li>';
		    	    					</c:forEach>
		    	    					guz = guz + '</ul>';
		    	    					
		    	    					guz = guz + '<ul style="display: inline-block; list-style: none;">';
		    	    					<c:forEach items="${item.activities}" var="activity">
		    	    						<c:if test="${empty activity.scheduleActivityReports}">
		    	    							guz = guz + '<li>&nbsp;</li>';
		    	    						</c:if>
		    	    						<c:if test="${not empty activity.scheduleActivityReports}">
			    	    						<c:forEach items="${activity.scheduleActivityReports}" var="report">
			    	    						if (moment(new Date('<c:out value="${report.date}"/>')).format('YYYY-MM-DD') == moment(event.start).format('YYYY-MM-DD'))
		    	    									guz = guz + '<li><c:if test="${not empty report.isFinished}"> <i class="fa fa-check-square-o" style="color: green;"></i> </c:if></li>';
		    	    							</c:forEach>
		    	    						</c:if>
			    	    						
	    	    						</c:forEach>
		    	    					guz = guz + '</ul>';
		    	    					guz = guz + '</div>';
		    	    					return guz
		    	    				}
		    	    		</c:forEach>
		    	    	}
		    	    } 
		    	    	
		    	}); 		
		    }
			
		});
	});
</script>
</body>
</html>