package com.ace.opname.DAO;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.ace.opname.Connect;
import com.ace.opname.beans.CartModel;
import com.ace.opname.beans.CashierMessageBean;
import com.ace.opname.beans.StockModel;
import com.google.gson.Gson;

public class TransactionDAO {
	Connect con;
	//0. show initial cart
	public CashierMessageBean onloadCashier(String sessionId){
		CashierMessageBean cmb = new CashierMessageBean();
		con = Connect.getInstance();
		sessionId = sessionId.trim();
		String msgCart = "", msgTotal="";
		try {
			con.openConnection();
			int total = 0;
			int subtotal = 0;
			ResultSet rs = con.executeQuery("select dc.productid, mp.productname, dc.quantity, mp.sellprice from cartdetail dc join cartheader hc on hc.cartid=dc.cartid join msproduct mp on dc.productid = mp.productid where hc.sessionno='"+sessionId+"'");
			msgCart = "ProductId-ProductName-Quantity-SellPrice-SubTotal<br>";
			while(rs.next()){
				subtotal = rs.getInt(3)*rs.getInt(4);
				msgCart += rs.getString(1)+"-"+rs.getString(2)+"-"+rs.getString(3)+"-"+rs.getString(4)+"-"+subtotal;
				msgCart	+= "<input type='button' value='Remove' onclick='removeCart("+rs.getString(1)+")'><br>";
				total += subtotal;
			}
			msgTotal = "Total : "+total;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			con.closeConnection();
		}
		
		cmb.setMessageCart(msgCart);
		cmb.setMessageTotal(msgTotal);
		return cmb;
	}
	//1. insert/update to cart
	public CashierMessageBean updateCart(String sessionId, String productId, String qty){
		CashierMessageBean cmb = new CashierMessageBean();
		con = Connect.getInstance();
		sessionId = sessionId.trim();
		productId = productId.trim();
		qty = qty.trim();
		int cartid = 1;

		String msgCart = "", msgTotal="";
		try {
			con.openConnection();
			ResultSet rs = con.executeQuery("select quantity,hc.cartid from cartheader hc join cartdetail dc on hc.cartid = dc.cartid where sessionno='"+sessionId+"' and productid="+productId);
			if(rs.next()){
				//item and session exists in cart detail -> update qty
				int qtybefore = rs.getInt(1);
				cartid = rs.getInt(2);
				int qtyafter = qtybefore + Integer.parseInt(qty);
				con.executeUpdate("update cartdetail set quantity="+qtyafter+" where cartid="+cartid+" and productid="+productId);
			} else {
				//check if session exists
				ResultSet rs2 = con.executeQuery("select cartid from cartheader where sessionno='"+sessionId+"'");
				if(rs2.next()){
					//session exists -> insert to detail
					cartid = rs2.getInt(1);
					con.executeUpdate("insert into cartdetail values ("+cartid+","+productId+","+qty+")");
				} else {
					//session does not exist -> create new cart & insert to detail
					cartid = 1;
					ResultSet rs3 = con.executeQuery("select max(cartid) from cartheader");
					if(rs3.next()){
						//take max num
						cartid = rs3.getInt(1) + 1;
					}
					rs3.close();
					con.executeUpdate("insert into cartheader values("+cartid+",'"+sessionId+"')");
					con.executeUpdate("insert into cartdetail values ("+cartid+","+productId+","+qty+")");
				}
				rs2.close();
			}
			rs.close();
			
			//create msgcart & msgtotal
			int total = 0;
			int subtotal = 0;
			rs = con.executeQuery("select dc.productid, mp.productname, dc.quantity, mp.sellprice from cartdetail dc join msproduct mp on dc.productid = mp.productid where dc.cartid="+cartid);
			msgCart = "ProductId-ProductName-Quantity-SellPrice-SubTotal<br>";
			while(rs.next()){
				subtotal = rs.getInt(3)*rs.getInt(4);
				msgCart += rs.getString(1)+"-"+rs.getString(2)+"-"+rs.getString(3)+"-"+rs.getString(4)+"-"+subtotal+"<br>";
				msgCart	+= "<input type='button' value='Remove' onclick='removeCart("+rs.getString(1)+")'><br>";
				total += subtotal;
			}
			msgTotal = "Total : "+total;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			con.closeConnection();
		}
		
		cmb.setMessageCart(msgCart);
		cmb.setMessageTotal(msgTotal);
		return cmb;
	}
	
