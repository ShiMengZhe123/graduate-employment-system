package com.zzuli.graduate.entity;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "news")
@Data
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private LocalDate publishDate;
    @Column(columnDefinition = "TEXT")
    private String content;

    public News() {}
    public News(String title, LocalDate publishDate, String content) {
        this.title = title;
        this.publishDate = publishDate;
        this.content = content;
    }
}