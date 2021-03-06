package com.StudShare.service;

import com.StudShare.domain.Rate;

import java.util.List;

public interface RateManagerDAO
{
    Rate addRate(Rate rate);
    void deleteRate(Rate rate);
    void deleteAll(long idNote);
    List<Rate> findRatesByIdNote(long idNote);
    List<Rate> findRatesByIdSiteUser(long idSiteUser);
}
