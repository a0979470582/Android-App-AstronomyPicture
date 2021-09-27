package com.bu.cmoney.adapter

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.RequestQueue
import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.NetworkImageView
import com.android.volley.toolbox.Volley
import com.bu.cmoney.R
import com.bu.cmoney.fragment.ApodFragment
import com.bu.cmoney.fragment.ApodFragmentDirections
import com.bu.cmoney.model.Apod
import com.bu.cmoney.tool.BitmapCache


class ApodAdapter(
    private val fragment: ApodFragment,
): RecyclerView.Adapter<ApodAdapter.ViewHolder>() {

    private val apodList: ArrayList<Apod> = ArrayList()
    private val queue: RequestQueue = Volley.newRequestQueue(fragment.context)
    private val imageLoader: ImageLoader = ImageLoader(queue, BitmapCache())

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val textView = itemView.findViewById<TextView>(R.id.textView)
        val imageView = itemView.findViewById<NetworkImageView>(R.id.imageView)

        fun bindData(title: String, bitmap: Bitmap?){
            textView.setText(title)
            imageView.setImageBitmap(bitmap)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_apod, parent, false)
        val holder = ViewHolder(view)
        view.setOnClickListener {
            val action = ApodFragmentDirections.actionListFragmentToDetailFragment(
                apod = apodList[holder.adapterPosition]
            )
            view.findNavController().navigate(action)
        }
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(
            title = apodList[position].title,
            bitmap = null
        )
        holder.imageView.setDefaultImageResId(android.R.color.darker_gray)
        holder.imageView.setErrorImageResId(android.R.drawable.btn_dialog)
        holder.imageView.setImageUrl(apodList[position].url, imageLoader)
    }

    override fun getItemCount() = apodList.size

    fun submitList(_list: List<Apod>){
        apodList.clear()
        apodList.addAll(_list)
        notifyDataSetChanged()
    }
}