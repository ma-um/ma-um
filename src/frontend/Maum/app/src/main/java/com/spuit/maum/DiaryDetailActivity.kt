package com.spuit.maum

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.google.gson.Gson
import com.makeramen.roundedimageview.RoundedImageView
import com.spuit.maum.dto.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

/**
 * 일기 상세보기 View
 */
class DiaryDetailActivity : AppCompatActivity() {

    // View Components
    lateinit var date: TextView
    lateinit var topEmotion: ImageView
    lateinit var subject: TextView
    lateinit var content: TextView
    lateinit var viewPager: ViewPager2

    var sliderHandler: Handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diary_detail)

        // Init viewComponents
        date = findViewById(R.id.detail_date)
        topEmotion = findViewById(R.id.detail_diary_emotion_image)
        subject = findViewById(R.id.detail_subject)
        content = findViewById(R.id.detail_content)
        viewPager = findViewById(R.id.detail_music_image_slider)

        /**
         * Get Data
         */
        val z = intent.getStringExtra("date")
        Log.d("log_", "일기 상세보기에서 받응 date : " + z)
        val sdf: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        val realDate = sdf.parse(z)

        val cal: Calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Seoul"))
        cal.setTime(realDate)
        val year: Int = cal.get(Calendar.YEAR)
        val month: Int = cal.get(Calendar.MONTH) + 1
        val day: Int = cal.get(Calendar.DAY_OF_MONTH)
//        val test = sdf.parse("2021-11-11")


        Log.d("log_", "year=>" + year)
        Log.d("log_", "month=>" + month)
        Log.d("log_", "day=>" + day)

        (application as GlobalApplication).diaryService.getDiaryDetail(
            year = year,
            month = month,
            day = day
        )
            .enqueue(object : Callback<BaseResponse> {
                override fun onResponse(
                    call: Call<BaseResponse>,
                    response: Response<BaseResponse>
                ) {
                    if (response.isSuccessful) {
                        // Data parsing
                        val json = Gson().toJson(response.body()?.data)
                        val data = Gson().fromJson(json, DiaryDetailResponse::class.java)

                            Log.d("__topemotion", data.emotions?.topEmotion.toString())

                        // 데이터 - 뷰 바인딩
                        date.text = data.date
                        topEmotion.setImageResource(
                            (GlobalApplication.ApplicationContext() as GlobalApplication).getEmoticonId(
                                data.emotions?.topEmotion
                            )
                        )
                        subject.setText(data.subject)
                        content.setText(data.content)

                        viewPager.adapter = SliderAdapter(
                            data.musicList!!,
                            Glide.with(this@DiaryDetailActivity),
                            viewPager
                        )
                        viewPager.clipToPadding = false
                        viewPager.clipChildren = false
                        viewPager.offscreenPageLimit = 3
                        viewPager.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
                        val cpt = CompositePageTransformer()
                        cpt.addTransformer(MarginPageTransformer(40))
                        cpt.addTransformer(object : ViewPager2.PageTransformer {
                            override fun transformPage(page: View, position: Float) {
                                val r: Float = 1 - Math.abs(position)
                                page.scaleY = 0.85f + r * 0.15f
                            }
                        })
                        viewPager.setPageTransformer(cpt)
                        viewPager.registerOnPageChangeCallback(object :
                            ViewPager2.OnPageChangeCallback() {
                            override fun onPageSelected(position: Int) {
                                super.onPageSelected(position)
                                sliderHandler.removeCallbacks(sliderRunnable)
                                sliderHandler.postDelayed(sliderRunnable, 3000)
                            }
                        })

                        // Chart
                        val chartFragment: EmotionChartFragment = EmotionChartFragment()
                        val bundle: Bundle = Bundle()
                        bundle.putSerializable("emotions", data.emotions)
                        chartFragment.arguments = bundle

                        val fragmentManager: FragmentManager = supportFragmentManager
                        val fragmentTransaction = fragmentManager.beginTransaction()
                        fragmentTransaction.replace(R.id.chart_container, chartFragment)
                        fragmentTransaction.commit()
                    }
                }

                override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                    Log.d("log_", "실패")
                }
            })
    }

    val sliderRunnable: Runnable = object : Runnable {
        override fun run() {
            viewPager.setCurrentItem(viewPager.currentItem + 1)
        }
    }

    override fun onPause() {
        super.onPause()
        sliderHandler.removeCallbacks(sliderRunnable)
    }

    override fun onResume() {
        super.onResume()
        sliderHandler.postDelayed(sliderRunnable, 3000)
    }
}

class SliderAdapter(
    val itemList: ArrayList<Music>,
    val glide: RequestManager,
    val viewPager: ViewPager2
) : RecyclerView.Adapter<SliderAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val musicJacket: RoundedImageView
        val musicName: TextView
        val singer: TextView

        init {
            musicJacket = itemView.findViewById(R.id.music_image_slider)
            musicName = itemView.findViewById(R.id.detail_music_name)
            singer = itemView.findViewById(R.id.detail_music_singer)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.music_slider_item_container, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList.get(position)

        glide
            .load(Uri.parse(item.jacketUrl))
            .into(holder.musicJacket)
        holder.musicName.setText(item.name)
        holder.singer.setText(item.singer)

        if (position == itemList.size - 2) {
            viewPager.post(runnable)
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    val runnable: Runnable = object : Runnable {
        override fun run() {
            itemList.addAll(itemList)
            notifyDataSetChanged()
        }
    }

}
