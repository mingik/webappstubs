var http = require('http');
var fs = require('fs');

http.createServer(function(req, res){
  // load only html file (css and js won't be loaded!)
  fs.readFile(__dirname + '/static/indexallin.html', function(err, data){
    if (err) {
      res.statusCode = 500;
      res.end(String(err));
    } else {
      res.end(data);
    }
  });
}).listen(3000);
