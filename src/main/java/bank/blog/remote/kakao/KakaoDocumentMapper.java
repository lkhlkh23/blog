package bank.blog.remote.kakao;

import bank.blog.common.mapper.GenericMapper;
import bank.blog.domain.search.SearchDocument;
import bank.blog.remote.kakao.dto.KakaoDocument;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface KakaoDocumentMapper extends GenericMapper<KakaoDocument, SearchDocument> {

    KakaoDocumentMapper INSTANCE = Mappers.getMapper(KakaoDocumentMapper.class);

}
