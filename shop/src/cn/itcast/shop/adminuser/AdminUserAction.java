package cn.itcast.shop.adminuser;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class AdminUserAction extends ActionSupport implements ModelDriven<AdminUser>{
	
	private AdminUser adminUser = new AdminUser();
	private AdminUserService adminUserService;
	
	public void setAdminUserService(AdminUserService adminUserService) {
		this.adminUserService = adminUserService;
	}

	public AdminUser getModel() {
		// TODO Auto-generated method stub
		return adminUser;
	}
	
	public String login(){
		AdminUser existAdminUser = adminUserService.login(adminUser);
		if(existAdminUser == null ){
			this.addActionMessage("用户名或密码错误");
			return LOGIN;
		}else {
			ServletActionContext.getRequest().getSession().setAttribute("existAdminUser", existAdminUser);
			return "loginSuccess";
		}
	}
	
}	
