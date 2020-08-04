package com.location.wanandroid.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.location.wanandroid.data.DataRepository
import com.location.wanandroid.databean.DataX
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 *
 * @author tianxiaolong
 * time：2020/7/12 5:08 PM
 * description：
 */
class ArticleViewModel(private  val repository: DataRepository):ViewModel() {

    val listData by lazy {
        MutableLiveData<List<DataX>>()
            .also {
                viewModelScope.launch {
                   val datas = withContext(Dispatchers.IO){
                        repository.getArticleList().data.datas
                    }
                    it.value = datas
                }
            }
    }
}


@Suppress("UNCHECKED_CAST")
class ArticleViewModelFactory(private  val repository: DataRepository):ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ArticleViewModel(repository) as T
    }
}