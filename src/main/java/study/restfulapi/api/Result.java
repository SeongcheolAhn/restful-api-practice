package study.restfulapi.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 결과 데이터를 감싸는 역할
 * @param <T>
 */
@Getter @Setter
@AllArgsConstructor
public class Result<T> {
    private T data;
}
