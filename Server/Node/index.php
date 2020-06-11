<!DOCTYPE html>
<html lang='en'>

<head>
	<link rel="stylesheet" href="http://192.168.1.243:80/leeleelookupphp/node/style.css" />
       <!-- <script type="text/javascript" src="http://192.168.1.243/leeleelookupphp/node/script.js"></script>-->
	<title>Parser</title>
</head>

<body>
    <div class='div-Parent'>
        <?php 
            $title = !empty($_GET['t'])?$_GET['t']:'-';
            $channel = !empty($_GET['c'])?$_GET['c']:'-';
            $id = !empty($_GET['i'])?$_GET['i']:'-';
            $img = !empty($_GET['im'])?$_GET['im']:'-';
            
            echo "<div class='div-Top'>
                    <div class='div-Top-Sub'><a>ID:</a><input class='inputURL' placeholder='' value='".$id."'/></div>
                    <div class='div-Top-Sub'><a>Title:</a><input class='inputName' id='idName' value='".$title."'/></div>
                    <div class='div-Top-Sub'><a>Channel:</a><input class='inputChan' id='idChan' value='".$channel."'/></div>
                    <div class='div-Top-Sub'><img src='".$img."'></div>
                </div>";
            
        ?>
            
        <div class='div-Bot'>
            <select class='opt'>
                    <option value='mp3'>mp3</option>
            </select>
            <button class='convert-button' id='btn'>Convert</button>
            <script src='http://192.168.1.243:80/leeleelookupphp/node/script.js'></script>
        </div>
        
    </div>
</body>

</html>