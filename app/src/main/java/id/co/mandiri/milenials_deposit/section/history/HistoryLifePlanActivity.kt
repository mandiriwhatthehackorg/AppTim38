package id.co.mandiri.milenials_deposit.section.history

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import id.co.mandiri.corelibrary.commons.DiffCallback
import id.co.mandiri.corelibrary.commons.GeneralRecyclerView
import id.co.mandiri.corelibrary.commons.showToast
import id.co.mandiri.corelibrary.commons.toRupiah
import id.co.mandiri.milenials_deposit.R
import id.co.mandiri.milenials_deposit.base.BaseActivity
import id.co.mandiri.milenials_deposit.data.firebase.SavingAuthModel
import id.co.mandiri.milenials_deposit.section.verification.VerificationDialogFragment
import kotlinx.android.synthetic.main.activity_history_life_plan.*
import kotlinx.android.synthetic.main.default_toolbar.view.*
import kotlinx.android.synthetic.main.viewholder_saving_history.view.*
import javax.inject.Inject


class HistoryLifePlanActivity : BaseActivity() {
    @Inject
    lateinit var historyLifePlanViewModel: HistoryLifePlanViewModel

    @Inject
    lateinit var diffCallback: DiffCallback

    private val savingAdapter by lazy {
        GeneralRecyclerView<SavingAuthModel>(
            diffCallback = diffCallback,
            holderResId = R.layout.viewholder_saving_history,
            specificResViewId = R.id.btn_saving,
            onBind = { data, view ->
                bindView(data, view)
            },
            itemListener = { data, pos, view ->

            },
            specificViewListener = { _, _, _ ->
                fragmentTransaction = supportFragmentManager.beginTransaction()
                fragmentTransaction.addToBackStack(null)
                VerificationDialogFragment().show(fragmentTransaction, "DIALOG")
            }
        )
    }

    private var fragmentTransaction = supportFragmentManager.beginTransaction()
    private var nominal = 0.0
    override fun onSetupLayout(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_history_life_plan)
        setupToolbarPropertiesWithBackButton(
            toolbar as Toolbar,
            toolbar.toolbar_title,
            R.string.empty_string
        )
    }

    override fun onViewReady(savedInstanceState: Bundle?) {
        observeViewModel()
        initRecycleView()
    }

    private fun initRecycleView() {
        with(rv_history) {
            adapter = savingAdapter
            layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this@HistoryLifePlanActivity)
        }
    }

    private fun observeViewModel() {
        with(historyLifePlanViewModel) {
            observeLoading().onResult {
                progress_bar.isVisible = it
            }

            observeErrorFirebase().onResult {
                observeErrorFirebase().onResult {
                    showToast(it.toString())
                }
            }

            observeSavingInformation().onResult {
                savingAdapter.setData(it)
                it.forEach { data ->
                    nominal += (data.nominal!! / data.duration!!) * data.done!!
                }
                tv_saving_info.text = toRupiah(nominal)
            }

            boundNetwork {
                if (it) {
                    historyLifePlanViewModel.getSavingInformation()
                } else {
                    showToast(getString(R.string.text_no_internet))
                }
            }
        }
    }

    private fun bindView(data: SavingAuthModel, view: View) {
        view.future_value.text = toRupiah(data.nominal!!)
        view.current_value.text = toRupiah((data.nominal / data.duration!!) * data.done!!.toDouble())
        view.saving_value.text = toRupiah(data.nominal / data.duration)
        view.tv_persentage.text = "${((data.done / data.duration) * 100).toInt()}%"
        view.progress.progress = ((data.done / data.duration) * 100).toInt()
        view.tv_plan_for_title.text = data.name
    }


}
