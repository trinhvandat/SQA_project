package com.ptit.sqa.service;

import com.ptit.sqa.model.Config;

import javax.annotation.PostConstruct;

public interface ConfigurationService {
    @PostConstruct
    void init();
    Config getConfig();
    void setConfig(Config config);
    void saveData(Config config);
    void addLevel();
    void deleteLevel();
}
