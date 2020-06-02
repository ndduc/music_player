//
//  ViewController.swift
//  MusicPlayer
//
//  Created by mac on 5/30/20.
//  Copyright © 2020 ndduc.project. All rights reserved.
//

import UIKit

class ViewController: UIViewController, UITableViewDataSource, UITableViewDelegate{
    //https://stackoverflow.com/questions/34563329/how-to-play-mp3-audio-from-url-in-ios-swift
    
    //http://192.168.1.243/leeleelookupphp/php_jsonrw/title_list.json
    let main_file = "http://192.168.1.243/leeleelookupphp/php_jsonrw/title_list.json";
    let sections = ["Fruit"]
    var fruit = [String]()
  //  let vegetables = ["Carrot", "Broccoli", "Cucumber"]
    
    @IBOutlet weak var btnCheck: UIButton!
    //MARK: UI Component

    override func viewDidLoad() {
        //fruit = ["Apple", "Orange", "Mango"]
        super.viewDidLoad()
        var f = [String]()
        let url = URL_Retriever.init()
        url.jsonRetriever()
        url.fetchUser( userCompletionHandler: { user, error in
          if let user = user {
            f = user;
            
            print(f)
          }
        })
        
        print(f)
        
        
        
        
        // Do any additional setup after loading the view.
        
        
    }
    
    
    @IBAction func btnHitClicked(_ sender: Any) {
        print("HIT")
        var tit = [String]()
        let url = URL_Retriever.init()
        url.jsonRetriever()
        url.fetchUser( userCompletionHandler: { user, error in
          if let user = user {
            self.fruit = user;
            
            print(self.fruit)
          }
        })
        
     //   print(self.fruit)
    }
    
    

    
    // MARK: UITableViewDataSource

    func tableView(_ tableView: UITableView, titleForHeaderInSection section: Int) -> String? {
      return sections[section]
    }

    func numberOfSections(in tableView: UITableView) -> Int {
      return sections.count
    }

    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
      switch section {
      case 0:
        // Fruit Section
        return fruit.count/*
      case 1:
        // Vegetable Section
        return vegetables.count*/
      default:
        return 0
      }
    }

    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
      // Create an object of the dynamic cell "PlainCell"
      let cell = tableView.dequeueReusableCell(withIdentifier: "PlainCell", for: indexPath)
      // Depending on the section, fill the textLabel with the relevant text
      switch indexPath.section {
      case 0:
        // Fruit Section
        cell.textLabel?.text = fruit[indexPath.row]
        break
        /*
      case 1:
        // Vegetable Section
        cell.textLabel?.text = vegetables[indexPath.row]*/
        break
      default:
        break
      }

      // Return the configured cell
      return cell
    }

}

