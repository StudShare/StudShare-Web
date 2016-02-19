package com.StudShare.service;

import com.StudShare.domain.LogToken;

import java.util.List;

public interface LogTokenManagerDao
{
    LogToken addLogToken(LogToken logToken);

    void deleteLogToken(LogToken logToken);

    LogToken findLogTokenBySSID(String ssid);

    List<LogToken> findLogTokensByIdSiteUser(long idSiteUser);

    void deleteExpiredLogTokens();

    LogToken findLogTokenById(LogToken logToken);

}
