//
//  ViewController.swift
//  UIControls
//
//  Created by Rabin Ranabhat on 9/8/16.
//  Copyright © 2016 Rabin Ranabhat. All rights reserved.
//

import UIKit

class ViewController: UIViewController {

    @IBOutlet weak var pictureDisplay: UIImageView!
    @IBOutlet weak var imageController: UISegmentedControl!
    @IBOutlet weak var pictureLabel: UILabel!
    @IBOutlet weak var capitalizeController: UISwitch!
    @IBOutlet weak var fontSize: UILabel!
    @IBOutlet weak var sliderController: UISlider!
    
    @IBAction func updateFontSize(sender: UISlider) {
        let sliderValue = sender.value
        let cgSliderVal = CGFloat(sliderValue)
        pictureLabel.font=UIFont.systemFontOfSize(cgSliderVal)
    }
    
    @IBAction func capitalize(sender: UISwitch) {
        
        if(capitalizeController.on)
        {
            pictureLabel.text = pictureLabel.text?.uppercaseString
        }
        else
        {
            pictureLabel.text = pictureLabel.text?.lowercaseString
        }
    
    }
    
    @IBAction func changePicture(sender: UISegmentedControl) {
        
        if(imageController.selectedSegmentIndex == 0)
        {
            pictureDisplay.image = UIImage(named: "BB_Family")
            pictureLabel.text = "Family"
        }
        else
        {
            pictureDisplay.image = UIImage(named: "BB_Kids")
            pictureLabel.text = "Kids"
        }
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
        fontSize.text = String(format: "%.0", sliderController.value)
    }

    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }


}

