package com.galandarf.bank

import com.galandarf.bank.transaction.Transaction
import com.galandarf.bank.transaction.TransactionRepository
import com.galandarf.bank.Account
import com.galandarf.bank.StatementPrinter
import kotlin.test.BeforeTest
import kotlin.test.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class AccountShould {

    private var statementPrinter = mock(StatementPrinter::class.java)
    private var transactionRepository = mock(TransactionRepository::class.java)

    private lateinit var account: Account

    @BeforeTest
    fun initialize() {
        account = Account(transactionRepository, statementPrinter)
    }

    @Test
    fun `store a deposit transaction`() {
        val amount = 100

        account.deposit(amount)

        verify(transactionRepository).addDeposit(100)
    }

    @Test
    fun `store a withdraw transaction`() {
        val amount = 100

        account.withdraw(amount)

        verify(transactionRepository).addWithdrawal(100)
    }

    @Test
    fun `print a statement`() {
        val transactions = listOf(Transaction("09/07/2021", 100))
        `when`(transactionRepository.allTransactions()).thenAnswer { transactions }

        account.printStatement()

        verify(statementPrinter).print(transactions)
    }
}
