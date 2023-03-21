package bank.controller.handler;

import bank.domain.search.SortType;
import bank.exception.InvalidParameterException;
import org.springframework.core.convert.converter.Converter;

public class SortTypeConverter implements Converter<String, SortType> {

    @Override
    public SortType convert(final String value) {
        final SortType type = SortType.getSortTypeByCode(value);
        if(type == SortType.NONE) {
            throw new InvalidParameterException("정렬조건은 accuracy, recency 중 하나이여야만 합니다.");
        }

        return type;
    }

}

