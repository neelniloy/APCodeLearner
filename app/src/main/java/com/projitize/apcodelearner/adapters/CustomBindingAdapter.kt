package com.braineer.scheduler.adapters

import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.braineer.scheduler.R
import com.google.android.material.card.MaterialCardView
import java.text.SimpleDateFormat
import java.util.*


@BindingAdapter("app:setTime")
fun setTime(textView: TextView, timestamp: Long) {
    textView.text = SimpleDateFormat("hh:mm a")
        .format(Date(timestamp))
}

@BindingAdapter("app:setTaskStatus")
fun setTaskStatus(iv: ImageView, status: String) {
    val icon = when (status) {
        "completed" -> R.drawable.tick
        "missed" -> R.drawable.cross
        else -> R.drawable.pending
    }
    iv.setImageResource(icon)
}

@BindingAdapter("app:setCardBackByStatus")
fun setCardBackByStatus(card: MaterialCardView, status: String) {
    val color = when (status) {
        "completed" -> R.color.colorGreen
        "missed" -> R.color.colorRed
        else -> R.color.colorYellow
    }
    card.setCardBackgroundColor(ContextCompat.getColor(card.context,color))
}