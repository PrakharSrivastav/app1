package com.example.demo.integration;

import org.springframework.stereotype.Component;

@Component
public class ReverseIntegration {

    public String reverse(String data){
        return new StringBuilder(data).reverse().toString();
    }
}
