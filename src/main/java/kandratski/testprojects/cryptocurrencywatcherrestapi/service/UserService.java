package kandratski.testprojects.cryptocurrencywatcherrestapi.service;

import kandratski.testprojects.cryptocurrencywatcherrestapi.entity.CryptoCurrency;
import kandratski.testprojects.cryptocurrencywatcherrestapi.entity.UserNotification;
import kandratski.testprojects.cryptocurrencywatcherrestapi.repository.UserNotificationRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final CryptoCurrencyService cryptoCurrencyService;
    private final UserNotificationRepository userNotificationRepository;

    public UserService(CryptoCurrencyService cryptoCurrencyService, UserNotificationRepository userNotificationRepository) {
        this.cryptoCurrencyService = cryptoCurrencyService;
        this.userNotificationRepository = userNotificationRepository;
    }

    public UserNotification registerUserForNotification(String username, String symbol) {

        CryptoCurrency cryptoCurrency = cryptoCurrencyService.getCryptoCurrencyBySymbol(symbol);

        UserNotification userNotification = new UserNotification();
        userNotification.setUsername(username);
        userNotification.setCryptoCurrency(cryptoCurrency);
        userNotification.setRegisteredPrice(cryptoCurrency.getCurrentPrice());

        return userNotificationRepository.save(userNotification);
    }

}
