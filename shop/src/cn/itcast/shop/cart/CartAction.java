package cn.itcast.shop.cart;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import cn.itcast.shop.product.Product;
import cn.itcast.shop.product.ProductService;

public class CartAction {
	private Integer pid;
	private Integer count;
	public void setPid(Integer pid) {
		this.pid = pid;
	}


	public void setCount(Integer count) {
		this.count = count;
	}


	public void setProductService(ProductService productService) {
		this.productService = productService;
	}


	private ProductService productService;
	
	public Cart getCart(HttpServletRequest request){
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		if (cart == null) {
			// 创建购物车对象
			cart = new Cart();
			// 将购物车对象放入到session范围:
			request.getSession().setAttribute("cart", cart);
		}
		return cart;
	}
	
	//添加购物项
	public String addCart(){
		
		Product product = productService.findByPid(pid);
		
		CartItem cartItem = new CartItem();
		cartItem.setCount(count);
		cartItem.setProduct(product);
		
		HttpServletRequest request = ServletActionContext.getRequest();
		Cart cart = getCart(request);
		cart.addCart(cartItem);
				
		return "addCartSuccess";
	}
	//清空购物车
	public String clearCart(){
		HttpServletRequest request = ServletActionContext.getRequest();
		Cart cart = getCart(request);
		cart.clearCart();
		return "clearCartSuccess";
	}
	
	public String removeCart(){
		HttpServletRequest request = ServletActionContext.getRequest();
		Cart cart = getCart(request);
		cart.removeCart(pid);
		return "removeCartSuccess";
	}
	
	public String myCart(){
		
		return "myCartSuccess";
	}
	
}
