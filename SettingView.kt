
//
//  SettingView.swift
//  alephbeth
//
//  Created by 御堂 大嗣 on 2020/06/03.
//  Copyright © 2020 御堂 大嗣. All rights reserved.
//
data class SettingView(
    @Binding var showSheetView: Boolean,
    @EnvironmentObject var userData: UserData): View {
    //@ObservedObject var userData: UserData
    //@State var selecetion = TransliterationMode.HebrewAcademy2006
    val body: Opaque<View>
        get() = NavigationView { Form { Picker(selection = //selection: $selecetion,
        $userData.transliterationMode, label = Text("ラテン文字転写の方法")) { ForEach(TransliterationMode.allCases.filter { when (it) {
            TransliterationMode.Common -> return@filter false
            else -> return@filter true
        } }, id = \.self) { Text(it.rawValue).tag(it) } } }.navigationBarTitle(Text("設定"), displayMode = .inline).navigationBarItems(trailing = Button(action = {
            print("Dismissing sheet view...")
            this.showSheetView = false
        }) { Text("完了") }) }
}

data class SettingView_Previews: PreviewProvider {
    companion object {
        val previews: Opaque<View>
            get() = SettingView(showSheetView = .constant(true)).environmentObject(//, userData: UserData())
            UserData())
    }
}
