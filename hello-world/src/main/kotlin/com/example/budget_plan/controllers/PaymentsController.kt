package com.example.budget_plan.controllers
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.*

@Service
class PaymentsService(private val paymentsRepository: com.example.budget_plan.repositories.PaymentsRepository) {

    fun getAllPayments(): List<com.example.budget_plan.model.Payments> = paymentsRepository.findAll()

    fun getPaymentById(id: Int): com.example.budget_plan.model.Payments = paymentsRepository.findById(id).orElseThrow {
        RuntimeException("Payment not found with id: $id")
    }

    fun createPayment(payment: com.example.budget_plan.model.Payments): com.example.budget_plan.model.Payments = paymentsRepository.save(payment)

    fun updatePayment(id: Int, updatedPayment: com.example.budget_plan.model.Payments): com.example.budget_plan.model.Payments {
        val existingPayment = getPaymentById(id)
        val updated = existingPayment.copy(
            paymentSum = updatedPayment.paymentSum,
            paymentPeriod = updatedPayment.paymentPeriod
        )
        return paymentsRepository.save(updated)
    }

    fun deletePayment(id: Int) {
        if (!paymentsRepository.existsById(id)) {
            throw RuntimeException("Payment not found with id: $id")
        }
        paymentsRepository.deleteById(id)
    }
}

@RestController
@RequestMapping("/payments")
class PaymentsController(private val paymentsService: com.example.budget_plan.controllers.PaymentsService) {

    @GetMapping
    fun getAllPayments() = paymentsService.getAllPayments()

    @GetMapping("/{id}")
    fun getPaymentById(@PathVariable id: Int) = paymentsService.getPaymentById(id)

    @PostMapping
    fun createPayment(@RequestBody payment: com.example.budget_plan.model.Payments) = paymentsService.createPayment(payment)

    @PutMapping("/{id}")
    fun updatePayment(@PathVariable id: Int, @RequestBody payment: com.example.budget_plan.model.Payments) = paymentsService.updatePayment(id, payment)

    @DeleteMapping("/{id}")
    fun deletePayment(@PathVariable id: Int) = paymentsService.deletePayment(id)
}
