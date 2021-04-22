package com.ptit.sqa.service.iml;

import com.ptit.sqa.model.Config;
import com.ptit.sqa.model.Level;
import com.ptit.sqa.repository.LevelRepository;
import com.ptit.sqa.service.ConfigurationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ConfigurationServiceIml implements ConfigurationService {
    private final LevelRepository levelRepository;
    private Config config;

    @Override
    public void ConfigInit() {
        config = new Config(levelRepository.findAll());
    }

    @Override
    public Config getConfig() {
        return this.config;
    }

    @Override
    public void setConfig(Config config) {
        this.config = config;
    }

    @Override
    public void saveConfigToDB(Config config) {
        levelRepository.deleteAll();
        levelRepository.saveAll(config.getLevelList());
    }

    @Override
    public void addLevelToForm() {
        List<Level> levelList = config.getLevelList();
        Level level = new Level();
        level.setId(levelList.size() + 1);
        Float maxValue=levelList.get(levelList.size() - 1).getMaxValue();maxValue+=Float.valueOf(String.valueOf(0.01));
        level.setMaxValue(maxValue);
        levelList.add(level);
        config.setLevelList(levelList);
    }

    public void deleteLevelFromForm(Integer idLevel) {
        List<Level> levelList = config.getLevelList();
        if (levelList.size() > 1) {
            levelList.remove(idLevel);
        }
        config.setLevelList(levelList);

    }
}
