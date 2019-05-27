package com.sdxxtop.zjlguardian.ui.politics

import android.content.Context
import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.sdxxtop.zjlguardian.R;
import com.sdxxtop.zjlguardian.data.PartBean
import kotlinx.android.synthetic.main.fragment_item_part_list_dialog.*
import kotlinx.android.synthetic.main.fragment_item_part_list_dialog_item.view.*

// TODO: Customize parameter argument names
const val ARG_ITEM_COUNT = "item_count"

/**
 *
 * A fragment that shows a list of items as a modal bottom sheet.
 *
 * You can show this modal bottom sheet from your activity like this:
 * <pre>
 *    PartSelectDialogFragment.newInstance(30).show(supportFragmentManager, "dialog")
 * </pre>
 *
 * You activity (or fragment) needs to implement [PartSelectDialogFragment.Listener].
 */
class PartSelectDialogFragment : BottomSheetDialogFragment() {
    private var mListener: Listener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_item_part_list_dialog, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        list.layoutManager = LinearLayoutManager(context)
        var mItemBean = arguments?.getSerializable(ARG_ITEM_COUNT)
        list.adapter = ItemAdapter(mItemBean as ArrayList<PartBean>)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val parent = parentFragment
        if (parent != null) {
            mListener = parent as Listener
        } else {
            mListener = context as Listener
        }
    }

    override fun onDetach() {
        mListener = null
        super.onDetach()
    }

    interface Listener {
        fun onItemClicked(checkBox: CheckBox, position: Int)
    }

    private inner class ViewHolder internal constructor(inflater: LayoutInflater, parent: ViewGroup)
        : RecyclerView.ViewHolder(inflater.inflate(R.layout.fragment_item_part_list_dialog_item, parent, false)) {

        internal val text: TextView = itemView.tv_text
        internal val checkBox: CheckBox = itemView.cb_check
        internal val llRoot: RelativeLayout = itemView.ll_root

        init {
            checkBox.isClickable = false
            llRoot.setOnClickListener {
                mListener?.let {
                    it.onItemClicked(checkBox, adapterPosition)
                    list.adapter?.notifyItemChanged(adapterPosition)
                    dismiss()
                }
            }
        }
    }

    private inner class ItemAdapter internal constructor(private val mItemBean: ArrayList<PartBean>) : RecyclerView.Adapter<ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(LayoutInflater.from(parent.context), parent)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.text.text = mItemBean.get(position).part_name
            holder.checkBox.isChecked = mItemBean.get(position).isCheck
        }

        override fun getItemCount(): Int {
            return mItemBean.size
        }
    }

    companion object {

        // TODO: Customize parameters
        fun newInstance(itemCount: ArrayList<PartBean>): PartSelectDialogFragment =
                PartSelectDialogFragment().apply {
                    arguments = Bundle().apply {
                        putSerializable(ARG_ITEM_COUNT, itemCount)
                    }
                }

    }
}
