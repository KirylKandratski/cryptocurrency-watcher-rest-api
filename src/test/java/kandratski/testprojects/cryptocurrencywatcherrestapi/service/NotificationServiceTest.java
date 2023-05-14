package kandratski.testprojects.cryptocurrencywatcherrestapi.service;

import kandratski.testprojects.cryptocurrencywatcherrestapi.entity.CryptoCurrency;
import kandratski.testprojects.cryptocurrencywatcherrestapi.entity.UserNotification;
import kandratski.testprojects.cryptocurrencywatcherrestapi.repository.UserNotificationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class NotificationServiceTest {

    @Mock
    private UserNotificationRepository userNotificationRepository;

    @InjectMocks
    private NotificationService notificationService;

    private CryptoCurrency cryptoCurrency;
    private UserNotification userNotification1;
    private UserNotification userNotification2;

    @BeforeEach
    public void setUp() {
        cryptoCurrency = new CryptoCurrency();
        cryptoCurrency.setId("1");
        cryptoCurrency.setSymbol("BTC");
        cryptoCurrency.setCurrentPrice(10000);

        userNotification1 = new UserNotification();
        userNotification1.setId(1L);
        userNotification1.setUsername("user1");
        userNotification1.setCryptoCurrency(cryptoCurrency);
        userNotification1.setRegisteredPrice(9900);

        userNotification2 = new UserNotification();
        userNotification2.setId(2L);
        userNotification2.setUsername("user2");
        userNotification2.setCryptoCurrency(cryptoCurrency);
        userNotification2.setRegisteredPrice(10100);
    }

    @Test
    public void checkAndNotifyPriceChange() {

        List<UserNotification> userNotifications = Arrays.asList(userNotification1, userNotification2);
        when(userNotificationRepository.findByCryptoCurrency(cryptoCurrency)).thenReturn(userNotifications);

        notificationService.checkAndNotifyPriceChange(cryptoCurrency);

        verify(userNotificationRepository).findByCryptoCurrency(cryptoCurrency);
    }
}
