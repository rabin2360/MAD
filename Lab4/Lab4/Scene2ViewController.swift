//
//  Scene2ViewController.swift
//  FavoritesApp
//
//  Created by Rabin Ranabhat on 9/27/16.
//  Copyright © 2016 Rabin Ranabhat. All rights reserved.
//

import UIKit

class Scene2ViewController: UIViewController, UITextFieldDelegate {

    @IBOutlet weak var favoriteAuthor: UITextField!
    @IBOutlet weak var favoriteBook: UITextField!
    @IBOutlet weak var bookYear: UITextField!
    
    let numberToolbar: UIToolbar = UIToolbar()
    
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        if segue.identifier == "doneFavs"
        {
            let scene1ViewController = segue.destinationViewController as! ViewController
            
            if favoriteAuthor.text!.isEmpty == false
            {
                scene1ViewController.user.favAuthor = favoriteAuthor.text
            }
            
            if favoriteBook.text!.isEmpty == false
            {
                scene1ViewController.user.favBook = favoriteBook.text
            }
            
            if bookYear.text!.isEmpty == false
            {
                scene1ViewController.user.year = bookYear.text
                
            }
        
        }
    }
    
    func numpadCancel()
    {
        bookYear.text = ""
        
        bookYear.resignFirstResponder()
    }
    
    func numpadApply()
    {
        bookYear.resignFirstResponder()
    }
    
    override func viewDidLoad() {
        favoriteBook.delegate = self
        favoriteAuthor.delegate = self
        bookYear.delegate = self
        
        super.viewDidLoad()
        
        
        numberToolbar.barStyle = UIBarStyle.BlackTranslucent
        
        // Do any additional setup after loading the view.
        numberToolbar.items=[
            UIBarButtonItem(title: "Cancel", style: UIBarButtonItemStyle.Plain, target: self, action: #selector(Scene2ViewController.numpadCancel)),
            UIBarButtonItem(barButtonSystemItem: UIBarButtonSystemItem.FlexibleSpace, target: self, action: nil),
            UIBarButtonItem(title: "Apply", style: UIBarButtonItemStyle.Plain, target: self, action: #selector(Scene2ViewController.numpadApply))
        ]
        
        numberToolbar.sizeToFit()
        
        bookYear.inputAccessoryView = numberToolbar //do it for every relevant textfield if there are more than one
    }
    
    func textFieldShouldReturn(textField: UITextField) -> Bool {
        textField.resignFirstResponder()
        return true
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */

}
