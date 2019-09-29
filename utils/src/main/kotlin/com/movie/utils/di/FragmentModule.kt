package com.movie.utils.di

import android.os.Build
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import dagger.Module
import dagger.Provides
import java.util.*

/**
 * Base module for dependencies
 * which might be taken from [Fragment] and fragment's context class
 * This class uses [Fragment.getContext] function
 * hence fragment must be bind to activity.
 */
@Module
class FragmentModule{

    @Provides
    fun getLocale(fragment: Fragment): Locale {
        return with(fragment.context!!.resources.configuration) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                locales[0] ?: Locale.ENGLISH
            } else {
                @Suppress("DEPRECATION")
                locale
            }
        }
    }

    @Provides
    fun getInflater(fragment: Fragment):LayoutInflater{
        return fragment.layoutInflater
    }
}