
<?php 
$caller = !empty($_POST['action'])?$_POST['action']:'-';
$title = !empty($_POST['t'])?$_POST['t']:'-';             //Titles
$name = !empty($_POST['f'])?$_POST['f']:'-';              //File Name
    class jsonGenerator {
        function createJson($filename, $data) {
            $response = "{\"titles\":".$data."}";
            $fp = fopen($filename.'.json', 'w');
            fwrite($fp, $response);
            fclose($fp);
        }
    }
    
    if($caller == "titles") {
        $event = new jsonGenerator();
        $event->createJson($name, $title);
    }

