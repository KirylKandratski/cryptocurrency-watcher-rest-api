package kandratski.testprojects.cryptocurrencywatcherrestapi.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@ConfigurationProperties(prefix = "supported-cryptocurrencies")
public class SupportedCryptoCurrencies {
    private final List<SupportedCryptoCurrency> currencies = new ArrayList<>();

    public List<SupportedCryptoCurrency> getCurrencies() {
        return currencies;
    }
}
