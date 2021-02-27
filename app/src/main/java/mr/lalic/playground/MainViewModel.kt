package mr.lalic.playground

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import mr.lalic.playground.data.Epg
import mr.lalic.playground.data.Vod
import mr.lalic.playground.data.XoItem

class MainViewModel : ViewModel() {

    private val _dataSet = MutableLiveData<List<XoItem>>(emptyList())
    val dataSet: LiveData<List<XoItem>> = _dataSet

    val randomData = mutableListOf<XoItem>()

    init {
        (0..22).forEach { index ->
            if (index % 3 == 0) {
                randomData.add(Vod(index.toLong(), "vodPic", "v$index"))
            } else {
                randomData.add(Epg(index.toLong(), "epgPic", "e$index"))
            }
        }
    }

    fun populateWithData() {
        viewModelScope.launch {
            _dataSet.postValue(randomData)
        }
    }
}