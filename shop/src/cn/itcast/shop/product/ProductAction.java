package cn.itcast.shop.product;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;

import cn.itcast.shop.category.Category;
import cn.itcast.shop.category.CategoryService;
import cn.itcast.shop.categorysecond.CategorySecond;
import cn.itcast.shop.categorysecond.CategorySecondService;
import cn.itcast.shop.utils.PageBean;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 商品访问的Action类
 * 
 * @author 传智.郭嘉
 * 
 */
public class ProductAction extends ActionSupport implements
		ModelDriven<Product> {
	private File upload;
	private String uploadContentType;
	private String uploadFileName;
	// 接收cid
	private Integer cid;
	// 接收二级分类id
	private Integer csid;
	// 接收当前页数
	private Integer page;
	// 注入一级分类的Service
	private CategoryService categoryService;
	
	private CategorySecondService categorySecondService;
	public void setCategorySecondService(CategorySecondService categorySecondService) {
		this.categorySecondService = categorySecondService;
	}

	// 注入商品的Service
	private ProductService productService;
	// 分页的商品显示:
	private PageBean<Product> pageBean;
	// 模型驱动类
	private Product product = new Product();

	public Product getModel() {
		return product;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public void setCsid(Integer csid) {
		this.csid = csid;
	}

	public Integer getCsid() {
		return csid;
	}

	public PageBean<Product> getPageBean() {
		return pageBean;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

	public Integer getCid() {
		return cid;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	// 查询商品的方法:
	public String findByCid() {
		// 查询分类:
		// 查询所有一级分类:
		List<Category> categoryList = categoryService.findAll();
		// 获得值栈:
		ActionContext.getContext().getValueStack()
				.set("categoryList", categoryList);

		// 查询商品:
		pageBean = productService.findByPage(cid, page);

		return "findByCidSuccess";
	}

	// 查询商品详情:
	public String findByPid() {
		// 查询所有一级分类:
		List<Category> categoryList = categoryService.findAll();
		// 获得值栈:
		ActionContext.getContext().getValueStack()
				.set("categoryList", categoryList);

		product = productService.findByPid(product.getPid());
		return "findByPidSuccess";
	}

	// 查询二级分类的商品:
	public String findByCsid() {
		// 查询所有一级分类:
		List<Category> categoryList = categoryService.findAll();
		// 获得值栈:
		ActionContext.getContext().getValueStack()
				.set("categoryList", categoryList);

		pageBean = productService.findByCsid(csid, page);
		return "findByCsidSuccess";
	}
	
	public String adminFindAll(){
		pageBean = productService.findByPage(page); 
		return "adminFindAllSuccess";
	}
	
	public String addPage(){
		List<CategorySecond> csList = categorySecondService.findAll();
		ActionContext.getContext().getValueStack().set("csList", csList);
		return "addPageSuccess"; 
	}
	
	public String save() throws IOException{
		
		String path = ServletActionContext.getServletContext().getRealPath("/products");
		String realPath = path + "/" + csid + "/" +uploadFileName;
		File diskFile = new File(realPath);
		FileUtils.copyFile(upload, diskFile);
		
		CategorySecond categorySecond = new CategorySecond();
		categorySecond.setCsid(csid);
		product.setCategorySecond(categorySecond);
		product.setPdate(new Date());
		product.setImage("products/" + csid + "/" + uploadFileName);
		
		productService.save(product);
		return "saveSuccess";
		
	}
	
}
