package com.ptit.sqa.model;

import java.util.*;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.Data;
@Data
@Component
@AllArgsConstructor
public class Config {

    private List<Level> levelList;

}
