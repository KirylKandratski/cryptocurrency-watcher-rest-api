package kandratski.testprojects.cryptocurrencywatcherrestapi.service;

import jakarta.annotation.PostConstruct;
import kandratski.testprojects.cryptocurrencywatcherrestapi.client.CoinLoreApiClient;
import kandratski.testprojects.cryptocurrencywatcherrestapi.config.SupportedCryptoCurrencies;
import kandratski.testprojects.cryptocurrencywatcherrestapi.config.SupportedCryptoCurrency;
import kandratski.testprojects.cryptocurrencywatcherrestapi.dto.CoinLoreResponse;
import kandratski.testprojects.cryptocurrencywatcherrestapi.entity.CryptoCurrency;
import kandratski.testprojects.cryptocurrencywatcherrestapi.repository.CryptoCurrencyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CryptoCurrencyService {

    private static final Logger log = LoggerFactory.getLogger(CryptoCurrencyService.class);
    private final CryptoCurrencyRepository cryptoCurrencyRepository;
    private final NotificationService notificationService;
    private final SupportedCryptoCurrencies supportedCryptoCurrencies;
    private final CoinLoreApiClient coinLoreApiClient;

    public CryptoCurrencyService(CryptoCurrencyRepository cryptoCurrencyRepository,
                                 NotificationService notificationService,
                                 SupportedCryptoCurrencies supportedCryptoCurrencies,
                                 CoinLoreApiClient coinLoreApiClient) {
        this.cryptoCurrencyRepository = cryptoCurrencyRepository;
        this.notificationService = notificationService;
        this.supportedCryptoCurrencies = supportedCryptoCurrencies;
        this.coinLoreApiClient = coinLoreApiClient;
    }

    public List<CryptoCurrency> getAllCryptoCurrencies() {
        List<CryptoCurrency> cryptoCurrencyList = cryptoCurrencyRepository.findAll();
        log.info("Список всех доступных криптовалют {}", cryptoCurrencyList);
        return cryptoCurrencyList;
    }

    public CryptoCurrency getCryptoCurrencyBySymbol(String symbol) {
        CryptoCurrency cryptoCurrency = cryptoCurrencyRepository.findBySymbol(symbol)
                .orElseThrow(() -> new NoSuchElementException("Криптовалюта с символом " + symbol + " не найдена."));
        log.info("Актуальная цена криптовалюты {} равна {}", symbol, cryptoCurrency.getCurrentPrice());
        return cryptoCurrency;
    }

    @PostConstruct
    private void loadCryptoCurrencies() {
        List<CryptoCurrency> cryptoCurrencies = new ArrayList<>();

        for (SupportedCryptoCurrency supportedCryptoCurrency : supportedCryptoCurrencies.getCurrencies()) {
            String coinId = supportedCryptoCurrency.getId();
            Optional<CoinLoreResponse> coinLoreResponseOpt = coinLoreApiClient.getCoinLoreResponse(coinId);

            if (coinLoreResponseOpt.isPresent()) {
                CryptoCurrency cryptoCurrency = new CryptoCurrency();
                cryptoCurrency.setId(coinLoreResponseOpt.get().getId());
                cryptoCurrency.setSymbol(coinLoreResponseOpt.get().getSymbol());
                cryptoCurrency.setCurrentPrice(coinLoreResponseOpt.get().getPriceUsd());
                cryptoCurrencies.add(cryptoCurrency);
            } else {
                log.error("Не удалось получить данные для криптовалюты с ID {}", coinId);
            }

        }

        cryptoCurrencyRepository.saveAll(cryptoCurrencies);
    }

    @Scheduled(fixedDelay = 10000)
    private void updateCryptoCurrencyPrices() {
        List<CryptoCurrency> cryptoCurrencies = cryptoCurrencyRepository.findAll();

        for (CryptoCurrency cryptoCurrency : cryptoCurrencies) {
            double newPrice = getNewPriceFromCoinLore(cryptoCurrency.getId());
            if (newPrice != -1) {
                cryptoCurrency.setCurrentPrice(newPrice);
                cryptoCurrencyRepository.save(cryptoCurrency);
                notificationService.checkAndNotifyPriceChange(cryptoCurrency);
            } else {
                log.error("Не удалось обновить цену для криптовалюты {}", cryptoCurrency.getSymbol());
            }
        }

        log.info("Цена криптовалют {}", cryptoCurrencies);
    }

    private double getNewPriceFromCoinLore(String coinId) {
        Optional<CoinLoreResponse> coinLoreResponseOpt = coinLoreApiClient.getCoinLoreResponse(coinId);

        if (coinLoreResponseOpt.isPresent()) {
            return coinLoreResponseOpt.get().getPriceUsd();
        } else {
            log.error("Не удалось получить новую цену для криптовалюты с ID {}", coinId);
            return -1;
        }
    }
}

