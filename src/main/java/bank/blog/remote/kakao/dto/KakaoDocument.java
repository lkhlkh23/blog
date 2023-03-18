package bank.blog.remote.kakao.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Getter
@Setter
public class KakaoDocument {

    private String title;
    private String contents;
    private String url;
    private LocalDateTime dataTime;

}
