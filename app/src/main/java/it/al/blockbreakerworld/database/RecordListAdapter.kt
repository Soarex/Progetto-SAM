package it.al.blockbreakerworld.database

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import it.al.blockbreakerworld.R
import kotlinx.android.synthetic.main.item_record.view.*
import kotlinx.coroutines.*


class RecordListAdapter(private val recordDao : RecordDao, private val levelId: Int) : RecyclerView.Adapter<RecordListAdapter.RecordViewHolder>() {
    lateinit var recordList: List<Record>
    var currentMinPosition = 0
    val maxItemsLoaded = 30

    class RecordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.name
        val score: TextView = itemView.score
        val latlng: TextView = itemView.latlng
    }

    override fun getItemCount(): Int {
        return recordDao.getRecordCount(levelId)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_record, parent, false)
        return RecordViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecordViewHolder, position: Int) {
        val currentRecordPosition = position % maxItemsLoaded

        if(position <  currentMinPosition)
            CoroutineScope(Dispatchers.IO).launch {
                recordList = recordDao.getSomeByLevelId(levelId, maxItemsLoaded * position / maxItemsLoaded, maxItemsLoaded)

                withContext(Dispatchers.Main) {
                    holder.name.text = recordList[currentRecordPosition].name
                    holder.score.text = recordList[currentRecordPosition].score.toString()
                    holder.latlng.text = "${recordList[currentRecordPosition].lat}, ${recordList[currentRecordPosition].lng}"
                }
            }
         else {
            holder.name.text = recordList[currentRecordPosition].name
            holder.score.text = recordList[currentRecordPosition].score.toString()
            holder.latlng.text = "${recordList[currentRecordPosition].lat}, ${recordList[currentRecordPosition].lng}"

        }


    }
}