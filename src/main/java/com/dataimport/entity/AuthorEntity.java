package com.dataimport.entity;

import java.io.Serializable;

/**
 * @author zhaobing
 */
public class AuthorEntity implements Serializable{
    private static final long serialVersionUID = 1269373329410167403l;
    private String author_name;
    private String author_institution;
    private Long author_neo4j_id;
    private String author_sql_id;//sql数据id
    private String type;

    public String getAuthor_institution() {
        return author_institution;
    }

    public String getAuthor_sql_id() {
        return author_sql_id;
    }

    public AuthorEntity(String author_name, String author_institution,
                        Long author_neo4j_id, String author_sql_id) {
        this.author_name = author_name;
        this.author_institution = author_institution;
        this.author_neo4j_id = author_neo4j_id;
        this.author_sql_id = author_sql_id;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public Long getAuthor_neo4j_id() {
        return author_neo4j_id;
    }

    public void setAuthor_neo4j_id(Long author_neo4j_id) {
        this.author_neo4j_id = author_neo4j_id;
    }
}
