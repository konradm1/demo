package booksearch.demo.service;

import booksearch.demo.converter.PriceConverter;
import booksearch.demo.model.BookStoreType;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import booksearch.demo.model.Offer;

import java.io.IOException;

@Service
@Slf4j
public class AmazonService {

    private final DomParser domParser;

    private static final String GET_URL = "https://www.amazon.com/s/ref=nb_sb_noss?field-keywords=";

    @Autowired
    public AmazonService(DomParser domParser) {
        this.domParser = domParser;
    }

    public Offer getOffer(String isbn) {
        Offer offer = Offer.builder().build();
        try {
                Document doc = domParser.parse(GET_URL+isbn);
                updateOfferValuesFromDOM(doc, offer);
                offer.setBookstore(BookStoreType.AMAZON);

            } catch (IOException e) {
                log.error("Error during getting info from amazon for isbn {} " + e, isbn);
            }

        return offer;
    }

    private void updateOfferValuesFromDOM(Document doc, Offer offer) {
        Element resultsSection = doc.getElementById("result_0");
        if (resultsSection != null) {
            offer.setUrl(resultsSection.getElementsByClass("a-link-normal").first().attr("href"));
            offer.setPrice(PriceConverter.convert(resultsSection.getElementsByClass("a-offscreen").first().text()));
        }
    }
}
