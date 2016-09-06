package pl.lodz.p.zzpj.controllers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.lodz.p.zzpj.managers.CurrenciesManager;
import pl.lodz.p.zzpj.managers.SearchManager;
import pl.lodz.p.zzpj.model.SearchResponse;

/**
 * Created by piotr on 05.09.16.
 */
@Controller
public class SearchController {
    Logger logger = Logger.getLogger(SearchController.class);

    public SearchManager getSearchManager() {
        return searchManager;
    }

    public void setSearchManager(SearchManager searchManager) {
        this.searchManager = searchManager;
    }

    @Autowired
    private SearchManager searchManager;

    @RequestMapping(value = "/history/get", method = RequestMethod.GET)
    public ResponseEntity<SearchResponse> getUserSearchHistory() {
        SearchResponse response = new SearchResponse();
        response.setData(searchManager.getUserSearchHistory(1));
        return new ResponseEntity<SearchResponse>(response, HttpStatus.OK);
    }

}
