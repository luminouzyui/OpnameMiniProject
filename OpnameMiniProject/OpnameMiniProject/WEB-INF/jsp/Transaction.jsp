<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Cashier</title>
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
<script src='dwr/interface/TransactionDAO.js'></script>
<script src='dwr/engine.js'></script>
<script src='dwr/util.js'></script>
<script>
	function addCart(){		
		var id = document.getElementById("prodId").value;
		var qty = document.getElementById("qty").value;
		var sid = document.getElementById("sessionId").value;
		//validate qty as number
		TransactionDAO.updateCart(addToContainer,sid,id,qty);
	}
	function removeCart(prodid){
		var sid = document.getElementById("sessionId").value;
		TransactionDAO.removeFromCart(addToContainer,sid,prodid);
	}
	function insertToTransaction(){
		var sid = document.getElementById("sessionId").value;
		TransactionDAO.insertToTrans(addToContainer,sid);
	}
	function getCart(ses){
		TransactionDAO.onloadCashier(addToContainer,ses);
	}
	function addToContainer(inHTML){
		document.getElementById("container").innerHTML = inHTML.messageCart;
		document.getElementById("total").innerHTML = inHTML.messageTotal;
	}
</script>
<%@ page import="java.util.List" %>
<%@ page import="com.ace.opname.DataHandler" %>
<body onload="getCart('<%= session.getId() %>')">
	<ul>
	  <li><a href="/OpnameMiniProject/EditProducts">Insert/Edit Stock</a></li>
	  <li><a href="/OpnameMiniProject/Audittrail">Audittrail Stock</a></li>
	  <li><a href="/OpnameMiniProject/Report">Profit/Loss Report</a></li>
	  <li><a class="active" href="/OpnameMiniProject/Transaction">Cashier</a></li>	
	</ul>
	<h3>Cashier</h3>
	<form name="dwrform">
		<input type='hidden' value='<%= session.getId() %>' name='sessionId' id='sessionId'>
		<table border=1>
			<tr>
				<td>
					<!-- for cart list -->
					<div id="container" name="container" style="min-height:200px;min-width:200px'"></div>
				</td>
				<td rowspan=2>
					Product Id - Quantity<br>
					<select id='prodId' name='prodId'>
						<%
							DataHandler dh = new DataHandler();
							List<String> pIdList = dh.getProductIds();
							for(int i = 0 ; i < pIdList.size() ; i++) {
						%>
							<option><%= pIdList.get(i) %></option>
						<%
							}
						%>
					</select>
					<input type='text' name='qty' id='qty'>
					<br>
					<input type='button' onclick='addCart()' value='Add to Cart'>
					<input type='button' onclick='insertToTransaction()' value='Insert as Transaction'>
				</td>
			</tr>
			<tr>
				<td>
					<span id="total" name="total"></span>
				</td>				
			</tr>
		</table>
	</form>
</body>
</html>
