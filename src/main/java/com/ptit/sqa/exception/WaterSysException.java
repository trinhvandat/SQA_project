package com.ptit.sqa.exception;

import java.util.LinkedHashMap;

public class WaterSysException extends WaterServiceException{

    private final String printMessage;
    private static final long serialVersionUID = 3374372229890332024L;

    protected WaterSysException(WaterError err, Throwable ex, LinkedHashMap<String, Object> params) {
        super(err, ex, params);
        printMessage = "";
    }

    protected WaterSysException(String printMessage, Throwable ex) {
        super(WaterError.UNEXPECTED_EXCEPTION, ex, null);
        this.printMessage = printMessage;
    }

    public String getPrintMessage() {
        return printMessage;
    }
}
