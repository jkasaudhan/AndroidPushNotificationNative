<?php
if (isset($_GET["regID"]) && isset($_GET["message"])) {
    $regId = $_GET["regID"];
    $message = $_GET["message"];
     
    include_once 'GCM.php';
     
    $gcm = new GCM();
	echo 'RegID ='.$regId.'</br>';
	echo 'Message = '.$message.'</br>';
    $registatoin_ids = array($regId);
    $message = array("message" => $message);
 
    $result = $gcm->send_notification($registatoin_ids, $message);
 
    echo $result;
}
?>