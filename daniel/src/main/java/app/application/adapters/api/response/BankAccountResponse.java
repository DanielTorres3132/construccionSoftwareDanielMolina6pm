package app.application.adapters.api.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class BankAccountResponse {
    private Long id;
    private String accountNumber;
    private String accountType;
    private String currency;
    private BigDecimal balance;
    private String status;
    private LocalDateTime openedDate;
    private Long clientId;
}
