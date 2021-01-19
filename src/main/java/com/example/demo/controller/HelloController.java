package com.example.demo.controller;

import com.example.demo.bean.Dao;
import com.example.demo.bean.Relation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;

//解决跨域
@CrossOrigin(origins = "http://127.0.0.1:8848/", maxAge = 3600)
@Controller
public class HelloController {

    //得到粉丝
    @ResponseBody
    @RequestMapping(path = "/fans",method = RequestMethod.GET)
    @CrossOrigin(origins = "http://127.0.0.1:8848/")
    public ArrayList<Relation> fans(String key) throws IOException {
        ArrayList<Relation> relationArrayList;
        System.out.println("Fans接收到的key："+key);
        relationArrayList = Dao.getFans(key);

        return relationArrayList;
    }

    //得到关注人
    @ResponseBody
    @RequestMapping(path = "/attends")
    public ArrayList<Relation> attends(String key) throws IOException {
        ArrayList<Relation> relationArrayList;

        relationArrayList = Dao.getAttends(key);

        return relationArrayList;
    }

//    //进行关注
    @ResponseBody
    @RequestMapping(path = "/gofollow")
    public boolean goFollow(String akey,String bkey) throws IOException {
        try {
            Dao.goFollow(akey,bkey);
        }catch (IOException e){
            return false; //出现错误，返回false
        }
        return true;
    }
//
//    //取消关注
    @ResponseBody
    @RequestMapping(path = "/backfollow")
    public boolean backFollow(String akey,String bkey) {
        try {
            Dao.backFollow(akey,bkey);
        }catch (IOException e){
            return false;   //出现错误，返回false
        }
        return true;
    }
//
    //查找用户
    @ResponseBody
    @RequestMapping(path = "/searchuser")
    public Relation searchUser(String akey,String bkey){
        ArrayList<Relation> relationArrayList = null;
        Relation relation = new Relation();
        relation.setId(bkey);
        try {
            relation.setIsfans(Dao.isFans(akey,bkey));
        } catch (IOException e) {
            return null;
        }
        return relation;
    }
//
    //判断两用户关系
    @ResponseBody
    @RequestMapping(path = "/userrelation")
    public  ArrayList<Boolean> userRelation(String akey,String bkey){
        ArrayList<Boolean> relation_list = new ArrayList<>();

        try {
            Boolean res_akey = Dao.isFans(akey,bkey);
            Boolean res_bkey = Dao.isFans(bkey,akey);
            relation_list.add(res_akey);
            relation_list.add(res_bkey);
        }catch (IOException e){
            return null;
        }

        return relation_list;
    }

    //



}
