package com.huahua.article.controller;

import com.huahua.article.pojo.CommentMongoDB;
import com.huahua.article.service.CommentMongoDBService;
import huahua.common.Result;
import huahua.common.StatusCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
@CrossOrigin
public class CommentMongoDBController {

    @Autowired private CommentMongoDBService commentMongoDBService;

    /**
     * 功能描述：添加评论
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody CommentMongoDB commentMongoDB){
        if (StringUtils.isEmpty(commentMongoDB.getArticleid())){
            return new Result(true, StatusCode.ERROR,"参数有误！");
        }
        commentMongoDBService.add(commentMongoDB);
        return new Result(true, StatusCode.OK,"添加成功");
    }
    /**
     * 功能描述：根据文章id查询评论列表
     */
    @RequestMapping(method = RequestMethod.GET,value = "/article/{articleid}")
    public Result queryByArticleid(@PathVariable("articleid")String articleid){
        return new Result(true, StatusCode.OK,"查询成功",commentMongoDBService.queryByArticleid(articleid));
    }
    /**
     * 功能描述：删除评论
     * @return huahua.common.Result
     */
    @RequestMapping(method = RequestMethod.DELETE,value = "/{ids}")
    public Result delete(@PathVariable("ids")String ids){
        commentMongoDBService.delete(ids);
        return new Result(true, StatusCode.OK,"删除成功");

    }
}
