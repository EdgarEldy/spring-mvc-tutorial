package com.thefreedevelopers.spring_mvc_tutorial.entity;

import javax.persistence.*;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    // Setting ManyToOne relationship to Customer
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = true)
    private Customer customer;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    // Setting ManyToOne relationship to Product
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = true)
    private Product product;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Add qty attribute
    @Column(name = "qty")
    private Integer qty;

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    // Constructor
    public Order(){

    }
}