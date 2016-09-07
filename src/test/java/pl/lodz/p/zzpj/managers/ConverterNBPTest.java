package pl.lodz.p.zzpj.managers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import pl.lodz.p.zzpj.config.TestContext;
import pl.lodz.p.zzpj.vm.ConverterVM;
import pl.lodz.p.zzpj.model.ExchangeRatesSeries;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContext.class})
@WebAppConfiguration
public class ConverterNBPTest {

    @InjectMocks
    private ConverterNBP converter;

    private ConverterVM converterVM;
    private ExchangeRatesSeries currencyRates1;
    private ExchangeRatesSeries currencyRates2;
    private CurrenciesManagerNBP currenciesManager;

    @Before
    public void setUp() {
        setUpExchangeRatesSeries();
        setUpConverterVM();
        setUpCurrenciesManager();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldReturnTwoThousandAfterConverting() {
        assertEquals("2000.00", converter.convert(converterVM));
    }

    private void setUpConverterVM() {
        converterVM = mock(ConverterVM.class, RETURNS_DEEP_STUBS);
        when(converterVM.getMoneyValue()).thenReturn("1000.00");
    }

    private void setUpExchangeRatesSeries() {
        currencyRates1 = mock(ExchangeRatesSeries.class, RETURNS_DEEP_STUBS);
        currencyRates2 = mock(ExchangeRatesSeries.class, RETURNS_DEEP_STUBS);
        when(currencyRates1.getRates().getRate().getMid()).thenReturn(new BigDecimal(7.0));
        when(currencyRates2.getRates().getRate().getMid()).thenReturn(new BigDecimal(3.5));
    }

    private void setUpCurrenciesManager() {
        currenciesManager = mock(CurrenciesManagerNBP.class, RETURNS_DEEP_STUBS);
        when(currenciesManager.getCurrencyRate(any())).thenReturn(currencyRates1, currencyRates2);
    }


}
