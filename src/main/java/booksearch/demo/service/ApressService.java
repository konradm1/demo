package booksearch.demo.service;

import booksearch.demo.converter.PriceConverter;
import booksearch.demo.model.BookStoreType;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import booksearch.demo.model.Offer;

import java.io.IOException;

@Service
@Slf4j
public class ApressService {
    private static final String ROOT_URL = "https://www.apress.com";
    private static final String SEARCH_URL = ROOT_URL + "/us/search?query=";
    private final DomParser domParser;

    @Autowired
    public ApressService(DomParser domParser) {
        this.domParser = domParser;
    }

    public Offer getOffer(String isbn) {
        Offer offer = Offer.builder().build();
        try {
                Document doc = domParser.parse(SEARCH_URL + isbn);
                updateOfferDataFromDOM(doc, offer);
                updateOfferPriceFromDetailsPage(offer);
                offer.setBookstore(BookStoreType.APRESS);
            } catch (IOException e) {
                log.error("Error during getting info from apress for isbn {} " + e, isbn);
            }

        return offer;
    }

    private void updateOfferDataFromDOM(Document doc, Offer offer) {
        Element resultsSection = doc.getElementById("result-list");
        if (resultsSection.getElementsByClass("result-item").size() > 0) {
            offer.setUrl(ROOT_URL + resultsSection.getElementsByTag("a").first().attr("href"));
        }
    }

    private void updateOfferPriceFromDetailsPage(Offer offer) throws IOException {
        if (!StringUtils.isEmpty(offer.getUrl())) {
            Document doc = domParser.parse(offer.getUrl());
            Element pricesSection = doc.getElementsByClass("product-buy").first();
            Element priceTag = pricesSection.getElementsByTag("dt").stream()
                    .filter(tag->tag.getElementsByClass("cover-type").first().text().equals("Softcover"))
                    .findFirst()
                    .get();

            offer.setPrice(PriceConverter.convert(priceTag.getElementsByClass("price").first().text()));
        }
    }
}
