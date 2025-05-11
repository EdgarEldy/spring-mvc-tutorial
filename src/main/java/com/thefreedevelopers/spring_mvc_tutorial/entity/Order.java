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
    // Setting ManyToOne relationship to Product
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = true)
    private Product product;
    // Add qty attribute
    @Column(name = "qty")
    private Integer qty;
    // Add total attribute
    @Column(name = "total")
    private Double total;

    // Constructor
    public Order(){

    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

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

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}