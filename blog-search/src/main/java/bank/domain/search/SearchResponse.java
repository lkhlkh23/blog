package bank.domain.search;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SearchResponse {

    private SearchPage page;
    private List<SearchDocument> documents;

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    public static final class SearchPage {

        private int total;
        private int requestPage;
        private int requestSize;
        private boolean isLast;

    }

}
