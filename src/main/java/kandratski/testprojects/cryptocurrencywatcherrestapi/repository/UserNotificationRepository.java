package kandratski.testprojects.cryptocurrencywatcherrestapi.repository;

import kandratski.testprojects.cryptocurrencywatcherrestapi.entity.CryptoCurrency;
import kandratski.testprojects.cryptocurrencywatcherrestapi.entity.UserNotification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserNotificationRepository extends JpaRepository<UserNotification, Long> {
    Optional<UserNotification> findByCryptoCurrency(CryptoCurrency cryptoCurrency);
}
