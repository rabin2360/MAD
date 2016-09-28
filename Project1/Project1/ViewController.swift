//
//  ViewController.swift
//  Project1
//
//  Created by Rabin Ranabhat on 9/27/16.
//  Copyright © 2016 Rabin Ranabhat. All rights reserved.
//

import UIKit

class ViewController: UIViewController {

    
    @IBOutlet weak var topLeft: UIButton!
    @IBOutlet weak var topCenter: UIButton!
    @IBOutlet weak var topRight: UIButton!
    
    @IBOutlet weak var centerLeft: UIButton!
    @IBOutlet weak var center: UIButton!
    @IBOutlet weak var centerRight: UIButton!
    
    @IBOutlet weak var bottomLeft: UIButton!
    @IBOutlet weak var bottomCenter: UIButton!
    @IBOutlet weak var bottomRight: UIButton!
    
    @IBOutlet weak var playerOneSpriteDisplay: UIImageView!
    @IBOutlet weak var playerTwoSpriteDisplay: UIImageView!
    @IBOutlet weak var playerOneLabel: UILabel!
    @IBOutlet weak var playerTwoLabel: UILabel!
    
    let xButton = UIImage(named: "x_button") as UIImage?
    let oButton = UIImage(named: "o_button") as UIImage?
    
    @IBOutlet weak var timerDisplay: UILabel!
    var timeInSeconds = 0
    
    //2-D matrix --> tic-tac-toe board
    var ticTacToeBoard : [[Bool]] = []
    var playerOne: player?
    var playerTwo: player?
    var currentPlayer: player?
    var timer = NSTimer()
    var elapsedTime = 0
    
    struct player
    {
        var mySprite: UIImage
        var myTurn = false
        var name = "none"
    }
    
    func randomNumGen() -> Int
    {
        return Int(arc4random_uniform(2))
    }
    
    @IBAction func buttonPressed(sender: UIButton) {
    
        
        let firstIndex = sender.tag / 10
        let secondIndex = sender.tag % 10
        
        //print(firstIndex)
        //print(secondIndex)
        
        if(ticTacToeBoard[firstIndex][secondIndex] == false)
        {
            //ticTacToeBoard[firstIndex][secondIndex] = true
            sender.setBackgroundImage(currentPlayer?.mySprite, forState: .Normal)
            //sender.enabled = false
        }
        
        //changeTurns
        changeTurns()
    }
    
    func changeTurns()
    {
        //changing the turn
        if(playerOne!.myTurn)
        {
            playerOne!.myTurn = false
            //playerTwo!.myTurn = true
            
            playerTwoLabel.backgroundColor = UIColor.grayColor()
            playerOneLabel.backgroundColor = UIColor.whiteColor()
            currentPlayer = playerTwo
            //print("Player two turn")
        }
        else
        {
            //playerTwo!.myTurn = false
            playerOne!.myTurn = true
            
            playerOneLabel.backgroundColor = UIColor.grayColor()
            playerTwoLabel.backgroundColor = UIColor.whiteColor()
            
            currentPlayer = playerOne
            //print("Player one turn")
        }
        
        //reset timer
        timeInSeconds = 16

    }
    
    @IBAction func resetGame(sender: AnyObject) {
        initGame()
        resetBoardDisplay()
        
        timeInSeconds = 0
        timer.invalidate()
        
        initializeTimer()
        updateTimerLabel()
    }
    
    func resetBoardDisplay()
    {
        topLeft.setBackgroundImage(nil, forState: .Normal)
        topCenter.setBackgroundImage(nil, forState: .Normal)
        topRight.setBackgroundImage(nil, forState: .Normal)
        
        centerLeft.setBackgroundImage(nil, forState: .Normal)
        center.setBackgroundImage(nil, forState: .Normal)
        centerRight.setBackgroundImage(nil, forState: .Normal)
        
        
        bottomLeft.setBackgroundImage(nil, forState: .Normal)
        bottomCenter.setBackgroundImage(nil, forState: .Normal)
        bottomRight.setBackgroundImage(nil, forState: .Normal)
        
    }

