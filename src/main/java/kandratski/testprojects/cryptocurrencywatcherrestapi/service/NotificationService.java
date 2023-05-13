package kandratski.testprojects.cryptocurrencywatcherrestapi.service;

import kandratski.testprojects.cryptocurrencywatcherrestapi.entity.CryptoCurrency;
import kandratski.testprojects.cryptocurrencywatcherrestapi.entity.UserNotification;
import kandratski.testprojects.cryptocurrencywatcherrestapi.repository.UserNotificationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class NotificationService {
    private static final Logger log = LoggerFactory.getLogger(CryptoCurrencyService.class);
    private final UserNotificationRepository userNotificationRepository;

    public NotificationService(UserNotificationRepository userNotificationRepository) {
        this.userNotificationRepository = userNotificationRepository;
    }

    @Transactional
    public void checkAndNotifyPriceChange(CryptoCurrency cryptoCurrency) {
        List<UserNotification> userNotifications = userNotificationRepository.findByCryptoCurrency(cryptoCurrency);

        if (!userNotifications.isEmpty()) {

            for (UserNotification userNotification : userNotifications) {
                double registeredPrice = userNotification.getRegisteredPrice();
                double newPrice = cryptoCurrency.getCurrentPrice();
                double priceChangePercentage = Math.round(((newPrice - registeredPrice) / registeredPrice) * 100);

                if (Math.abs(priceChangePercentage) >= 1) {
                    log.warn("Цена {} изменилась на {} % для пользователя {}", cryptoCurrency.getSymbol(), priceChangePercentage, userNotification.getUsername());
                    userNotification.setRegisteredPrice(newPrice);
                }
            }
        }


    }
}
