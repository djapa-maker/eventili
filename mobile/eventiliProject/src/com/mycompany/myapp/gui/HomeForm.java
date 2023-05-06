/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.ui.Button;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;

/**
 *
 * @author bhk
 */
public class HomeForm extends Form{

    public HomeForm() {
        
        setTitle("Eventili");
        setLayout(BoxLayout.y());
        
//        add(new Label("Choose an option"));
        Button btnAddTask = new Button("Add Services");
//        Button btnListTasks = new Button("List Tasks");
        
        btnAddTask.addActionListener(e-> new ServicesGUI(this).show());
//        btnListTasks.addActionListener(e-> new ListTasksForm(this).show());
        addAll(btnAddTask);
        
        
    }
    
    
}
