package es.upm.grise.prof.curso2024.control1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;


class CustomerTest {

    @Test
    void testGetAccountWithHighestBalanceThrowsExceptionWhenNoAccounts() {
        Customer customer = new Customer();

        assertThrows(NoAccountsException.class, customer::getAccountWithHighestBalance);
    }
    
    @Test
    void testGetAccountWithHighestBalanceReturnsCorrectAccountNumber() throws NoAccountsException {
        final String ACCOUNT_NUMBER_1 = "ACCOUNT_1";
        final String ACCOUNT_NUMBER_2 = "ACCOUNT_2";
        final float BALANCE_1 = 1000.0f;
        final float BALANCE_2 = 1500.0f;

        Account account1 = createAccount(ACCOUNT_NUMBER_1, BALANCE_1);
        Account account2 = createAccount(ACCOUNT_NUMBER_2, BALANCE_2);

        Customer customer = new Customer();
        addAccountsToCustomer(customer, account1, account2);

        assertEquals(ACCOUNT_NUMBER_2, customer.getAccountWithHighestBalance());
    }

    private Account createAccount(String accountNumber, float initialBalance) {
        Account account = new Account();

        try {
            var accountNumberField = Account.class.getDeclaredField("accountNumber");
            accountNumberField.setAccessible(true);
            accountNumberField.set(account, accountNumber);
        } catch (Exception e) {
            throw new RuntimeException("Error al configurar el número de cuenta", e);
        }

        try {
            var initialAmountField = Account.class.getDeclaredField("initialAmount");
            initialAmountField.setAccessible(true);
            initialAmountField.set(account, initialBalance);
        } catch (Exception e) {
            throw new RuntimeException("Error al configurar el saldo inicial", e);
        }

        return account;
    }

    private void addAccountsToCustomer(Customer customer, Account... accounts) {
        try {
            var accountsField = Customer.class.getDeclaredField("accounts");
            accountsField.setAccessible(true);
            List<Account> customerAccounts = (List<Account>) accountsField.get(customer);
            for (Account account : accounts) {
                customerAccounts.add(account);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al asociar cuentas al cliente", e);
        }
    }
}
