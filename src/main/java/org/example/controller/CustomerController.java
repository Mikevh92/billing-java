package org.example.controller;

import org.example.model.Customer;
import org.example.repository.CustomerRepository;

import java.util.ArrayList;
import java.util.List;

public class CustomerController {
    public int addCustomer(
            String name,
            String lastName,
            String rfc,
            String razon,
            int cp,
            String regimen)
    {
        Customer customer = new Customer();
        customer.setName(name);
        customer.setLastname(lastName);
        customer.setRfc(rfc);
        customer.setRazon(razon);
        customer.setCp(cp);
        customer.setRegimen(regimen);
        customer.setActive(1);

        CustomerRepository customerRepository = new CustomerRepository();
        return customerRepository.add(customer);
    }

    public List<Object[]> getAllCustomers(){
        CustomerRepository customerRepository = new CustomerRepository();
        List<Customer> customerList = customerRepository.getList();

        List<Object[]> customerData = new ArrayList<>();

        for(Customer customer : customerList){
            Object[] rowData = {
                    customer.getId().toString(),
                    customer.getName(),
                    customer.getLastname(),
                    customer.getRfc(),
                    customer.getRazon(),
                    customer.getCp().toString(),
                    customer.getRegimen()
            };

            customerData.add(rowData);
        }

        return customerData;
    }

    public void deleteCustomerList(List<Integer> ids){
        for (Integer id : ids) {
            CustomerRepository customerRepository = new CustomerRepository();
            customerRepository.delete(id);
        }
    }

    public void updateCustomerList(List<Object[]> customerData){
        for(Object[] data : customerData){
            Customer customer = new Customer();
            customer.setId(Integer.valueOf(data[0].toString()));
            customer.setName((String) data[1]);
            customer.setLastname((String) data[2]);
            customer.setRfc((String) data[3]);
            customer.setRazon((String) data[4]);
            customer.setCp(Integer.valueOf(data[5].toString()));
            customer.setRegimen((String) data[6]);
            customer.setActive(1);

            CustomerRepository customerRepository = new CustomerRepository();
            customerRepository.update(customer);
        }
    }
}
