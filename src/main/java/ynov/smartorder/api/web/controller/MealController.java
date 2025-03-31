package ynov.smartorder.api.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import smartorder.alison_api.web.dtos.MealDto;
import ynov.smartorder.api.persistence.repository.MealRepository;
import ynov.smartorder.api.web.mappers.MealDtoMapper;
import smartorder.alison_api.web.apis.MealsApi;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class MealController implements MealsApi {

    private final MealRepository mealRepository;
    private final MealDtoMapper mealMapper;


    @Override
    public ResponseEntity<Void> deleteMeal(String title) {
        mealRepository.deleteMeal(title);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<List<MealDto>> listMeals(String title) {
        if (title != null) {
            return mealRepository.getMeal(title) == null ? ResponseEntity.noContent().build() :
                    ResponseEntity.ok(Collections.singletonList(mealMapper.toDto(mealRepository.getMeal(title))));
        } else {
            return mealRepository.getAllMeals().isEmpty() ? ResponseEntity.noContent().build() :
                    ResponseEntity.ok(mealRepository.getAllMeals().stream().map(mealMapper::toDto).toList());
        }
    }

    @Override
    public ResponseEntity<List<String>> mealCategoriesGet() {
        return mealRepository.GetAllCategories().isEmpty() ? ResponseEntity.noContent().build() :
                ResponseEntity.ok(mealRepository.GetAllCategories());
    }

    @Override
    public ResponseEntity<MealDto> mealGet(String title) {
        return mealRepository.getMeal(title) == null ? ResponseEntity.noContent().build() :
                ResponseEntity.ok(mealMapper.toDto(mealRepository.getMeal(title)));
    }

    @Override
    public ResponseEntity<Void> publishMeal(List<MealDto> mealDto) {
        mealDto.forEach(meal -> mealRepository.createMeal(mealMapper.toEntity(meal)));
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> updateMeal(MealDto mealDto) {
        mealRepository.updateMeal(mealMapper.toEntity(mealDto));
        return ResponseEntity.ok().build();
    }

}

