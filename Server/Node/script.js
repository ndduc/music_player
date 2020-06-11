let Btn = document.getElementById('btn');
let URLinput = document.querySelector('.inputURL');
let select = document.querySelector('.opt');
let serverURL = 'http://localhost:4000';

Btn.addEventListener('click', () => {
	if (!URLinput.value) {
		alert('Enter YouTube URL');
	} else {
		if (select.value == 'mp3') {
			redirectMp3__ID(URLinput.value);
		} 
	}
});


function redirectMp3(query) {
	console.log(query);
	var urlParams = new URLSearchParams(query);
	var val = '';
	for (let p of urlParams) {
          if(p.includes("https://www.youtube.com/watch?v")) {
             // console.log(p);
              val = p[1];
          }
	}/*
        console.log("TEST: ");
        console.log(val);*/
		
		console.log("TEST: " + query);
        var url = `${serverURL}/downloadmp3?url=${val}`;
        xmlhttpConnetion(`${serverURL}/downloadmp3?url=${val}`);
}

function redirectMp3__ID(query) {
	console.log("TEST: " + query);
	var url = `${serverURL}/downloadmp3?url=${query}`;
	console.log("URL: " + url);
	xmlhttpConnetion(`${serverURL}/downloadmp3?url=${query}`);
}




function xmlhttpConnetion(url) {
    var name = document.getElementById('idName').value;
    console.log(name);
    var xmlhttp = new XMLHttpRequest();
    xmlhttp.onreadystatechange = function() {
        if (this.readyState === 4 && this.status === 200) {
            console.log(this.responseText);
        }
    };
    xmlhttp.open("GET", "phpfunction/file_to_server.php?action=mp3&u="  + url+ "&t=" + name, true);
	console.log("phpfunction/file_to_server.php?action=mp3&u="  + url+ "&t=" + name);
    xmlhttp.send();
}
