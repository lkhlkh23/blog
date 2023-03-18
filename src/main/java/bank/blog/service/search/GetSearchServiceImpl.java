package bank.blog.service.search;

import bank.blog.domain.search.SearchDocument;
import bank.blog.remote.common.RemoteSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class GetSearchServiceImpl implements GetSearchService {

    @Autowired
    @Qualifier("kakaoSearchService")
    private RemoteSearchService kakaoSearchService;

    @Autowired
    @Qualifier("naverSearchService")
    private RemoteSearchService naverSearchService;

    @Override
    public List<SearchDocument> search(final SearchCommand command) {
        final List<SearchDocument> response = kakaoSearchService.search(command.getQuery(),
                                                                        command.getSort().getKakoCode(),
                                                                        command.getPage(),
                                                                        command.getSize());
        if(!response.isEmpty()) {
            return response;
        }

        return naverSearchService.search(command.getQuery(),
                                         command.getSort().getNaverCode(),
                                         command.getPage(),
                                         command.getSize());
    }

}
