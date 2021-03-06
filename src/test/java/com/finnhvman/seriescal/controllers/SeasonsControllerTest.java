package com.finnhvman.seriescal.controllers;

import com.finnhvman.seriescal.model.Season;
import com.finnhvman.seriescal.model.SeasonNews;
import com.finnhvman.seriescal.model.SeasonSeed;
import com.finnhvman.seriescal.services.update.SeasonUpdatesService;
import com.finnhvman.seriescal.services.store.SeasonStoreService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

public class SeasonsControllerTest {

    @InjectMocks
    private SeasonsController underTest;
    @Mock
    private SeasonStoreService seasonStoreService;
    @Mock
    private SeasonUpdatesService seasonUpdatesService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testListSeasons() {
        Season season = new Season();
        season.setId(1L);
        season.setTitle("Season");

        Mockito.when(seasonStoreService.getAllSeason()).thenReturn(Collections.singletonList(season));


        List<Season> seasons = underTest.listSeasons();


        Assert.assertEquals(1, seasons.size());
        Assert.assertTrue(seasons.contains(season));
    }

    @Test
    public void testAddSeason() {
        SeasonSeed seasonSeed = new SeasonSeed();
        seasonSeed.setUrl("url");

        Season season = new Season();
        season.setTitle("title");
        season.setId(1L);

        Mockito.when(seasonStoreService.add(seasonSeed)).thenReturn(season);


        ResponseEntity responseEntity = underTest.addSeason(seasonSeed);


        Assert.assertEquals(season, responseEntity.getBody());
        Assert.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    public void testUpdateSeason() {
        SeasonSeed seasonSeed = new SeasonSeed();
        seasonSeed.setUrl("url");

        Season season = new Season();
        season.setTitle("title");
        season.setId(1L);

        Mockito.when(seasonStoreService.update(1L, seasonSeed)).thenReturn(season);


        ResponseEntity responseEntity = underTest.updateSeason(1L, seasonSeed);


        Assert.assertEquals(season, responseEntity.getBody());
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void testRemoveSeason() {
        ResponseEntity responseEntity = underTest.removeSeason(1L);


        Mockito.verify(seasonStoreService).remove(1L);
        Assert.assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }

    @Test
    public void testListSeasonNews() throws Exception {
        SeasonNews seasonNews = new SeasonNews();
        seasonNews.setId(1L);
        seasonNews.setTitle("Season");
        seasonNews.setNewEpisodes(Collections.singleton(1));

        Mockito.when(seasonUpdatesService.getAllSeasonNews()).thenReturn(Collections.singletonList(seasonNews));


        List<SeasonNews> seasons = underTest.listSeasonNews();


        Assert.assertEquals(1, seasons.size());
        Assert.assertTrue(seasons.contains(seasonNews));
    }

    @Test
    public void notQuerying_StartUpdatesQuery_ShouldStartANewQuery() throws Exception {
        Mockito.when(seasonUpdatesService.isQuerying()).thenReturn(false);


        ResponseEntity<String> response = underTest.startUpdatesQuery();


        Mockito.verify(seasonUpdatesService).querySeasonUpdates();

        Assert.assertEquals("Querying...", response.getBody());
        Assert.assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
    }

    @Test
    public void querying_StartUpdatesQuery_ShouldNotStartANewQuery() throws Exception {
        Mockito.when(seasonUpdatesService.isQuerying()).thenReturn(true);


        ResponseEntity<String> response = underTest.startUpdatesQuery();


        Mockito.verify(seasonUpdatesService, Mockito.never()).querySeasonUpdates();

        Assert.assertEquals("Querying...", response.getBody());
        Assert.assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
    }

    @Test
    public void testGetUpdatesQueryProgress() throws Exception {
        Mockito.when(seasonUpdatesService.getQueryProgress()).thenReturn(50);


        int progress = underTest.getUpdatesQueryProgress();


        Assert.assertEquals(50, progress);
    }
}
