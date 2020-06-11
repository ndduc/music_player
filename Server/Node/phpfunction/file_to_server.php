<?php 
    // Initialize a file URL to the variable 
    $_url = !empty($_GET['u'])?$_GET['u']:'-';
    $title = !empty($_GET['t'])?$_GET['t']:'-';
    $caller = !empty($_GET['action'])?$_GET['action']:'-';
    class file_to_server{
        function download($url, $tit) {
            $ch = curl_init($url);
            curl_setopt($ch, CURLOPT_HEADER, 0);
            curl_setopt($ch, CURLOPT_NOBODY, 0);
            curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
            curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, 1);
            curl_setopt($ch, CURLOPT_FOLLOWLOCATION, true);
            $output = curl_exec($ch);
            $status = curl_getinfo($ch, CURLINFO_HTTP_CODE);
            curl_close($ch);
            if ($status == 200) {
                //file_put_contents(dirname(__FILE__) . '/audio7.mp3', $output);
                $path = 'J:\Framework\Xampp\htdocs\leeleelookupphp\youtubeDownloader\Audio';
               
                $name = '/'.$tit.'.mp3';
                $full = $path.$name;
                file_put_contents($full, $output);
            }
            
  
            echo PHP_EOL ;
            echo 'Finish';
        }
    }
    
    
    if($caller=="mp3") {
        $action_class = new file_to_server();
        $action_class->download($_url, $title);
    }
   
?> 


