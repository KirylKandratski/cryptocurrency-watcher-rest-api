package kandratski.testprojects.cryptocurrencywatcherrestapi.dto;

import lombok.Data;

@Data
public class CryptoCurrencyDto {
    private String id;
    private String symbol;
    private double currentPrice;

}
