//
//  ViewController.swift
//  firstApplication
//
//  Created by Rabin Ranabhat on 8/26/16.
//  Copyright © 2016 Rabin Ranabhat. All rights reserved.
//

import UIKit

class ViewController: UIViewController {

    @IBOutlet weak var displayLabel: UILabel!
    @IBOutlet weak var playButton: UIButton!
    @IBOutlet weak var stopButton: UIButton!
    
    var pauseButtonImage = UIImage(named: "pauseButton")
    var playButtonImage = UIImage(named: "playButton")
    var pause = true

    //var pauseButtonImageView = UIImageView(image:pauseButtonImage)
    
    @IBAction func stopButtonPressed(sender: UIButton) {
        
        displayLabel.text = "Stop button pressed"
        playButton.setImage(playButtonImage, forState: .Normal)
        pause = true
        stopButton.enabled = false
    }
    
    @IBAction func okayButtonPressed(sender: UIButton) {
        if(pause)
        {
            displayLabel.text = "Press the pause button"
            playButton.setImage(pauseButtonImage, forState: .Normal)
            pause = false
            stopButton.enabled = true
        }
        else
        {
            displayLabel.text = "Press the play button"
            playButton.setImage(playButtonImage, forState: .Normal)
            pause = true
            stopButton.enabled = false
        }
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.stopButton.enabled = false
        
        // Do any additional setup after loading the view, typically from a nib.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }


}

