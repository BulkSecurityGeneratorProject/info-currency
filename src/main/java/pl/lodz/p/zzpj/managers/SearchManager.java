package pl.lodz.p.zzpj.managers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.lodz.p.zzpj.domain.util.Search;
import pl.lodz.p.zzpj.repository.SearchRepository;

import java.util.List;

/**
 * Created by piotr on 05.09.16.
 */
@Component("searchManager")
public class SearchManager {
    Logger logger = Logger.getLogger(SearchManager.class);

    private SearchRepository repository;

    @Autowired
    public SearchManager(SearchRepository repository) {
        this.repository = repository;
    }

    public List<Search> getUserSearchHistory(int userId) {
        return repository.getUserSearchHistory(userId);
    }

    public void saveSearchHistoryItem(Search search) { repository.save(search); }
}
