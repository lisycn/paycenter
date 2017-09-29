package com.jx.blackface.paycenter.controllers;

import java.util.Date;

import com.jx.argo.ActionResult;
import com.jx.argo.controller.AbstractController;
import com.jx.blackface.gaea.usercenter.entity.BFLoginEntity;
import com.jx.blackface.gaea.usercenter.utils.CookieUtils;
import com.jx.blackface.paycenter.annotaion.TracePoint;
import com.jx.blackface.paycenter.frame.PSF;
@TracePoint
public class PayBaseController extends AbstractController{

	public ActionResult payview(String viewpage){
		long uid = getLoginUserid();//.getLoginuserid(beat());
		BFLoginEntity us = null;
		if(uid > 0){
			try {
				us = PSF.getLoginService().getLoginEntityById(uid);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(us != null){
			beat().getModel().add("ue", us);
			int unusercounponscout = 0;
			try {
				unusercounponscout = PSF.getUserCounponsService().getUcounponsCountbycondition("userid = "+uid+" and isuse=0 and dqtime > "+new Date().getTime());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			beat().getModel().add("unusedcounpons", unusercounponscout);
		}
		return view(viewpage);
	}

	protected ActionResult payredirect(String redirectUrl) {
		// TODO Auto-generated method stub
		return super.redirect(redirectUrl);
	}
	protected long getLoginUserid(){
		return CookieUtils.getUseridFromCookie(beat().getRequest());
	}
}
