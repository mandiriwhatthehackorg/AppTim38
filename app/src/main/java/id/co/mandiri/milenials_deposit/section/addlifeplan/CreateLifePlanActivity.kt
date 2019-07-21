package id.co.mandiri.milenials_deposit.section.addlifeplan

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import id.co.mandiri.corelibrary.commons.DiffCallback
import id.co.mandiri.corelibrary.commons.GeneralRecyclerView
import id.co.mandiri.corelibrary.commons.showToast
import id.co.mandiri.corelibrary.commons.toRupiah
import id.co.mandiri.milenials_deposit.R
import id.co.mandiri.milenials_deposit.base.BaseActivity
import id.co.mandiri.milenials_deposit.data.hardcoded.LifePlanPackageModel
import id.co.mandiri.milenials_deposit.section.home.HomeActivity.Companion.NEW_ACCOUNT
import id.co.mandiri.milenials_deposit.section.verification.VerificationDialogFragment
import kotlinx.android.synthetic.main.activity_create_life_plan.*
import kotlinx.android.synthetic.main.default_toolbar.view.*
import kotlinx.android.synthetic.main.viewholder_lifeplan_package.view.*
import javax.inject.Inject

class CreateLifePlanActivity : BaseActivity() {
    @Inject
    lateinit var diffCallback: DiffCallback

    @Inject
    lateinit var createLifePlanViewModel: CreateLifePlanViewModel

    lateinit var fragment: DialogFragment

    private val productAdapter by lazy {
        GeneralRecyclerView<LifePlanPackageModel>(
            diffCallback = diffCallback,
            holderResId = R.layout.viewholder_lifeplan_package,
            specificResViewId = R.id.btn_saving,
            onBind = { data, view ->
                bindViewHolder(data, view)
            },
            itemListener = { data, pos, view ->
                collapsedItem(data, pos, view)
            },
            specificViewListener = { data, pos, _ ->
                createLifePlanViewModel.savePlanPackage(pos)
                fragment =
                    VerificationDialogFragment().newInstance(data.packageName, (data.nominal / data.duration), isNew)
            }
        )
    }
    private var fragmentTransaction = supportFragmentManager.beginTransaction()
    private var isNew = true

    override fun onSetupLayout(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_create_life_plan)
        setupToolbarPropertiesWithBackButton(
            toolbar as Toolbar,
            toolbar.toolbar_title,
            R.string.empty_string
        )
    }

    override fun onViewReady(savedInstanceState: Bundle?) {
        initRecycleView()
        observeViewModel()
        isNew = intent.getBooleanExtra(NEW_ACCOUNT, true)
    }

    private fun initRecycleView() {
        with(rv_add_plan) {
            adapter = productAdapter
            layoutManager = LinearLayoutManager(this@CreateLifePlanActivity)
        }
    }

    private fun observeViewModel() {
        with(createLifePlanViewModel) {
            observeSaveSuccess().onResult {
                if (it!!) {
                    fragmentTransaction = supportFragmentManager.beginTransaction()
                    fragmentTransaction.addToBackStack(null)
                    fragment.show(fragmentTransaction, "DIALOG")
                }
            }
            observeLoading().onResult {
                progress_bar.isVisible = it
            }

            observeErrorFirebase().onResult {
                showToast(it.toString())
            }

            observeLifePlanPackage().onResult {
                productAdapter.setData(it)
            }

            boundNetwork {
                if (it) {
                    createLifePlanViewModel.getLifePlanPackage()
                } else {
                    showToast(getString(R.string.text_no_internet))
                }
            }
        }
    }

    private fun bindViewHolder(data: LifePlanPackageModel, view: View) {
        view.tv_plan_for_title.text = data.packageName
        val year = data.duration / 12
        val month = data.duration % 12

        var showDuration = ""

        if (year != 0.0) {
            showDuration += "${year.toInt()} ${getString(R.string.text_year)}"
        }

        if (month != 0.0) {
            showDuration += " ${month.toInt()} ${getString(R.string.text_month)}"
        }

        view.tv_plan_desc.text = getString(R.string.text_add_plan_package_desc, toRupiah(data.nominal), showDuration)
        view.tv_plan_price.text =
            getString(R.string.text_add_nominal_package, toRupiah(data.nominal / data.duration))
        view.child_view.isVisible = data.isExpanded
    }

    private fun collapsedItem(data: LifePlanPackageModel, pos: Int, view: View) {
        data.isExpanded = !data.isExpanded
        productAdapter.notifyItemChanged(pos)
    }
}
