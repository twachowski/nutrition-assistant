package pl.polsl.wachowski.nutritionassistant.domain.db.user;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Data
@ToString(exclude = "user")
@EqualsAndHashCode(exclude = "user")
@NoArgsConstructor
@Entity
@Table(name = "USER_CREDENTIALS")
public class UserCredentialsEntity {

    @Id
    private Long id;

    @Column(nullable = false,
            columnDefinition = "CHAR(60)")
    private String password;

    @OneToOne
    @MapsId
    @JoinColumn(name = "USER_ID")
    private UserEntity user;

    public UserCredentialsEntity(final String password, final UserEntity user) {
        this.password = password;
        this.user = user;
    }

}
