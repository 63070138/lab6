package com.example.lab6;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class WizardController {
    @Autowired
    private WizardService wizardService;


    @RequestMapping(value = "/wizards", method = RequestMethod.GET)
    public Wizards getWizards(){
        Wizards wizards = wizardService.retrieveWizards();
        return wizards;
    }

    @RequestMapping(value = "/addWizard", method = RequestMethod.POST)
    public boolean createWizard(@RequestBody Wizard wizard){
        wizardService.createWizard(wizard);
        return true;
    }

    @RequestMapping(value= "/updateWizard", method = RequestMethod.POST)
    public boolean updateWizard(@RequestBody Wizard wizard){
        Wizard oldWiz = wizardService.retrieveWizardById(wizard.get_id());
        if (oldWiz != null){
            wizardService.updateWizard(new Wizard(oldWiz.get_id(), wizard.getSex(), wizard.getName(), wizard.getSchool(), wizard.getHouse(), wizard.getPosition(), wizard.getMoney()));
            return true;
        } else {
            return false;
        }
    }
    @RequestMapping(value= "/deleteWizard", method = RequestMethod.POST)
    public boolean deleteWizard(@RequestBody Wizard wizard){
        wizardService.deleteWizard(wizard);
        return true;

    }

}
