//
//  JSON_Handler.swift
//  MusicPlayer
//
//  Created by mac on 5/30/20.
//  Copyright © 2020 ndduc.project. All rights reserved.
//

import Foundation

class JSON_Handler {
    func jsonSerial(js : Data) {
       do {
            if let j = try JSONSerialization.jsonObject(with: js, options: []) as? [String: Any] {
                   // try to read out a string array
                   if let names = j["titles"] as? [String] {
                       print(names)
                   }
               }
           } catch let DecodingError.dataCorrupted(context) {
               print("1")
               print(context)
           } catch let DecodingError.keyNotFound(key, context) {
            print("2")
               print("Key '\(key)' not found:", context.debugDescription)
               print("codingPath:", context.codingPath)
           } catch let DecodingError.valueNotFound(value, context) {
            print("3")
               print("Value '\(value)' not found:", context.debugDescription)
               print("codingPath:", context.codingPath)
           } catch let DecodingError.typeMismatch(type, context)  {
            print("4")
               print("Type '\(type)' mismatch:", context.debugDescription)
               print("codingPath:", context.codingPath)
           } catch {
            print("5")
               print("error: ", error)
           }
        

        
    }
}
