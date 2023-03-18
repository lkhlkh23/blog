package bank.blog.controller.v1.mapper;

import bank.blog.common.mapper.GenericMapper;
import bank.blog.controller.v1.dto.BlogSearchV1;
import bank.blog.domain.search.SearchDocument;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BlogSearchV1Mapper extends GenericMapper<SearchDocument, BlogSearchV1> {

    BlogSearchV1Mapper INSTANCE = Mappers.getMapper(BlogSearchV1Mapper.class);

}
