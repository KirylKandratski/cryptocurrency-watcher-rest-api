package kandratski.testprojects.cryptocurrencywatcherrestapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CryptoCurrencyDto {
    private String id;
    private String symbol;
    private double currentPrice;

}
