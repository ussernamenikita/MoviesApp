package ru.test.moviesapp.ui.view

import android.content.Context
import android.content.res.Resources
import android.content.res.TypedArray
import android.graphics.*
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import ru.test.moviesapp.R
import ru.test.moviesapp.ui.TextUtils
import kotlin.math.ceil
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToInt

/**
 * Custom view which draw movie poster [drawable]
 * and movie description [text].
 * Text split to two parts one draws to right of poster
 * and the rest to bottom of poster.
 */
class MovieDescriptionView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : View(context, attrs, defStyleAttr, defStyleRes) {

    private val textMarginTop: Float
        get() {
            return imageMarginTop - mTextPaint.descent()
        }
    /**
     * Layout which will be use on android upper 23
     */
    private var upper23Layout: StaticLayout? = null

    /**
     * Layout with text which will be below image
     * on android < 23
     * On android >= 23 will be null
     */
    private var wideLayout: Layout? = null

    /**
     * Text width fot wide part (below image)
     */
    private val wideTextWidth: Int
        get() {
            return width - imageMarginStart.toInt() - (textMarginEnd
                ?: defaultTextMarginEnd.toInt())
        }
    /**
     * How much of view's width must be image
     */
    private val imageWidthPercent: Float = 0.5f

    /**
     * Margin sets like a
     */
    private val marginPercents: Float = 0.03f

    private val defaultTextMarginEnd: Float
        get() {
            return width * marginPercents
        }

    private val narrowTextWidth: Int
        get() {
            return (width - getImageWidthWithMargins() - textMarginStart - (textMarginEnd
                ?: defaultTextMarginEnd.toInt())).toInt()
        }

    private val narrowLinesCount: Int
        get() {
            return ceil(imageHeight / oneLineTextHeight).toInt()
        }

    private val imageRectF: RectF = RectF()

    private val drawableMatrix: Matrix = Matrix()

    private val imageHeight: Float
        get() {
            return imageWidth * 1.5f
        }

    private val imageMarginTop: Float
        get() {
            return width * marginPercents
        }

    private val imageWidth: Float
        get() {
            return width * imageWidthPercent
        }

    private val imageMarginEnd: Float
        get() {
            return width * marginPercents
        }

    private val imageMarginStart: Float
        get() {
            return width * marginPercents
        }
    private val defaultTextColor: Int = Color.BLACK

    private val screenWidth: Int by lazy {
        context.resources.displayMetrics.widthPixels
    }

    private val screenHeight: Int by lazy {
        context.resources.displayMetrics.heightPixels
    }

    private val defaultTextSize: Int by lazy {
        context
            .resources
            .getDimensionPixelSize(R.dimen.movie_description_default_text_size)
    }

    private val oneLineTextHeight: Float
        get() {
            mTextPaint.measureText("Test")
            return -mTextPaint.ascent() + mTextPaint.descent()
        }

    /**
     * Movie description drawable
     */
    var drawable: Drawable? = null

    /**
     * Space between text and parent
     * in the right part of view
     */
    var textMarginEnd: Int? = null
        set(value) {
            field = value
            reInitTextLayout()
        }

    var textColor: Int = defaultTextColor
        set(value) {
            field = value
            mTextPaint.color = value
            requestLayout()
        }


    /**
     * Space between end text and parent
     */
    var textMarginStart: Int = 0
        set(value) {
            field = value
            reInitTextLayout()
        }

    /**
     * Layout for text which will be draw to right of image
     * on android < 23
     * on android >= 23 will be null
     */
    private var narrowLayout: Layout? = null

    private val mTextPaint: TextPaint = TextPaint()

    var text: String = ""
        set(value) {
            field = value
            reInitTextLayout()
        }

    var textSize: Float = 0f
        set(value) {
            field = value
            mTextPaint.textSize = field
            reInitTextLayout()
        }

    /**
     * Create new textLayout
     */
    private fun reInitTextLayout() {
        if (parent == null || getWideWidth() < 0) {
            return
        }
        requestLayout()
        invalidate()
    }

