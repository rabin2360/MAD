//
//  ViewController.swift
//  Lab3
//
//  Created by Rabin Ranabhat on 9/23/16.
//  Copyright © 2016 Rabin Ranabhat. All rights reserved.
//

import UIKit
import QuartzCore

class ViewController: UIViewController, UIPickerViewDelegate, UIPickerViewDataSource {

    @IBOutlet weak var studentID: UITextField!
    @IBOutlet weak var studentFirstname: UITextField!
    @IBOutlet weak var studentLastname: UITextField!
    @IBOutlet weak var studentPageLabel: UILabel!
    @IBOutlet weak var studentDOB: UITextField!
    @IBOutlet weak var studentEthnicity: UITextField!
    @IBOutlet weak var studentConsentDate: UITextField!
    @IBOutlet weak var studentIEP: UITextField!
    @IBOutlet weak var studentIEPSwitch: UISwitch!
    @IBOutlet weak var student401Report: UITextField!
    @IBOutlet weak var student401Switch: UISwitch!
    
    //loading the ethnicity values
    var ethnicities = ["None","Caucasian", "African American"]

    
    
    @IBAction func clearFields(sender: UIButton) {
    
        studentID.text = ""
        studentFirstname.text = ""
        studentLastname.text = ""
        studentDOB.text = ""
        studentEthnicity.text = ""
        studentConsentDate.text = ""
        studentIEP.text = ""
        student401Report.text = ""
        
        //resetting the buttons as well
        studentIEPSwitch.setOn(false, animated: true)
        student401Switch.setOn(false, animated: true)
    }
    
