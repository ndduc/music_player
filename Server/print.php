<html>
    <head>
         <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js" type="text/javascript"></script>
         <script type="text/javascript" src="script/audio_script.js"></script>
         <link href="style/audio_style.css" rel="stylesheet" type="text/css" />
       
         <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta charset="UTF-8">
            <title>Label</title>
        </head>
   
        
    <body  onload="audioList_onload();" >
       
       <div class="div-Nav-1"> 
           <div class="div-Nav-Child-1"><a href="http://192.168.1.243:80/leeleelookupphp/youtubedownloader/youtubeDownloader/">Add Music</a></div>
       </div>
       <div class="body-audio">
        
            <div id="idAudio_List"  class="div-Audio-List"></div>

            <div id="idAudio" class="au-Audio-Control">
                 <audio controls id="audioControl">
                     <source id="idSource" type="audio/mpeg"></source>
                     <!--<source src="http://192.168.1.243/leeleelookupphp/youtubedownloader/youtubeDownloader/audio/Lost_Phelian.mp3" type="audio/mpeg">-->
                 </audio>
            </div>

             <div class="div-player">

                 <div class="div-player-prev" onclick="prev()">
                     <img id="id-img-prev">
                 </div>

                 <div  class="div-player-next" onclick="next()">
                     <img id="id-img-next">
                 </div>

                 <div class="div-player-all" onclick="playall()">
                     <img id="id-img-all">
                 </div>

                 <div class="div-player-re" onclick="re()">
                     <img id="id-img-re">
                 </div>


             </div>
       
       </div>
    </body>
   

</html>