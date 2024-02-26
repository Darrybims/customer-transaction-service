package customer_tranx_service.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "sender_account", referencedColumnName = "accountNumber")
    private Account account;
    private String description;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    @Enumerated(EnumType.STRING)
    private TransactionStatus transactionStatus;
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
    private String createdBy;
    private BigDecimal amount;
    private String narration;
    private String transactionId;
}
