package customer_tranx_service.service.impl;

import customer_tranx_service.http_request.HttpRequest;
import customer_tranx_service.model.TransactionType;
import customer_tranx_service.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import customer_tranx_service.model.Account;
import customer_tranx_service.model.Transaction;
import customer_tranx_service.model.TransactionStatus;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import customer_tranx_service.repository.AccountRepository;
import customer_tranx_service.repository.TransactionRepository;
import customer_tranx_service.utils.GenerateAccount;

import java.math.BigDecimal;
import java.util.*;

import static customer_tranx_service.model.TransactionStatus.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public Map<String, String> initiateTransaction(HttpRequest request) {
        log.info("Initial Request for transaction => {}",request);
        Map<String, String> output = new HashMap<>();
        try{
            accountValidate(request);
            Transaction transaction = new Transaction();

            BeanUtils.copyProperties(request,transaction);

            Account account = accountRepository.findByAccountNumber(request.getAccountId()).get();

            transaction.setAccount(account);
            transaction.setTransactionStatus(PENDING);

            String createdDateString = new Date().toString().replaceAll("[^0-9]", "");
            String transactionId = GenerateAccount.generateRandom(3).concat(createdDateString);
            transaction.setTransactionId(transactionId);

            transactionRepository.save(transaction);

            output.put("response",String.format("Generated transaction Id = %s",transactionId));

            return output;

        }catch (Exception e){
            output.put("error","Failed to initiate Transaction :: "+e.getMessage());
            return output;
        }
    }

    @Override
    public Map<String, String> checkTransactionStatus(String transactionId) {
        Map<String,String> status = new HashMap<>();
        Transaction transaction = validateTransactionId(transactionId);
        TransactionStatus transactionStatus = transaction.getTransactionStatus();
        status.put("status",String.format("Transaction status :: %s",transactionStatus));
        status.put("reason",transaction.getNarration());
        return status;
    }

    @Override
    public List<Transaction> findAllPendingTransactions() {
        return transactionRepository.findAllByTransactionStatus(PENDING);
    }

    @Override
    public void processTransaction(Transaction transaction) {
        Account account = transaction.getAccount();

        TransactionType transactionType = transaction.getTransactionType();
        BigDecimal transactionAmount = transaction.getAmount();
        Transaction savedTransaction = new Transaction();
        switch (transactionType){
            case DEBIT -> {

                if (account.getBalance().compareTo(transactionAmount) < 0){
                    transaction.setTransactionStatus(FAILED);
                    transaction.setNarration("Insufficient Balance for debit transaction");
                } else{
                    account.setBalance(account.getBalance().subtract(transactionAmount));
                    accountRepository.save(account);
                    transaction.setTransactionStatus(SUCCESS);
                    transaction.setNarration("Debit transaction successful");
                }
                savedTransaction = transactionRepository.save(transaction);

            }

            case CREDIT -> {
                account.setBalance(account.getBalance().add(transactionAmount));
                accountRepository.save(account);
                transaction.setTransactionStatus(SUCCESS);
                transaction.setNarration("Credit transaction successful");
                savedTransaction = transactionRepository.save(transaction);
            }

            default -> {
                transaction.setTransactionStatus(FAILED);
                transaction.setNarration("Invalid transaction type");
                savedTransaction = transactionRepository.save(transaction);

            }
        }

        log.info("Transaction with Id {}, updated to {} ",transaction.getId(),savedTransaction.getTransactionStatus());
    }

    @Override
    public Map<String, String> receiveTransactionUpdates() {
        return null;
    }

    public void accountValidate(HttpRequest request){
        String accountId = request.getAccountId();
        if(!accountRepository.existsByAccountNumber(accountId)) {
            throw new NotFoundException("Account does not exist");
        }
    }

    private Transaction validateTransactionId(String transactionId) {
        if(Objects.isNull(transactionId) || transactionId.length() < 10){
            throw new IllegalArgumentException("Invalid transaction id");
        }
        return transactionRepository.findByTransactionId(transactionId)
                .orElseThrow(() -> new NotFoundException("TransactionId does not exist"));
    }
}
