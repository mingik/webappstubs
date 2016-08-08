var WebSocket = require('ws').Server;
var wss = new WebSocket({port:3001});

wss.on('connection', function connection(ws){
  ws.on('message', function incoming(message){
    console.log('received: %s', message);
    ws.send('got it');

    // timeout
    setTimeout(function timeout(){
      ws.send(Date.now().toString());
    }, 500);
  });

  ws.on('close', function close(){
    console.log('client closed ws');
  });
});

var express = require('express');
var app = express();

app.get('/', function(req,res){
  res.send('To use WebSockets, open console and type:<br><br> <code>var ws = new WebSocket("ws://localhost:3001");<br> ws.onmessage = function(msg){console.log(msg);};<br> ws.send("hi");</code>');
});

app.listen(3000);