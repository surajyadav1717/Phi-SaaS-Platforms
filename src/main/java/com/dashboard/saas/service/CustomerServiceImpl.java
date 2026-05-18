//package com.dashboard.saas.service;
//
//import com.dashboard.saas.dtos.CustomerRequestDto;
//import com.dashboard.saas.dtos.CustomerResponseDto;
//import com.dashboard.saas.entities.Customer;
//import com.dashboard.saas.repositories.CustomerRepository;
//import com.dashboard.saas.repositories.OrderRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//
//@Service
//public class CustomerServiceImpl implements CustomerService{
//
//
//    @Autowired
//    private CustomerRepository customerRepository;
//
//    @Autowired
//    private OrderRepository orderRepository;
////
////    public Customer createCustomer (CustomerRequestDto customerRequestDto) {
////        // Create a new customer
////        Customer customer = new Customer();
////        customer.setName(customerRequestDto.getName());
////        customer.setEmail(customerRequestDto.getEmail());
////
////        // Save customer to the database (this will auto-generate the ID)
////        return customerRepository.save(customer);
////    }
//
//
//
//    @Override
//    public CustomerResponseDto createCustomer(CustomerRequestDto customerRequestDto) {
//        // Create a new customer
//        Customer customer = new Customer();
//        customer.setName(customerRequestDto.getName());
//        customer.setEmail(customerRequestDto.getEmail());
//
//        // Save customer to the database (this will auto-generate the ID)
//        customer = customerRepository.save(customer);
//
//        // Convert the entity to DTO and return it
//        return new CustomerResponseDto(customer.getCustomerId(), customer.getName(), customer.getEmail());
//    }
//
//}
