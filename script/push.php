<?php

include_once 'config.php';
$now = date('Y-m-d');
echo "\r\n\r\n\r\n\r\n### TRIGGER STARTS ###\r\n\r\n";

## GET DATA

$curl = curl_init();

curl_setopt_array($curl, array(
  CURLOPT_URL => getBaseUrl() . '/bits.json?orderBy="due"&equalTo="' . $now .'"',
  CURLOPT_RETURNTRANSFER => true,
  CURLOPT_ENCODING => "",
  CURLOPT_MAXREDIRS => 10,
  CURLOPT_TIMEOUT => 30,
  CURLOPT_HTTP_VERSION => CURL_HTTP_VERSION_1_1,
  CURLOPT_CUSTOMREQUEST => "GET",
  CURLOPT_POSTFIELDS => "",
  CURLOPT_HTTPHEADER => getHeaders(),
));

$response = curl_exec($curl);
$err = curl_error($curl);

curl_close($curl);

if ($err) {
  echo "cURL Error #:" . $err;
} else {
  echo $response;
}


## PROCESS DATA

$data_all = array_values(json_decode($response, true));
if(count($data_all) == 0)   die("\nNo data for date: {$now}\n");
$data = $data_all[0];

$push = [
  "to" => "/topics/all",
  "data" => [
    "title" => $data["title"],
    "message" => $data["message"],
    "icon" => $data["icon"],
  ]
];
$push_json = json_encode($push);
echo "\r\n\r\n".$push_json. "\r\n\r\n";

## PUSH DATA

$curl = curl_init();

curl_setopt_array($curl, array(
  CURLOPT_URL => "https://fcm.googleapis.com/fcm/send",
  CURLOPT_RETURNTRANSFER => true,
  CURLOPT_ENCODING => "",
  CURLOPT_MAXREDIRS => 10,
  CURLOPT_TIMEOUT => 30,
  CURLOPT_HTTP_VERSION => CURL_HTTP_VERSION_1_1,
  CURLOPT_CUSTOMREQUEST => "POST",
  CURLOPT_POSTFIELDS => $push_json,
  CURLOPT_HTTPHEADER => getHeaders(),
));

$response = curl_exec($curl);
$err = curl_error($curl);

curl_close($curl);

if ($err) {
  echo "cURL Error #:" . $err;
} else {
  echo $response;
}