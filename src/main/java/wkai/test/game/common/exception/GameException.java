package wkai.test.game.common.exception;


import wkai.test.game.common.response.ResultCode;

import java.text.MessageFormat;

/**
 * 自定义异常类型
 * @author pyy
 **/
public class GameException extends RuntimeException {

    //错误代码
    ResultCode resultCode;

    public GameException(ResultCode resultCode){
        super(resultCode.message());
        this.resultCode = resultCode;
    }

    public GameException(ResultCode resultCode, Object... args){
        super(resultCode.message());
        String message = MessageFormat.format(resultCode.message(), args);
        resultCode.setMessage(message);
        this.resultCode = resultCode;
    }

    public ResultCode getResultCode(){
        return resultCode;
    }

}
