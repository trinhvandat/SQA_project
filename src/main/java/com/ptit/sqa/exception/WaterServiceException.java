package com.ptit.sqa.exception;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public abstract class WaterServiceException extends RuntimeException{

    private static final long serialVersionUID = -6662460118306199774L;

    private final WaterError err;
    private Map<String, Object> params;

    protected WaterServiceException(WaterError err, Throwable ex, LinkedHashMap<String, Object> params) {
        super(makeErrorMessage(err.getMessage(), params), ex);
        this.params = Objects.nonNull(params) ? params: Collections.emptyMap();
        this.err = err;
    }

    private static String makeErrorMessage(String messageKey, LinkedHashMap<String, Object> params) {
        return StringUtils.defaultIfEmpty(messageKey, StringUtils.EMPTY)
                + ";" + MapUtils.emptyIfNull(params).toString();
    }

    public WaterError getErr() {
        return err;
    }

    public Map<String, Object> getParams() {
        return params;
    }
}
