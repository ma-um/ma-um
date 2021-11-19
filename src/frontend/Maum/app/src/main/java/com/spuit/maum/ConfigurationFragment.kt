package com.spuit.maum

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class ConfigurationFragment : Fragment() {
    // 뷰를 그리는 라이프 사이클
    override fun onCreateView(
        inflater: LayoutInflater, // 뷰를 그려주는 역할
        container: ViewGroup?, // 이 Fragment가 들러붙을 곳. => 부모 뷰
        savedInstanceState: Bundle?
    ): View? {
        // Fragment가 인터페이스를 처음으로 그릴 때 호출된다.
        return inflater.inflate(R.layout.activity_configuration, container, false) // 레이아웃(뷰를 그릴 때 어떤 레이아웃 리소스를 쓸건지 지정), 들러 붙을 곳(container), 붙을지 말지
//        return super.onCreateView(inflater, container, savedInstanceState)
    }
}