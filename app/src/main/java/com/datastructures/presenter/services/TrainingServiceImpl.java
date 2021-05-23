package com.datastructures.presenter.services;

import android.os.Build;

import com.datastructures.model.Study;
import com.datastructures.model.dsinsa.DSInSAModule;
import com.datastructures.model.enums.SectionOfTrainingCatalog;
import com.datastructures.model.enums.dsinsa.DSInSAModuleTerm;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import androidx.annotation.RequiresApi;

public class TrainingServiceImpl implements TrainingService {
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public DSInSAModule readDataOfDSInSATrainingModule(SectionOfTrainingCatalog course,
                                                       DSInSAModuleTerm module, String jsonText) {
        Study study = getStudyFromJson(jsonText);

        return study.getDsInSACourse().getModules().get(module.toString());
    }

    private Study getStudyFromJson(String jsonText) {
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        JsonObject object = (JsonObject) parser.parse(jsonText);

        return gson.fromJson(object, Study.class);
    }
}
