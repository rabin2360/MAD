//
//  ViewController.swift
//  FavoritesApp
//
//  Created by Rabin Ranabhat on 9/27/16.
//  Copyright © 2016 Rabin Ranabhat. All rights reserved.
//

import UIKit

class ViewController: UIViewController {

    @IBOutlet weak var favoriteAuthor: UILabel!
    @IBOutlet weak var favoriteBook: UILabel!
    @IBOutlet weak var bookYear: UILabel!
    
    var user = Favorite()
    
    @IBAction func unwindSegue(segue:UIStoryboardSegue)
    {
        favoriteAuthor.text = user.favAuthor
        favoriteBook.text = user.favBook
        bookYear.text = user.year
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

