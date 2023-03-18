package bank.blog.domain.search;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum SortType {

    ACCURACY("01", "accuracy", "sim", "정확도순"),
    RECENCY("02", "recency", "date", "최신순");

    private String code;
    private String kakoCode;
    private String naverCode;
    private String description;

}
