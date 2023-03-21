package bank.controller.v1.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchPageV1 {

    private int total;
    private int requestPage;
    private int requestSize;
    private boolean isLast;

}
