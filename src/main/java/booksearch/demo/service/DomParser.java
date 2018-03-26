package booksearch.demo.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class DomParser {
    public Document parse(String url) throws IOException {
        return Jsoup.connect(url)
                .userAgent("Mozilla")
                .timeout(2000)
                .get();
    }
}
