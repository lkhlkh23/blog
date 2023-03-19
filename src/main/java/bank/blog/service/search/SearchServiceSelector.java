package bank.blog.service.search;

import bank.blog.domain.search.SearchInterfaceType;
import bank.blog.remote.common.RemoteSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static bank.blog.domain.search.SearchInterfaceType.KAKAO;
import static bank.blog.domain.search.SearchInterfaceType.NAVER;

@Component
public class SearchServiceSelector {

    private final Map<SearchInterfaceType, RemoteSearchService> selectors = new HashMap<>();

    @Autowired
    public SearchServiceSelector(@Qualifier("kakaoSearchService") RemoteSearchService kakaoSearch,
                                 @Qualifier("naverSearchService") RemoteSearchService naverSearch) {
        selectors.put(KAKAO, kakaoSearch);
        selectors.put(NAVER, naverSearch);
    }

    public Collection<RemoteSearchService> getAllSearchServices() {
        return selectors.values();
    }

}
