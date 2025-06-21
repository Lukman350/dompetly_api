package dev.lukmann;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponseDto<T> {

    private Boolean success;
    private T data;
    private String message;

    public BaseResponseDto(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }

}
