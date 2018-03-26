package booksearch.demo.converter;

import org.springframework.util.StringUtils;

import java.math.BigDecimal;

public class PriceConverter {
    public static BigDecimal convert(String price) {
        if (StringUtils.isEmpty(price)) {
            return null;
        }

        return new BigDecimal(price.substring(1));
    }
}
