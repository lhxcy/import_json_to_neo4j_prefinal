package com.dataimport;

import org.json.JSONObject;

import javax.annotation.processing.FilerException;
import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * @author zhaobing
 */
public class UtilRead {
    private static String basePath = "/home/zhzy/Downloads/xcy/Main_Tech/tech_analysis/py/model/";
    //读文件，变成流文件
    public BufferedReader getBufferedReaderForJson(String file) throws FileNotFoundException {
        File inFilePath = new File(file);
        FileInputStream fileInputStream = new FileInputStream(inFilePath);
        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
        BufferedReader read = new BufferedReader(inputStreamReader);
        return read;
    }

    /**
     * 读取institutionData对象
     * 键为论文uid，值为发表该论文的机构
     * @return
     */
    public static HashMap<String,LinkedList<String>> readPaperInstitution(String filePath) {
//        String filePath = "/home/zhzy/Downloads/xcy/Main_Tech/tech_analysis/py/model/paperInstitutionForImport.csv";
//        String prefilePath = "/home/zhzy/Downloads/xcy/tech_analysis/py/model/paperInstitutionForImport.csv";
//        String filePath = "E:\\tech_analysis_my\\tech_analysis\\py\\model\\paperInstitutionForImport.csv";
        HashMap<String,LinkedList<String>> institutionData = new HashMap<>();
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(filePath));
            String line = "";
            while (true) {
                line = bufferedReader.readLine();
                if (line == null || line.trim().equals("")) break;
                String[] stringSplit = line.split(",");
                LinkedList<String> tempList = new LinkedList<>();
                for (int i = 1; i < stringSplit.length; ++i){
                    if ("".equals(stringSplit[i].trim())) continue;
                    tempList.add(stringSplit[i].trim());
                }
                institutionData.put(stringSplit[0],tempList);
            }
            System.out.println("读取institutionData对象成功");
        }catch (Exception e){
            System.out.println("读取institutionData对象失败");
        }
        return institutionData;
    }


    /**
     * 读取expertData对象
     * 键为论文uid，值为写该论文的作者
     * @return
     */
    public static HashMap<String,LinkedList<String>> readExpertData(String filePath) {
//        String filePath = "/home/zhzy/Downloads/xcy/Main_Tech/tech_analysis/py/model/expertCooperateForImport.csv";
//        String prefilePath = "/home/zhzy/Downloads/xcy/tech_analysis/py/model/expertCooperateForImport.csv";
//        String filePath = "E:\\tech_analysis_my\\tech_analysis\\py\\model\\expertCooperateForImport.csv";
        HashMap<String,LinkedList<String>> expertData = new HashMap<>();
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(filePath));
            String line = "";
            while (true) {
                line = bufferedReader.readLine();
                if (line == null || line.trim().equals("")) break;
                String[] stringSplit = line.split(",");
                LinkedList<String> tempList = new LinkedList<>();
                for (int i = 1; i < stringSplit.length; ++i){
                    if ("".equals(stringSplit[i].trim())) continue;
                    tempList.add(stringSplit[i].trim());
                }
                expertData.put(stringSplit[0],tempList);
            }
            System.out.println("读取expertData对象成功");
        }catch (Exception e){
            System.out.println("读取expertData对象失败");
        }
        return expertData;
    }

    /**
     * 读取enterpriseAndExpert对象
     * 键为机构名字， 值为属于该机构的专家的名字列表
     * @return
     */
    public static HashMap<String,LinkedList<String>> readEnterpriseAndExpert(String filePath) {
//        String filePath = "/home/zhzy/Downloads/xcy/Main_Tech/tech_analysis/py/model/enterpriseAndExpertForImport.csv";
//        String prefilePath = "/home/zhzy/Downloads/xcy/tech_analysis/py/model/enterpriseAndExpertForImport.csv";
//        String filePath = "E:\\tech_analysis_my\\tech_analysis\\py\\model\\enterpriseAndExpertForImport.csv";
        HashMap<String,LinkedList<String>> enterpriseAndExpert = new HashMap<>();
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(filePath));
            String line = "";
            while (true) {
                line = bufferedReader.readLine();
                if (line == null || line.trim().equals("")) break;
                String[] stringSplit = line.split(",");
                LinkedList<String> tempList = new LinkedList<>();
                for (int i = 1; i < stringSplit.length; ++i){
                    if ("".equals(stringSplit[i].trim())) continue;
                    tempList.add(stringSplit[i].trim());
                }
                enterpriseAndExpert.put(stringSplit[0],tempList);
            }
            System.out.println("读取enterpriseAndExpert对象成功");
        }catch (Exception e){
            System.out.println("读取enterpriseAndExpert对象失败");
        }
        return enterpriseAndExpert;
    }

    /**
     * 读取name2Enterprise对象
     * 键为专家，{"sql_id":"1","jigou":"中电科"}
     * @return
     */
    public static HashMap<String,JSONObject> readName2Enterprise(String filePath) {
//        String filePath = "/home/zhzy/Downloads/xcy/Main_Tech/tech_analysis/py/model/name2EnterpriseForImport.csv";
//        String prefilePath = "/home/zhzy/Downloads/xcy/tech_analysis/py/model/name2EnterpriseForImport.csv";
//        String filePath = "E:\\tech_analysis_my\\tech_analysis\\py\\model\\name2EnterpriseForImport.csv";
        HashMap<String,JSONObject> name2Enterprise = new HashMap<>();
        BufferedReader bufferedReader = null;
        try {
//            filePath    author_sql_id,专家,机构
            bufferedReader = new BufferedReader(new FileReader(filePath));
            String line = "";
            while (true) {
                line = bufferedReader.readLine();
                if (line == null || line.trim().equals("")) break;
                String[] stringSplit = line.split("@@@");
                if ("".equals(stringSplit[1].trim()) || stringSplit.length < 3) continue;
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("author_sql_id",stringSplit[0]);
//                jsonObject.put("institution_sql_id",stringSplit[2]);
                jsonObject.put("jigou",stringSplit[2]);
                name2Enterprise.put(stringSplit[1],jsonObject);
            }
            System.out.println("读取name2Enterprise对象成功");
        }catch (Exception e){
            System.out.println("读取name2Enterprise对象失败");
        }
        return name2Enterprise;
    }



    public static HashMap<String, String> readCode2FatherCodeObject(String filePath){
        FileInputStream freader;
        HashMap<String,String> code2fathercode = new HashMap<>();
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(filePath));
            String line = "";
            while (true) {
                line = bufferedReader.readLine();
                if (line == null || line.trim().equals("")) break;
                String[] stringSplit = line.split(",");
                if ("".equals(stringSplit[1].trim()) || stringSplit.length < 2) continue;
                code2fathercode.put(stringSplit[0],stringSplit[1]);
            }
            System.out.println("读取code2fathercode对象成功");
        }catch (Exception e){
            System.out.println("读取code2fathercode对象失败");
        }
        return code2fathercode;
    }


    public static HashMap<String, String> readCode2NameObject(String filePath){
        FileInputStream freader;
        HashMap<String,String> code2name = new HashMap<>();
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(filePath));
            String line = "";
            while (true) {
                line = bufferedReader.readLine();
                if (line == null || line.trim().equals("")) break;
                String[] stringSplit = line.split(",");
                if ("".equals(stringSplit[1].trim()) || stringSplit.length < 2) continue;
                code2name.put(stringSplit[0],stringSplit[1]);
            }
            System.out.println("读取code2name对象成功");
        }catch (Exception e){
            System.out.println("读取code2name对象失败");
        }
        return code2name;
    }

}
