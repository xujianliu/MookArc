package com.mook.arc.widget


import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.toColorInt
import com.blankj.utilcode.util.ConvertUtils
import com.mook.arc.R


/**
 * 自定义标题栏
 *
 * 1 可以设置标题，文字颜色
 * 2 可以设置左右按钮图片和点击事件
 */
class TitleBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    /**
     * 显示白色的返回图标
     */
    private var isShowLeftWhite: Boolean = false
    private var titleText = "Title"
    private var rightText = "Right"
    private var titleTextColor = Color.BLACK
    private var rightTextColor = "#36B99F".toColorInt()
    private var rootLayoutColor = Color.WHITE
    private var titleTextSize: Float
    private var rightTextSize: Float
    private var rightResourceId: Int
    private var isShowRight: Boolean = false
    private var isShowRightIsText: Boolean = false
    private var isShowLeftImage: Boolean = true

    private lateinit var leftClickListener: (() -> Unit)
    private lateinit var rightClickListener: (() -> Unit)

    fun setOnLeftClickListener(listener: () -> Unit) {
        this.leftClickListener = listener
    }

    fun setOnRightClickListener(listener: () -> Unit) {
        this.rightClickListener = listener
    }

    fun setTitleText(text: String) {
        tvTitle.text = text
    }

    fun setRightVisibility(bool: Boolean) {
        layoutRight.visibility = if (bool) View.VISIBLE else View.GONE
    }

    fun setLeftVisibility(b: Boolean) {
        layoutBack.visibility = if (b) View.VISIBLE else View.GONE
    }

    fun setRightImage(resourceId: Int) {
        layoutRight.visibility = View.VISIBLE
        imvRight.visibility = View.VISIBLE
        tvRight.visibility = View.GONE
        imvRight.setBackgroundResource(resourceId)
    }


    private val tvTitle: TextView
    private val tvRight: TextView
    private val imvRight: ImageView
    private val layoutRight: RelativeLayout
    private var layoutBack: RelativeLayout

    init {
        context.obtainStyledAttributes(attrs, R.styleable.TitleBar).run {
            titleText = getString(R.styleable.TitleBar_titleText) ?: ""
            rightText = getString(R.styleable.TitleBar_rightText) ?: ""

            titleTextColor = getColor(R.styleable.TitleBar_titleColor, "#181818".toColorInt())
            rightTextColor = getColor(R.styleable.TitleBar_rightTextColor, "#36B99F".toColorInt())

            titleTextSize =
                getDimension(R.styleable.TitleBar_titleSize, ConvertUtils.sp2px(18f).toFloat())
            rightTextSize =
                getDimension(R.styleable.TitleBar_rightTextSize, ConvertUtils.sp2px(15f).toFloat())

            rightResourceId = getResourceId(R.styleable.TitleBar_rightImage, R.drawable.icon_close)
            isShowRight = getBoolean(R.styleable.TitleBar_showRight, false)
            isShowRightIsText = getBoolean(R.styleable.TitleBar_showRightIsText, false)
            isShowLeftImage = getBoolean(R.styleable.TitleBar_showLeftImage, true)
            isShowLeftWhite = getBoolean(R.styleable.TitleBar_showLeftImageWhite, false)

            rootLayoutColor =
                getColor(R.styleable.TitleBar_titleBarBgColor, Color.parseColor("#FFFFFF"))
            recycle()
        }
        //findViewById
        val view = View.inflate(context, R.layout.title_bar, this)
        tvTitle = view.findViewById(R.id.tBtvTitle)
        tvRight = view.findViewById(R.id.tvRight)
        layoutRight = view.findViewById(R.id.rlRight)
        layoutBack = view.findViewById<RelativeLayout>(R.id.rlBack)
        imvRight = view.findViewById<ImageView>(R.id.imvRight)
        val rootView = view.findViewById<RelativeLayout>(R.id.rootLayout)
        rootView.setBackgroundColor(rootLayoutColor)
        if (isShowRight) {
            layoutRight.visibility = View.VISIBLE
            if (isShowRightIsText) {
                imvRight.visibility = View.GONE
                tvRight.run {
                    text = rightText
                    setTextSize(TypedValue.COMPLEX_UNIT_PX, rightTextSize)
                    setTextColor(rightTextColor)
                }
            } else {
                tvRight.visibility = View.GONE
                imvRight.setBackgroundResource(rightResourceId)
            }
        } else {
            layoutRight.visibility = View.GONE
        }
        layoutBack.visibility = if (isShowLeftImage) View.VISIBLE else View.GONE
        tvTitle.apply {
            text = titleText
            setTextSize(TypedValue.COMPLEX_UNIT_PX, titleTextSize)
            setTextColor(titleTextColor)
        }
        val tvSettingBack = view.findViewById<TextView>(R.id.tv_setting_back)
        tvSettingBack.also {
            setOnClickListener {
                if (this::leftClickListener.isInitialized) {
                    leftClickListener.invoke()
                }
            }

            if (isShowLeftWhite) {
                val drawable = ResourcesCompat.getDrawable(resources,R.drawable.icon_back_white, null)
                drawable?.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
                it.setCompoundDrawables(drawable, null, null, null)
            } else {
                val drawable = ResourcesCompat.getDrawable(resources,R.drawable.icon_back, null)
                drawable?.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
                it.setCompoundDrawables(drawable, null, null, null)
            }

        }
        layoutRight.setOnClickListener {
            if (this::rightClickListener.isInitialized) {
                rightClickListener.invoke()
            }
        }

    }
}