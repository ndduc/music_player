<?php


$path = 'J:\Framework\Xampp\htdocs\leeleelookupphp\Node\Audio';
    class audio {
        
        function getFiles($_path) {
            /*$path    = $_path;*/
            $files = scandir($_path);
            $arr = array();
            foreach($files as $key => $value)
            {
                if(preg_match('/\.mp3\b/', $value)) {
                    //echo "<li>".$value."</li>";
                    array_push($arr, $value);
                } else {
                    unset($files[$key]);
                }
            }
            return json_encode($arr);
        }
        
        function getFilesArray($_path) {
            $files = scandir($_path);
            $arr = array();
            foreach($files as $key => $value)
            {
                if(preg_match('/\.mp3\b/', $value)) {
                    //echo "<li>".$value."</li>";
                    array_push($arr, $value);
                } else {
                    unset($files[$key]);
                }
            }
            return $arr;
        }
    }
    $caller = !empty($_GET['action'])?$_GET['action']:'-';
    if($caller == "json") {
        $test_class = new audio();
        $arr = $test_class->getFiles($path);        
        //echo $arr;
    } else if ($caller == "array") {
        $test_class = new audio();
        $arr = $test_class->getFilesArray($path);        
        
        /* 
        echo "<table id=\"idTableAudio\" border=\"1\">";
        foreach ($arr as $key=>$item){
             echo "<tr onclick=\"play_Audio()\" class=\"divAudi\" ><td id=\"idDivPlay".$key."\">".$item."</td></tr>";
        } 
        echo "</table>";
        */
        echo "<ul class=\"ul-audio-list\" id=\"audiolist\" onclick=\"play_Audio_Li(event)\">";
        foreach ($arr as $key=>$item){
             echo "<li class=\"classDivPlay".$key."\" id=\"idDivPlay".$key."\">".$item."</li>";
        } 
        echo "</ul>";
    }
   
            
?>