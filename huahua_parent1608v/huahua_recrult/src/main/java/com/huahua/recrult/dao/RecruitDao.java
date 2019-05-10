package com.huahua.recrult.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.huahua.recrult.pojo.Recruit;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface RecruitDao extends JpaRepository<Recruit,String>,JpaSpecificationExecutor<Recruit>{
    /**
     * 推荐职位
     */
    public List<Recruit> findTop4AllByState(String state);

    /**
     * z最新职位
     */
    @Query(nativeQuery = true,value = "SELECT * FROM tb_recruit t WHERE 1=1 and t.state<> 0 order by t.createtime desc")
    public List<Recruit> queryByStateNewRecruitList(String state);
}
