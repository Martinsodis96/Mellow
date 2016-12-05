package com.mellow.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.context.annotation.Lazy;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "`like`")
public class Like extends AbstractEntity {

    @ManyToOne
    private Post post;

    protected Like() {
    }

    public Post getPost() {
        return post;
    }
}
