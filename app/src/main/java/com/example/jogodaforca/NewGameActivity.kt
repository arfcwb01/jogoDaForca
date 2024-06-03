package com.example.jogodaforca

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.jogodaforca.constants.REQUEST_SECREAT_WORD
import com.example.jogodaforca.constants.SECREAT_WORD_FROM_NEW_WORD
import com.example.jogodaforca.databinding.ActivityNewGameBinding

class NewGameActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityNewGameBinding.inflate(layoutInflater)
    }

    private lateinit var secreatWordFromNewWord: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnSecreatWord.setOnClickListener {
            val intent = Intent(this@NewGameActivity, SecreatWordActivity::class.java)
            startActivityForResult(intent, REQUEST_SECREAT_WORD)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == REQUEST_SECREAT_WORD && data != null) {
            data.getStringExtra(SECREAT_WORD_FROM_NEW_WORD).let {
                secreatWordFromNewWord = (it ?: null).toString()
                Toast.makeText(this@NewGameActivity, "new word: $secreatWordFromNewWord", Toast.LENGTH_SHORT).show()
            }
        }
    }
}