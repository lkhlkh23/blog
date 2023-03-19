package bank.blog.service.search;

import bank.blog.domain.search.SearchDocument;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static bank.blog.domain.search.SearchInterfaceType.KAKAO;
import static bank.blog.domain.search.SearchInterfaceType.NAVER;

@RequiredArgsConstructor
@Service
public class GetSearchServiceImpl implements GetSearchService {

    @Autowired
    private SearchServiceSelector selector;

    @Override
    public List<SearchDocument> search(final SearchCommand command) {
        final List<SearchDocument> response = selector.getServiceByType(KAKAO)
                                                      .search(command.getQuery(),
                                                              command.getSort().getKakoCode(),
                                                              command.getPage(),
                                                              command.getSize());
        if(!response.isEmpty()) {
            return response;
        }

        return selector.getServiceByType(NAVER)
                       .search(command.getQuery(),
                               command.getSort().getKakoCode(),
                               command.getPage(),
                               command.getSize());
    }

}
