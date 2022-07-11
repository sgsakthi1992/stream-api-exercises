package demo.streamapi.controller;

import demo.streamapi.exercises.Exercises;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/exercises")
public class ExercisesController {
    private final Exercises exercises;

    public ExercisesController(Exercises exercises) {
        this.exercises = exercises;
    }

    @GetMapping
    public void getExercises() {
        // exercises.exercise1();
        // exercises.exercise2();
        // exercises.exercise3();
        // exercises.exercise4();
        // exercises.exercise5();
        // exercises.exercise6();
        // exercises.exercise7();
        // exercises.exercise8();
        // exercises.exercise9();
        // exercises.exercise10();
        // exercises.exercise11();
        // exercises.exercise12();
        // exercises.exercise13();
        // exercises.exercise14();
        exercises.exercise15();
    }
}
