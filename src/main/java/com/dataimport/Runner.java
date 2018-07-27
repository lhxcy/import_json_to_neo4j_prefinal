package com.dataimport;


//import org.neo4j.cypher.internal.frontend.v2_3.ast.functions.Str;

import java.io.File;
import java.io.IOException;

/**
 * @author zhaobing
 */
public class Runner {


        public static void main(String[] args) {
            String basePath = args[0];
//            System.err.println(basePath);
            GenerateHashTable generateHashTable = new GenerateHashTable();
            System.err.println("开始处理数据产生hashtable");
            generateHashTable.generate_my(basePath);
            System.err.println("hashtable构建成功");
//
            System.err.println("start");
            String filePath = args[1];
            System.out.println(filePath);
////            File dbPath = new File("/home/zhzy/Downloads/xcy/Main_Tech/paperData2");
            File dbPath = new File(filePath);
//
            try {
                System.err.println("构建EdgeAndNodeImport对象");
                EdgeAndNodeImport edgeAndNodeImport = new EdgeAndNodeImport(dbPath);
                System.err.println("ReadHash");
                edgeAndNodeImport.ReadHash(basePath);
                System.err.println("importNode");
                edgeAndNodeImport.importNode();
                System.err.println("importRelationship");
                edgeAndNodeImport.importRelationship();
                System.err.println("flushIndex");
                edgeAndNodeImport.flushIndex();
                System.err.println("shutDownIndex");
                edgeAndNodeImport.shutDownIndex();
                System.err.println("shutDownNeo4j");
                edgeAndNodeImport.shutDownNeo4j();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.err.println("end");
        }
}
