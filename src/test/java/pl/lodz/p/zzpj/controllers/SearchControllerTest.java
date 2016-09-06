package pl.lodz.p.zzpj.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import pl.lodz.p.zzpj.config.TestContext;
import pl.lodz.p.zzpj.managers.SearchManager;
import pl.lodz.p.zzpj.repository.SearchRepository;

import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by piotr on 06.09.16.
 */
@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = {TestContext.class})
@WebAppConfiguration
public class SearchControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private SearchController searchController;

    private SearchRepository repository;

    @Before
    public void setUp() {
        initializeMocksController();
        mockMvc = MockMvcBuilders.standaloneSetup(searchController)
                .setViewResolvers(configureViewResolver())
                .build();
    }

    private InternalResourceViewResolver configureViewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("resources/templates/");
        viewResolver.setSuffix(".html");
        return viewResolver;
    }

    private void initializeMocksController() {
        MockitoAnnotations.initMocks(this);
        repository = mock(SearchRepository.class);
        searchController = new SearchController();
        searchController.setSearchManager(new SearchManager(repository));
    }

    @Test
    public void getUserSearchHistory() throws Exception {
        mockMvc.perform(get("/history/get"))
                .andExpect(status().isOk());
    }
}
