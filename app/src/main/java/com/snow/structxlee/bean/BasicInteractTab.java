package com.snow.structxlee.bean;

public class BasicInteractTab {
    /** 显示区域选项卡名称 */
    private String name;
    /** 显示区域选项卡对应json数据路径 */
    private String path;
    /** 显示区域选项卡对应不同类型 */
    private int    type;
    /** 显示区域选项卡对应显示类型 */
    private int    areaType;

    public int getAreaType() {
        return areaType;
    }

    public void setAreaType(int areaType) {
        this.areaType = areaType;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public static class TabType {

        public static final int TYPE_ALL = 1;//全部

        public static final int TYPE_FOR_PAYMENT = 2;//待付款

        public static final int TYPE_SPELL_GROUP = 3;//待拼团

        public static final int TYPE_WAIT_SEND_GOODS = 4;//待发货

        public static final int TYPE_WAIT_GOODS = 5;//待收货

        public static final int TYPE_RETURN_OF = 6;//退款中

    }

}
