package com.datastructures;

import com.datastructures.model.LectureOfModule;
import com.datastructures.model.Study;
import com.datastructures.model.TestOfModule;
import com.datastructures.model.dsinsa.DSInSACourse;
import com.datastructures.model.dsinsa.DSInSAModule;
import com.google.gson.Gson;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void test() {
        LectureOfModule lectureOfModule = new LectureOfModule();
        lectureOfModule.setText("LECTURE");
        TestOfModule testOfModule = new TestOfModule();
        testOfModule.setAnswerOptions(Arrays.asList("Вариант 1", "Вариант 2", "Вариант 3", "Вариант 4"));
        testOfModule.setCorrectAnswer("Вариант 4");
        DSInSAModule dsInSAModule = new DSInSAModule();
        dsInSAModule.setLectures(Collections.singletonList(lectureOfModule));
        dsInSAModule.setTests(Collections.singletonList(testOfModule));

        Map<String, DSInSAModule> modules = new HashMap<>();
        modules.put("Модуль 1", dsInSAModule);

        Study study = new Study();
        DSInSACourse dsInSACourse = new DSInSACourse();
        dsInSACourse.setModules(modules);
        study.setDsInSACourse(dsInSACourse);

        System.out.println(new Gson().toJson(study));

    }
}