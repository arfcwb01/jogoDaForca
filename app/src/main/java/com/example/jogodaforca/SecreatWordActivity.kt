package com.example.jogodaforca

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.jogodaforca.constants.SECREAT_WORD_FROM_NEW_WORD
import com.example.jogodaforca.databinding.ActivitySecreatWordBinding

class SecreatWordActivity : AppCompatActivity() {

    val binding by lazy {
        ActivitySecreatWordBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.run {
            /*etSecreatWord.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    btnSaveSecreatWord.isEnabled = count >= 2
                }

                override fun afterTextChanged(s: Editable?) {
                }

            })*/

            btnSaveSecreatWord.setOnClickListener {
                etSecreatWord.text.toString().trim()
                val intent = Intent()
                intent.putExtra(SECREAT_WORD_FROM_NEW_WORD, etSecreatWord.text.toString())
                setResult(RESULT_OK, intent)
                finish()
            }
        }
    }
}
