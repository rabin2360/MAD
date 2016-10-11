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
    @IBOutlet weak var single2PlayerSelection: UISegmentedControl!
    @IBOutlet weak var winnerDisplay: UILabel!
    
    @IBOutlet weak var startButton: UIButton!
    @IBOutlet weak var stopButton: UIButton!
    let xButton = UIImage(named: "x_button") as UIImage?
    let oButton = UIImage(named: "o_button") as UIImage?
    
    @IBOutlet weak var timerDisplay: UILabel!
    var timeInSeconds = 0
    
    //2-D matrix --> tic-tac-toe board
    var ticTacToeBoard : [[String]] = []
    var playerOne: player?
    var playerTwo: player?
    var currentPlayer: player?
    var timer = NSTimer()
    var elapsedTime = 0
    var movesLeft = 9
    var gameState = GameState.STOPPED
    var gameMode = GameMode.TWO_PLAYER
    
    static let TIMELIMIT = 16

    enum SquareType:String {
        case Empty  = "-"
        case Cross  = "X"
        case Circle = "O"
    }
    
    enum GameState
    {
        case STOPPED
        case PLAYING
    }
    
    enum GameMode
    {
        case SINGLE_PLAYER
        case TWO_PLAYER
    }
    
    //struct with player attributes
    struct player
    {
        var mySprite: UIImage
        var myTurn = false
        var name = "none"
        var spriteType = SquareType.Empty
    }
    
    func randomNumGen() -> Int
    {
        return Int(arc4random_uniform(2))
    }
    
    //win logic for the game
    func gameWon() -> Bool
    {
        var hasWon = false
        var count = 0
        
        //check along the columns
        for columns in 0 ..< ticTacToeBoard[0].count
        {
            count = 0
            for rows in 0 ..< ticTacToeBoard.count
            {

                if(ticTacToeBoard[rows][columns] != "false" && ticTacToeBoard[0][columns] != "false" && ticTacToeBoard[rows][columns] == ticTacToeBoard[0][columns])
                {
                    hasWon = true
                    count += 1
                }
                else
                {
                    hasWon = false
                    break
                }
            }
            
            if(count == 3)
            {
                break
            }
     
        }
        
        if(hasWon && count == 3)
        {
            hasWon = false
            count = 0
            print("You win - columns!")
            return true
        }
        
        //check along the rows
        for rows in 0 ..< ticTacToeBoard[0].count
        {
            count = 0
            for columns in 0 ..< ticTacToeBoard.count
            {
                if(ticTacToeBoard[rows][columns] != "false" && ticTacToeBoard[rows][0] != "false" && ticTacToeBoard[rows][columns] == ticTacToeBoard[rows][0])
                {
                    hasWon = true
                    count += 1
                }
                else
                {
                    hasWon = false
                    break
                }
                
                //print(ticTacToeBoard[rows][columns])
            }
            
            if(count == 3)
            {
                break
            }
        }
        
        if(hasWon && count == 3)
        {
            hasWon = false
            count = 0
            print("You win - rows!")
            return true
        }
        
        //check diagonal - left to right
        let leftDiagonalVal = ticTacToeBoard[0][0]
        hasWon = false
        
        for rowColVal in 1 ..< ticTacToeBoard.count
        {
            if(leftDiagonalVal != "false" && ticTacToeBoard[rowColVal][rowColVal] != "false" && leftDiagonalVal == ticTacToeBoard[rowColVal][rowColVal])
            {
                hasWon = true
            }
            else
            {
                hasWon = false
                break
            }
        }
        
        if(hasWon)
        {
            print("You win - left diagonal")
            return true
        }
        
        //check diagonal - right to left
        let rowIndex = 0
        let colIndex = 2
        let rightDiagonalVal = ticTacToeBoard[rowIndex][colIndex]
        
        for val in 0..<3
        {
            if(rightDiagonalVal == "false")
            {
                hasWon = false
                break
            }
            
            if(ticTacToeBoard[rowIndex+val][colIndex-val] != "false" && rightDiagonalVal == ticTacToeBoard[rowIndex+val][colIndex-val])
            {
                hasWon = true
            }
            else
            {
                hasWon = false
                break
            }
        }
        
        if(hasWon)
        {
            print("You win - right diagonal")
            return true
        }
        
        return false
    }
    

    //takes the tag of the sender button and then if empty, places appropriate sprite for the current player
    func twoPlayerMode(sender: UIButton)
    {
    
        let firstIndex = sender.tag / 10
        let secondIndex = sender.tag % 10
        
        if(ticTacToeBoard[firstIndex][secondIndex] == "false")
        {
            //ticTacToeBoard[firstIndex][secondIndex] = true
            sender.setBackgroundImage(currentPlayer?.mySprite, forState: .Normal)
            ticTacToeBoard[firstIndex][secondIndex] = (currentPlayer?.spriteType.rawValue)!
            
            sender.enabled = false
            movesLeft -= 1
        }

    
    }
    //takes the given index and populates them with sprite
    func populateButtonsWithSprite(rowIndex: Int, colIndex: Int) -> Bool
    {
        var tempTag = -11
        tempTag = rowIndex*10
        tempTag = tempTag + colIndex
        //print("\(rowIndex)\(colIndex)")
        let tempButton = self.view.viewWithTag(tempTag) as? UIButton
        
        if(tempButton != nil){
            tempButton!.setBackgroundImage(currentPlayer?.mySprite, forState: .Normal)
            ticTacToeBoard[rowIndex][colIndex] = (currentPlayer?.spriteType.rawValue)!
            
            tempButton!.enabled = false
            movesLeft -= 1
            
            return true
        }
        
        return false
    }
    
    //takes the given array of indices and populates them with sprite if empty
    func checkGivenIndices(indices: [Int])->Bool
    {
        for index: Int in indices
        {
            let rowIndex = index/10
            let colIndex = index%10
            
            if(ticTacToeBoard[rowIndex][colIndex] == "false")
            {
                if(populateButtonsWithSprite(rowIndex, colIndex: colIndex)){
                    return true
                }
            }
        }
        
        return false
    }
    
    //AI logic for single player mode
    func singlePlayerMode()
    {
        //check if the middle of the board is empty
        if(ticTacToeBoard[1][1] == "false")
        {
            let tempButton = self.view.viewWithTag(11) as? UIButton
            tempButton!.setBackgroundImage(currentPlayer?.mySprite, forState: .Normal)
            ticTacToeBoard[1][1] = (currentPlayer?.spriteType.rawValue)!
            
            tempButton!.enabled = false
            movesLeft -= 1
            return
        }
        
            //check along the columns
            var count = 0
            var rowIndex = -1
            var colIndex = -1
            for columns in 0 ..< ticTacToeBoard[0].count
            {
                count = 0
                rowIndex = -1
                colIndex = -1
                for rows in 0 ..< ticTacToeBoard.count
                {
                    
                    if(ticTacToeBoard[rows][columns] == "O")
                    {
                        count += 1
                    }
                    else if (ticTacToeBoard[rows][columns] == "false")
                    {
                        rowIndex = rows
                        colIndex = columns
                    }
                }
                
                if(colIndex != -1 && rowIndex != -1 && count == 2 && ticTacToeBoard[rowIndex][colIndex] == "false")
                {
                    if(populateButtonsWithSprite(rowIndex, colIndex: colIndex)){
                        return
                    }
                }
                
            }
            

            //check along the rows
          
            for rows in 0 ..< ticTacToeBoard[0].count
            {
                count = 0
                rowIndex = -1
                colIndex = -1
                for columns in 0 ..< ticTacToeBoard.count
                {
                    if(ticTacToeBoard[rows][columns] == "O")
                    {
                        count += 1
                    }
                    else if(ticTacToeBoard[rows][columns] == "false")
                    {
                        rowIndex = rows
                        colIndex = columns
                    }
                }
                
                if(rowIndex != -1 && colIndex != -1 && count == 2 && ticTacToeBoard[rowIndex][colIndex] == "false")
                {
                    if(populateButtonsWithSprite(rowIndex, colIndex: colIndex)){
                        return
                    }
                }
            
            //check diagonal - left to right
                count = 0
                rowIndex = -1
                colIndex = -1
                for rowColVal in 0 ..< ticTacToeBoard.count
                {
                    if(ticTacToeBoard[rowColVal][rowColVal] == "O")
                    {
                        count += 1
                    }
                    else
                    {
                        rowIndex = rowColVal
                        colIndex = rowColVal
                    }
                }
                
                if(count == 2 && ticTacToeBoard[rowIndex][colIndex] == "false")
                {
                    if(populateButtonsWithSprite(rowIndex, colIndex: colIndex)){
                    return
                    }
                }
            
            
            //check diagonal - right to left
                rowIndex = 0
                colIndex = 2
                count = 0
                for val in 0..<2
                {
                    if(ticTacToeBoard[rowIndex+val][colIndex-val] == "O")
                    {
                        count += 1
                    }
                    else
                    {
                        rowIndex = rowIndex+val
                        colIndex = colIndex-val
                    }
                }
                
                if(count == 2 && ticTacToeBoard[rowIndex][colIndex] == "false")
                {
                    if(populateButtonsWithSprite(rowIndex, colIndex: colIndex)){
                        return
                    }
                }
                
                //put it in all four corners
                let fourCorners : [Int] = [00, 02, 22, 20]
                
                if(checkGivenIndices(fourCorners))
                {
                    return
                }
                
                
                //fill the following sides
                let fourSides : [Int] = [12,10,21,01]
                
                if(checkGivenIndices(fourSides))
                {
                    return
                }
        }
    }
    
    //this is the sequence called to stop the game
    func stopSequence()
    {
        enableButtons(false)
        stopTimer()
        startButton.enabled = true
        single2PlayerSelection.enabled = true
        stopButton.enabled = false
    }
    
    //determine if game won, otherwise change turns
    //also change the state of the game appropriately from PLAYING to STOPPED and vice versa
    func determineWinner()
    {
    
        if(gameWon())
        {
            winnerDisplay.text = (currentPlayer?.name)! + " won!"
            stopSequence()
        }
        else if(movesLeft <= 0)
        {
            winnerDisplay.text = "Tie!"
            gameState = GameState.STOPPED
            
            stopSequence()
            
        }
        else
        {
            changeTurns()
            gameState = GameState.PLAYING
            
        }
        
        if(gameWon())
        {
            gameState = GameState.STOPPED
        }
        
      
    }
    
    //for all the buttons in the tic-tac-toe board, this method is triggered
    //based on the game mode and the current player, the logic is appropriately routed
    @IBAction func buttonPressed(sender: UIButton) {
    
        if(gameMode == GameMode.TWO_PLAYER)
        {
            twoPlayerMode(sender)
        }
        else if(gameMode == GameMode.SINGLE_PLAYER && currentPlayer?.name == "Player One")
        {
            twoPlayerMode(sender)
        }
        
        determineWinner()
        

        if(gameMode == GameMode.SINGLE_PLAYER && currentPlayer?.name == "Player Two")
        {
            singlePlayerMode()
            determineWinner()
        }
        
    }
    
    //if currently player one turn
    //set the turn of player one to false
    //change background highlights appropriately
    //set current player at player two
    func playerOneTurn()
    {
        playerOne!.myTurn = false
        playerTwoLabel.backgroundColor = UIColor.grayColor()
        playerOneLabel.backgroundColor = UIColor.whiteColor()
        currentPlayer = playerTwo
    }
    
    //same as above but just reverse
    func playerTwoTurn()
    {
        playerOne!.myTurn = true
        playerOneLabel.backgroundColor = UIColor.grayColor()
        playerTwoLabel.backgroundColor = UIColor.whiteColor()
        currentPlayer = playerOne
    }
    
    //changing turns appropriately depending on whether the game is in single player mode or 
    //two player mode
    func changeTurns()
    {
        if(gameMode == GameMode.TWO_PLAYER){
        //changing the turn
            if(playerOne!.myTurn)
            {
                playerOneTurn()
            }
            else
            {
                playerTwoTurn()
            }
        }
        else
        {
        
            if(playerOne!.myTurn)
            {
                playerOneTurn()
                singlePlayerMode()
                determineWinner()
            }
            else
            {
                playerTwoTurn()
            }
        }
        
        //reset timer
        timeInSeconds = ViewController.TIMELIMIT

    }
    
    @IBAction func stopButtonPressed(sender: UIButton) {
        
        //the state of the game is stopped
        gameState = GameState.STOPPED
        stopSequence()
    }
    

    //called when segmented control is pressed
    @IBAction func changeGameMode(sender: UISegmentedControl) {
        
        selectPlayer()
    }
    
    //segmented control listener method calls this
    //also called by the init method at the beginning of the game
    func selectPlayer()
    {
        if(single2PlayerSelection.selectedSegmentIndex == 0)
        {
            //select two players at random
            playerOneLabel.text = "Player 1"
            playerTwoLabel.text = "Player 2"
            
            if(randomNumGen() == 1)
            {
                playerOne = player(mySprite: UIImage(named:"o_button")!, myTurn: true, name: "Player One", spriteType: SquareType.Circle)
                currentPlayer = playerOne
                playerTwo = player(mySprite: UIImage(named:"x_button")!, myTurn: false, name: "Player Two", spriteType: SquareType.Cross)
                
                playerOneLabel.backgroundColor = UIColor.grayColor()
                playerTwoLabel.backgroundColor = UIColor.whiteColor()
                //print("Player one turn")
            }
            else
            {
                playerOne = player(mySprite: UIImage(named:"x_button")!, myTurn: false, name: "Player One", spriteType: SquareType.Cross)
                playerTwo = player(mySprite: UIImage(named:"o_button")!, myTurn: true, name: "Player Two", spriteType: SquareType.Circle)
                currentPlayer = playerTwo
                
                playerTwoLabel.backgroundColor = UIColor.grayColor()
                playerOneLabel.backgroundColor = UIColor.whiteColor()
                //print("Player two turn")
            }
            
            gameMode = GameMode.TWO_PLAYER
            
        }
        else
        {
            //select player 1 as player and player 2 as opponent
            playerOne = player(mySprite: UIImage(named:"o_button")!, myTurn: true, name: "Player One", spriteType: SquareType.Circle)
            currentPlayer = playerOne
            
            playerTwo = player(mySprite: UIImage(named:"x_button")!, myTurn: false, name: "A.I.", spriteType: SquareType.Cross)
            
            playerOneLabel.text = "Player 1"
            playerTwoLabel.text = "A.I."
            
            playerOneLabel.backgroundColor = UIColor.grayColor()
            playerTwoLabel.backgroundColor = UIColor.whiteColor()
            
            gameMode = GameMode.SINGLE_PLAYER
        }
    }
    
    //resetting the display images of the buttons in the tic-tac-toe board
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
        ticTacToeBoard = [["false","false","false"], ["false","false","false"], ["false","false","false"]]
        
        //select player based on the segmented control
        selectPlayer()
        
        //game state is stopped
        gameState = GameState.STOPPED
        
        //disable stop button
        stopButton.enabled = false
        
        //setting the symbols assigned to each player
        playerOneSpriteDisplay.image = playerOne?.mySprite
        playerTwoSpriteDisplay.image = playerTwo?.mySprite
        
    }
    
    //called every second
    func updateTimerLabel()
    {
        timeInSeconds = timeInSeconds + 1
        timeInSeconds = timeInSeconds % ViewController.TIMELIMIT
        
        timerDisplay.text = "Time Remaining: "+String(ViewController.TIMELIMIT - timeInSeconds)
        
        switch timeInSeconds {
        case 0..<6:
            timerDisplay.textColor = UIColor.blackColor()
        
        case 6..<11:
            timerDisplay.textColor = UIColor.brownColor()
        
        default:
            timerDisplay.textColor = UIColor.redColor()
        }
        
        if(timeInSeconds == (ViewController.TIMELIMIT-1))
        {
            changeTurns()
        }

    }
    
    //called to stop the timer
    func stopTimer()
    {
        timeInSeconds = 0
        timer.invalidate()
    }
    
    //called when entering background
    func pauseTimer()
    {
        elapsedTime = timeInSeconds
        timer.invalidate()
    }
    
    //called when entering foreground
    func resumeTimer()
    {
        //timer will only resume if the state of the game before going to background was playing
        if(gameState == GameState.PLAYING)
        {
            timeInSeconds = elapsedTime
        
            //invalidate any previous timers
            timer.invalidate()
            initializeTimer()
        }
    }
    
    func initializeTimer()
    {
        timer = NSTimer.scheduledTimerWithTimeInterval(1, target: self, selector:#selector(ViewController.updateTimerLabel), userInfo: nil, repeats: true)
    }
    
    @IBAction func startButtonPressed(sender: UIButton) {
        
        initGame()
        resetBoardDisplay()
        
        gameState = GameState.PLAYING
        enableButtons(true)
        winnerDisplay.text = ""
        timeInSeconds = elapsedTime
        movesLeft = 9
        initializeTimer()
       
        NSNotificationCenter.defaultCenter().addObserver(self, selector: #selector(ViewController.pauseTimer), name: UIApplicationDidEnterBackgroundNotification, object: nil)
        
        
        NSNotificationCenter.defaultCenter().addObserver(self, selector: #selector(ViewController.resumeTimer), name: UIApplicationDidBecomeActiveNotification, object: nil)

        //disable the button until stop is pressed again
        startButton.enabled = false
        stopButton.enabled = true
        single2PlayerSelection.enabled = false
    }
    
    //enable/disable the buttons based on the input parameter flag
    func enableButtons(flag : Bool)
    {
    
        center.enabled = flag
        centerLeft.enabled = flag
        centerRight.enabled = flag
        
        topLeft.enabled = flag
        topCenter.enabled = flag
        topRight.enabled = flag
        
        bottomLeft.enabled = flag
        bottomCenter.enabled = flag
        bottomRight.enabled = flag
    }
    
    //loading the first look of the game with all the button backgrounds set as white
    override func viewDidLoad() {
        super.viewDidLoad()
      
        initGame()
       
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
        
        //disable buttons until start is pressed
        enableButtons(false)
        
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }


}

