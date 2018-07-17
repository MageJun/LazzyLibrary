package com.android.kotlindemo.view.fragment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.android.kotlindemo.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [HomeFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
abstract class BaseFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    open abstract fun onFragmentViewCreate(inflater: LayoutInflater, container: ViewGroup?,
                                           savedInstanceState: Bundle?): View?;

    open  fun onFragmentCreate(savedInstanceState: Bundle?) {
    }
    open fun onFragmentViewDestory(){
    }
    open fun onFragmentDestory(){
    }


     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        onFragmentCreate(savedInstanceState);
    }

     override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return onFragmentViewCreate(inflater,container,savedInstanceState)
    }

     override fun onDestroy() {
        super.onDestroy()
        onFragmentDestory()
    }

     override fun onDestroyView() {
        super.onDestroyView()
        onFragmentViewDestory()
    }

}
