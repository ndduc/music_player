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
    var titles = [String]()
    //MARK: URL json retriever

    
    func jsonRetriever(){
        var user : [String]?
        let url = URL(string: main_file)!
        _ = URLSession.shared.dataTask(with: url, completionHandler: { data, response, error in
            guard let data = data else { return }
            let jsonHandler = JSON_Handler.init()
            user = jsonHandler.jsonSerial(js: data)
            //print(user)

        }).resume()
        print(user)
       // print(user!)
        // return user outside the completion handler
    }
    
    // add userCompletionHandler and remove ' -> User?' to make it a void function
    func fetchUser(userCompletionHandler: @escaping ([String]?, Error?) -> Void) {
      let url = URL(string: main_file)!
      let task = URLSession.shared.dataTask(with: url, completionHandler: { data, response, error in

        guard let data = data else { return }
        do {
          // parse json data and return it
          let jsonHandler = JSON_Handler.init()
          let user = jsonHandler.jsonSerial(js: data)

            userCompletionHandler(user, nil)
          
          
        } catch let parseErr {
          print("JSON Parsing Error", parseErr)
          userCompletionHandler(nil, parseErr)
        }
      })
      
      task.resume()
      // function will end here and return
      // then after receiving HTTP response, the completionHandler will be called
    }
    
    func jsonRetriever_2()->[String] {
        var tit = [String]()
        let requestUrl = URL(string: main_file)
        let request = URLRequest(url: requestUrl!)
        let requestTask = URLSession.shared.dataTask(with: request) {
            (data: Data?, response: URLResponse?, error: Error?) in

            if(error != nil) {
                print("Error: \(String(describing: error))")
            }else
            {
                let jsonHandler = JSON_Handler.init()
               // self.setTitles(tit: jsonHandler.jsonSerial(js: data!))
                tit = jsonHandler.jsonSerial(js: data!)
                print(tit)
            }
        }
        requestTask.resume()
        print(tit)
        return tit
    }

    
    func setTitles ( tit : [String]) {
        self.titles = tit
    }

    
    func printData() {
        print(titles)
    }
    
    
}
