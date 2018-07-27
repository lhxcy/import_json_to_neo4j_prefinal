package com.dataimport.entity;

import java.io.Serializable;

/**
 * @author zhaobing
 */
public class InstitutionEntity implements Serializable{
    private static final long serialVersionUID = 1269373329410167403l;
    private String institution_name;
    private String institution_level;
    private String institution_province;
    private String institution_city;
    private String institution_provinceid;
    private String institution_cityid;
    private String institution_sql_id;

    public String getInstitution_level() {
        return institution_level;
    }

    public String getInstitution_province() {
        return institution_province;
    }

    public String getInstitution_city() {
        return institution_city;
    }

    public String getInstitution_provinceid() {
        return institution_provinceid;
    }

    public String getInstitution_cityid() {
        return institution_cityid;
    }

    public String getInstitution_sql_id() {
        return institution_sql_id;
    }

    private Long institution_neo4j_id;

    public InstitutionEntity(String institution_name, String institution_level,
                             String institution_province, String institution_city,
                             String institution_provinceid, String institution_cityid,
                             String institution_sql_id, Long institution_neo4j_id) {
        this.institution_name = institution_name;
        this.institution_level = institution_level;
        this.institution_province = institution_province;
        this.institution_city = institution_city;
        this.institution_provinceid = institution_provinceid;
        this.institution_cityid = institution_cityid;
        this.institution_sql_id = institution_sql_id;
        this.institution_neo4j_id = institution_neo4j_id;
    }

    public String getInstitution_name() {
        return institution_name;
    }

    public void setInstitution_name(String institution_name) {
        this.institution_name = institution_name;
    }

    public Long getInstitution_neo4j_id() {
        return institution_neo4j_id;
    }

    public void setInstitution_neo4j_id(Long institution_neo4j_id) {
        this.institution_neo4j_id = institution_neo4j_id;
    }
}
