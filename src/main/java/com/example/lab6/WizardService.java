package com.example.lab6;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WizardService {
    @Autowired
    private WizardRepository repository;
    protected Wizards wizards;

    public WizardService(){
        wizards = new Wizards();
    }
    public WizardService(WizardRepository repository){
        this.repository = repository;
    }
    public Wizards retrieveWizards(){
        wizards.wizardList.addAll(repository.findAll());
        return wizards;
    }
    public Wizard retrieveWizardById(Object id){
        return repository.findWizardById(id);
    }
    public Wizard createWizard(Wizard wizard){
        return repository.insert(wizard);
    }
    public boolean deleteWizard(Wizard wizard){
        try {
            repository.delete(wizard);
            return true;
        } catch (Exception e){
            return false;
        }
    }
    public Wizard updateWizard(Wizard wizard){
        return repository.save(wizard);
    }

}
