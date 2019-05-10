package com.huahua.base.pojo;

import lombok.Data;



import javax.persistence.Entity;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @ClassName: Label
 * @Author: zhangyuyang
 * @Time : 2019/4/11 -上午11:38
 * @Version:1.0
 */
@Data
@Entity
@Table(name = "tb_label")
public class Label implements Serializable {

    private static final long serialVersionUID = 4022470030047664847L;

    @Id
    private String id;
    private String  labelname;
    private String state;
    private Long count;
    private Long fans;
    private String recommend;


}
