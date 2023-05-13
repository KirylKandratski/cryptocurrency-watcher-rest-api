package kandratski.testprojects.cryptocurrencywatcherrestapi.repository;

import kandratski.testprojects.cryptocurrencywatcherrestapi.entity.CryptoCurrency;
import kandratski.testprojects.cryptocurrencywatcherrestapi.entity.UserNotification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserNotificationRepository extends JpaRepository<UserNotification, Long> {
    List<UserNotification> findByCryptoCurrency(CryptoCurrency cryptoCurrency);
}
