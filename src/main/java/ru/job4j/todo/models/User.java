package ru.job4j.todo.models;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Alex Gutorov
 * @version 1.0
 * @created 05/05/2022 - 15:59
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "users")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String email;
    private String password;
}

