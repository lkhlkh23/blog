package bank.controller.handler;

import bank.domain.search.SortType;
import org.springframework.core.convert.converter.Converter;

public class SortTypeConverter implements Converter<String, SortType> {
    @Override
    public SortType convert(final String value) {
        return SortType.getSortTypeByCode(value);
    }
}

