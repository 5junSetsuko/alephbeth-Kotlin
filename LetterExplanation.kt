
//
//  LetterExplanation.swift
//  alephbeth
//
//  Created by 御堂 大嗣 on 2020/05/04.
//  Copyright © 2020 御堂 大嗣. All rights reserved.
//
data class LetterExplanation(
    @EnvironmentObject var userData: UserData,
    var withUnderScores: Boolean? = null,
    var letter: Letters.Letter,
    var letters: List<Letters.Letter>,
    var pickers: List<Pickable>): View {
    val body: Opaque<View>
        get() = ScrollView {
            Text("いいから覚えるんだ").padding()
            if ((withUnderScores ?: true)) {
                Text("左右の横線（アンダースコア）を文字の高さの基準にしてください。").padding()
            }
            this.displayFont(text = Text(this.textWithUnderScores(string = letter.script)), name = "活字体")
            if ((!(letter.dagesh ?: true))) {
                this.displayFont(text = Text(this.textWithUnderScores(string = letter.script)).font(.custom("KtavYadCLM-BoldItalic", size = 150)), name = "筆記体")
            }
            HStack {
                Text("名前:")
                Text(letter.name[TransliterationMode.Common.rawValue] ?: letter.name[userData.transliterationMode.rawValue]!!).font(.system(.largeTitle))
            }
            if ((letter.dagesh ?: false)) {
                HStack { Text("ダゲッシュ（真ん中の点）あり") }.padding()
                HStack {
                    Text("元の字:")
                    Text(findLetterById(id = letter.original!!).script).font(.largeTitle)
                }.padding()
            }
            if (((letter.answerId) != null)) {
                HStack {
                    Text("発音: ")
                    Text(pickers.filter { it.id == letter.answerId!! }.firstOrNull().map { it.name[TransliterationMode.Common.rawValue] ?: it.name[userData.transliterationMode.rawValue]!! }!!).font(.largeTitle)
                }.padding()
            }
            Spacer()
            Text("解説:")
            Text(letter.explanation).padding()
        }.navigationBarTitle(Text(letter.script), displayMode = .inline)
    
    fun textWithUnderScores(string: String) : String {
        if ((this.withUnderScores ?: true)) {
            return "_" + string + "_"
        } else {
            return string
        }
    }
    
    fun findLetterById(id: Int) : Letters.Letter {
        val targetId = letters.enumerated().filter({ it.element.id == id }).map({ it.offset })
        return this.letters[targetId[0]]
    }
    
    fun displayFont(text: Text, name: String) : Opaque<View> =
        VStack {
            HStack { text.font(.system(size = 150)).padding() }
            Text(name).padding(.bottom, 100.0)
        }
}

data class LetterExplanation_Previews: PreviewProvider {
    companion object {
        val previews: Opaque<View>
            get() = Group {
                LetterExplanation(letter = lettersData[0].letters[0], letters = lettersData[0].letters, pickers = lettersData[0].letters)
                LetterExplanation(letter = lettersData[0].letters[2], letters = lettersData[0].letters, pickers = lettersData[0].letters)
                //LetterExplanation(letter: lettersData[0].letters[2])
                //LetterExplanation(letter: lettersData[0].letters[3])
                //LetterExplanation(letter: lettersData[0].letters[17])
                LetterExplanation(letter = lettersData[1].letters[0], letters = lettersData[1].letters, pickers = lettersData[1].pickers!!)
            }
    }
}
