package com.loaneligibility.service;

import com.loaneligibility.model.Customer;
import com.loaneligibility.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

@Service
public class LoanEligibilityService {

    @Autowired
    private CustomerRepository customerRepository;

    public String checkLoanEligibility(int customerId, BigDecimal loanAmount) {
        // Fetch the customer from the repository
        Optional<Customer> customerOpt = customerRepository.findById(customerId);
        if (customerOpt.isEmpty()) {
            return "Customer not found!";
        }

        Customer customer = customerOpt.get();

        // 1. Check if the customer has a credit score of at least 650
        if (customer.getCreditScore() < 650) {
            return "Rejected: Credit Score too low, credit score: " + customer.getCreditScore();
        }

        // 2. Check if the customer has existing loans exceeding $10,000
        if (customer.getTotalDebt().compareTo(new BigDecimal("10000")) > 0) {
            return "Rejected: Outstanding loan limit exceeded, total debt: " + customer.getTotalDebt();
        }

        // 3. Check Income-to-Debt Ratio (IDR) if needed
        // Let's modify this logic to be more flexible:
        BigDecimal incomeToDebtRatio = customer.getTotalDebt().divide(customer.getYearlyIncome(), 2, RoundingMode.HALF_UP);
        if (incomeToDebtRatio.compareTo(new BigDecimal("0.60")) > 0) {  // Set threshold for the ratio here
            return "Rejected: Income-to-Debt Ratio too high, income-to-debt ratio: " + incomeToDebtRatio;
        }

        // 4. Check if the account age is at least 1 year
        LocalDate accountCreationDate = customer.getAccountCreationDate();
        Period period = Period.between(accountCreationDate, LocalDate.now());
        if (period.getYears() < 1) {
            return "Rejected: Account age less than 1 year, account creation date: " + accountCreationDate;
        }

        // 5. Check employment status (must be Employed or Self-Employed)
        if (customer.getEmploymentStatus().equalsIgnoreCase("Unemployed")) {
            return "Rejected: Employment status is Unemployed";
        }

        // 6. If all checks pass, approve the loan
        return "Approved: Eligible for loan, credit score: " + customer.getCreditScore();
    }
}
