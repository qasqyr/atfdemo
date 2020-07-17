package kz.atf.atfdemo.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "patronymic_name")
    private String patronymicName;
    @Column(name = "deleted", nullable = false)
    private Boolean deleted = false;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    private List<Contact> contacts;
}
