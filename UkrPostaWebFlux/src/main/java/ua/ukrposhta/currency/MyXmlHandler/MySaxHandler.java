package ua.ukrposhta.currency.MyXmlHandler;

import org.springframework.stereotype.Component;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import ua.ukrposhta.currency.model.Currency;
import ua.ukrposhta.currency.model.Exchange;

import java.util.ArrayList;

@Component
public class MySaxHandler extends DefaultHandler {

    private final String EXCHANG_TAG = "exchange";
    private final String CURRENCY_TAG = "currency";
    private final String R030_TAG = "r030";
    private final String TXT_TAG = "txt";
    private final String CC_TAG = "cc";
    private final String RATE_TAG = "rate";
    private final String EXCHANGEDATE_TAG = "echangedate";

    private Exchange exchange;
    private Currency currency;
    private String currentTag;

    @Override
    public void startDocument() throws SAXException {
        exchange = new Exchange();
    }

    public Exchange getExchange() {
        return exchange;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

        currentTag = qName;

        switch (currentTag){
            case EXCHANG_TAG: {
                exchange.setExchange(new ArrayList<>());
            }
            case CURRENCY_TAG: currency = new Currency();
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {

        String text = new String(ch,start,length);

        if(text.contains("<") || currentTag == null){
            return;
        }
        switch (currentTag){
            case R030_TAG: currency.setR030(text);
            case TXT_TAG: currency.setTxt(text);
            case RATE_TAG: currency.setRate(text);
            case CC_TAG: currency.setCc(text);
            case EXCHANGEDATE_TAG: currency.setExchangedate(text);
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {

        currentTag = qName;

        if (CURRENCY_TAG.equals(currentTag)) {
            exchange.getExchange().add(currency);
            currency = null;
        }

        currentTag = null;
    }
}
