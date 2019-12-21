package pl.polsl.wachowski.nutritionassistant.db.user;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
public class UserCredentials {

    @Id
    private Long id;

    @Column(nullable = false,
            columnDefinition = "CHAR(60)")
    private String password;

    @OneToOne
    @MapsId
    @JoinColumn(name = "USER_ID")
    private User user;

    public UserCredentials(final String password, final User user) {
        this.password = password;
        this.user = user;
    }

}
