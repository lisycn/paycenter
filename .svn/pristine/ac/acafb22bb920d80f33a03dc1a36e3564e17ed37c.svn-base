package com.jx.blackface.paycenter.buzs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import com.jx.blackface.gaea.sell.contract.ILvzSellProductService;
import com.jx.blackface.gaea.sell.entity.LvzSellProductEntity;
import com.jx.blackface.gaea.usercenter.contract.IAreasService;
import com.jx.blackface.gaea.usercenter.entity.BFAreasEntity;
import com.jx.blackface.paycenter.frame.PSF;
import com.jx.blackface.paycenter.vo.OrderPayvo;
import com.jx.blackface.paycenter.vo.SingleOrderPayvo;
import com.jx.blackface.servicecoreclient.contract.IOrderBFGService;
import com.jx.blackface.servicecoreclient.entity.OrderBFGEntity;

public class QueryBuz {

	public static QueryBuz qb = new QueryBuz();
	private static IOrderBFGService os = PSF.getOrderBFGService();
	private static IAreasService as = PSF.getAreaService();
	private static ILvzSellProductService ss = PSF.getSellProductService();
	
	/**
	 * 根据payid获取单品订单详情
	 * @author zx
	 * @param payid
	 * @return
	 * @throws Exception
	 */
	public List<SingleOrderPayvo> queryOrderBypayid(long payid)throws Exception{
		List<SingleOrderPayvo> list = new ArrayList<SingleOrderPayvo>();
		String condition = "payid="+payid+" ";
		List<OrderBFGEntity> olist = os.getOrderListBycondition(condition, 1, 99, "orderid");
		if(null != olist && olist.size() > 0){
			for(OrderBFGEntity ofg : olist){
				SingleOrderPayvo ov = tranceSingleOrderVo(ofg);
				list.add(ov);
			}
		}
		return list;
	}
	
	/**
	 * 根据payid获取订单列表
	 * @param payid
	 * @return
	 * @throws Exception
	 */
	public List<OrderPayvo> queryOrderlistBypayid(long payid)throws Exception{
		List<OrderPayvo> list = new ArrayList<OrderPayvo>();
		String condition = "payid="+payid+" ";
		List<OrderBFGEntity> olist = os.getOrderListBycondition(condition, 1, 99, "orderid");
		if(null != olist && olist.size() > 0){
			for(OrderBFGEntity ofg : olist){
				OrderPayvo ov = tranceOrder2vo(ofg);
				list.add(ov);
			}
		}
		return list;
	}
	public OrderPayvo tranceOrder2vo(OrderBFGEntity ofe)throws Exception{
		OrderPayvo pvo = new OrderPayvo();
		if(null != ofe){
			pvo.setOrderid(ofe.getOrderid());
			pvo.setLocalid(ofe.getLocalid());
			BFAreasEntity afe = as.getAeasEntityById(ofe.getLocalid());
			pvo.setLocalstr(afe.getName());
			int paid = Integer.parseInt(afe.getParentid());
			if(paid > 0){
				BFAreasEntity pa = as.getAeasEntityById(paid);
				pvo.setCityid(paid);
				pvo.setCitystr(pa.getName());
			}
			pvo.setPrice(ofe.getOrderprice());
			pvo.setDiscountprice(ofe.getOrderprice() - ofe.getPaycount());
			long sellerid = ofe.getSellerid();
			if(sellerid > 0){
				LvzSellProductEntity seen = ss.getSellProductEntityById(sellerid);
				if(null != seen){
					pvo.setProname(seen.getSell_product_name());
					pvo.setSellamount(seen.getSell_amount());
				}
			}
			pvo.setSellerid(sellerid);
			pvo.setUsercoupon(ofe.getUsercoupeids());
			pvo.setPackagesellid(ofe.getPackagesellid());
		}
		return pvo;
	}
	
