/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var audioList;
var selectedAudio;

var selectedId;
var selectedTitle;
var selectedClass;
var title = [];
var title_json;     //variable hold list of titles in JSON format

const PATH = 'http://192.168.1.243:80/leeleelookupphp/youtubeDownloader/Audio/';
/**
 *  Loading audio list on load
 *  data pull from php by Ajax
 * */
function audioList_onload() {
    //AJAX
    xmlhttpConnetion("json", "1");
    xmlhttpConnetion("array", "1");
    loadImages();
    
    /*
        console.log("TEST");
        TitlesToJson();
        console.log(title);
    */
    
    
}



function xmlhttpConnetion(phpaction, option) {
    var xmlhttp = new XMLHttpRequest();
    
    if(option === "1") {
        xmlhttp.onreadystatechange = function() {
        if (this.readyState === 4 && this.status === 200) {
            audioList = this.responseText;
            console.log(this.responseText);
            var dom_com = document.getElementById("idAudio_List");
            dom_com.insertAdjacentHTML('beforeend', audioList);
            }
        };
        xmlhttp.open("GET", "phpfunction/audio_php.php?action=" + phpaction, true);
        xmlhttp.send();
    } else if (option === "2") {
        xmlhttpConnection_Json();
    }
    
}

/**TEST json generator*/
function xmlhttpConnection_Json() {
    var xmlhttp = new XMLHttpRequest();
    xmlhttp.onreadystatechange = function() {
        if (this.readyState === 4 && this.status === 200) {
        }
    };
    xmlhttp.open("GET", "php_JsonRW/Json_RW.php?action=titles&t=TEST&f=new", true);
    xmlhttp.send();
}

/**2nd TEST json*/
function xmlhttpConnection_Json_2(json_data) {
    console.log("TEST___" + json_data);
    $.ajax({
        type: "POST",
        url: "php_JsonRW/Json_RW.php",
        data: 'action=titles&t='+ json_data +  ' &f=title_list',
        success: function(data) {
            $("#info").html(data);
        }
    });
}




/**
 *  Goal
 *      set play audion id and onlick in the increase order
 *      such as i, i++,.....
 * */
function play_Audio() {
    getSelectedCell("idTableAudio");
    console.log(selectedAudio);
    //playAudio_param(selectedAudio);
    playAudio_fromPlayer(selectedAudio);
    
    
}


/*
 * This function get element from UL
 * then pass selected value to playAudio_fromPlayer
 * **/

function play_Audio_Li(event) {
        var target = event.target || event.srcElement;
        selectedTitle = event.target.innerHTML;
        selectedId = event.target.id;
        selectedClass = event.target.className;
        playAudio_fromPlayer(event.target.innerHTML);
}
function test() {
    console.log(selectedId);
    console.log(selectedTitle);
    console.log(selectedClass);
}


function next() {
    
    var current = document.getElementById(selectedId);
    var nxt = current.nextSibling;
    selectedTitle = nxt.innerHTML;
    selectedId = nxt.id;
    selectedClass = nxt.className;
    playAudio_fromPlayer(selectedTitle);
}

function json() {
    console.log("TEST");
    TitlesToJson();
    xmlhttpConnection_Json_2(title_json);
    console.log(title);
        
}

function prev() {
    var current = document.getElementById(selectedId);
    var prev = current.previousSibling;
    selectedTitle = prev.innerHTML;
    selectedId = prev.id;
    selectedClass = prev.className;
    playAudio_fromPlayer(selectedTitle);
}


/*
 * add all element from list to array
 * then pass the initiated array to sub method
 * 
 * @param {type} selectedAudio
 * @returns {undefined}
 * 
 */
function playall() {
    getAllTitles();
    playAudio_fromPlayer_All(title);
    //playAudio_fromPlayer_All_Mobile(title);
}

