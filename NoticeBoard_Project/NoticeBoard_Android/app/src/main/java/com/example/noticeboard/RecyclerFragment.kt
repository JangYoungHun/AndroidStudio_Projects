package com.example.noticeboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_recycler.*
import kotlinx.android.synthetic.main.fragment_recycler.view.*
import kotlinx.android.synthetic.main.fragment_recycler.view.toolbar
import org.json.JSONArray


class RecyclerFragment : Fragment() {
    lateinit var v: View
    lateinit var adapter: RecyclerAdapter
    lateinit var navController: NavController



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        navController = requireActivity().findNavController(R.id.nav_host_fragment)

        v = inflater.inflate(R.layout.fragment_recycler, container, false)
        v.toolbar.inflateMenu(R.menu.menu_main)
        v.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                //새로고침 Menu
                R.id.menu1 -> {
                    showToast("새로고침")
                    adapter.setItemList(getNotices())
                    adapter.notifyDataSetChanged()
                    true
                }
                else -> false
            }
        }

        v.btn_floating.setOnClickListener {
            if(adapter.itemCount() < 20)
                navController.navigate(R.id.action_recyclerFragment_to_memoFragment)
            else
                showToast("메모 최대갯수 제한")
        }

        adapter = RecyclerAdapter()



        adapter.addClickListener( object : RecyclerViewOnClick {
            override fun onClick(itemList: ArrayList<NoticeItem>, position: Int) {
                    var bundle = Bundle().apply {
                        putString("title",itemList[position].title)
                        putString("body",itemList[position].body)
                    }
                navController.navigate(R.id.action_recyclerFragment_to_memoViewFragment,bundle)
            }
        })



        adapter.setItemList(getNotices())

        v.recyclerView.adapter = adapter
        v.recyclerView.layoutManager = LinearLayoutManager(activity?.applicationContext)
        v.recyclerView.setHasFixedSize(true)


        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            showToast("logout")
            navController.popBackStack()
        }


        return v
    }


    fun addItem(item: NoticeItem): NoticeItem {
        return adapter.addItem(item)
    }

    fun addItem(title: String, author: String, body: String): NoticeItem {
        return adapter.addItem(title, author, body)
    }

    fun showToast(message: String) {
        Toast.makeText(activity?.applicationContext, message, Toast.LENGTH_SHORT).show()
    }

    fun getNotices() : ArrayList<NoticeItem>{
        var itemlist: ArrayList<NoticeItem> = ArrayList()
        val json = HttpRequest.readNotice(HttpRequest.Action.READ_NOTICE)
        println(json)
        val jsonArray = JSONArray(json)

        for (i in 0 until jsonArray.length()) {

            var jsonObject = jsonArray.getJSONObject(i)
            itemlist.add(
                NoticeItem(
                    jsonObject.getString("title"),
                    jsonObject.getString("author"),
                    jsonObject.getString("body"),
                    jsonObject.getString("date")
                )
            )
        }
        return itemlist
    }




}