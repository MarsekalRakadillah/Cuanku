package com.marsekal.cuanku

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.room.Room
import com.marsekal.cuanku.databinding.ActivityAddTransactionBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AddTransactionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddTransactionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnClose.setOnClickListener { finish() }

        binding.ietLabel.addTextChangedListener {
            if (it!!.count() > 0)
                binding.tilLabel.error = null
        }

        binding.ietAmount.addTextChangedListener {
            if (it!!.count() > 0)
                binding.tilAmount.error = null
        }

        binding.btnAddTransaction.setOnClickListener {
            val label = binding.ietLabel.text.toString()
            val amount = binding.ietAmount.text.toString().toDoubleOrNull()
            val descrip = binding.ietDescription.text.toString()

            if (label.isEmpty() && amount==null) {
                binding.tilLabel.error = "Tolong isi label"
                binding.tilAmount.error = "Tolong isi jumlah"
            }
            else if (label.isEmpty()) {
                binding.tilLabel.error = "Tolong isi label"
            }
            else if (amount == null) {
                binding.tilAmount.error = "Tolong isi jumlah"
            }
            else {
                val transactions = Transactions(0, label, amount, descrip)
                insert(transactions)
            }

        }

    }


    private fun insert(transactions: Transactions) {
        val db = Room.databaseBuilder(this,
                TransactionDB::class.java, "transactions").build()
        GlobalScope.launch {
            db.transactionDao().insertTransaction(transactions)
            finish()
        }
    }
}