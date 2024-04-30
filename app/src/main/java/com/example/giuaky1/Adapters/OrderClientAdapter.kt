package com.example.giuaky1.Adapters

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.giuaky1.Administrator.Activitys.DetailOrder
import com.example.giuaky1.Firebase.DataHandler
import com.example.giuaky1.Models.CartModel
import com.example.giuaky1.Models.Order
import com.example.giuaky1.R

class OrderClientAdapter(private var orderList: List<Order>) : RecyclerView.Adapter<OrderClientAdapter.OrderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_order_list_client, parent, false)
        return OrderViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orderList[position]
        holder.orderIdTextView.text = "ID : ${order.orderID}"
        holder.statusTextView.text = order.state
        holder.dateAndTimeTextView.text = order.time
        if(order.state == "Đã hủy") {
            holder.cancelButton.visibility = View.GONE
        }
        if(order.state == "Đã giao hàng") {
            holder.cancelButton.visibility = View.GONE
        }
        holder.cancelButton.setOnClickListener {
            DataHandler.updateState(order.uID, order.orderID, "Đã hủy")
        }
        holder.detailsButton.setOnClickListener {
            DataHandler.getOrderDetails(order.orderID, order.uID) { orderDetail ->
                val intent = Intent(it.context, DetailOrder::class.java)
                intent.putExtra("orderDetail", orderDetail)
                it.context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return orderList.size
    }
    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newOrderList: List<Order>) {
        orderList = newOrderList
        notifyDataSetChanged()
    }

    class OrderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val orderIdTextView: TextView = view.findViewById(R.id.orderID)
        val statusTextView: TextView = view.findViewById(R.id.orderStatus)
        val dateAndTimeTextView: TextView = view.findViewById(R.id.dateAndTime)
        val detailsButton: Button = view.findViewById(R.id.detailsButton)
        val cancelButton: Button = view.findViewById(R.id.cancelButton)

    }
}