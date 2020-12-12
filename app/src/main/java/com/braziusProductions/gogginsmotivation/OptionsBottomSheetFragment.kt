package com.braziusProductions.gogginsmotivation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottom_sheet.*


class OptionsBottomSheetFragment : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViews()
    }

    private fun setUpViews() {
        // We can have cross button on the top right corner for providing elemnet to dismiss the bottom sheet
        //iv_close.setOnClickListener { dismissAllowingStateLoss() }
        share_sound.setOnClickListener {
            dismissAllowingStateLoss()
            mListener?.onItemClick(BottomSheetItem.SHARE_SOUND)
        }
        download_image.setOnClickListener {
            dismissAllowingStateLoss()
            mListener?.onItemClick(BottomSheetItem.DOWNLOAD_IMAGE)
        }
        show_all.setOnClickListener {
            dismissAllowingStateLoss()
            mListener?.onItemClick(BottomSheetItem.SHOW_ALL)
        }
        rate.setOnClickListener {
            dismissAllowingStateLoss()
            mListener?.onItemClick(BottomSheetItem.RATE)
        }
    }

    var mListener: ItemClickListener? = null

    interface ItemClickListener {
        fun onItemClick(item: BottomSheetItem)
    }

    companion object {
        @JvmStatic
        fun newInstance(bundle: Bundle): OptionsBottomSheetFragment {
            val fragment = OptionsBottomSheetFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}