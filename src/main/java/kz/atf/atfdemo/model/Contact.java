package kz.atf.atfdemo.model;

import lombok.Data;
import org.hibernate.annotations.GeneratorType;

import javax.persistence.*;

@Data
@Entity
@Table(name = "contacts")
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "type")
    @Enumerated(value = EnumType.STRING)
    private Type type;
    @Column(name = "deleted", nullable = false)
    private Boolean deleted = false;
    @ManyToOne
    private User user;

    public enum Type {
        MOBILE, WORK, HOME
    }
}
