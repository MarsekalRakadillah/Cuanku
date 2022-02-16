package com.marsekal.cuanku

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.room.Room
import com.marsekal.cuanku.databinding.ActivityAddTransactionBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

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

        val myCalendar = Calendar.getInstance()

        val datePicker = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateLable(myCalendar)
        }

        binding.tilDate.setOnClickListener {
            DatePickerDialog(this, datePicker, myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        var date = Date()
        var simpleDate = SimpleDateFormat("HH:mm")

    }

    private fun insert(transactions: Transactions) {
        val db = Room.databaseBuilder(this,
                TransactionDB::class.java, "transactions").build()
        GlobalScope.launch {
            db.transactionDao().insertTransaction(transactions)
            finish()
        }
    }

    private fun updateLable(myCalendar: Calendar) {
        val sdf = SimpleDateFormat("d/M/yyyy", Locale.JAPANESE)
        binding.tilDate.setText(sdf.format(myCalendar.time))
    }


}