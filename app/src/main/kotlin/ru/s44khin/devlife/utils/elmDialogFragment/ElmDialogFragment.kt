package ru.s44khin.devlife.utils.elmDialogFragment

import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import vivid.money.elmslie.android.screen.ElmDelegate
import vivid.money.elmslie.android.screen.ElmScreen
import vivid.money.elmslie.android.storeholder.LifecycleAwareStoreHolder
import vivid.money.elmslie.android.storeholder.StoreHolder

abstract class ElmDialogFragment<Event : Any, Effect : Any, State : Any> :
    BottomSheetDialogFragment(), ElmDelegate<Event, Effect, State> {

    @Suppress("LeakingThis", "UnusedPrivateMember")
    private val elm = ElmScreen(this, lifecycle) { requireActivity() }

    protected val store
        get() = storeHolder.store

    override val storeHolder: StoreHolder<Event, Effect, State> by lazy(LazyThreadSafetyMode.NONE) {
        LifecycleAwareStoreHolder(lifecycle, ::createStore)
    }
}