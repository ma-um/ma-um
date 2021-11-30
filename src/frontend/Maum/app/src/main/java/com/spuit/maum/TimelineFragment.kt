package com.spuit.maum

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.widget.*
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat.startActivity
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.google.gson.Gson
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.spuit.maum.dto.*
import kotlinx.android.synthetic.main.fragment_timeline.*
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.thread

class TimelineFragment : Fragment() {

    lateinit var glide: RequestManager
    lateinit var adapter: RecyclerViewAdapter
    var list: ArrayList<DiaryCard> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        glide = Glide.with(this)

        Thread(Runnable {
            val body =
                (GlobalApplication.ApplicationContext() as GlobalApplication).diaryService.getTimeline()
                    .execute().body()

            val json = Gson().toJson(body?.data)
            val data = Gson().fromJson(json, DiaryCardListResponse::class.java)

            Log.d("log_", data.toString())

            list = data.diaryCardList!!

//            Log.d("log_", "타임라인 리스트 받음 : " + list.get(0).date)
        }).start()

        Thread.sleep(2000)

//        thread {
//            val body =
//                (GlobalApplication.ApplicationContext() as GlobalApplication).diaryService.getTimeline()
//                    .execute().body()
//
//            val json = Gson().toJson(body?.data)
//            val data = Gson().fromJson(json, DiaryCardListResponse::class.java)
//
//            list = data.diaryCardList!!
//
//            Log.d("log_", "타임라인 리스트 받음 : " + list.get(0).date)
//        }

        adapter = RecyclerViewAdapter(list, glide)

//        (GlobalApplication.ApplicationContext() as GlobalApplication).diaryService.getTimeline()
//            .enqueue(object : Callback<BaseResponse> {
//                override fun onResponse(
//                    call: Call<BaseResponse>,
//                    response: Response<BaseResponse>
//                ) {
//                    if (response.isSuccessful) {
//                        val json = Gson().toJson(response.body()?.data)
//                        val data = Gson().fromJson(json, DiaryCardListResponse::class.java)
//
//                        list = data.diaryCardList!!
//
//                        adapter.notifyDataSetChanged()
//                    }
//                }
//
//                override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
//                }
//            })

        super.onCreate(savedInstanceState)
    }

    // 뷰를 그리는 라이프 사이클
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        if (list.size != 0) {
//            thread {
//                Thread.sleep(1000)
//            }
//            adapter = RecyclerViewAdapter(list, glide) // adapter 가져오기
//        } else {
//            Log.d("log_", "아직도 널이야")
//        }
        val view = inflater.inflate(R.layout.fragment_timeline, container, false)

        val recyclerView: RecyclerView = view.findViewById(R.id.timeline_recyclerview)

        // 검색
        val editText: EditText = view.findViewById(R.id.tl_search)
        editText.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                adapter.filter.filter(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        recyclerView.adapter = adapter // adapter 장착
        recyclerView.layoutManager = LinearLayoutManager(activity) // layoutManager 장착
        return view // 레이아웃(뷰를 그릴 때 어떤 레이아웃 리소스를 쓸건지 지정), 들러 붙을 곳(container), 붙을지 말지
    }

    //    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        val item = menu.findItem(R.id.timeline_search)
//        val searchView: SearchView = item.actionView as SearchView
//
//        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                return false
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                return false
//            }
//        })
//
//        super.onCreateOptionsMenu(menu, inflater)
//    }

}

class RecyclerViewAdapter(
    val itemList: ArrayList<DiaryCard>,
    val glide: RequestManager
) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>(), Filterable {

    var searchedList: ArrayList<DiaryCard>? = itemList

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val date: TextView
        val topEmotion: ImageView
        val subject: TextView
        val content: TextView
        val musicJacket: ImageView
        val musicName: TextView
        val singer: TextView

        init {
            date = itemView.findViewById(R.id.date)
            topEmotion = itemView.findViewById(R.id.timeline_topEmotion)
            subject = itemView.findViewById(R.id.subject)
            content = itemView.findViewById(R.id.content)
            musicJacket = itemView.findViewById(R.id.timeline_music_jacket)
            musicName = itemView.findViewById(R.id.music_name)
            singer = itemView.findViewById(R.id.singer)

            /**
             * 일기 상세보기 화면으로 이동
             */
            itemView.setOnClickListener {
                val position: Int = adapterPosition // position 정보를 가져올 수 있다.
                val item = itemList.get(position)

                val intent = Intent(it.context, DiaryDetailActivity::class.java)
                intent.putExtra("date", date.text)
                startActivity(it.context, intent, null)
            }
        }
    }

    // 뷰를 만들어줌
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_timeline, parent, false) // item 하나가 들어갈 view를 만든다.

        return ViewHolder(view)
    }

    // 세팅이 된 애들을 불러다가 setText
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = searchedList!!.get(position)

        holder.date.setText(item.date)
        Log.d("emotions?", "" + holder.topEmotion)
        holder.topEmotion.setImageResource(
            (GlobalApplication.ApplicationContext() as GlobalApplication).getEmoticonId(item.topEmotion)
        )
        holder.subject.setText(item.subject)
        holder.content.setText(item.content)
        glide
            .load(Uri.parse(item.topMusic!!.jacketUrl))
            .into(holder.musicJacket)
        holder.musicName.setText(item.topMusic!!.name)
        holder.singer.setText(item.topMusic!!.singer)
    }

    // 리스트의 크기 리턴
    override fun getItemCount(): Int {
        return searchedList!!.size
    }

    // 검색어 필터
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint.toString()
                Log.d("log_", "charString: " + charString)

                if (charString.isEmpty()) {
                    searchedList = itemList
                } else {
                    val filteredList = ArrayList<DiaryCard>()
                    for (row in itemList) {
                        if (row.content!!.lowercase()
                                .contains(charString.lowercase()) || row.subject!!.lowercase()
                                .contains(charString.toLowerCase())
                            || row.topMusic!!.name!!.lowercase()
                                .contains(charString.toLowerCase()) || row.topMusic!!.singer!!.lowercase()
                                .contains(charString.toLowerCase())
                        ) {
                            filteredList.add(row)
                        }
                    }
                    searchedList = filteredList
                }
                val filterResults = FilterResults()
                filterResults.values = searchedList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                searchedList = results?.values as ArrayList<DiaryCard>
                notifyDataSetChanged()
            }

        }
    }
}
