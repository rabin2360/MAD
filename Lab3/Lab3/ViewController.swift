//
//  ViewController.swift
//  DelegatesProtocols
//
//  Created by Rabin Ranabhat on 9/20/16.
//  Copyright © 2016 Rabin Ranabhat. All rights reserved.
//

import UIKit

class ViewController: UIViewController, UITextFieldDelegate {

    //outlets for the application
    @IBOutlet weak var checkAmount: UITextField!
    @IBOutlet weak var tipPercent: UITextField!
    @IBOutlet weak var people: UITextField!
    @IBOutlet weak var tipDue: UILabel!
    @IBOutlet weak var totalDue: UILabel!
    @IBOutlet weak var totalDuePerPerson: UILabel!
    
    //pressing the done button makes the key board go away
    func textFieldShouldReturn(textField: UITextField) -> Bool {
        textField.resignFirstResponder()
        return true
    }
    
    func textFieldDidEndEditing(textField: UITextField) {
        updateTipTotals()
    }
    
    override func viewDidLoad() {
        //setting the delegates for all the text buttons
        checkAmount.delegate = self
        tipPercent.delegate = self
        people.delegate = self
        
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }

    func updateTipTotals()
    {
    
        var amount: Float
        var pct: Float
        
        if checkAmount.text!.isEmpty
        {
            amount = 0.0
        }
        else
        {
            amount = Float(checkAmount.text!)!
        }
        
        if tipPercent.text!.isEmpty
        {
            pct = 0.0
        }
        else
        {
            pct = Float(tipPercent.text!)!/100
        }
        
        let numberOfPeople = Int(people.text!)
        let tip = amount*pct
        let total = amount + tip
        var personTotal : Float = 0.0
        
        if numberOfPeople != nil
        {
            //how many people are entered!
            if numberOfPeople > 0
            {
                personTotal = total / Float(numberOfPeople!)
            }
            else
            {
                //create UIAlertController object
                let alert = UIAlertController(title: "Warning", message: "Invalid # of people entered!", preferredStyle: UIAlertControllerStyle.Alert)
                
                let cancelAction = UIAlertAction(title: "Cancel", style: UIAlertActionStyle.Cancel, handler: nil)
                
                alert.addAction(cancelAction)
                
                let okAction = UIAlertAction(title: "OK", style: UIAlertActionStyle.Default, handler:
                    {
                        action in
                        self.people.text = "1"
                        self.updateTipTotals()
                })
                
                alert.addAction(okAction)
                presentViewController(alert, animated: true, completion: nil)
                
            }
        }
        
        //format results as currency
        let currentFormatter = NSNumberFormatter()
        currentFormatter.numberStyle = NSNumberFormatterStyle.CurrencyStyle
        tipDue.text = currentFormatter.stringFromNumber(tip)
        totalDue.text = currentFormatter.stringFromNumber(total)
        totalDuePerPerson.text = currentFormatter.stringFromNumber(personTotal)

}
    
    @IBAction func tapGesture(sender: AnyObject) {
        checkAmount.resignFirstResponder()
        tipPercent.resignFirstResponder()
        people.resignFirstResponder()
    }
}
