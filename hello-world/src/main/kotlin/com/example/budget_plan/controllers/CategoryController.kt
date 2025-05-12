package com.example.budget_plan.controllers

import com.example.budget_plan.model.Category
import com.example.budget_plan.repositories.CategoryRepository
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import org.springframework.security.access.prepost.PreAuthorize

@Service
class CategoryService(private val categoryRepository: CategoryRepository) {

    fun getAllCategories(): List<Category> = categoryRepository.findAll()

    fun getCategoryById(id: Int): Category =
        categoryRepository.findById(id).orElseThrow {
            ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found with ID: $id")
        }

    fun createCategory(category: Category): Category = categoryRepository.save(category)

    fun updateCategory(id: Int, updatedCategory: Category): Category {
        val existingCategory = categoryRepository.findById(id).orElseThrow {
            ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found with ID: $id")
        }
        val updated = existingCategory.copy(categoryName = updatedCategory.categoryName)
        return categoryRepository.save(updated)
    }

    fun deleteCategory(id: Int) {
        if (!categoryRepository.existsById(id)) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found with ID: $id")
        }
        categoryRepository.deleteById(id)
    }
}

@RestController
@RequestMapping("/categories")
class CategoryController(private val categoryService: CategoryService) {

    @GetMapping
    fun getAllCategories(): List<Category> = categoryService.getAllCategories()

    @GetMapping("/{id}")
    fun getCategoryById(@PathVariable id: Int): Category = categoryService.getCategoryById(id)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ROLE_PREMIUM')")  // Только для пользователей с премиум-доступом
    fun createCategory(@RequestBody category: Category): Category = categoryService.createCategory(category)

    @PutMapping("/{id}")
    fun updateCategory(@PathVariable id: Int, @RequestBody updatedCategory: Category): Category =
        categoryService.updateCategory(id, updatedCategory)

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteCategory(@PathVariable id: Int) = categoryService.deleteCategory(id)
}
