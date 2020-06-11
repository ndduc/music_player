
<?php

$dir = 'J:\Framework\Xampp\htdocs\leeleelookupphp\Node\Audio';
//$caller = !empty($_GET['action'])?$_GET['action']:'-';
$c = basename(filter_input(INPUT_GET, 'action', FILTER_UNSAFE_RAW, FILTER_FLAG_STRIP_LOW));
class read {
    function read_from_dir($dir) {
        if (is_dir($dir)) {
            if ($dh = opendir($dir)) {
                while (($file = readdir($dh)) !== false) {
                    if(strlen($file) > 5) {
                        echo "<div>".$file ."</div>";
                    }
                }
                closedir($dh);
            }
        }
    }
    
    /**
     * This function return json array
     * Can be use to pull data from server to android
     * **/
    function getNameList($dir) {
        $titles = [];
        if ($dh = opendir($dir)) {
            while (($file = readdir($dh)) !== false) {
                if(strlen($file) > 5) {
                    array_push($titles, $file);
                }
            }
            closedir($dh);
        }
        
      
        $obj['titles'] = $titles;
        echo '<pre>'; print_r(json_encode($obj, JSON_PRETTY_PRINT)); echo '</pre>';
      //  header('Content-Type: application/json');
        return json_encode($obj, JSON_PRETTY_PRINT);
    }
}
    

if($c == "simple_read") {
    $event = new read();
    $event -> read_from_dir($dir);
} else if ($c == "adv_read") {
    $event = new read();
    $event -> getNameList($dir);
}

         