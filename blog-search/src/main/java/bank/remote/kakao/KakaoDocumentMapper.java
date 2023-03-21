package bank.remote.kakao;

import bank.domain.search.SearchDocument;
import bank.mapper.GenericMapper;
import bank.remote.kakao.dto.KakaoDocument;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface KakaoDocumentMapper extends GenericMapper<KakaoDocument, SearchDocument> {

    KakaoDocumentMapper INSTANCE = Mappers.getMapper(KakaoDocumentMapper.class);

}
