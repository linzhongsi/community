package com.example.demo.controller;

import com.example.demo.service.AlphaService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@Controller
@RequestMapping("/alpha")
public class AlphaController {
        @Autowired
        private AlphaService alphaService;

        @RequestMapping("/hello")
        @ResponseBody
        public String sayHello() {
            return "Hello Spring Boot.";
        }

        @RequestMapping("/data")
        @ResponseBody
        public String getData(){
            return alphaService.find();
        }

        //对底层对象的处理
        @RequestMapping("/http")
        //这里不写reponsebody了，内容都写入serlvetResponse
        public void http(HttpServletRequest request, HttpServletResponse response) throws IOException {
                //获取请求数据
                System.out.println(request.getMethod());
                System.out.println(request.getServletPath());
                // 需要迭代
                Enumeration<String> enumeration = request.getHeaderNames();
                while(enumeration.hasMoreElements()){
                        String name = enumeration.nextElement();
                        String values = request.getHeader(name);
                        System.out.println(name + " : " + values);
                }
                System.out.println(request.getParameter("code")); //可以获取传入参数 路径 ? code（参数名) = 123 即可传参

                // 返回响应数据
                response.setContentType("text/html;charset=utf-8"); // 返回的数据类型
                try(
                        PrintWriter printWriter = response.getWriter();
                        ){
                        printWriter.write("<h1> 2023/8/10 </h1> "); //html内容
                }catch(IOException e){
                        e.printStackTrace();
                }
        }
        //接受浏览器请求数据
        //GET请求
        // students?current=1&limit=20
        @RequestMapping(path = "/students", method = RequestMethod.GET) // 强制必须是Get请求
        @ResponseBody
        public String getstudents(
                //  参数名 从路径中获取对应的内容，然后传给后面的参数 / 是否是必须 / 默认值 / 都是String类型
                @RequestParam(name = "current", required = false, defaultValue = "1") int current,
                @RequestParam(name = "limit", required = false , defaultValue = "20") int limit
        ){
                System.out.println(current);
                System.out.println(limit);
                return "students";
        }

        // 参数作为路径传入
        // /student/23
        @RequestMapping(path = "/student/{id}", method = RequestMethod.GET)
        @ResponseBody
        public String getid(@PathVariable("id") int id){
                System.out.println(id);
                return "id";
        }
        @RequestMapping(path = "/student", method = RequestMethod.POST)
        @ResponseBody
        public String saveStudent(String name, String age){ //和html表单中内容一致即可传入 其实也可以ParaVariable
                System.out.println(name);
                System.out.println(age);
                return "saveStudent";
        }

        // 响应浏览器 返回html 不加注解，默认返回html
        @RequestMapping(path = "/teacher", method = RequestMethod.GET)
        public ModelAndView getTeacher(){ //model view 参考dispatcherservlet那一节内容 组合给模板引擎
                ModelAndView mav = new ModelAndView();
                //键值对，可以看作对模板引擎中对因变量的替换
                mav.addObject("name", "张三");
                mav.addObject("age", 30);
                mav.setViewName("/demo/view"); // 选择传递给哪一个模板(只需要写templates下的路径即可，不需要后缀)
                return mav;
        }

        @RequestMapping(path = "/school", method = RequestMethod.GET)
        public String getSchool(Model model){ // 是一个对象，dispatcher持有其应用，return的string就是网页路径
                //键值对，可以看作对模板引擎中对因变量的替换
                model.addAttribute("name", "张三"); //这里是addAttribute
                model.addAttribute("age", 30);
                return "/demo/view"; // 看作直接返回view
        }

        //响应JSON数据 异步请求 （当前网页不刷新 但是向服务器进行访问了 例如提示用户名已经被占用)
        // Java对象  - JSON字符串 - JS对象 中间字符串，适用于跨语言
        // Java中看作一个map对象 注解 + 返回类型 自动转化为JSON类型
        @RequestMapping(path = "/emp", method = RequestMethod.GET)
        @ResponseBody
        public Map<String, Object> getEmp(){
                Map<String, Object> emp = new HashMap<>();
                emp.put("name", "张三");
                emp.put("age", 19);
                emp.put("salary", 3000);
                return emp;
        }

        //返回多个对象
        @RequestMapping(path = "/emps", method = RequestMethod.GET)
        @ResponseBody
        public List<Map<String, Object>> getEmps(){

                List<Map<String, Object>> list = new ArrayList<>();

                Map<String, Object> emp = new HashMap<>();
                emp.put("name", "张三");
                emp.put("age", 19);
                emp.put("salary", 3000);
                list.add(emp);
                list.add(emp);
                list.add(emp);

                return list;
        }

}
