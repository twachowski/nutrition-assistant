package pl.polsl.wachowski.nutritionassistant.db.user;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class UserCredentials {

    @Id
    private Long id;

    @Column(nullable = false,
            columnDefinition = "CHAR(60)")
    private String password;

    @OneToOne
    @MapsId
    private User user;

}
