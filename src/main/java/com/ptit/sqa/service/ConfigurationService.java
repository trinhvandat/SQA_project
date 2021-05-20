package com.ptit.sqa.service;

import com.ptit.sqa.model.Config;

import javax.annotation.PostConstruct;

public interface ConfigurationService {
    @PostConstruct
    void ConfigInit();
    Config getConfig();
    void setConfig(Config config);
    void saveConfigToDB(Config config);
    void addLevelToForm();
    void deleteLevelFromForm(Integer idLevel);
}
