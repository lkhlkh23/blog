package bank.blog.controller.handler;

import bank.blog.domain.search.SortType;
import org.springframework.core.convert.converter.Converter;

public class SortTypeConverter implements Converter<String, SortType> {
    @Override
    public SortType convert(final String value) {
        return SortType.getSortTypeByCode(value);
    }
}

