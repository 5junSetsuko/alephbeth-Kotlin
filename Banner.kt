
//
private//  Banner.swift
//  alephbeth
//
//  Created by 御堂 大嗣 on 2020/05/08.
//  Copyright © 2020 御堂 大嗣. All rights reserved.
//
 final class BannerVC: UIViewControllerRepresentable {
    
    fun makeUIViewController(context: Context) : UIViewController {
        val view = GADBannerView(adSize = kGADAdSizeBanner)
        //test用bannerID
        //let bannerID = "ca-app-pub-3940256099942544/2934735716"
        //本番用bannerID: first_banner
        val bannerID = "ca-app-pub-3865271057827792/9425055269"
        val viewController = UIViewController()
        view.adUnitID = bannerID
        view.rootViewController = viewController
        viewController.view.addSubview(view)
        viewController.view.frame = CGRect(origin = .zero, size = kGADAdSizeBanner.size)
        view.load(GADRequest())
        return viewController
    }
    
    fun updateUIViewController(uiViewController: UIViewController, context: Context) {}
}

data class Banner: View {
    val body: Opaque<View>
        get() = HStack {
            Spacer()
            BannerVC().frame(width = 320, height = 50, alignment = .center)
            Spacer()
        }
}

data class Banner_Previews: PreviewProvider {
    companion object {
        val previews: Opaque<View>
            get() = Banner()
    }
}
