package com.example.noticeboard

import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.NavController
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.fragment_memo_view.view.*


class memoViewFragment : Fragment() {

    lateinit var navController:NavController
    lateinit var v:View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        navController = requireActivity().findNavController(R.id.nav_host_fragment)
        v =inflater.inflate(R.layout.fragment_memo_view, container, false)

        setTexts(arguments?.getString("title")!!,arguments?.getString("body")!! )

        v.btn_Ok.setOnClickListener {
            navController.popBackStack()
        }
        return  v
    }



    fun setTexts(item:NoticeItem){
        v.text_body.text = Html.fromHtml("<u>${item.title}</u>",Html.FROM_HTML_MODE_LEGACY)
        v.text_body.text = Html.fromHtml("<u>${item.body}</u>",Html.FROM_HTML_MODE_LEGACY)
    }
    fun setTexts(title:String, body:String){
        v.text_body.text = Html.fromHtml("<u>${title}</u>",Html.FROM_HTML_MODE_LEGACY)
        v.text_body.text = Html.fromHtml("<u>${body}</u>",Html.FROM_HTML_MODE_LEGACY)
    }

}