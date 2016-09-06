package pl.lodz.p.zzpj.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import pl.lodz.p.zzpj.config.TestContext;
import pl.lodz.p.zzpj.managers.CurrenciesManagerNBP;
import pl.lodz.p.zzpj.model.ExchangeRatesSeries;
import pl.lodz.p.zzpj.repository.SearchRepository;

import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = {TestContext.class})
@WebAppConfiguration
public class CurrenciesControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private CurrenciesController currenciesController;

    private SearchRepository repository;

    @Before
    public void setUp() {
        initializeMocksController();
        mockMvc = MockMvcBuilders.standaloneSetup(currenciesController)
                .setViewResolvers(configureViewResolver())
                .build();
        repository = mock(SearchRepository.class);
    }

    private InternalResourceViewResolver configureViewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("resources/templates/");
        viewResolver.setSuffix(".html");
        return viewResolver;
    }

    private void initializeMocksController() {
        MockitoAnnotations.initMocks(this);
        currenciesController = new CurrenciesController();
        currenciesController.setCurrenciesManager(new CurrenciesManagerNBP(repository));
    }

    @Test
    public void showCurrenciesPage_shouldShowCurrenciesPage() throws Exception {
        mockMvc.perform(get("/currencies"))
                .andExpect(status().isOk());
                /*.andExpect(view().name("currencies"))
                .andExpect(forwardedUrl("resources/templates/lastCurrencies.html"));*/
    }

    @Test
    public void showConverterPage_shouldShowConverterPage() throws Exception {
        mockMvc.perform(get("/converter"))
                .andExpect(status().isOk());
    }

}
