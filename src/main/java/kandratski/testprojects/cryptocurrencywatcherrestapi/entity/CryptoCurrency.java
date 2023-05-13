package kandratski.testprojects.cryptocurrencywatcherrestapi.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "crypto_currencies")
public class CryptoCurrency {

    @Id
    private String id;
    private String symbol;
    private double currentPrice;
}

