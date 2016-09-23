//
//  ViewController.swift
//  Lab3
//
//  Created by Rabin Ranabhat on 9/23/16.
//  Copyright © 2016 Rabin Ranabhat. All rights reserved.
//

import UIKit

class ViewController: UIViewController, UIPickerViewDelegate, UIPickerViewDataSource {

    @IBOutlet weak var studentID: UITextField!
    @IBOutlet weak var studentFirstname: UITextField!
    @IBOutlet weak var studentLastname: UITextField!
    @IBOutlet weak var studentPageLabel: UILabel!
    @IBOutlet weak var studentDOB: UITextField!
    @IBOutlet weak var studentEthnicity: UITextField!
    
    //loading the ethnicity values
    var ethnicities = ["None","Caucasian", "African American"]
    
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

    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
        
        //setting the title of the page to bold
        studentPageLabel.font = UIFont.boldSystemFontOfSize(17)
        
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
        
        if(studentFirstname != nil)
        {
            studentFirstname.resignFirstResponder()
        }
        
        if(studentLastname != nil)
        {
            studentLastname.resignFirstResponder()
        }
        
        studentDOB.resignFirstResponder()
        studentEthnicity.resignFirstResponder()
    }
    
    @IBAction func textFieldDoneEditing(sender: UITextField)
    {
        sender.resignFirstResponder()
    }
}

