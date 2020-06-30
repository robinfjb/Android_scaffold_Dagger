package robin.scaffold.dagger.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import robin.scaffold.dagger.ui.databind.User
import robin.scaffold.dagger.utils.Preference
import javax.inject.Inject

class BindViewModel @Inject constructor(private val context: Context) : ViewModel() {
    var userId: String by Preference(context, "user_id", "")
    var userName: String by Preference(context, "user_name", "")

    private val _text = MutableLiveData<String>().apply {
        value = "This is databinding Fragment"
    }

    fun getText() = _text

    fun getUser(): User {
        val user = User("robin", 777)
        userId = user.userId.toString()
        userName = user.userName
        return user
    }
}