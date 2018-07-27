//package com.dataimport;
//
//import org.neo4j.driver.v1.AuthTokens;
//import org.neo4j.driver.v1.GraphDatabase;
//import org.neo4j.driver.v1.Session;
//import org.neo4j.driver.v1.StatementResult;
//
//import static org.neo4j.driver.v1.Values.parameters;
//
///**
// * Created by XCY on 2018/3/13.
// */
//public class CommunityChange {
////    private Neo4jOperations neo4jTemplate;
//    public void generateCommunity(){
//        org.neo4j.driver.v1.Driver driver = GraphDatabase.driver( "bolt://localhost:7687", AuthTokens.basic( "neo4j", "123456" ));//配置驱动
//        Session session = driver.session();//建立连接
////        StatementResult result = session.run( "MATCH (n:Keyword) RETURN n LIMIT 25");//获取结果集
//        session.run(
//                "CALL apoc.algo.community(25,['Keyword'],'partitionKey','similar','OUTGOING','weight',10000)",
//                parameters( "" , "" ));//获取结果集
//
////       就是用这句话  CALL apoc.algo.community(25,['Keyword'],'partitionKey1','similar','OUTGOING','partitionKey',1000)
////        neo4jTemplate.query("CALL apoc.algo.community(25,['Keyword'],'partitionKey','similar','OUTGOING','weight',10000)", mapUtil.map("",""));
//        //@Description("CALL apoc.algo.community(times,labels,partitionKey,type,direction,weightKey,batchSize) - simple label propagation kernel")
//    }
//}
