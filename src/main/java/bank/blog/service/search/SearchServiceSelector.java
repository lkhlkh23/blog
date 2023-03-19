package bank.blog.service.search;

import bank.blog.domain.search.SearchInterfaceType;
import bank.blog.remote.common.RemoteSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.security.InvalidParameterException;

import static bank.blog.domain.search.SearchInterfaceType.KAKAO;
import static bank.blog.domain.search.SearchInterfaceType.NAVER;

@Component
public class SearchServiceSelector {

    private final RemoteSearchService kakaoSearchService;
    private final RemoteSearchService naverSearchService;

    @Autowired
    public SearchServiceSelector(@Qualifier("kakaoSearchService") RemoteSearchService kakaoSearch,
                                 @Qualifier("naverSearchService") RemoteSearchService naverSearch) {
        kakaoSearchService = kakaoSearch;
        naverSearchService = naverSearch;
    }

    public RemoteSearchService getServiceByType(final SearchInterfaceType type) throws InvalidParameterException {
        if(KAKAO == type) {
            return kakaoSearchService;
        }

        if(NAVER == type) {
            return naverSearchService;
        }

        throw new InvalidParameterException();
    }
}