    @Suppress("DEPRECATION")
    private fun createLayouts() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //For versions equals and bigger than 23 we might
            //only set indents and static layout break text himself.
            val leftIndents =
                Array<Int>(narrowLinesCount + 1) { (imageWidth + imageMarginStart + textMarginStart).toInt() }
            //Lines which number is bigger than narrowLinesCount
            // will be use last indent from array
            // so we add additional indent
            leftIndents[narrowLinesCount] = 0
            upper23Layout = StaticLayout
                .Builder
                .obtain(text, 0, text.length, mTextPaint, wideTextWidth)
                .setIndents(leftIndents.toIntArray(), null)
                .build()
        } else {
            val narrowText = getTextForNarrowPart(text)
            narrowLayout = StaticLayout(
                narrowText,
                mTextPaint,
                narrowTextWidth,
                Layout.Alignment.ALIGN_NORMAL,
                1.0f,
                0f,
                true
            )
            if (narrowText.length < text.length) {
                val wideText = text.substring(narrowText.length, text.length)
                wideLayout = StaticLayout(
                    wideText,
                    mTextPaint,
                    wideTextWidth,
                    Layout.Alignment.ALIGN_NORMAL,
                    1.0f,
                    0f,
                    true
                )
            }
        }
    }


    /**
     * Return text which fit in narrow part( to the right of the image)
     */
    private fun getTextForNarrowPart(text: String): CharSequence {
        val lineH = oneLineTextHeight
        val height = narrowLinesCount * lineH
        val width = narrowTextWidth
        val intArray = floatArrayOf(-1f, -1f)
        var accumulateHeight = 0f
        var resultChars = 0
        var inLineChars: Int
        val textAsArray = text.toCharArray()
        do {
            //Get char count what fit in one line
            inLineChars = mTextPaint.breakText(
                textAsArray,
                resultChars,
                text.length - resultChars,
                width.toFloat(),
                intArray
            )
            //remove cropped word
            //If you do not delete cropped work
            //then calculate text will not fit to area
            //Because layout move cropped words to next sting
            inLineChars = TextUtils.discardNotFitted(resultChars, inLineChars, textAsArray)
            accumulateHeight += lineH
            resultChars += inLineChars
        } while (accumulateHeight < height && resultChars < text.length)
        return text.subSequence(0, resultChars)
    }


    /**
     * Return width of text without intends in pixels
     * that width was on below image text
     */
    private fun getWideWidth(): Float {
        return width - getImageWidthWithMargins() - (textMarginEnd?.toFloat()
            ?: defaultTextMarginEnd + imageMarginStart)
    }

    /**
     * Image will be 100*[imageWidthPercent] percent of view
     */
    private fun getImageWidthWithMargins(): Float {
        return imageWidth + imageMarginStart + imageMarginEnd
    }


    init {
        var typefaceFromResource: Typeface? = null
        val array: TypedArray? = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.MovieDescriptionView,
            defStyleAttr,
            defStyleRes
        )
        //Try to get parameters from xml
        if (array != null) {
            try {
                textSize = array.getDimensionPixelSize(
                    R.styleable.MovieDescriptionView_android_textSize,
                    defaultTextSize
                ).toFloat()
                textMarginStart = array.getDimensionPixelSize(
                    R.styleable.MovieDescriptionView_md_text_margin_start,
                    0
                )
                if (array.hasValue(R.styleable.MovieDescriptionView_md_text_margin_end)) {
                    textMarginEnd = array.getDimensionPixelSize(
                        R.styleable.MovieDescriptionView_md_text_margin_end,
                        0
                    )
                }
                val fontFromXml =
                    array.getString(R.styleable.MovieDescriptionView_android_fontFamily)
                if (fontFromXml != null) {
                    typefaceFromResource = Typeface.create(fontFromXml, Typeface.NORMAL)
                }
                val textFromXml = array.getString(R.styleable.MovieDescriptionView_android_text)
                if (textFromXml != null) {
                    text = textFromXml
                }
                textColor = array.getColor(
                    R.styleable.MovieDescriptionView_android_textColor,
                    defaultTextColor
                )
                drawable =
                    array.getDrawable(R.styleable.MovieDescriptionView_md_image) ?: ColorDrawable(
                        Color.RED
                    )
            } finally {
                array.recycle()
            }
        } else {
            textSize = defaultTextSize.toFloat()
            //If font ot specified from xml layout
            //then try get from theme android:fontFamily attribute
            textColor = getTextColorFromTheme(context.theme)
            drawable = ColorDrawable(Color.RED)
        }
        //Set all parameters to paint text
        with(mTextPaint) {
            isAntiAlias = true
            textSize = textSize
            color = textColor
            density = resources.displayMetrics.density
            typeface = typefaceFromResource ?: getFontFromTheme(context)
        }
        reInitTextLayout()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val desiredWidth = getSize(
            suggestedMinimumWidth,
            screenWidth - paddingLeft - paddingRight,
            widthMeasureSpec
        )
        val desiredHeight = getSize(
            suggestedMinimumHeight,
            screenHeight - paddingBottom - paddingTop,
            heightMeasureSpec
        )
        setMeasuredDimension(desiredWidth, desiredHeight)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (changed) {
            imageRectF.left = imageMarginStart
            imageRectF.top = imageMarginTop
            imageRectF.right = imageMarginStart + imageWidth
            imageRectF.bottom = imageMarginTop + imageHeight
            drawable?.setBounds(0, 0, drawable!!.intrinsicWidth, drawable!!.intrinsicHeight)
            drawable?.let {
                val scale: Float
                var dx = 0f
                var dy = 0f

                if (it.intrinsicWidth * imageHeight > imageWidth * it.intrinsicHeight) {
                    scale = imageHeight / it.intrinsicHeight.toFloat()
                    dx = (imageWidth - it.intrinsicWidth * scale) * 0.5f
                } else {
                    scale = imageWidth / it.intrinsicWidth.toFloat()
                    dy = (imageHeight - it.intrinsicHeight * scale) * 0.5f
                }
                drawableMatrix.setScale(scale, scale)
                drawableMatrix.postTranslate(
                    imageMarginStart + dx.roundToInt().toFloat(),
                    imageMarginTop + dy.roundToInt().toFloat()
                )
            }
            createLayouts()
        }
    }

    /**
     * Get size which may be use for view
     * depending of [spec]
     * @param spec [View.MeasureSpec] from parent
     * @param minimumValue minimum value for size
     * @param maxValue maximum value for size
     */
    private fun getSize(minimumValue: Int, maxValue: Int, spec: Int): Int {
        val measureSize = MeasureSpec.getSize(spec)
        //We need sure what our suggested size is bigger than minimum
        val aboveMinimum = max(minimumValue, maxValue)
        return when (MeasureSpec.getMode(spec)) {
            MeasureSpec.AT_MOST -> {
                min(aboveMinimum, measureSize)
            }
            MeasureSpec.EXACTLY -> {
                measureSize
            }
            else -> {
                aboveMinimum
            }
        }
    }

    override fun onDraw(canvas: Canvas) {
        drawImage(canvas)
        drawLayouts(canvas)
    }


    private fun drawLayouts(canvas: Canvas) {
        narrowLayout?.let {
            canvas.save()
            canvas.translate(getImageWidthWithMargins() + textMarginStart, textMarginTop)
            it.draw(canvas)
            canvas.restore()
        }
        wideLayout?.let {
            canvas.save()
            canvas.translate(
                imageMarginStart,
                narrowLinesCount * oneLineTextHeight + textMarginTop
            )
            it.draw(canvas)
            canvas.restore()
        }
        upper23Layout?.let {
            canvas.translate(imageMarginStart, textMarginTop)
            it.draw(canvas)
        }
    }

    private fun drawImage(canvas: Canvas) {
        val c = canvas.saveCount
        canvas.save()
        canvas.clipRect(imageRectF)
        canvas.concat(drawableMatrix)
        drawable?.draw(canvas)
        canvas.restoreToCount(c)
    }

    private fun getTextColorFromTheme(theme: Resources.Theme): Int {
        val array = theme.obtainStyledAttributes(intArrayOf(android.R.attr.textColor))
        try {
            return array.getColor(0, defaultTextColor)
        } finally {
            array.recycle()
        }
    }

    private fun getFontFromTheme(context: Context): Typeface {
        val typedArray = context.theme.obtainStyledAttributes(intArrayOf(R.attr.fontFamily))
        val fontFamilyFromTheme = try {
            val fontName = typedArray.getString(0)
            if (fontName == null) {
                null
            } else {
                Typeface.create(fontName, Typeface.NORMAL)
            }
        } finally {
            typedArray.recycle()
        }
        return fontFamilyFromTheme ?: Typeface.DEFAULT
    }
}