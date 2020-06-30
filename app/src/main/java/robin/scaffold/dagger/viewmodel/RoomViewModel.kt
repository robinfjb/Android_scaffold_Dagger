package robin.scaffold.dagger.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import robin.scaffold.dagger.db.*
import robin.scaffold.dagger.repo.RoomRepository
import robin.scaffold.dagger.utils.coroutine
import javax.inject.Inject

class RoomViewModel@Inject constructor(
        private val repository: RoomRepository
) : ViewModel() {
    private val _text = MutableLiveData<String>("This is tools Fragment")

    fun getTex() = _text

    fun generateData() {
        val s1 = Shop(1,"name1","address1")
        val s2 = Shop(2,"name2","address2")
        val b1 = Book("第一本书", 100, Producer("AAA"), s1.shopId)
        val b2 = Book("第二本书", 200, Producer("BBB"), s1.shopId)
        val b3 = Book("第三本书", 300, Producer("CCC"), s2.shopId)
        coroutine {
            repository.insertBook(b1, b2, b3)
            repository.insertShop(s1, s2)
        }
    }

    fun delete(id:Int) {
        coroutine {
            repository.deleteBookById(id)
        }
    }

    fun queryAll() {
        coroutine {
            val result = repository.queryAll()
            _text.postValue(result)
        }

    }

    fun queryByFilter(name:String, priceLowest:Int, priceHighest:Int) {
        coroutine {
            val result = repository.queryByFilter("%${name}%", priceLowest, priceHighest)
            _text.postValue(result)
        }
    }
}