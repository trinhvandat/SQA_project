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
        //Xử lý khi tạo lần đầu
        if(levelList.size()==0){
            level.setId(1);
            level.setMaxValue(null);
            level.setPrice(0);
            levelList.add(level);
        }
        //Xử lý nếu số bậc lớn hơn 1
        else if(levelList.size()>1){
            //config giá trị mới
            level.setId(levelList.size());
            int maxValue=levelList.get(levelList.size() - 2).getMaxValue();maxValue++;
            level.setMaxValue(maxValue);
            level.setPrice(0);
            levelList.add(levelList.size()-1,level);
            //config giá trị cuối
            level=levelList.get(levelList.size()-1);
            level.setId(levelList.size());
            levelList.set(levelList.size()-1,level );
        }
        //Xử lý nếu xố bậc bằng 1
        else{
            //config giá trị mới
            level.setId(levelList.size());
            level.setMaxValue(1);
            level.setPrice(0);
            levelList.add(levelList.size()-1,level);
            //config giá trị cuối
            level=levelList.get(levelList.size()-1);
            level.setId(levelList.size());
            levelList.set(levelList.size()-1,level );
        }
        config.setLevelList(levelList);
    }

    public void deleteLevelFromForm(Integer idLevel) {
        List<Level> levelList = config.getLevelList();
        Level level=new Level();
        if (levelList.size() > 1) {
            levelList.remove(levelList.get(idLevel));
            for(int i=idLevel;i<levelList.size();i++){
                level=levelList.get(i);
                level.setId(level.getId()-1);
                levelList.set(i,level);
            }
        }
        config.setLevelList(levelList);

    }
}
