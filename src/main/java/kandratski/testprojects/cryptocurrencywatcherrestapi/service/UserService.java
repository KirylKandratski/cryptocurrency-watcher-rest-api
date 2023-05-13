package kandratski.testprojects.cryptocurrencywatcherrestapi.service;

import kandratski.testprojects.cryptocurrencywatcherrestapi.entity.CryptoCurrency;
import kandratski.testprojects.cryptocurrencywatcherrestapi.entity.UserNotification;
import kandratski.testprojects.cryptocurrencywatcherrestapi.repository.UserNotificationRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
        validateInputs(username, symbol);

        CryptoCurrency cryptoCurrency = cryptoCurrencyService.getCryptoCurrencyBySymbol(symbol);
        checkUserNotificationExists(username, cryptoCurrency);

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

    private void validateInputs(String username, String symbol) {
        if (!StringUtils.hasText(username)) {
            throw new IllegalArgumentException("Имя пользователя не может быть пустым");
        }
        if (!StringUtils.hasText(symbol)) {
            throw new IllegalArgumentException("Код криптовалюты не может быть пустым");
        }
    }

    private void checkUserNotificationExists(String username, CryptoCurrency cryptoCurrency) {
        Optional<UserNotification> existingUserNotification = userNotificationRepository.findByUsernameAndCryptoCurrency(username, cryptoCurrency);

        if (existingUserNotification.isPresent()) {
            throw new IllegalStateException("Пользователь уже зарегистрирован для уведомлений о данной криптовалюте");
        }
    }
}
