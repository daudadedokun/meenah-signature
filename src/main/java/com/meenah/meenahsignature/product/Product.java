package com.meenah.meenahsignature.product;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Optional;

@Entity
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(nullable = true)
    private String imageLink;
//    @Enumerated(EnumType.STRING)
//    private Category category;
    private double price;
    private String brand;
    private Long numReviews;
    private Long countInStock;
    public Product(String name, String imageLink) {
        this.name = name;
        this.imageLink = imageLink;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Optional<String> getImageLink() {
        return Optional.ofNullable(imageLink);
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }
}
