package com.huahua.qa.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.huahua.qa.pojo.Reply;

import java.util.List;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface ReplyDao extends JpaRepository<Reply,String>,JpaSpecificationExecutor<Reply>{
    /**
     * 根据问题的id查询 回答的列表
     */
    public List<Reply> findAllByProblemidOrderByCreatetimeDesc(String problemid);
}
