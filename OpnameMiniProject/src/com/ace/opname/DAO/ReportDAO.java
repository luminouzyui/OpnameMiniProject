package com.ace.opname.DAO;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.ace.opname.Connect;
import com.ace.opname.beans.ReportBean;

public class ReportDAO {
	Connect con;
	public String getProfitReporting(String date){
		date = date.trim();
		Map<Integer, ReportBean> mapReport = new TreeMap<Integer, ReportBean>();
		con = Connect.getInstance();
		try {
			con.openConnection();
			ReportBean rb = new ReportBean();
			//+1 karena perbandingannya dengan hari selanjutnya jam 00
			//-30 karena dminta generate report dari tanggal yang diinput - 30 hari sebelumnya
			ResultSet rs = con.executeQuery("select s.productid, sum(s.buyprice * s.stock) as totalstock    from msstock s    where datepurchased between to_date('"+date+"','yyyy-MM-dd')-30 and to_Date('"+date+"','yyyy-MM-dd')+1    group by s.productid");
			while(rs.next()){
				rb = new ReportBean();
				rb.setProductid(rs.getInt(1));
				rb.setTotalStock(rs.getInt(2));
				mapReport.put(rb.getProductid(), rb);
			}
			rs.close();
			rs = con.executeQuery("select dt.productid, sum(dt.sellprice * dt.quantity) as totalsold    from transactionheader ht    join transactiondetail dt on ht.transactionid = dt.transactionid    where transactiondate between to_date('"+date+"','yyyy-MM-dd')-30 and to_Date('"+date+"','yyyy-MM-dd')+1   group by dt.productid");
			while(rs.next()){
				int pid = rs.getInt(1);
				if(mapReport.containsKey(pid)){
					rb = mapReport.get(pid);
					rb.setTotalSold(rs.getInt(2));
				} else {
					rb = new ReportBean();
					rb.setProductid(pid);
					rb.setTotalSold(rs.getInt(2));
					mapReport.put(pid, rb);
				}
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			con.closeConnection();
		}				
		
		String jspString = "<table border=1><tr><td>Product Id</td><td>Total Sold</td><td>Total Stock</td><td>Total Profit/Loss</td></tr>";
		for (Map.Entry<Integer, ReportBean> entry : mapReport.entrySet()) {
			ReportBean reportBean = entry.getValue();
			jspString += "<tr>";
			jspString += "<td>"+reportBean.getProductid()+"</td>";
			jspString += "<td>"+reportBean.getTotalSold()+"</td>";
			jspString += "<td>"+reportBean.getTotalStock()+"</td>";
			jspString += "<td>"+(reportBean.getTotalSold() - reportBean.getTotalStock())+"</td>";
			jspString += "</tr>";
		}
		jspString += "</table>";
		
		
		return jspString;
	}
}
