package customer_tranx_service.controller;

import customer_tranx_service.http_request.HttpRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import customer_tranx_service.model.Transaction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import customer_tranx_service.service.TransactionService;

import java.util.Map;

@RestController
@RequestMapping("/init_transaction")
@RequiredArgsConstructor
@SecurityRequirement(name = "basicAuth")
public class TranxController {
    private final TransactionService transactionService;

    @Operation(summary = "Initiate Transaction",
            description = "Validate transaction details and initiate transaction as pending",method = "POST",security = @SecurityRequirement(name = "basicAuth"))
    @ApiResponses({@ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Transaction.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema()) }) })
    @PostMapping("/create")
    public ResponseEntity<Map<String,String>> initiateTransaction(@Valid @RequestBody HttpRequest request){
        return ResponseEntity.ok(transactionService.initiateTransaction(request));
    }


    @Operation(summary = "Get transaction status with ID",
            description = "Input is a transaction ID. Retrieves and maps current status of transaction",method = "GET",
            security = @SecurityRequirement(name = "basicAuth"))
    @ApiResponses({@ApiResponse(responseCode = "200", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema()) }) })

    @GetMapping("/get-status")
    public ResponseEntity<Map<String,String>> checkTransactionStatus(@RequestParam String transactionId){
        return ResponseEntity.ok(transactionService.checkTransactionStatus(transactionId));
    }

    @Operation(summary = "Receive Updates",
            description = "Retrieval of webhook transaction notification update",method = "GET",
            security = @SecurityRequirement(name = "basicAuth"))
    @ApiResponses({@ApiResponse(responseCode = "200", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema()) }) })
    @GetMapping("/receive-updates")
    public ResponseEntity<?> receiveTransactionUpdates(){
        return ResponseEntity.ok(transactionService.receiveTransactionUpdates());
    }
}
