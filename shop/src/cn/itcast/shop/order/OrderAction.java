package cn.itcast.shop.order;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import cn.itcast.shop.cart.Cart;
import cn.itcast.shop.cart.CartItem;
import cn.itcast.shop.user.User;
import cn.itcast.shop.utils.PageBean;
import cn.itcast.shop.utils.PaymentUtil;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;



/**
 * 订单的Action
 * @author 传智.郭嘉
 *
 */
public class OrderAction extends ActionSupport{
	private Order order;
	private String pd_FrpId;
	private String r3_Amt;
	private String r6_Order;
	
	private Integer oid;
	
	private Integer page;
	private Integer state;
	
	public void setPage(Integer page) {
		this.page = page;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public void setR3_Amt(String r3_Amt) {
		this.r3_Amt = r3_Amt;
	}

	public void setR6_Order(String r6_Order) {
		this.r6_Order = r6_Order;
	}

	public void setPd_FrpId(String pd_FrpId) {
		this.pd_FrpId = pd_FrpId;
	}

	public Order getOrder() {
		return order;
	}


	private OrderService orderService;


	public void setOrder(Order order) {
		this.order = order;
	}
	
	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}
	

	public String saveOrder(){
		order = new Order();
		order.setOrdertime(new Date());
		order.setState(1);
		HttpServletRequest request = ServletActionContext.getRequest();
		// 获得购物车:
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		if(cart == null){
			this.addActionMessage("您还没有购物!请先去购物!");
			return "msg";
		}
		order.setTotal(cart.getTotal());
		User existUser = (User) request.getSession().getAttribute("existUser");
		if(existUser == null){
			this.addActionMessage("您还没有登录!请先去登录!");
			return "msg";
		}
		order.setUser(existUser);
		for (CartItem cartItem : cart.getCartItems()) {
			OrderItem orderItem = new OrderItem();
			orderItem.setCount(cartItem.getCount());
			orderItem.setSubtotal(cartItem.getSubtotal());
			orderItem.setProduct(cartItem.getProduct());
			orderItem.setOrder(order);
			
			order.getOrderItems().add(orderItem);
		}
		
		cart.clearCart();
		
		Integer oid = orderService.save(order);
		order.setOid(oid);
		//order = orderService.findByOid(oid);
		
		return "saveOrderSuccess";
	}
	
	public String payOrder() throws IOException{
		
		Order currOrder = orderService.findByOid(order.getOid());
		currOrder.setAddr(order.getAddr());
		currOrder.setName(order.getName());
		currOrder.setPhone(order.getPhone());
		
		orderService.update(currOrder);
		
		String p0_Cmd = "Buy";
		String p1_MerId = "10001126856";
		String p2_Order = order.getOid().toString();
		String p3_Amt = "0.01";
		String p4_Cur = "CNY";
		String p5_Pid = "";
		String p6_Pcat = "";
		String p7_Pdesc = "";
		String p8_Url = "http://172.27.195.18:8080/shop/order_callBack.action";
		String p9_SAF = "";
		String pa_MP = "";
		String pr_NeedResponse = "1";
		String keyValue = "69cl522AV6q613Ii4W6u8K6XuW8vM1N6bFgyv769220IuYe9u37N4y7rI4Pl";
		String hmac = PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt, p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc, p8_Url, p9_SAF, pa_MP,pd_FrpId , pr_NeedResponse, keyValue);
		
		StringBuffer sb = new StringBuffer("https://www.yeepay.com/app-merchant-proxy/node?");
		sb.append("p0_Cmd=").append(p0_Cmd).append("&");
		sb.append("p1_MerId=").append(p1_MerId).append("&");
		sb.append("p2_Order=").append(p2_Order).append("&");
		sb.append("p3_Amt=").append(p3_Amt).append("&");
		sb.append("p4_Cur=").append(p4_Cur).append("&");
		sb.append("p5_Pid=").append(p5_Pid).append("&");
		sb.append("p6_Pcat=").append(p6_Pcat).append("&");
		sb.append("p7_Pdesc=").append(p7_Pdesc).append("&");
		sb.append("p8_Url=").append(p8_Url).append("&");
		sb.append("p9_SAF=").append(p9_SAF).append("&");
		sb.append("pa_MP=").append(pa_MP).append("&");
		sb.append("pd_FrpId=").append(pd_FrpId).append("&");
		sb.append("pr_NeedResponse=").append(pr_NeedResponse).append("&");
		sb.append("hmac=").append(hmac);
		
		HttpServletResponse httpServletResponse = ServletActionContext.getResponse();
		httpServletResponse.sendRedirect(sb.toString());
		
		return NONE;
	}
	
	public String callBack(){
		Order currOrder = orderService.findByOid(Integer.parseInt(r6_Order));
		currOrder.setState(2);
		orderService.update(currOrder);
		
		this.addActionMessage("付款成功！订单号为：" + r6_Order + "付款金额为" + r3_Amt );
		return "msg";
		
	}
	
	public String findByUid(){
		User existUser = (User) ServletActionContext.getRequest().getSession().getAttribute("existUser");
		List<Order> oList = orderService.findByUid(existUser);
		ActionContext.getContext().getValueStack().set("oList", oList);
		return "findByUidSuccess";
	}
	
	public String findByOid(){
		order = orderService.findByOid(oid);
		return "findByOidSuccess";
	}
	
	public String adminFindByState(){
		PageBean<Order> pageBean = orderService.findByPage(state,page);
		ActionContext.getContext().getValueStack().set("pageBean", pageBean);
		return "adminFindByStateSuccess";
	}
	
	public String adminFindAll(){
		PageBean<Order> pageBean = orderService.findByPage(page);
		ActionContext.getContext().getValueStack().set("pageBean", pageBean);
		return "adminFindAllSuccess";
	}
	
	public String adminUpdateState(){
		order = orderService.findByOid(oid);
		order.setState(3);
		orderService.update(order);
		
		return "adminUpdateStateSuccess";
	}
	
	public String updateState(){
		order = orderService.findByOid(oid);
		order.setState(4);
		orderService.update(order);
		
		return "updateStateSuccess";
	}
	
}
