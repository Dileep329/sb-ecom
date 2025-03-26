package com.ecommerce.project.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long address_id;

    @NotBlank
    @Size(min = 5,message = "Street must be atleast 5 characters")
    private String street;


    @NotBlank
    @Size(min = 5,message = "Street must be atleast 5 characters")
    private String buildingName;

    @NotBlank
    @Size(min = 4,message = "Street must be atleast 5 characters")
    private String city;

    @NotBlank
    @Size(min = 2,message = "Street must be atleast 5 characters")
    private String state;

    @NotBlank
    @Size(min = 2,message = "Street must be atleast 5 characters")
    private String country;

    @NotBlank
    @Size(min = 6,message = "Street must be atleast 5 characters")
    private String pincode;

    @ToString.Exclude
    @ManyToMany(mappedBy = "addressList")
    private List<User> users=new ArrayList<>();

    public Address(Long address_id, String pincode, String country, String state, String city, String buildingName, String street) {
        this.address_id = address_id;
        this.pincode = pincode;
        this.country = country;
        this.state = state;
        this.city = city;
        this.buildingName = buildingName;
        this.street = street;

    }
}
