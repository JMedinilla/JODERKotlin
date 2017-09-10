package ncatz.jvm.jodergenerator

import android.content.Context
import android.support.v7.widget.AppCompatTextView
import android.util.AttributeSet

class CanaroText : AppCompatTextView {
    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        setTypeface(App.canaroExtraBold)
    }
}