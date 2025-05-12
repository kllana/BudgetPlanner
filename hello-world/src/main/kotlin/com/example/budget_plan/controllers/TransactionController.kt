package com.example.budget_plan.controllers
import com.example.budget_plan.model.Transaction
import org.springframework.web.bind.annotation.*
import org.springframework.stereotype.Service

@Service
class TransactionService(private val transactionRepository: com.example.budget_plan.repositories.TransactionRepository) {

    fun getAllTransactions(): List<Transaction> = transactionRepository.findAll()

    fun getTransactionById(id: Int): Transaction = transactionRepository.findById(id).orElseThrow {
        RuntimeException("Transaction not found with id: $id")
    }

    fun createTransaction(transaction: Transaction): Transaction = transactionRepository.save(transaction)

    fun updateTransaction(id: Int, updatedTransaction: Transaction): Transaction {
        val existingTransaction = getTransactionById(id)
        val updated = existingTransaction.copy(
            user = updatedTransaction.user,
            transactionName = updatedTransaction.transactionName,
            transactionAmount = updatedTransaction.transactionAmount,
            transactionDate = updatedTransaction.transactionDate,
            transactionDescription = updatedTransaction.transactionDescription,
            transactionType = updatedTransaction.transactionType,
            payment = updatedTransaction.payment
        )
        return transactionRepository.save(updated)
    }

    fun deleteTransaction(id: Int) {
        if (!transactionRepository.existsById(id)) {
            throw RuntimeException("Transaction not found with id: $id")
        }
        transactionRepository.deleteById(id)
    }
}

@RestController
@RequestMapping("/transaction")
class TransactionController(private val transactionService: com.example.budget_plan.controllers.TransactionService) {

    @GetMapping
    fun getAllTransactions(): List<Transaction> = transactionService.getAllTransactions()

    @GetMapping("/{id}")
    fun getTransactionById(@PathVariable id: Int): Transaction = transactionService.getTransactionById(id)

    @PostMapping
    fun createTransaction(@RequestBody transaction: Transaction): Transaction = transactionService.createTransaction(transaction)

    @PutMapping("/{id}")
    fun updateTransaction(@PathVariable id: Int, @RequestBody updatedTransaction: Transaction): Transaction =
        transactionService.updateTransaction(id, updatedTransaction)

    @DeleteMapping("/{id}")
    fun deleteTransaction(@PathVariable id: Int) = transactionService.deleteTransaction(id)
}
