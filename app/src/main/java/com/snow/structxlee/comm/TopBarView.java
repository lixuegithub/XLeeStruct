package com.snow.structxlee.comm;

import java.util.ArrayList;
import java.util.List;


import android.content.Context;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.view.View.OnClickListener;

import com.snow.structxlee.R;
import com.snow.structxlee.util.ScreenUtils;

/**
 * 自定义头部实现
 */
public class TopBarView extends RelativeLayout implements OnClickListener {

	/**
	 * 自定义view点击事件回调
	 */
	public interface TopBarViewListener {
		public void onLeftMenuClick();

		public void onRightMenuOneClick();

		public void onRightMenuTwoClick();

		public void onCenterMenuItemClick(int position);
	}

	private TopBarViewListener listener;

	private List<String> titleItem = new ArrayList<String>();
	private int currentPosition = 0;


	private Context mContext = null;
	private LinearLayout itemContainer = null;
	private RelativeLayout leftMenuLayout = null;
	private RelativeLayout rightMenuLayout = null;

	private TextView submenurightOneText = null;
	private RelativeLayout submenurightOneImage = null;
	private TextView submenurightTwoText = null;
	private RelativeLayout submenurightTwoImage = null;

	private String defaultTheme = "";
	private RelativeLayout topbaRelativeLayout =null;

	public TopBarView(Context context) {
		this(context, null);
	}

	/**
	 * 返回指定title栏的topbar 默认单个没有底部红点 两个以上有红点，最多四个item
	 *
	 * @param context
	 * @param attrs
	 */
	public TopBarView(Context context, AttributeSet attrs) {

		super(context, attrs);
		this.mContext = context;
		this.currentPosition =0;
		initView();
		updateTopBarView();

	}
	public void setCurrentPosition(int currentPosition) {
		this.currentPosition = currentPosition;
	}
	public static int getTopBarHeightPix(Context context) {
		return context.getResources().getDimensionPixelSize(
				R.dimen.custom_top_bar_height);
	}

	public void initView() {
		topbaRelativeLayout = (RelativeLayout) LayoutInflater.from(mContext)
				.inflate(R.layout.custom_top_bar, this, true);

		itemContainer = (LinearLayout) topbaRelativeLayout
				.findViewById(R.id.title_container);
		leftMenuLayout = (RelativeLayout) topbaRelativeLayout.findViewById(R.id.left_menu);
		rightMenuLayout = (RelativeLayout) topbaRelativeLayout.findViewById(R.id.right_menu);
		leftMenuLayout.setOnClickListener(this);

		submenurightOneText = (TextView) topbaRelativeLayout
				.findViewById(R.id.sub_menu_one_right_text);
		submenurightOneImage = (RelativeLayout) topbaRelativeLayout
				.findViewById(R.id.right_menu_one_img);

		submenurightTwoText = (TextView) topbaRelativeLayout
				.findViewById(R.id.sub_menu_two_right_text);
		submenurightTwoImage = (RelativeLayout) topbaRelativeLayout
				.findViewById(R.id.right_menu_two_img);

		submenurightOneText.setOnClickListener(this);
		submenurightOneImage.setOnClickListener(this);
		submenurightTwoText.setOnClickListener(this);
		submenurightTwoImage.setOnClickListener(this);

	}

	/**
	 * 设置topbar的标题 此方法无法设置可以滚动的标题栏
	 * @param title
	 */
	public void setTitle(String title) {

		titleItem.clear();
		titleItem.add(title);
		updateTopBarView();

	}

	/**
	 * 设置主题
	 * @param theme
	 */
	public void setTopBarTheme(String theme) {
		defaultTheme = theme;
		updateTopBarView();
	}

	/**
	 * 获得主题
	 * @return
	 */
	public String getTopbarTheme() {

		return defaultTheme;
	}