    func initGame()
    {
        //initialize the board
        ticTacToeBoard = [[false,false,false], [false, false, false], [false, false, false]]
        
        /*for columns in 0 ..< ticTacToeBoard.count
        {
            for rows in 0 ..< ticTacToeBoard[columns].count
            {
                print(ticTacToeBoard[columns][rows],terminator:", ")
            }
            
            print()
        }
        */
        
        if(randomNumGen() == 1)
        {
            playerOne = player(mySprite: UIImage(named:"o_button")!, myTurn: true, name: "playerOne")
            currentPlayer = playerOne
            playerTwo = player(mySprite: UIImage(named:"x_button")!, myTurn: false, name: "playerTwo")
            
            playerOneLabel.backgroundColor = UIColor.grayColor()
            playerTwoLabel.backgroundColor = UIColor.whiteColor()
            //print("Player one turn")
        }
        else
        {
            playerOne = player(mySprite: UIImage(named:"x_button")!, myTurn: false, name: "playerOne")
            playerTwo = player(mySprite: UIImage(named:"o_button")!, myTurn: true, name: "playerTwo")
            currentPlayer = playerTwo
           
            playerTwoLabel.backgroundColor = UIColor.grayColor()
            playerOneLabel.backgroundColor = UIColor.whiteColor()
           //print("Player two turn")
        }
        
        
        //setting the symbols assigned to each player
        playerOneSpriteDisplay.image = playerOne?.mySprite
        playerTwoSpriteDisplay.image = playerTwo?.mySprite
        
    }
    
    func updateTimerLabel()
    {
        timeInSeconds = timeInSeconds + 1
        timeInSeconds = timeInSeconds % 16
        
        timerDisplay.text = "Time Elapsed: "+String(timeInSeconds)
        
        
        if(timeInSeconds > 5 && timeInSeconds <= 10)
        {
            timerDisplay.textColor = UIColor.brownColor()
        }
        else if(timeInSeconds > 10 && timeInSeconds <= 15)
        {
            timerDisplay.textColor = UIColor.redColor()
        }
        else
        {
            timerDisplay.textColor = UIColor.blackColor()
        }
        
        if(timeInSeconds == 15)
        {
            changeTurns()
        }

    }
    
    func pauseTimer()
    {
        elapsedTime = timeInSeconds
        timer.invalidate()
        
    }
    
    func resumeTimer()
    {
        timeInSeconds = elapsedTime
        
        //invalidate any previous timers
        timer.invalidate()
        initializeTimer()
    }
    
    func initializeTimer()
    {
        timer = NSTimer.scheduledTimerWithTimeInterval(1, target: self, selector:#selector(ViewController.updateTimerLabel), userInfo: nil, repeats: true)
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
      
        initGame()
        
        timeInSeconds = elapsedTime
        initializeTimer()
        
        NSNotificationCenter.defaultCenter().addObserver(self, selector: #selector(ViewController.pauseTimer), name: UIApplicationDidEnterBackgroundNotification, object: nil)
       
        //loading the border for the buttons
        center.layer.borderWidth = 1
        center.layer.borderColor = UIColor.blackColor().CGColor
        
        centerLeft.layer.borderWidth = 1
        centerLeft.layer.borderColor = UIColor.blackColor().CGColor
        
        centerRight.layer.borderWidth = 1
        centerRight.layer.borderColor = UIColor.blackColor().CGColor
        
        topCenter.layer.borderWidth = 1
        topCenter.layer.borderColor = UIColor.blackColor().CGColor
        
        topLeft.layer.borderWidth = 1
        topLeft.layer.borderColor = UIColor.blackColor().CGColor
        
        topRight.layer.borderWidth = 1
        topRight.layer.borderColor = UIColor.blackColor().CGColor
        
        bottomCenter.layer.borderWidth = 1
        bottomCenter.layer.borderColor = UIColor.blackColor().CGColor
        
        bottomLeft.layer.borderWidth = 1
        bottomLeft.layer.borderColor = UIColor.blackColor().CGColor
        
        bottomRight.layer.borderWidth = 1
        bottomRight.layer.borderColor = UIColor.blackColor().CGColor
        
        
        
        NSNotificationCenter.defaultCenter().addObserver(self, selector: #selector(ViewController.resumeTimer), name: UIApplicationDidBecomeActiveNotification, object: nil)
        
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }


}

