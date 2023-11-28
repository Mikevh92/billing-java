package org.example.views;

import org.example.controller.CustomerController;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class HomeView extends JFrame {
    private JPanel panel1;
    private JTable table1;
    DefaultTableModel tableModel1;
    private JButton agregarClienteButton;
    private JButton eliminarClienteButton;
    private JMenuBar menuBar;
    private JMenu archivoMenu;
    private JMenuItem salirMenuItem;
    private JMenuItem agregarProducto;
    private JMenuItem agregarCliente;
    private JMenuItem crearCompra;
    private JFrame addClientView;

    public HomeView() {
        super("Home");
        setContentPane(panel1);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        tableModel1 = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 0;
            }
        };
        tableModel1.addColumn("ID");
        tableModel1.addColumn("NOMBRE");
        tableModel1.addColumn("APELLIDOS");
        tableModel1.addColumn("RFC");
        tableModel1.addColumn("RAZON");
        tableModel1.addColumn("CP");
        tableModel1.addColumn("REGIMEN");
        table1 = new JTable(tableModel1);
        getContentPane().add(new JScrollPane(table1));

        CustomerController customerController = new CustomerController();
        List<Object[]> customersData = customerController.getAllCustomers();
        for(Object[] data : customersData){
            tableModel1.addRow(data);
        }

        menuBar = new JMenuBar();
        archivoMenu = new JMenu("Archivo");
        /* acciones del menu Archivo*/
        salirMenuItem = new JMenuItem("Salir");
        agregarProducto = new JMenuItem("Agregar Producto");
        agregarCliente = new JMenuItem("Agregar Cliente");
        crearCompra = new JMenuItem("Crear Compra");

        archivoMenu.add(agregarCliente);
        archivoMenu.add(agregarProducto);
        archivoMenu.add(crearCompra);
        archivoMenu.add(salirMenuItem);
        menuBar.add(archivoMenu);
        setJMenuBar(menuBar);

        salirMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                System.exit(0);
            }
        });

        agregarCliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openClientView();
            }
        });

        agregarClienteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openClientView();
            }
        });

        eliminarClienteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteClient();
            }
        });

        table1.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    updateClient();
                }
            }
        });
    }

    private void openClientView(){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                addClientView = new AddClientView((DefaultTableModel)table1.getModel());
                addClientView.setSize(500, 500);
                addClientView.setVisible(true);
            }
        });
    }

    private void updateClient(){
        List<Object[]> rowData = new ArrayList<>();
        int[] selectedRows = table1.getSelectedRows();
        for (int row : selectedRows) {
            Object[] data = {
                    table1.getModel().getValueAt(row, 0), //id
                    table1.getModel().getValueAt(row, 1), //name
                    table1.getModel().getValueAt(row, 2), //lastname
                    table1.getModel().getValueAt(row, 3), //rfc
                    table1.getModel().getValueAt(row, 4), //razon
                    table1.getModel().getValueAt(row, 5), //cp
                    table1.getModel().getValueAt(row, 6) //regimen
            };

            rowData.add(data);
        }

        CustomerController customerController = new CustomerController();
        customerController.updateCustomerList(rowData);
    }

    private void deleteClient(){
        List<Integer> idsCustomerSelected = getItemSelected();
        CustomerController customerController = new CustomerController();
        customerController.deleteCustomerList(idsCustomerSelected);
        removeItemSelected();
    }

    private List<Integer> getItemSelected(){
        List<Integer> idsCustomerSelected = new ArrayList<>();

        int[] selectedRows = table1.getSelectedRows();
        for (int row : selectedRows) {
            idsCustomerSelected.add(Integer.parseInt(table1.getModel().getValueAt(row, 0).toString()));
        }

        return idsCustomerSelected;
    }

    private void removeItemSelected(){
        int[] selectedRows = table1.getSelectedRows();
        for (int i = 0; i < selectedRows.length / 2; i++) {
            int temp = selectedRows[i];
            selectedRows[i] = selectedRows[selectedRows.length - 1 - i];
            selectedRows[selectedRows.length - 1 - i] = temp;
        }

        for (int row : selectedRows) {
            tableModel1.removeRow(row);
        }
    }
}
