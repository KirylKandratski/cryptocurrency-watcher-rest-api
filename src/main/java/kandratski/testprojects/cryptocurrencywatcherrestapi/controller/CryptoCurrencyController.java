package kandratski.testprojects.cryptocurrencywatcherrestapi.controller;

import kandratski.testprojects.cryptocurrencywatcherrestapi.dto.CryptoCurrencyDto;
import kandratski.testprojects.cryptocurrencywatcherrestapi.dto.UserNotificationRequest;
import kandratski.testprojects.cryptocurrencywatcherrestapi.entity.UserNotification;
import kandratski.testprojects.cryptocurrencywatcherrestapi.service.CryptoCurrencyService;
import kandratski.testprojects.cryptocurrencywatcherrestapi.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/cryptocurrencies")
public class CryptoCurrencyController {

    private final CryptoCurrencyService cryptoCurrencyService;
    private final UserService userService;

    public CryptoCurrencyController(CryptoCurrencyService cryptoCurrencyService, UserService userService) {
        this.cryptoCurrencyService = cryptoCurrencyService;
        this.userService = userService;
    }

    @GetMapping
    public List<CryptoCurrencyDto> getAllCryptoCurrencies() {
        return cryptoCurrencyService.getAllCryptoCurrencies();
    }

    @GetMapping("price/{symbol}")
    public CryptoCurrencyDto getCryptoCurrencyBySymbol(@PathVariable String symbol) {
        return cryptoCurrencyService.getCryptoCurrencyDtoBySymbol(symbol);
    }

    @PostMapping("/notify")
    public ResponseEntity<UserNotification> registerUserForNotification(@RequestBody UserNotificationRequest request) {
        UserNotification userNotification = userService.registerUserForNotification(request.getUsername(), request.getSymbol());
        return new ResponseEntity<>(userNotification, HttpStatus.CREATED);
    }

}
