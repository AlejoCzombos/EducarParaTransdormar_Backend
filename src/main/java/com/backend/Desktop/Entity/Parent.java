package com.backend.Desktop.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "parent")
public class Parent {

    @Id
    private Integer id;

    private String firstname;
    private String lastname;

    @ManyToMany
    @JoinTable(
            name = "parent_student",
            joinColumns = @JoinColumn(name = "parent_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "student_id", referencedColumnName = "id")
    )
    private List<Student> childrens;

    @JsonIgnore
    @OneToMany(mappedBy = "parent")
    private List<Fee> fees;

    public Parent(String firstname, String lastname){
        this.firstname = firstname;
        this.lastname = lastname;
    }

}
