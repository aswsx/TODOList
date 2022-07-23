package ru.job4j.todo.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author Alex Gutorov
 * @version 1.0
 * @created 13/07/2022 - 19:22
 */
@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    public static Category of(String name) {
        Category category = new Category();
        category.name = name;
        return category;
    }
}
