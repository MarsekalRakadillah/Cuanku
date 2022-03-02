package com.marsekal.cuanku

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.marsekal.cuanku.adapter.TransactionsAdapter
import com.marsekal.cuanku.database.TransactionDB
import com.marsekal.cuanku.database.Transactions
import com.marsekal.cuanku.databinding.ActivityMainBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    private lateinit var deletedTransaction: Transactions
    private lateinit var oldTransaction: List<Transactions>
    private lateinit var transactions: List<Transactions>
    private lateinit var transactionsAdapter: TransactionsAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager

    private lateinit var db: TransactionDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        transactions = arrayListOf(
        )

        transactionsAdapter = TransactionsAdapter(transactions)
        linearLayoutManager = LinearLayoutManager(applicationContext)
        linearLayoutManager.reverseLayout = true
        linearLayoutManager.stackFromEnd = true

        db = Room.databaseBuilder(this,
        TransactionDB::class.java, "transactions").build()

        binding.rvTransactions.apply {
            adapter = transactionsAdapter
            layoutManager = linearLayoutManager
        }

        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                deleteTransaction(transactions[viewHolder.adapterPosition])
            }

        }

        val swipeHelper = ItemTouchHelper(itemTouchHelper)
        swipeHelper.attachToRecyclerView(binding.rvTransactions)

        binding.fabAddTransaction.setOnClickListener {
            startActivity(Intent(this, AddTransactionActivity::class.java))
        }


    }

    private fun fetchAll() {
        GlobalScope.launch {
            transactions = db.transactionDao().getTransaction()

            runOnUiThread {
                updateDashboard()
                transactionsAdapter.setData(transactions)
            }
        }
    }
    fun getCurrency(price: Double): String {
        val format = DecimalFormat("#,###,###")
        return "Rp"+format.format(price).replace(",".toRegex(), ".")
    }
    private fun updateDashboard() {
        val totalAmount = transactions.map { it.amount }.sum()
        val budgetAmount = transactions.filter { it.amount>0 }.map { it.amount }.sum()
        val expenseAmount = totalAmount - budgetAmount

        binding.balance.text = getCurrency(totalAmount)
        binding.budget.text = getCurrency(budgetAmount)
        binding.expense.text = getCurrency(expenseAmount)
    }


    private fun deleteTransaction(transaction: Transactions) {
        deletedTransaction = transaction
        oldTransaction = transactions


            val builder = AlertDialog.Builder(this)
            builder.setMessage("Apa Anda yakin ingin Menghapus?")
                .setNegativeButton("Tidak",DialogInterface.OnClickListener { dialog, which ->
                        transactionsAdapter.setData(transactions)
                })
                .setPositiveButton("Ya", DialogInterface.OnClickListener { dialog, which ->
                    GlobalScope.launch {
                        db.transactionDao().deleteTransaction(transaction)

                        transactions = transactions.filter { it.id != transaction.id }
                        runOnUiThread{
                            updateDashboard()

                            transactionsAdapter.setData(transactions)
                        }
                    }
                }).create().show()

    }

    override fun onResume() {
        super.onResume()
        fetchAll()
    }

}