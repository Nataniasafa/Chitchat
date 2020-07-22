package com.natania.chitchat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth
import com.natania.chitchat.fragment.CallFragment
import com.natania.chitchat.fragment.ChatFragment
import com.natania.chitchat.fragment.SearchFragment

class MainActivity : AppCompatActivity()  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val toolbar: Toolbar = findViewById(R.id.tool_bar)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = ""

        val tabLayout: TabLayout = findViewById(R.id.tab_layout)
        val viewPager : ViewPager = findViewById(R.id.view_pager)

        val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        viewPagerAdapter.addFragment(ChatFragment(),"chats")
        viewPagerAdapter.addFragment(CallFragment(), "call")
        viewPagerAdapter.addFragment(SearchFragment(), "search")

        viewPager.adapter = viewPagerAdapter
        tabLayout.setupWithViewPager(viewPager)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_sign_out->{
                FirebaseAuth.getInstance().signOut()

                val intent = Intent(this,WelcomeActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()

                return true
            }
        }
        return false
    }

    internal class ViewPagerAdapter(fragmentManager : FragmentManager):
        FragmentPagerAdapter(fragmentManager){

        private val titles : ArrayList<String>
        private val fragments : ArrayList<Fragment>
        init {
            titles = ArrayList()
            fragments = ArrayList()
        }
        override fun getItem(position: Int): Fragment {
            return fragments[position]
        }

        override fun getCount(): Int {
            return fragments.size
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return titles[position]
        }
        fun addFragment(fragment : Fragment, title: String){
            fragments.add(fragment)
            titles.add(title)

        }

    }
}

