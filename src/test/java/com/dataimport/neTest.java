//package com.dataimport;
//import com.dataimport.entity.AuthorEntity;
//import org.json.JSONArray;
//import org.json.JSONObject;
//import org.junit.Test;
//import java.io.*;
//import java.util.*;
//import org.neo4j.cypher.internal.frontend.v2_3.ast.functions.Str;
//import org.neo4j.driver.v1.*;
//import org.neo4j.cypher.*;
//import org.json.*;
//import static org.neo4j.driver.v1.Values.parameters;
//import java.util.Collections;
///**
// * Created by XCY on 2018/3/12.
// */
//public class neTest {
//    public static void main(String[] args){
//        HashMap<String,ArrayList<Integer>>data = new HashMap<String,ArrayList<Integer>>();
//        data = new neTest().getData("高精度");
//
////        HashMap<Integer,HashMap<Integer,ArrayList<String>>> community = new neTest().getCommunity(data);
////        new neTest().writeJson(community);
//
//    }
////    public HashMap<String,ArrayList<Integer>> getDataQuery(String query){
////        org.neo4j.driver.v1.Driver driver = GraphDatabase.driver( "bolt://localhost:7687", AuthTokens.basic( "neo4j", "123456" ));//配置驱动
////        Session session = driver.session();//建立连接
////        StatementResult result1 = session.run(
////                "MATCH (n:Keyword) WHERE n.name = \"高精度\" " +
////                        "return n.partitionKey AS partitionKey,n.partitionKey1 AS partitionKey1",
////                parameters( "", "" ));//获取结果集
////        int firstCommunityNum = -1;
////        try {
////            Record recordFirst = result1.next();
////            firstCommunityNum = recordFirst.get("partitionKey").asInt();
////        }catch (Exception e){
////            System.out.println("获取节点所属第一社区失败!!!" + e);
////            return null;
////        }
////        StatementResult result = session.run(
////                "MATCH (n:Keyword) WHERE n.partitionKey = "+ firstCommunityNum +
////                        " return n.name AS name,n.partitionKey AS partitionKey,n.partitionKey1 AS partitionKey1",
////                parameters( "", "" ));//获取结果集
////        session.close();
////        driver.close();
////
////    }
//    public void partitionCommunity(){
//        org.neo4j.driver.v1.Driver driver = GraphDatabase.driver( "bolt://localhost:7687", AuthTokens.basic( "neo4j", "123456" ));//配置驱动
//        Session session = driver.session();//建立连接
//        session.run(
//                "CALL apoc.algo.community(25,['Keyword'],'partitionKey','similar','OUTGOING','weight',10000)",
//                parameters( "" , "" ));//获取结果集
//        session.run(
//                "CALL apoc.algo.community(25,['Keyword'],'partitionKey1','similar','OUTGOING','partitionKey',1000)",
//                parameters( "" , "" ));//获取结果集
//        session.close();
//        driver.close();
//    }
//    //返回数据格式 （节点  社区1,社区2）
//    public HashMap<String,ArrayList<Integer>> getData(String query){
//        HashMap<String,ArrayList<Integer>> data = new HashMap<String,ArrayList<Integer>>();
//        org.neo4j.driver.v1.Driver driver = GraphDatabase.driver( "bolt://10.168.103.21:7687",AuthTokens.basic( "neo4j", "123456" ));//配置驱动
//        Session session = driver.session();//建立连接
////        StatementResult result = session.run( "MATCH (n:Keyword) RETURN n LIMIT 25");//获取结果集
//        StatementResult result;
//        if (query ==null){
//            result = session.run(
//                    "MATCH (n:Keyword) RETURN n.name AS name,n.weight AS weight," +
//                            "n.partitionKey AS partitionKey,n.partitionKey1 AS partitionKey1",
//                    parameters( "", "" ));//获取结果集
//        }else {
//            StatementResult result1 = session.run(
//                    "MATCH (n:Keyword) WHERE n.name = \"" + query +
//                            "\" return n.partitionKey AS partitionKey,n.partitionKey1 AS partitionKey1",
//                    parameters( "", "" ));//获取结果集
//            int firstCommunityNum = -1;
//            try {
//                Record recordFirst = result1.next();
//                firstCommunityNum = recordFirst.get("partitionKey").asInt();
//            }catch (Exception e){
//                System.out.println("获取节点所属第一社区失败!!!" + e);
//                return null;
//            }
//            result = session.run(
//                    "MATCH (n:Keyword) WHERE n.partitionKey = "+ firstCommunityNum +
//                            " return n.name AS name,n.partitionKey AS partitionKey,n.partitionKey1 AS partitionKey1",
//                    parameters( "", "" ));//获取结果集
//        }
//
//
//        int count = 0;
//        int good = 0;
//        int bad = 0;
//        while ( result.hasNext() )
//        {
//            ++count;
//            Record record = result.next();
//            System.out.println("partitionKey: " + record.get("partitionKey") + " " +
//                    "partitionKey1: " + record.get("partitionKey1"));
////            Value key = record.get( "partitionKey" );
////            if (key.equals("NULL"))continue;
////            System.out.println(record.get( "name" ).asString() + " partitionKey " +
////                    record.get( "partitionKey" )+ " partitionKey1 " +
////                    record.get( "partitionKey1" ));
//            try {
//                ArrayList<Integer> templist = new ArrayList<Integer>();
//                templist.add(record.get("partitionKey").asInt());
//                templist.add(record.get("partitionKey1").asInt());
//                data.put(record.get( "name" ).asString(),templist);
//                ++good;
//            }catch (Exception e){
//                ++bad;
//                continue;
//            }
//        }
//
//        session.close();
//        driver.close();
//        System.out.println("总数： "+count);
//        System.out.println("bad总数： "+bad);
//        System.out.println("good总数： "+good);
//        return data;
//    }
//
//
//    //返回数据格式    （社区id为键,该社区成员的成员社区为键，对应的字符串为值）
//    public HashMap<Integer,HashMap<Integer,ArrayList<String>>> getCommunity(HashMap<String,ArrayList<Integer>> data){
////        HashMap<Integer,ArrayList<String>> community =new HashMap<Integer,ArrayList<String>>();//社区id为键 社区成员为值
//        HashMap<Integer,HashMap<Integer,ArrayList<String>>> community =
//                new HashMap<Integer,HashMap<Integer,ArrayList<String>>>();//社区id为键 该社区成员的成员社区为键，对应的字符串为值
////        ArrayList<String> list = new ArrayList<String>();
//        int sum = 0;
//        System.out.println(data.size());
//        for (String string: data.keySet()){
////            System.out.println(++sum);
//            if (community.containsKey(data.get(string).get(0))){
//                if (community.get(data.get(string).get(0)).containsKey(data.get(string).get(1))){
//                    community.get(data.get(string).get(0)).get(data.get(string).get(1)).add(string);
//                }else {
//                    ArrayList<String> tempList = new ArrayList<String>();
//                    tempList.add(string);
//                    community.get(data.get(string).get(0)).put(data.get(string).get(1),tempList);
//                }
//            }else {
//                HashMap<Integer,ArrayList<String>> soncommunity =new HashMap<Integer,ArrayList<String>>();
//                if(soncommunity.containsKey(data.get(string).get(1))){
//                    soncommunity.get(data.get(string).get(1)).add(string);
//                }else {
//                    ArrayList<String> tempList = new ArrayList<String>();
//                    tempList.add(string);
//                    soncommunity.put(data.get(string).get(1), tempList);
//                }
//                community.put(data.get(string).get(0),soncommunity);
//            }
//        }
//        for (int i : community.keySet()){
//            int count = 0;
//            System.out.println("partitionKey:  " + i);
//            for (int j : community.get(i).keySet()){
//                System.out.println("   partitionKey1:  " + j  + "  count: "+community.get(i).get(j).size()+" " + community.get(i).get(j));
//                count += community.get(i).get(j).size();
//            }
//            System.out.println("  count " + count);
//        }
//        System.out.println(community.size());
//        return community;
//    }
//
//    public void writeJson(HashMap<Integer,HashMap<Integer,ArrayList<String>>> community){
//        JSONObject obj = new JSONObject();
//        JSONArray objArray = new JSONArray();
//        obj.put("name","community");
//        for (int out : community.keySet()){
//            int count = 0;
//            for (int temp : community.get(out).keySet()){
//                count += community.get(out).get(temp).size();
//            }
//            if (count < 20)
//                continue;
//            JSONObject obj1 = new JSONObject();
////            obj1.put("name",community.get(out).get(0));
//            boolean flag = true;
//            JSONArray objArray1 = new JSONArray();
//            for (int in : community.get(out).keySet()){
//                if (community.get(out).get(in).size() < 5){
////                    continue;
//                    for (String string : community.get(out).get(in)){
//                        JSONObject objtemp = new JSONObject();
//                        objtemp.put("name",string);
//                        objtemp.put("size",(int)(Math.random()*300));
//                        objArray1.put(objtemp);
//                    }
//                    continue;
//                }
//                if(flag){
////                    obj1.put("name",community.get(out).get(in).get(0));
//                    flag = false;
//                }
//                JSONObject obj2 = new JSONObject();
//                obj2.put("name",community.get(out).get(in).get(0));
//                JSONArray objArray2 = new JSONArray();
//                for (String string : community.get(out).get(in)){
//                    JSONObject obj3 = new JSONObject();
//                    obj3.put("name",string);
//                    obj3.put("size",(int)(Math.random()*300));
//                    objArray2.put(obj3);
//                }
//                obj2.put("children",objArray2);
//                objArray1.put(obj2);
//            }
//            obj1.put("children",objArray1);
////            obj1.put("name",objArray1.get(0).)
//            objArray.put(obj1);
//        }
//        obj.put("children",objArray);
//        System.out.println(obj.toString());
//        boolean flag = createJsonFile(obj.toString(),"E:\\communitydata","Communityjson1");
//        System.out.println(flag);
//    }
//    public boolean createJsonFile(String jsonString, String filePath, String fileName) {
//        // 标记文件生成是否成功
//        boolean flag = true;
//
//        // 拼接文件完整路径
//        String fullPath = filePath + File.separator + fileName + ".json";
//
//        // 生成json格式文件
//        try {
//            // 保证创建一个新文件
//            File file = new File(fullPath);
//            if (!file.getParentFile().exists()) { // 如果父目录不存在，创建父目录
//                file.getParentFile().mkdirs();
//            }
//            if (file.exists()) { // 如果已存在,删除旧文件
//                file.delete();
//            }
//            file.createNewFile();
//
//
//            // 将格式化后的字符串写入文件
//            Writer write = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
//            write.write(jsonString);
//            write.flush();
//            write.close();
//        } catch (Exception e) {
//            flag = false;
//            e.printStackTrace();
//        }
//
//        // 返回是否成功的标记
//        return flag;
//    }
//}
