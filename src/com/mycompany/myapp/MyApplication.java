package com.mycompany.myapp;


import com.codename1.components.OnOffSwitch;
import com.codename1.components.SpanLabel;
import static com.codename1.ui.CN.*;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Dialog;
import com.codename1.ui.Label;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.codename1.io.Log;
import com.codename1.ui.Toolbar;
import java.io.IOException;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.io.NetworkEvent;
import com.codename1.ui.Button;
import com.codename1.ui.CheckBox;
import com.codename1.ui.TextField;
import com.codename1.ui.spinner.Picker;

/**
 * This file was generated by <a href="https://www.codenameone.com/">Codename One</a> for the purpose 
 * of building native mobile applications using Java.
 */
public class MyApplication {

    private Form current;
    private Resources theme;

    public void init(Object context) {
        // use two network threads instead of one
        updateNetworkThreadCount(2);

        theme = UIManager.initFirstTheme("/theme");

        // Enable Toolbar on all Forms by default
        Toolbar.setGlobalToolbar(true);

        // Pro only feature
        Log.bindCrashProtection(true);

        addNetworkErrorListener(err -> {
            // prevent the event from propagating
            err.consume();
            if(err.getError() != null) {
                Log.e(err.getError());
            }
            Log.sendLogAsync();
            Dialog.show("Connection Error", "There was a networking error in the connection to " + err.getConnectionRequest().getUrl(), "OK", null);
        });        
    }
    
    public void start() {
        if(current != null){
            current.show();
            return;
        }
        
        //form 1 init
        Form f1 = new Form("Inscription", BoxLayout.y());
        
        //components init
        TextField tf_nom = new TextField("", "Saisir votre nom");
        
        TextField tf_prenom = new TextField("", "Saisir votre prenom");
        
        TextField tf_password = new TextField();
        tf_password.setHint("Password");
        tf_password.setConstraint(TextField.PASSWORD);
        
        Label genderLabel = new Label("Gender :");
        
        //f1.addComponent(new OnOffSwitch());
        OnOffSwitch onOffswitch = new OnOffSwitch();
        onOffswitch.setOff("Homme");
        onOffswitch.setOn("Femme");
        
        Picker date = new Picker();
        
        Label preferenceLabel = new Label("Preferences :");
        CheckBox tennisCB = new CheckBox("Tennis");
        CheckBox natationCB = new CheckBox("Natation");
        CheckBox footCB = new CheckBox("Foot");
        CheckBox lectureCB = new CheckBox("Lecture");
        
        Button btn = new Button("Valider");

        //Add Components to form 1
        f1.addAll(tf_nom, tf_prenom, tf_password, genderLabel, 
                onOffswitch, date, preferenceLabel, tennisCB, 
                natationCB, footCB, lectureCB, btn);

        f1.show();
        
        //=================================================================
        //=================================================================
        //=================================================================
        //form 2 init
        Form f2 = new Form("Details", BoxLayout.y());
        
        //Components init
        Label tb_nom = new Label("Nom : ");
        Label tb_prenom = new Label("Prenom : ");
        Label tb_gender = new Label("Genre : ");
        Label tb_date = new Label("Date de naissance : ");
        SpanLabel tb_prefs = new SpanLabel("Preferences : ");
        
        Button btnBack = new Button("Back");
        
        //Add components to Form 2
        f2.addAll(tb_nom, tb_prenom, tb_gender, tb_date, tb_prefs, btnBack);
        
        
        
        
        //=================================================================
        
        btn.addActionListener((e)->{
            
            //TF
            tb_nom.setText("Nom : " + tf_nom.getText());
            tb_prenom.setText("Prenom : " + tf_prenom.getText());
            //ON_OFF SWITCH
            if(onOffswitch.isValue()){
                
                tb_gender.setText("Genre : " + onOffswitch.getOn());
                
            }else{
                
                tb_gender.setText("Genre : " + onOffswitch.getOff());
            }
            //PICKER
            tb_date.setText("Date de naissance : "+ date.getText()); //getDate();
            
            //Preferences
            String prefs = "Preferences : \n";
            
            if(tennisCB.isSelected()){
                prefs += tennisCB.getText() + " \n";
            }
            if(natationCB.isSelected()){
                prefs += natationCB.getText() + " \n";
            }
            if(footCB.isSelected()){
                prefs += "Foot \n";
            }
            if(lectureCB.isSelected()){
                prefs += "Lecture \n";
            }
            
            
            tb_prefs.setText(prefs);
            f2.show();
        });
        
        //=================================================================
        
        btnBack.addActionListener((e)->{
            f1.show();
        });
        
        
        
        
        
    }

    public void stop() {
        current = getCurrentForm();
        if(current instanceof Dialog) {
            ((Dialog)current).dispose();
            current = getCurrentForm();
        }
    }
    
    public void destroy() {
    }

}
