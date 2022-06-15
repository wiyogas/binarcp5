package com.example.binarcp5

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator

class MainActivity : AppCompatActivity(),LandingPageLoginFragment.UserNameInputListener {


    lateinit var viewPager : ViewPager
    lateinit var dotIndicator : DotsIndicator
    lateinit var imgNext : ImageView

    var namaUser = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewPager = findViewById(R.id.viewPager)
        dotIndicator = findViewById(R.id.dots_indicator)
        imgNext = findViewById(R.id.img_Next)

        viewPager.adapter = SimpleViewPagerAdapter(supportFragmentManager)
        dotIndicator.attachTo(viewPager)


        imgNext.setOnClickListener{
            val currentIndex = viewPager.currentItem
            viewPager.currentItem = currentIndex+1

            if(currentIndex == 2) {
                val intentToHome = Intent(this, HomeActivity::class.java)
                intentToHome.putExtra("DATA_USER_NAME", namaUser)
                startActivity(intentToHome)
            }

            if(currentIndex == 0) {
                // step 6 kirim data dari activity ke fragment, panggil method dan kirim data sesuai yg dibutuhkan
                listener?.onDataSend("data from activity")
            }
        }
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                if (position == 1 || position == 0 || namaUser.isNotEmpty()) imgNext.visibility =
                    View.VISIBLE
                else imgNext.visibility = View.GONE
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })
    }


    private inner class SimpleViewPagerAdapter(fm : FragmentManager) : FragmentStatePagerAdapter(fm) {
        override fun getCount(): Int = 3

        override fun getItem(position: Int): Fragment = when (position) {
            0 -> LandingPage1Fragment()
            1 -> LandingPage2Fragment()
            else -> LandingPageLoginFragment()
        }
    }
    // step 4 kirim data dari fragment ke activity, override method interfacenya
    override fun afterUserInputName(input: String) {
        if(input.isNotEmpty()) imgNext.visibility = View.VISIBLE else imgNext.visibility = View.GONE
        namaUser = input
    }

    // step 2 data dari activity ke fragment. bikin variable di activity
    var listener: OnSendDataToFragment? = null

    // step 1 kirim data dari activity ke fragment, bikin interface di activity
    interface OnSendDataToFragment {
        fun onDataSend(input: String)
    }

}