/**Get all existing titles in folde then store in titles variable*/
function getAllTitles() {
    title = [];
    var ulist = document.getElementById("audiolist");
    var children = ulist.children;
    console.log(children.length);
    for (var i = 0; i < children.length; i++) {
            title.push(children[i].textContent);
    }
    console.log(title);
}

/**Convert Titles to JSON*/
function TitlesToJson() {
    title_json = null;
    getAllTitles();
    title_json = JSON.stringify(title);
    console.log(title_json);
}

/**
 *  Audio Playr Function
 *  Load audio on click
 * */
function playAudio_fromPlayer(selectedAudio) {
    var path = PATH;
    var au = document.getElementById('audioControl');
    var src= document.getElementById('idSource');
    src.setAttribute("src", path + selectedAudio)
    
    au.load();
    au.play();
    /*
    au.addEventListener("ended", function(){
    au.currentTime = 0;
        console.log("ended");
    });*/
}

/*PLAY ALL
 * audio.onended allow the program to play all
 * */
function playAudio_fromPlayer_All(audioArray) {
    var path = PATH;
    var index = 1;
    var au = document.getElementById('audioControl');
    var src= document.getElementById('idSource');
    src.setAttribute("src", path + audioArray[0]);
    au.load();
    au.play();
    
    au.ontimeupdate = function() {timmer();};

    function timmer() {
      // Display the current position of the video in a p element with id="demo"
        console.log(au.currentTime);
        
        
        if(au.currentTime >= 10) {
            console.log("HIt CASE");/*
                src.setAttribute("src", path + audioArray[1]);
                au.load();
                au.play();*/
        }
        
        
    }
    
   


    au.onended  = function() {
        if(index < audioArray.length){
            src.setAttribute("src", path + audioArray[index]);
            au.load();
            au.play();
            index++;
        }
    };
    

}

/**
 * New Play All
 * Handle browser requirement on IPHONE
 * BUSTED -- solution -- SWIFT Application 
 * */
function playAudio_fromPlayer_All_Mobile(audioArray) {
    var path = PATH;
    var index = 0;
    var au = document.getElementById('audioControl');
    
    if(index < audioArray.length){
       au.insertAdjacentHTML('beforeend', '<source src="' + path + audioArray[index]  + '" type="audio/mpeg"></source>');
       index++;
    }
    
}

/**NOT FIN*/

/** NO LONGER USING
 *  Get value from selected cell
 *  return value to global var
 * */
function getSelectedCell(id){
    var tbl = document.getElementById(id);
        if (tbl != null) {
            for (var i = 0; i < tbl.rows.length; i++) {
                for (var j = 0; j < tbl.rows[i].cells.length; j++)
                    tbl.rows[i].cells[j].onclick = function () { getval(this); };
            }
        }
        function getval(cel) {
            selectedAudio = cel.innerHTML;
        }
}


function play_Audio_li() {
    getSelectedLi();
}


/**Load Image Icon to Player
 * This class will be initiated with onload()
 * */
var src_next = "http://192.168.1.243:80/leeleelookupphp/resourse/AU_Image/next_2.svg";
var src_prev = "http://192.168.1.243:80/leeleelookupphp/resourse/AU_Image/back_2.svg";
var src_all = "http://192.168.1.243:80/leeleelookupphp/resourse/AU_Image/all_2.svg";
var src_re = "http://192.168.1.243:80/leeleelookupphp/resourse/AU_Image/repeat_2.svg";
var src_shuf = "http://192.168.1.243:80/leeleelookupphp/resourse/AU_Image/shuffle.svg";
function loadImages() {
    loadImg(src_next, "id-img-next");
    loadImg(src_prev, "id-img-prev");
    loadImg(src_all, "id-img-all");
    loadImg(src_re, "id-img-re");
    loadImg(src_shuf, "id-img-shuf");
}

function loadImg(src, id) {
    var img = document.getElementById(id);
    img.setAttribute("src", src);
}


/**LOAD Navigator bar*/

