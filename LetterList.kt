
//
//  LetterExplanationsView.swift
//  alephbeth
//
//  Created by 御堂 大嗣 on 2020/05/04.
//  Copyright © 2020 御堂 大嗣. All rights reserved.
//
data class LetterList(
    @EnvironmentObject var userData: UserData,
    var withUnderScores: Boolean? = null,
    //フルの文字群
    var letters: List<Letters.Letter>,
    //リストに表示したい文字群
    var listedUpLetters: List<Letters.Letter>? = null,
    var pickers: List<Pickable>? = null,
    var title: String): View {
    val body: Opaque<View>
        get() = List((listedUpLetters ?: letters).filter {
            val keys = it.name.keys
            keys.contains(TransliterationMode.Common.rawValue) || keys.contains(userData.transliterationMode.rawValue)
        }, id = \.id) { letter  -> 
            NavigationLink(destination = LetterExplanation(withUnderScores = this.withUnderScores, letter = letter, letters = this.letters, pickers = this.pickers ?: this.letters)) { LetterRow(letter = letter, letterPronunciation = this.getPronunciationOfLetter(letter = letter)) }
        }.navigationBarTitle(Text(this.title), displayMode = .inline)
    
    fun getPronunciationOfLetter(letter: Letters.Letter) : String? =
        letter.answerId.flatMap { answerId  -> 
            pickers?.filter { picker  -> 
                picker.id == answerId
            }?.firstOrNull()
        }.flatMap { it.name[userData.transliterationMode.rawValue] }
}

data class LetterList_Previews: PreviewProvider {
    companion object {
        val previews: Opaque<View>
            get() = LetterList(letters = lettersData[1].letters, listedUpLetters = listOf(lettersData[1].letters[1]), pickers = lettersData[1].pickers, title = "ヘブライ文字を覚える")
    }
}
