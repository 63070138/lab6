package com.example.lab6;

import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.ArrayList;

@RestController
public class Wizards implements Serializable {
    public ArrayList<Wizard> wizardList;
    public Wizards(){
        wizardList = new ArrayList<Wizard>();
    }
}
