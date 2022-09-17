package com.sanya.mts.tariffs_manager.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

@Entity
@Table(name="news")
public class News {

    @Id
    private Integer id;

    private String article;

    private String text;

//    @Column(name = "image")
//    private String filePath;
    private String related_tariffs;
    private Date date;

    public News(Integer id, String article, String text, String related_tariffs, Date date) {
        this.id = id;
        this.article = article;
        this.text = text;
        this.related_tariffs = related_tariffs;
        this.date = date;
    }

    public News() {
    }



    //    public String getFilePath() {
//        return filePath;
//    }
//
//    public void setFilePath(String filePath) {
//        this.filePath = filePath;
//    }

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

    public String getRelated_tariffs() {
        return related_tariffs;
    }

    public void setRelated_tariffs(String related_tariffs) {
        this.related_tariffs = related_tariffs;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
