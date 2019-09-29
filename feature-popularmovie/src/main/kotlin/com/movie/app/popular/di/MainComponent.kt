package com.movie.app.popular.di

import androidx.fragment.app.Fragment
import com.movie.utils.di.FragmentModule
import dagger.BindsInstance
import dagger.Component
import ru.movie.app.network.NetworkApi

@Component(
    dependencies = [NetworkApi::class],
    modules = [MainModule::class, FragmentModule::class]
)
interface MainComponent {

    @Component.Builder
    interface Builder{
        fun build():MainComponent
        @BindsInstance
        fun bind(fragment:Fragment)
    }
}