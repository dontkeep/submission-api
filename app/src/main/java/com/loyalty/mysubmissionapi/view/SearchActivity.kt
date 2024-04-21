package com.loyalty.mysubmissionapi.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.loyalty.mysubmissionapi.R
import com.loyalty.mysubmissionapi.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity() {
   private lateinit var binding: ActivitySearchBinding

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      binding = ActivitySearchBinding.inflate(layoutInflater)
      setContentView(binding.root)

      with(binding) {
         searchView.setupWithSearchBar(searchBar)
         searchView.editText.setOnEditorActionListener { textView, i, keyEvent ->
            searchBar.setText(searchView.text)
            searchView.hide()

            val intent = Intent()
            intent.putExtra(EXTRA_SELECTED_USER, searchView.text.toString())
            setResult(RESULT, intent)
            finish()
            false
         }
      }
   }

   companion object {
      val EXTRA_SELECTED_USER = "extra_selected_user"
      val RESULT = 110
   }
}