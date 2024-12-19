package com.loaneligibility.controller;

import com.loaneligibility.service.LoanEligibilityService;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoanEligibilityController {

    @Autowired
    private LoanEligibilityService loanEligibilityService;
    
    @CrossOrigin(origins = "*")  // Allows all origins to access this endpoint
    @GetMapping("/api/loans/check")
    public String checkLoanEligibility(@RequestParam int customerId,
                                       @RequestParam BigDecimal loanAmount) {
        return loanEligibilityService.checkLoanEligibility(customerId, loanAmount);
    }
}
