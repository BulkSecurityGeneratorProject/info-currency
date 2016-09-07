package pl.lodz.p.zzpj.controllers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.lodz.p.zzpj.domain.User;
import pl.lodz.p.zzpj.managers.CurrenciesManager;
import pl.lodz.p.zzpj.managers.SearchManager;
import pl.lodz.p.zzpj.model.SearchResponse;
import pl.lodz.p.zzpj.repository.UserRepository;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

/**
 * REST controller fo managing search features.
 */
@Controller
public class SearchController {
    private Logger logger = Logger.getLogger(SearchController.class);

    public SearchManager getSearchManager() {
        return searchManager;
    }

    public void setSearchManager(SearchManager searchManager) {
        this.searchManager = searchManager;
    }

    @Autowired
    private SearchManager searchManager;

    @Inject
    private UserRepository userRepository;

    @RequestMapping(value = "/history/get", method = RequestMethod.GET)
    public ResponseEntity<SearchResponse> getUserSearchHistory(HttpServletRequest request) {
        SearchResponse response = new SearchResponse();
        User user = (userRepository.findOneByLogin(request.getUserPrincipal().getName())).get();
        response.setData(searchManager.getUserSearchHistory(user.getId()));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
