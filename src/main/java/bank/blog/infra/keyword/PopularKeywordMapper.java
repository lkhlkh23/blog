package bank.blog.infra.keyword;

import bank.blog.common.mapper.GenericMapper;
import bank.blog.domain.keyword.PopularKeyword;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PopularKeywordMapper extends GenericMapper<KeywordEntity, PopularKeyword> {

    PopularKeywordMapper INSTANCE = Mappers.getMapper(PopularKeywordMapper.class);

}