	public SingleOrderPayvo tranceSingleOrderVo(OrderBFGEntity ofe)throws Exception{
		SingleOrderPayvo pvo = new SingleOrderPayvo();
		if(null != ofe){
			pvo.setOrderid(ofe.getOrderid());
			pvo.setLocalid(ofe.getLocalid());
			BFAreasEntity afe = as.getAeasEntityById(ofe.getLocalid());
			pvo.setLocalstr(afe.getName());
			int paid = Integer.parseInt(afe.getParentid());
			if(paid > 0){
				BFAreasEntity pa = as.getAeasEntityById(paid);
				pvo.setCityid(paid);
				pvo.setCitystr(pa.getName());
			}
			pvo.setPrice(ofe.getPaycount());
			long sellerid = ofe.getSellerid();
			if(sellerid > 0){
				LvzSellProductEntity seen = ss.getSellProductEntityById(sellerid);
				if(null != seen){
					pvo.setProname(seen.getSell_product_name());
					pvo.setOriginalPrice(seen.getSell_overprice());//获取商品成交价格
					
					//优惠价格 有可能优惠券叠加  直接做计算  不从库里取值了   
					float originalprice = seen.getSell_overprice();
					float pricenow = ofe.getPaycount();
					BigDecimal bd = BigDecimal.valueOf(originalprice - pricenow);
					float coupons =bd.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
					pvo.setCoupons(coupons);
				} 
			}
		}
		return pvo;
	}
	public static void sendPost(String url,String param){
		URL turl = null;
		BufferedReader in = null;
		PrintWriter pw = null;
		URLConnection connection = null;
		String res = "";
		try {
			turl = new URL(url);
			connection = turl.openConnection();
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			
			connection.setDoOutput(true);
			connection.setDoInput(true);
			
			pw = new PrintWriter(connection.getOutputStream());
			pw.println(param);
			pw.flush();
			
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String ret = "";//in.readLine();
			while((ret = in.readLine()) != null){
				System.out.println("ret is "+ret);
				res += ret;
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(null != in){
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(null != pw){
				pw.close();
			}
		}
		System.out.println("res is "+res);
	}
	public static void main(String[] args){
		String url = "http://pay.lvzheng.com/pospayreq";
		
		String orderid = "40685999280641";
		String sign = makesign("80808020130930190020901"+orderid);
		
		String param = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><request><sign>"+
		sign+"</sign><mernum>808080201309301</mernum>"
				+ "<ext1></ext1><ext2></ext2><termid>90020901</termid>"
				+ "<reqTime>20160715114900</reqTime><orderId>"+orderid
				+"</orderId></request>";
		
		
		System.out.println(param);
		
		QueryBuz.qb.sendPost(url, param);
		
		String signstr = makesign("808080201309301900209012016071818000197766460988362259900987*****876"+orderid);
		
		String payurl = "http://pay.lvzheng.com/pospaycheck";
		String payparm = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><request>"
				+ "<mernum>808080201309301</mernum>"
				+ "<termid>90020901</termid>"
				+ "<trandate>20160718</trandate>"
				+ "<trantime>180001</trantime>"
				+ "<referno>977664609883</referno>"
				+ "<batchno>7899876667</batchno>"
				+ "<serialno>78976543</serialno>"
				+ "<pan>62259900987*****876</pan>"
				+ "<trantype>01</trantype>"
				+ "<paytype>1</paytype>"
				+ "<orderId>"+orderid+"</orderId>"
				+ "<Sign>"+signstr+"</Sign>"
				+ "</request>";
		
		QueryBuz.qb.sendPost(payurl, payparm);
		
	}
	public static String makesign(String valustr){
		String ret = "";
		valustr += "UDswG1GaHVhxpUtg";
		System.out.println("valustr ++++++"+valustr);
		MessageDigest messageDigest = null;
		try {
			
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(valustr.getBytes("UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		byte[] byteArray = messageDigest.digest();
		StringBuffer md5StrBuff = new StringBuffer();
		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
				md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
			else
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
		}
		ret = md5StrBuff.toString();
		
		System.out.println("ret ++++++"+ret);
		return ret;
	}
}
