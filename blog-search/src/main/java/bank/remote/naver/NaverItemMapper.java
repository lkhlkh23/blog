package bank.remote.naver;

import bank.domain.search.SearchDocument;
import bank.mapper.GenericMapper;
import bank.remote.naver.dto.NaverItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface NaverItemMapper extends GenericMapper<NaverItem, SearchDocument> {

    NaverItemMapper INSTANCE = Mappers.getMapper(NaverItemMapper.class);

    @Override
    @Mapping(source = "from.description", target = "contents")
    @Mapping(source = "from.link", target = "url")
    SearchDocument from(final NaverItem from);
}
