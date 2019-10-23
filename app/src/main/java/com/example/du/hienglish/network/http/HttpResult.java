package com.example.du.hienglish.network.http;

/**
 * Created by Bob Du on 2019/04/05 18:39
 */
public class HttpResult<T> {
    private static int SUCCESS_CODE = 200;
    private int status;
    private String msg;
    private T data;

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setData(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public boolean isSuccess(){
        return getStatus() == SUCCESS_CODE;
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("status=" + status + " msg=" + msg);
        if (buffer != null) {
            buffer.append(" data:" + data);
        }
        return buffer.toString();
    }
}
