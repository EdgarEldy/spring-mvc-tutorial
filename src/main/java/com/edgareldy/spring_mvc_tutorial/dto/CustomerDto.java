package com.edgareldy.spring_mvc_tutorial.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class CustomerDto {

    private Long id;

    @NotBlank
    @Size(min = 2, max = 100)
    private String firstName;

    @NotBlank
    @Size(min = 2, max = 100)
    private String lastName;

    @NotBlank
    @Size(max = 20)
    private String tel;

    @NotBlank
    @Email
    @Size(max = 150)
    private String email;

    @NotBlank
    @Size(max = 255)
    private String address;
}
