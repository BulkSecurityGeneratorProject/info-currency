package pl.lodz.p.zzpj.managers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import pl.lodz.p.zzpj.config.TestContext;
import pl.lodz.p.zzpj.controllers.HomePageController;
import pl.lodz.p.zzpj.controllers.vm.CurrencyVM;
import pl.lodz.p.zzpj.model.ExchangeRatesSeries;

import java.util.ArrayList;
import static org.junit.Assert.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContext.class})
@WebAppConfiguration
public class CurrenciesManagerNBPTest {

    private MockMvc mockMvc;
    private CurrenciesManagerNBP currManager;
    private CurrencyVM request;
    private ArrayList<ExchangeRatesSeries> givenRates;
    private ExchangeRatesSeries givenRate;

    @Before
    public void setUp() {
        currManager = new CurrenciesManagerNBP();
        givenRates = new ArrayList<>();
        request = new CurrencyVM();
    }

    @Test
    public void shouldReturnListWith12Rates() {
        //given
        request.setCurrency("EUR");
        request.setLowHistDate("2016-07-03");
        request.setHighHistDate("2016-07-14");
        //when
        givenRates = currManager.getRangeRates(request);
        //then
        assertEquals(12, givenRates.size());
    }

    @Test
    public void shouldHaveRateFromFridayAtSaturday() {
        //given
        request.setCurrency("EUR");
        request.setLowHistDate("2016-09-02");
        request.setHighHistDate("2016-09-03");
        //when
        givenRates = currManager.getRangeRates(request);
        //then
        assertEquals(givenRates.get(0).getRates().getRate().getAsk(), givenRates.get(1).getRates().getRate().getAsk());
        assertEquals(givenRates.get(0).getRates().getRate().getBid(), givenRates.get(1).getRates().getRate().getBid());
        assertEquals(givenRates.get(0).getRates().getRate().getMid(), givenRates.get(1).getRates().getRate().getMid());
    }
}
