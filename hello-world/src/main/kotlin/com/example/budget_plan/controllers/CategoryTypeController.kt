package com.example.budget_plan.controllers

import com.example.budget_plan.model.CategoryType
import com.example.budget_plan.repositories.CategoryTypeRepository
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.*

@Service
class CategoryTypeService(private val categoryTypeRepository: CategoryTypeRepository) {

    // Получение всех типов категорий
    fun getAllCategoryTypes(): List<CategoryType> = categoryTypeRepository.findAll()

    // Получение типа категории по id
    fun getCategoryTypeById(id: Int): CategoryType = categoryTypeRepository.findById(id).orElseThrow {
        RuntimeException("CategoryType not found with id: $id")
    }

    // Создание нового типа категории
    fun createCategoryType(categoryType: CategoryType): CategoryType = categoryTypeRepository.save(categoryType)

    // Обновление типа категории
    fun updateCategoryType(id: Int, updatedCategoryType: CategoryType): CategoryType {
        val existingType = getCategoryTypeById(id)
        val updated = existingType.copy(categoryType = updatedCategoryType.categoryType)
        return categoryTypeRepository.save(updated)
    }

    // Удаление типа категории
    fun deleteCategoryType(id: Int) {
        if (!categoryTypeRepository.existsById(id)) {
            throw RuntimeException("CategoryType not found with id: $id")
        }
        categoryTypeRepository.deleteById(id)
    }
}

@RestController
@RequestMapping("/category-types")
class CategoryTypeController(private val categoryTypeService: CategoryTypeService) {

    // Получение всех типов категорий
    @GetMapping
    fun getAllCategoryTypes() = categoryTypeService.getAllCategoryTypes()

    // Получение типа категории по id
    @GetMapping("/{id}")
    fun getCategoryTypeById(@PathVariable id: Int) = categoryTypeService.getCategoryTypeById(id)

    // Создание нового типа категории
    @PostMapping
    fun createCategoryType(@RequestBody categoryType: CategoryType) = categoryTypeService.createCategoryType(categoryType)

    // Обновление типа категории
    @PutMapping("/{id}")
    fun updateCategoryType(@PathVariable id: Int, @RequestBody categoryType: CategoryType) =
        categoryTypeService.updateCategoryType(id, categoryType)

    // Удаление типа категории
    @DeleteMapping("/{id}")
    fun deleteCategoryType(@PathVariable id: Int) = categoryTypeService.deleteCategoryType(id)
}
