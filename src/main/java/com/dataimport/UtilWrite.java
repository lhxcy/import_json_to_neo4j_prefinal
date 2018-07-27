package com.dataimport;

import com.dataimport.entity.*;
import org.neo4j.cypher.internal.frontend.v2_3.ast.functions.Str;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author zhaobing
 */
public class UtilWrite {

    public void WriteAuthorFile(HashMap<String, AuthorEntity> hashmap,String filePath) {
//        String filePath = "D:/Entity/authorEntity.dat";
//        String filePath = "/home/zhzy/Documents/data/authorEntity.dat";
//        String filePath = "F:/authorEntity.dat";
//        String filePath = "E:/PycharmCode/authorEntity.dat";
//        String filePath = "/home/zhzy/Downloads/xcy/Main_Tech/authorEntity.dat";
        try {
            FileOutputStream outStream = new FileOutputStream(filePath);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                    outStream);
            objectOutputStream.writeObject(hashmap);
            objectOutputStream.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
//    public void WritePaperFile(HashMap<String, PaperEntity> hashmap) {
////        String filePath = "D:/Entity/paperEntity.dat";
////        String filePath = "/home/zhzy/Documents/data/paperEntity.dat";
//        String filePath = "F:/paperEntity.dat";
//        try {
//            FileOutputStream outStream = new FileOutputStream(filePath);
//            ObjectOutputStream objectOutputStream = new ObjectOutputStream(
//                    outStream);
//            objectOutputStream.writeObject(hashmap);
//            objectOutputStream.close();
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//    }
    public  void WriteInstitutionFile(HashMap<String, InstitutionEntity> hashmap,String filePath) {
//        String filePath = "D:/Entity/institutionEntity.dat";
//        String filePath = "/home/zhzy/Documents/data/institutionEntity.dat";
//        String filePath = "F:/institutionEntity.dat";
//        String filePath = "E:/PycharmCode/institutionEntity.dat";
//        String filePath = "/home/zhzy/Downloads/xcy/Main_Tech/institutionEntity.dat";
        try {
            FileOutputStream outStream = new FileOutputStream(filePath);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                    outStream);
            objectOutputStream.writeObject(hashmap);
            objectOutputStream.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }


//    public void WriteKeywordFile(HashMap<String, KeywordEntity> hashmap) {
////        String filePath = "D:/Entity/keywordEntity.dat";
////        String filePath = "/home/zhzy/Documents/data/keywordEntity.dat";
////        String filePath = "F:/keywordEntity.dat";
////        String filePath = "E:/PycharmCode/Test/keywordEntity.dat";
////        String filePath = "E:/PycharmCode/keywordEntity.dat";
//        String filePath = "/home/zhzy/Downloads/xcy/Main_Tech/keywordEntity.dat";
//        try {
////            FileOutputStream out = new FileOutputStream(filePath);
////            BufferedWriter bufw = new BufferedWriter(new OutputStreamWriter(out));
////            bufw.write(hashmap.toString());
////            bufw.close();
//            FileOutputStream outStream = new FileOutputStream(filePath);
//            ObjectOutputStream objectOutputStream = new ObjectOutputStream(
//                    outStream);
//            objectOutputStream.writeObject(hashmap);
//            objectOutputStream.close();
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//    }
//    public void WriteJournalFile(HashMap<String, JournalEntity> hashmap) {
////        String filePath = "D:/Entity/journalEntity.dat";
////        String filePath = "/home/zhzy/Documents/data/journalEntity.dat";
//        String filePath = "F:/journalEntity.dat";
//        try {
//            FileOutputStream outStream = new FileOutputStream(filePath);
//            ObjectOutputStream objectOutputStream = new ObjectOutputStream(
//                    outStream);
//            objectOutputStream.writeObject(hashmap);
//            objectOutputStream.close();
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//    }
    public  void WriteRelationFile(HashMap<String, RelationshipEntity> hashmap,String filePath) {
//        String filePath = "D:/Entity/relationshipEntity.dat";
//        String filePath = "/home/zhzy/Documents/data/relationshipEntity.dat";
//        String filePath = "/home/zhzy/Documents/data/relationshipEntity.dat";
//        String filePath = "F:/relationshipEntity.dat";
//        String filePath = "E:/PycharmCode/Test/relationshipEntity.dat";
//        String filePath = "E:/PycharmCode/relationshipEntity.dat";
//        String filePath = "/home/zhzy/Downloads/xcy/Main_Tech/relationshipEntity.dat";
        try {
            FileOutputStream outStream = new FileOutputStream(filePath);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                    outStream);
            objectOutputStream.writeObject(hashmap);
            objectOutputStream.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void WriteKeywordTimesFile(HashMap<String, Long> hashmap) {
//        String filePath = "E:/PycharmCode/Test/keywordTimes.dat";
        String filePath = "E:/PycharmCode/keywordTimes.dat";
//        for (String str : hashmap.keySet()){
//            System.out.println(str + "  " + hashmap.get(str));
//        }
        try {
            FileWriter fw = null;
            fw = new FileWriter(filePath);
            for (String str : hashmap.keySet()){
                fw.write(str + "\t" + hashmap.get(str));
                fw.write("\n");
            }
            fw.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
