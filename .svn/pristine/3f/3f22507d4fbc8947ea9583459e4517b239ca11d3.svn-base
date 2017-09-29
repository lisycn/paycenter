package com.jx.blackface.paycenter.utools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.jx.argo.BeatContext;
import com.jx.blackface.gaea.usercenter.entity.BFLoginEntity;
import com.jx.blackface.orderplug.frame.RSBLL;
import com.jx.blackface.paycenter.frame.PSF;

public class CommonUtils {
	public static final String cookieName = "lvuser";
	/***
	 * 通过cookie判断用户是否还存在
	 * @param beat
	 * @return
	 */
//	public static LoginUserVO getLoginEntity(BeatContext beat){
//		LoginUserVO loginVo = null;
//		if(CommonUtils.checkCookieName(cookieName, beat.getRequest())){
//			String userid = CommonUtils.getUserIdFormCookie(cookieName, beat.getRequest());
//			if(StringUtils.isNotBlank(userid)){
				BFLoginEntity loginEntity = null;
//				try {
//					loginEntity = RSBLL.getstance().getLoginService().getLoginEntityById(32284470234625l);
//				} catch (Exception e) {
////					System.out.println("获取用户失败userid:"+userid);
//					e.printStackTrace();
//				}
//				if(null != loginEntity){
//					loginVo = EntityUtils.transferEntity(loginEntity, LoginUserVO.class);
//				}
//			}
//		}
		
//		return loginVo;
//	}

	public static long getLoginuserid(BeatContext beat){
		long uid = 0l;
		/*****调用通用调取cookie包 Start*******/
		if(CommonUtils.checkCookieName(cookieName, beat.getRequest())){
			String userid = CommonUtils.getUserIdFormCookie(cookieName, beat.getRequest());
			if(StringUtils.isNotBlank(userid)){
				BFLoginEntity loginEntity = null;
				try {
					loginEntity = PSF.getLoginService().getLoginEntityById(Long.valueOf(userid));
				} catch (Exception e) {
					System.out.println("获取用户失败userid:"+userid);
					e.printStackTrace();
				}
				if(null != loginEntity){
					uid = loginEntity.getUserid();
				}
			}
		}
		/*****End*******/
		return uid;
	}
	
	/***
	 * 从cookie中获取用户id
	 * @param request
	 */
	public static String getUserIdFormCookie(String cookieName,HttpServletRequest request){
		String userid="";
		try {
			userid = CookieUtils.getCookieValueByName(cookieName, request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("pay cookie is "+userid);
		if(userid != null && !"".equals(userid) && userid.contains(":")){
			String[] arr = userid.split(":");
			if(null != arr && arr.length > 0){
				userid = arr[0];
			}
		}
		return userid;
	}
	
	/***
	 * 检查此cookie是否存在
	 * @param cookieName
	 * @param request
	 * @return true 存在 false 不存在
	 */
	public static boolean checkCookieName(String cookieName,HttpServletRequest request){
		boolean checkFlag = false;
		try {
			checkFlag = CookieUtils.checkCookieName(cookieName, request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return checkFlag;
	}
	
	public static boolean isChinese(String text){
		if(StringUtils.isBlank(text)){
			return false;
		}
		Pattern p_str = Pattern.compile("[\\u4e00-\\u9fa5]+");
		Matcher m = p_str.matcher(text);
		if (!m.find() || !m.group(0).equals(text)) {
			return false;
		}
		return true;
	}
}
