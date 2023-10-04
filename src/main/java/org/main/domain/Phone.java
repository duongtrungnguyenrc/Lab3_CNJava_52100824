package org.main.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "Phone")
public class Phone {
    @Id
    private int id;
    @Column
    private String name;
    @Column
    private double price;
    @Column
    private String color;
    @Column
    private String country;
    @Column
    private int quantity;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Manufacture manufacture;
}