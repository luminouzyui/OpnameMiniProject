<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Report</title>
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
    background-color: #5491f2;
}
</style>
<script src='dwr/interface/ReportDAO.js'></script>
<script src='dwr/engine.js'></script>
<script src='dwr/util.js'></script>
<script>
	function generate(){
		var rdate = document.getElementById('rdate').value;
		//restrict to yyyy-mm-dd
		ReportDAO.getProfitReporting(setTable,rdate);
	}
	function setTable(inHTML){
		document.getElementById("tableWrap").innerHTML = inHTML;
	}
</script>

<body>
	<ul>
	  <li><a href="/OpnameMiniProject/EditProducts">Insert/Edit Stock</a></li>
	  <li><a href="/OpnameMiniProject/Audittrail">Audittrail Stock</a></li>
	  <li><a class="active" href="/OpnameMiniProject/Report">Profit/Loss Report</a></li>
	  <li><a href="/OpnameMiniProject/Transaction">Cashier</a></li>	
	</ul>
	<h3>Report</h3>
	Choose Date for generate:<input type='date' name='rdate' id='rdate'>
	<input type='button' value='Generate Report' onclick='generate()'>
	<div id='tableWrap' name='tableWrap'></div>
</body>
</html>