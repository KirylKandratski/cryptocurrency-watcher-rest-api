package kandratski.testprojects.cryptocurrencywatcherrestapi.repository;

import kandratski.testprojects.cryptocurrencywatcherrestapi.entity.CryptoCurrency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CryptoCurrencyRepository extends JpaRepository<CryptoCurrency, String> {
    Optional<CryptoCurrency> findBySymbol(String symbol);
}
