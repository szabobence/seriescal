package com.finnhvman.seriescal.controllers;

import com.finnhvman.seriescal.model.Season;
import com.finnhvman.seriescal.model.SeasonNews;
import com.finnhvman.seriescal.model.SeasonSeed;
import com.finnhvman.seriescal.services.update.SeasonUpdatesService;
import com.finnhvman.seriescal.services.store.SeasonStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/seasons")
public class SeasonsController {

    // TODO validation

    @Autowired
    private SeasonStoreService seasonStoreService;
    @Autowired
    private SeasonUpdatesService seasonUpdatesService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Season> listSeasons() {
        return seasonStoreService.getAllSeason();
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Season> addSeason(@RequestBody @Valid SeasonSeed body) {
        Season season = seasonStoreService.add(body);
        return new ResponseEntity<>(season, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{seasonId}", method = RequestMethod.PUT)
    public ResponseEntity<Season> updateSeason(@PathVariable Long seasonId, @RequestBody @Valid SeasonSeed body) {
        Season season = seasonStoreService.update(seasonId, body);
        return new ResponseEntity<>(season, HttpStatus.OK);
    }

    @RequestMapping(value = "/{seasonId}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> removeSeason(@PathVariable Long seasonId) {
        seasonStoreService.remove(seasonId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/new-episodes", method = RequestMethod.GET)
    public List<SeasonNews> listSeasonNews() {
        return seasonUpdatesService.getAllSeasonNews();
    }

    @RequestMapping(value = "/updates-query", method = RequestMethod.POST)
    public ResponseEntity<String> startUpdatesQuery() {
        if (!seasonUpdatesService.isQuerying()) {
            seasonUpdatesService.querySeasonUpdates();
        }
        return new ResponseEntity<>("Querying...", HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "/updates-query", method = RequestMethod.GET)
    public Integer getUpdatesQueryProgress() {
        return seasonUpdatesService.getQueryProgress();
    }

}
