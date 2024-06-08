package com.example.jogodaforca

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.jogodaforca.constants.MENSAGEM_DE_DERROTA
import com.example.jogodaforca.constants.MENSAGEM_DE_VITORIA
import com.example.jogodaforca.constants.NOVA_PALAVRA
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
    private val alphabetArrayBase: Array<String> = ('A'..'Z').map { it.toString() }.toTypedArray()
    private val alphabetArray: Array<String> = ('A'..'Z').map { it.toString() }.toTypedArray()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        defineBotaoParaNovaPalavra()

        binding.pcLetra.visibility = View.INVISIBLE
        binding.ivForca.setImageResource(getEstadoDoJogador())

        binding.pcLetra.minValue = 0
        binding.pcLetra.maxValue = 25
        binding.pcLetra.displayedValues = alphabetArray
        binding.pcLetra.setOnValueChangedListener { picker, oldVal, newVal ->
            binding.btnSecreatWord.text = "Testar letra  ${alphabetArray[newVal]}"
            binding.btnSecreatWord.setOnClickListener {
                testaLetra(alphabetArray[newVal])

                binding.tvUsedLetters.text =
                    binding.tvUsedLetters.text.toString() + alphabetArray[newVal]

                binding.pcLetra.value = 0
                alphabetArray[newVal] = "-"
            }
        }
    }

    private fun defineBotaoParaNovaPalavra() {
        binding.btnSecreatWord.text = NOVA_PALAVRA
        binding.btnSecreatWord.setOnClickListener {
            val intent = Intent(this@NewGameActivity, SecreatWordActivity::class.java)
            startActivityForResult(intent, SOLICITAR_PALAVRA_SECRETA)
        }
        palavraSecretaQueVeioDeDigitarPalavraSecreta = String()
        estadoDoJogador = StateOfPlayer.FORCA_LIMPA
        palavraResposta.clear()
        binding.ivForca.setImageResource(getEstadoDoJogador())
        binding.tvUsedLetters.text = String()

        alphabetArrayBase.forEachIndexed { index, s ->
            alphabetArray[index] = s
        }

    }

    fun testaLetra(letraDeTeste: String) {
        if (palavraSecretaQueVeioDeDigitarPalavraSecreta.contains(letraDeTeste, true)) {
            atualizaPalavraResposta(letraDeTeste.toCharArray()[0])
            binding.tvSecreatWord.text = palavraResposta
        } else {
            atualizaEstadoDoJogador()
            binding.ivForca.setImageResource(getEstadoDoJogador())
        }
        analisaResultado()
    }

    private fun analisaResultado() {
        if (!palavraResposta.contains('*')) {
            binding.tvMensagemFimDeJogo.visibility = View.VISIBLE
            binding.tvMensagemFimDeJogo.text = MENSAGEM_DE_VITORIA
            binding.pcLetra.visibility = View.INVISIBLE
            defineBotaoParaNovaPalavra()

        } else if (estadoDoJogador == StateOfPlayer.FORCA_MORTO) {
            binding.tvMensagemFimDeJogo.text = MENSAGEM_DE_DERROTA
            binding.tvMensagemFimDeJogo.visibility = View.VISIBLE
            binding.pcLetra.visibility = View.INVISIBLE
            defineBotaoParaNovaPalavra()
        }
    }

    fun atualizaPalavraResposta(char: Char) {
        palavraSecretaQueVeioDeDigitarPalavraSecreta.forEachIndexed { index, c ->
            if (c.equals(char, true))
                palavraResposta[index] = char
        }
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
        binding.pcLetra.visibility = View.VISIBLE
        jogoAcontecento = true
    }
}