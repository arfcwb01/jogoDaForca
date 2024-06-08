package com.example.jogodaforca

object constants {
    const val SOLICITAR_PALAVRA_SECRETA = 1024
    const val SECREAT_WORD_FROM_NEW_WORD = "SECREAT_WORD_FROM_NEW_WORD"
    const val TESTA_LETRA = "Testar letra"
}

enum class StateOfPlayer(id: Int){
    FORCA_LIMPA(R.drawable.forca_01),
    FOCA_CABECA(R.drawable.forca_cabeca),
    FORCA_BRACO(R.drawable.forma_braco_esquerdo),
    FORCA_TRONCO(R.drawable.forca_dois_bracos),
    FORCA_PERNA_ESQUERDA(R.drawable.forca_perna_esquerda),
    FORCA_CORPO_INTEIRO(R.drawable.forca_corpo_inteiro),
    FORCA_MORTO(R.drawable.forca_morto)
}
