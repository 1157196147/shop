package cn.itcast.shop.category;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public class CategoryService {
	// 注入DAO
	private CategoryDao categoryDao;

	public void setCategoryDao(CategoryDao categoryDao) {
		this.categoryDao = categoryDao;
	}

	// 业务层查询所有的一级分类的方法
	public List<Category> findAll() {
		return categoryDao.findAll();
	}

	public void save(Category category) {
		// TODO Auto-generated method stub
		categoryDao.save(category);
	}

	public void delete(Category category) {
		// TODO Auto-generated method stub
		categoryDao.delete(category);
	}

	public Category findByCid(Integer cid) {
		// TODO Auto-generated method stub
		return categoryDao.findByCid(cid);
	}

	public void update(Category category) {
		// TODO Auto-generated method stub
		categoryDao.update(category);
		
	}

	
	
	
}
