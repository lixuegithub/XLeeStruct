package com.snow.structxlee.util;

public class Config {

	public static int MYORDER_TYPE=-1;//订单type  选择

	public static enum OrderStatus {
		ORDER_WAIT_PAY(1, "等待状态"), ORDER_PAY_SUCCESS(2, "支付成功,等待发货"), ORDER_CANCEL(3, "取消订单"), ORDER_PAY_FAILD(4,
				"支付失败"), ORDER_HAS_SEND(5, "已发货,等待接收"), ORDER_HAS_RECEIVED(6, "已经接收,结束"), ORDER_APPLY_RETURN(7,
				"申请退货"), ORDER_CONFIRM_RETURN(8, "接受退货"), ORDER_REFUSED_RETURN(9, "拒绝退货");

		public int key;
		public String value;

		OrderStatus(int key, String value) {
			this.key = key;
			this.value = value;
		}

	}
}
