package com.snow.structxlee.bean;

public class InteractTab extends BasicInteractTab {
    private String id;
    private int    type;                   // 如 AsyncAdapter.LiveInteractType.COMMENT
    private int    refreshType;
    private Status status = Status.PENDING;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getRefreshType() {
        return refreshType;
    }

    public void setRefreshType(int refreshType) {
        this.refreshType = refreshType;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public enum Status {
        /** 显示区域选项卡对应数据获取状态：PENDING：还未获取，RUNNING：正在获取，COMPLETED：获取完成 */
        PENDING, RUNNING, COMPLETED, NONEEDREFRESHING;
    }

}
