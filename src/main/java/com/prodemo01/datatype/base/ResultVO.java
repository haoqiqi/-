package com.prodemo01.datatype.base;

import com.prodemo01.datatype.enums.ResponseCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ResultVO<T> {

    private Integer code = ResponseCode.SUCCESS.getCode();

    private String message = ResponseCode.SUCCESS.getMessage();

    private T data;

    public static <T> ResultVO<T> success(T obj) {
        ResultVO<T> resultVO = new ResultVO<>();
        resultVO.setCode(ResponseCode.SUCCESS.getCode());
        resultVO.setMessage(ResponseCode.SUCCESS.getMessage());
        resultVO.setData(obj);
        return resultVO;
    }

    public static <T> ResultVO<T> failed() {
        ResultVO<T> resultVO = new ResultVO<>();
        resultVO.setData(null);
        resultVO.setCode(ResponseCode.FAILED.getCode());
        resultVO.setMessage(ResponseCode.FAILED.getMessage());
        return resultVO;
    }

    public static <T> ResultVO<T> failed(String message) {
        ResultVO<T> resultVO = new ResultVO<>();
        resultVO.setData(null);
        resultVO.setCode(ResponseCode.FAILED.getCode());
        resultVO.setMessage(message);
        return resultVO;
    }
}
