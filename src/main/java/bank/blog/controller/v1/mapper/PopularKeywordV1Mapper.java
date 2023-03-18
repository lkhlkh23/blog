package bank.blog.controller.v1.mapper;

import bank.blog.common.mapper.GenericMapper;
import bank.blog.controller.v1.dto.PopularKeywordV1;
import bank.blog.domain.keyword.PopularKeyword;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PopularKeywordV1Mapper extends GenericMapper<PopularKeyword, PopularKeywordV1> {

    PopularKeywordV1Mapper INSTANCE = Mappers.getMapper(PopularKeywordV1Mapper.class);

}
