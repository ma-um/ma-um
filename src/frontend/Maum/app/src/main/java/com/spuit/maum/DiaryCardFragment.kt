package com.spuit.maum

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.material.card.MaterialCardView
import java.net.URL

/**
 * 일기 요약 카드
 */
class DiaryCardFragment : Fragment() {

    /**
     * 뷰 그리는 함수
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // 데이터 꺼내서 View에 바인딩
        val view = inflater.inflate(R.layout.fragment_diary_card, container, false)

        val emotionImgView: ImageView = view.findViewById(R.id.topEmotion) // 감정 이모티콘 이미지
        val titleView: TextView = view.findViewById(R.id.subject) // 일기 제목
        val contentView: TextView = view.findViewById(R.id.content) // 일기 내용
        val musicJacketImgView: ImageView = view.findViewById(R.id.music_jacket) // 음악 앨범 자켓
        val musicNameView: TextView = view.findViewById(R.id.music_name) // 음악 제목
        val singerView: TextView = view.findViewById(R.id.singer) // 가수

        emotionImgView.setImageResource(
            (GlobalApplication.ApplicationContext() as GlobalApplication).getEmoticonId(arguments?.getString("topEmotion"))
        )
        titleView.setText(arguments?.getString("subject"))
        contentView.setText(arguments?.getString("content"))
//        musicJacketImgView.setImageURI(Uri.parse(arguments?.getString("jacketUrl")))
        Glide.with(this).load(Uri.parse(arguments?.getString("jacketUrl"))).into(musicJacketImgView)
//        var bitmap = BitmapFactory.decodeFile("drawable://" + R.drawable.music_jacket)
//        Thread(object: Runnable {
//            override fun run() {
//                bitmap = BitmapFactory.decodeStream(URL(arguments?.getString("jacketUrl")).openConnection()
//                    .getInputStream());
//            }
//        }).start()
//        musicJacketImgView.setImageBitmap(bitmap)
        musicNameView.setText(arguments?.getString("musicName"))
        Log.d("singer_", arguments?.getString("singer")!!)
        singerView.setText(arguments?.getString("singer"))

        Log.d("imageURL : ", "" + arguments?.getString("jacketUrl"))

        return view
    }

    /**
     * 로직 위치하는 함수
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /**
         * 일기 상세보기 페이지로 이동
         */
        view.findViewById<MaterialCardView>(R.id.card).setOnClickListener {
            val date = arguments?.getString("date")

            val intent = Intent(it.context, DiaryDetailActivity::class.java)
            intent.putExtra("date", date)
            ContextCompat.startActivity(it.context, intent, null)
        }

        // TODO: 수정 버튼 클릭 리스너

        // TODO: 삭제 버튼 클릭 리스너
    }

}
