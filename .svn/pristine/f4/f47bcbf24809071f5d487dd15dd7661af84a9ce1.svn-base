package com.jx.blackface.paycenter.annotaion.imp;

import org.apache.commons.lang.StringUtils;

import com.jx.argo.ActionResult;
import com.jx.argo.BeatContext;
import com.jx.argo.interceptor.PreInterceptor;
import com.jx.blackface.paycenter.actionresult.ActionResultUtils;
import com.jx.blackface.paycenter.utools.CommonUtils;

public class CheckLoginImpl implements PreInterceptor{
	@Override
	public ActionResult preExecute(BeatContext beat) {
		//如果存在此cookie则不跳转
		if(CommonUtils.checkCookieName("lvuser", beat.getRequest())){
			return null;
		}
		String path = beat.getRequest().getRequestURL().toString();
		String queryString = beat.getRequest().getQueryString();
		if(StringUtils.isNotBlank(queryString)){
			path += "?" + queryString;
		}
		System.out.println("***********请求的路径：" + path); 
		try {
			beat.getResponse().sendRedirect("http://www.lvzheng.com/login.html?test=1&path="+path);
			return ActionResultUtils.renderJson("");
		} catch (Exception e) {
			return ActionResultUtils.renderJson("");
		}
	}

}
