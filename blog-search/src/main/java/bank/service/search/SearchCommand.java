package bank.service.search;

import bank.common.type.SortType;
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

}
