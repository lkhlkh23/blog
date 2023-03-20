package bank.domain.search;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SearchDocument {

    private String title;
    private String contents;
    private String url;

}
