package com.dataimport.generic;

import org.neo4j.graphdb.RelationshipType;

/**
 * @author zhaobing
 */
public enum RelationshipTypes implements RelationshipType {
    Is_Work_For,//A 隶属于某企业
    Cooperate_Author,//作者合作关系
    Cooperate_Institution,//企业之间合作关系
    Is_Super_Of//上级企业 A Is_Super_Of B A是B的上级
}
