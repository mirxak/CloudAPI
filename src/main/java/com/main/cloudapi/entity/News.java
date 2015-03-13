package com.main.cloudapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.main.cloudapi.entity.base.BaseEntity;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

/**
 * Created by mirxak on 04.03.15.
 */
@Entity
@Table(name = News.TABLE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class News extends BaseEntity {

    public static final String TABLE = "news";
    public static final String CATEGORY = "cloud_news";

    //<editor-fold desc="fields">
    private String title;
    private String newsText;
    //</editor-fold>

    @Override
    @Transient
    public String getTable() {
        return TABLE;
    }

    @Override
    @Transient
    public String getObjectCategory() {
        return CATEGORY;
    }

    @Override
    public BaseEntity generateEntity() {
        return new News();
    }

    @Column(name = "title")
    @NotNull(message = "title is null")
    @NotEmpty(message = "title is empty")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "news_text")
    public String getNewsText() {
        return newsText;
    }

    public void setNewsText(String newsText) {
        this.newsText = newsText;
    }
}
