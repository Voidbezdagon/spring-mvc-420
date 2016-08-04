<%@ page language = "java" contentType = "text/html; charset=UTF-8"
	pageEncoding = "UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Schedule List</title>
	<!-- Bootstrap CSS -->
	  <meta name="viewport" content="width=device-width, initial-scale=1">
	  <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
	  <link rel="stylesheet" href="http://cdnjs.cloudflare.com/ajax/libs/fullcalendar/2.9.1/fullcalendar.min.css">
	  <link rel="stylesheet" href="http://labs.voronianski.com/jquery.avgrund.js/avgrund.css">
		<%-- <link rel="stylesheet" href="http://cdnjs.cloudflare.com/ajax/libs/fullcalendar/2.9.1/fullcalendar.print.css">  --%>
	  <script src="https://code.jquery.com/jquery-2.2.4.min.js" integrity="sha256-BbhdlvQf/xTY9gja0Dq3HiwQF8LaCRTXxZKRutelT44=" crossorigin="anonymous"></script>
	  <script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.14.1/moment.min.js"></script>
	  <script src="http://labs.voronianski.com/media/js/jquery.avgrund.js"></script>
	  <script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.14.1/moment.min.js"></script>
	  <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
	  <script src="http://cdnjs.cloudflare.com/ajax/libs/fullcalendar/2.9.1/fullcalendar.min.js"></script>
	<style type="text/css">
		.myrow-container {
		margin: 20px;
		}
		.map {
		width: 100%;
        height: 300px;
      	}
      	.fc-content:hover
      	{
      		cursor: pointer;
      	}
	</style>
</head>
<body class=".container-fluid">
<div class="container myrow-container">
	<c:import url="/Menu"/>
	<input type="hidden" id="contextPath" value="<%=request.getContextPath()%>"/>
	<div id="calendar"></div>
			<div class="panel panel-success">
				<h3 class="panel-title">
					<div align="left"><b>Schedule List</b></div>
				</h3>
			</div>
			<div class="panel-body">
					<c:set var="now" value='<%=new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss:").format(new java.util.Date()).substring(0, 10)%>'/>
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
							<th></th>
						</tr>
						</thead>
						<tbody>
							
							<c:forEach items="${ItemList}" var="item">
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
									<th><a href="<%=request.getContextPath()%>/ScheduleReport/create?parentId=<c:out value='${item.id}'/>">Create Report</a></th>
								</tr>
								<tr>
									<td colspan="12">
										<div class="collapse" id="bot${item.title}">
											<c:forEach items="${item.activities}" var="activity">
												<div>
													<c:out value="${activity.description}"/><a href="<%=request.getContextPath()%>/ScheduleActivity/delete?id=<c:out value='${activity.id}'/>">Delete</a>
												</div>
											</c:forEach>
										</div>
										<div class="collapse" id="noob${item.title}">
											<c:forEach items="${item.reports}" var="report">
												<h3><c:out value="${report.date}"/>: <c:out value="${report.description}"/></h3>
												<ul>
													<c:forEach items="${report.activityReports}" var="aReport">
														<li><c:out value="${aReport.scheduleActivity.description}"/>  <c:out value="${aReport.isFinished}"/></li>
													</c:forEach>
												</ul>
											</c:forEach>
										</div>
										
									</td>
								</tr>
								
							</c:forEach>
							
						</tbody>
					</table>
			</div>
</div>

<script>

	var contextPath = document.getElementById("contextPath");
	
	
	$(document).ready(function()
	{	
		eventArray = [];
		<c:forEach items="${ItemList}" var="noob">
			<c:if test="${noob.recurringTime > 0}">
				for (i = new Date('<c:out value="${noob.startDate}"/>'); i < new Date(2016, 7, 21); i.setDate(i.getDate() + <c:out value="${noob.recurringTime}"/>))
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
		    	    		guz = '<a href="<%=request.getContextPath()%>/Schedule/edit?id='+ event.scheduleId +'"><button class="btn btn-default" style="margin-top:110px; margin-left: 65px;">Edit Event</button></a>' + 
			    	    	'<a href="<%=request.getContextPath()%>/ScheduleReport/create?parentId=' + event.scheduleId + '"><button class="btn btn-default" style="margin-top: 110px; margin-left: 20px;">Create Report</button></a>'
		    	    		<c:forEach items="${ItemList}" var="item">
		    	    			if (<c:out value="${item.id}"/> == event.scheduleId)
		    	    				{
			    	    				guz = guz + '<ul style="display: inline-block; float: left">';
		    	    					<c:forEach items="${item.activities}" var="activity">
		    	    						guz = guz + '<li><c:out value="${activity.description}"/></li>';
		    	    						console.log(guz);
		    	    					</c:forEach>
		    	    					guz = guz + '</ul>';
		    	    					
		    	    					guz = guz + '<ul style="display: inline-block; list-style: none;">';
		    	    					<c:forEach items="${item.activities.scheduleActivityReports}" var="report">
		    	    						if (moment(new Date('<c:out value="${report.date}"/>')).format('YYYY-MM-DD') == moment(event.start).format('YYYY-MM-DD'))
	    	    							guz = guz + '<li><c:out value="${report.isFinished}"/></li>';
	    	    							console.log(guz);
	    	    						</c:forEach>
		    	    					guz = guz + '</ul>';
		    	    					return guz
		    	    				}
		    	    		</c:forEach>
		    	    	}
		    	    	else
		    	    	{
		    	    		var guz;
		    	    		guz = '<a href="<%=request.getContextPath()%>/Schedule/edit?id='+ event.scheduleId +'"><button class="btn btn-default" style="margin-top:110px; margin-left: 65px;">Edit Event</button></a>' + 
			    	    	'<a href="<%=request.getContextPath()%>/ScheduleReport/create?parentId=' + event.scheduleId + '"><button class="btn btn-default" style="margin-top: 110px; margin-left: 20px;" disabled>Create Report</button></a>'
		    	    		<c:forEach items="${ItemList}" var="item">
		    	    			if (<c:out value="${item.id}"/> == event.scheduleId)
		    	    				{
		    	    					guz = guz + '<ul style="display: inline-block; float: left">';
		    	    					<c:forEach items="${item.activities}" var="activity">
		    	    						guz = guz + '<li><c:out value="${activity.description}"/></li>';
		    	    						console.log(guz);
		    	    					</c:forEach>
		    	    					guz = guz + '</ul>';
		    	    					
		    	    					guz = guz + '<ul style="display: inline-block; list-style: none;">';
		    	    					<c:forEach items="${item.activities.scheduleActivityReports}" var="report">
		    	    						if (moment(new Date('<c:out value="${report.date}"/>')).format('YYYY-MM-DD') == moment(event.start).format('YYYY-MM-DD'))
	    	    							guz = guz + '<li><c:out value="${report.isFinished}"/></li>';
	    	    							console.log(guz);
	    	    						</c:forEach>
		    	    					guz = guz + '</ul>';
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
	