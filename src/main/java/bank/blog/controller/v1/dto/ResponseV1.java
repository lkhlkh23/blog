package bank.blog.controller.v1.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseV1<T> {

    private T data;
    private int status;
    private String message;

}
