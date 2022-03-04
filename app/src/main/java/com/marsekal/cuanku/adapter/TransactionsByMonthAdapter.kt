package com.marsekal.cuanku.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.marsekal.cuanku.DetailTransactionActivity
import com.marsekal.cuanku.MonthTransactionsActivity
import com.marsekal.cuanku.R
import com.marsekal.cuanku.database.DataClassByMonth
import com.marsekal.cuanku.databinding.TransactionsByMonthLayoutBinding
import java.text.DecimalFormat

class TransactionsByMonthAdapter(
        private var monthTransactions: List<DataClassByMonth>
): RecyclerView.Adapter<TransactionsByMonthAdapter.TransactionHolder>() {

    class TransactionHolder(val binding: TransactionsByMonthLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionHolder {
       return TransactionHolder(
               TransactionsByMonthLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
       )
    }
    fun getCurrency(price: Double): String {
        val format = DecimalFormat("#,###,###")
        return "Rp" + format.format(price).replace(",".toRegex(), ".")
    }

    override fun onBindViewHolder(holder: TransactionHolder, position: Int) {
        val monthTransaction = monthTransactions[position]
        val context = holder.binding.tvAmount.context

        if (monthTransaction.monthAmount >= 0) {
            holder.binding.tvAmount.text = getCurrency(monthTransaction.monthAmount)
            holder.binding.tvAmount.setTextColor(ContextCompat.getColor(context, R.color.green))
        } else {
            holder.binding.tvAmount.text = getCurrency(Math.abs(monthTransaction.monthAmount))
            holder.binding.tvAmount.setTextColor(ContextCompat.getColor(context, R.color.red))
        }

        holder.binding.tvMonth.text = monthTransaction.monthName

//        holder.itemView.setOnClickListener {
//            val intent = Intent(context, MonthTransactionsActivity::class.java)
//            intent.putExtra("ra", monthTransaction)
//            context.startActivity(intent)
//        }
    }

    override fun getItemCount() = monthTransactions.size

    fun setData(monthTransactions: List<DataClassByMonth>) {
        this.monthTransactions = monthTransactions
        notifyDataSetChanged()
    }

}