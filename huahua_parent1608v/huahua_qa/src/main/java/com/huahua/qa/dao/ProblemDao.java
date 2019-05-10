package com.huahua.qa.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.huahua.qa.pojo.Problem;
import org.springframework.data.jpa.repository.Query;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface ProblemDao extends JpaRepository<Problem,String>,JpaSpecificationExecutor<Problem>{


    /**
     * 最新问答
     * @param labelid
     * @param pageRequest
     * @return
     */
    @Query(nativeQuery = true,value = "select * from tb_problem,tb_pl where 1=1 and id=problemid and labeld = ? order by createtime desc")
    public Page<Problem>  newlist(String labelid, PageRequest pageRequest);

    /**
     * 热门回答
     * @param labelid
     * @param pageRequest
     * @return
     */
    @Query(nativeQuery = true,value = "select * from tb_problem,tb_pl where 1=1 and id=problemid and labeld = ? order by visits desc")
    public Page<Problem> hotlist(String labelid, PageRequest pageRequest);

    /**
     * 等待回答
     * @param labelid
     * @param pageRequest
     * @return
     */
    @Query(nativeQuery = true,value = "select * from tb_problem,tb_pl where 1=1 and id=problemid and reply =0 and labeld = ? order by createtime desc")
    public Page<Problem> waitlist(String labelid, PageRequest pageRequest);

}
