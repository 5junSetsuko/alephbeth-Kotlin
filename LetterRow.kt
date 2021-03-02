
//
//  LetterRow.swift
//  alephbeth
//
//  Created by 御堂 大嗣 on 2020/05/04.
//  Copyright © 2020 御堂 大嗣. All rights reserved.
//
data class LetterRow(
    @EnvironmentObject var userData: UserData,
    var letter: Letters.Letter,
    var letterPronunciation: String? = null): View {
    val body: Opaque<View>
        get() = HStack {
            Text(letter.script).font(.largeTitle).padding(.trailing)
            if ((letterPronunciation != null)) {
                Text(letterPronunciation!!).padding()
            }
            Text(letter.name[TransliterationMode.Common.rawValue] ?: letter.name[userData.transliterationMode.rawValue]!!)
            Spacer()
        }.padding(.horizontal)
}

data class LetterRow_Previews: PreviewProvider {
    companion object {
        val previews: Opaque<View>
            get() = Group {
                LetterRow(letter = lettersData[0].letters[0])
                LetterRow(letter = lettersData[1].letters[1], letterPronunciation = lettersData[1].pickers!![0].name[TransliterationMode.Common.rawValue])
            }.previewLayout(.fixed(width = 300, height = 70))
    }
}
