package com.yper.feng.growup.Util;

import com.mongodb.BasicDBObject;
import com.mongodb.util.JSON;

import net.sf.json.JSONObject;

import org.bson.Document;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


/**
 * Created by wangpeng on 2017/3/18.
 * 作用：将mongodb的查询语句字符串转化为BasicDBObject
 */
public class BsonUtil {
    /**
     * find查询
     * 例子：{type:'dailyReport',writer:{$in:['何蕾','万']},delFlag:'0',date:{$gte: '2000-12-12 00:00:00',$lte: '2017-03-30 00:00:00'}}
     * 表：Report
     *
     * @param bsonString 字符串
     * @return 返回结果
     */
    public static BasicDBObject getBasicDBObject(String bsonString) {
        return (BasicDBObject) JSON.parse(bsonString);
    }


    /**
     * aggregate 查询
     * String[] str = new String[]{
     * "{$match:{type:'dailyReport',writer:{$in:['何蕾','万']},delFlag:'0',date:{$gte: '2000-12-12 00:00:00',$lte: '2017-03-30 00:00:00'}}}"};
     *
     * @param bsonString 字符串
     * @return 返回结果
     */
    public static List<BasicDBObject> getBasicDBObjectList(String[] bsonString) {
        if (bsonString.length == 0) return null;
        List<BasicDBObject> basicDBObjects = new ArrayList<>();
        for (String bson : bsonString) {
            basicDBObjects.add(getBasicDBObject(bson));
        }
        return basicDBObjects;
    }


//    /**
//     * 数组转换为字符串
//     * @param str 字符串数组
//     * @return 返回结果
//     */
//    public static String array2String(String[] str){
//        String[] strr = new String[str.length];
//        for(int i = 0; i < str.length; i++){
//            strr[i] =  "'"+str[i]+"'";
//        }
//
//        return "["+String.join(",", strr)+"]";
//    }

//    /**
//     * 数组转换为字符串
//     * @param str 字符串数组
//     * @return 返回结果
//     */
//    public static String array2Str(String[] str){
//        if(str == null || str.length == 0) return "";
//        String[] strr = new String[str.length];
//        for(int i = 0; i < str.length; i++){
//            strr[i] =  str[i];
//        }
//        return String.join(",", strr);
////    }
//
//    /**
//     * 数组转换为字符串
//     * @param str 字符串数组
//     * @return 返回结果
//     */
//    public static String list2Str(List<String> str){
//        if(str == null || str.isEmpty()) return "";
//        String[] strr = new String[str.size()];
//        for(int i = 0; i < str.size(); i++){
//            strr[i] =  str.get(i);
//        }
//        return String.join(",", strr);
//    }
//
//    /**
//     * 集合转换为字符串
//     * @param str 字符串集合
//     * @return 返回结果
//     */
//    public static String list2String(List<String> str){
//        String[] strr = new String[str.size()];
//        for(int i = 0; i < str.size(); i++){
//            strr[i] =  "'"+str.get(i)+"'";
//        }
//        return "["+String.join(",", strr)+"]";
//    }


    /**
     * 字符串转换为集合
     *
     * @param str 字符串
     * @return 返回结果
     */
    public static List<String> string2list(String str) {
        List<String> list = new ArrayList<>();
        if (!str.contains(",")) {
            list.add(str);
        } else {
            list = Arrays.asList(str.split(","));
        }
        return list;
    }

    /**
     * 字符串转为时间
     *
     * @param date 2017-05-13 13:45:00
     * @return 返回结果
     */
    public static Date string2Date(String date) {
        if (!date.contains(" ")) date += " 00:00:00";
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param docs docs
     * @return 返回结果
     */
    public static List<JSONObject> docListToJSONList(List<Document> docs) {
        List<JSONObject> results = new ArrayList<>();
        for (Document doc : docs) {
            results.add(JSONObject.fromObject(doc.toJson()));
        }
        return results;
    }

    /**
     * 工具方法 将list集合转为数组
     *
     * @param dbObjects 参数
     * @return 范湖字符串数组
     */
    public static String[] changeList2Array(List<BasicDBObject> dbObjects) {
        if (dbObjects == null || dbObjects.size() == 0) return new String[]{};
        String[] dbsqls = new String[dbObjects.size()];
        for (int i = 0; i < dbObjects.size(); i++) {
            dbsqls[i] = dbObjects.get(i).toJson();
        }
        return dbsqls;
    }

    /**
     * 工具方法
     *
     * @param docs 参数
     * @return 结果
     */
    public static List<JSONObject> changeDocument2JSONObject(List<Document> docs) {
        List<JSONObject> result = new ArrayList<>();
        if (docs == null) return result;
        for (Document doc : docs) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.putAll(doc);
            result.add(jsonObject);
        }
        return result;
    }

    /**
     * @param sids sids
     * @return 返回结果
     */
    public static String[] string2Array(String sids) {
        if ("".equals(sids) || sids == null) {
            return new String[]{};
        }
        if (sids.contains(",")) {
            return sids.split(",");
        } else {
            return new String[]{sids};
        }
    }
}
