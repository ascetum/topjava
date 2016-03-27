package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        Map<Integer, Long> timeComplexityMap = new TreeMap<>();
            List<UserMeal> mealList = Arrays.asList(
                    new UserMeal(LocalDateTime.of(2015, Month.MAY, 29, 10, 0), "Завтрак", 500),
                    new UserMeal(LocalDateTime.of(2015, Month.MAY, 29, 13, 0), "Обед", 1000),
                    new UserMeal(LocalDateTime.of(2015, Month.MAY, 29, 20, 0), "Ужин", 400),
                    new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                    new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                    new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                    new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                    new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                    new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510),
                    new UserMeal(LocalDateTime.now(), "eda", 1)
            );
            long startTime = System.nanoTime();
            for (UserMealWithExceed meal : getFilteredMealsWithExceeded(mealList, LocalTime.of(1, 0), LocalTime.of(22, 0), 200)) {
                System.out.println(meal.getDateTime());
            }

//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed> getFilteredMealsWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with correctly exceeded field

        Map<LocalDate, Integer> exceedDays =
        mealList.stream().collect(Collectors.groupingBy(o -> o.getDateTime().toLocalDate(), Collectors.summingInt(UserMeal::getCalories)));

        return
        mealList.stream().filter(um -> (TimeUtil.isBetween(um.getDateTime().toLocalTime(), startTime, endTime)))
                .map(um -> new UserMealWithExceed(um.getDateTime(), um.getDescription(), um.getCalories(),
                        exceedDays.get(um.getDateTime().toLocalDate()) > caloriesPerDay)).collect(Collectors.toList());
    }
}
