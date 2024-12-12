package es.upm.grise.prof.curso2024.control1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
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
    
    @Test
    void mockitoTestGetAccountWithHighestBalanceReturnsCorrectAccountNumber() throws NoAccountsException {
        final String ACCOUNT_NUMBER_1 = "ACCOUNT_1";
        final String ACCOUNT_NUMBER_2 = "ACCOUNT_2";
        final float BALANCE_1 = 1000.0f;
        final float BALANCE_2 = 1500.0f;

        Account mockAccount1 = mock(Account.class);
        Account mockAccount2 = mock(Account.class);

        when(mockAccount1.getAccountNumber()).thenReturn(ACCOUNT_NUMBER_1);
        when(mockAccount1.getCurrentBalance()).thenReturn(BALANCE_1);

        when(mockAccount2.getAccountNumber()).thenReturn(ACCOUNT_NUMBER_2);
        when(mockAccount2.getCurrentBalance()).thenReturn(BALANCE_2);

        List<Account> accounts = new ArrayList<>();
        accounts.add(mockAccount1);
        accounts.add(mockAccount2);

        Customer customer = new Customer();

        try {
            var accountsField = Customer.class.getDeclaredField("accounts");
            accountsField.setAccessible(true);
            accountsField.set(customer, accounts);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Error al configurar las cuentas del cliente", e);
        }

        assertEquals(ACCOUNT_NUMBER_2, customer.getAccountWithHighestBalance());
    }
}
