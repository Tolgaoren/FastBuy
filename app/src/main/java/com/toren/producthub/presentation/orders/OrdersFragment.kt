package com.toren.producthub.presentation.orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.toren.producthub.databinding.FragmentOrdersBinding
import com.toren.producthub.presentation.adapter.OrderAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrdersFragment : Fragment() {

    private var _binding: FragmentOrdersBinding? = null
    private val binding get() = _binding!!
    private val viewModel: OrdersViewModel by viewModels()
    private val orderAdapter = OrderAdapter(mutableListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentOrdersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.ordersRv.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = orderAdapter
            setHasFixedSize(true)
        }
        observeOrders()
    }

    private fun observeOrders() {
        viewModel.orders.observe(viewLifecycleOwner) { orders ->
            binding.apply {
                orderAdapter.updateOrders(orders ?: emptyList())
                println(orders)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}