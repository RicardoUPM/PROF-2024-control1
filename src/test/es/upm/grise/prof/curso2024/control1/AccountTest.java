package es.upm.grise.prof.curso2024.control1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

class AccountTest {

    @Test
    void testGetCurrentBalanceReturnsCorrectBalance() {
        final float INITIAL_BALANCE = 1000.0f;
        final float TRANSACTION_AMOUNT_1 = 500.0f;
        final float TRANSACTION_AMOUNT_2 = -200.0f;
        final float EXPECTED_BALANCE = 1300.0f;

        Transaction mockTransaction1 = mock(Transaction.class);
        Transaction mockTransaction2 = mock(Transaction.class);

        when(mockTransaction1.getAmount()).thenReturn(TRANSACTION_AMOUNT_1);
        when(mockTransaction2.getAmount()).thenReturn(TRANSACTION_AMOUNT_2);

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(mockTransaction1);
        transactions.add(mockTransaction2);

        Account account = new Account();

        try {
            var initialAmountField = Account.class.getDeclaredField("initialAmount");
            initialAmountField.setAccessible(true);
            initialAmountField.set(account, INITIAL_BALANCE);

            var transactionsField = Account.class.getDeclaredField("transactions");
            transactionsField.setAccessible(true);
            transactionsField.set(account, transactions);
        } catch (Exception e) {
            throw new RuntimeException("Error al configurar los campos de la cuenta", e);
        }

        assertEquals(EXPECTED_BALANCE, account.getCurrentBalance());
    }
}
