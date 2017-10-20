package com.gap22.community.apartment.Entities;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class Poll {

    public String question;
    public String active;
    public String option1;
    public String option2;
    public String option3;
    public String type;
    public String created_by;
    public Date created_date;
    public String modified_by;
    public Date modified_date;

    public Poll() {
    }

    public Poll(String question, Status active, String option1, String option2, String option3,
                String type, String created_by, Date created_date, String modified_by, Date modified_date) {
        this.question = question;
        this.active = active.value;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.type = type;
        this.created_by = created_by;
        this.created_date = created_date;
        this.modified_by = modified_by;
        this.modified_date = modified_date;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("question", question);
        result.put("active", active);
        result.put("type", type);
        result.put("option1", option1);
        result.put("option2", option2);
        result.put("option3", option3);
        result.put("created_by", created_by);
        result.put("created_date", created_date);
        result.put("modified_by", modified_by);
        result.put("modified_date", modified_date);
        return result;
    }

    public enum Status {
        Active("A"), InActive("I"), Archive("X");
        public String value;

        Status(String value) {
            this.value = value;
        }
    }
}
