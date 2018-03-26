package booksearch.demo.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import booksearch.demo.model.Book;
import booksearch.demo.model.GoogleSearchResult;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;

@Service
@Slf4j
public class GoogleBookSearchService {

    private static String URL_GOOGLE_SEARCH = "https://www.googleapis.com/books/v1/volumes?";
    private static String APP_PUBLIC_KEY = "AIzaSyDIRQyYsKTAQF4tg8TqwF8qxd2e1sGGF0o";

    final private ObjectMapper mapper;

    @Autowired
    public GoogleBookSearchService(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public Optional<Book> getBookInfo(String title) {
        Optional<Book> book = Optional.empty();
        try {
            URL url = new URL(prepareSearchUrl(prepareTitleForSearchQuery(title)));
            GoogleSearchResult result = mapper.readValue(url, GoogleSearchResult.class);

            book = CollectionUtils.emptyIfNull(result.getItems()).stream()
                    .filter(bk -> bk.getVolumeInfo().getTitle().toUpperCase().equals(title.toUpperCase()))
                    .findFirst();

        } catch (IOException e) {
            log.error("Error during getting info from google book search " + e);
        }

        return book;
    }

    private String prepareSearchUrl(String query) {
        return URL_GOOGLE_SEARCH + "q=" + query + "&printType=books&projection=full&key=" + APP_PUBLIC_KEY;
    }

    private String prepareTitleForSearchQuery(String title) {
        String result = "";
        String[] titlePhrases = title.split(" ");
        for(String phrase : titlePhrases) {
            result += "intitle:" + phrase + "+";
        }

        return result.substring(0, result.length() - 1);
    }
}
