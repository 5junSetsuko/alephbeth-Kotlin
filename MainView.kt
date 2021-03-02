
//
//  MainView.swift
//  alephbeth
//
//  Created by 御堂 大嗣 on 2020/05/04.
//  Copyright © 2020 御堂 大嗣. All rights reserved.
//
data class MainView(
    @EnvironmentObject var userData: UserData,
    @State var showingSetting = false): View {
    val body: Opaque<View>
        get() = VStack {
            NavigationView { List {
                HStack {
                    Spacer()
                    Text("メニューを選んでね").fontWeight(.thin)
                }
                NavigationLink(destination = ContentView(letters = lettersData[0].letters, pickers = lettersData[0].letters, title = "ヘブライ文字クイズ")) { Text("ヘブライ文字クイズ") }
                NavigationLink(destination = ContentView(withUnderScores = false, letters = lettersData[1].letters, pickers = lettersData[1].pickers!!, title = "母音記号クイズ")) { Text("母音記号クイズ") }
                NavigationLink(destination = LetterList(letters = lettersData[0].letters, title = "ヘブライ文字を覚える")) { Text("ヘブライ文字を覚える") }
                NavigationLink(destination = LetterList(withUnderScores = false, letters = lettersData[1].letters, pickers = lettersData[1].pickers!!, title = "母音記号を覚える")) { Text("母音記号を覚える") }
            }.navigationBarTitle(Text("ヘブライ文字暗記")).navigationBarItems(trailing = Button(action = { this.showingSetting.toggle() }) { Image(systemName = "gear").imageScale(.large) }.sheet(isPresented = $showingSetting) { SettingView(showSheetView = //showSheetView: self.$showingSetting//userData: self.userData	//@@
            this.showingSetting).environmentObject(//userData: self.userData
            this.userData) }) }
            Banner()
        }
}

data class MainView_Previews: PreviewProvider {
    companion object {
        val previews: Opaque<View>
            get() = ForEach(listOf("iPhone SE"), id = //"iPhone XS Max"
            \.self) { deviceName  -> 
                MainView().environmentObject(UserData()).previewDevice(PreviewDevice(rawValue = deviceName)).previewDisplayName(deviceName)
            }
    }
}
