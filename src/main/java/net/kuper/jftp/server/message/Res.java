package net.kuper.jftp.server.message;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Res<T> {

    private int code;
    private String msg;
    private T data;

    public Res<T> success() {
        this.success(1000);
        return this;
    }

    public Res<T> success(int code) {
        this.code = code;
        this.msg = "success";
        return this;
    }

    public Res<T> error() {
        this.error(1001);
        return this;
    }

    public Res<T> error(int code) {
        this.code = code;
        return this;
    }

    public Res<T> msg(String msg) {
        this.msg = msg;
        return this;
    }

    public Res<T> data(T data) {
        this.data = data;
        return this;
    }
}
