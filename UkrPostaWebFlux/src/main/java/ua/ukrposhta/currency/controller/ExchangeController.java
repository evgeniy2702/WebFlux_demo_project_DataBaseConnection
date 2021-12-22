package ua.ukrposhta.currency.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;
import ua.ukrposhta.currency.model.Exchange;
import ua.ukrposhta.currency.service.CurrencyService;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

@RestController
@RequestMapping("/exchange")
public class ExchangeController {


    private final CurrencyService currencyService;

    public ExchangeController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping
    public Exchange listExchange() throws ParserConfigurationException, SAXException, IOException {

        return currencyService.exchangeCurrentDay();
    }
}
