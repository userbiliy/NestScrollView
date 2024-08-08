package com.example.nestscrollview

import android.os.Bundle
import android.util.Log
import android.view.ViewTreeObserver
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nestscrollview.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val tabStartAxis = intArrayOf(0, 0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater, null, false)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 创建多个独立的 RecyclerView 实例
        val recyclerView1 = createRecyclerView()
        val recyclerView2 = createRecyclerView()
        val recyclerView3 = createRecyclerView()

        // 设置 ViewPager2 的适配器
        binding.viewPager2.adapter = VpgAdapter(listOf(recyclerView1, recyclerView2, recyclerView3))

        // 设置 TabLayout 和 ViewPager2 的联动
        TabLayoutMediator(binding.tabLayout, binding.viewPager2) { tab, position ->
            when (position) {
                0 -> tab.text = "Monday"
                1 -> tab.text = "Tuesday"
                2 -> tab.text = "Wednesday"
            }
        }.attach()

        // 使用 ViewTreeObserver 在布局完成后调整 ViewPager2 的高度
        binding.root.viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                // 移除监听器避免多次调用
                binding.root.viewTreeObserver.removeOnGlobalLayoutListener(this)

                val tabBottomY = with(binding.tabLayout) {
                    setOnScrollChangeListener { _, _, _, _, _ ->
                        getLocationInWindow(tabStartAxis) // 监听滚动计算tabLayout的startY
                    }
                    tabStartAxis[1] + height
                }

                val totalHeight = binding.root.bottom
                binding.viewPager2.layoutParams.height = totalHeight - tabBottomY - 100

                // Instead of requestLayout(), directly apply the new layout params
                binding.viewPager2.layoutParams = binding.viewPager2.layoutParams
            }
        })

    }

    private fun createRecyclerView(): RecyclerView {
        val recyclerView = RecyclerView(this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = RevAdapter(
            listOf(
                "1", "2", "3", "4", "5",
                "6", "7", "8", "9", "10",
                "11", "12", "13", "14", "15",
                "16", "17", "18", "19", "20"
            )
        )
        return recyclerView
    }

}