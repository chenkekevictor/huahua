package com.huahua.article.dao;

import com.huahua.article.pojo.Channel;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.huahua.article.pojo.Article;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface ArticleDao extends JpaRepository<Article,String>,JpaSpecificationExecutor<Article>{

	/**
	 * 文章审核findOneById方法等价于updateArticleStateByArticleId方法
	 */
	@Query(nativeQuery = true,value = "select * from tb_article where id=?")
	Article findOneById(String id);

	@Modifying
	@Query(value = "update tb_article set state = '1' where id = ? ",nativeQuery = true)
	void  updateArticleStateByArticleId(String id);
	//@Modifying如果直接执行增删改的方法,需要加上Modifying的注解,否则不起效果

	/**
	 * 文章点赞
	 */
	@Modifying
	@Query(value = "update tb_article set thumbup = thumbup+1 where id =? ",nativeQuery = true)
	void  updateArticleThumbup(String id);

	/**
	 * 头条
	 */
	@Query(nativeQuery = true,value = "select * from huahua_article.tb_article where istop = ?")
	Article headline(String istop);
}
