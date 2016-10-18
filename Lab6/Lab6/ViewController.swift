//
//  ViewController.swift
//  Gestures
//
//  Created by Rabin Ranabhat on 10/6/16.
//  Copyright © 2016 Rabin Ranabhat. All rights reserved.
//

import UIKit
import AVFoundation

class ViewController: UIViewController, UIGestureRecognizerDelegate {
    
    
    var audioPlayer : AVAudioPlayer?
    
    
    @IBAction func handleLongPress(sender: UILongPressGestureRecognizer) {
        let audioFilePath = NSBundle.mainBundle().pathForResource("tina", ofType: "mp3")
        let fileURL = NSURL(fileURLWithPath: audioFilePath!)
        audioPlayer = try? AVAudioPlayer(contentsOfURL: fileURL)
        
        if audioPlayer != nil
        {
            //print("Playing")
            audioPlayer!.play()
        }
    }
    
    @IBAction func handlePinch(sender: UIPinchGestureRecognizer) {
    
        sender.view!.transform = CGAffineTransformScale(sender.view!.transform, sender.scale, sender.scale)
        sender.scale = 1
    }
    
    @IBAction func handleRotate(sender: UIRotationGestureRecognizer) {
        
        sender.view!.transform = CGAffineTransformRotate(sender.view!.transform, sender.rotation)
        sender.rotation = 0
        
    }
    
    @IBAction func handlePan(sender: UIPanGestureRecognizer) {
        
        //NOTE: sender is the image in this case, since handlePan is associated with the image in the storyboard
        let translation = sender.translationInView(view)
        //new center is:
        //new x = old x + translated in x-axis
        //new y = old y + translated in y-axis
        sender.view!.center = CGPoint(x: sender.view!.center.x+translation.x, y: sender.view!.center.y + translation.y)
        
        //reset the translation matrix?!!!
        sender.setTranslation(CGPointZero, inView: view)
        
    }
    
    //delegate method
    func gestureRecognizer(gestureRecognizer: UIGestureRecognizer, shouldRecognizeSimultaneouslyWithGestureRecognizer otherGestureRecognizer: UIGestureRecognizer) -> Bool {
        return true
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }


}

