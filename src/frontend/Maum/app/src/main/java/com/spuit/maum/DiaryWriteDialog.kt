package com.spuit.maum

import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import android.widget.TextView
import com.google.gson.Gson
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.spuit.maum.dto.*
import kotlinx.android.synthetic.main.dialog_diary_write.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class DiaryWriteDialog : DialogFragment() {

    interface OnDataPassListener {
        fun onDataPass(data: Emotions?)
    }

    /**
     * 다이얼로그 생성 시 로직
     */
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder: AlertDialog.Builder? = activity?.let {
            AlertDialog.Builder(it, R.style.AlertDialogTheme)
        }

        val view = requireActivity().layoutInflater.inflate(
            R.layout.dialog_diary_write,
            null
        )
        builder?.setView(
            view
        )

        builder?.setPositiveButton("작성하기", DialogInterface.OnClickListener { dialog, id ->
            // 글 작성 시 입력 데이터
            val date = arguments?.getString("date")
            val subject = view.findViewById<TextView>(R.id.write_subject).text
            val content = view.findViewById<TextView>(R.id.write_content).text
            val request: DiaryWriteRequest =
                DiaryWriteRequest(
                    date = date,
                    subject = subject.toString(),
                    content = content.toString()
                )

            Log.d("log_", "입력 제목 : " + subject)
            Log.d("log_", "입력 날짜 : " + date)
            Log.d("log_", "입력 내용 : " + content)

            // TODO: 감정 수치 리스트 받아오기 => 글작성 API
            (GlobalApplication.ApplicationContext() as GlobalApplication).diaryService.write(
                request
            )
                .enqueue(object : Callback<BaseResponse> {
                    override fun onResponse(
                        call: Call<BaseResponse>,
                        response: Response<BaseResponse>
                    ) {
                        if (response.isSuccessful) {
                            Log.d("log_", "성공??")
                            val json = Gson().toJson(response.body()?.data)
                            val data = Gson().fromJson(json, EmotionResponse::class.java)

                            val intent: Intent =
                                Intent(view.context, EmotionCustomActivity::class.java)
                            intent.putExtra("date", date)
                            intent.putExtra("emotions", data.emotion)

                            view.context?.startActivity(
                                intent
                            )
                        } else {
                            Log.d("log_", "" + response.code())
                        }
                    }

                    override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                        Log.d("calendarr", "달력 전체 가져오기 API 실패")
                    }
                })
        })

        val dialog: AlertDialog? = builder?.create()
//        return activity?.let {
//            val alertDialog = AlertDialog.Builder(it)
//
//            alertDialog.setView(
//                requireActivity().layoutInflater.inflate(
//                    R.layout.dialog_diary_write,
//                    null
//                )
//            )
//            alertDialog.setPositiveButton("작성하기", DialogInterface.OnClickListener({ dialog, id ->
//                this.context?.startActivity(
//                    Intent(this.context, BottomNavigationActivity::class.java)
//                )
//            }))
//
//            alertDialog.create()
//        } ?: throw IllegalStateException("Activity is null!")

//        val dateView: TextView? = dialog?.findViewById(R.id.write_date)
//        Log.d("log_", "" + dateView?.text)
//        dateView?.setText(arguments?.getString("date"))

        return dialog!!
    }

}
