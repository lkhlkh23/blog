package bank.blog.domain.search;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum SearchInterfaceType {

    KAKAO("kakao", "카카오 검색엔진 사용"),
    NAVER("naver", "네이버 검색엔진 사용");

    private String type;
    private String description;
}
