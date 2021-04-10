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
    @PostConstruct
    public void init(){
        config=new Config(levelRepository.findAll());
    }
    @Override
    public Config getConfig(){
        return this.config;
    }
    @Override
    public void setConfig(Config config){
        this.config=config;
    }
    @Override
    public void saveData(Config config){
        levelRepository.deleteAll();
        levelRepository.saveAll(config.getLevelList());
    }
    @Override
    public void addLevel(){
        List<Level> levelList=config.getLevelList();
        Level level=new Level();
        level.setId(levelList.size()+1);
        levelList.add(level);
        config.setLevelList(levelList);
    }
    public void deleteLevel(){
        List<Level> levelList=config.getLevelList();
        if(levelList.size()>1){
            levelList.remove(levelList.size()-1);
        }
        config.setLevelList(levelList);

    }
}
