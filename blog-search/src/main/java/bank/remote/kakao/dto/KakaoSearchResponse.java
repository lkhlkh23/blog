package bank.remote.kakao.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class KakaoSearchResponse {

    private KakaoMeta meta;
    private List<KakaoDocument> documents;
}
