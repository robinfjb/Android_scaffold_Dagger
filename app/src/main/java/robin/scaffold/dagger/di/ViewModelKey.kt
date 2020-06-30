package robin.scaffold.dagger.di

import androidx.lifecycle.ViewModel
import dagger.MapKey
import java.lang.annotation.Documented
import kotlin.reflect.KClass


@Documented
@Target(
        AnnotationTarget.FUNCTION
)
@Retention(AnnotationRetention.RUNTIME)
@MapKey//使用@MapKey标注并让value（）的型态为继承ViewModel的类。
annotation class ViewModelKey(val value: KClass<out ViewModel>)