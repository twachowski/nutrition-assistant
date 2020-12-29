package pl.polsl.wachowski.nutritionassistant.domain.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import pl.polsl.wachowski.nutritionassistant.domain.db.user.UserEntity;
import pl.polsl.wachowski.nutritionassistant.domain.db.user.VerificationTokenEntity;
import pl.polsl.wachowski.nutritionassistant.domain.samples.UserEntitySamples;
import pl.polsl.wachowski.nutritionassistant.domain.samples.VerificationTokenEntitySamples;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

@DataJpaTest
@ContextConfiguration(classes = {TokenRepository.class, UserRepository.class})
@EnableJpaRepositories(basePackages = "pl.polsl.wachowski.nutritionassistant.domain.repository")
@EntityScan(basePackages = "pl.polsl.wachowski.nutritionassistant.domain.db")
class TokenRepositoryTest {

    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;

    @Autowired
    public TokenRepositoryTest(final TokenRepository tokenRepository, final UserRepository userRepository) {
        this.tokenRepository = tokenRepository;
        this.userRepository = userRepository;
    }

    @Test
    @DisplayName("Should delete active tokens assigned to active users by user status")
    void shouldDeleteActiveTokensAssignedToActiveUsersByUserStatus() {
        //given
        final UserEntity activeUser = UserEntitySamples.activeUser("foo@bar.com");
        final List<VerificationTokenEntity> activeTokens = VerificationTokenEntitySamples.randomActiveTokens(activeUser);
        final UserEntity inactiveUser = UserEntitySamples.inactiveUser("bar@foo.com");
        final List<VerificationTokenEntity> randomTokens = VerificationTokenEntitySamples.randomTokens(inactiveUser);

        //when
        userRepository.save(activeUser);
        userRepository.save(inactiveUser);
        tokenRepository.saveAll(activeTokens);
        tokenRepository.saveAll(randomTokens);
        final int deletedTokensCount = tokenRepository.deleteVerificationTokensByUserStatus(UserEntity.UserStatus.ACTIVE);
        final List<VerificationTokenEntity> remainingTokens = tokenRepository.findAll();

        //then
        assertEquals(activeTokens.size(), deletedTokensCount);
        assertIterableEquals(randomTokens, remainingTokens);
    }

    @Test
    @DisplayName("Should delete expired tokens assigned to active users by user status")
    void shouldDeleteExpiredTokensAssignedToActiveUsersByUserStatus() {
        //given
        final UserEntity activeUser = UserEntitySamples.activeUser("foo@bar.com");
        final List<VerificationTokenEntity> expiredTokens = VerificationTokenEntitySamples.randomExpiredTokens(activeUser);
        final UserEntity inactiveUser = UserEntitySamples.inactiveUser("bar@foo.com");
        final List<VerificationTokenEntity> randomTokens = VerificationTokenEntitySamples.randomTokens(inactiveUser);

        //when
        userRepository.save(activeUser);
        userRepository.save(inactiveUser);
        tokenRepository.saveAll(expiredTokens);
        tokenRepository.saveAll(randomTokens);
        final int deletedTokenCount = tokenRepository.deleteVerificationTokensByUserStatus(UserEntity.UserStatus.ACTIVE);
        final List<VerificationTokenEntity> remainingTokens = tokenRepository.findAll();

        //then
        assertEquals(expiredTokens.size(), deletedTokenCount);
        assertIterableEquals(randomTokens, remainingTokens);
    }

    @Test
    @DisplayName("Should delete active tokens assigned to inactive users by user status")
    void shouldDeleteActiveTokensAssignedToInactiveUsersByUserStatus() {
        //given
        final UserEntity inactiveUser = UserEntitySamples.inactiveUser("foo@bar.com");
        final List<VerificationTokenEntity> activeTokens = VerificationTokenEntitySamples.randomActiveTokens(inactiveUser);
        final UserEntity activeUser = UserEntitySamples.activeUser("bar@foo.com");
        final List<VerificationTokenEntity> randomTokens = VerificationTokenEntitySamples.randomTokens(activeUser);

        //when
        userRepository.save(inactiveUser);
        userRepository.save(activeUser);
        tokenRepository.saveAll(activeTokens);
        tokenRepository.saveAll(randomTokens);
        final int deletedTokensCount = tokenRepository.deleteVerificationTokensByUserStatus(UserEntity.UserStatus.INACTIVE);
        final List<VerificationTokenEntity> remainingTokens = tokenRepository.findAll();

        //then
        assertEquals(activeTokens.size(), deletedTokensCount);
        assertIterableEquals(randomTokens, remainingTokens);
    }

