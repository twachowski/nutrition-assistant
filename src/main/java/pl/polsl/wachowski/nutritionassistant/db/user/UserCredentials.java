package pl.polsl.wachowski.nutritionassistant.db.user;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class UserCredentials {

    @Id
    private Long id;

    @Column(nullable = false)
    private String password;

    @Column(unique = true,
            nullable = false,
            columnDefinition = "CHAR(6)")
    private String passwordSalt;

    @OneToOne
    @MapsId
    private User user;

}
