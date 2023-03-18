package bank.blog.service.search;

import bank.blog.domain.search.SortType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SearchCommand {

    private String query;
    private SortType sort;
    private int page;
    private int size;

}
