package com.example.lab6;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;

@Route(value = "mainPage.it")
public class MainWizardView extends VerticalLayout {
    private TextField fnfield;
    private NumberField dolfield;
    private RadioButtonGroup<String> genderradio;
    private ComboBox<String> poscombo, schcombo, housecombo;
    private Button left, create, update, delete, right;
    private HorizontalLayout btngroup;
    private int selectedWizard;
    private Wizards allWizard;
    public MainWizardView(){
        fnfield = new TextField();
        dolfield = new NumberField();
        genderradio = new RadioButtonGroup<String>();
        poscombo = new ComboBox<String>();
        schcombo = new ComboBox<String>();
        housecombo = new ComboBox<String>();
        left = new Button("<<");
        create = new Button("Create");
        update = new Button("Update");
        delete = new Button("Delete");
        right = new Button(">>");
        btngroup = new HorizontalLayout();
        allWizard = new Wizards();
        selectedWizard = -1;
        fnfield.setPlaceholder("Fullname");
        genderradio.setLabel("Gender :");
        genderradio.setItems("Male", "Female");
        poscombo.setPlaceholder("Position");
        dolfield.setPrefixComponent(new Span("$"));
        dolfield.setLabel("Dollars");
        schcombo.setPlaceholder("School");
        housecombo.setPlaceholder("House");
        poscombo.setItems("Student","Teacher");
        schcombo.setItems("Hogwarts", "Beauxbatons", "Durmstrang");
        housecombo.setItems("Gryffindor", "Ravenclaw", "Hufflepuff", "Slyther");
        btngroup.add(left, create, update, delete, right);
        this.add(fnfield, genderradio, poscombo, dolfield, schcombo, housecombo, btngroup);
        allWizard = WebClient.create().get().uri("http://localhost:8080/wizards").retrieve().bodyToMono(Wizards.class).block();

        right.addClickListener(event -> {
            if (selectedWizard < allWizard.wizardList.size()-1){
                String gender = "";
                selectedWizard += 1;
                if(allWizard.wizardList.get(selectedWizard).getSex().equals("m")){
                    gender = "Male";
                } else if (allWizard.wizardList.get(selectedWizard).getSex().equals("f")) {
                    gender = "Female";
                }
                fnfield.setValue(allWizard.wizardList.get(selectedWizard).getName());
                genderradio.setValue(gender);
                dolfield.setValue((double) allWizard.wizardList.get(selectedWizard).getMoney());
                poscombo.setValue(allWizard.wizardList.get(selectedWizard).getPosition());
                schcombo.setValue(allWizard.wizardList.get(selectedWizard).getSchool());
                housecombo.setValue(allWizard.wizardList.get(selectedWizard).getHouse());
            }
        });

        left.addClickListener(event -> {
            if (selectedWizard > 0){
                String gender = "";
                selectedWizard -= 1;
                if(allWizard.wizardList.get(selectedWizard).getSex().equals("m")){
                    gender = "Male";
                } else if (allWizard.wizardList.get(selectedWizard).getSex().equals("f")) {
                    gender = "Female";
                }
                fnfield.setValue(allWizard.wizardList.get(selectedWizard).getName());
                genderradio.setValue(gender);
                dolfield.setValue((double) allWizard.wizardList.get(selectedWizard).getMoney());
                poscombo.setValue(allWizard.wizardList.get(selectedWizard).getPosition());
                schcombo.setValue(allWizard.wizardList.get(selectedWizard).getSchool());
                housecombo.setValue(allWizard.wizardList.get(selectedWizard).getHouse());
            }
        });

        create.addClickListener(event -> {
            String gender = "";
            if(genderradio.getValue().equals("Male")){
                gender = "m";
            } else if (genderradio.getValue().equals("Female")) {
                gender = "f";
            }
            Wizard newWizard = new Wizard(null, gender, fnfield.getValue(), schcombo.getValue(), housecombo.getValue(), poscombo.getValue(), dolfield.getValue().intValue());
            WebClient.create().post().uri("http://localhost:8080/addWizard").body(Mono.just(newWizard), Wizard.class).retrieve().bodyToMono(String.class).block();
            allWizard.wizardList.add(newWizard);
            Notification notification = Notification.show("Wizard has been create");
        });

        update.addClickListener(event -> {
            String gender = "";
            if(genderradio.getValue().equals("Male")){
                gender = "m";
            } else if (genderradio.getValue().equals("Female")) {
                gender = "f";
            }
            Wizard wizard = new Wizard(allWizard.wizardList.get(selectedWizard).get_id(), gender, fnfield.getValue(), schcombo.getValue(), housecombo.getValue(), poscombo.getValue(), dolfield.getValue().intValue());
            WebClient.create().post().uri("http://localhost:8080/updateWizard").body(Mono.just(wizard), Wizard.class).retrieve().bodyToMono(String.class).block();
            allWizard.wizardList.set(selectedWizard, wizard);
            Notification notification = Notification.show("Wizard has been update");
        });

        delete.addClickListener(event -> {
            String gender = "";
            if(genderradio.getValue().equals("Male")){
                gender = "m";
            } else if (genderradio.getValue().equals("Female")) {
                gender = "f";
            }
            Wizard wizard = allWizard.wizardList.get(selectedWizard);
            wizard.setSex(gender);
            WebClient.create().post().uri("http://localhost:8080/deleteWizard").body(Mono.just(wizard), Wizard.class).retrieve().bodyToMono(String.class).block();
            allWizard.wizardList.remove(wizard);
            Notification notification = Notification.show("Wizard has been delete");
        });
    }

}
