package bank.blog.domain.keyword;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PopularKeyword {

    private String keyword;
    private long total;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
