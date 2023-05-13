package kandratski.testprojects.cryptocurrencywatcherrestapi.mapper;

import kandratski.testprojects.cryptocurrencywatcherrestapi.dto.CryptoCurrencyDto;
import kandratski.testprojects.cryptocurrencywatcherrestapi.entity.CryptoCurrency;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CryptoCurrencyMapper {

    CryptoCurrencyDto toDto(CryptoCurrency cryptoCurrency);

    CryptoCurrency toEntity(CryptoCurrencyDto cryptoCurrencyDto);

}
