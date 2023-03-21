package bank.controller.v1.mapper;

import bank.controller.v1.dto.PopularKeywordV1;
import bank.domain.keyword.PopularKeyword;
import bank.mapper.GenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PopularKeywordV1Mapper extends GenericMapper<PopularKeyword, PopularKeywordV1> {

    PopularKeywordV1Mapper INSTANCE = Mappers.getMapper(PopularKeywordV1Mapper.class);

}
