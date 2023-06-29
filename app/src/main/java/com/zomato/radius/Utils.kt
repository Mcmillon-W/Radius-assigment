package com.zomato.radius

import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.Toast

inline fun <T: Activity?> T?.runSafely (crossinline block: T.() -> Unit) {
    takeIf { this?.isDestroyed == false && this?.isFinishing == false }?.run {
        block()
    }
}

fun Context.showToast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun Context.getFacilityDrawable(string: String?): Drawable {
    return when(string) {
        resources.getString(R.string.apartment) -> resources.getDrawable(R.drawable.apartment_btn)
        resources.getString(R.string.condo) -> resources.getDrawable(R.drawable.condo_btn)
        resources.getString(R.string.boat) -> resources.getDrawable(R.drawable.boat_btn)
        resources.getString(R.string.land) -> resources.getDrawable(R.drawable.land_btn)
        resources.getString(R.string.swimming) -> resources.getDrawable(R.drawable.swimming_btn)
        resources.getString(R.string.garden) -> resources.getDrawable(R.drawable.garden_btn)
        resources.getString(R.string.garage) -> resources.getDrawable(R.drawable.garage_btn)
        resources.getString(R.string.rooms) -> resources.getDrawable(R.drawable.room_btn)
        resources.getString(R.string.noroom) -> resources.getDrawable(R.drawable.noroom_btn)
        else -> resources.getDrawable(R.drawable.apartment_btn) //placeholder
    }
}