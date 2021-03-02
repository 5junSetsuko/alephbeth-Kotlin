
//
//  ResultView.swift
//  alephbeth
//
//  Created by 御堂 大嗣 on 2020/05/04.
//  Copyright © 2020 御堂 大嗣. All rights reserved.
//
data class ResultView(
    @EnvironmentObject var userData: UserData,
    @ObservedObject var quizData: QuizData,
    var withUnderScores: Boolean? = null,
    var questionAmount: Int? = null,
    val letters: List<Letters.Letter>,
    var pickers: List<Pickable>? = null,
    val percent: Double): View {
    val body: Opaque<View>
        get() = VStack {
            Text("達成率" + String(Int(percent)) + "%！").padding()
            Text("全問題数: " + String(this.questionAmount ?: this.letters.size))
            //Text("unqueestioned: " + String(self.userData.unQuestionedNums.count))
            //Text("next:" + String(self.userData.nextAnswerNum.count))
            Text("誤答数: " + String(quizData.incorrectlyAnsweredLetters.size))
            if ((quizData.incorrectlyAnsweredLetters.size > 0)) {
                NavigationLink(destination = ContentView(letters = letters, questionedLetters = quizData.incorrectlyAnsweredLetters, pickers = pickers ?: letters, title = "間違えた文字だけをテスト").environmentObject(userData)) { Text("間違えた文字だけをテストする").fontWeight(.heavy).foregroundColor(Color.blue).padding() }
            } else {
                Text("間違えた文字だけをテストする").fontWeight(.heavy).foregroundColor(Color.gray).padding()
            }
            Spacer()
            LetterList(withUnderScores = this.withUnderScores, letters = letters, listedUpLetters = quizData.incorrectlyAnsweredLetters, pickers = pickers ?: letters, title = "間違えた文字を復習しよう")
        }.navigationBarTitle("間違えた文字たち", displayMode = .inline)
}

data class ResultView_Previews: PreviewProvider {
    companion object {
        val previews: Opaque<View>
            get() {
                val quizData = QuizData()
                quizData.incorrectlyAnsweredLetters = lettersData[0].letters
                //[
                //letterData[0],
                //letterData[1],
                //letterData[2]
                //]
                quizData.unQuestionedLetters = listOf()
                return ResultView(quizData = quizData, letters = lettersData[0].letters, percent = 0)
            }
    }
}
//.environmentObject(userData)
