package com.example.bankmanagementsystem.Controller;


import com.example.bankmanagementsystem.ApiResponse.ApiResponse;
import com.example.bankmanagementsystem.Model.CustomerModel;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/bank")
public class CustomerController {

    ArrayList<CustomerModel> customers=new ArrayList<>();

    @GetMapping("/getcustomers")
    public ArrayList<CustomerModel> getCustomers(){
        return customers;
    }

    @PostMapping("/addcustomer")
    public ApiResponse addCustomer(@RequestBody CustomerModel customer){
        customers.add(customer);
        return new ApiResponse("Added","200");
    }


    @PutMapping("/updatecustomer/{index}")
    public ApiResponse updateCustomer(@PathVariable int index, @RequestBody CustomerModel customer){
        if(index<customers.size()) {
            customer.setID(customers.get(index).getID());
                customers.set(index, customer);
                return new ApiResponse("Updated", "200");
        }
        return new ApiResponse("Can't update","404");

    }

    @DeleteMapping("/deletecustomer/{index}")
    public ApiResponse deleteCustomer(@PathVariable int index){

        if(index<customers.size()){
        customers.remove(index);
        return new ApiResponse("Deleted","200");
        }
        return new ApiResponse("Can't delete","404");

    }

    @GetMapping ("/deposit/{index}/{money}")
    public ApiResponse depositMoney(@PathVariable int index,@PathVariable int money) {
    if(index<customers.size())
    {
        customers.get(index).setBalance((customers.get(index).getBalance()) + money);
        return new ApiResponse("Balance updated", "200");
    }
        return new ApiResponse("Can't Update a balance","404");
    }

    @GetMapping ("/withdrawmoney/{index}/{money}")
    public ApiResponse withdrawMoney(@PathVariable int index,@PathVariable int money) {
        if(index<customers.size()) {
            if (customers.get(index).getBalance() > money) {
                customers.get(index).setBalance((customers.get(index).getBalance()) - money);
                return new ApiResponse("Balance updated", "200");
            }
            return new ApiResponse("Can't Update a balance, the balance is insufficient", "404");

        }
        return new ApiResponse("Can't Update a balance","404");
    }


}
