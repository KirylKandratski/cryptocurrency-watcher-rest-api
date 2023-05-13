package kandratski.testprojects.cryptocurrencywatcherrestapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserNotificationRequest {
    private String username;
    private String symbol;
}
