package com.dataimport;

import com.dataimport.entity.*;
import com.dataimport.generic.Labels;
import org.apache.lucene.analysis.Analyzer;
import org.neo4j.graphdb.index.IndexManager;
import org.neo4j.graphdb.schema.IndexCreator;
import org.neo4j.helpers.collection.MapUtil;
import org.neo4j.index.lucene.unsafe.batchinsert.LuceneBatchInserterIndexProvider;
import org.neo4j.storageengine.api.schema.IndexReader;
import org.neo4j.unsafe.batchinsert.BatchInserter;
import org.neo4j.unsafe.batchinsert.BatchInserterIndex;
import org.neo4j.unsafe.batchinsert.BatchInserterIndexProvider ;
import org.neo4j.unsafe.batchinsert.BatchInserters;
//import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhaobing
 */

public class EdgeAndNodeImport {

    HashMap<String, AuthorEntity> authors;
    HashMap<String, InstitutionEntity> institutions;
    //HashMap<String, JournalEntity> journals;
//    HashMap<String, KeywordEntity> keywords;
    //HashMap<String, PaperEntity> papers;
    HashMap<String, RelationshipEntity> relationships;

    BatchInserter inserter;
    BatchInserterIndexProvider indexProvider;
    BatchInserterIndex author_index, institution_index;
//    BatchInserterIndex  keyword_index;

    private final String AUTHOR_INDEX = "author";
    //private final String PAPER_INDEX = "paper";
    private final String INSTITUTION_INDEX = "institution";
//    private final String KEYWORD_INDEX = "keyword";
    //private final String JOURNAL_INDEX = "journal";

    public EdgeAndNodeImport(File filePath) throws Exception{
        InitializeInserter(filePath);
    }

