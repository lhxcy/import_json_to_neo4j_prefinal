//package com.dataimport;
////
//
////import net.sf.json.JSONArray;
////import net.sf.json.JSONObject;
//import org.json.*;
//
//import java.util.HashMap;
//import java.util.Scanner;
//
///**
// * Created by XCY on 2018/3/12.
// */
//public class jsonT {
//    public static void main(String[] args){
//        Scanner in = new Scanner(System.in);
//        String name = in.nextLine();
//        while (name != null){
//            System.out.println("found and print name: " + name);
//            name = in.nextLine();
//        }
////        new jsonT().testJson();
//    }
//    public void testJson(){
////        System.out.println("hello");
//        JSONObject obj = new JSONObject();
////        System.out.println("world");
//        obj.put("name","own");
//        obj.put("age",21);
//        JSONObject obj2 = new JSONObject();
//        obj2.put("1","张三");
//        obj2.put("2","李四");
//        obj.put("friend",obj2);
//        JSONObject obj3 = new JSONObject();
//        obj3.put("1","张三");
//        obj3.put("2","李四");
//        JSONArray array = new JSONArray();
//        array.put(obj2);
//        array.put(obj3);
////        array.put("唱歌");
////        array.put("跳舞");
//        obj.put("爱好",array);
//        System.out.println(obj.toString());
//        System.out.println(obj.get("爱好"));
//    }
//}
//
//class Main{
//    public static void main(String[] args){
//        Scanner sc = new Scanner(System.in);
//        String str = sc.nextLine();
//        while(str != null){
//            System.out.println(meth(str));
//            str = sc.nextLine();
//        }
//    }
//    public static int meth(String str){
//        char[] chs = str.toCharArray();
//        if(chs == null)return 0;
//        HashMap<Character,Integer> map = new HashMap<Character,Integer>();
//        for(char c : chs){
//            if(map.containsKey(c)) map.put(c,map.get(c)+1);
//            else map.put(c,1);
//        }
//        if(map.size() > 2)
//            return 0;
//        if(map.size() == 1)
//            return 1;
//        if(map.size() == 2)
//            return 2;
//        return 0;
//    }
//}