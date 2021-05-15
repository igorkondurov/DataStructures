package com.datastructures.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.NonNull;

/**
 * Учебный курс
 */
@Data
public class TrainingCourse {
    @NonNull
    private String section;

    private List<Boolean> signsOfPassingModules = new ArrayList<>();

    private CourseStatistics courseStatistics;
}
