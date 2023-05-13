package kandratski.testprojects.cryptocurrencywatcherrestapi.service;

import kandratski.testprojects.cryptocurrencywatcherrestapi.entity.CryptoCurrency;
import kandratski.testprojects.cryptocurrencywatcherrestapi.entity.UserNotification;
import kandratski.testprojects.cryptocurrencywatcherrestapi.repository.UserNotificationRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final CryptoCurrencyService cryptoCurrencyService;
    private final UserNotificationRepository userNotificationRepository;

    public UserService(CryptoCurrencyService cryptoCurrencyService, UserNotificationRepository userNotificationRepository) {
        this.cryptoCurrencyService = cryptoCurrencyService;
        this.userNotificationRepository = userNotificationRepository;
    }

    public UserNotification registerUserForNotification(String username, String symbol) {

        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Имя пользователя не может быть пустым");
        }

        if (symbol == null || symbol.trim().isEmpty()) {
            throw new IllegalArgumentException("Код криптовалюты не может быть пустым");
        }

        CryptoCurrency cryptoCurrency = cryptoCurrencyService.getCryptoCurrencyBySymbol(symbol);

        Optional<UserNotification> existingUserNotification = userNotificationRepository.findByUsernameAndCryptoCurrency(username, cryptoCurrency);

        if (existingUserNotification.isPresent()) {
            throw new IllegalStateException("Пользователь уже зарегистрирован для уведомлений о данной криптовалюте");
        }

        UserNotification userNotification = new UserNotification();
        userNotification.setUsername(username);
        userNotification.setCryptoCurrency(cryptoCurrency);
        userNotification.setRegisteredPrice(cryptoCurrency.getCurrentPrice());

        try {
            return userNotificationRepository.save(userNotification);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при регистрации пользователя для уведомлений", e);
        }
    }

}
