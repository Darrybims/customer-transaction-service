package customer_tranx_service.http_request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import customer_tranx_service.model.TransactionType;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class HttpRequest {

    @NotNull(message = "Input Account Id")
    @Size(min = 10, max = 10, message = "maximum 10 characters")
    private String accountId;

    @Size(min = 5,message = "minimum of 5 characters" )
    @NotNull(message = "Description can not be empty")
    private String description;

    @NotNull(message = "choose either DEBIT or CREDIT")
    private TransactionType transactionType;

    @NotNull(message = "Name of initiator")
    private String createdBy;

    @NotNull(message = "Enter amount")
    @DecimalMin(value = "0.01",message = "Amount can not be less than 0.01")
    private BigDecimal amount;
}
