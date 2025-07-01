package ynov.smartorder.api.persistence.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ynov.smartorder.api.common.utils.WithRandom;
import ynov.smartorder.api.persistence.entities.MealEty;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MealRepositoryJPATest implements WithRandom {

    @Mock
    private MealRepositoryJPA mealRepositoryJPA;

    @Test
    void shouldFindMealByTitle() {
        // given
        String title = randomString();
        MealEty mealEty = random(MealEty.class);
        when(mealRepositoryJPA.findByTitle(title)).thenReturn(Optional.of(mealEty));

        // when
        Optional<MealEty> result = mealRepositoryJPA.findByTitle(title);

        // then
        assertTrue(result.isPresent());
        assertEquals(mealEty, result.get());
    }

    @Test
    void shouldReturnDistinctCategories() {
        // given
        List<String> categories = List.of("Entrée", "Plat", "Dessert");
        when(mealRepositoryJPA.findDistinctCategories()).thenReturn(categories);

        // when
        List<String> result = mealRepositoryJPA.findDistinctCategories();

        // then
        assertEquals(categories, result);
    }
}
