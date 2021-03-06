package com.finnhvman.seriescal.services.update;

import com.finnhvman.seriescal.model.SeasonNews;

import java.util.List;

public interface SeasonUpdatesService {

    List<SeasonNews> getAllSeasonNews();

    boolean isQuerying();
    int getQueryProgress();
    void querySeasonUpdates();
}
