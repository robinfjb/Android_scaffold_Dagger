package robin.scaffold.dagger.repo

import android.content.Context
import robin.scaffold.dagger.utils.Preference
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class PreferenceObject @Inject constructor(context: Context) {
    var userId: String by Preference(context, "user_id", "")
    var userName: String by Preference(context, "user_name", "")
}