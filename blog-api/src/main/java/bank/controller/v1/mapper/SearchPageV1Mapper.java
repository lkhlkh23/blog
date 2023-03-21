package bank.controller.v1.mapper;

import bank.controller.v1.dto.SearchPageV1;
import bank.domain.search.SearchResponse;
import bank.mapper.GenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SearchPageV1Mapper extends GenericMapper<SearchResponse.SearchPage, SearchPageV1> {

    SearchPageV1Mapper INSTANCE = Mappers.getMapper(SearchPageV1Mapper.class);

}
