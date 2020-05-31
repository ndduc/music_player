//
//  URL_Retriever.swift
//  MusicPlayer
//
//  Created by mac on 5/30/20.
//  Copyright © 2020 ndduc.project. All rights reserved.
//

import Foundation
class URL_Retriever {
    
    let main_file = "http://192.168.1.243/leeleelookupphp/php_jsonrw/title_list_2.json";
    
    //MARK: URL json retriever
    func jsonRetriever() {
        //var json = ""
        if let url = URL(string: main_file) {
           URLSession.shared.dataTask(with: url) { data, response, error in
              if let data = data {
                
                let jsonHandler = JSON_Handler.init()
                jsonHandler.jsonSerial(js: data)
                 /*if let jsonString = String(data: data, encoding: .utf8) {
                    print(jsonString)
                 }*/
               }
           }.resume()
        }
    }
    
    
}
