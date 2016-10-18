//
//  ViewController.swift
//  location
//
//  Created by Rabin Ranabhat on 10/4/16.
//  Copyright © 2016 Rabin Ranabhat. All rights reserved.
//

import UIKit
import MapKit
import CoreLocation

class ViewController: UIViewController, CLLocationManagerDelegate {

    var locationManager = CLLocationManager()
    var annotation = MKPointAnnotation()
    @IBOutlet weak var mapView: MKMapView!
    
    //after updating the location
    func locationManager(manager: CLLocationManager, didUpdateLocations locations: [CLLocation]) {
        let span = MKCoordinateSpanMake(0.05, 0.05) //defines the area spanned by a map region
        let region = MKCoordinateRegionMake(manager.location!.coordinate, span) //region of the map to be displayed
        mapView.setRegion(region, animated: true) //animates changing the currently visible region
        
        //annotations
        annotation.coordinate=manager.location!.coordinate
        annotation.title="Your location"
        annotation.subtitle="Latitude: \(manager.location!.coordinate.latitude), Longitude: \(manager.location!.coordinate.longitude)"
        mapView.addAnnotation(annotation)
    }
    
    //checks for authorizations, or when authorizations change
    func locationManager(manager: CLLocationManager, didChangeAuthorizationStatus status: CLAuthorizationStatus) {
        
        if status == CLAuthorizationStatus.AuthorizedWhenInUse
        {
            locationManager.startUpdatingLocation()
        }
    }
    
    
    func locationManager(manager: CLLocationManager, didFailWithError error: NSError) {
        
        var errorType = String()
        
        if let clError=CLError(rawValue: error.code) {
            if clError == .Denied {
                errorType="access denied"
                let alert=UIAlertController(title: "Error", message: errorType, preferredStyle: UIAlertControllerStyle.Alert)
                let okAction:UIAlertAction=UIAlertAction(title: "OK", style:UIAlertActionStyle.Default, handler: nil)
                alert.addAction(okAction)
                presentViewController(alert, animated: true, completion: nil)
            }
        }
    }
    
    override func viewDidLoad() {
        mapView.mapType=MKMapType.Hybrid //hybrid with map and satellite
        let status = CLLocationManager.authorizationStatus()
        if status==CLAuthorizationStatus.NotDetermined{
            locationManager.requestWhenInUseAuthorization()
        }
        locationManager.delegate=self
        locationManager.desiredAccuracy=kCLLocationAccuracyBest //specify the desired accuracy
        locationManager.distanceFilter=kCLDistanceFilterNone //specify the distance a device must move laterally(in meters) to generate an update. We specify to be notified of all movements
        mapView.showsUserLocation=true
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
        
        
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
}

