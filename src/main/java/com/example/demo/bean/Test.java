package com.example.demo.bean;

import com.example.demo.contans.Contans;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;

import java.io.IOException;

public class Test {
    public static void main(String[] args) throws IOException {
        Connection connection = ConnectionFactory.createConnection(Contans.CONF);
        Admin admin = connection.getAdmin();

        System.out.println("connection: "+connection);
        System.out.println(admin);

        //添加用户*************************
//        for (int i = 2; i <= 2; i++) {
//            for (int a = 0; a <= 9; a++) {
//                for (int b = 0; b <= 9; b++) {
//                    for (int c = 0; c <= 9; c++) {
//                        String id = i + String.valueOf(a) + b + c;  //id
//                        String password = "aaa";
//                        Dao.creatUser(id,password);
//                    }
//                }
//            }
//        }
//        //**********************
//
//        //添加粉丝
//        for(int i=2100;i<=2110;i++){
//            Dao.goFollow(String.valueOf(i),"2998");
//        }
//        //添加关注
//        for(int i=2100;i<=2110;i++){
//            Dao.goFollow("2998",String.valueOf(i));
//        }

//        System.out.println(Dao.getFans("2998"));

    }
}
