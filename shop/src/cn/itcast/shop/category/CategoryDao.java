package cn.itcast.shop.category;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class CategoryDao extends HibernateDaoSupport{

	// DAO层的查询所有的一级分类的代码
	public List<Category> findAll() {
		return this.getHibernateTemplate().find("from Category");
	}

	public void save(Category category) {
		// TODO Auto-generated method stub
		this.getHibernateTemplate().save(category);
	}

	public void delete(Category category) {
		category = this.getHibernateTemplate().get(Category.class, category.getCid());
		this.getHibernateTemplate().delete(category);
	}

	public Category findByCid(Integer cid) {
		// TODO Auto-generated method stub
		return this.getHibernateTemplate().get(Category.class, cid);
	}

	public void update(Category category) {
		// TODO Auto-generated method stub
		this.getHibernateTemplate().update(category);
	}
	
}
