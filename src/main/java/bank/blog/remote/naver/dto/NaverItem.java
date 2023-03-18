package bank.blog.remote.naver.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NaverItem {

    private String title;
    private String link;
    private String description;
    @JsonProperty("bloggername") private String bloggerName;
    @JsonProperty("bloggerlink") private String bloggerLink;
    // @JsonProperty("postdate") private LocalDateTime postDate;

}
