package libiao.libiaodemo.android.ui.constraint

import android.app.Activity
import android.graphics.BitmapFactory
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ImageSpan
import android.widget.TextView
import libiao.libiaodemo.R

class ConstraintActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.constraint_main)

        val tv = findViewById<TextView>(R.id.tv)

        val imageSpan = ImageSpan(this, BitmapFactory.decodeResource(resources, R.mipmap.anchor))
        val spannableString = SpannableString("11")
        spannableString.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        tv.text = spannableString
    }
}