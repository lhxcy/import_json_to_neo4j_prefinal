package com.dataimport;

import com.dataimport.entity.*;
import com.dataimport.generic.RelationshipTypes;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * @author zhaobing
 */
public class GenerateHashTable {

    private UtilRead utilRead = new UtilRead();
    private BufferedReader in = null;
    private ConvertToNode convertToNode = new ConvertToNode();
    private UtilWrite utilWrite = new UtilWrite();

    public List<String> getKeyWords(String line){//获取关键字列表
        List<String> keywords = new ArrayList<String>();
//        String file = "E:\\PycharmCode\\Test\\paper.dat";

//        System.out.println(line);
        String[] strings = line.trim().split(",");
        if (strings.length > 1){
            for (int i = 1; i < strings.length; i++)
                keywords.add(strings[i]);
        }
//        for (String str : strings)
//            keywords.add(str);
//        System.out.println(keywords);

        return keywords;
    }

    public static JSONObject readJsonObject(String filePath){
        JSONObject jsonObject = new JSONObject();
        BufferedReader bufferedReader = null;
        try {
            //id，机构名，level，provinceid,province,city,cityid，
            bufferedReader = new BufferedReader(new FileReader(filePath));
            String line = "";
            while (true) {
                line = bufferedReader.readLine();
                if (line == null || line.trim().equals("")) break;
                String[] stringSplit = line.split("@@@");
                if ("".equals(stringSplit[1].trim()) || stringSplit.length < 2) continue;
                JSONObject tempJsonObject = new JSONObject();
                tempJsonObject.put("sql_id",stringSplit[0]);
                tempJsonObject.put("level",stringSplit[2]);
                tempJsonObject.put("provinceid",stringSplit[3]);
                tempJsonObject.put("province",stringSplit[4]);
                tempJsonObject.put("city",stringSplit[5]);
                tempJsonObject.put("cityid",stringSplit[6]);
                jsonObject.put(stringSplit[1],tempJsonObject);
            }
            System.out.println("读取institutionDetailInfo对象成功");
        }catch (Exception e){
            System.out.println("读取institutionDetailInfo对象失败");
            e.printStackTrace();
        }
        return jsonObject;
    }
    public void generate_my(String basePath){
//        String expertfilePath = "/home/zhzy/Downloads/xcy/Main_Tech/tech_analysis/py/model/expertCooperateForImport.csv";
//        String enterprisefilePath = "/home/zhzy/Downloads/xcy/Main_Tech/tech_analysis/py/model/enterpriseAndExpertForImport.csv";
//        String name2enterfilePath = "/home/zhzy/Downloads/xcy/Main_Tech/tech_analysis/py/model/name2EnterpriseForImport.csv";
//        String paperInstitutionfilePath = "/home/zhzy/Downloads/xcy/Main_Tech/tech_analysis/py/model/paperInstitutionForImport.csv";
        String expertfilePath = basePath+File.separator+"py"+File.separator+"model"+File.separator+"expertCooperateForImport.csv";
        String enterprisefilePath = basePath+File.separator+"py"+File.separator+"model"+File.separator+"enterpriseAndExpertForImport.csv";
        String name2enterfilePath = basePath+File.separator+"py"+File.separator+"model"+File.separator+"name2EnterpriseForImport.csv";
        String paperInstitutionfilePath = basePath+File.separator+"py"+File.separator+"model"+File.separator+"paperInstitutionForImport.csv";
        String code2fathercodefilePath = basePath+File.separator+"py"+File.separator+"model"+ File.separator+"code2fathercodeForImport.csv";
        String code2namefilePath = basePath+File.separator+"py"+File.separator+"model"+File.separator+"code2nameForImport.csv";
        String institutionDetailInfo = basePath+File.separator+"py"+File.separator+"model"+File.separator+"institutionDetailInfoForImport.csv";
        //载入数据库数据
        //论文的合著专家
//        HashMap<String,LinkedList<String>> expertCooperate = UtilRead.readExpertData(expertfilePath);
//        //企业下对应的专家
//        HashMap<String,LinkedList<String>> enterpriseAndExpert = UtilRead.readEnterpriseAndExpert(enterprisefilePath);
//        //专家和所属企业
        HashMap<String,JSONObject> name2Enterprise = UtilRead.readName2Enterprise(name2enterfilePath);
//        //论文的合作机构
//        HashMap<String,LinkedList<String>> paperInstitution = UtilRead.readPaperInstitution(paperInstitutionfilePath);
//        /////////***************************
        HashMap<String,String> code2fathercode = UtilRead.readCode2FatherCodeObject(code2fathercodefilePath);
        HashMap<String,String> code2name = UtilRead.readCode2FatherCodeObject(code2namefilePath);
//        System.out.println("expertCooperate size: "+expertCooperate.size());
//        System.out.println("enterpriseAndExpert size: "+enterpriseAndExpert.size());
//        System.out.println("name2Enterprise size: "+name2Enterprise.size());
//        System.out.println("paperInstitution size: "+paperInstitution.size());
//        System.out.println("code2fathercode size: "+code2fathercode.size());
//        System.out.println("code2name size: "+code2name.size());

        //存储Authors
        HashMap<String, AuthorEntity> authors = new HashMap<String, AuthorEntity>();

        //机构
        HashMap<String, InstitutionEntity> institutions = new HashMap<String, InstitutionEntity>();

        //关系
        HashMap<String, RelationshipEntity> relationships = new HashMap<String, RelationshipEntity>();
        JSONObject jsonObject = GenerateHashTable.readJsonObject(institutionDetailInfo);//读取企业json
        long nodeId = 0l;
        long relationshipId = 0l;
        /*构建作者关系*/
        System.out.println("开始构建作者关系");
        BufferedReader bufferedReader = null;
        try {
            //expertfilePath uid,sql_id,时间，作者，作者。。。，作者
            bufferedReader = new BufferedReader(new FileReader(expertfilePath));
            String line = "";
            while (true) {
                line = bufferedReader.readLine();
                if (line == null || line.trim().equals("")) break;
                String[] stringSplit = line.split(",");
                if (stringSplit.length <= 3 || "".equals(stringSplit[0]) || stringSplit[0] == null) continue;
                List<Long> authorIdList = new ArrayList<Long>();
                for (int i = 3;i < stringSplit.length; ++i){
                    if (!name2Enterprise.containsKey(stringSplit[i])) continue;
                    JSONObject tempJson = name2Enterprise.get(stringSplit[i]);
                    String author_institution = name2Enterprise.get(stringSplit[i]).getString("jigou");
                    if ("".equals(author_institution) || author_institution == null)
                        author_institution = "null";
                    if (!authors.containsKey(stringSplit[i])){
                        AuthorEntity authorEntity = new AuthorEntity(stringSplit[i],author_institution,
                                nodeId,stringSplit[1]);
                        authorIdList.add(nodeId);
                        ++nodeId;
                        authors.put(stringSplit[i],authorEntity);
                    }else {
                        authorIdList.add(authors.get(stringSplit[i]).getAuthor_neo4j_id());
                    }
                }
                //建立作者合作关系
//                System.out.println("开始建立作者合作关系");
                if (authorIdList.size() < 2) continue;
                for (int i = 0; i < authorIdList.size(); ++i){
                    Long authorId1 = authorIdList.get(i);
                    for (int j = i+1; j < authorIdList.size(); ++j){
                        Long authorId2 = authorIdList.get(j);
                        String relationshipHashKey1 = authorId1.toString()+"_"+authorId2.toString();
                        String relationshipHashKey2 = authorId2.toString()+"_"+authorId1.toString();
                        if (!relationships.containsKey(relationshipHashKey1) && !relationships.containsKey(relationshipHashKey2)){
                            RelationshipEntity relationshipEntity = new RelationshipEntity(authorId1,authorId2,stringSplit[2],1L,RelationshipTypes.Cooperate_Author);
                            relationships.put(relationshipHashKey1,relationshipEntity);
                        }else if (relationships.containsKey(relationshipHashKey1)){
                            relationships.get(relationshipHashKey1).setTimes(relationships.get(relationshipHashKey1).getTimes()+1L);
                        }else if (relationships.containsKey(relationshipHashKey2)){
                            relationships.get(relationshipHashKey2).setTimes(relationships.get(relationshipHashKey2).getTimes()+1L);
                        }
                    }
                }
            }
            System.out.println("处理expertData对象成功");
        }catch (Exception e){
            System.out.println("处理expertData对象失败");
            e.printStackTrace();
        }

        /*构建机构关系*/
        System.out.println("开始构建机构关系");
        try {
            //paperInstitutionfilePath  uid,论文时间，机构名，，，机构名，
            //另外还有一个表institutionDetailInfoForImport存储 id，机构名，level，provinceid,province,city,cityid，
            // 用这个文件构建一个jsonobject
            // {“机构名”：{“level”：“1”，“provinceid”：“3”，“province”：“北京”，“city”：“北京”，“cityid”：“3”,"sql_id":"2","neo4j_id":"4"}}
            bufferedReader = new BufferedReader(new FileReader(paperInstitutionfilePath));
            String line = "";
            while (true) {
                line = bufferedReader.readLine();
                if (line == null || line.trim().equals("")) break;
                String[] stringSplit = line.split("@@@");
                if (stringSplit.length <= 2 || "".equals(stringSplit[0]) || stringSplit[0] == null) continue;
                //存储要建立关系的institution实体Id
                List<Long> institutionIdList = new ArrayList<Long>();
                for (int i = 2; i < stringSplit.length; ++i){
//                    JSONObject institutionJSONObject = null;
                    if (!jsonObject.has(stringSplit[i])) continue;
                    JSONObject institutionJSONObject = jsonObject.getJSONObject(stringSplit[i]);
                    if (institutionJSONObject ==null)continue;
                    if (!institutions.containsKey(stringSplit[i])){
                        InstitutionEntity institutionEntity = new InstitutionEntity(stringSplit[i],institutionJSONObject.getString("level"),
                                institutionJSONObject.getString("province"),institutionJSONObject.getString("city"),
                                institutionJSONObject.getString("provinceid"),institutionJSONObject.getString("cityid"),
                                institutionJSONObject.getString("sql_id"),nodeId);
                        institutionIdList.add(nodeId);
                        ++nodeId;
                        institutions.put(stringSplit[i],institutionEntity);
                    }else {
                        institutionIdList.add(institutions.get(stringSplit[i]).getInstitution_neo4j_id());
                    }
                }
                //建立institutions之间的关系 权重为合作次数
//                System.out.println("开始构建机构合作关系");
                for (int i = 0; i < institutionIdList.size(); ++i){
                    Long institutionId1 = institutionIdList.get(i);
                    for (int j = i + 1; j < institutionIdList.size(); j++) {
                        Long institutionId2 = institutionIdList.get(j);
                        String relationshipHashKey1 = institutionId1.toString() + "_" + institutionId2.toString();
                        String relationshipHashKey2 = institutionId2.toString() + "_" + institutionId1.toString();
                        if (!relationships.containsKey(relationshipHashKey1) && !relationships.containsKey(relationshipHashKey2)){
                            RelationshipEntity relationshipEntity = new RelationshipEntity(institutionId1,institutionId2,stringSplit[1],1L,RelationshipTypes.Cooperate_Institution);
                            relationships.put(relationshipHashKey1,relationshipEntity);
                        }else if (relationships.containsKey(relationshipHashKey1)){
                            relationships.get(relationshipHashKey1).setTimes(relationships.get(relationshipHashKey1).getTimes()+1L);
                        }else if (relationships.containsKey(relationshipHashKey2)){
                            relationships.get(relationshipHashKey2).setTimes(relationships.get(relationshipHashKey2).getTimes()+1L);
                        }
                    }
                }
            }
            System.out.println("处理institutionData对象成功");
        }catch (Exception e){
            System.out.println("处理institutionData对象失败");
            e.printStackTrace();
        }

         /*构建作者和机构的关系*/
        System.out.println("开始构建作者的机构关系");
        try {
            //enterprisefilePath 企业名字，企业内的专家，，，企业内的专家
            bufferedReader = new BufferedReader(new FileReader(enterprisefilePath));
            String line = "";
            while (true) {
                line = bufferedReader.readLine();
                if (line == null || line.trim().equals("")) break;
                String[] stringSplit = line.split("@@@");
                if (stringSplit.length <= 1 || "".equals(stringSplit[0]) || stringSplit[0] == null) continue;
                for (int i = 1; i < stringSplit.length; ++i){
                    String hashKey = stringSplit[0]+stringSplit[i];
                    if (!relationships.containsKey(hashKey)){
                        if (institutions.containsKey(stringSplit[0]) && authors.containsKey(stringSplit[i])){
                            RelationshipEntity relationshipEntity =
                                    new RelationshipEntity(institutions.get(stringSplit[0]).getInstitution_neo4j_id(),authors.get(stringSplit[1]).getAuthor_neo4j_id(),"all time",1L,RelationshipTypes.Is_Work_For);
                            relationships.put(hashKey,relationshipEntity);
                        }else if (institutions.containsKey(stringSplit[0]) && !authors.containsKey(stringSplit[1])){
                            String authorInstitution = name2Enterprise.get(stringSplit[1]).getString("jigou");
                            if ("".equals(authorInstitution) || authorInstitution == null)
                                authorInstitution = "null";
                            AuthorEntity authorEntity = new AuthorEntity(stringSplit[1],authorInstitution,nodeId,name2Enterprise.get(stringSplit[1]).getString("author_sql_id"));
                            authors.put(stringSplit[1],authorEntity);
                            ++nodeId;
                            RelationshipEntity relationshipEntity =
                                    new RelationshipEntity(authors.get(stringSplit[1]).getAuthor_neo4j_id(),institutions.get(stringSplit[0]).getInstitution_neo4j_id(),"all time",1L,RelationshipTypes.Is_Work_For);
                            relationships.put(hashKey,relationshipEntity);
                        }else if (!institutions.containsKey(stringSplit[0]) && authors.containsKey(stringSplit[1])){
                            if (!jsonObject.has(stringSplit[i])) continue;
                            JSONObject institutionJSONObject = jsonObject.getJSONObject(stringSplit[i]);
                            InstitutionEntity institutionEntity = new InstitutionEntity(stringSplit[0],institutionJSONObject.getString("level"),
                                    institutionJSONObject.getString("province"),institutionJSONObject.getString("city"),
                                    institutionJSONObject.getString("provinceid"),institutionJSONObject.getString("cityid"),
                                    institutionJSONObject.getString("sql_id"),nodeId);
                            institutions.put(stringSplit[0],institutionEntity);
                            ++nodeId;
                            RelationshipEntity relationshipEntity =
                                    new RelationshipEntity(authors.get(stringSplit[1]).getAuthor_neo4j_id(),institutions.get(stringSplit[0]).getInstitution_neo4j_id(),"all time",1L,RelationshipTypes.Is_Work_For);
                            relationships.put(hashKey,relationshipEntity);
                        }else if (!institutions.containsKey(stringSplit[0]) && !authors.containsKey(stringSplit[1])){
                            String authorInstitution = name2Enterprise.get(stringSplit[1]).getString("jigou");
                            if ("".equals(authorInstitution) || authorInstitution == null)
                                authorInstitution = "null";
                            AuthorEntity authorEntity = new AuthorEntity(stringSplit[1],authorInstitution,nodeId,name2Enterprise.get(stringSplit[1]).getString("author_sql_id"));
                            authors.put(stringSplit[1],authorEntity);
                            ++nodeId;
                            if (!jsonObject.has(stringSplit[i]))continue;
                            JSONObject institutionJSONObject = jsonObject.getJSONObject(stringSplit[i]);
                            InstitutionEntity institutionEntity = new InstitutionEntity(stringSplit[0],institutionJSONObject.getString("level"),
                                    institutionJSONObject.getString("province"),institutionJSONObject.getString("city"),
                                    institutionJSONObject.getString("provinceid"),institutionJSONObject.getString("cityid"),
                                    institutionJSONObject.getString("sql_id"),nodeId);
                            institutions.put(stringSplit[0],institutionEntity);
                            ++nodeId;
                            RelationshipEntity relationshipEntity =
                                    new RelationshipEntity(authors.get(stringSplit[1]).getAuthor_neo4j_id(),institutions.get(stringSplit[0]).getInstitution_neo4j_id(),"all time",1L,RelationshipTypes.Is_Work_For);
                            relationships.put(hashKey,relationshipEntity);
                        }
                    }
                }
            }
            System.out.println("处理enterpriseAndExpert对象成功");
        }catch (Exception e){
            System.out.println("处理enterpriseAndExpert对象失败");
        }
        /*开始构建企业上下级关系*/
        System.out.println("开始构建企业上下级关系");
        for (String code : code2fathercode.keySet()){
            if (code == null || "".equals(code) || code2fathercode.get(code) == null || "".equals(code2fathercode.get(code))) continue;
            if (institutions.get(code2name.get(code2fathercode.get(code))) != null && institutions.get(code2name.get(code)) != null){
                String hashKey = institutions.get(code2name.get(code2fathercode.get(code))).getInstitution_name()+institutions.get(code2name.get(code)).getInstitution_name()+"Is_Super_Of";
//                if (!jsonObject.has(code2name.get(code2fathercode.get(code)))) continue;
//                JSONObject institutionJSONObject = jsonObject.getJSONObject(code2name.get(code2fathercode.get(code)));
                RelationshipEntity relationshipEntity = new RelationshipEntity(institutions.get(code2name.get(code2fathercode.get(code))).getInstitution_neo4j_id(),
                        institutions.get(code2name.get(code)).getInstitution_neo4j_id(),"null",
                        1L,RelationshipTypes.Is_Super_Of);
                relationships.put(hashKey,relationshipEntity);
            }
        }

        System.out.println("开始写入hashtable数据");
        System.out.println(jsonObject.get("中国电子科技集团公司,第十三研究所"));
        System.out.println(institutions.get("中国电子科技集团公司,第十三研究所").getInstitution_name());
        System.out.println(institutions.get("中国电子科技集团公司,第十三研究所").getInstitution_sql_id());
        utilWrite.WriteAuthorFile(authors,basePath+File.separator+"py"+File.separator+"model"+File.separator+"authorEntity.dat");
        utilWrite.WriteInstitutionFile(institutions,basePath+File.separator+"py"+File.separator+"model"+File.separator+"institutionEntity.dat");
        utilWrite.WriteRelationFile(relationships,basePath+File.separator+"py"+File.separator+"model"+File.separator+"relationshipEntity.dat");
        System.out.println("写入hashtable数据完毕");
    }
}


