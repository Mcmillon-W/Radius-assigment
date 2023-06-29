package com.zomato.radius.data

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.zomato.radius.R
import com.zomato.radius.getFacilityDrawable

class FacilityAdapter(val exclusionFlow: Boolean = false): RecyclerView.Adapter<FacilityAdapter.FacilityVH>() {
    var facilities = listOf<FacilityData>()
    var exclusions = listOf<List<ExclusionData>>()
    var communicator: FacilityCommunicator? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FacilityVH {
        return FacilityVH(LayoutInflater.from(parent.context).inflate(R.layout.item_facility, parent, false))
    }

    override fun onBindViewHolder(holder: FacilityVH, position: Int) {
        if (!exclusionFlow) {
            val data = facilities.getOrNull(position)
            val context = holder.itemView.context
            val checkedColor = context.getColor(R.color.red)
            val uncheckedColor = context.getColor(R.color.black)
            holder.title?.text = data?.name
            holder.radioGrp?.removeAllViews()
            data?.options?.forEach { option ->
                val radioBtn = RadioButton(context).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                    text = option.name
                    id = option.id ?: -1
                    tag = option.id
                    buttonDrawable = context.getFacilityDrawable(option.icon)
                    val padding = context.resources.getDimensionPixelOffset(R.dimen.radio_padding)
                    setPadding(
                        resources.getDimensionPixelOffset(R.dimen.img_padding),
                        padding,
                        0,
                        padding
                    )
                    setBackgroundResource(R.drawable.radio_bgcolor)
                    setTextColor(
                        ColorStateList(
                            arrayOf(
                                intArrayOf(android.R.attr.state_checked),
                                intArrayOf()
                            ), intArrayOf(checkedColor, uncheckedColor)
                        )
                    )
                }
                holder.radioGrp?.addView(radioBtn)
            }
            holder.radioGrp?.apply {
                data?.selectedOptionId = checkedRadioButtonId
                setOnCheckedChangeListener { _, checkedId ->
                    data?.selectedOptionId = checkedRadioButtonId
                    requestLayout()
                    communicator?.checkButton()
                }
            }
        } else {
            val exclusiondata = exclusions.getOrNull(position)
            val firstPair = exclusiondata?.getOrNull(0)
            val secondPair = exclusiondata?.getOrNull(1)
            val firstElementData = facilities?.find { it.id == firstPair?.facilityId }
            val secondElementData = facilities?.find { it.id == secondPair?.facilityId }
            val firstString = firstElementData?.name + ":" + firstElementData?.options?.find { it.id == firstPair?.optionsId }?.name
            val secondString = secondElementData?.name + ":" + secondElementData?.options?.find { it.id == secondPair?.optionsId }?.name
            holder.title?.text = "(${position+1}) $firstString & $secondString"
        }
    }

    override fun getItemCount() = facilities.size

    class FacilityVH : RecyclerView.ViewHolder {
        var title: TextView? = null
        var radioGrp: RadioGroup? = null
        constructor(itemView: View) : super(itemView) {
            title = itemView.findViewById(R.id.textView)
            radioGrp = itemView.findViewById(R.id.radioGrp)
        }
    }
}

interface FacilityCommunicator {
    fun checkButton()
    fun showLoader()
    fun hideLoader()
}