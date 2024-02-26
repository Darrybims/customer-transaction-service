package customer_tranx_service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import customer_tranx_service.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import customer_tranx_service.repository.AccountRepository;
import customer_tranx_service.utils.GenerateAccount;

import java.math.BigDecimal;
import java.util.List;
@SpringBootApplication
@RequiredArgsConstructor
@Slf4j
@EnableJpaAuditing
public class CustomerTransApp implements CommandLineRunner {
    @Autowired
    private AccountRepository accountRepository;

    public static void main(String[] args) {
        SpringApplication.run(CustomerTransApp.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
// Generate and save two accounts with unique 10-digit numeric account numbers
        Account account1 = new Account();
        account1.setAccountNumber(GenerateAccount.createAccountNumber());
        account1.setAccountName("Abimbola");
        account1.setBalance(BigDecimal.valueOf(5000));

        Account account2 = new Account();
        account2.setAccountNumber(GenerateAccount.createAccountNumber());
        account2.setAccountName("Ogundari");
        account2.setBalance(BigDecimal.valueOf(10000));

        Account account3 = new Account();
        account3.setAccountNumber(GenerateAccount.createAccountNumber());
        account3.setAccountName("Adenike");
        account3.setBalance(BigDecimal.valueOf(15000));
        accountRepository.saveAll(List.of(account1,account2, account3));


        log.info("Created account ==> {},{}, {}",account1,account2, account3);
    }
}
