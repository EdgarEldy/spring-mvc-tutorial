package com.edgareldy.spring_mvc_tutorial.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 2, max = 100)
    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @NotBlank
    @Size(min = 2, max = 100)
    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    @NotBlank
    @Size(max = 20)
    @Column(name = "tel", nullable = false, length = 20)
    private String tel;

    @NotBlank
    @Email
    @Size(max = 150)
    @Column(name = "email", nullable = false, length = 150)
    private String email;

    @NotBlank
    @Size(max = 255)
    @Column(name = "address", nullable = false)
    private String address;
}
