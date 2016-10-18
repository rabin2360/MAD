//
//  ViewController.swift
//  Midterm
//
//  Created by Rabin Ranabhat on 10/13/16.
//  Copyright © 2016 Rabin Ranabhat. All rights reserved.
//

import UIKit

class ViewController: UIViewController, UITextFieldDelegate {

    @IBOutlet weak var workoutTime: UITextField!
    @IBOutlet weak var milesRanLabel: UILabel!
    @IBOutlet weak var caloriesBurntLabel: UILabel!
    @IBOutlet weak var segmentControForActivities: UISegmentedControl!
    @IBOutlet weak var timesWorkedOut: UILabel!
    @IBOutlet weak var sliderWorkerOutTimes: UISlider!
    @IBOutlet weak var pictureDisplay: UIImageView!
    
    //vars 
    var enteredWorkOutTime = 0.00
    
    //constants for running
    var pacePerMile  = 10.00
    var caloriesBurntPerMin = 10.00
    
    //constants for biking
    var bikesSpeedPerMile = 4.00
    var bikeCaloriesBurntPerMin = 8.50
    
    //constants for swimming
    var swimmingPerMile = 30.0
    var swimmingCaloriesBurntPerMin = 7.00
    
    //pressing the done button makes the key board go away
    func textFieldShouldReturn(textField: UITextField) -> Bool {
        textField.resignFirstResponder()
        return true
    }
    
    @IBAction func changingTimesWorkedOut(sender: UISlider) {
    
        timesWorkedOut.text = String(Int(sliderWorkerOutTimes.value))
        
        getWorkOutTimeVal()
        calculateValues()
    }
    
    func calculateValues()
    {
        let tempWorkedOutTimes = Double(sliderWorkerOutTimes.value)
        
        //determining the kind of activity selected
        if(segmentControForActivities.selectedSegmentIndex == 0)
        {
            //run variables calculated
            let totalMilesPerWeek = enteredWorkOutTime * tempWorkedOutTimes
            var tempTotal = (totalMilesPerWeek / pacePerMile)
            
            tempTotal = round (tempTotal * 100)/100
            var tempString = String(tempTotal)
            
            milesRanLabel.text = tempString + " miles"
            
            var tempCalroies  = (enteredWorkOutTime * caloriesBurntPerMin * tempWorkedOutTimes)
            tempCalroies = round(tempCalroies * 100)/100
            
            tempString = String(tempCalroies)
            caloriesBurntLabel.text = tempString + " calories"
        }
        else if(segmentControForActivities.selectedSegmentIndex == 1)
        {
            
            let totalBikedMilesPerWeek = enteredWorkOutTime * tempWorkedOutTimes
            var temptotal = totalBikedMilesPerWeek / bikesSpeedPerMile
            temptotal = round(temptotal * 100)/100
            
            var tempString = String(temptotal)
            milesRanLabel.text = tempString + " miles"
            
            var tempCalories = enteredWorkOutTime * bikeCaloriesBurntPerMin * tempWorkedOutTimes
            tempCalories = round(tempCalories * 100)/100
            
            tempString = String(tempCalories)
            caloriesBurntLabel.text = tempString + " calories"
        }
        else
        {
            let totalSwimPerWeek = enteredWorkOutTime * tempWorkedOutTimes
            
            var tempTotal = (totalSwimPerWeek / swimmingPerMile)
            tempTotal = round(tempTotal * 100)/100
            
            var tempString = String(tempTotal)
            milesRanLabel.text = tempString + " miles"
            
            var tempCalories = enteredWorkOutTime * swimmingCaloriesBurntPerMin * tempWorkedOutTimes
            tempCalories = round(tempCalories * 100)/100
            
            tempString = String(tempCalories)
            caloriesBurntLabel.text = tempString + " calories"
        }

    }
    
    func showAlert()
    {
    
        //create UIAlertController object
        let alert = UIAlertController(title: "Warning", message: "Work out time entered is less than 30!", preferredStyle: UIAlertControllerStyle.Alert)
        
        let okAction = UIAlertAction(title: "OK", style: UIAlertActionStyle.Default, handler: nil)
        
        alert.addAction(okAction)
        presentViewController(alert, animated: true, completion: nil)
    }
    
    func getWorkOutTimeVal()
    {
        
        //getting the workout time
        if workoutTime.text ==  ""
        {
            enteredWorkOutTime = 0
        }
        else
        {
            enteredWorkOutTime = Double(workoutTime.text!)!
        }
        
        if(enteredWorkOutTime < 30)
        {
            showAlert()
        }
    }
    
    func changeImage(segmentIndex: Int)
    {
        if(segmentIndex == 0)
        {
            pictureDisplay.image = UIImage(named: "run.png")
        }
        else if(segmentIndex == 1)
        {
         pictureDisplay.image = UIImage(named: "bike.png")
        }
        else
        {
         pictureDisplay.image = UIImage(named: "swim.png")
        }
    }
    
    @IBAction func activityChanged(sender: UISegmentedControl) {
        getWorkOutTimeVal()
        calculateValues()
        changeImage(sender.selectedSegmentIndex)
    }
    
    @IBAction func workoutButtonPressed(sender: UIButton) {
        
     
        getWorkOutTimeVal()
        //calcute final values for the labels
        calculateValues()
        
    
    }
    
    
    override func viewDidLoad() {
        
        workoutTime.delegate = self
        
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }


}

