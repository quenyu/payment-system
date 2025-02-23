package Java.com.paymentapp.entity.Account;

import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class AccountEntity {
    @ToString.Include
    @EqualsAndHashCode.Include
    private Integer id;

    private Integer userId;
    private String accountNumber;
    private BigDecimal balance;
    private AccountStatus status;
    private Timestamp createdAt;
    private String currency;

    public AccountEntity withBalance(BigDecimal newBalance) {
        return new AccountEntity(
                this.id,
                this.userId,
                this.accountNumber,
                newBalance,
                this.status,
                this.createdAt,
                this.currency
        );
    }
}