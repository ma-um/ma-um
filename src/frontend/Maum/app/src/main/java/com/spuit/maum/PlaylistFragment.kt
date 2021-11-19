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
import com.spuit.maum.dto.DiaryCard
import com.spuit.maum.dto.Music
import com.spuit.maum.dto.PlayListCard
import kotlinx.android.synthetic.main.fragment_timeline.*
import org.w3c.dom.Text

class PlaylistFragment : Fragment() {

    lateinit var glide: RequestManager
    lateinit var adapter: PlayListRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        glide = Glide.with(this)

        // 사용자의 전체 일기 리스트 받아오기
        val list = ArrayList<PlayListCard>()
        val topMusic = PlayListCard(
            1,
            "비상",
            "임재범",
            "https://cdnimg.melon.co.kr/cm/album/images/003/57/227/357227_500.jpg/melon/resize/282/quality/80/optimize"
        )
        val topMusic2 = PlayListCard(
            2,
            "벌써 일년",
            "브라운 아이즈",
            "https://cdnimg.melon.co.kr/cm/album/images/000/02/393/2393_500.jpg/melon/resize/282/quality/80/optimize"
        )
        for (i in 0 until 5) {
            list.add(topMusic)
        }
        for (i in 0 until 5) {
            list.add(topMusic2)
        }
        // TODO: 리스트 받아오기

        adapter = PlayListRecyclerViewAdapter(list, glide) // adapter 가져오기

        super.onCreate(savedInstanceState)
    }

    // 뷰를 그리는 라이프 사이클
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.frag_playlist, container, false)

        val recyclerView: RecyclerView = view.findViewById(R.id.playlist_recyclerview)

        // 검색
        val editText: EditText = view.findViewById(R.id.pl_search)
        editText.addTextChangedListener(object : TextWatcher {
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

}

class PlayListRecyclerViewAdapter(
    val itemList: ArrayList<PlayListCard>,
    val glide: RequestManager
) : RecyclerView.Adapter<PlayListRecyclerViewAdapter.ViewHolder>(), Filterable {

    var searchedList: ArrayList<PlayListCard>? = itemList

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val musicJacket: ImageView
        val musicName: TextView
        val singer: TextView

        init {
            musicJacket = itemView.findViewById(R.id.playlist_music_jacket)
            musicName = itemView.findViewById(R.id.playlist_music_name)
            singer = itemView.findViewById(R.id.playlist_singer)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_playlist, parent, false) // item 하나가 들어갈 view를 만든다.

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = searchedList!!.get(position)

        glide
            .load(Uri.parse(item.jacketUrl))
            .into(holder.musicJacket)
        holder.musicName.setText(item.name)
        holder.singer.setText(item.singer)
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

                if (charString.isEmpty()) {
                    searchedList = itemList
                } else {
                    val filteredList = ArrayList<PlayListCard>()
                    for (row in itemList) {
                        if (row.name!!.lowercase()
                                .contains(charString.lowercase()) || row.singer!!.lowercase()
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
                searchedList = results?.values as ArrayList<PlayListCard>
                notifyDataSetChanged()
            }

        }
    }
}
