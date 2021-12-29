package ru.s44khin.devlife.presentation.cardFragment.adapter

class PaginationAdapterHelper(
    private val onLoadMoreCallback: (offset: Int) -> Unit
) {

    companion object {
        private const val DEFAULT_LOAD_MORE_SUBSTITUTIONS = 10
    }

    fun onBind(adapterPosition: Int, totalItemCount: Int) {
        if (adapterPosition > totalItemCount - DEFAULT_LOAD_MORE_SUBSTITUTIONS) {
            onLoadMoreCallback(adapterPosition)
        }
    }
}