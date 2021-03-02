
//
//  SceneDelegate.swift
//  alephbeth
//
//  Created by 御堂 大嗣 on 2020/05/01.
//  Copyright © 2020 御堂 大嗣. All rights reserved.
//
class SceneDelegate: UIResponder, UIWindowSceneDelegate {
    var window: UIWindow? = null
    
    fun scene(scene: UIScene, session: UISceneSession, connectionOptions: UIScene.ConnectionOptions) {
        // Use this method to optionally configure and attach the UIWindow `window` to the provided UIWindowScene `scene`.
        val // If using a storyboard, the `window` property will automatically be initialized and attached to the scene.
        // This delegate does not imply the connecting scene or session are new (see `application:configurationForConnectingSceneSession` instead).
        // Create the SwiftUI view that provides the window contents.
        //let contentView = ContentView()
        // Use a UIHostingController as window root view controller.
        windowScene = scene as? UIWindowScene
        if (windowScene != null) {
            val window = UIWindow(windowScene = windowScene)
            window.rootViewController = UIHostingController(rootView = MainView().environmentObject(UserData()))
            this.window = window
            window.makeKeyAndVisible()
        }
    }
    
    fun sceneDidDisconnect(scene: UIScene) {}
    
    // Called as the scene is being released by the system.
    // This occurs shortly after the scene enters the background, or when its session is discarded.
    // Release any resources associated with this scene that can be re-created the next time the scene connects.
    // The scene may re-connect later, as its session was not neccessarily discarded (see `application:didDiscardSceneSessions` instead).
    fun sceneDidBecomeActive(scene: UIScene) {}
    
    // Called when the scene has moved from an inactive state to an active state.
    // Use this method to restart any tasks that were paused (or not yet started) when the scene was inactive.
    fun sceneWillResignActive(scene: UIScene) {}
    
    // Called when the scene will move from an active state to an inactive state.
    // This may occur due to temporary interruptions (ex. an incoming phone call).
    fun sceneWillEnterForeground(scene: UIScene) {}
    
    // Called as the scene transitions from the background to the foreground.
    // Use this method to undo the changes made on entering the background.
    fun sceneDidEnterBackground(scene: UIScene) {}
}
// Called as the scene transitions from the foreground to the background.
// Use this method to save data, release shared resources, and store enough scene-specific state information
// to restore the scene back to its current state.
