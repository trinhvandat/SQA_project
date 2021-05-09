package com.ptit.sqa.model;

import java.util.*;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.Data;
@Data
@AllArgsConstructor
public class Config {

    private List<Level> levelList;
    public Integer getMinValue(int idLevel){
        if(idLevel==0)
            return 0;
        else
            return levelList.get(idLevel-1).getMaxValue()+1;

    }
}
