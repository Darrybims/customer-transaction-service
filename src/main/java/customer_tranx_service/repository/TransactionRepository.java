package customer_tranx_service.repository;

import customer_tranx_service.model.Transaction;
import customer_tranx_service.model.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findAllByTransactionStatus(TransactionStatus status);
    Optional<Transaction> findByTransactionId(String transactionId);
}