    @Test
    @DisplayName("Should delete expired tokens assigned to inactive users by user status")
    void shouldDeleteExpiredTokensAssignedToInactiveUsersByUserStatus() {
        //given
        final UserEntity inactiveUser = UserEntitySamples.inactiveUser("foo@bar.com");
        final List<VerificationTokenEntity> expiredTokens = VerificationTokenEntitySamples.randomExpiredTokens(inactiveUser);
        final UserEntity activeUser = UserEntitySamples.activeUser("bar@foo.com");
        final List<VerificationTokenEntity> randomTokens = VerificationTokenEntitySamples.randomTokens(activeUser);

        //when
        userRepository.save(inactiveUser);
        userRepository.save(activeUser);
        tokenRepository.saveAll(expiredTokens);
        tokenRepository.saveAll(randomTokens);
        final int deletedTokenCount = tokenRepository.deleteVerificationTokensByUserStatus(UserEntity.UserStatus.INACTIVE);
        final List<VerificationTokenEntity> remainingTokens = tokenRepository.findAll();

        //then
        assertEquals(expiredTokens.size(), deletedTokenCount);
        assertIterableEquals(randomTokens, remainingTokens);
    }

    @Test
    @DisplayName("Should delete expired tokens assigned to active users by token expiry date")
    void shouldDeleteExpiredTokensAssignedToActiveUsersByTokenExpiryDate() {
        //given
        final UserEntity activeUser = UserEntitySamples.activeUser("foo@bar.com");
        final List<VerificationTokenEntity> expiredTokens = VerificationTokenEntitySamples.randomExpiredTokens(activeUser);
        final UserEntity inactiveUser = UserEntitySamples.inactiveUser("bar@foo.com");
        final List<VerificationTokenEntity> activeTokens = Stream.of(VerificationTokenEntitySamples.randomActiveTokens(inactiveUser),
                                                                     VerificationTokenEntitySamples.randomActiveTokens(activeUser))
                .flatMap(List::stream)
                .collect(Collectors.toList());

        //when
        userRepository.save(activeUser);
        userRepository.save(inactiveUser);
        tokenRepository.saveAll(expiredTokens);
        tokenRepository.saveAll(activeTokens);
        final int deletedTokenCount = tokenRepository.deleteExpiredVerificationTokens();
        final List<VerificationTokenEntity> remainingTokens = tokenRepository.findAll();

        //then
        assertEquals(expiredTokens.size(), deletedTokenCount);
        assertIterableEquals(activeTokens, remainingTokens);
    }

    @Test
    @DisplayName("Should delete expired tokens assigned to inactive users by token expiry date")
    void shouldDeleteExpiredTokensAssignedToInactiveUsersByTokenExpiryDate() {
        //given
        final UserEntity inactiveUser = UserEntitySamples.inactiveUser("foo@bar.com");
        final List<VerificationTokenEntity> expiredTokens = VerificationTokenEntitySamples.randomExpiredTokens(inactiveUser);
        final UserEntity activeUser = UserEntitySamples.activeUser("bar@foo.com");
        final List<VerificationTokenEntity> activeTokens = Stream.of(VerificationTokenEntitySamples.randomActiveTokens(inactiveUser),
                                                                     VerificationTokenEntitySamples.randomActiveTokens(activeUser))
                .flatMap(List::stream)
                .collect(Collectors.toList());

        //when
        userRepository.save(inactiveUser);
        userRepository.save(activeUser);
        tokenRepository.saveAll(expiredTokens);
        tokenRepository.saveAll(activeTokens);
        final int deletedTokenCount = tokenRepository.deleteExpiredVerificationTokens();
        final List<VerificationTokenEntity> remainingTokens = tokenRepository.findAll();

        //then
        assertEquals(expiredTokens.size(), deletedTokenCount);
        assertIterableEquals(activeTokens, remainingTokens);
    }

}