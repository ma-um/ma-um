package com.spuit.maum

import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.IValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.utils.ColorTemplate.COLORFUL_COLORS
import com.github.mikephil.charting.utils.ViewPortHandler

import com.spuit.maum.dto.DetailEmotions

class EmotionChartFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(
            R.layout.frag_emotion_chart,
            container,
            false
        )

        // emotion data
        val data: DetailEmotions = arguments?.getSerializable("emotions") as DetailEmotions
        Log.d("log__", data.sadness!!.toString())

        // create chart
        val pieChart: PieChart = view.findViewById(R.id.pieChart)

        // 리스트 구성
        val list: ArrayList<PieEntry> = ArrayList()
        list.add(
            PieEntry(
                data.fear!!.toFloat(),
                "두려움"
//                ResourcesCompat.getDrawable(requireActivity().resources, R.drawable.disgust_small, null)
            )
        )
        list.add(
            PieEntry(
                data.surprise!!.toFloat(),
                "놀람"
            )
        )
        list.add(
            PieEntry(
                data.anger!!.toFloat(),
                "분노"
            )
        )
        list.add(
            PieEntry(
                data.sadness!!.toFloat(),
                "슬픔"
            )
        )
        list.add(
            PieEntry(
                data.neutrality!!.toFloat(),
                "중립"
            )
        )
        list.add(
            PieEntry(
                data.happiness!!.toFloat(),
                "행복"
            )
        )
        list.add(
            PieEntry(
                data.disgust!!.toFloat(),
                "역겨움"
            )
        )
        list.add(
            PieEntry(
                data.pleasure!!.toFloat(),
                "즐거움"
            )
        )
        list.add(
            PieEntry(
                data.embarrassment!!.toFloat(),
                "당황"
            )
        )
        list.add(
            PieEntry(
                data.unrest!!.toFloat(),
                "불안"
            )
        )
        list.add(
            PieEntry(
                data.bruise!!.toFloat(),
                "의기소침"
            )
        )


        pieChart.setEntryLabelColor(Color.GRAY)
        pieChart.animateY(1000, Easing.EaseInCubic)

        // 색깔 지정
        val colorsItems = ArrayList<Int>()
        for (c in ColorTemplate.VORDIPLOM_COLORS) colorsItems.add(c)
        for (c in ColorTemplate.JOYFUL_COLORS) colorsItems.add(c)
        for (c in COLORFUL_COLORS) colorsItems.add(c)
        for (c in ColorTemplate.LIBERTY_COLORS) colorsItems.add(c)
        for (c in ColorTemplate.PASTEL_COLORS) colorsItems.add(c)
        colorsItems.add(ColorTemplate.getHoloBlue())

        // 라벨 제거
//        pieChart.setDrawSliceText(false)

        val pieDataSet: PieDataSet = PieDataSet(list, "")
//        pieDataSet.valueFormatter = MyXAxisFormatter()
        pieDataSet.apply {
            colors = colorsItems
            valueTextColor = Color.BLACK
            valueTextSize = 16f
//            xValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE
//            yValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE
            valueLinePart1OffsetPercentage = 90f
            valueLinePart1Length = 10f;
            valueLinePart2Length = 50f;
        }

        val pieData: PieData = PieData(pieDataSet)

        pieChart.data = pieData
        pieChart.description.isEnabled = false
        pieChart.centerText = "감정 분포"
        pieChart.animate()

        return view
    }

    inner class MyXAxisFormatter : ValueFormatter() {
        override fun getFormattedValue(value: Float, axis: AxisBase?): String {
            return "" + value.toInt() + "%"
        }
    }

}