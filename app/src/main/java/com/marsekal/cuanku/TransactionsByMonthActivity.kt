package com.marsekal.cuanku

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import androidx.recyclerview.widget.LinearLayoutManager
import com.marsekal.cuanku.adapter.TransactionsAdapter
import com.marsekal.cuanku.adapter.TransactionsByMonthAdapter
import com.marsekal.cuanku.database.DataClassByMonth
import com.marsekal.cuanku.database.TransactionDB
import com.marsekal.cuanku.database.Transactions
import com.marsekal.cuanku.databinding.ActivityMainBinding
import com.marsekal.cuanku.R
import com.marsekal.cuanku.databinding.ActivityTransactionsByMonthBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TransactionsByMonthActivity : AppCompatActivity(),  AdapterView.OnItemClickListener {

    private lateinit var binding: ActivityTransactionsByMonthBinding

    private lateinit var monthTransactions: List<DataClassByMonth>
    private lateinit var monthAdapter: TransactionsByMonthAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager


    private lateinit var transactions: List<Transactions>

    private lateinit var db: TransactionDB
    lateinit var myListview: ListView
    var monthArrList = arrayListOf<DataClassByMonth>()
    var totalYear: Int = 0

    var  totalJan: Int = 0
    var  totalFeb: Int = 0
    var  totalMar: Int = 0
    var  totalApr: Int = 0
    var  totalMay: Int = 0
    var  totalJun: Int = 0
    var  totalJul: Int = 0
    var  totalAug: Int = 0
    var  totalSep: Int = 0
    var  totalOct: Int = 0
    var  totalNov: Int = 0
    var  totalDec: Int = 0

    var obj1: DataClassByMonth =
            DataClassByMonth()
    var obj2: DataClassByMonth =
            DataClassByMonth()
    var obj3: DataClassByMonth =
            DataClassByMonth()
    var obj4: DataClassByMonth =
            DataClassByMonth()
    var obj5: DataClassByMonth =
            DataClassByMonth()
    var obj6: DataClassByMonth =
            DataClassByMonth()
    var obj7: DataClassByMonth =
            DataClassByMonth()
    var obj8: DataClassByMonth =
            DataClassByMonth()
    var obj9: DataClassByMonth =
            DataClassByMonth()
    var obj10: DataClassByMonth =
            DataClassByMonth()
    var obj11: DataClassByMonth =
            DataClassByMonth()
    var obj12: DataClassByMonth =
            DataClassByMonth()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransactionsByMonthBinding.inflate(layoutInflater)
        setContentView(binding.root)
        myListview = findViewById(R.id.expensesByMonthLsv)


        linearLayoutManager = LinearLayoutManager(applicationContext)

        for (item in transactions)
        {
            if (item.date.contains("Jan")) {
                totalJan = totalJan + item.amount.toInt()
            } else if (item.date.contains("Feb")) {
                totalFeb = totalFeb + item.amount.toInt()
            }
            if (item.date.contains("Mar")) {
                totalMar = totalMar + item.amount.toInt()
            } else if (item.date.contains("Apr")) {
                totalApr = totalApr + item.amount.toInt()
            }
            if (item.date.contains("May")) {
                totalMay = totalMay + item.amount.toInt()
            } else if (item.date.contains("Jun")) {
                totalJun = totalJun + item.amount.toInt()
            }
            if (item.date.contains("Jul")) {
                totalJul = totalJul + item.amount.toInt()
            } else if (item.date.contains("Aug")) {
                totalAug = totalAug + item.amount.toInt()
            }
            if (item.date.contains("Sep")) {
                totalSep = totalSep + item.amount.toInt()
            } else if (item.date.contains("Oct")) {
                totalOct = totalOct + item.amount.toInt()
            }
            if (item.date.contains("Nov")) {
                totalNov = totalNov + item.amount.toInt()
            } else if (item.date.contains("Dec")) {
                totalDec = totalDec + item.amount.toInt()
            }

            totalYear = totalYear + item.amount.toInt()
        }
        binding.totalYearExpenses.text = totalYear.toString()

        obj1.monthAmount = totalJan.toDouble()
        obj1.monthName = "January"
        monthArrList.add(obj1)
        obj2.monthAmount = totalFeb.toDouble()
        obj2.monthName = "February"
        monthArrList.add(obj2)
        obj3.monthAmount = totalMar.toDouble()
        obj3.monthName = "March"
        monthArrList.add(obj3)
        obj4.monthAmount = totalApr.toDouble()
        obj4.monthName = "April"
        monthArrList.add(obj4)
        obj5.monthAmount = totalMay.toDouble()
        obj5.monthName = "May"
        monthArrList.add(obj5)
        obj6.monthAmount = totalJun.toDouble()
        obj6.monthName = "June"
        monthArrList.add(obj6)
        obj7.monthAmount = totalJul.toDouble()
        obj7.monthName = "July"
        monthArrList.add(obj7)
        obj8.monthAmount = totalAug.toDouble()
        obj8.monthName = "August"
        monthArrList.add(obj8)
        obj9.monthAmount = totalSep.toDouble()
        obj9.monthName = "September"
        monthArrList.add(obj9)
        obj10.monthAmount = totalOct.toDouble()
        obj10.monthName = "October"
        monthArrList.add(obj10)
        obj11.monthAmount = totalNov.toDouble()
        obj11.monthName = "November"
        monthArrList.add(obj11)
        obj12.monthAmount = totalDec.toDouble()
        obj12.monthName = "December"
        monthArrList.add(obj12)

        monthAdapter = TransactionsByMonthAdapter(this, R.layout.transactions_layout, monthArrList)

        linearLayoutManager = LinearLayoutManager(applicationContext)

    }

    private fun fetchAll() {
        GlobalScope.launch {
            transactions = db.transactionDao().getTransaction()

        }
    }
    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        TODO("Not yet implemented")
    }

    override fun onResume() {
        super.onResume()
        fetchAll()
    }
}