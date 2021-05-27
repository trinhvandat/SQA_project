package com.ptit.sqa.exception;

/*
Throw exception with application exception
 */
public class WaterAppException extends WaterServiceException {

    private static final long serialVersionUID = 6910434576449212427L;

    public WaterAppException(WaterError err) {
        super(err, null, null);
    }
}
