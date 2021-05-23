package com.datastructures.presenter.services;

import com.datastructures.model.dsinsa.DSInSAModule;
import com.datastructures.model.enums.SectionOfTrainingCatalog;
import com.datastructures.model.enums.dsinsa.DSInSAModuleTerm;

public interface TrainingService {
    public DSInSAModule readDataOfDSInSATrainingModule(SectionOfTrainingCatalog course,
                                                       DSInSAModuleTerm module,
                                                       String jsonText);
}
