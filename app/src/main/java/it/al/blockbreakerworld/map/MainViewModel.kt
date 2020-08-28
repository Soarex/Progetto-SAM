package it.al.blockbreakerworld.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.*
import java.util.*

class MainViewModel : ViewModel() {
    private val _downloadError = MutableLiveData<Boolean>()
    val downloadError: LiveData<Boolean>
        get() = _downloadError

    private val _levels = MutableLiveData<MutableList<Level>>()
    val levels: LiveData<MutableList<Level>>
        get() = _levels

    private val _averageLocation = MutableLiveData<LatLng>()
    val averageLocation: LiveData<LatLng>
        get() = _averageLocation

    init {
        val parser = LevelJsonParser()

        CoroutineScope(Dispatchers.IO).launch {

            if(!parser.downloadJson("https://api.jsonbin.io/b/5f4544e94d8ce411138088e0/4")) {
                _downloadError.postValue(true)
                while (!parser.downloadJson("https://api.jsonbin.io/b/5f4544e94d8ce411138088e0/4")) delay(3000)
            }

            _downloadError.postValue(false)
            withContext(Dispatchers.Default) {
                val res = LinkedList<Level>()
                var avgLat = 0.0
                var avgLon = 0.0

                for(i in 0 until parser.levelCount) {
                    val level = parser.getLevel(i)
                    avgLat += level.lat
                    avgLon += level.lng

                    res.add(level)
                    _levels.postValue(res)
                }

                avgLat /= parser.levelCount
                avgLon /= parser.levelCount

                _averageLocation.postValue(LatLng(avgLat, avgLon))
            }
        }
    }

}