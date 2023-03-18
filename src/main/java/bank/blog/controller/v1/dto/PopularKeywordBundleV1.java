package bank.blog.controller.v1.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PopularKeywordBundleV1 {

    private List<PopularKeywordV1> keywords;

    public void addKeyword(final PopularKeywordV1 keyword) {
        if(keywords == null) {
            this.keywords = new ArrayList<>();
        }

        keywords.add(keyword);
    }

}
