package com.example.budget_plan.services

import com.example.budget_plan.model.LoanChanges
import com.example.budget_plan.repositories.LoanChangesRepository
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.*
import org.springframework.http.HttpStatus

@Service
class LoanChangesService(private val loanChangesRepository: LoanChangesRepository) {

    fun getAllLoanChanges(): List<LoanChanges> = loanChangesRepository.findAll()

    fun getLoanChangeById(id: Int): LoanChanges = loanChangesRepository.findById(id).orElseThrow {
        RuntimeException("LoanChange not found with id: $id")
    }

    fun createLoanChange(loanChange: LoanChanges): LoanChanges = loanChangesRepository.save(loanChange)

    fun updateLoanChange(id: Int, updatedLoanChange: LoanChanges): LoanChanges {
        val existingLoanChange = getLoanChangeById(id)
        val updated = existingLoanChange.copy(
            loan = updatedLoanChange.loan,
            oldLoanValue = updatedLoanChange.oldLoanValue,
            newLoanValue = updatedLoanChange.newLoanValue,
            changeDate = updatedLoanChange.changeDate
        )
        return loanChangesRepository.save(updated)
    }

    fun deleteLoanChange(id: Int) {
        if (!loanChangesRepository.existsById(id)) {
            throw RuntimeException("LoanChange not found with id: $id")
        }
        loanChangesRepository.deleteById(id)
    }
}

@RestController
@RequestMapping("/loanChanges")
class LoanChangesController(private val loanChangesService: LoanChangesService) {

    @GetMapping
    fun getAllLoanChanges(): List<LoanChanges> = loanChangesService.getAllLoanChanges()

    @GetMapping("/{id}")
    fun getLoanChangeById(@PathVariable id: Int): LoanChanges = loanChangesService.getLoanChangeById(id)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createLoanChange(@RequestBody loanChange: LoanChanges): LoanChanges =
        loanChangesService.createLoanChange(loanChange)

    @PutMapping("/{id}")
    fun updateLoanChange(@PathVariable id: Int, @RequestBody updatedLoanChange: LoanChanges): LoanChanges =
        loanChangesService.updateLoanChange(id, updatedLoanChange)

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteLoanChange(@PathVariable id: Int) = loanChangesService.deleteLoanChange(id)
}
