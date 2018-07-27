package com.dataimport.entity;

import com.dataimport.generic.RelationshipTypes;

import java.io.Serializable;

/**
 * @author zhaobing
 */
public class RelationshipEntity implements Serializable{
    private static final long serialVersionUID = 1269373329410167403l;
    private Long source;
    private Long target;
    private String cooperate_time;
    private Long cooperate_times;
    private RelationshipTypes type;

    public RelationshipEntity(Long source, Long target, String cooperate_time,
                              Long cooperate_times, RelationshipTypes type) {
        this.source = source;
        this.target = target;
        this.cooperate_time = cooperate_time;
        this.cooperate_times = cooperate_times;
        this.type = type;
    }

    public String getCooperate_time() {
        return cooperate_time;
    }

    public Long getCooperate_times() {
        return cooperate_times;
    }

    public Long getSource() {
        return source;
    }

    public void setSource(Long source) {
        this.source = source;
    }

    public Long getTarget() {
        return target;
    }

    public void setTarget(Long target) {
        this.target = target;
    }

    public Long getTimes() {
        return cooperate_times;
    }

    public void setTimes(Long time) {
        this.cooperate_times = cooperate_times;
    }

    public RelationshipTypes getType() {
        return type;
    }

    public void setType(RelationshipTypes type) {
        this.type = type;
    }
}