	public void updateTopBarView() {

		if (itemContainer == null)
			return;
		itemContainer.removeAllViews();
		// 测量左右菜单
		leftMenuLayout.measure(View.MeasureSpec.makeMeasureSpec(0,
				View.MeasureSpec.UNSPECIFIED), View.MeasureSpec
				.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
		rightMenuLayout.measure(View.MeasureSpec.makeMeasureSpec(0,
				View.MeasureSpec.UNSPECIFIED), View.MeasureSpec
				.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));

		// 获得左右菜单的宽度
		int leftMenuWidth = leftMenuLayout.getMeasuredWidth();
		int rightMenuWidth = rightMenuLayout.getMeasuredWidth();

		// 获得中间标题宽度(取菜单的宽度最大值)
		int titleWidth = (int) ScreenUtils.getWidthJust()
				- 2
				* Math.max(
				Math.max(leftMenuWidth, rightMenuWidth),
				mContext.getResources().getDimensionPixelSize(
						R.dimen.custom_top_bar_menu_max_width));
		int itemWidth = 0;
		if (titleItem != null && titleItem.size() > 0)
			itemWidth = titleWidth / Math.min(titleItem.size(), 4);

		// 添加顶部居中title 兼容滑动红点

		if (titleItem != null && titleItem.size() != 0) {
			for (int position = 0; position < Math.min(titleItem.size(), 4); position++) {

				final int k = position;
				FrameLayout view = (FrameLayout) LayoutInflater.from(mContext)
						.inflate(R.layout.custom_top_bar_item, null);

				if (itemWidth != 0) {
					FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
							FrameLayout.LayoutParams.WRAP_CONTENT,
							FrameLayout.LayoutParams.MATCH_PARENT);
					params.width = itemWidth;
					params.height = getTopBarHeightPix(mContext);
					view.setLayoutParams(params);
				}

				TextView itemTitle = (TextView) view
						.findViewById(R.id.item_text);
				itemTitle.setText(titleItem.get(position));

				// 设置红点是否显示
				if (titleItem.size() == 1) {
					itemTitle
							.setTextColor(mContext
									.getResources()
									.getColor(R.color.text_main));
					TextPaint tpaint = itemTitle.getPaint();
//					tpaint.setFakeBoldText(true);

				} else if (currentPosition == position) {
					itemTitle.setTextColor(mContext.getResources().getColor(R.color.red_main));
					TextPaint tpaint = itemTitle.getPaint();
//					tpaint.setFakeBoldText(true);
				} else {
					itemTitle
							.setTextColor(mContext.getResources().getColor(R.color.text_main));
					TextPaint tpaint = itemTitle.getPaint();
//					tpaint.setFakeBoldText(false);
				}

				view.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						onTitleItemClick(k);
					}
				});
				itemContainer.addView(view);

			}

		}
	}

	public List<String> getTitleItem() {
		return titleItem;
	}

	/**
	 * 设置topbar的标题 此方法设置可以滚动的标题栏
	 */
	public void setTitleItem(List<String> titleItem) {
		if(titleItem!=null)
			this.titleItem = titleItem;
	}

	/**
	 * 设置左边菜单可见性,文本按钮
	 * @param visiable
	 * @param text
	 */
	public void setLeftMenuVisiable(boolean visiable, String text, int color) {
		if (leftMenuLayout == null)
			return;
		if (visiable) {
			leftMenuLayout.setVisibility(View.VISIBLE);
			TextView title = (TextView) leftMenuLayout
					.findViewById(R.id.sub_menu_left_text);
			ImageView imageView = (ImageView) leftMenuLayout
					.findViewById(R.id.sub_menu_left_image);
			title.setVisibility(View.VISIBLE);
			title.setText(text);
			title.setTextColor(mContext.getResources().getColor(color));
			imageView.setVisibility(View.INVISIBLE);
		} else {
			leftMenuLayout.setVisibility(View.GONE);
		}
	}

	/**
	 * 设置左边菜单可见性,文本按钮
	 * @param visiable
	 * @param text
	 */
	public void setLeftMenuVisiable(boolean visiable, int imgRes) {
		if (leftMenuLayout == null)
			return;
		if (visiable) {
			leftMenuLayout.setVisibility(View.VISIBLE);
			TextView title = (TextView) leftMenuLayout
					.findViewById(R.id.sub_menu_left_text);
			ImageView imageView = (ImageView) leftMenuLayout
					.findViewById(R.id.sub_menu_left_image);
			title.setVisibility(View.INVISIBLE);
			imageView.setVisibility(View.VISIBLE);
			imageView.setImageResource(imgRes);
		} else {
			leftMenuLayout.setVisibility(View.GONE);
		}
	}

	/**
	 * 设置右边第一个菜单可见性,文本按钮，有两个菜单时务必要将hasTwoMenu 设置为true
	 * @param visiable
	 * @param text
	 */
	public void setRightMenuOneVisiable(boolean visiable, String text,
										int color, boolean hasTwoMenu) {
		if (rightMenuLayout == null)
			return;
		if (visiable) {
			rightMenuLayout.setVisibility(View.VISIBLE);
			submenurightOneText.setVisibility(View.VISIBLE);
			submenurightOneImage.setVisibility(View.INVISIBLE);
			if (!hasTwoMenu) {
				submenurightTwoImage.setVisibility(View.GONE);
				submenurightTwoText.setVisibility(View.GONE);
			} else {
				submenurightTwoImage.setVisibility(View.INVISIBLE);
				submenurightTwoText.setVisibility(View.INVISIBLE);
			}
			ImageView imageView = (ImageView) rightMenuLayout
					.findViewById(R.id.sub_menu_one_right_image);
			submenurightOneText.setText(text);
			submenurightOneText.setTextColor(getResources().getColor(color));
			imageView.setVisibility(View.INVISIBLE);
		} else {
			submenurightOneImage.setVisibility(View.GONE);
			submenurightOneText.setVisibility(View.GONE);
			submenurightTwoImage.setVisibility(View.GONE);
			submenurightTwoText.setVisibility(View.GONE);
		}
	}

	/**
	 * 设置右边第二个菜单可见性,文本按钮，务必先添加第一个菜单
	 * @param visiable
	 * @param text
	 */
	public void setRightMenuTwoVisiable(boolean visiable, String text, int color) {
		if (rightMenuLayout == null)
			return;
		if (visiable) {
			rightMenuLayout.setVisibility(View.VISIBLE);
			submenurightTwoText.setVisibility(View.VISIBLE);
			submenurightTwoImage.setVisibility(View.INVISIBLE);
			ImageView imageView = (ImageView) rightMenuLayout
					.findViewById(R.id.sub_menu_two_right_image);
			submenurightTwoText.setText(text);
			submenurightTwoText.setTextColor(getResources().getColor(color));
			imageView.setVisibility(View.INVISIBLE);
		} else {
			submenurightTwoImage.setVisibility(View.GONE);
			submenurightTwoText.setVisibility(View.GONE);
		}
	}

	/**
	 * 设置右边第一个菜单可见性,图标按钮，有两个菜单时务必要将hasTwoMenu 设置为true
	 * @param visiable
	 */
	public void setRightMenuOneVisiable(boolean visiable, int imgRes,
										boolean hasTwoMenu) {
		if (rightMenuLayout == null)
			return;
		if (visiable) {
			rightMenuLayout.setVisibility(View.VISIBLE);
			submenurightOneText.setVisibility(View.INVISIBLE);
			submenurightOneImage.setVisibility(View.VISIBLE);
			if (!hasTwoMenu) {
				submenurightTwoImage.setVisibility(View.GONE);
				submenurightTwoText.setVisibility(View.GONE);
			} else {
				submenurightTwoImage.setVisibility(View.INVISIBLE);
				submenurightTwoText.setVisibility(View.INVISIBLE);
			}
			ImageView imageView = (ImageView) rightMenuLayout
					.findViewById(R.id.sub_menu_one_right_image);
			imageView.setVisibility(View.VISIBLE);
			imageView.setImageResource(imgRes);
		} else {
			submenurightOneImage.setVisibility(View.GONE);
			submenurightOneText.setVisibility(View.GONE);
			submenurightTwoImage.setVisibility(View.GONE);
			submenurightTwoText.setVisibility(View.GONE);
		}
	}

	/**
	 * 设置右边第二个菜单可见性,图标按钮，务必先添加第一个菜单
	 * @param visiable
	 */
	public void setRightMenuTwoVisiable(boolean visiable, int imgRes) {
		if (rightMenuLayout == null)
			return;
		if (visiable) {
			rightMenuLayout.setVisibility(View.VISIBLE);
			submenurightTwoText.setVisibility(View.INVISIBLE);
			submenurightTwoImage.setVisibility(View.VISIBLE);
			ImageView imageView = (ImageView) rightMenuLayout
					.findViewById(R.id.sub_menu_two_right_image);
			imageView.setVisibility(View.VISIBLE);
			imageView.setImageResource(imgRes);
		} else {
			submenurightTwoImage.setVisibility(View.GONE);
			submenurightTwoText.setVisibility(View.GONE);
		}
	}

	/**
	 * 红点移动到某个位置
	 * @param index 位置索引
	 */
	public void dotMoveToPosition(int index) {
		if (itemContainer != null) {
			for (int i = 0; i < itemContainer.getChildCount(); i++) {
				View view = itemContainer.getChildAt(i);
				TextView itemTitle = (TextView) view
						.findViewById(R.id.item_text);
				if (i != index) {
					itemTitle.setTextColor(mContext
									.getResources()
									.getColor(R.color.text_main));
					TextPaint tpaint = itemTitle.getPaint();
//					tpaint.setFakeBoldText(false);
//					itemDot.setVisibility(View.INVISIBLE);
				} else {
					itemTitle.setTextColor(mContext.getResources().getColor(
							R.color.red_main));
					TextPaint tpaint = itemTitle.getPaint();
//					tpaint.setFakeBoldText(true);
//					itemDot.setVisibility(View.VISIBLE);
				}
			}
		}

	}

	public void resetCurrentPosition(){
		currentPosition=0;
	}

	public void onTitleItemClick(int position) {
		if (currentPosition == position) {
			return;
		} else {
			currentPosition = position;
			dotMoveToPosition(position);
			if (listener != null)
				listener.onCenterMenuItemClick(position);
			else {
			}
		}

	}

	public TopBarViewListener getListener() {
		return listener;
	}

	public void setListener(TopBarViewListener listener) {
		this.listener = listener;
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
			case R.id.left_menu:
				if (listener != null)
					listener.onLeftMenuClick();
				break;
			// 右边第一个button点击
			case R.id.sub_menu_one_right_text:
				if (listener != null) {
					listener.onRightMenuOneClick();
				}
				break;
			case R.id.right_menu_one_img:
				if (listener != null) {
					listener.onRightMenuOneClick();
				}
				break;
			// 右边第二个button点击
			case R.id.sub_menu_two_right_text:
				if (listener != null) {
					listener.onRightMenuTwoClick();
				}
				break;
			case R.id.right_menu_two_img:
				if (listener != null) {
					listener.onRightMenuTwoClick();
				}
				break;
		}

	}

	public RelativeLayout getSubmenurightOneImage() {
		return submenurightOneImage;
	}

	public void setSubmenurightOneImage(RelativeLayout submenurightOneImage) {
		this.submenurightOneImage = submenurightOneImage;
	}

	public TextView getSubmenurightOneText() {
		return submenurightOneText;
	}

	public void setSubmenurightOneText(TextView submenurightOneText) {
		this.submenurightOneText = submenurightOneText;
	}

}
