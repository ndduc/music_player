//
//  JSON_Handler.swift
//  MusicPlayer
//
//  Created by mac on 5/30/20.
//  Copyright © 2020 ndduc.project. All rights reserved.
//

import Foundation

class JSON_Handler {
    
    var titles = [String]()
    func jsonSerial(js : Data) ->[String] {
       do {
            if let j = try JSONSerialization.jsonObject(with: js, options: []) as? [String: Any] {
                   // try to read out a string array
                   if let names = j["titles"] as? [String] {
                        titles = names
                    //print(titles)
                    }
               }
           } catch {
               print("error: ", error)
           }
        return titles

        
    }
}
