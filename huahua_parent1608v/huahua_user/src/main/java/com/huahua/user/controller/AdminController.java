package com.huahua.user.controller;
import java.util.List;
import java.util.Map;

import antlr.StringUtils;
import huahua.common.utils.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.huahua.user.pojo.Admin;
import com.huahua.user.service.AdminService;

import huahua.common.PageResult;
import huahua.common.Result;
import huahua.common.StatusCode;
import sun.security.krb5.KrbCryptoException;

/**
 * 控制器层
 * @author Administrator
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private AdminService adminService;
	@Autowired
	private IdWorker idWorker;
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	/**
	 * 查询全部数据
	 * @return
	 */
	@RequestMapping(method= RequestMethod.GET)
	public Result findAll(){
		return new Result(true,StatusCode.OK,"查询成功",adminService.findAll());
	}

	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.GET)
	public Result findById(@PathVariable String id){
		return new Result(true,StatusCode.OK,"查询成功",adminService.findById(id));
	}


	/**
	 * 分页+多条件查询
	 * @param searchMap 查询条件封装
	 * @param page 页码
	 * @param size 页大小
	 * @return 分页结果
	 */
	@RequestMapping(value="/search/{page}/{size}",method=RequestMethod.POST)
	public Result findSearch(@RequestBody Map searchMap , @PathVariable int page, @PathVariable int size){
		Page<Admin> pageList = adminService.findSearch(searchMap, page, size);
		return  new Result(true,StatusCode.OK,"查询成功",  new PageResult<Admin>(pageList.getTotalElements(), pageList.getContent()) );
	}

	/**
     * 根据条件查询
     * @param searchMap
     * @return
     */
    @RequestMapping(value="/search",method = RequestMethod.POST)
    public Result findSearch( @RequestBody Map searchMap){
        return new Result(true,StatusCode.OK,"查询成功",adminService.findSearch(searchMap));
    }

	/**
	 * 增加
	 * @param admin
	 */
	@RequestMapping(method=RequestMethod.POST)
	public Result add(@RequestBody Admin admin  ){
		admin.setId((idWorker.nextId() + ""));
		admin.setPassword(bCryptPasswordEncoder.encode(admin.getPassword()));
		adminService.add(admin);
		return new Result(true,StatusCode.OK,"增加成功");
	}

	/**
	 * 修改
	 * @param admin
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.PUT)
	public Result update(@RequestBody Admin admin, @PathVariable String id ){
		admin.setId(id);
		adminService.update(admin);
		return new Result(true,StatusCode.OK,"修改成功");
	}

	/**
	 * 删除
	 * @param id
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.DELETE)
	public Result delete(@PathVariable String id ){
		adminService.deleteById(id);
		return new Result(true,StatusCode.OK,"删除成功");
	}
	@PostMapping("/login")
	public Result login(@PathVariable Admin admin){
	if (null!=admin && StringUtils.isEmpty.getPassword()){
		return new Result(false,StatusCode.ERROR,"参数有误");
	}
		if (null!=admin && StringUtils.isEmpty.getloginname()){
			return new Result(false,StatusCode.ERROR,"参数有误");
		}
		Admin byloginName = adminService.findByloginName(admin);
		if(null!=byloginName){
			//生成token
			return new Result(true,StatusCode.OK,"登陆成功");
		}else {
			return new Result(false,StatusCode.LOGINERROR,"登陆失败");
		}
	}
}
