package com.arz.pdms.test.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class Questions {

    @XmlAttribute
    private int id;

    @XmlElement
    private List<Question> question;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public List<Question> getQuestion() {
        return question;
    }
    public void setQuestion(List<Question> question) {
        this.question = question;
    }
}
