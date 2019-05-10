package com.huahua.base.service;

import com.huahua.base.dao.BaseDao;
import com.huahua.base.pojo.Label;
import huahua.common.utils.IdWorker;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @ClassName: BaseService
 * @Author: zhangyuyang
 * @Time : 2019/4/11 -上午11:36
 * @Version:1.0
 */
@Service
public class BaseService {
    @Autowired
    private BaseDao baseDao;


    @Autowired
    private IdWorker idWorker;

    public void add(Label label) {
       // String.valueOf(idWorker.nextId());
        if(StringUtils.isNotEmpty(label.getId())){
            baseDao.save(label);
        }else {
            label.setId(idWorker.nextId() + "");
            baseDao.save(label);
        }
    }

    public List<Label> findAll() {
        return baseDao.findAll();
    }



    public List<Label> toplist(String recommend){

        return baseDao.findAllByRecommendOrderByFansDesc(recommend);
    }


    public List<Label> queryByStateAllList(String state){

        return baseDao.findAllByStateOrderByFansDesc(state);
    }

    public Label queryById(String id) {

        return  baseDao.queryById(id);
    }

    public void delete(String id) {
        baseDao.deleteById(id);
    }

    public Page<Label> findSearch(Label label,Integer page,Integer size){
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return baseDao.findAll(this.createSpecification(label),pageRequest);
    }

    /**
     * 条件构造器
     * @param label
     * @return
     */
    private Specification<Label> createSpecification(Label label){
        return new Specification<Label>() {
            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<>();
                //创建SQL语句
                if(null == label){
                    return  null;
                }
                if (StringUtils.isNotEmpty(label.getId())){
                    //where id =?
                    predicateList.add(cb.equal(root.get("id").as(String.class),label.getId()));
                }
                if (StringUtils.isNotEmpty(label.getLabelname())){
                    //and Labelname like "%?%"
                    predicateList.add(cb.like(root.get("labelname").as(String.class),"%"+label.getLabelname()+"%"));
                }
                if (StringUtils.isNotEmpty(label.getState())){
                    predicateList.add(cb.equal(root.get("state").as(String.class),label.getState()));
                }
                if (null!=predicateList &&predicateList.size()>0) {
                    return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));
                }
                return null;
            }
        };
    }
}
