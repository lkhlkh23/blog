package bank.service.search;

import bank.domain.search.SortType;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@EqualsAndHashCode
public class SearchCommand {

    private String query;
    private SortType sort;
    private int page;
    private int size;

    public void setSort(String sort) {
        this.sort = SortType.ACCURACY;
    }
}
