package kandratski.testprojects.cryptocurrencywatcherrestapi.controller;

import kandratski.testprojects.cryptocurrencywatcherrestapi.dto.CryptoCurrencyDto;
import kandratski.testprojects.cryptocurrencywatcherrestapi.service.CryptoCurrencyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CryptoCurrencyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CryptoCurrencyService cryptoCurrencyService;


    private List<CryptoCurrencyDto> cryptoCurrencies;

    @BeforeEach
    void setUp() {
        cryptoCurrencies = Arrays.asList(
                new CryptoCurrencyDto("1", "BTC", 10000),
                new CryptoCurrencyDto("2", "ETH", 2000)
        );
    }

    @Test
    void getAllCryptoCurrencies() throws Exception {
        when(cryptoCurrencyService.getAllCryptoCurrencies()).thenReturn(cryptoCurrencies);

        mockMvc.perform(get("/api/cryptocurrencies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].symbol").value("BTC"))
                .andExpect(jsonPath("$[1].symbol").value("ETH"));
    }

    @Test
    void getCryptoCurrencyBySymbol() throws Exception {
        CryptoCurrencyDto btc = cryptoCurrencies.get(0);
        when(cryptoCurrencyService.getCryptoCurrencyDtoBySymbol("BTC")).thenReturn(btc);

        mockMvc.perform(get("/api/cryptocurrencies/price/BTC"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.symbol").value("BTC"))
                .andExpect(jsonPath("$.currentPrice").value(10000));
    }

}
