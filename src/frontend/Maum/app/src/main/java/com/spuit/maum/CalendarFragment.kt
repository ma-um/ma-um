package com.spuit.maum

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener
import com.prolificinteractive.materialcalendarview.spans.DotSpan
import kotlinx.android.synthetic.main.activity_calendar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList
import androidx.fragment.app.FragmentManager
import com.google.gson.Gson
import com.google.gson.internal.LinkedTreeMap
import com.spuit.maum.dto.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import kotlin.collections.HashSet

class CalendarFragment : Fragment() {

    lateinit var calendarView: MaterialCalendarView // 캘린더 뷰

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    // 뷰를 그리는 라이프 사이클
    override fun onCreateView(
        inflater: LayoutInflater, // 뷰를 그려주는 역할
        container: ViewGroup?, // 이 Fragment가 들러붙을 곳. => 부모 뷰
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(
            R.layout.activity_calendar,
            container,
            false
        ) // 레이아웃(뷰를 그릴 때 어떤 레이아웃 리소스를 쓸건지 지정), 들러 붙을 곳(container), 붙을지 말지

        // Fragment가 인터페이스를 처음으로 그릴 때 호출된다.
        calendarView = v.findViewById(R.id.calendar)

        initCalendar(this@CalendarFragment)

        // 날짜 선택 리스너
        // 날짜 선택 시 글이 없으면 +버튼 활성화 / 있으면 밑에 카드 형식 Fragment 보여주기
        calendarView.setOnDateChangedListener(OnDateSelectedListener { widget, date, selected ->
            val diaryCardFragment: DiaryCardFragment = DiaryCardFragment() // 일기 카드 Fragment
            val emptyFragment: EmptyFragment = EmptyFragment()

            Log.d("log_", "리얼 로그" + date.year + " " + date.month + " " + date.day)

            // 카드 데이터 가져오기
            (GlobalApplication.ApplicationContext() as GlobalApplication).diaryService.getDiaryCard(
                year = date.year,
                month = date.month + 1,
                day = date.day
            )
                .enqueue(object : Callback<BaseResponse> {
                    override fun onResponse(
                        call: Call<BaseResponse>,
                        response: Response<BaseResponse>
                    ) {
                        if (response.isSuccessful) {
                            // Data parsing
                            val json = Gson().toJson(response.body()?.data)
                            val data = Gson().fromJson(json, DiaryCard::class.java)
                            // + 버튼 숨기기
                            plusBtn.hide()

                            // Fragment에 데이터 채워서 보여주기
                            val bundle: Bundle = Bundle()
                            bundle.putString("date", data.date)
                            bundle.putString("topEmotion", data.topEmotion)
                            bundle.putString("subject", data.subject)
                            bundle.putString("content", data.content)
                            bundle.putString("jacketUrl", data.topMusic!!.jacketUrl)
                            bundle.putString("musicName", data.topMusic!!.name)
                            bundle.putString("singer", data.topMusic!!.singer)
                            Log.d("singer__", data.topMusic!!.singer.toString())
                            diaryCardFragment.arguments = bundle

                            // 일기 요약 카드 Fragment 동적으로 추가
                            val fragmentManager: FragmentManager? = getFragmentManager()
                            val fragmentTransaction = fragmentManager?.beginTransaction()
                            fragmentTransaction?.replace(R.id.container, diaryCardFragment)
                            fragmentTransaction?.commit()
                        } else {
                            // + 버튼 보여주기
                            plusBtn.show()

                            // + 버튼 클릭 리스너
                            // date를 전달하여 글 작성 Dialog를 띄워준다.
                            val sdf: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
                            plusBtn.setOnClickListener {
                                val dialog = DiaryWriteDialog()
                                // date 데이터 넣어주기
                                val bundle: Bundle = Bundle()
                                bundle.putString("date", sdf.format(CalendarDay.from(date.year, date.month, date.day).date))
                                dialog.arguments = bundle

                                dialog.show(childFragmentManager, "writeDialog")
                            }

                            // 일기 요약 카드 Fragment 떼기
                            val fragmentManager: FragmentManager? = getFragmentManager()
                            val fragmentTransaction = fragmentManager?.beginTransaction()
                            fragmentTransaction?.remove(diaryCardFragment)
                            fragmentTransaction?.replace(R.id.container, emptyFragment)
                            fragmentTransaction?.commitNow()
                        }
                    }

                    override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                        Log.d("log_", "실패로오냐???")
                    }
                })
        })

        return v
    }

    /**
     * 캘린더 초기화
     */
    fun initCalendar(fragment: Fragment) {
        calendarView.setSelectedDate(CalendarDay.today()) // 현재 날짜로 선택된 날짜 지정
        calendarView.addDecorator(TodayDecorator())

        val year = CalendarDay.today().year
        val month = CalendarDay.today().month + 1
        Log.d("log_", "year : " + year + " " + "month : " + month)

        // API 연동
        (GlobalApplication.ApplicationContext() as GlobalApplication).diaryService.getDiaryList(
            year = year,
            month = month
        )
            .enqueue(object : Callback<BaseResponse> {
                override fun onResponse(
                    call: Call<BaseResponse>,
                    response: Response<BaseResponse>
                ) {
                    if (response.isSuccessful) {
                        val json = Gson().toJson(response.body()?.data)
                        val data = Gson().fromJson(json, CalendarListResponse::class.java)
                        val sdf: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd")

                        data.diaryList?.forEach {
                            calendarView.addDecorator(
                                EventDecorator(
                                    Collections.singleton(
                                        CalendarDay.from(sdf.parse(it.date))
                                    )
                                )
                            )
                        }

                    }
                }

                override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                    Log.d("calendarr", "달력 전체 가져오기 API 실패")
                }
            })
    }
}

/**
 * 현재 날짜를 꾸며주는 Decorator
 */
class TodayDecorator : DayViewDecorator {

    var date = CalendarDay.today()

    override fun shouldDecorate(day: CalendarDay?): Boolean {
        return day?.equals(date)!!
    }

    override fun decorate(view: DayViewFacade?) {
        view?.addSpan(StyleSpan(Typeface.BOLD))
        view?.addSpan(RelativeSizeSpan(1.4f))
        view?.addSpan(ForegroundColorSpan(Color.parseColor("#1D872A")))
    }

}

/**
 * 일기가 작성된 날짜에 표시
 */
class EventDecorator(dates: Collection<CalendarDay>) : DayViewDecorator {

    var dates: HashSet<CalendarDay> = HashSet(dates)

    override fun shouldDecorate(day: CalendarDay?): Boolean {
        return dates.contains(day)
    }

    override fun decorate(view: DayViewFacade?) {
        view?.addSpan(DotSpan(10F, Color.parseColor("#FF0000")))
    }

}
