package com.sanya.mts.tariffs_manager.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Table(name = "archived_news")
public class ArchivedNews {

    @Id
    private Integer id;

    private String article;

    private String text;

//    @Column(name = "image")
//    private String filePath;

    private String related_tariffs;

    @Column(name="post_date")
    private Timestamp date;


    public ArchivedNews() {
    }


    public ArchivedNews(Integer id, String article, String text, String related_tariffs, Timestamp date) {
        this.id = id;
        this.article = article;
        this.text = text;
        this.related_tariffs = related_tariffs;
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

//    public String getFilePath() {
//        return filePath;
//    }
//
//    public void setFilePath(String filePath) {
//        this.filePath = filePath;
//    }

    public String getRelated_tariffs() {
        return related_tariffs;
    }

    public void setRelated_tariffs(String related_tariffs) {
        this.related_tariffs = related_tariffs;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }
}
