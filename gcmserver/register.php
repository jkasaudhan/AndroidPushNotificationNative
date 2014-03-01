<?php
 
// response json
$json = array();
 
/**
 * Registering a user device
 * Store reg id in users table
 */
 //echo 'name='.isset($_GET["name"]);
 //echo 'email='. isset($_GET["email"]);
 //echo 'regID='. isset($_GET["regID"]);
if (isset($_GET["name"]) && isset($_GET["email"]) && isset($_GET["regID"])) {
    $name = $_GET["name"];
    $email = $_GET["email"];
    $gcm_regid = $_GET["regID"]; // GCM Registration ID
    // Store user details in db
	echo $name.$email.$gcm_regid;
    include_once 'db_functions.php';
    include_once 'GCM.php';
	echo 'inside...';
    $database = new DB_Functions();
    $gcm = new GCM();
 
    $res = $database->storeUser($name, $email, $gcm_regid);
 
    $registatoin_ids = array($gcm_regid);
    //$message = array("product" => "shirt");
 
    //$result = $gcm->send_notification($registatoin_ids, $message);
    // return "data saved successfully";
    //echo $result;
}
 else {
    echo 'User details missing';
}
?>