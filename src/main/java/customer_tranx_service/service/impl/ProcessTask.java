package customer_tranx_service.service.impl;

import customer_tranx_service.model.Transaction;
import customer_tranx_service.service.TransactionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@EnableScheduling
public class ProcessTask {
    @Autowired
    private TransactionService transactionService;

    @Scheduled(fixedRateString = "${processing.rate}")
    @Transactional
    public void processPendingTransactions(){
        List<Transaction> pendingTransactions = transactionService.findAllPendingTransactions();
        System.out.printf("A total of %d transactions pending to be processed ",pendingTransactions.size());
        pendingTransactions.forEach(transactionService::processTransaction);

    }
}
