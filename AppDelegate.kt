
//
@UIApplicationMain//  AppDelegate.swift
//  alephbeth
//
//  Created by 御堂 大嗣 on 2020/05/01.
//  Copyright © 2020 御堂 大嗣. All rights reserved.
//
 class AppDelegate: UIResponder, UIApplicationDelegate {
    
    fun application(application: UIApplication, launchOptions: Map<UIApplication.LaunchOptionsKey, Any>?) : Boolean {
        // Override point for customization after application launch.
        //GoogleAdMobのMObile Ads SDKをinitializeする
        GADMobileAds.sharedInstance().start(completionHandler = null)
        return true
    }
    
    // MARK: UISceneSession Lifecycle
    fun application(application: UIApplication, connectingSceneSession: UISceneSession, options: UIScene.ConnectionOptions) : UISceneConfiguration =
        // Called when a new scene session is being created.
        // Use this method to select a configuration to create the new scene with.
        UISceneConfiguration(name = "Default Configuration", sessionRole = connectingSceneSession.role)
    
    fun application(application: UIApplication, sceneSessions: Set<UISceneSession>) {}
}
// Called when the user discards a scene session.
// If any sessions were discarded while the application was not running, this will be called shortly after application:didFinishLaunchingWithOptions.
// Use this method to release any resources that were specific to the discarded scenes, as they will not return.
