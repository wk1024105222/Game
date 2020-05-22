package wkai.test.game.common.response;

public enum ResultCode {
    /**
     * 0001 用户名或密码异常
     * 0002 原密码异常
     * 0003 验证码不正确
     *
     * 0005 商品信息异常
     * 0006
     * 0007

     * 0010 余额不足
     * 0012 商品列表查询失败


     */

    SUCCESS("0000", "操作成功！"),
    FAIL("9999", "操作失败！"),

    /* 参数错误：10001-19999 */
    PARAM_IS_INVALID("1001", "参数无效"),
    PARAM_IS_BLANK("1002", "参数为空"),
    PARAM_TYPE_BIND_ERROR("1003", "参数格式错误"),
    PARAM_NOT_COMPLETE("1004", "参数缺失"),

    /* 用户错误：2001-2099*/
    USER_NOT_LOGGED_IN("2001", "用户未登录，请先登录"),
    USER_LOGIN_ERROR("2002", "账号不存在或密码错误"),
    USER_ACCOUNT_FORBIDDEN("2003", "账号已被禁用"),
    USER_NOT_EXIST("2004", "用户不存在"),
    USER_HAS_EXISTED("2005", "用户已存在"),

    GOODS_NOT_EXIST("3001","商品已经下架"),
    GOODS_PRICE_ERROR("3002","商品价格变化，请重新提交"),
    GOODS_STOCKOUT("3003","商品库存不足，请重新提交"),
    GOODS_LIST_ERROR("3004","商品列表获取失败"),
    GOODS_CREATE_ERROR("3005","商品创建失败"),
    SELLER_NOT_EXIST("3006","卖家信息不存在"),

    ORDER_AMOUNT_ERROR("4000","订单总金额异常,请重新提交"),

    ACCOUNT_BALANCE_NOT_ENOUGH("5001","余额不足");

    //操作代码
    String code;
    //提示信息
    String message;

    ResultCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String code() {
        return code;
    }

    public String message() {
        return message;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
