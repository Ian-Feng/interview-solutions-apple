package com.apple.interview.day10;

public class ResultType {
    private Integer matchPoint;
    private boolean success;
//

    public ResultType(boolean success) {
        this.success = success;
    }

    public ResultType() {
    }

    //    public ResultType(int matchPoint, boolean success) {
//        this.matchPoint = matchPoint;
//        this.success = success;
//    }

    public Integer getMatchPoint() {
        return matchPoint;
    }

    public void setMatchPoint(Integer matchPoint) {
        this.matchPoint = matchPoint;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
