package org.main.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "Manufacture")
public class Manufacture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String name;
    @Column
    private String location;
    @Column
    private int employee;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "id")
    private Set<Phone> phones;

    public Manufacture(String name, String location, int employee) {
        this.name = name;
        this.location = location;
        this.employee = employee;
    }

    public Manufacture() {

    }

    @Override
    public String toString(){
        return this.id + " " + this.name + " " + this.location + " " + this.employee;
    }
}
