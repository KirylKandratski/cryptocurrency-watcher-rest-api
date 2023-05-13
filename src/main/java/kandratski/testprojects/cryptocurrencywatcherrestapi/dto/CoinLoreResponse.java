package kandratski.testprojects.cryptocurrencywatcherrestapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CoinLoreResponse {
    private String id;
    private String symbol;
    @JsonProperty("price_usd")
    private double priceUsd;
}

