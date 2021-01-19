package com.example.demo.contans;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;

public class Contans {

    //Hbase配置信息
    public static Configuration CONF = null;
    static {
        CONF= HBaseConfiguration.create();
        CONF.set("hbase.zookeeper.quorum","192.168.255.134");
        CONF.set("hbase.zookeeper.property.clientPort","2181");
    }

    //表名
    public static String WEIBO_NAME = "hbase:weibo";
    public static String WEIBO_PASSWORD = "password";
    public static String WEIBO_ATTENDS = "attends";
    public static String WEIBO_FANS = "fans";
}
