package com.huahua.spit.controller;

import com.huahua.spit.pojo.Spit;
import com.huahua.spit.service.SpitService;
import huahua.common.PageResult;
import huahua.common.Result;
import huahua.common.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;


import java.util.Date;

@RestController
@RequestMapping("/spit")
@CrossOrigin
public class SpitController {
    @Autowired private RedisTemplate redisTemplate;
    @Autowired private SpitService spitService;
    /**
     * 功能描述：查询全部
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll(){
        return new Result(true,StatusCode.OK,"查询成功",spitService.findAll());
    }
    /**
     * 功能描述：根据id查询
     */
    @RequestMapping(method = RequestMethod.GET,value = "/{id}")
    public Result findById(@PathVariable("id")String id){
        return new Result(true,StatusCode.OK,"查询成功",spitService.findById(id));
    }
    /**
     * 功能描述：添加
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody Spit spit){
        spit.setPublishtime(new Date());
        spitService.add(spit);
        return new Result(true,StatusCode.OK,"添加成功");
    }
    /**
     * 功能描述：修改
     */
    @RequestMapping(method = RequestMethod.PUT,value = "/{id}")
    public Result update(@RequestBody Spit spit,@PathVariable("id")String id){
        spit.set_id(id);
        spitService.update(spit);
        return new Result(true,StatusCode.OK,"修改成功");
    }
    /**
     * 功能描述：删除
     */
    @RequestMapping(method = RequestMethod.DELETE,value = "/{id}")
    public Result delete(@PathVariable("id")String id){
        spitService.delete(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }
    /**
     * 功能描述：根据上级id查询吐槽数据(分页)
     */
    @RequestMapping(method = RequestMethod.GET,value = "/comment/{parentid}/{page}/{size}")
    public Result findByPid(@PathVariable("parentid")String parentid,@PathVariable("page")Integer page,@PathVariable("size")Integer size){
        Page<Spit> pageList = spitService.findByPid(parentid,page,size);
        return new Result(true,StatusCode.OK,"查询成功",new PageResult<Spit>(pageList.getTotalElements(),pageList.getContent()));
    }
    /**
     * 功能描述：吐槽点赞
     */
    @RequestMapping(method = RequestMethod.PUT,value = "/thumbup/{spitId}")
    public Result updateThumbup(@PathVariable("spitId")String spitId){
        String userid="xxx";
        Integer obj = (Integer) redisTemplate.opsForValue().get("thumbup_" + userid + "id_" + spitId);
        if(null == obj){
            spitService.updateThumbup(spitId);
            redisTemplate.opsForValue().set("thumbup_" + userid + "id_" + spitId,1);
            return new Result(true,StatusCode.OK,"点赞成功");
        }
        return new Result(true,StatusCode.OK,"已经点过了");
    }
    /**
     * 功能描述：分页查询全部
     */
    @RequestMapping(method = RequestMethod.POST,value = "/search/{page}/{size}")
    public Result findByPage(@PathVariable("page")Integer page,@PathVariable("size")Integer size){
        Page<Spit> pageList = spitService.findByPage(page,size);
        return new Result(true,StatusCode.OK,"查询成功",new PageResult<Spit>(pageList.getTotalElements(),pageList.getContent()));
    }
    /**
     * 功能描述：根据条件查询
     */
    @RequestMapping(method = RequestMethod.POST,value = "/search")
    public Result findByConditions(@RequestBody Spit spit){
        return new Result(true,StatusCode.OK,"查询成功",spitService.findByConditions(spit));
    }
}
