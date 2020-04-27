package com.dj.edu.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dj.commonutils.R;
import com.dj.edu.entity.Teacher;
import com.dj.edu.entity.vo.TeacherQuery;
import com.dj.edu.service.TeacherService;
import com.dj.edu.servicebase.exceptionhandler.DjException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.poi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *  接口都是返回数据
 *  controller调用service的方法
 *  从前到后controller->service->mapper->entity
 * @author dj
 * @since 2020-04-19
 */
@CrossOrigin
@Api(description = "讲师管理")
//@Controller。@ResponseBody:一般返回Json数据
@RestController
@RequestMapping("/edu/teacher")//正常写就行,只是与路径有关系
public class TeacherController {

    //未作处理时的访问地址:http://localhost:8001/edu/teacher/findAllTeacher

    //把service注入controller,把mapper注入service
    @Autowired
    private TeacherService teacherService;

    //1.查询所有讲师表数据
    //rest风格
    @ApiOperation(value = "查询所有讲师表数据")
    @GetMapping("findAllTeacher")
    public R findAllTeacher(){
        //异常测试，成功
        try {
            int i=10/0;
        } catch (Exception e) {
            //对自定义异常手动抛出异常
            throw new DjException(20001,"执行了自定义异常处理。。。");
        }


        //调用service的方法实现查询所有的操作
        //wrapper应该是数据库的复杂操作
        List<Teacher> list = teacherService.list(null);
        return R.ok().data("items",list);
    }

    /**
     * 2.逻辑删除讲师的方法,id值需要通过路径进行传递
     * 使用swagger进行测试，postman一般前端用
     * swagger1.生成在线接口文档2.方便接口测试
     * @param id 讲师ID
     * @return R 成功或失败
     */
    @ApiOperation(value = "逻辑删除讲师")
    @DeleteMapping("{id}")
    public R removeTeacher(
            @ApiParam(name = "id", value = "讲师ID", required = true)
            @PathVariable String id){

        boolean flag = teacherService.removeById(id);
        if (flag){
            return R.ok();
        }else {
            return R.error();
        }
    }


    /**
     * 3 分页查询讲师的方法
     * 路径传值
     * current 当前页数
     * limit   每页显示个数
     */

    @ApiOperation(value = "分页查询讲师的方法")
    @GetMapping("pageTeacher/{current}/{limit}")
    public R pageListTeacher(
            @PathVariable long current,
            @PathVariable long limit){

        //创建page对象,(当前页，每页显示个数)
        Page<Teacher> pageTeacher = new Page<>(current,limit);
        //调用方法实现分页
        //调用方法时候，底层封装，将分页所有数据封装到pageTeacher对象中
        teacherService.page(pageTeacher,null);

        long total = pageTeacher.getTotal();//总记录数
        List<Teacher> records = pageTeacher.getRecords();//数据List集合

        //方法1。返回map
//        Map<String,Object> map = new HashMap<>();//生成双Object
//        map.put("total",total);
//        map.put("records",records);
//
//        return R.ok().data(map);

        //方法2。返回两个List
        return R.ok().data("total",total).data("records",records);
    }


    //4.多条件组合查询带分页方法
    /**
     * @RequestBody 使用json传递数据，把json数据封装到对应对象中
     *              此时需要使用POST方式
     * required = false 表示筛选的参数值可以为空
     * @param current 当前页
     * @param limit   记录数
     * @param teacherQuery  筛选条件
     * @return
     */
    @ApiOperation(value = "多条件组合查询讲师带分页方法")
    @PostMapping("pageTeacherCondition/{current}/{limit}")
    public R pageTeacherCondition(@PathVariable long current,
                                  @PathVariable long limit,
                                  @RequestBody(required = false) TeacherQuery teacherQuery){

        //创建page对象
        Page<Teacher> pageTeacher = new Page<>(current, limit);
        //构建条件
        QueryWrapper<Teacher> wrapper = new QueryWrapper<>();
        //wrapper.多条件组合查询
        //mybatis学过动态sql，即在xml中动态判断，拼接sql语句
        //此处利用判断进行操作，判断条件值是否为空，如果不为空就拼接条件
        String name = teacherQuery.getName();//对应TeacherQuery
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        //判断条件是否为空，如果不为空则拼接条件
        if (!StringUtils.isEmpty(name)){
            //构建条件
            wrapper.like("name",name);//字段名称，实际传入的值
        }
        if (!StringUtils.isEmpty(level)){
            wrapper.eq("level",level);
        }
        if (!StringUtils.isEmpty(begin)){
            wrapper.ge("gmt_create",begin);//此处加的不是类中的属性名称而是表中的字段名称
        }
        if (!StringUtils.isEmpty(end)){
            wrapper.le("gmt_modified",end);
        }

        //根据创建时间降序排列
        wrapper.orderByDesc("gmt_create");

        //调用方法，实现条件查询,调用方法时候，底层封装，将分页所有数据封装到pageTeacher对象中
        teacherService.page(pageTeacher,wrapper);


        //构建结果并返回
        long total = pageTeacher.getTotal();//总记录数
        List<Teacher> records = pageTeacher.getRecords();//数据List集合
        return R.ok().data("total",total).data("records",records);


    }

    //5.添加讲师的接口方法
    @ApiOperation(value = "添加讲师")
    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody Teacher teacher){
        boolean save = teacherService.save(teacher);
        if (save){
            return R.ok();
        }else{
            return R.error();
        }

    }


    //6.讲师id进行查询
    /**
     * 1.首先根据讲师id进行查询
     * 2.讲师修改
     */
    @ApiOperation(value = "讲师id查询")
    @GetMapping("getTeacher/{id}")
    public R getTeacher(@PathVariable String id){
        // 6.1.首先根据讲师id进行查询
        Teacher teacher = teacherService.getById(id);
        return R.ok().data("teacher",teacher);

    }

    /**
     * 7.讲师修改
     * （根据id使用put），使用put请求时需手动将id属性填入对象中
     * （根据对象使用post）
     */
//    //1.（根据id使用put）
//    @ApiOperation(value = "根据ID修改讲师,put")
//    @PutMapping("upDateTeacherByIdPut")
//    public R upDateById(@PathVariable String id,
//                        @RequestBody Teacher teacher){
//
//        teacher.setId(id);
//        teacherService.updateById(teacher);
//        return R.ok();
//
//    }

    //2.（根据对象使用post）,requestBody需要和POST一起使用
    //注意此处修改根据id值，必须需要id
    @ApiOperation(value = "根据ID修改讲师,post")
    @PostMapping ("upDateTeacher")
    public R updateTeacher(@RequestBody Teacher teacher){
        //teacher对象中含有id
        boolean flag = teacherService.updateById(teacher);
        if (flag){
            return R.ok();
        }else {
            return R.error();
        }

    }




}

