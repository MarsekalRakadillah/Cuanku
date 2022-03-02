package com.marsekal.cuanku.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.marsekal.cuanku.DetailTransactionActivity
import com.marsekal.cuanku.R
import com.marsekal.cuanku.database.Transactions
import com.marsekal.cuanku.databinding.TransactionsLayoutBinding
import java.text.DecimalFormat

class TransactionsAdapter(
    private var transactions: List<Transactions>
): RecyclerView.Adapter<TransactionsAdapter.TransactionHolder>() {

    class TransactionHolder(val binding: TransactionsLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionHolder {
        return TransactionHolder(
            TransactionsLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }
    fun getCurrency(price: Double): String {
        val format = DecimalFormat("#,###,###")
        return "Rp" + format.format(price).replace(",".toRegex(), ".")
    }
    override fun onBindViewHolder(holder: TransactionHolder, position: Int) {
        val transaction = transactions[position]
        val context = holder.binding.amount.context

        if (transaction.amount >= 0) {
            holder.binding.amount.text = getCurrency(transaction.amount)
            holder.binding.amount.setTextColor(ContextCompat.getColor(context, R.color.green))
        } else {
            holder.binding.amount.text = getCurrency(Math.abs(transaction.amount))
            holder.binding.amount.setTextColor(ContextCompat.getColor(context, R.color.red))
        }

        holder.binding.label.text = transaction.label

        holder.itemView.setOnClickListener {
            val intent = Intent(context, DetailTransactionActivity::class.java)
            intent.putExtra("transaction", transaction)
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = transactions.size

    fun setData(transactions: List<Transactions>) {
        this.transactions = transactions
        notifyDataSetChanged()
    }
}