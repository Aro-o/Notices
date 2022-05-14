<%@page import="com.Notice"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	// SAVE
	if (request.getParameter("topic") != null) {	
		
		Notice notice = new Notice();
		String statusMsg = "";
		
		String topic = request.getParameter("topic");
		String areasAffected = request.getParameter("areasAffected");
		String date = request.getParameter("date");
		String details = request.getParameter("details");
		
		if (request.getParameter("hidNoticeIDSave") == "") {
			statusMsg = notice.insertNotice(topic, areasAffected, date, details);
		} else {
			statusMsg = notice.updateNotice(request.getParameter("hidNoticeIDSave"),topic, areasAffected, date, details);
		}
		
		
		session.setAttribute("statusMsg", statusMsg);
	}

	// DELETE
	if (request.getParameter("hidNoticeIDDelete") != null) {
		Notice notice = new Notice();
		String statusMsg = notice.deleteNotice(request.getParameter("hidNoticeIDDelete"));
		session.setAttribute("statusMsg", statusMsg);
	}
%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<link rel="stylesheet" href="views/bootstrap.min.css">
		<script src="components/jquery-3.2.1.min.js"></script>
        <script src="components/notices.js"></script>
		<title>Notices Management</title>
	</head>
	<body>
		<div class="container">
			<div class="row">
				<div class="col">
					<h1>Notices Management</h1>
					<form id="formNotice" name="formNotice" method="POST" action="notices.jsp">
						topic: 
						<input 
							id="topic" 
							name="topic" 
							type="text" 
							class="form-control form-control-sm"
						><br>

						Areas Affected: 
						<input 
							id="areasAffected"
							name="areasAffected" 
							type="text" 
							class="form-control form-control-sm"
						><br>

						Date: 
						<input 
							id="date" 
							name="date" 
							type="text" 
							class="form-control form-control-sm"
						><br>

						Details: 
						<input 
							id="details" 
							name="details" 
							type="text" 
							class="form-control form-control-sm"
						><br>

						<input 
							id="btnSave" 
							name="btnSave" 
							type="button" 
							value="Save" 
							class="btn btn-primary"
						>

						<input type="hidden" name="hidNoticeIDSave" id="hidNoticeIDSave" value="">
					</form>
				
					<br>
					<div id="alertSuccess" class="alert alert-success">
						<% out.print(session.getAttribute("statusMsg")); %>
					</div>
					<div id="alertError" class="alert alert-danger"></div>
					<br>
					<div id="divNoticesGrid">
					<%
						Notice notice = new Notice(); 
						out.print(notice.readNotices());
					%>
				</div>
			</div>
		</div>
	</body>
</html>