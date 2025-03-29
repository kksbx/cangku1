package org.tinkerhub.offgo.entity;

import jakarta.persistence.*;

@Entity
@Table(name="diary_content")
public class diary_content {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer diaryID;
    private String diary_content;
    public diary_content() {}
    public diary_content(Integer diaryID, String diary_content) {
        this.diaryID = diaryID;
        this.diary_content = diary_content;
    }

}
