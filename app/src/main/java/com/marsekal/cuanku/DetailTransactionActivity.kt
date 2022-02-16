package com.marsekal.cuanku

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.InputDevice
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.addTextChangedListener
import androidx.room.Room
import com.marsekal.cuanku.databinding.ActivityDetailTransactionBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class DetailTransactionActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDetailTransactionBinding

    private lateinit var transaction: Transactions
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var transaction = intent.getSerializableExtra("transaction") as Transactions

        binding.ietLabel.setText(transaction.label)
        binding.ietAmount.setText(transaction.amount.toString())
        binding.ietDescription.setText(transaction.description)

        binding.layoutDetail.setOnClickListener {
            this.window.decorView.clearFocus()

            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)
        }

        binding.btnClose.setOnClickListener { finish() }

        binding.ietLabel.addTextChangedListener {
            binding.btnUpdateTransaction.visibility = View.VISIBLE
            if (it!!.count() > 0)
                binding.tilLabel.error = null
        }

        binding.ietAmount.addTextChangedListener {
            binding.btnUpdateTransaction.visibility = View.VISIBLE
            if (it!!.count() > 0)
                binding.tilAmount.error = null
        }

        binding.ietDescription.addTextChangedListener {
            binding.btnUpdateTransaction.visibility = View.VISIBLE
        }

        binding.btnUpdateTransaction.setOnClickListener {
            val label = binding.ietLabel.text.toString()
            val amount = binding.ietAmount.text.toString().toDoubleOrNull()
            val descrip = binding.ietLabel.text.toString()

            if (label.isEmpty())
                binding.tilLabel.error = "Tolong isi label"

            else if (amount == null)
                binding.tilAmount.error = "Tolong isi jumlah"
            else {
                val transactions = Transactions(transaction.id, label, amount, descrip
//                ,Calendar.getInstance().time
                )
                update(transactions)
            }

        }

    }


    private fun update(transactions: Transactions) {
        val db = Room.databaseBuilder(this,
            TransactionDB::class.java, "transactions").build()
        GlobalScope.launch {
            db.transactionDao().updateTransaction(transactions)
            finish()
        }
    }
}