    @IBAction func cancelApplication(sender: AnyObject) {
    
        //create UIAlertController object
        let alert = UIAlertController(title: "Warning", message: "Are you sure you want to exit? Unsaved info will be lost!", preferredStyle: UIAlertControllerStyle.Alert)
        
        let cancelAction = UIAlertAction(title: "Cancel", style: UIAlertActionStyle.Cancel, handler: nil)
        
        alert.addAction(cancelAction)
        
        let okAction = UIAlertAction(title: "OK", style: UIAlertActionStyle.Default, handler:
            {
                action in
                //exit the application if okay pressed
                UIControl().sendAction(#selector(NSURLSessionTask.suspend), to: UIApplication.sharedApplication(), forEvent: nil)

        })
        
        alert.addAction(okAction)
        presentViewController(alert, animated: true, completion: nil)
    }
    
    func validateFields() -> Bool
    {
        var validStatus = true

        if studentID.text == ""
        {
            studentID.layer.borderWidth = 1
            studentID.layer.borderColor = UIColor.redColor().CGColor
            validStatus = false
        }
        else
        {
            studentID.layer.borderWidth = 0
            studentID.layer.borderColor = UIColor.whiteColor().CGColor
            
        }
        
        if studentFirstname.text == ""
        {
            studentFirstname.layer.borderWidth = 1
            studentFirstname.layer.borderColor = UIColor.redColor().CGColor
            validStatus = false
        }
        else
        {
            studentFirstname.layer.borderWidth = 0
            studentFirstname.layer.borderColor = UIColor.whiteColor().CGColor
            
        }
        
        
        if studentLastname.text == ""
        {
            studentLastname.layer.borderWidth = 1
            studentLastname.layer.borderColor = UIColor.redColor().CGColor
            validStatus = false
        }
        else
        {
            studentLastname.layer.borderWidth = 0
            studentLastname.layer.borderColor = UIColor.whiteColor().CGColor
            
        }
        
        
        if studentDOB.text == ""
        {
            studentDOB.layer.borderWidth = 1
            studentDOB.layer.borderColor = UIColor.redColor().CGColor
            validStatus = false
        }
        else
        {
            studentDOB.layer.borderWidth = 0
            studentDOB.layer.borderColor = UIColor.whiteColor().CGColor
        }
        
        
        if studentConsentDate.text == ""
        {
            studentConsentDate.layer.borderWidth = 1
            studentConsentDate.layer.borderColor = UIColor.redColor().CGColor
            validStatus = false
        }
        else
        {
            studentConsentDate.layer.borderWidth = 0
            studentConsentDate.layer.borderColor = UIColor.whiteColor().CGColor
            
        }
        
        
        if(studentIEPSwitch.on)
        {
            if studentIEP.text == ""
            {
                studentIEP.layer.borderWidth = 1
                studentIEP.layer.borderColor = UIColor.redColor().CGColor
                validStatus = false
                
            }
            else
            {
                studentIEP.layer.borderWidth = 0
                studentIEP.layer.borderColor = UIColor.whiteColor().CGColor
            }
        }
        
        if(student401Switch.on)
        {
            if student401Report.text == ""
            {
                student401Report.layer.borderWidth = 1
                student401Report.layer.borderColor = UIColor.redColor().CGColor
                validStatus = false
                
            }
            else
            {
                student401Report.layer.borderWidth = 0
                student401Report.layer.borderColor = UIColor.whiteColor().CGColor
            }
        }
        
        return validStatus
    
    }
    
    @IBAction func okPressed(sender: UIButton) {
        
        if validateFields()
        {
            print("Valid Form!")
        }
        else
        {
            print("Invalid form!")
        }
    }
    
    func datePickerValueChanged(sender: UIDatePicker)
    {
        let dateFormatter = NSDateFormatter()
        dateFormatter.dateStyle = NSDateFormatterStyle.MediumStyle
        dateFormatter.timeStyle = NSDateFormatterStyle.NoStyle
        studentDOB.text = dateFormatter.stringFromDate(sender.date)
    }
    
    @IBAction func enterDOB(sender: UITextField) {
        let datePickerView:UIDatePicker = UIDatePicker()
        
        datePickerView.datePickerMode = UIDatePickerMode.Date
        sender.inputView = datePickerView
        datePickerView.addTarget(self, action: #selector(ViewController.datePickerValueChanged), forControlEvents: UIControlEvents.ValueChanged)
        
    }
    
    func consentDateValueChanged(sender: UIDatePicker)
    {
        let dateFormatter = NSDateFormatter()
        dateFormatter.dateStyle = NSDateFormatterStyle.MediumStyle
        dateFormatter.timeStyle = NSDateFormatterStyle.NoStyle
        studentConsentDate.text = dateFormatter.stringFromDate(sender.date)
    }
    
    @IBAction func enterConsent(sender: UITextField) {
        let datePickerView:UIDatePicker = UIDatePicker()
        
        datePickerView.datePickerMode = UIDatePickerMode.Date
        sender.inputView = datePickerView
        datePickerView.addTarget(self, action: #selector(ViewController.consentDateValueChanged), forControlEvents: UIControlEvents.ValueChanged)
        
    }

    @IBAction func IEPSwitchClicked(sender: UISwitch) {
    
        if studentIEPSwitch.on
        {
            studentIEP.enabled = true
        }
        else
        {
            studentIEP.enabled = false
            studentIEP.text = ""
            studentIEP.layer.borderWidth = 0
            studentIEP.layer.borderColor = UIColor.whiteColor().CGColor
        }
    }

    @IBAction func SwitchClicked(sender: UISwitch) {
        
        if student401Switch.on
        {
            student401Report.enabled = true
        }
        else
        {
            student401Report.enabled = false
            student401Report.text = ""
            student401Report.layer.borderWidth = 0
            student401Report.layer.borderColor = UIColor.whiteColor().CGColor
        }
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
        
        //setting the title of the page to bold
        studentPageLabel.font = UIFont.boldSystemFontOfSize(17)
        
        //reading the switches to determine the enabled states of the text fields
        if studentIEPSwitch.on
        {
            studentIEP.enabled = true
        }
        else
        {
            studentIEP.enabled = false
        }
        
        if student401Switch.on
        {
            student401Report.enabled = true
        }
        else
        {
            student401Report.enabled = false
        }
        
        let ethnicityPicker = UIPickerView()
        ethnicityPicker.delegate = self
        ethnicityPicker.dataSource = self
        studentEthnicity.inputView = ethnicityPicker
        
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }

    func numberOfComponentsInPickerView(pickerView: UIPickerView) -> Int {
        return 1
    }
    
    func pickerView(pickerView: UIPickerView, numberOfRowsInComponent component: Int) -> Int {
        return ethnicities.count
    }
    
    func pickerView(pickerView: UIPickerView, didSelectRow row: Int, inComponent component: Int) {
        
        studentEthnicity.text = ethnicities[row]
    }
    
    func pickerView(pickerView: UIPickerView, titleForRow row: Int, forComponent component: Int) -> String? {
        return 	ethnicities[row]
    }

    //when tapped anywhere outside the textboxes or the pickers
    //screen is dismissed
    @IBAction func ScreenTapped(sender: AnyObject) {
        
        if studentID != nil
        {
            studentID.resignFirstResponder()
        }
        
        if studentFirstname != nil
        {
            studentFirstname.resignFirstResponder()
        }
        
        if studentLastname != nil
        {
            studentLastname.resignFirstResponder()
        }
        
        studentDOB.resignFirstResponder()
        studentEthnicity.resignFirstResponder()
        studentConsentDate.resignFirstResponder()
        student401Report.resignFirstResponder()
        studentIEP.resignFirstResponder()
    }
    
    //this will listen to all the text fields and then resign the control to keyboard when 'DONE' is tapped
    @IBAction func textFieldDoneEditing(sender: UITextField)
    {
        sender.resignFirstResponder()
    }
}

