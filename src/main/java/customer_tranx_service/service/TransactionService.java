package customer_tranx_service.service;

import customer_tranx_service.http_request.HttpRequest;
import customer_tranx_service.model.Transaction;

import java.util.List;
import java.util.Map;

public interface TransactionService {

    Map<String,String> initiateTransaction(HttpRequest request);

    Map<String,String> checkTransactionStatus(String transactionId);

    List<Transaction> findAllPendingTransactions();

    void processTransaction(Transaction transaction);

    Map<String,String> receiveTransactionUpdates();
}
