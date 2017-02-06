package com.ace.opname;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.ace.opname.beans.AudittrailStockBean;
import com.ace.opname.beans.MsProductBean;
import com.ace.opname.beans.ReportBean;

public class DataHandler {
	Connect con;
	
	public List<String> getProductIds(){
		List<String> productIdList = new ArrayList<String>();
		
		con = con.getInstance();
		try {
			con.openConnection();
			ResultSet rs = con.executeQuery("select productid from msproduct order by productid");
			while(rs.next()) {
				productIdList.add(rs.getString(1));
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			con.closeConnection();
		}
		
		return productIdList;
	}
	
	public List<AudittrailStockBean> getAudittrailStockList(){
		List<AudittrailStockBean> asbList = new ArrayList<AudittrailStockBean>();
		con = Connect.getInstance();
		try {
			con.openConnection();
			ResultSet rs = con.executeQuery("select productid, ats.stockid,stockbefore,stockafter,datechanged from Audittrailstock ats join MsStock ms on ats.stockid = ms.stockid order by productid, ats.stockid, datechanged");
			AudittrailStockBean ab = null;
			while(rs.next()){
				ab = new AudittrailStockBean();
				ab.setProductid(rs.getInt(1));
				ab.setStockId(rs.getInt(2));
				ab.setStockBefore(rs.getInt(3));
				ab.setStockAfter(rs.getInt(4));
				ab.setDateChanged(rs.getTimestamp(5));
				asbList.add(ab);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			con.closeConnection();
		}
		return asbList;		
	}
	
	public List<MsProductBean> getProductList(){
		List<MsProductBean> mpbList = new ArrayList<MsProductBean>();
		con = Connect.getInstance();
		try {
			con.openConnection();
			ResultSet rs = con.executeQuery("select mp.productid,productname,description,sellprice,stockid,stock,buyprice,datepurchased from MsProduct mp join MsStock ms on mp.productid = ms.productid order by mp.productid, stockid");
			MsProductBean mp = null;
			while(rs.next()){
				mp = new MsProductBean();
				mp.setProductId(rs.getInt(1));
				mp.setProductName(rs.getString(2));
				mp.setDescription(rs.getString(3));
				mp.setSellPrice(rs.getInt(4));
				mp.setStockId(rs.getInt(5));
				mp.setQuantity(rs.getInt(6));
				mp.setBuyPrice(rs.getInt(7));
				mp.setBuyDate(rs.getTimestamp(8));
				mpbList.add(mp);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			con.closeConnection();
		}
		return mpbList;
	}
	
}
