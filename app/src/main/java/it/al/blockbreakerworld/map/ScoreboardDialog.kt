package it.al.blockbreakerworld.map

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import it.al.blockbreakerworld.R
import it.al.blockbreakerworld.database.RecordListAdapter
import kotlinx.android.synthetic.main.dialog_scoreboard.*


class ScoreboardDialog : DialogFragment() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val dao = viewModel.database.recordDao()
            val levelId = tag!!.toInt()

            val view = layoutInflater.inflate(R.layout.dialog_scoreboard, null).apply {
                recordList.adapter = RecordListAdapter(dao, levelId)
            }


            val builder = AlertDialog.Builder(it)
            builder.setView(view)
            builder.create().apply {window!!.setBackgroundDrawable(resources.getDrawable(R.drawable.background_dialog, null))}
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}