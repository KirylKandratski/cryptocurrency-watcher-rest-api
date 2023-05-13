package kandratski.testprojects.cryptocurrencywatcherrestapi.client;

import kandratski.testprojects.cryptocurrencywatcherrestapi.dto.CoinLoreResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Component
public class CoinLoreApiClient {

    private static final Logger log = LoggerFactory.getLogger(CoinLoreApiClient.class);
    private final RestTemplate restTemplate;
    private final String coinLoreApiUrl;

    public CoinLoreApiClient(RestTemplate restTemplate,
                             @Value("${coinlore.api.url}") String coinLoreApiUrl) {
        this.restTemplate = restTemplate;
        this.coinLoreApiUrl = coinLoreApiUrl;
    }


    public Optional<CoinLoreResponse> getCoinLoreResponse(String coinId) {
        String url = coinLoreApiUrl + "/api/ticker/?id=" + coinId;
        ResponseEntity<CoinLoreResponse[]> response = restTemplate.getForEntity(url, CoinLoreResponse[].class);
        CoinLoreResponse[] coinLoreResponses = response.getBody();

        if (coinLoreResponses != null && coinLoreResponses.length > 0) {
            return Optional.of(response.getBody()[0]);
        } else {
            log.error("Получено пустое тело ответа или массив не содержит элементов");
            return Optional.empty();
        }
    }
}
