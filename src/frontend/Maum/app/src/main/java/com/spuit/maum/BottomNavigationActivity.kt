package com.spuit.maum

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_bottom_navigation.*

class BottomNavigationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_navigation)

        // adapter 만들기
        val adapter = FragmentPagerAdapter(supportFragmentManager, 4)
        view_pager.adapter = adapter

        // nav에 리스너 달기
        nav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.calendar_id -> {
                    view_pager.setCurrentItem(0)
                }
                R.id.timeline_id -> {
                    view_pager.setCurrentItem(1)
                }
                R.id.playlist_id -> {
                    view_pager.setCurrentItem(2)
                }
                R.id.config_id -> {
                    view_pager.setCurrentItem(3)
                }
            }
            true
        }

        // pager와 tab을 연결
        view_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                // 네비게이션 메뉴 아이템 체크상태
                nav.menu.getItem(position).isChecked = true
            }
        })
    }
}

class FragmentPagerAdapter(
    fragmentManager: FragmentManager, // Pager 리스트는 fragment로 구성되어 있다. 페이지 하나하나가 fragment
    val tabCount: Int
) : FragmentStatePagerAdapter(fragmentManager) {

    override fun getCount(): Int {
        return tabCount
    }

    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> {
                return CalendarFragment()
            }
            1 -> {
                return TimelineFragment()
            }
            2 -> {
                return PlaylistFragment()
            }
            3 -> {
                return ConfigurationFragment()
            }
            else -> {
                return CalendarFragment()
            }
        }
    }

}
