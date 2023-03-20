package bank.remote.naver.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class NaverSearchResponse {

    private String title;
    private String link;
    private String description;
    private int total;
    private int start;
    private int display;
    private List<NaverItem> items;

}
