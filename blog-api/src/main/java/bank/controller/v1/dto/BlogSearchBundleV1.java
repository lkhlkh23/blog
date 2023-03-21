package bank.controller.v1.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class BlogSearchBundleV1 {

    private List<BlogSearchV1> searches;
    private SearchPageV1 page;

    public void addSearch(final BlogSearchV1 search) {
        if(this.searches == null) {
            this.searches = new ArrayList<>();
        }

        this.searches.add(search);
    }

}
