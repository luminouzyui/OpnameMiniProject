<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert/Edit Products</title>
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
<script src='dwr/interface/MsProductDAO.js'></script>
<script src='dwr/engine.js'></script>
<script src='dwr/util.js'></script>
<script>
	function update(id){
		var upqty = document.getElementById("upqty"+id).value;
		
		if(upqty == null || upqty == ""){
			showMessage("Please Input qty for update item");
		} else {
			//check number here
			MsProductDAO.updateProduct(showMessage,id,upqty);			
		}		
	}
	function showMessage(message){
		document.getElementById("errorText").innerHTML = message;
		init();
	}
	function insert(){
		var id = document.getElementById("id").value;
		var name = document.getElementById("name").value;
		var sellp = document.getElementById("sellp").value;
		var desc = document.getElementById("desc").value;
		var buyp = document.getElementById("buyp").value;
		var stock = document.getElementById("stock").value;
		
		//validation here
		MsProductDAO.insertProduct(showMessage,id,name,desc,sellp,stock,buyp);		
	}
	function init(){
		MsProductDAO.initProductTable(setTable,"a");
	}
	function setTable(inHTML){
		document.getElementById("tableContainer").innerHTML = inHTML;
	}
</script>
<%@ page import = "java.util.List" %>
<%@ page import = "com.ace.opname.beans.MsProductBean" %>
<%@ page import = "com.ace.opname.DataHandler" %>
<body onload='init()'>
	<ul>
	  <li><a class="active" href="/OpnameMiniProject/EditProducts">Insert/Edit Stock</a></li>
	  <li><a href="/OpnameMiniProject/Audittrail">Audittrail Stock</a></li>
	  <li><a href="/OpnameMiniProject/Report">Profit/Loss Report</a></li>
	  <li><a href="/OpnameMiniProject/Transaction">Cashier</a></li>	
	</ul>

	<h3>Insert/Edit Products</h3>
	<form name='dwrform'>
		<table>
			<tr>
				<td>Product Id</td>
				<td><input type='text' id='id' name='id'></td>
			</tr>
			<tr>
				<td>Product Name</td>
				<td><input type='text' id='name' name='name'></td>
			</tr>
			<tr>
				<td>Product Description</td>
				<td><input type='text' id='desc' name='desc'></td>
			</tr>
			<tr>
				<td>Product Sell Price</td>
				<td><input type='text' id='sellp' name='sellp'></td>
			</tr>
			<tr>
				<td>Product Buy Price</td>
				<td><input type='text' id='buyp' name='buyp'></td>
			</tr>
			<tr>
				<td>Product Stock</td>
				<td><input type='text' id='stock' name='stock'></td>
			</tr>
			<tr>
				<td colspan=2>
					<input type='button' value='Insert New/Update Stock' onclick='insert()'>
					<input type='reset'>
				</td>
			</tr>
		</table>
	</form>
	<br>
	<span name='errorText' id='errorText'></span>
	<br>
	<div id='tableContainer' name='tableContainer'>
		<!-- show all products here -->
		
	</div>
</body>
</html>