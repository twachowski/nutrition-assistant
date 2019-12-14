package pl.polsl.wachowski.nutritionassistant.db.user;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@Data
@AllArgsConstructor
@Entity
public class UserCredentials {

    @Id
    private final Long id;

    @Column(nullable = false)
    private String password;

    @Column(unique = true,
            nullable = false,
            length = 6)
    private String passwordSalt;

    @OneToOne
    @MapsId
    private final User user;

}
