package wkai.test.game.common.response;


public class Result {

    //操作代码
    String rltCode;

    //提示信息
    String rltDesc;

    public Result(String rltCode, String rltDesc) {
        this.rltCode = rltCode;
        this.rltDesc = rltDesc;
    }

    public String getRltCode() {
        return rltCode;
    }

    public void setRltCode(String rltCode) {
        this.rltCode = rltCode;
    }

    public String getRltDesc() {
        return rltDesc;
    }

    public void setRltDesc(String rltDesc) {
        this.rltDesc = rltDesc;
    }
}
