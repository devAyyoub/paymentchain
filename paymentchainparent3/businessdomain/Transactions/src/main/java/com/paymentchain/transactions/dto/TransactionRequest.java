
package com.paymentchain.transactions.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

/**
 *
 * @author ayyoub
 */
@Schema(name = "CustomerRequest", description = "Model represent a customer on database")
@Data
public class TransactionRequest {

    @Schema(name = "reference", required = true, example = "01", defaultValue = "0", description = "Transaction's reference")
    private String reference;

    @Schema(name = "ibanAccount", required = true, example = "ES9121000418450200051332", description = "IBAN account associated with the transaction")
    private String ibanAccount;

    @Schema(name = "date", required = true, example = "2023-01-01T12:00:00", description = "Date and time of the transaction")
    private LocalDateTime date;

    @Schema(name = "amount", required = true, example = "1000.50", description = "Transaction amount")
    private double amount;

    @Schema(name = "fee", required = false, example = "5.00", description = "Transaction fee, if any")
    private double fee;

    @Schema(name = "description", required = false, example = "Payment for invoice #12345", description = "Description of the transaction")
    private String description;

    @Schema(name = "status", required = true, example = "COMPLETED", description = "Transaction status")
    private String status;

    @Schema(name = "channel", required = false, example = "ONLINE", description = "Channel through which the transaction was made")
    private String channel;

    @Schema(name = "balanceAfterTransaction", required = true, example = "995.50", description = "Account balance after the transaction")
    private double balanceAfterTransaction;
}
