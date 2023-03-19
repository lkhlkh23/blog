package bank.blog.service.search;

import bank.blog.remote.common.RemoteSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SearchServiceSelector {

    private final List<RemoteSearchService> selectors = new ArrayList<>();

    @Autowired
    public SearchServiceSelector(@Qualifier("kakaoSearchService") RemoteSearchService kakaoSearch,
                                 @Qualifier("naverSearchService") RemoteSearchService naverSearch) {
        selectors.add(kakaoSearch);
        selectors.add(naverSearch);
    }

    public List<RemoteSearchService> getAllSearchServices() {
        return selectors;
    }

}
