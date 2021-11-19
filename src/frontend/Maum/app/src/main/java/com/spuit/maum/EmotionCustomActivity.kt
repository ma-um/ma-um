package com.spuit.maum

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.spuit.maum.dto.*
import kotlinx.android.synthetic.main.activity_emotion_custom.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

/**
 * 감정 분석 결과를 커스텀 할 수 있는 화면
 */
class EmotionCustomActivity : AppCompatActivity() {

    lateinit var list: ArrayList<Emotion> // 감정 리스트
    lateinit var recyclerView: RecyclerView
    lateinit var recyclerViewAdapter: EmotionListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emotion_custom)

        // 데이터 받기
        val date = intent.getStringExtra("date") // 날짜 ex) 2021-11-03
        val emotions: DetailEmotions = intent.getSerializableExtra("emotions") as DetailEmotions

        // 초기화
        list = ArrayList<Emotion>()
        // 리스트 채우고 % 수치로 내림차순 정렬
        list.add(Emotion(name = "fear", koreanName = "두려움", percent = emotions.fear))
        list.add(Emotion(name = "surprise", koreanName = "놀람", percent = emotions.surprise))
        list.add(Emotion(name = "anger", koreanName = "분노", percent = emotions.anger))
        list.add(Emotion(name = "sadness", koreanName = "슬픔", percent = emotions.sadness))
        list.add(Emotion(name = "neutrality", koreanName = "중립", percent = emotions.neutrality))
        list.add(Emotion(name = "happiness", koreanName = "행복", percent = emotions.happiness))
        list.add(Emotion(name = "disgust", koreanName = "역겨움", percent = emotions.disgust))
        list.add(Emotion(name = "pleasure", koreanName = "줄거움", percent = emotions.pleasure))
        list.add(
            Emotion(
                name = "embarrassment",
                koreanName = "당황",
                percent = emotions.embarrassment
            )
        )
        list.add(Emotion(name = "unrest", koreanName = "불안", percent = emotions.unrest))
        list.add(Emotion(name = "bruise", koreanName = "의기소침", percent = emotions.bruise))
        list.sortByDescending { it.percent } // % 기준 내림차순 정렬
        Log.d("log_", "내림차순된 감정 리스트 : " + list)

        recyclerView = findViewById(R.id.custom_emotion_list)
        recyclerViewAdapter = EmotionListAdapter(list, LayoutInflater.from(this))

        // adapter, manager 장착
        recyclerView.adapter = recyclerViewAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Drag&Drop 장착
        val mIth = ItemTouchHelper(
            object : ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP or ItemTouchHelper.DOWN,
                ItemTouchHelper.LEFT
            ) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    val fromPosition = viewHolder.adapterPosition
                    val toPosition = target.adapterPosition

                    Collections.swap(list, fromPosition, toPosition)

                    recyclerView.adapter?.notifyItemMoved(fromPosition, toPosition)

                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                }
            })
        mIth.attachToRecyclerView(recyclerView)

        // 글 작성 완료 버튼 클릭
        custom_complete_button.setOnClickListener {
            Log.d("log_", "리스트 바뀐 순서 맞냐 ? 첫번쨰 : " + list.get(0).name + list.get(0).koreanName)

            // TODO: topEmotion 설정 => list.get(0).name해서 request 보내기
            val request: RecommendRequest = RecommendRequest(
                date = date,
                emotions = Emotions(),
                topEmotion = list.get(0).name
            )

            list.forEach {
                when (it.name) {
                    "fear" -> request.emotions?.fear = it.percent
                    "surprise" -> request.emotions?.surprise = it.percent
                    "anger" -> request.emotions?.anger = it.percent
                    "sadness" -> request.emotions?.sadness = it.percent
                    "neutrality" -> request.emotions?.neutrality = it.percent
                    "happiness" -> request.emotions?.happiness = it.percent
                    "disgust" -> request.emotions?.disgust = it.percent
                    "pleasure" -> request.emotions?.pleasure = it.percent
                    "embarrassment" -> request.emotions?.embarrassment = it.percent
                    "unrest" -> request.emotions?.unrest = it.percent
                    "bruise" -> request.emotions?.bruise = it.percent
                    null -> request.emotions?.neutrality = it.percent
                    else -> request.emotions?.neutrality = it.percent
                }
            }

            (GlobalApplication.ApplicationContext() as GlobalApplication).diaryService.recommend(
                request
            )
                .enqueue(object : Callback<BaseResponse> {
                    override fun onResponse(
                        call: Call<BaseResponse>,
                        response: Response<BaseResponse>
                    ) {
                        if (response.isSuccessful) {
                            Log.d("log_", "추천 성공인가요")

                            // 메인화면(캘린더) 액티비티로 이동
                            Toast.makeText(applicationContext, "글 작성이 완료되었습니다", Toast.LENGTH_SHORT)
                            finish()
                            startActivity(
                                Intent(
                                    this@EmotionCustomActivity,
                                    BottomNavigationActivity::class.java
                                )
                            )
                        } else {
                            Log.d("log_", "" + response.code())
                        }
                    }

                    override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                        Log.d("calendarr", "달력 전체 가져오기 API 실패")
                    }
                })
        }
    }

}

/**
 * Recyclerview Adapter
 */
class EmotionListAdapter(
    val itemList: ArrayList<Emotion>,
    val inflater: LayoutInflater
) : RecyclerView.Adapter<EmotionListAdapter.ViewHolder>() {

    // 안에서 inner class로 ViewHolder 만들기
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val emotionImg: ImageView
        val emotionText: TextView
        val seekBar: SeekBar
        val seekBarValue: TextView

        init {
            emotionImg = itemView.findViewById(R.id.custom_emotion_img)
            emotionText = itemView.findViewById(R.id.custom_emotion_text)
            seekBar = itemView.findViewById(R.id.custom_seekbar)
            seekBarValue = itemView.findViewById(R.id.custom_seekbar_value)

            // Seekbar 수치 조정 리스너
            seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    seekBarValue.text = progress.toString()
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                }
            })
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.item_emotion_custom, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val emotion = itemList.get(position)

        holder.emotionImg.setImageResource(
            (GlobalApplication.ApplicationContext() as GlobalApplication).getEmoticonId(emotion.name)
        )
        holder.emotionText.setText(emotion.koreanName)
        holder.seekBar.setProgress(emotion.percent!!, true)
        holder.seekBarValue.setText(emotion.percent!!.toString())
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

}
