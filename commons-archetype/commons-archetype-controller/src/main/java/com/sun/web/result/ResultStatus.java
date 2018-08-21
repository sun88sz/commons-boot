package com.sun.web.result;

import lombok.Getter;

/**
 * 接口返回状态
 * <p>
 * 0 错误
 * 1 正确
 * 不采用boolean的形式 考虑可能有些特殊返回处理无法用 true/ false 表示
 *
 * @author Sun
 */
public enum ResultStatus {

    ERROR(0),
    OK(1),;

    @Getter
    private int status;

    ResultStatus(int status) {
        this.status = status;
    }
}
