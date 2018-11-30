package cn.itcast.shop.category;

import java.util.List;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class CategoryAction extends ActionSupport implements ModelDriven<Category>{
	
	private Category category = new Category();
	public Category getModel() {
		// TODO Auto-generated method stub
		return category;
	}
	
	private CategoryService categoryService;
	
	public String adminFindAll(){
		List<Category> cList = categoryService.findAll();
		ActionContext.getContext().getValueStack().set("cList", cList);
		return "adminFindAllSuccess";
	}

	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	public String save(){
		categoryService.save(category);
		return "saveSuccess";
	}
	
	public String delete(){
		categoryService.delete(category);
		return "deleteSuccess";
	}
	
	public String edit(){
		category = categoryService.findByCid(category.getCid());
		return "editSuccess";
	}
	
	public String update(){
		categoryService.update(category);
		return "updateSuccess";
		
	}
	
}
