package ua.ukrposhta.currency.MyXmlParser;

import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;
import ua.ukrposhta.currency.MyXmlHandler.MySaxHandler;
import ua.ukrposhta.currency.model.Exchange;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;

@Component
public class MySaxParser {


    public Exchange getObjectExchangeFromXML(String uri, MySaxHandler mySaxHandler) throws ParserConfigurationException, SAXException, IOException {

        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        parser.parse(uri, mySaxHandler);

        return mySaxHandler.getExchange();
    }
}
