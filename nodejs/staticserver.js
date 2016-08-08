var http = require('http');
var fs = require('fs');
//var zlib = require('zlib');

http.createServer(function(req, res){

  // serve one file
  var stream = fs.createReadStream(__dirname + '/static/indexallin.html');

  // Handle errors:
  stream.on('error', function(err){
    console.trace();
    console.error('Stack:', err.stack);
    console.error('The error raised was:', err);
    // add 404 or 500 to res
  });

  // Pipe to res
  stream
//  .pipe(zlib.createGzip())
  .pipe(res);
}).listen(3000);