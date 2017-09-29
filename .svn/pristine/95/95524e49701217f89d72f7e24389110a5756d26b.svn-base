package com.jx.blackface.paycenter.frame;

import com.jx.argo.ArgoTool;
import com.jx.blackface.gaea.sell.contract.ILvzSellProductService;
import com.jx.blackface.gaea.usercenter.contract.IAreasService;
import com.jx.blackface.gaea.usercenter.contract.ILoginService;
import com.jx.blackface.gaea.usercenter.contract.IUserCounponsService;
import com.jx.blackface.servicecoreclient.contract.IOrderBFGService;
import com.jx.blackface.servicecoreclient.contract.IOrderFlowBFGService;
import com.jx.blackface.servicecoreclient.contract.IPayOrderBFGService;
import com.jx.blackface.servicecoreclient.contract.IPosPayRecordBFGService;
import com.jx.spat.gaea.client.GaeaInit;
import com.jx.spat.gaea.client.proxy.builder.ProxyFactory;

public class PSF {

	
	static{
		String url = ArgoTool.getConfigFolder()+ArgoTool.getNamespace()+"/gaea.config";
		GaeaInit.init(url);
	}
	public static IPayOrderBFGService getPayOrderbfgService(){
		return ProxyFactory.create(IPayOrderBFGService.class, "tcp://servicecore/PayOrderBFGService");
	}
	public static IOrderBFGService getOrderBFGService(){
		return ProxyFactory.create(IOrderBFGService.class, "tcp://servicecore/OrderBFGService");
	}
	public static IAreasService getAreaService(){
		return ProxyFactory.create(IAreasService.class, "tcp://usercenter/AreasService");
	}
	public static ILvzSellProductService getSellProductService(){
		return ProxyFactory.create(ILvzSellProductService.class,"tcp://sellcore/LvzSellProductService");
	}
	public static ILoginService getLoginService(){
		return ProxyFactory.create(ILoginService.class,"tcp://usercenter/LoginService");
	}
	public static IUserCounponsService getUserCounponsService(){
		return ProxyFactory.create(IUserCounponsService.class, "tcp://usercenter/UserCounponsService");
	}
	public static IOrderFlowBFGService getOrderFlowBFGService(){
		return ProxyFactory.create(IOrderFlowBFGService.class, "tcp://servicecore/OrderFlowBFGService");
	}
	public static IPosPayRecordBFGService getPosPayRecordBFGService(){
		return ProxyFactory.create(IPosPayRecordBFGService.class, "tcp://servicecore/PosPayRecordBFGService");
	}
}