	//2. remove from cart
	public CashierMessageBean removeFromCart(String sessionId, String productId){
		CashierMessageBean cmb = new CashierMessageBean();
		con = Connect.getInstance();
		sessionId = sessionId.trim();
		productId = productId.trim();
		String msgCart = "", msgTotal="";
		int cartid = 1;
		try {
			con.openConnection();
			ResultSet rs = con.executeQuery("select hc.cartid from cartheader hc join cartdetail dc on hc.cartid = dc.cartid where sessionno='"+sessionId+"' and productid="+productId);
			if(rs.next()){
				cartid = rs.getInt(1);
				con.executeUpdate("delete from cartdetail where productid="+productId+" and cartid="+cartid);
			}
			rs.close();
			
			//create msgcart & msgtotal
			int total = 0;
			int subtotal = 0;
			rs = con.executeQuery("select dc.productid, mp.productname, dc.quantity, mp.sellprice from cartdetail dc join msproduct mp on dc.productid = mp.productid where dc.cartid="+cartid);
			msgCart = "ProductId-ProductName-Quantity-SellPrice-SubTotal<br>";
			while(rs.next()){
				subtotal = rs.getInt(3)*rs.getInt(4);
				msgCart += rs.getString(1)+"-"+rs.getString(2)+"-"+rs.getString(3)+"-"+rs.getString(4)+"-"+subtotal;
				msgCart	+= "<input type='button' value='Remove' onclick='removeCart("+rs.getString(1)+")'><br>";
				total += subtotal;
			}
			msgTotal = "Total : "+total;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			con.closeConnection();
		}
		
		cmb.setMessageCart(msgCart);
		cmb.setMessageTotal(msgTotal);
		return cmb;		
	}
	//3. insert to transaction+update msstock+remove all session cart
	public CashierMessageBean insertToTrans(String sessionId){
		CashierMessageBean cmb = new CashierMessageBean();
		sessionId = sessionId.trim();
		con = Connect.getInstance();
		try {
			con.openConnection();
			//get all cart item from this session
			List<CartModel> cartItems = new ArrayList<CartModel>();
			CartModel cm = null;
			int transid = 1;
			ResultSet rs = con.executeQuery("select cd.cartid, cd.productid, quantity, sellprice from cartdetail cd join msproduct mp on cd.productid = mp.productid join cartheader ch on cd.cartid=ch.cartid  where sessionno='"+sessionId+"'");
			while(rs.next()){
				cm = new CartModel();
				cm.setCartid(rs.getInt(1));
				cm.setProductid(rs.getInt(2));
				cm.setQuantity(rs.getInt(3));
				cm.setSellprice(rs.getInt(4));
				cartItems.add(cm);
			}
			rs.close();
			//insert into transaction header + detail + update msstock
				//get new transactionid
			rs = con.executeQuery("select max(transactionid) from transactionheader");
			if(rs.next()){
				transid = rs.getInt(1) + 1;
			}
			rs.close();
				//insert into transaction header
			con.executeUpdate("insert into transactionheader values ("+transid+",sysdate)");
				//insert all detail into transaction detail & update stock
			for (CartModel cartModel : cartItems) {
				con.executeUpdate("insert into transactiondetail values ("+transid+","+cartModel.getProductid()+","+cartModel.getQuantity()+","+cartModel.getSellprice()+")");
				//look for each product stock, ordered by purchase date
				int purchase = cartModel.getQuantity();
				List<StockModel> stockList = new ArrayList<StockModel>();
				StockModel sm = null;
				rs = con.executeQuery("select stockid, productid, stock from msstock where productid="+cartModel.getProductid()+" order by datepurchased");
				while(rs.next()){
					sm = new StockModel();
					sm.setStockid(rs.getInt(1));
					sm.setProductid(rs.getInt(2));
					sm.setQuantity(rs.getInt(3));
					stockList.add(sm);
				}
				rs.close();
				//update stock
				for (int i = 0 ; i < stockList.size() ; i++) {
					sm = stockList.get(i);
					if(purchase > sm.getQuantity()){
						//if purchased > stock batch qty, update this stock batch = 0
						con.executeUpdate("update msstock set stock=0 where stockid="+sm.getStockid());
						//then minus the quantity of purchased and find next stock
						purchase -= sm.getQuantity();
					} else {
						//else update this stock - purchased, then break
						int stockafter = sm.getQuantity() - purchase;
						con.executeUpdate("update msstock set stock="+stockafter+" where stockid="+sm.getStockid());
						break;
					}
				}
			}
			//remove all cart detail & header from this session's cartid
			con.executeUpdate("delete from cartdetail where cartid="+cartItems.get(0).getCartid());
			con.executeUpdate("delete from cartheader where cartid="+cartItems.get(0).getCartid());
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			con.closeConnection();
		}
		
		//re-init jsp fields
		String msgCart = "", msgTotal="";
		msgCart = "ProductId-ProductName-Quantity-SellPrice-SubTotal<br>";
		msgTotal = "Total : 0";
		cmb.setMessageCart(msgCart);
		cmb.setMessageTotal(msgTotal);
		return cmb;
	}
}
