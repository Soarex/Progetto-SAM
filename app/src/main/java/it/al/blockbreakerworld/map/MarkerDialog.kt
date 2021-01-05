package it.al.blockbreakerworld.map

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import it.al.blockbreakerworld.GameActivity
import it.al.blockbreakerworld.R
import kotlinx.android.synthetic.main.dialog_marker.view.*


class MarkerDialog : DialogFragment(), View.OnClickListener {
    private val _scoreboardDialog by lazy(LazyThreadSafetyMode.NONE) { ScoreboardDialog() }
    lateinit var currentLevel: Level

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val view = layoutInflater.inflate(R.layout.dialog_marker, null).apply {
                startButton.setOnClickListener(this@MarkerDialog)
                scoreboardButton.setOnClickListener(this@MarkerDialog)

                title.text = currentLevel.title
                description.text = currentLevel.description
            }

            val builder = AlertDialog.Builder(it)
            builder.setView(view)
            builder.create().apply {window!!.setBackgroundDrawable(resources.getDrawable(R.drawable.background_dialog, null))}
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    fun setup(level: Level) {
        currentLevel = level
    }

    override fun onClick(p0: View?) {

        when(p0!!.id) {
            R.id.startButton -> {
                val i = Intent(context, GameActivity::class.java)
                i.putExtra("url", currentLevel.url)
                startActivity(i)
            }

            R.id.scoreboardButton -> {
                _scoreboardDialog.show(parentFragmentManager, "${currentLevel.id}")
            }
        }
    }
}