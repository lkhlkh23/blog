package bank.controller.v1.mapper;

import bank.controller.v1.dto.BlogSearchV1;
import bank.domain.search.SearchDocument;
import bank.mapper.GenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BlogSearchV1Mapper extends GenericMapper<SearchDocument, BlogSearchV1> {

    BlogSearchV1Mapper INSTANCE = Mappers.getMapper(BlogSearchV1Mapper.class);

}
