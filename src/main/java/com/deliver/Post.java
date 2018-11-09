package com.deliver;

import com.deliver.model.BaseObject;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by pdl on 2018/9/13.
 */

@Entity
@Table(name="tc_post")
public class Post extends BaseObject implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "content")
    private String content;

    private Integer weight;
    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getWeight() {
        return weight;

    }
}
