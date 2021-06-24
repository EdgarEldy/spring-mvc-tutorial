package com.thefreedevelopers.spring_mvc_tutorial.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public String getCategoryName() {
        return categoryName;
    }

    //Constructor
    public Category() {
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    private String categoryName;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    //Setting OneToMany relationship to Product
    @OneToMany(mappedBy = "category")
    private Set<Product> products;
}
