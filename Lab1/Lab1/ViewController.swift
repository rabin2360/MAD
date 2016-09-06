import UIKit

class ViewController: UIViewController {
    
    @IBOutlet weak var pictureDisplay: UIImageView!
    
    @IBOutlet weak var labelDisplayer: UILabel!
    
    @IBAction func paintingsPressed(sender: UIButton) {
        pictureDisplay.image = UIImage(named: "monalisa.png")
        labelDisplayer.text = "MonaLisa"
    }
    
    @IBAction func drawingPressed(sender: UIButton) {
        pictureDisplay.image = UIImage(named: "anatomy.png")
        labelDisplayer.text = "Anatomy picture"
    }
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
        labelDisplayer.text = "Leonardo Da Vinci"
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    
}

