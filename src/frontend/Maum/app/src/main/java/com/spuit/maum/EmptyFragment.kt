package com.spuit.maum

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class EmptyFragment : Fragment() {

    // 뷰를 그리는 라이프 사이클
    override fun onCreateView(
        inflater: LayoutInflater, // 뷰를 그려주는 역할
        container: ViewGroup?, // 이 Fragment가 들러붙을 곳. => 부모 뷰
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_empty, container, false) // 레이아웃(뷰를 그릴 때 어떤 레이아웃 리소스를 쓸건지 지정), 들러 붙을 곳(container), 붙을지 말지
    }

}