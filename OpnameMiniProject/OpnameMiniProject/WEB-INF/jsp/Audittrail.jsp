<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Audittrail Stock</title>
</head>
<!-- http://www.w3schools.com/css/tryit.asp?filename=trycss_navbar_horizontal_black -->
<style>
ul {
    list-style-type: none;
    margin: 0;
    padding: 0;
    overflow: hidden;
    background-color: #555;
}

li {
    float: left;
}

li a {
    display: block;
    color: white;
    text-align: center;
    padding: 14px 16px;
    text-decoration: none;
}

li a:hover {
    background-color: #111;
}

.active {
    background-color: #555555;
}
</style>
<script src='dwr/engine.js'></script>
<script src='dwr/util.js'></script>

<%@ page import = "java.util.List" %>
<%@ page import = "com.ace.opname.beans.AudittrailStockBean" %>
<%@ page import = "com.ace.opname.DataHandler" %>
<body>
	<ul>
	  <li><a href="/OpnameMiniProject/EditProducts">Insert/Edit Stock</a></li>
	  <li><a class="active" href="/OpnameMiniProject/Audittrail">Audittrail Stock</a></li>
	  <li><a href="/OpnameMiniProject/Report">Profit/Loss Report</a></li>
	  <li><a href="/OpnameMiniProject/Transaction">Cashier</a></li>	
	</ul>
	<h3>Audit Trail Stock</h3>
	<table border=1>
			<tr>
				<td>Product Id</td>
				<td>Stock Id</td>
				<td>Stock Before</td>
				<td>Stock After</td>
				<td>Date Changed</td>
			</tr>
	<% 
		DataHandler dh = new DataHandler();
		List<AudittrailStockBean> mpList = dh.getAudittrailStockList();
		for(AudittrailStockBean mb : mpList){
	%>
			<tr>
				<td><%= mb.getProductid() %></td>
				<td><%= mb.getStockBefore() %></td>
				<td><%= mb.getStockAfter() %></td>
				<td><%= mb.getDateChanged().toString() %></td>
			</tr>
	<%  } %>
	</table>
</body>
</html>