package com.jx.blackface.paycenter.controllers;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.jbarcode.JBarcode;
import org.jbarcode.encode.Code128Encoder;
import org.jbarcode.paint.EAN8TextPainter;
import org.jbarcode.paint.WidthCodedPainter;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import com.jx.argo.ActionResult;
import com.jx.argo.BeatContext;
import com.jx.argo.annotations.Path;
import com.jx.blackface.gaea.usercenter.utils.ActionResultUtils;
import com.jx.blackface.orderplug.buzs.PayBuz;
import com.jx.blackface.paycenter.frame.PSF;
import com.jx.blackface.paycenter.utools.Constants;
import com.jx.blackface.servicecoreclient.entity.PayOrderBFGEntity;
import com.jx.blackface.servicecoreclient.entity.PosPayRecordEntity;
import com.jx.blackface.tools.blackTrack.entity.WebLogs;

public class PosPayController extends PayBaseController{

	@Path("/posjrcode/{orderid:\\S+}")
	public ActionResult makejrcode(final String orderid){
		return new ActionResult(){
			@Override
			public void render(BeatContext beatContext) {
				// TODO Auto-generated method stub
				WebLogs logs = WebLogs.getIntanse(PosPayController.class, "makejrcode");
				JBarcode jbc = new JBarcode(Code128Encoder.getInstance(),WidthCodedPainter.getInstance(),EAN8TextPainter.getInstance());
				//BarcoderMaker bcm = new BarcoderMaker(EAN14Encoder.instance);
				logs.putParam("makecodeorderid", orderid.length());
				try {
					BufferedImage imagebuffer = jbc.createBarcode(orderid);
					//BufferedImage imagebuffer = bcm.makecode(orderid);
					ImageIO.write(imagebuffer, "jpg", beat().getResponse().getOutputStream());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					logs.printInfoLog(e);
					e.printStackTrace();
				}finally{
					try {
						beat().getResponse().getOutputStream().close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					logs.printInfoLog();
				}
				
				
			}
		};
	}
	@Path("/pospayreq")
	public ActionResult pospayreq(){
		WebLogs logs = WebLogs.getIntanse(PosPayController.class, "pospayreq");
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
		sb.append("<response>");
		Element el = null;
		long payid = 0;
		
		SAXBuilder builder = new SAXBuilder();
		
		try {
			Document doc = builder.build(new BufferedReader(new InputStreamReader(   
					beat().getRequest().getInputStream(),"UTF-8")));
			el = doc.getRootElement();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			logs.printInfoLog(e1);
			e1.printStackTrace();
		}
		
		Map<String,String> elmap = new HashMap<String,String>();
		if(el != null){
			List<Element> clist = el.getChildren();
			
			if(null != clist){
				for(Element ee : clist){
					elmap.put(ee.getName(), ee.getValue());
				}
			}
			if(elmap != null && elmap.size() > 0){
				String mernum = elmap.containsKey("mernum")?elmap.get("mernum"):"";//el.getChild("").getValue();//商户号
				logs.putParam("mernum", mernum);
				String termid = elmap.containsKey("termid")?elmap.get("termid"):"";//el.getChild("").getValue();//终端号
				logs.putParam("termid", termid);
				String reqTime = elmap.containsKey("reqTime")?elmap.get("reqTime"):"";//el.getChild("").getValue();//请求时间
				logs.putParam("reqTime", reqTime);
				String orderid = elmap.containsKey("orderId")?elmap.get("orderId"):"";//el.getChild("").getValue();//订单编号
				logs.putParam("reqpayid", orderid);
				String sign = elmap.containsKey("sign")?elmap.get("sign"):"";//el.getChild("").getValue();//签名
				logs.putParam("sign", sign);
				
				if(mernum.isEmpty() || termid.isEmpty() || reqTime.isEmpty() || orderid.isEmpty() || sign.isEmpty() || !verfyPay(sign,mernum+termid+orderid)){//验证失败
					logs.putParam("signret", "fail");
				}else{//成功
					payid = Long.parseLong(orderid);
					logs.putParam("signret", "ok");
				}
			}
			logs.printInfoLog();
			
		}
		if(payid > 0){
			try {
				PayOrderBFGEntity payentity = PSF.getPayOrderbfgService().getPayOrderByid(payid);
				if(payentity != null){
					
					
					//PosPayRecordEntity ppe = new PosPayRecordEntity();
					
					
					sb.append("<responseCode>00</responseCode>");
					sb.append("<responseDes>交易成功</responseDes>");
					sb.append("<orderId>"+payid+"</orderId>");
					sb.append("<amt>"+(int)(payentity.getPaycount()*100)+"</amt>");
					sb.append("<show><![CDATA[订单编号:"+payid+"<br>支付金额:"+payentity.getPaycount()+"元]]></show>");
					sb.append("<print><![CDATA[小微律政<br>"
							+ "专业为您提供<br>"
							+ "工商、法律、商标<br>"
							+ "等一站式服务<br>"
							+ "<qr>http://static.lvzheng.com/wx/xiaoweiqr.jpg</qr>]]></print>");
					
					
				}else{
					sb.append("<responseCode>98</responseCode>");
					sb.append("<responseDes>没有查询到相关订单</responseDes>");
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				sb.append("<responseCode>97</responseCode>");
				sb.append("<responseDes>查询订单出现异常</responseDes>");
				logs.printInfoLog(e);
			}finally{
				logs.putParam("responsestr", sb.toString());
				logs.printInfoLog();
			}
		}else{
			sb.append("<responseCode>99</responseCode>");
			sb.append("<responseDes>订单为空</responseDes>");
		}
		sb.append("</response>");
		logs.putParam("responsestr", sb.toString());
		return ActionResultUtils.renderXml(sb.toString());
	}
	public boolean verfyPay(String sign,String valuestr){
		boolean f = false;
		WebLogs logs = WebLogs.getIntanse(PosPayController.class, "verfyPay");
		logs.putParam("valuestr", valuestr);
		logs.putParam("sign", sign);
		logs.printInfoLog();
		if(!sign.isEmpty() && !valuestr.isEmpty()){
			String plaintext = valuestr + Constants.pos_sign_key;
			MessageDigest digst = null;
			try {
				digst = MessageDigest.getInstance("MD5");
				digst.reset();
				digst.update(plaintext.getBytes("UTF-8"));

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(null != digst){
				byte[] byteArray = digst.digest();
				StringBuffer md5StrBuff = new StringBuffer();
				for (int i = 0; i < byteArray.length; i++) {
					if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
						md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
					else
						md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
				}

				if(md5StrBuff != null && md5StrBuff.length() > 0){
					System.out.println("md5StrBuff is "+md5StrBuff);
					if(sign.toLowerCase().equals(md5StrBuff.toString())){
						f = true;
					}
				}

			}
		}
		logs.putParam("verfyreet", f);
		logs.printInfoLog();

		return f;
	}
	@Path("/pospaycheck")
	public ActionResult checkPayresult(){
		String returnxmlstr = "";
		Element el = null;
		SAXBuilder builder = new SAXBuilder();
		try {
			Document doc = builder.build(new BufferedReader(new InputStreamReader(   
					beat().getRequest().getInputStream(),"UTF-8")));
			el = doc.getRootElement();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Map<String,String> vmap = new HashMap<String,String>();
		if(el != null){
			List<Element> clist = el.getChildren();
			if(clist != null && clist.size() > 0){
				for(Element ee : clist){
					vmap.put(ee.getName(),ee.getValue());
				}
			}
		}
		if(vmap != null && vmap.size() > 0){
			String mernum = vmap.containsKey("mernum")?vmap.get("mernum"):"";//商户号
			String termid = vmap.containsKey("termid")?vmap.get("termid"):"";//终端号
			String trandate = vmap.containsKey("trandate")?vmap.get("trandate"):"";//交易日期 YYYYMMDD
			String trantime = vmap.containsKey("trantime")?vmap.get("trantime"):"";//交易时间 HHMISS
			String referno = vmap.containsKey("referno")?vmap.get("referno"):"";//交易参考号
			String pan = vmap.containsKey("pan")?vmap.get("pan"):"";//交易卡号 前六后四，中间*屏蔽
			String amt = vmap.containsKey("amt")?vmap.get("amt"):"";//刷卡交易金额（分为单位）
			String Sign = vmap.containsKey("Sign")?vmap.get("Sign"):"";//刷卡交易金额（分为单位）
			String orderId = vmap.containsKey("orderId")?vmap.get("orderId"):"";//订单编号
			String transtype = vmap.containsKey("trantype")?vmap.get("trantype"):"";//订单编号
			String planttext = mernum + termid + trandate + trantime + referno + pan + amt;
			
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\" UTF-8\" ?>");
			sb.append("<response>");
			
			if(mernum.isEmpty() || termid.isEmpty() || trandate.isEmpty() || trantime.isEmpty() || referno.isEmpty() || pan.isEmpty() || amt.isEmpty() || Sign.isEmpty() || !verfyPay(Sign,planttext)){
				
				
				//排重如果重复请求 直接返回 重复信息--begin
				boolean f = false;//是否有重复
				String condition = "checkdate='"+trandate+"' and trantype='"+transtype+"' and referno='"+referno+"' and payid="+orderId;
				try {
					int i = PSF.getPosPayRecordBFGService().getpospayCountBycondition(condition);
					if(i > 0){
						f = true;
					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				//=======排重结束
				if(f){
					sb.append("<responseCode>00</responseCode>");
					sb.append("<responseDes>成功</responseDes>");
					sb.append("<ext></ext>");
				}else{
					long payid = Long.parseLong(orderId);
					PayOrderBFGEntity pob = null;
					try {
						pob = PSF.getPayOrderbfgService().getPayOrderByid(payid);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
					if(null != pob){
						//更新订单支付状态
						
						try {
							PayBuz.pb.updatePay(payid,3);
							PosPayRecordEntity ppe = new PosPayRecordEntity();
							
							
							if(ppe != null){
								
								ppe.setPayid(payid);
								ppe.setBatchno(vmap.get("batchno"));
								ppe.setCheckdate(vmap.get("trandate"));
								ppe.setChecktime(vmap.get("trantime"));
								ppe.setMernum(vmap.get("mernum"));
								ppe.setPan(vmap.get("pan"));
								ppe.setReferno(vmap.get("referno"));
								ppe.setSerialno(vmap.get("serialno"));
								ppe.setTermid(vmap.get("termid"));
								ppe.setTrantype(vmap.get("trantype"));
								ppe.setAmt(vmap.get("amt"));
								
								PSF.getPosPayRecordBFGService().addPosRecorde(ppe);//(ppe);
								
							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						sb.append("<responseCode>00</responseCode>");
						sb.append("<responseDes>成功</responseDes>");
						sb.append("<ext></ext>");
					}else{
						sb.append("<responseCode>99</responseCode>");
						sb.append("<responseDes>没有查到相应订单</responseDes>");
					}
					
					
				}
				sb.append("</response>");
				returnxmlstr = sb.toString();
			}
		}
		System.out.println("返回报文"+returnxmlstr);
		return ActionResultUtils.renderXml(returnxmlstr);
	}

	
	
}
