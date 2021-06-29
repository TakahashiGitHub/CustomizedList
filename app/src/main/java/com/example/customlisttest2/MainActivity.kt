package com.example.customlisttest2

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.ListView
import android.widget.TextView

//https://joyplot.com/documents/kotlin-listview-button/
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 初期のリスト項目を設定
        val arrayAdapter = MyArrayAdapter(this, 0).apply {
            add(ListItem("Windows"))
            add(ListItem("macOS"))
            add(ListItem("Unix"))
        }

        // ListView にリスト項目と ArrayAdapter を設定
        val listView: ListView = findViewById(R.id.mainlist)
        listView.adapter = arrayAdapter
    }

    // リスト項目のデータ
    class ListItem(val title: String) {

        var description: String = "No description"

        constructor(title: String, description: String) : this(title) {
            this.description = description
        }
    }

    // リスト項目を再利用するためのホルダー
    data class ViewHolder(
        val titleView: TextView,
        val deleteIcon: ImageButton
    )

    // 自作のリスト項目データを扱えるようにした ArrayAdapter
    class MyArrayAdapter : ArrayAdapter<ListItem> {

        private var inflater: LayoutInflater? =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater?

        constructor(context: Context, resource: Int) : super(context, resource) {}

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

            var viewHolder: ViewHolder? = null
            var view = convertView

            // 再利用の設定
            if (view == null) {

                view = inflater!!.inflate(R.layout.row, parent, false)

                viewHolder = ViewHolder(
                    view.findViewById(R.id.rowText),//text1が見つからない？
                    view.findViewById(R.id.rowbutton)
                )
                view.tag = viewHolder
            } else {
                viewHolder = view.tag as ViewHolder
            }

            // 項目の情報を設定
            val listItem = getItem(position)
            viewHolder.titleView.text = listItem!!.title
            viewHolder.deleteIcon.setOnClickListener { _ ->
                // 削除ボタンをタップしたときの処理
                this.remove(listItem)
                this.notifyDataSetChanged()
            }

            return view!!
        }
    }
}