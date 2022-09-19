package com.loginDiego.Dielog.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Getter
@Setter

@Entity
@Table(name = "productos")
@ToString
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotBlank
    @Column(name = "imagen")
    private String image;

    @NotBlank
    @Column(name = "nombre")
    private String name;

    @NotBlank
    @Column(name = "marca")
    private String brand;

    @NotBlank
    @Column(name = "descripcion")
    private String desccription;

    @NotBlank
    @Column(name = "especificacion")
    private String specification;

    @NotBlank
    @Column(name = "oferta")
    private String offer;

    @NotBlank
    @Column(name = "precio")
    private Double price;

    @NotBlank
    @Column(name = "categoria")
    private String category;
}
