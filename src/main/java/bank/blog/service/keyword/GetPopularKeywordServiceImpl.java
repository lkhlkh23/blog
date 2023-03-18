package bank.blog.service.keyword;

import bank.blog.domain.keyword.PopularKeyword;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetPopularKeywordServiceImpl implements GetPopularKeywordService {

    @Override
    public List<PopularKeyword> getPopularKeywords(final int limit) {
        return null;
    }

}
