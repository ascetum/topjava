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

        Map<LocalDate, Boolean> exceedDays = new HashMap<>();
        mealList.stream().collect(Collectors.groupingBy(o -> o.getDateTime().toLocalDate(), Collectors.summingInt(s -> s.getCalories()))).
                forEach(((localDate, integer) -> exceedDays.put(localDate, integer > caloriesPerDay)));

        List<UserMealWithExceed> resultList = new ArrayList<>();
        mealList.stream().filter(s -> (TimeUtil.isBetween(s.getDateTime().toLocalTime(), startTime, endTime)) &&
                exceedDays.get(s.getDateTime().toLocalDate())).
                forEach(d -> resultList.add(new UserMealWithExceed(d.getDateTime(), d.getDescription(), d.getCalories(), true)));

        return resultList;
    }
}
