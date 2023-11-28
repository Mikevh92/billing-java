package org.example;

import org.example.model.Customer;
import org.example.repository.CustomerRepository;
import org.example.views.HomeView;

import javax.swing.*;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame homeFrame = new HomeView();
                homeFrame.setSize(700,500);
                homeFrame.setVisible(true);
            }
        });
    }
}