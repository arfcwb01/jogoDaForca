package com.example.jogodaforca

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.jogodaforca.constants.SECREAT_WORD_FROM_NEW_WORD
import com.example.jogodaforca.constants.SOLICITAR_PALAVRA_SECRETA
import com.example.jogodaforca.constants.TESTA_LETRA
import com.example.jogodaforca.databinding.ActivityNewGameBinding

class NewGameActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityNewGameBinding.inflate(layoutInflater)
    }

    private var palavraSecretaQueVeioDeDigitarPalavraSecreta = String()

    private var palavraResposta = StringBuilder()

    private var estadoDoJogador = StateOfPlayer.FORCA_LIMPA

    private var jogoAcontecento = false

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
            if (jogoAcontecento) {
                testaLetra()
            } else {
                val intent = Intent(this@NewGameActivity, SecreatWordActivity::class.java)
                startActivityForResult(intent, SOLICITAR_PALAVRA_SECRETA)
            }
        }

        binding.ivForca.setImageResource(getEstadoDoJogador())

    }

    fun testaLetra() {
        atualizaEstadoDoJogador()
        binding.ivForca.setImageResource(getEstadoDoJogador())
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == SOLICITAR_PALAVRA_SECRETA && data != null) {
            data.getStringExtra(SECREAT_WORD_FROM_NEW_WORD).let {
                palavraSecretaQueVeioDeDigitarPalavraSecreta = (it).toString()
                preparaJogo()
            }
        }
    }

    fun atualizaEstadoDoJogador() {
        if (estadoDoJogador == StateOfPlayer.FORCA_MORTO) {
            estadoDoJogador = StateOfPlayer.FORCA_LIMPA
        } else {
            estadoDoJogador = enumValues<StateOfPlayer>().get(estadoDoJogador.ordinal + 1)
        }
    }

    fun getEstadoDoJogador(): Int {
        return when (estadoDoJogador) {
            StateOfPlayer.FORCA_LIMPA -> R.drawable.forca_01
            StateOfPlayer.FOCA_CABECA -> R.drawable.forca_cabeca
            StateOfPlayer.FORCA_BRACO -> R.drawable.forma_braco_esquerdo
            StateOfPlayer.FORCA_TRONCO -> R.drawable.forca_dois_bracos
            StateOfPlayer.FORCA_PERNA_ESQUERDA -> R.drawable.forca_perna_esquerda
            StateOfPlayer.FORCA_CORPO_INTEIRO -> R.drawable.forca_corpo_inteiro
            StateOfPlayer.FORCA_MORTO -> R.drawable.forca_morto
        }
    }

    fun preparaJogo() {
        palavraResposta.clear()
        palavraSecretaQueVeioDeDigitarPalavraSecreta.forEach {
            palavraResposta.append("*")
        }
        binding.tvSecreatWord.text = palavraResposta
        binding.btnSecreatWord.text = TESTA_LETRA
        jogoAcontecento = true
    }
}