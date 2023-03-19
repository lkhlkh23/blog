package bank.blog.service.keyword;

import bank.blog.infra.keyword.RegisterPopularKeywordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RegisterPopularKeywordServiceImpl implements RegisterPopularKeywordService {

    private final RegisterPopularKeywordRepository keywordRepository;

    @Transactional()
    @Override
    public void processIfPresentOrNot(final String query) {
        keywordRepository.processIfPresentOrNot(query);
    }

}
