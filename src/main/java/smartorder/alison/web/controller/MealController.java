package smartorder.alison.web.controller;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import smartorder.alison.domain.models.Meal;
import smartorder.alison.persistence.repository.MealRepository;
import smartorder.alison.web.apis.MealsApi;
import smartorder.alison.web.dtos.MealDto;
import smartorder.alison.web.mappers.MealDtoMapper;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class MealController implements MealsApi {

    private final MealRepository mealRepository;
    private final MealDtoMapper mealMapper;


    @Override
    public ResponseEntity<List<MealDto>> listMeals(String title) {
        if (title != null) {
            return mealRepository.getMeal(title) == null ? ResponseEntity.noContent().build() :
                    ResponseEntity.ok(Collections.singletonList(mealMapper.toDto(mealRepository.getMeal(title))));
        }else {
        return mealRepository.getMeal(title) == null ? ResponseEntity.noContent().build() :
                ResponseEntity.ok(mealRepository.getAllMeals().stream()
                        .map(mealMapper::toDto)
                        .toList());
        }

    }

    @Override
    public ResponseEntity<Void> publishMeal(MealDto mealDto) {
        mealRepository.createMeal(mealMapper.toEntity(mealDto));
        return ResponseEntity.ok().build();
    }
}
