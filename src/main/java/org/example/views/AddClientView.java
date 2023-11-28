package org.example.views;

import org.example.controller.CustomerController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class AddClientView extends JFrame {
    private JPanel panel1;
    private JTextField nombreTextField;
    private JTextField apellidoTextField;
    private JTextField rfcTextField;
    private JTextField razonTextField;
    private JTextField cpTextField;
    private JComboBox regimenComboBox;
    private JButton guardarButton;
    private DefaultTableModel tableModel;

    public AddClientView(final DefaultTableModel tableModel) throws HeadlessException {
        super("Agregar Cliente");
        setContentPane(panel1);
        setLocationRelativeTo(null);

        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String name = nombreTextField.getText();
                    String lastName = apellidoTextField.getText();
                    String rfc = rfcTextField.getText();
                    String razon = razonTextField.getText();

                    if(nombreTextField.getText().isEmpty()){
                        throw new Exception("Se requiere que ingrese un nombre");
                    }

                    if(apellidoTextField.getText().isEmpty()){
                        throw new Exception("Se requiere que ingrese sus apellidos");
                    }

                    if(rfcTextField.getText().isEmpty()){
                        throw new Exception("Se requiere que ingrese su rfc");
                    }

                    if(razonTextField.getText().isEmpty()){
                        throw new Exception("Se requiere que ingrese su razon social");
                    }

                    if(cpTextField.getText().isEmpty()){
                        throw new Exception("Se requiere que ingrese un codigo postal");
                    }

                    int cp = Integer.valueOf(cpTextField.getText());
                    String regimen = String.valueOf(regimenComboBox.getModel().getSelectedItem()).substring(0,3);

                    int optionSelected = JOptionPane.showOptionDialog(null,
                            "¿Registrar Cliente?",
                            "Opciones",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            new String[]{"no", "si"},
                            "Si");

                    if(optionSelected == 1){
                        CustomerController customerController = new CustomerController();
                        int id_customer = customerController.addCustomer(
                                name,
                                lastName,
                                rfc,
                                razon,
                                cp,
                                regimen
                        );

                        Object[] rowData = {id_customer, name, lastName, rfc, razon, cp, regimen};
                        tableModel.addRow(rowData);
                        dispose();
                    }

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Ups", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
    }
}
