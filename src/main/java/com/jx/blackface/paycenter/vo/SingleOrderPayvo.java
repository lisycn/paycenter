package com.jx.blackface.paycenter.vo;

public class SingleOrderPayvo {

	private long orderid;
	private String proname;//商品名称
	private String citystr;
	private int cityid;
	private int localid;
	private String localstr;
	private float price;//实际付款
	private float coupons;//优惠
	private float originalPrice;//原价
	
	
	public long getOrderid() {
		return orderid;
	}
	public void setOrderid(long orderid) {
		this.orderid = orderid;
	}
	public String getProname() {
		return proname;
	}
	public void setProname(String proname) {
		this.proname = proname;
	}
	public String getCitystr() {
		return citystr;
	}
	public void setCitystr(String citystr) {
		this.citystr = citystr;
	}
	public int getCityid() {
		return cityid;
	}
	public void setCityid(int cityid) {
		this.cityid = cityid;
	}
	public int getLocalid() {
		return localid;
	}
	public void setLocalid(int localid) {
		this.localid = localid;
	}
	public String getLocalstr() {
		return localstr;
	}
	public void setLocalstr(String localstr) {
		this.localstr = localstr;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public float getCoupons() {
		return coupons;
	}
	public void setCoupons(float coupons) {
		this.coupons = coupons;
	}
	public float getOriginalPrice() {
		return originalPrice;
	}
	public void setOriginalPrice(float originalPrice) {
		this.originalPrice = originalPrice;
	}
	
	
	
	
	
	
}
