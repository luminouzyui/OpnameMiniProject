package com.ace.opname.beans;

import java.sql.Date;
import java.sql.Timestamp;

public class AudittrailStockBean {
	int stockId;
	int productid;
	int stockBefore;
	int stockAfter;
	Timestamp dateChanged;
	public int getProductid() {
		return productid;
	}
	public void setProductid(int productid) {
		this.productid = productid;
	}
	public int getStockId() {
		return stockId;
	}
	public void setStockId(int stockId) {
		this.stockId = stockId;
	}
	public int getStockBefore() {
		return stockBefore;
	}
	public void setStockBefore(int stockBefore) {
		this.stockBefore = stockBefore;
	}
	public int getStockAfter() {
		return stockAfter;
	}
	public void setStockAfter(int stockAfter) {
		this.stockAfter = stockAfter;
	}
	public Timestamp getDateChanged() {
		return dateChanged;
	}
	public void setDateChanged(Timestamp dateChanged) {
		this.dateChanged = dateChanged;
	}	
}
