package org.tinkerhub.offgo.entity;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tinkerhub.offgo.Repository.UserRepository;
import org.tinkerhub.offgo.mysql_service.User_service;

@Entity
@Table(name = "diary")
public class diary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String description;
    private int[] image;
    private Integer hot;
    private String destination;
    private double rating;
    private int rate_sum;
    private int rate_counts;

    private int userID;

    private int content;

    public diary() {}
    public diary(Integer id, String title, int user, String description, int content, int[] image,String destination) {
        this.id = id;
        this.title = title;
        this.userID = user;
        this.description = description;
        this.content = content;
        this.image = image;
        this.rating = 0.0;
        this.rate_sum = 0;
        this.rate_counts = 0;
        this.hot = 0;
        this.destination = destination;
    }
    public diary(Integer id, String title, int user, String description, int content,String destination) {
        this.id = id;
        this.title = title;
        this.hot=0;
        this.rating=0;
        this.rate_sum=0;
        this.rate_counts=0;
        this.userID = user;
        this.description = description;
        this.content = content;
        this.image=new int[0];
        this.destination=destination;
    }
}
