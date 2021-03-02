
//
//  ContentView.swift
//  alephbeth
//
//  Created by 御堂 大嗣 on 2020/05/01.
//  Copyright © 2020 御堂 大嗣. All rights reserved.
//
data class ContentView(
    var withUnderScores: Boolean? = null,
    val letters: List<Letters.Letter>,
    var questionedLetters: List<Letters.Letter>? = null,
    val pickers: List<Pickable>,
    val title: String,
    @ObservedObject var quizData = QuizData(),
    @EnvironmentObject var userData: UserData,
    @State var selection = 0,
    @State var submitted = false,
    @State var isCorrect = false,
    @State var answerLetter: Letters.Letter? = null,
    @State private var showingUnansweredAlert = false,
    @State private var showingUnfinishedAlert = false,
    @State private var goResultView = false): View {
    val body: Opaque<View>
        get() = VStack {
            Text(answerLetterScript()).font(.largeTitle).padding()
            Text("これの読み方は？").font(.subheadline)
            Picker(selection = $selection, label = Text("")) { ForEach(pickers.filter {
                val contains = it.name.keys.contains
                contains(TransliterationMode.Common.rawValue) || contains(userData.transliterationMode.rawValue)
            }, id = \.id) { picker  -> 
                Text(picker.name[this.userData.transliterationMode.rawValue] ?: picker.name[TransliterationMode.Common.rawValue]!!).tag(picker.id)
            } }.labelsHidden()
            HStack { //まだこの問題に対して回答していない時
            if ((!this.submitted)) {
                //回答ボタン有効
                Button(action = {
                    //正誤をチェック
                    val answerId: Int? = this.answerLetter.map { it.answerId ?: it.id }
                    this.isCorrect = answerId.map { it == this.pickers[this.selection].id } ?: false
                    //未出題の問題を一つ削って次の解答へ入れる
                    this.setNextAnswer()
                    //間違っていたら誤回答リストに加える
                    if ((!this.isCorrect)) {
                        this.addIncorrectAnswer()
                    }
                    //回答提出済み
                    this.submitted = true
                }) { Text("回答").padding() }
            } else //回答済みなのでボタン無効
            {
                Text("回答").foregroundColor(Color.gray).padding()
            } }
            Spacer()
            if ((this.unAnswered() > 0)) {
                Text("あと" + String(unAnswered()) + "問あるよ")
            } else {
                Text("もうないよ")
            }
            Spacer()
            if ((this.submitted)) {
                if ((this.isCorrect)) {
                    Text("正解")
                } else {
                    Text("不正解！正解は↓")
                    Spacer()
                    Text(this.answerLetterName()).bold()
                }
            }
            Spacer()
            HStack {
                NavigationLink(destination = ResultView(quizData = quizData, withUnderScores = this.withUnderScores, questionAmount = this.questionedLetters?.size, letters = letters, pickers = pickers, percent = this.completedRate()).environmentObject(userData), isActive = $goResultView) { Text("") }
                Button("結果を見る") { if ((this.quizData.unQuestionedLetters.size > 0)) {
                    this.showingUnfinishedAlert = true
                } else {
                    this.goResultView = true
                } }.alert(isPresented = $showingUnfinishedAlert) { Alert(title = Text("未終了"), message = Text("まだ全ての問題に回答していません。このクイズを終了して結果を見ますか？"), primaryButton = .destructive(Text("結果を見る")) { this.goResultView = true }, secondaryButton = .cancel()) }
                Spacer()
                Button(action = { this.resetQuetions() }) { Text("最初から") }
                Spacer()
                //まだ残りの問題がある時
                if ((this.unAnswered() > 1 && !this.submitted || this.unAnswered() > 0 && this.submitted)) {
                    //次へボタン有効
                    Button(action = { //未回答のまま次の問題に行ったらアラートを表示
                    if ((!this.submitted)) {
                        this.showingUnansweredAlert = true
                    } else {
                        this.submitted = false
                        this.isCorrect = false
                        this.answerLetter = this.quizData.nextAnswerLetter.popLast()
                    } }) { Text("次へ").padding() }.alert(isPresented = $showingUnansweredAlert) { Alert(title = Text("未回答"), message = Text("未回答です。この問題を不正解として次の問題に行きますか？"), primaryButton = .destructive(Text("次へ")) {
                        this.addIncorrectAnswer()
                        //未出題の問題を一つ削って次の解答へ入れる
                        this.setNextAnswer()
                        this.submitted = false
                        this.isCorrect = false
                        this.answerLetter = this.quizData.nextAnswerLetter.popLast()
                    }, secondaryButton = .cancel()) }
                } else //もう残りの問題はないので次へボタン無効
                {
                    Text("次へ").foregroundColor(Color.gray).padding()
                }
            }
        }.padding().onAppear(perform = resetQuetions).navigationBarTitle(Text(this.title), displayMode = .inline)
    
    fun answerLetterName() : String {
        val answerId = this.answerLetter.map { it.answerId ?: it.id }
        val correctPicker = pickers.filter { it.id == answerId }.firstOrNull()
        val name = correctPicker?.name
        return name?[this.userData.transliterationMode.rawValue] ?: name!![TransliterationMode.Common.rawValue]!!
    }
    
    fun answerLetterScript() : String =
        this.answerLetter.map { it.script } ?: "答えがないよ"
    
    fun resetQuetions() {
        this.quizData.incorrectlyAnsweredLetters = listOf()
        this.quizData.nextAnswerLetter = listOf()
        this.quizData.unQuestionedLetters = (questionedLetters ?: letters).filter {
            val contains = it.name.keys.contains
            contains(TransliterationMode.Common.rawValue) || contains(userData.transliterationMode.rawValue)
        }
        this.quizData.unQuestionedLetters.shuffle()
        val pop = this.quizData.unQuestionedLetters.popLast()
        if (pop != null) {
            this.quizData.nextAnswerLetter.append(pop)
        }
        this.answerLetter = this.quizData.nextAnswerLetter.popLast()
        this.submitted = false
    }
    
    fun addIncorrectAnswer() {
        val answer = this.answerLetter
        if (answer != null) {
            this.quizData.incorrectlyAnsweredLetters.append(answer)
        }
    }
    
    fun setNextAnswer() {
        val pop = this.quizData.unQuestionedLetters.popLast()
        if (pop != null) {
            this.quizData.nextAnswerLetter.append(pop)
        }
    }
    
    fun unAnswered() : Int =
        this.quizData.unQuestionedLetters.size + this.quizData.nextAnswerLetter.size + (if (this.submitted) 0 else 1)
    
    fun completedRate() : Double {
        val allLetters = questionedLetters ?: letters
        val completed = allLetters.size - this.quizData.incorrectlyAnsweredLetters.size - this.unAnswered()
        val rate = Double(completed) / Double(allLetters.size)
        val percent = rate * 100.0
        return percent
    }
}

data class ContentView_Previews: PreviewProvider {
    companion object {
        val previews: Opaque<View>
            get() = ContentView(letters = /*letters: lettersData[0].letters,
            pickers: lettersData[0].letters,*/
            lettersData[1].letters, questionedLetters = lettersData[1].letters.filter { it.id < 7 }, pickers = lettersData[1].pickers!!, title = "何かタイトル")
    }
}
