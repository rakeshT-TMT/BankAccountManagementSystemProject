package com.bank.dao;

import com.bank.models.Interest;

import java.util.List;

public interface InterestDAO {
    void addInterest(Interest interest);
    List<Interest> getInterestByAccount(int accountNumber);
}
