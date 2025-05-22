package com.braziusProductions.gogginsmotivation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.braziusProductions.gogginsmotivation.databinding.BottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class OptionsBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: BottomSheetBinding? = null
    private val binding get() = _binding!!

    var mListener: ItemClickListener? = null

    interface ItemClickListener {
        fun onItemClick(item: BottomSheetItem)
    }

    companion object {
        fun newInstance(bundle: Bundle): OptionsBottomSheetFragment {
            val fragment = OptionsBottomSheetFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rate.setOnClickListener {
            mListener?.onItemClick(BottomSheetItem.RATE)
            dismiss()
        }

        binding.showAll.setOnClickListener {
            mListener?.onItemClick(BottomSheetItem.SHOW_ALL)
            dismiss()
        }

        binding.shareSound.setOnClickListener {
            mListener?.onItemClick(BottomSheetItem.SHARE_SOUND)
            dismiss()
        }

        binding.downloadImage.setOnClickListener {
            mListener?.onItemClick(BottomSheetItem.DOWNLOAD_IMAGE)
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}