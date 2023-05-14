package kandratski.testprojects.cryptocurrencywatcherrestapi.service;

import kandratski.testprojects.cryptocurrencywatcherrestapi.client.CoinLoreApiClient;
import kandratski.testprojects.cryptocurrencywatcherrestapi.config.SupportedCryptoCurrencies;
import kandratski.testprojects.cryptocurrencywatcherrestapi.dto.CryptoCurrencyDto;
import kandratski.testprojects.cryptocurrencywatcherrestapi.entity.CryptoCurrency;
import kandratski.testprojects.cryptocurrencywatcherrestapi.mapper.CryptoCurrencyMapper;
import kandratski.testprojects.cryptocurrencywatcherrestapi.repository.CryptoCurrencyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CryptoCurrencyServiceTest {

    @InjectMocks
    private CryptoCurrencyService cryptoCurrencyService;

    @Mock
    private CryptoCurrencyRepository cryptoCurrencyRepository;
    @Mock
    private NotificationService notificationService;
    @Mock
    private SupportedCryptoCurrencies supportedCryptoCurrencies;
    @Mock
    private CoinLoreApiClient coinLoreApiClient;
    @Mock
    private CryptoCurrencyMapper cryptoCurrencyMapper;

    private CryptoCurrency cryptoCurrency;

    @BeforeEach
    public void setUp() {
        cryptoCurrency = new CryptoCurrency();
        cryptoCurrency.setId("1");
        cryptoCurrency.setSymbol("BTC");
        cryptoCurrency.setCurrentPrice(10000);
    }

    @Test
    public void testGetCryptoCurrencyBySymbol() {
        when(cryptoCurrencyRepository.findBySymbol(anyString())).thenReturn(Optional.of(cryptoCurrency));

        CryptoCurrency result = cryptoCurrencyService.getCryptoCurrencyBySymbol("BTC");

        verify(cryptoCurrencyRepository, times(1)).findBySymbol("BTC");
        assert result != null;
        assert result.getId().equals("1");
        assert result.getSymbol().equals("BTC");
        assert result.getCurrentPrice() == 10000;
    }

    @Test
    public void testGetAllCryptoCurrencies() {
        List<CryptoCurrency> cryptoCurrencyList = Collections.singletonList(cryptoCurrency);
        when(cryptoCurrencyRepository.findAll()).thenReturn(cryptoCurrencyList);
        when(cryptoCurrencyMapper.toDto(any())).thenReturn(new CryptoCurrencyDto("1", "BTC", 10000));

        List<CryptoCurrencyDto> result = cryptoCurrencyService.getAllCryptoCurrencies();

        verify(cryptoCurrencyRepository, times(1)).findAll();
        verify(cryptoCurrencyMapper, times(1)).toDto(cryptoCurrency);
        assert result != null;
        assert result.size() == 1;
        CryptoCurrencyDto dto = result.get(0);
        assert dto.getId().equals("1");
        assert dto.getSymbol().equals("BTC");
        assert dto.getCurrentPrice() == 10000;
    }

    @Test
    public void testGetCryptoCurrencyDtoBySymbol() {
        when(cryptoCurrencyRepository.findBySymbol(anyString())).thenReturn(Optional.of(cryptoCurrency));
        when(cryptoCurrencyMapper.toDto(any())).thenReturn(new CryptoCurrencyDto("1", "BTC", 10000));

        CryptoCurrencyDto result = cryptoCurrencyService.getCryptoCurrencyDtoBySymbol("BTC");

        verify(cryptoCurrencyRepository, times(1)).findBySymbol("BTC");
        verify(cryptoCurrencyMapper, times(1)).toDto(cryptoCurrency);
        assert result != null;
        assert result.getId().equals("1");
        assert result.getSymbol().equals("BTC");
        assert result.getCurrentPrice() == 10000;
    }
}
