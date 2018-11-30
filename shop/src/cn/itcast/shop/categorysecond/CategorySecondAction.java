package cn.itcast.shop.categorysecond;

import java.util.List;

import cn.itcast.shop.category.Category;
import cn.itcast.shop.category.CategoryService;
import cn.itcast.shop.utils.PageBean;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class CategorySecondAction extends ActionSupport implements ModelDriven<CategorySecond>{
	
	private Integer page;
	private CategorySecondService categorySecondService;
	private CategoryService categoryService;
	private Integer cid;
	
	private CategorySecond categorySecond = new CategorySecond();
	public CategorySecond getModel() {
		
		return categorySecond;
	}
	
	public void setCid(Integer cid) {
		this.cid = cid;
	}

	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public void setCategorySecondService(CategorySecondService categorySecondService) {
		this.categorySecondService = categorySecondService;
	}

	public String adminFindAll(){
		 PageBean<CategorySecond> pageBean = categorySecondService.findByPage(page); 
		 ActionContext.getContext().getValueStack().set("pageBean", pageBean);
		 return "adminFindAllSuccess";
	}
	
	public String addPage(){
		List<Category> cList = categoryService.findAll();
		ActionContext.getContext().getValueStack().set("cList", cList);
		return "addPageSuccess";
	}

	public String save(){
		Category category = new Category();
		category.setCid(cid);
		categorySecond.setCategory(category);
		categorySecondService.save(categorySecond);
		return "saveSuccess";
	}
	
}
