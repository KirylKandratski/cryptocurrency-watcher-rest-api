package kandratski.testprojects.cryptocurrencywatcherrestapi.service;

import kandratski.testprojects.cryptocurrencywatcherrestapi.entity.CryptoCurrency;
import kandratski.testprojects.cryptocurrencywatcherrestapi.entity.UserNotification;
import kandratski.testprojects.cryptocurrencywatcherrestapi.repository.UserNotificationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    private UserService userService;
    private CryptoCurrencyService cryptoCurrencyService;
    private UserNotificationRepository userNotificationRepository;

    @BeforeEach
    public void setUp() {
        cryptoCurrencyService = mock(CryptoCurrencyService.class);
        userNotificationRepository = mock(UserNotificationRepository.class);
        userService = new UserService(cryptoCurrencyService, userNotificationRepository);
    }

    @Test
    public void testRegisterUserForNotification_success() {
        String username = "test_user";
        String symbol = "BTC";

        CryptoCurrency cryptoCurrency = new CryptoCurrency();
        cryptoCurrency.setSymbol(symbol);
        cryptoCurrency.setCurrentPrice(50000.0);

        when(cryptoCurrencyService.getCryptoCurrencyBySymbol(symbol)).thenReturn(cryptoCurrency);
        when(userNotificationRepository.findByUsernameAndCryptoCurrency(username, cryptoCurrency)).thenReturn(Optional.empty());

        UserNotification userNotification = new UserNotification();
        userNotification.setUsername(username);
        userNotification.setCryptoCurrency(cryptoCurrency);
        userNotification.setRegisteredPrice(cryptoCurrency.getCurrentPrice());

        when(userNotificationRepository.save(any(UserNotification.class))).thenReturn(userNotification);

        UserNotification result = userService.registerUserForNotification(username, symbol);

        assertEquals(userNotification, result);
        verify(cryptoCurrencyService, times(1)).getCryptoCurrencyBySymbol(symbol);
        verify(userNotificationRepository, times(1)).findByUsernameAndCryptoCurrency(username, cryptoCurrency);
        verify(userNotificationRepository, times(1)).save(any(UserNotification.class));
    }

    @Test
    public void testRegisterUserForNotification_emptyUsername() {
        String username = "";
        String symbol = "BTC";

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> userService.registerUserForNotification(username, symbol));
        assertEquals("Имя пользователя не может быть пустым", exception.getMessage());
    }

    @Test
    public void testRegisterUserForNotification_nullUsername() {
        String username = null;
        String symbol = "BTC";

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> userService.registerUserForNotification(username, symbol));
        assertEquals("Имя пользователя не может быть пустым", exception.getMessage());
    }

    @Test
    public void testRegisterUserForNotification_emptySymbol() {
        String username = "test_user";
        String symbol = "";

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> userService.registerUserForNotification(username, symbol));
        assertEquals("Код криптовалюты не может быть пустым", exception.getMessage());
    }

    @Test
    public void testRegisterUserForNotification_nullSymbol() {
        String username = "test_user";
        String symbol = null;

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> userService.registerUserForNotification(username, symbol));
        assertEquals("Код криптовалюты не может быть пустым", exception.getMessage());
    }

    @Test
    public void testRegisterUserForNotification_alreadyRegistered() {
        String username = "test_user";
        String symbol = "BTC";

        CryptoCurrency cryptoCurrency = new CryptoCurrency();
        cryptoCurrency.setSymbol(symbol);
        cryptoCurrency.setCurrentPrice(50000.0);

        when(cryptoCurrencyService.getCryptoCurrencyBySymbol(symbol)).thenReturn(cryptoCurrency);

        UserNotification existingUserNotification = new UserNotification();
        existingUserNotification.setUsername(username);
        existingUserNotification.setCryptoCurrency(cryptoCurrency);
        existingUserNotification.setRegisteredPrice(cryptoCurrency.getCurrentPrice());

        when(userNotificationRepository.findByUsernameAndCryptoCurrency(username, cryptoCurrency)).thenReturn(Optional.of(existingUserNotification));

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> userService.registerUserForNotification(username, symbol));
        assertEquals("Пользователь уже зарегистрирован для уведомлений о данной криптовалюте", exception.getMessage());
    }
}
