package cn.itcast.shop.interceptor;

import org.apache.struts2.ServletActionContext;

import cn.itcast.shop.adminuser.AdminUser;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

public class LoginIntercepter extends MethodFilterInterceptor{

	@Override
	protected String doIntercept(ActionInvocation actionInvocation) throws Exception {
		AdminUser existAdminUser = (AdminUser) ServletActionContext.getRequest().getSession().getAttribute("existAdminUser");
		if(existAdminUser != null){
			return actionInvocation.invoke();
		}else{
			
			ActionSupport aciton = (ActionSupport) actionInvocation.getAction();
			aciton.addActionError("您还未登录，请登录");
			return aciton.LOGIN;
		}
		
	}

}
