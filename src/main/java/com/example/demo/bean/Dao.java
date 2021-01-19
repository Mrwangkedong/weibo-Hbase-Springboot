package com.example.demo.bean;

import com.example.demo.contans.Contans;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;

import org.apache.hadoop.hbase.client.*;

import java.util.ArrayList;
import java.util.Map;

public class Dao {
    //创建用户
    public static void creatUser(String user_id,String password) throws IOException {
        //连接
        Connection connection = ConnectionFactory.createConnection(Contans.CONF);
        //表对象
        TableName tablename = TableName.valueOf(Contans.WEIBO_NAME);
        Table table =  connection.getTable(tablename);
        //Put集合
        Put put = new Put((user_id).getBytes());  //row key
        //put赋值
        put.addColumn(Contans.WEIBO_PASSWORD.getBytes(), Bytes.toBytes("password"),Bytes.toBytes(password));
        table.put(put);
        connection.close();
        System.out.println(user_id+"添加成功！");
    }

    //进行关注
    public static void goFollow(String uid,String fid) throws IOException {
        //获得连接
        Connection connection = ConnectionFactory.createConnection(Contans.CONF);
        //操作表对象
        TableName tablename = TableName.valueOf(Contans.WEIBO_NAME);
        Table table =  connection.getTable(tablename);
        //创建操作者的Put集合
        Put put_uid = new Put((uid).getBytes());  //row key
        Put put_fid = new Put((fid).getBytes());  //row key
        //put赋值,添加关注人和粉丝,列名称和值相同
        put_uid.addColumn(Contans.WEIBO_ATTENDS.getBytes(), Bytes.toBytes(fid),Bytes.toBytes(fid));
        put_fid.addColumn(Contans.WEIBO_FANS.getBytes(),Bytes.toBytes(uid),Bytes.toBytes(uid));
        //Table放入数据
        table.put(put_uid);
        table.put(put_fid);
        //关闭资源
        connection.close();
        System.out.println("用户"+uid+"关注用户"+fid+"...");
    }

    //取消关注
    public static boolean backFollow(String uid,String fid) throws IOException {
        //获得连接
        Connection connection = ConnectionFactory.createConnection(Contans.CONF);

        TableName tablename = TableName.valueOf(Contans.WEIBO_NAME);
        Table table =  connection.getTable(tablename);

        Delete uid_delete = new Delete(Bytes.toBytes(uid));
        Delete fid_delete = new Delete(Bytes.toBytes(fid));

        uid_delete.deleteColumn(Contans.WEIBO_ATTENDS.getBytes(),fid.getBytes());
        fid_delete.deleteColumn(Contans.WEIBO_FANS.getBytes(),uid.getBytes());

        table.delete(uid_delete);
        table.delete(fid_delete);

        connection.close();
        System.out.println("用户"+uid+"取消关注用户"+fid+"...");
        return true;
    }

    //获得粉丝
    public static ArrayList<Relation> getFans(String rowkey) throws IOException {
        System.out.println("获得全部粉丝...");
        //获得连接
        Connection connection = ConnectionFactory.createConnection(Contans.CONF);
        TableName tablename = TableName.valueOf(Contans.WEIBO_NAME);
        Table table =  connection.getTable(tablename);
        ArrayList<Relation> relation_list = new ArrayList<>();
        Get get = new Get(rowkey.getBytes());

        if(!get.isCheckExistenceOnly()){
            Result result = table.get(get);
            Map<byte[], byte[]> familyMap = result.getFamilyMap(Bytes.toBytes(Contans.WEIBO_FANS));
            for(Map.Entry<byte[], byte[]> entry:familyMap.entrySet()){
                Relation relation = new Relation();
                String id = Bytes.toString(entry.getKey());
                System.out.println(id);
                relation.setId(Bytes.toString(entry.getKey()));
                boolean isfans = isFans(id,rowkey);
                relation.setIsfans(isfans);
                relation_list.add(relation);
            }
        }
        //返回数组
        return relation_list;
    }


    //获得关注
    public static ArrayList<Relation> getAttends(String rowkey) throws IOException {
        System.out.println("获得全部关注...");
        //获得连接
        Connection connection = ConnectionFactory.createConnection(Contans.CONF);
        TableName tablename = TableName.valueOf(Contans.WEIBO_NAME);
        Table table =  connection.getTable(tablename);
        ArrayList<Relation> info_list = new ArrayList<>();
        Get get = new Get(rowkey.getBytes());

        if(!get.isCheckExistenceOnly()){
            //get
            Result result = table.get(get);
            //获得列名
            Map<byte[], byte[]> familyMap = result.getFamilyMap(Bytes.toBytes(Contans.WEIBO_ATTENDS));
            for(Map.Entry<byte[], byte[]> entry:familyMap.entrySet()){
                Relation relation = new Relation();
                //获得列名
                String id = Bytes.toString(entry.getKey());
                relation.setId(Bytes.toString(entry.getKey()));
                //是否关注为true
                relation.setIsfans(true);
                info_list.add(relation);
            }

        }
        //返回List
        return info_list;
    }


    //判断是否为粉丝
    public static boolean isFans(String uid,String fid) throws IOException {
        //获得连接
        Connection connection = ConnectionFactory.createConnection(Contans.CONF);
        //获取操作表对象
        TableName tablename = TableName.valueOf(Contans.WEIBO_NAME);
        Table table =  connection.getTable(tablename);
        boolean res = false;
        Get get = new Get(uid.getBytes());
        if(!get.isCheckExistenceOnly()){
            Result result = table.get(get);
            //获得fans下的全部列名
            Map<byte[], byte[]> familyMap = result.getFamilyMap(Bytes.toBytes(Contans.WEIBO_FANS));
            for(Map.Entry<byte[], byte[]> entry:familyMap.entrySet()){
                //获得列名
                String id = Bytes.toString(entry.getKey());
                //判断
                res = id.equals(fid);
                if (res){break;}
            }
        }else {
            return false; //不存在操作者ID返回false
        }
        return res;
    }

}
