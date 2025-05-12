package com.example.budget_plan.controllers
import org.springframework.web.bind.annotation.*
import org.springframework.stereotype.Service

@Service
class LoanService(private val loanRepository: com.example.budget_plan.repositories.LoanRepository) {

    fun getAllLoans(): List<com.example.budget_plan.model.Loan> = loanRepository.findAll()

    fun getLoanById(id: Int): com.example.budget_plan.model.Loan = loanRepository.findById(id).orElseThrow {
        RuntimeException("Loan not found with id: $id")
    }

    fun createLoan(loan: com.example.budget_plan.model.Loan): com.example.budget_plan.model.Loan = loanRepository.save(loan)

    fun updateLoan(id: Int, updatedLoan: com.example.budget_plan.model.Loan): com.example.budget_plan.model.Loan {
        val existingLoan = getLoanById(id)
        val updated = existingLoan.copy(
            user = updatedLoan.user,
            loanName = updatedLoan.loanName,
            loanSum = updatedLoan.loanSum,
            loanDeadline = updatedLoan.loanDeadline
        )
        return loanRepository.save(updated)
    }

    fun deleteLoan(id: Int) {
        if (!loanRepository.existsById(id)) {
            throw RuntimeException("Loan not found with id: $id")
        }
        loanRepository.deleteById(id)
    }
}

@RestController
@RequestMapping("/loan")
class LoanController(private val loanService: com.example.budget_plan.controllers.LoanService) {

    @GetMapping
    fun getAllLoans(): List<com.example.budget_plan.model.Loan> = loanService.getAllLoans()

    @GetMapping("/{id}")
    fun getLoanById(@PathVariable id: Int): com.example.budget_plan.model.Loan = loanService.getLoanById(id)

    @PostMapping
    fun createLoan(@RequestBody loan: com.example.budget_plan.model.Loan): com.example.budget_plan.model.Loan = loanService.createLoan(loan)

    @PutMapping("/{id}")
    fun updateLoan(@PathVariable id: Int, @RequestBody updatedLoan: com.example.budget_plan.model.Loan): com.example.budget_plan.model.Loan =
        loanService.updateLoan(id, updatedLoan)

    @DeleteMapping("/{id}")
    fun deleteLoan(@PathVariable id: Int) = loanService.deleteLoan(id)
}