    public void ReadHash(String basePath) {
        FileInputStream fAuthor;
        FileInputStream fInstitution;
        FileInputStream fRelationship;
        authors = new HashMap<String, AuthorEntity>();
        institutions = new HashMap<String, InstitutionEntity>();
        relationships = new HashMap<String, RelationshipEntity>();
        try {
            fAuthor = new FileInputStream(basePath+File.separator+"py"+File.separator+"model"+File.separator+"authorEntity.dat");////改
//            fAuthor = new FileInputStream("/home/zhzy/Downloads/xcy/Main_Tech/tech_analysis/py/model/authorEntity.dat");////改
            ObjectInputStream authorInputStream = new ObjectInputStream(fAuthor);
            authors = (HashMap<String, AuthorEntity>) authorInputStream.readObject();
            System.out.println(authors.size());

//            fInstitution = new FileInputStream("/home/zhzy/Downloads/xcy/Main_Tech/institutionEntity.dat");
            fInstitution = new FileInputStream(basePath+File.separator+"py"+File.separator+"model"+File.separator+"institutionEntity.dat");
//            fInstitution = new FileInputStream("/home/zhzy/Downloads/xcy/Main_Tech/tech_analysis/py/model/institutionEntity.dat");
            ObjectInputStream institutionInputStream = new ObjectInputStream(fInstitution);
            institutions = (HashMap<String, InstitutionEntity>) institutionInputStream.readObject();
            System.out.println(institutions.size());

//            fRelationship = new FileInputStream("/home/zhzy/Downloads/xcy/Main_Tech/tech_analysis/py/model/relationshipEntity.dat");
            fRelationship = new FileInputStream(basePath+File.separator+"py"+File.separator+"model"+File.separator+"relationshipEntity.dat");
            ObjectInputStream relationshipInputStream = new ObjectInputStream(fRelationship);
            relationships = (HashMap<String, RelationshipEntity>) relationshipInputStream.readObject();

            System.out.println(relationships.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//    public static Map<String, String> config() throws IOException{//配置
//        String filePath = "./src/resources/batchinserter.properties";
//        FileReader file = new FileReader(new File(filePath).getAbsoluteFile());
//        Map<String, String> config = MapUtil.load(file);//从输入流中读取属性列表（键和元素对）并返回
//        for(Map.Entry<String, String> map:config.entrySet()){
//            System.out.println(map.getKey() + " " + map.getValue());
//        }
//        return config;
//    }

    public void InitializeInserter(File filePath) throws Exception {


        System.out.println("开始构建空库");
        inserter = BatchInserters.inserter(filePath);//使用BatchInserter分批导入大量数据
        System.out.println("构建空库完毕");
//        IndexCreator a = inserter.createDeferredSchemaIndex(Labels.Author);
//        a.on("name").create();
        indexProvider = new LuceneBatchInserterIndexProvider(inserter);
//        indexProvider = new org.neo4j.index.lucene.unsafe.batchinsert.LuceneBatchInserterIndexProvider(inserter);
        //批插入过程中的索引使用BatchInserterIndexProvider提供的BatchInserterIndex完成

        Map<String, String> exactConfig = new HashMap<String, String>();
        exactConfig.put("type", "exact");
        Map<String, String> fulltextConfig = new HashMap<String, String>();
        fulltextConfig.put("type", "fulltext");//全文索引
        fulltextConfig.put("analyzer", "org.wltea.analyzer.lucene.IKAnalyzer");//分词器

        author_index = indexProvider.nodeIndex(AUTHOR_INDEX, fulltextConfig);
        author_index.setCacheCapacity("name", 10000);
        institution_index = indexProvider.nodeIndex(INSTITUTION_INDEX, fulltextConfig);
        //journal_index = indexProvider.nodeIndex(JOURNAL_INDEX, exactConfig);
//        keyword_index = indexProvider.nodeIndex(KEYWORD_INDEX, fulltextConfig);
//        keyword_index.setCacheCapacity("name", 10000);
        //paper_index = indexProvider.nodeIndex(PAPER_INDEX, fulltextConfig);
//        paper_index.setCacheCapacity("title", 10000);
    }

    public void importNode() {

        for (Map.Entry<String, AuthorEntity> map : authors.entrySet()) {//改动///////////////
            AuthorEntity authorEntity = map.getValue();
            Map<String, Object> author = new HashMap<String, Object>();
            author.put("name", authorEntity.getAuthor_name());
            author.put("enterprisename", authorEntity.getAuthor_institution());
            author.put("id", authorEntity.getAuthor_sql_id());
            inserter.createNode(authorEntity.getAuthor_neo4j_id(), author, Labels.Expert);
            author_index.add(authorEntity.getAuthor_neo4j_id(), MapUtil.map("name", authorEntity.getAuthor_name(),
                    "enterprisename", authorEntity.getAuthor_institution(),"id",authorEntity.getAuthor_sql_id()));
//            author_index.add(authorEntity.getId(), MapUtil.map());
        }

        for(Map.Entry<String, InstitutionEntity> map: institutions.entrySet()){//////////////
            InstitutionEntity institutionEntity = map.getValue();
            Map<String, Object> institution = new HashMap<String, Object>();
            institution.put("name", institutionEntity.getInstitution_name());
            institution.put("city", institutionEntity.getInstitution_city());
            institution.put("level", institutionEntity.getInstitution_level());
            institution.put("province", institutionEntity.getInstitution_province());
            institution.put("provinceid", institutionEntity.getInstitution_provinceid());
            institution.put("cityid", institutionEntity.getInstitution_cityid());
            institution.put("id", institutionEntity.getInstitution_sql_id());
            inserter.createNode(institutionEntity.getInstitution_neo4j_id(), institution, Labels.EnterpriseInfo);
            institution_index.add(institutionEntity.getInstitution_neo4j_id(),
                    MapUtil.map("name", institutionEntity.getInstitution_name(),
                                        "city",institutionEntity.getInstitution_city(),
                                        "level", institutionEntity.getInstitution_level(),
                                        "province", institutionEntity.getInstitution_province(),
                                        "provinceid", institutionEntity.getInstitution_provinceid(),
                                        "cityid", institutionEntity.getInstitution_cityid(),
                                        "id", institutionEntity.getInstitution_sql_id()));
        }
    }


    public void importRelationship(){
        for(Map.Entry<String, RelationshipEntity> map: relationships.entrySet()){
            RelationshipEntity relationshipEntity = map.getValue();
            Map<String, Object> relationship = new HashMap<String, Object>();
            relationship.put("cooperate_time", relationshipEntity.getCooperate_time());
            relationship.put("cooperate_times", relationshipEntity.getCooperate_times());
            inserter.createRelationship(relationshipEntity.getSource(), relationshipEntity.getTarget(),
                    relationshipEntity.getType(), relationship);
        }
    }

    public void flushIndex(){//改动
        author_index.flush();
        institution_index.flush();
    }
    public void shutDownIndex(){
        indexProvider.shutdown();
    }
    public void shutDownNeo4j(){
        inserter.shutdown();
    }

}
